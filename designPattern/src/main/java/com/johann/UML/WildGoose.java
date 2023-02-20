package com.johann.UML;

/** 大雁
 * @ClassName: WildGoose
 **/
public class WildGoose extends Bird implements IFly{

    @Override
    public void reproduce() {
        System.out.println("大雁繁殖。");
    }

    @Override
    public void layEggs() {
        System.out.println("大雁下蛋。");
    }

    @Override
    public void fly() {
        System.out.println("大雁会飞。");
    }
}
