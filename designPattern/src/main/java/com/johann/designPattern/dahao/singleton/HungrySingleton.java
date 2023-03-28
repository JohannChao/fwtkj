package com.johann.designPattern.dahao.singleton;

/**
 * @ClassName HungrySingleton
 * @Description 饿汉式单例模式
 * @Author Johann
 **/
public class HungrySingleton {

    /**
     * 类在首次加载的时候，就创建好了一个实例对象，且之后不再改变，因此线程是安全的，可用于多线程。
     * 缺点：如果构造的单例很大，构造完又迟迟不使用，会导致资源浪费。
     **/
    private static final HungrySingleton INSTANCE = new HungrySingleton();

    private HungrySingleton(){};

    public static HungrySingleton getInstance(){
        System.out.println("我是饿汉式单例！");
        return INSTANCE;
    }
}
