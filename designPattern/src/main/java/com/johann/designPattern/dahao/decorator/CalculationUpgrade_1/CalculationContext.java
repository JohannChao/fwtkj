package com.johann.designPattern.dahao.decorator.CalculationUpgrade_1;

import java.math.BigDecimal;

/** 用于配置具体的策略
 * @ClassName: CalculationContext
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class CalculationContext {

    private final ICalculation strategy;

    /**
     * 构造器中传入具体的策略
     */
    public CalculationContext(AbstractCalculationStrategyDecorator strategy){
        this.strategy = strategy;
    }

    /**
     * 策略模式+简单工厂模式
     * @param type
     */
    public CalculationContext(String type){
        switch (type) {
            case "zc":
                ICalculation nomal = new CalculationNormal();
                this.strategy = nomal;
                break;
            case "7z":
                nomal = new CalculationNormal();
                AbstractCalculationStrategyDecorator discount = new CalculationDiscount(0.7d);
                discount.decorate(nomal);
                this.strategy = discount;
                break;
            case "9z":
                nomal = new CalculationNormal();
                discount = new CalculationDiscount(0.9d);
                discount.decorate(nomal);
                this.strategy = discount;
                break;
            case "m100f10":
                nomal = new CalculationNormal();
                AbstractCalculationStrategyDecorator rebate = new CalculationRebate(100d,10d);
                rebate.decorate(nomal);
                this.strategy = rebate;
                break;
            case "8z_m100f10":
                // 先打8折，再满100返10
                nomal = new CalculationNormal();
                rebate = new CalculationRebate(100d,10d);
                discount = new CalculationDiscount(0.8d);
                rebate.decorate(nomal);
                discount.decorate(rebate);
                this.strategy = discount;
                break;
            case "m100f10_8z":
                // 先满100返10，再打8折
                nomal = new CalculationNormal();
                discount = new CalculationDiscount(0.8d);
                rebate = new CalculationRebate(100d,10d);
                discount.decorate(nomal);
                rebate.decorate(discount);
                this.strategy = rebate;
                break;
            default:
                this.strategy = null;
                break;
        }
    }

    /**
     * 根据具体的策略，调用该策略实现的方法
     * @param unitPrice
     * @param quantity
     * @return
     */
    public BigDecimal getResult(Double unitPrice,Double quantity){
        return strategy.calculate(unitPrice,quantity);
    }
}
