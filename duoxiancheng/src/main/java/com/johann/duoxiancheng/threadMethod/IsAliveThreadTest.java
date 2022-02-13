package com.johann.duoxiancheng.threadMethod;

/**
 * @ClassName: IsAliveThreadTest
 * @Description: 测试 isAlive（）方法
 * @Author: Johann
 * @Version: 1.0
 **/
public class IsAliveThreadTest {
    public static void main(String[] args) throws InterruptedException {
        Thread subIsAliveThread = new SubIsAliveThread();
        System.out.println("begin : "+subIsAliveThread.isAlive()); // false
        subIsAliveThread.start();
        Thread.sleep(2000);
        System.out.println("end : "+subIsAliveThread.isAlive()); // sleep 2 秒，子线程结束，false
    }
}

class SubIsAliveThread extends Thread{
    @Override
    public void run() {
        System.out.println("run()方法内，this.isAlive() ： "+this.isAlive()); // 运行状态 true
        System.out.println("run()方法内，Thread.currentThread().isAlive() ： "+Thread.currentThread().isAlive());
    }
}
