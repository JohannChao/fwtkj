package com.johann.designPattern.dahao.proxy;

/** 真实主题类
 * @ClassName: DogBehavior
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
