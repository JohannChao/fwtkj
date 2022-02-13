package com.johann.duoxiancheng.createThread;

/**
 * @ClassName: MyRunnable
 * @Description: 线程已经有父类了，此时可以用实现Runnable接口的方式来创建线程
 * @Author: Johann
 * @Version: 1.0
 **/
public class MyRunnable implements Runnable{
    @Override
    public void run() {
        System.out.println("这是Runable子线程打印的内容。");
        try {
            for (int i=0;i<10;i++){
                System.out.println("Runable子线程-- : "+i);
                int times = (int)Math.random()*1000;
                Thread.sleep(times);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
