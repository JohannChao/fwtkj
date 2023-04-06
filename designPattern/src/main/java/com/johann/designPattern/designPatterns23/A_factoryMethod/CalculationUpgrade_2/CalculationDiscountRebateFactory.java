package com.johann.designPattern.designPatterns23.A_factoryMethod.CalculationUpgrade_2;

/** 先打折，再满返
 * @ClassName: CalculationDiscountRebateFactory
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class CalculationDiscountRebateFactory implements IFactory{
    /**
     * 折扣,满多少,减多少
     */
    private final double discount,costCondition,costRebate;

    public CalculationDiscountRebateFactory(double discount,double costCondition,double costRebate){
        this.discount = discount;
        this.costCondition = costCondition;
        this.costRebate = costRebate;
    }

    /**
     * @return
     */
    @Override
    public ICalculation createCalculationMode() {
        //正常收费
        ICalculation normal = new CalculationNormal();
        //满返
        AbstractCalculationStrategyDecorator rebate = new CalculationRebate(this.costCondition,this.costRebate);
        //折扣
        AbstractCalculationStrategyDecorator discount = new CalculationDiscount(this.discount);
        rebate.decorate(normal);
        discount.decorate(rebate);
        return discount;
    }
}
