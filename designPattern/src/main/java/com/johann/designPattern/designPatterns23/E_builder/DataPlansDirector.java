package com.johann.designPattern.designPatterns23.E_builder;

/**
 * 指挥者类
 */
public class DataPlansDirector {
    public void construct(DataPlansBuilder builder){
        builder.buildCallTime();
        builder.buildCellularData();
    }
}
