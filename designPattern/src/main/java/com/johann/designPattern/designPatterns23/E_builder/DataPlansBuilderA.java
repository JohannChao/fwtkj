package com.johann.designPattern.designPatterns23.E_builder;

/**
 * 套餐A
 */
public class DataPlansBuilderA extends DataPlansBuilder{

    private DataPlans dataPlans = new DataPlans("套餐A");
    @Override
    public void buildCallTime() {
        dataPlans.add(new CallTime("200分钟"));
    }

    @Override
    public void buildCellularData() {
        dataPlans.add(new CellularData("50G"));
    }

    @Override
    public DataPlans build() {
        return dataPlans;
    }
}
