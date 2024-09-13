package com.johann.algorithm.rateLimiting;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.atomic.AtomicLong;
/**
 * 令牌桶算法，使用一个令牌桶来调节数据流的速率，允许一定程度的流量突发。桶初始时为空，并以固定的速率填充令牌，直至达到预设的容量上限。
 * 与漏桶算法不同，令牌桶算法在桶未满时，可以在每个时间间隔内向桶中添加多个令牌，从而积累处理突发请求的能力。
 * 当请求到达时，如果桶中存在令牌，算法会从桶中移除相应数量的令牌来处理请求。如果桶中的令牌不足，请求将被延迟处理或根据策略拒绝服务。
 * 如果桶已满，额外的令牌将不会被添加，确保了令牌数量不会超过桶的容量限制。
 *
 * 基本概念：
 *   - 令牌桶：想象一个固定容量的桶，里面装有令牌。
 *   - 令牌：可以理解为允许执行操作的许可。
 *   - 放入速率：以固定速率向桶中放入令牌。
 *   - 取出机制：每次请求都会尝试从桶中取出一个令牌。
 *
 * 核心原理：
 * a) 令牌生成：系统以固定速率生成令牌（例如，每秒10个），令牌持续生成，直到桶满为止。
 * b) 处理请求：当请求到达时，会尝试从桶中获取一个令牌。如果有可用令牌，请求被处理。如果没有令牌，请求被拒绝或延迟。
 *
 */
public class T04_TokenBucket {
    // 用于同步访问，保证并发安全
    private final Lock lock = new ReentrantLock();
    // 桶的容量
    private final int capacity;
    // 当前的令牌数
    private AtomicInteger tokens;
    // 令牌的填充速率（每秒填充的令牌数）
    private final int refillRate;
    // 上次填充令牌的时间
    private long lastRefillTimestamp;

    // 构造函数初始化 TokenBucket 实例
    public T04_TokenBucket(int capacity, int refillRate) {
        this.capacity = capacity;
        this.tokens = new AtomicInteger(capacity); // 初始化时桶被填满
        this.refillRate = refillRate;
        this.lastRefillTimestamp = System.currentTimeMillis(); // 设置为当前时间
    }

    /**
     * 尝试获取 permits 个令牌，如果获取成功则返回true，否则返回false
     * @param permits
     * @return
     */
    public boolean tryAcquire(int permits) {
        // 进入临界区，确保操作的原子性
        lock.lock();
        try {
            refill();
            System.out.println("当前令牌数量: "+tokens.get());

            // 如果桶中令牌足够，则移除相应的令牌并允许请求通过
            if (tokens.get() >= permits) {
                tokens.addAndGet(-permits);
                return true;
            } else {
                // 如果桶中令牌不足以支持当前的流量突发，则请求被拒绝
                return false;
            }
        } finally {
            // 释放锁
            lock.unlock();
        }
    }

    // 按照令牌速率填充令牌
    private void refill() {
        long now = System.currentTimeMillis(); // 获取当前时间
        // 计算自上次填充以来经过的秒数
        double timeElapsed = (now - lastRefillTimestamp) / 1000.0;
        System.out.println("自上次填充以来，经过的时间: "+timeElapsed);
        // 根据 refillRate 计算应该添加的令牌数
        int tokensToAdd = (int) (refillRate * timeElapsed);
        System.out.println("自上次填充以来，应该填充的令牌数量: "+tokensToAdd);

        if (tokensToAdd > 0) {
            // 更新令牌数，但不超过桶的容量
            int currentTokens = Math.min(tokens.get() + tokensToAdd, capacity);
            tokens.set(currentTokens);
            // 更新上次填充时间
            lastRefillTimestamp = now;
        }
    }


    // 主函数是程序的入口点
    public static void main(String[] args) {
        // 创建一个新的令牌桶实例，桶的容量为10，每秒填充2个令牌
        T04_TokenBucket limiter = new T04_TokenBucket(10, 2);

        // 模拟请求，观察限流效果
        // 循环15次，每次请求判断是否被允许
        for (int i = 0; i < 30; i++) {
            if (limiter.tryAcquire(1)) {
                System.out.println("Request " + (i + 1) + " allowed");
            } else {
                System.out.println("Request " + (i + 1) + " rejected");
            }

            // 为了观察效果，延迟一段时间
            try {
                Thread.sleep(100); // 每次请求间隔500毫秒
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 恢复中断状态
            }
        }
    }
}

/**
 * 使用独立线程添加令牌 的Java令牌桶限流算法
 */
class TokenBucketWithThread {
    // 令牌桶的容量
    private final long capacity;
    // 每次补充的令牌数
    private final long refillTokens;
    // 补充令牌的时间间隔（毫秒）
    private final long refillPeriodMillis;
    // 当前可用的令牌数，使用AtomicLong保证线程安全
    private final AtomicLong availableTokens;
    // 补充令牌的线程
    private final Thread refillThread;

    public TokenBucketWithThread(long capacity, long refillTokens, long refillPeriodMillis) {
        this.capacity = capacity;
        this.refillTokens = refillTokens;
        this.refillPeriodMillis = refillPeriodMillis;
        this.availableTokens = new AtomicLong(capacity);
        this.refillThread = new Thread(this::refillLoop);
        // 设置为守护线程
        this.refillThread.setDaemon(true);
        // 启动补充令牌的线程
        this.refillThread.start();
    }

    /**
     * 尝试获取一个令牌
     * @return 如果成功获取到令牌返回true，否则返回false
     */
    public boolean tryAcquire() {
        // return availableTokens.updateAndGet(current -> current > 0 ? current - 1 : current) != availableTokens.get();
        long currentTokens = availableTokens.get();
        while (currentTokens > 0 && !availableTokens.compareAndSet(currentTokens, currentTokens - 1)) {
            currentTokens = availableTokens.get();
        }
        return currentTokens > 0;
    }

    /**
     * 补充令牌的循环逻辑
     */
    private void refillLoop() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(refillPeriodMillis);
                // 计算新的令牌数
                long newTokens = Math.min(capacity, availableTokens.get() + refillTokens);
                // 更新可用令牌数
                availableTokens.set(newTokens);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void shutdown() {
        refillThread.interrupt();
    }

    public static void main(String[] args) throws InterruptedException {
        TokenBucketWithThread limiter = new TokenBucketWithThread(10, 1, 100); // 10个令牌容量，每100毫秒添加1个令牌

        for (int i = 0; i < 20; i++) {
            System.out.println("Try acquire: " + limiter.tryAcquire());
            Thread.sleep(50);
        }
        // 关闭补充令牌的线程
        limiter.shutdown();
    }
}
