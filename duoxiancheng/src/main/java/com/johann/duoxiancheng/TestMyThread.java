package com.johann.duoxiancheng;

/**
 * @ClassName: TestMyThread
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class TestMyThread {
    public static void main(String[] args) {
        System.out.println("JVM启动main主线程，main线程执行main方法");
        //创建子线程对象
        MyTread tread = new MyTread();
        //启动线程
        tread.start();

        //main线程后续代码
        System.out.println("这是main线程，后续其他内容。。。");
        try {
            for (int i=0;i<10;i++){
                System.out.println("main线程-- : "+i);
                int times = (int)Math.random()*1000;
                Thread.sleep(times);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     *  调用线程的start方法来启动线程，启动线程的实质就是请求 JVM 运行相应的线程，这个线程具体在什么时间运行，
     *  由线程调度器（Scheduler）决定。
     *  1，start()方法调用结束，并不意味着子线程开始运行。start()方法，只是通知线程调度器（Scheduler），这个子线程准备好了，可以执行了。
     *  2，启动新开启的线程，会执行run()方法。
     *  3，如果开启了多个线程，start()方法的调用顺序，并不一定就是线程的启动顺序。具体哪个线程先执行，由线程调度器决定。
     *  4，多线程的运行结果，与代码的执行顺序或者调用顺序无关。
     **/


}
