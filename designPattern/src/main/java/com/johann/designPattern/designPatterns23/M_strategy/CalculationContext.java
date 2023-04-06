package com.johann.designPattern.designPatterns23.M_strategy;

import java.math.BigDecimal;

/** 用于配置具体的策略
 * @ClassName: CalculationContext
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class CalculationContext {

    private final AbstractCalculationStrategy strategy;

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
