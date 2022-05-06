package com.johann.javaReflect.staticProxy;

/** 真实主题类
 * @ClassName: DogBehavior
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class DogBehavior implements AnimalBehavior{
    @Override
    public void eat() {
        System.out.println("小狗爱吃排骨肉！");
    }

    @Override
    public void move() {
        System.out.println("小狗善于奔跑！");
    }
}
