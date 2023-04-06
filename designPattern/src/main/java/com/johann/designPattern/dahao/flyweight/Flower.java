package com.johann.designPattern.dahao.flyweight;

/**
 * 游戏对象Flower,包含内部状态，外部状态
 * @author Johann
 * @version 1.0
 * @see
 **/
public class Flower extends GameObject {

    //游戏对象，外部状态
    private int x,y;

    public Flower(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void display() {
        System.out.println(String.format("绘制一朵%s的花，位置(%d, %d)", gameAsset, x, y));
    }
}
