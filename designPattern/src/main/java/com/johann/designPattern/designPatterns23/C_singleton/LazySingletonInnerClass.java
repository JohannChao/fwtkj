package com.johann.designPattern.designPatterns23.C_singleton;

/** 懒汉式单例模式四,通过静态内部类来实现懒加载
 * @ClassName: LazySingletonInnerClass
 * @Description: 懒汉式（避免上面的资源浪费）、线程安全、代码简单。
 * 因为java机制规定，内部类SingletonHolder只有在getInstance()方法第一次调用的时候才会被加载（实现了lazy），
 * 而且其加载过程是线程安全的（实现线程安全）。内部类加载的时候实例化一次instance。
 * @Author: Johann
 * @Version: 1.0
 **/
public class LazySingletonInnerClass {

    /**
     * @Description: 静态内部类，只有在调用getInstance()方法时，才会加载SingletonHolder类，从而实现懒加载
     * @Param:
     * @return:
     * @Author: Johann
     */
    private static class SingletonHolder{
        private static final LazySingletonInnerClass instance = new LazySingletonInnerClass();
    }

    private LazySingletonInnerClass() {
    }

    public static LazySingletonInnerClass getInstance() {
        return SingletonHolder.instance;
    }
}
