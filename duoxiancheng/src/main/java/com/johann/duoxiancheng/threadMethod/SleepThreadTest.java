package com.johann.duoxiancheng.threadMethod;

/**
 * @ClassName: SleepThreadTest
 * @Description: 测试 sleep 方法。让当前线程【currentThread】休眠
 * @Author: Johann
 * @Version: 1.0
 **/
public class SleepThreadTest {
    public static void main(String[] args) {
        Thread subSleepThread = new SubSleepThread();
        System.out.println("main方法内，threadName : "+Thread.currentThread().getName()+", begin : "+System.currentTimeMillis());

        // start()方法，开启一个新的线程，run（）方法内，休眠的是新开启的子线程。
        // 休眠的是子线程
        //subSleepThread.start();

        // run()方法，没有开启新的线程，此时休眠的是主线程。
        subSleepThread.run();
        System.out.println("main方法内，threadName : "+Thread.currentThread().getName()+", end : "+System.currentTimeMillis());
    }

}
class SubSleepThread extends Thread{
    @Override
    public void run() {
        try {
            System.out.println("run方法内，threadName : "+Thread.currentThread().getName()+", begin : "+System.currentTimeMillis());
            // 当前线程休眠 2000 毫秒
            Thread.sleep(2000);
            System.out.println("run方法内，threadName : "+Thread.currentThread().getName()+", end : "+System.currentTimeMillis());
        } catch (InterruptedException e) {
            // 在子线程的run（）方法中，如果有受检异常（编译时异常）需要处理，只有选择捕获处理，不能抛出处理。
            // run()方法是重写的，没有抛出异常，重写run（）方法，也不能抛出异常。
            e.printStackTrace();
        }
    }
}