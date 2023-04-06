package com.johann.designPattern.designPatterns23.C_singleton;

/** 懒汉式单例模式一,简单的懒汉式单例
 * @ClassName: LazySingletonOne
 * @Description: 线程不安全,如果两个线程同时进入if(singleton == null),都判断是null,那么就会得到两个实例
 * @Author: Johann
 * @Version: 1.0
 **/
public class LazySingletonOne {
    private static LazySingletonOne singleton = null;

    private LazySingletonOne() {
    }

    /**
     * @Description: 线程不安全,如果两个线程同时进入if(singleton == null),都判断是null,那么就会得到两个实例
     * @Param: []
     * @return: LazySingleton
     * @Author: Johann
     */
    public static LazySingletonOne getInstance() {
        if (singleton == null) {
            singleton = new LazySingletonOne();
        }
        System.out.println("我是简单的懒汉式单例！");
        return singleton;
    }


}
