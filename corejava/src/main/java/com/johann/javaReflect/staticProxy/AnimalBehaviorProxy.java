package com.johann.javaReflect.staticProxy;

import java.util.Date;

/** 当前代理类是对 DogBehavior 这个实现类的功能进行增强
 * @ClassName: AnimalBehaviorProxy
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class AnimalBehaviorProxy implements AnimalBehavior{

    //被代理的对象
    private AnimalBehavior target;

    public AnimalBehaviorProxy(AnimalBehavior target){
        this.target = target;
    }
    @Override
    public void eat() {
        before();
        //真正执行业务逻辑的还是真实主题类
        target.eat();
        after();
    }

    @Override
    public void move() {
        before();
        //真正执行业务逻辑的还是真实主题类
        target.move();
        after();
    }

    /**
     * 执行方法前做什么事情
     */
    private void before(){
        System.out.println(String.format("before : [%s] ",new Date()));
    }
    /**
     * 执行方法后做什么事情
     */
    private void after(){
        System.out.println(String.format("after : [%s]",new Date()));
    }
}
