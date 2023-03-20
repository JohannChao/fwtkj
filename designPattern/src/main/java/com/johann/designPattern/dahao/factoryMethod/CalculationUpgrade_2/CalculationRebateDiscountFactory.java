package com.johann.designPattern.dahao.factoryMethod.CalculationUpgrade_2;

/** 先满返，再打折
 * @ClassName: CalculationDiscountRebateFactory
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class CalculationRebateDiscountFactory implements IFactory{
    /**
     * 折扣,满多少,减多少
     */
    private final double discount,costCondition,costRebate;

    public CalculationRebateDiscountFactory(double discount, double costCondition, double costRebate){
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
        //折扣
        AbstractCalculationStrategyDecorator discount = new CalculationDiscount(this.discount);
        //满返
        AbstractCalculationStrategyDecorator rebate = new CalculationRebate(this.costCondition,this.costRebate);
        discount.decorate(normal);
        rebate.decorate(discount);
        return rebate;
    }
}
