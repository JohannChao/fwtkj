package com.johann.designPattern.dahao.flyweight;

/**
 * 享元模式，客户端类
 * @author Johann
 * @version 1.0
 * @see
 **/
public class ZyhFlyweightDemo {
    public static void main(String[] args) {
        //创建一个游戏对象
        GameObject tree = new Tree(1, 2, 3, 4);
        tree.setGameAsset(GameAssetFactory.getGameAsset("green", "tree"));
        tree.display();

        GameObject tree2 = new Tree(5, 6, 7, 8);
        tree2.setGameAsset(GameAssetFactory.getGameAsset("green", "tree"));
        tree2.display();

        GameObject tree3 = new Tree(9, 10, 11, 12);
        tree3.setGameAsset(GameAssetFactory.getGameAsset("yellow", "tree"));
        tree3.display();

        GameObject flower = new Flower(5, 6);
        flower.setGameAsset(GameAssetFactory.getGameAsset("red", "flower"));
        flower.display();

        GameObject flower2 = new Flower(7, 8);
        flower2.setGameAsset(GameAssetFactory.getGameAsset("red", "flower"));
        flower2.display();

        GameObject flower3 = new Flower(9, 10);
        flower3.setGameAsset(GameAssetFactory.getGameAsset("pink", "flower"));
        flower3.display();

    }
}
