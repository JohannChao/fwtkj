package com.johann.designPattern.dahao.singleton;

/**
 * @ClassName InnerClassLazySingleton
 * @Description 内部类实现懒汉式单例模式
 *  懒汉式（避免上面的资源浪费）、线程安全、代码简单。因为java机制规定，内部类SingletonHolder只有在getInstance()方法第一次调用的时候才会被加载（实现了lazy），
 *  而且其加载过程是线程安全的（实现线程安全）。内部类加载的时候实例化一次instance。
 * @Author Johann
 **/
public class InnerClassLazySingleton {

    /**
     * 静态内部类
     **/
    private static class SingletonHolder{
        //单例变量
        private static InnerClassLazySingleton instance = new InnerClassLazySingleton();
    }

    /**私有化的构造方法，保证外部的类不能通过构造器来实例化。*/
    private InnerClassLazySingleton() {

    }

    /**获取单例对象实例*/
    public static InnerClassLazySingleton getInstance() {
        System.out.println("我是内部类实现的懒汉式单例！");
        return SingletonHolder.instance;
    }
}
