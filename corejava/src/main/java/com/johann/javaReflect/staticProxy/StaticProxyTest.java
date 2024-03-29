package com.johann.javaReflect.staticProxy;

/**
 * @ClassName: StaticProxyTest
 * @Author: Johann
 * @Version: 1.0
 **/
public class StaticProxyTest {

    public static void main(String[] args) {
        AnimalBehavior animalBehavior = new DogBehavior();
        AnimalBehaviorProxy proxy = new AnimalBehaviorProxy(animalBehavior);
        proxy.eat();
        proxy.move();
    }


}
