package com.johann.duoxiancheng.threadMethod;

/**
 * @ClassName: CurrentThreadTest02
 * @Description: 分析 CurrentThread（）返回的线程对象 和 this指向的线程对象 的区别
 * @Author: Johann
 * @Version: 1.0
 **/
public class CurrentThreadTest02 {
    public static void main(String[] args) throws InterruptedException {
        //创建子线程
        Thread subCurrentThread02 = new SubCurrentThread02();
        subCurrentThread02.setName("subCurrentThread02");
        subCurrentThread02.start();

        Thread.sleep(1000);

        // Thread(Runnable)构造方法的形参是Runnable接口，调用时传递的实参是接口的实现对象
        Thread t3 = new Thread(subCurrentThread02);
        t3.setName("t3");
        t3.start();
    }
    /*
     * 结果显示：
     *  1）构造器中的，Thread.currentThread().getName() : main
        2）构造器中的，his.getName() : Thread-0
        3）run()方法中的，Thread.currentThread().getName() : subCurrentThread02
        4）run()方法中的，this.getName() : subCurrentThread02
        5）run()方法中的，Thread.currentThread().getName() : t3
        6）run()方法中的，this.getName() : subCurrentThread02
     *
     * 结果分析：
     *  在 1）中，显示的结果是 “main”，这是因为，这个执行这个构造器方法的线程就是main线程【执行当前代码的线程就是当前线程】
     *  在 2）中，显示的结果是 “Thread-0”，是一个子线程名字。这是因为此时获取的不是执行当前代码的线程（CurrentThread），而是当前对象（this），
     *  即 new 出来的这个 SubCurrentThread02 对象【此时还没有被重命名】，的名字。
     *  在 3） 4）中，显示的是 “SubCurrentThread02”，这是因为执行start（）方法，开启了一个新的线程，新的线程执行run（）方法，
     *  此时执行当前run（）方法的线程就是这个新开启的线程，同时，this也指向这个新开启的线程对象。
     *  在 5）中，显示的是“t3”，这是因为当前线程就是新开启的这个新的线程 t3。
     *
     *  在 6）中，显示的是“subCurrentThread02”，这又是为什么呢？
     *  这是因为，我们新开启的这个新的线程，是是使用 Thread(Runnable)构造方法来创建的。点开Thread源码，发现如下代码：
     *
        虽然执行当前 run（）方法的是 t3线程对象，但是这个run（）方法内的 this 指向的是 target对象，即Runnable实参对象，
        也就是我们传入的 subCurrentThread02 对象。所以 this.getName()返回的是 “subCurrentThread02”。
     *
     **/
//    public Thread(Runnable target) {
//        init(null, target, "Thread-" + nextThreadNum(), 0);
//    }
//    @Override
//    public void run() {
//        if (target != null) {
//            target.run();
//        }
//    }
}

class SubCurrentThread02 extends Thread{
    public SubCurrentThread02(){
        System.out.println("构造器中的，Thread.currentThread().getName() : "+Thread.currentThread().getName());
        System.out.println("构造器中的，this.getName() : "+this.getName());
    }
    @Override
    public void run() {
        System.out.println("run()方法中的，Thread.currentThread().getName() : "+Thread.currentThread().getName());
        System.out.println("run()方法中的，this.getName() : "+this.getName());
    }
}