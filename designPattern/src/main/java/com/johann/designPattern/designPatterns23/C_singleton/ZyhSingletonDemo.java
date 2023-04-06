package com.johann.designPattern.designPatterns23.C_singleton;

/**
 * @ClassName: ZyhSingletonDemo
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class ZyhSingletonDemo {
    public static void main(String[] args) {
        SingletonEnum singletonEnum = SingletonEnum.INSTANCE;
        singletonEnum.setValue(1);
        System.out.println(singletonEnum.getValue());
    }
}
