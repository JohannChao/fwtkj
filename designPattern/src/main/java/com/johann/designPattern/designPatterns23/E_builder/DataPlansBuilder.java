package com.johann.designPattern.designPatterns23.E_builder;

/**
 * 数据套餐抽象建造者类
 */
public abstract class DataPlansBuilder {
    //protected DataPlans dataPlans = new DataPlans();
    public abstract void buildCallTime();
    public abstract void buildCellularData();
    public abstract DataPlans build();
}
