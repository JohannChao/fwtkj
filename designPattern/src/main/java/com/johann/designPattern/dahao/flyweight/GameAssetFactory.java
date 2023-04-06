package com.johann.designPattern.dahao.flyweight;

import java.util.HashMap;
import java.util.Map;

/**
 * 享元工厂类
 * @author Johann
 * @version 1.0
 * @see
 **/
public class GameAssetFactory {

    private static final Map<String,GameAsset> gameAssetMap = new HashMap<>();

    /**
     * 获取内部状态
     * @param color
     * @param type
     * @return
     */
    public static GameAsset getGameAsset(String color, String type) {
        String key = color + type;
        GameAsset gameAsset = gameAssetMap.get(key);
        if (gameAsset == null) {
            gameAsset = new GameAsset(color, type);
            gameAssetMap.put(key, gameAsset);
        }
        return gameAsset;
    }
}
