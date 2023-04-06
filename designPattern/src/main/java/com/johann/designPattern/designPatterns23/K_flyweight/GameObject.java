package com.johann.designPattern.designPatterns23.K_flyweight;

/**
 * 游戏对象类，包含内部状态，外部状态
 * @author Johann
 * @version 1.0
 * @see
 **/
public abstract class GameObject {
    //游戏内部状态,使用享元工厂来共享内部状态
    protected GameAsset gameAsset;
    public abstract void display();
    public void setGameAsset(GameAsset gameAsset) {
        this.gameAsset = gameAsset;
    }
}
