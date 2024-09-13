package com.johann.algorithm.rateLimiting;

import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 固定窗口计时器算法
 *
 * 实现原理：
 * 固定窗口计数器算法将时间分割成固定长度的窗口，每个窗口对应一个计数器。当事件发生时，算法会检查当前窗口的计数器是否已达到阈值。
 * 如果未达到，则允许事件发生并增加计数器；如果已达到，则拒绝事件。当时间推进到下一个窗口时，计数器会被重置。
 *
 * 优缺点：
 *  - 实现简单，易于理解。
 *  - 临界值问题：在窗口边界处，可能会出现请求集中现象，导致在两个窗口之间的请求总数超过阈值。
 *   （每个窗口为 1s，阈值为 2。如果请求发生在前一个窗口的后 500ms 和后一个窗口的钱500ms，那么就会出现在 1s 内请求了 4 次）
 *  - 限流不够平滑：如果请求在窗口的前半部分集中，后半部分则可能空闲，导致资源利用不均。
 *
 *  固定窗口计数器算法适用于请求分布相对均匀的场景，但在请求可能在短时间内集中到达的场景下，可能需要考虑更复杂的限流算法，如滑动窗口或令牌桶算法。
 */
public class T01_FixedWindowCounter {
    // 阈值
    private static Integer QPS = 2;
    // 时间窗口（毫秒）
    private static long TIME_WINDOWS = 1000;
    // 计数器
    private static AtomicInteger REQ_COUNT = new AtomicInteger();

    private static long START_TIME = System.currentTimeMillis();

    public synchronized static boolean tryAcquire() {
        if ((System.currentTimeMillis() - START_TIME) > TIME_WINDOWS) {
            REQ_COUNT.set(0);
            START_TIME = System.currentTimeMillis();
        }
        return REQ_COUNT.incrementAndGet() <= QPS;
    }

    /**
     * 在该示例中，出现了在窗口边界的请求集中现象，导致在两个窗口之间的请求总数超过阈值。
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(500);
        for (int i = 0; i < 10; i++) {
            Thread.sleep(200);
            LocalTime now = LocalTime.now();
            if (!tryAcquire()) {
                System.out.println(now + " 被限流");
            } else {
                System.out.println(now + " 做点什么");
            }
        }
    }
}

