package com.johann.designPattern.designPatterns23.K_flyweight;

import lombok.Data;

/**
 * 游戏资产类,享元类,内部状态
 * @author Johann
 * @version 1.0
 * @see
 **/
@Data
public class GameAsset {
    private String color;
    private String type;

    public GameAsset(String color, String type) {
        this.color = color;
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    //重写toString方法，用于打印对象信息
    @Override
    public String toString() {
        return String.format(" [color: %s, type: %s] ", color, type);
    }
}
