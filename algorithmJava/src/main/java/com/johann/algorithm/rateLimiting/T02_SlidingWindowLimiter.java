package com.johann.algorithm.rateLimiting;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 滑动窗口算法
 */
public class T02_SlidingWindowLimiter {

}

/**
 * 使用一个队列来存储每个请求的时间戳，并根据当前时间清除队列中已经超出时间窗口的请求时间戳，然后判断剩余的请求数量是否超出限制。
 *
 * 实现原理：
 * 通过动态维护一个请求时间戳的队列来实现限流。将时间窗口视为一个滑动的时间段，而不是固定的时间段。使用一个数据结构（如队列或列表）来存储每个请求的时间戳。
 * 当请求到达时，首先记录当前时间。移除队列中所有超出时间窗口的请求（例如，当前时间减去窗口大小的请求）。检查队列的大小，如果大小超过了设定的阈值，则拒绝该请求；否则，将当前请求的时间戳加入队列。
 *
 * 优点：
 *   - 能够在任意时间段内精确控制请求数量，避免了固定窗口的临界值问题。
 *   - 能够更好地处理突发流量，避免在窗口边界处的请求集中现象。
 * 缺点：
 *   - 需要存储每个请求的时间戳，可能会增加内存使用。
 *   - 相较于固定窗口算法，滑动窗口算法实现更为复杂。
 *
 * 滑动窗口算法适用于需要平滑流量控制的场景，尤其是在面对突发流量时，能够提供比固定窗口计数器更优的流量控制效果。
 */
class SlidingWindowRateLimiter {
    private final int maxRequests;
    private final long windowSizeInMs;
    private final Queue<Long> requestTimestamps;

    public SlidingWindowRateLimiter(int maxRequests, long windowSizeInMs) {
        this.maxRequests = maxRequests;
        this.windowSizeInMs = windowSizeInMs;
        this.requestTimestamps = new LinkedList<>();
    }

    public synchronized boolean allowRequest(long currentTime) {
        // 移除窗口外的请求时间戳
        while (!requestTimestamps.isEmpty() && currentTime - requestTimestamps.peek() >= windowSizeInMs) {
            requestTimestamps.poll();
        }

        // 如果当前窗口内的请求数小于最大请求数，允许新的请求
        if (requestTimestamps.size() < maxRequests) {
            requestTimestamps.offer(currentTime);
            return true;
        }

        return false;
    }

    public static void main(String[] args) throws InterruptedException {
        SlidingWindowRateLimiter limiter = new SlidingWindowRateLimiter(5, 1000); // 每秒最多5个请求

        for (int i = 0; i < 18; i++) {
            long currentTime = System.currentTimeMillis();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(currentTime), ZoneId.systemDefault());
            System.out.println(localDateTime+" [Request I] " + (i + 1) + " allowed: " + limiter.allowRequest(currentTime));
            Thread.sleep(100); // 每100毫秒发送一个请求
        }
    }
}

/**
 * 使用固定的时间间隔（interval）将整个时间窗口（windowDuration）划分为多个小的“桶”（buckets）。
 *
 * 实现原理：
 *   滑动窗口算法通过将时间分为多个小的时间段，每个时间段内维护一个独立的计数器。
 *   当一个请求到达时，它会被分配到当前时间所在的小时间段，并检查该时间段的计数器是否已达到限制。
 *   如果未达到，则允许请求并增加计数；如果已达到，则拒绝请求。随着时间的推移，旧的时间段会淡出窗口，新的时间段会加入。
 *
 * 滑动窗口算法适用于需要平滑流量控制的场景，尤其是在面对突发流量时，能够提供比固定窗口计数器更优的流量控制效果。
 */
class SlidingWindowLimiter {
    private final Lock mutex = new ReentrantLock();
    private final int[] counters;
    private final int limit;
    private long windowStart;
    private final long windowDuration;
    private final long interval;

    // 构造函数初始化 SlidingWindowLimiter 实例
    public SlidingWindowLimiter(int limit, long windowDuration, long interval) {
        int buckets = (int) (windowDuration / interval);
        this.counters = new int[buckets];
        this.limit = limit;
        this.windowStart = System.currentTimeMillis();
        this.windowDuration = windowDuration;
        this.interval = interval;
    }

    // 判断当前请求是否被允许，并实现滑动窗口的逻辑
    public boolean allow() {
        mutex.lock();
        try {
            // 检查是否需要滑动窗口
            if (System.currentTimeMillis() - windowStart > windowDuration) {
                slideWindow();
            }

            long now = System.currentTimeMillis();
            int index = (int) ((now - windowStart) / interval) % counters.length;
            if (counters[index] < limit) {
                counters[index]++;
                return true;
            }
            return false;
        } finally {
            mutex.unlock();
        }
    }

    // 实现滑动窗口逻辑，移除最旧的时间段并重置计数器
    private void slideWindow() {
        // 滑动窗口，忽略最旧的时间段
        System.arraycopy(counters, 1, counters, 0, counters.length - 1);
        // 重置最后一个时间段的计数器
        counters[counters.length - 1] = 0;
        // 更新窗口开始时间
        windowStart = System.currentTimeMillis();
    }

    // 主函数是程序的入口点
    public static void main(String[] args) {
        SlidingWindowLimiter limiter = new SlidingWindowLimiter(1, 1000, 10);
        for (int i = 0; i < 100; i++) {
            if (limiter.allow()) {
                System.out.println("Request " + (i + 1) + " allowed");
            } else {
                System.out.println("Request " + (i + 1) + " rejected");
            }
        }
    }
}
