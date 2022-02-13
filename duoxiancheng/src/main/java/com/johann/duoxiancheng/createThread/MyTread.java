package com.johann.duoxiancheng.createThread;

/**
 * @ClassName: MyTread
 * @Description: 定义类继承Thread
 * @Author: Johann
 * @Version: 1.0
 **/
public class MyTread extends Thread{

    /**
     * @Author: Johann
     * @Description: 重写Thread父类中的run()。run() 方法体内的代码就是子线程要执行的任务。
     * @Param: []
     * @return: void
     **/
    @Override
    public void run() {
        //super.run();
        System.out.println("这是子线程打印的内容。");
        try {
            for (int i=0;i<10;i++){
                System.out.println("子线程-- : "+i);
                int times = (int)Math.random()*1000;
                Thread.sleep(times);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
