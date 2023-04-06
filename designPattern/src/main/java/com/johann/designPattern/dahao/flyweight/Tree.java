package com.johann.designPattern.dahao.flyweight;

/**
 * 游戏对象Tree,包含内部状态，外部状态
 * @author Johann
 * @version 1.0
 * @see
 **/
public class Tree extends GameObject {

    //游戏对象，外部状态
    private int x,y;
    private int width,height;

    public Tree(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void display() {
        System.out.println(String.format("绘制一棵%s的树，位置(%d, %d)，大小(%d, %d)", gameAsset, x, y, width, height));
    }
}
