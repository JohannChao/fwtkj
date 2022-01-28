package com.johann.duoxiancheng;

/**
 * @ClassName: TestMyRunnable
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class TestMyRunnable {
    public static void main(String[] args) {
        System.out.println("JVM启动main主线程，main线程执行main方法");
        //创建Runnable接口的实现类对象
        MyRunnable myRunnable = new MyRunnable();
        //创建线程对象
        Thread thread = new Thread(myRunnable);
        thread.start();

        //有时调用Thread(Runnable)构造方法是，实参也会传递匿名内部类对象
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i=0;i<10;i++){
                        System.out.println("Runable_匿名内部类线程-- : "+i);
                        int times = (int)Math.random()*1000;
                        Thread.sleep(times);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread2.start();

        //main线程后续代码
        System.out.println("这是main线程，后续其他内容。。。");
        try {
            for (int i=0;i<10;i++){
                System.out.println("Runable_main线程-- : "+i);
                int times = (int)Math.random()*1000;
                Thread.sleep(times);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
