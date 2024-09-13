package com.johann.algorithm.rateLimiting;
import java.util.concurrent.LinkedBlockingQueue;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 漏桶限流算法，是一种常用的流量控制方法,其核心思想是将系统比作一个水桶,而请求则类比为水滴。
 *
 * 实现原理：
 * 通过一个固定容量的队列来模拟桶，以恒定速率从桶中取出请求进行处理，无论请求到达的频率如何，都保证请求以均匀的速度被处理，从而平滑流量并防止流量突增。
 *   - 桶的容量: 桶有一个固定的容量,代表系统在任一时刻能够处理的最大请求数。
 *   - 进水速率: 请求以任意速率"流入"桶中。如果桶已满,额外的请求会被直接丢弃或拒绝。
 *   - 出水速率: 水(请求)以固定速率从桶底流出,这个速率就是系统能够处理请求的速度。
 *   - 恒定流出: 无论进水速率如何变化,只要桶中有水,出水速率始终保持恒定。
 *   - 削峰填谷: 当短时间内有大量请求(洪峰)时,超过桶容量的请求会被丢弃。而在请求较少时,水桶会慢慢积水,保证了水桶始终有水可以流出。
 *
 * 优点：能够强制实现固定的数据处理速率，平滑流量。即使面对突发流量，也能保持稳定的处理速率。
 * 缺点：对于突发流量的处理不够灵活，可能会延迟处理。实现相对简单，但需要维护桶的状态。
 *
 * 漏桶算法适用于需要强制执行固定速率处理的场景，如网络流量控制、API 请求限制等。通过控制令牌的添加速率，漏桶算法能够有效地避免系统因瞬时流量高峰而过载。
 */
public class T03_LeakyBucket {
    // 桶的容量，表示在任何时刻桶中可以容纳的最大水量（请求数）。
    private final long capacity;
    // 漏水速率，表示每秒从桶中流出的水量（处理的请求数）。
    private final long leakRate;
    // 当前桶中的水量，使用AtomicLong保证线程安全。
    private final AtomicLong water;
    // 上次漏水的时间，用于计算经过的时间。
    private long lastLeakTime;


    public T03_LeakyBucket(long capacity, long leakRate) {
        this.capacity = capacity;
        this.leakRate = leakRate;
        this.water = new AtomicLong(0);
        this.lastLeakTime = System.currentTimeMillis();
    }

    /**
     * 首先调用leak()方法模拟漏水。
     * 然后检查添加新的请求后是否超过桶的容量。
     * 如果不超过，则增加水量并返回true，表示允许请求。
     * 如果超过，则返回false，表示拒绝请求。
     */
    public synchronized boolean allowRequest(int requestSize) {
        leak();

        if (water.get() + requestSize <= capacity) {
            water.addAndGet(requestSize);
            return true;
        }

        return false;
    }

    /**
     * 计算从上次漏水到现在经过的时间。
     * 根据漏水速率计算这段时间内应该漏掉的水量（这段时间，应该有多少请求被处理）。
     * 更新桶中的水量。
     */
    private void leak() {
        long now = System.currentTimeMillis();
        // 计算自上次漏水以来的时间差
        long timeElapsed = now - lastLeakTime;
        // 根据时间差和漏水速率计算流失的水量
        long leakedWater = timeElapsed * leakRate / 1000;

        // 如果有水流失
        if (leakedWater > 0) {
            // 如果流失的水量大于当前水量，则将当前水量设置为0
            if (leakedWater > water.get()) {
                water.set(0);
            } else {
                // 否则，从当前水量中减去流失的水量
                water.addAndGet(-leakedWater);
            }
            // 更新上次漏水时间为当前时间
            lastLeakTime = now;
        }
    }
}

/**
 * 使用独立线程来处理请求的 漏桶算法
 */
class LeakyBucketWithThread  {
    // 用于存储请求的队列
    private LinkedBlockingQueue<Object> queue;

    /**
     * 构造函数，初始化漏桶容量
     * @param capacity 漏桶的最大容量
     */
    public LeakyBucketWithThread(int capacity){
        this.queue = new LinkedBlockingQueue<>(capacity);
    }

    /**
     * 尝试将请求放入漏桶（请求以任意速率"流入"桶中。）
     * @return 如果请求被成功放入队列返回true，否则返回false
     */
    public boolean push() {
        return queue.offer(new Object());
    }

    /**
     * 处理队列中的请求
     * 模拟处理每个请求需要1秒钟的时间（水以固定速率从桶底流出）
     */
    public void process() {
        while (!queue.isEmpty()) {
            System.out.println("    [Consumer] Request processed at " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            try {
                // 模拟请求处理时间
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 处理完请求后从队列中移除
            queue.poll();
        }
    }

    public static void main(String[] args) {
        // 启动新的线程来处理请求
        LeakyBucketWithThread lb = new LeakyBucketWithThread(5);
        // 启动新的线程来处理请求
        new Thread(lb::process).start();

        // 模拟请求
        for (int i = 0; i < 20; i++) {
            // 尝试将请求放入漏桶
            boolean accepted = lb.push();
            if (accepted) {
                System.out.printf("[Accepted] ==== Request  %d arrived at %s\n", i + 1, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            } else {
                System.out.printf("[Rejected] ==== Request  %d arrived at %s\n", i + 1, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }

            if (i > 8){
                try {
                    // 模拟请求到来的时间间隔
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}