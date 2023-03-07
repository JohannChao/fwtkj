package com.johann.designPattern.dahao.decorator.CalculationUpgrade;

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
    public CalculationContext(AbstractCalculationStrategy strategy){
        this.strategy = strategy;
    }

    /**
     * 策略模式+简单工厂模式
     * @param type
     */
    public CalculationContext(String type){
        switch (type) {
            case "zc":
                this.strategy = new CalculationNormal();
                break;
            case "7z":
                this.strategy = new CalculationDiscount(0.7d);
                break;
            case "9z":
                this.strategy = new CalculationDiscount(0.9d);
                break;
            case "m100f10":
                this.strategy = new CalculationRebate(100d,10d);
                break;
            case "8z_m100f10":
                // 先打8折，再满100返10
                AbstractCalculationStrategy discount_8 = new CalculationDiscount(0.8d);
                AbstractCalculationStrategy rebate_100_10 = new CalculationRebate(100d,10d);
                rebate_100_10.decorate(discount_8);
                this.strategy = rebate_100_10;
                break;
            case "m100f10_8z":
                // 先满100返10，再打8折
                AbstractCalculationStrategy rebate = new CalculationRebate(100d,10d);
                AbstractCalculationStrategy discount = new CalculationDiscount(0.8d);
                discount.decorate(rebate);
                this.strategy = discount;
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
