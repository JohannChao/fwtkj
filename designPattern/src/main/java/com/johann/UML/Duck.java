package com.johann.UML;

/** 鸭子
 * @ClassName: Duck
 **/
public class Duck extends Bird {
    @Override
    public void reproduce() {
        System.out.println("鸭子繁殖。");
    }

    @Override
    public void layEggs() {
        System.out.println("鸭子下蛋。");
    }
}
