package com.johann.designPattern.designPatterns23.C_singleton;

/** 饿汉式单例
 * @ClassName: HungrySingleton
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class HungrySingleton {
    private static final HungrySingleton singleton = new HungrySingleton();

    private HungrySingleton() {

    }

    public static HungrySingleton getInstance() {
        return singleton;
    }

}
