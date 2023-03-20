package com.johann.designPattern.dahao.factoryMethod.CalculationUpgrade_2;

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
        IFactory factory = null;
        switch (type) {
            case "zc":
                factory = new CalculationRebateDiscountFactory(1d,0d,0d);
                break;
            case "7z":
                factory = new CalculationRebateDiscountFactory(0.7d,0d,0d);
                break;
            case "9z":
                factory = new CalculationRebateDiscountFactory(0.9d,0d,0d);
                break;
            case "m100f10":
                factory = new CalculationRebateDiscountFactory(1d,100d,10d);
                break;
            case "8z_m100f10":
                // 先打8折，再满100返10
                factory = new CalculationDiscountRebateFactory(0.8d,100d,10d);
                break;
            case "m100f10_8z":
                // 先满100返10，再打8折
                factory = new CalculationRebateDiscountFactory(0.8d,100d,10d);
                break;
            default:
                break;
        }
        this.strategy = (factory!=null?factory.createCalculationMode():null);
    }

    /**
     * 根据具体的策略，调用该策略实现的方法
     * @param unitPrice
     * @param quantity
     * @return
     */
    public BigDecimal getResult(Double unitPrice,Double quantity){
        return this.strategy.calculate(unitPrice,quantity);
    }
}
