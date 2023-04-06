package com.johann.designPattern.designPatterns23.E_builder;

/**
 * 套餐B
 */
public class DataPlansBuilderB extends DataPlansBuilder{

    private DataPlans dataPlans = new DataPlans("套餐B");
    @Override
    public void buildCallTime() {
        dataPlans.add(new CallTime("100分钟"));
    }

    @Override
    public void buildCellularData() {
        dataPlans.add(new CellularData("100G"));
    }

    @Override
    public DataPlans build() {
        return dataPlans;
    }
}
