package com.johann.designPattern.dahao.singleton;

/** 懒汉式单例模式二,同步法懒汉式单例
 * @ClassName: LazySingletonTwo
 * @Description: 加锁的方式来实现互斥，保证线程安全，但是效率低下，因为每次调用getInstance()方法时都要进行同步
 * @Author: Johann
 * @Version: 1.0
 **/
public class LazySingletonTwo {
    private static LazySingletonTwo singleton = null;

    private LazySingletonTwo() {
    }

    /**
     * @Description: 加锁的方式来实现互斥，保证线程安全，但是效率低下，因为每次调用getInstance()方法时都要进行同步
     * @Param: []
     * @return: LazySingletonTwo
     * @Author: Johann
     */
    public static synchronized LazySingletonTwo getInstance() {
        if (singleton == null) {
            singleton = new LazySingletonTwo();
        }
        System.out.println("我是同步法懒汉式单例！");
        return singleton;
    }
}
