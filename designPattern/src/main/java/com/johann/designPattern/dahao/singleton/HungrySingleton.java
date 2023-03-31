package com.johann.designPattern.dahao.singleton;

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
