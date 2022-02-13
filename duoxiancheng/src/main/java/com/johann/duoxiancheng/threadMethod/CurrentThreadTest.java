package com.johann.duoxiancheng.threadMethod;

/**
 * @ClassName: CurrentThreadTest
 * @Description: 分析 CurrentThread（）方法。执行当前代码的线程就是当前线程
 * @Author: Johann
 * @Version: 1.0
 **/
public class CurrentThreadTest {
    public static void main(String[] args) {
        System.out.println("main方法中，打印当前线程名称："+Thread.currentThread().getName());

        //调用SubCurrentThread（）构造方法来创建子线程，由于是在main线程中调用的构造方法，所以构造器中的当前线程就是“main”线程
        Thread subCurrentThread = new SubCurrentThread();
        //调用线程的start方法来启动子线程，线程会调用run()方法，所以run()方法中的当前线程就是 “子线程”
        subCurrentThread.start();

        // 如果在main（）方法中直接调用run（）方法，这不会开启新的线程，所以此时执行run（）方法的当前线程就是“main”线程
        subCurrentThread.run();
    }
}

class SubCurrentThread extends Thread{
    public SubCurrentThread(){
        System.out.println("子线程构造器中，打印当前线程名称："+Thread.currentThread().getName());
    }

    @Override
    public void run() {
        System.out.println("子线程run()方法中，打印当前线程名称："+Thread.currentThread().getName());
    }
}