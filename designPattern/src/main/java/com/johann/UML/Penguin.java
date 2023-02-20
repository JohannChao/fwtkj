package com.johann.UML;

/** 企鹅
 * @ClassName: Penguin
 **/
public class Penguin extends Bird{

    /**
     * 关联关系，强依赖的关系，被关联方作为关联方的成员变量存在。
     */
    private Climate climate;

    @Override
    public void reproduce() {
        System.out.println("企鹅繁殖。");
    }

    @Override
    public void layEggs() {
        System.out.println("企鹅下蛋。");
    }
}
