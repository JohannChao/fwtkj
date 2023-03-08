package com.johann.designPattern.dahao.proxy;

/**
 * @ClassName: ZyhProxyDemo
 * @Author: Johann
 * @Version: 1.0
 **/
public class ZyhProxyDemo {

    public static void main(String[] args) {
        AnimalBehavior animalBehavior = new DogBehavior();
        AnimalBehaviorProxy proxy = new AnimalBehaviorProxy(animalBehavior);
        proxy.eat();
        proxy.move();
    }


}
