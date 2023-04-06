package com.johann.designPattern.designPatterns23.O_templateMethod;

/**
 * @ClassName: WithoutTemplateDemo
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class WithoutTemplateDemo {

    public static void main(String[] args) {
        Pig pig = new Pig();
        System.out.println("当前动物是："+pig.getName());
        pig.eat();
        pig.product();

        Cow cow = new Cow();
        System.out.println("当前动物是："+cow.getName());
        cow.eat();
        cow.product();

    }

}

class Pig{
    private String name;
    public Pig(){
        this.name = "小猪";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void eat(){
        System.out.println("吃什么？");
        System.out.println("吃【饲料】");
    }
    public void product(){
        System.out.println("产什么？");
        System.out.println("产【猪肉】");
    }
}

class Cow{
    private String name;
    public Cow(){
        this.name = "奶牛";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void eat(){
        System.out.println("吃什么？");
        System.out.println("吃【青草】");
    }
    public void product(){
        System.out.println("产什么？");
        System.out.println("产【牛奶】");
    }
}
