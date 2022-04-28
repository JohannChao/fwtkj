package com.johann.javaReflect;

/**
 * @ClassName: ReflectUser
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class ReflectUser extends ReflectUserSuper{

    private String name = "Johann";

    private int age = 18;

    public String alias = "小六子";

    public ReflectUser(){
        super();
    }

    public ReflectUser(String name,int age){
        super();
        this.name = name;
        this.age = age;
    }

    private void privateMethod(){
        System.out.println("这是一个私有方法");
    }

    public void publicMethod(){
        System.out.println("这是一个公开方法");
    }

}
