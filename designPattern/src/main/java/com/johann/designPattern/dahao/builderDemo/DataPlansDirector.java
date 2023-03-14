package com.johann.designPattern.dahao.builderDemo;

/**
 * 指挥者类
 */
public class DataPlansDirector {
    public void construct(DataPlansBuilder builder){
        builder.buildCallTime();
        builder.buildCellularData();
    }
}
