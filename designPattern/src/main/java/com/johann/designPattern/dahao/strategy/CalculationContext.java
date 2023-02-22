package com.johann.designPattern.dahao.strategy;

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
     * 根据具体的策略，调用该策略实现的方法
     * @param unitPrice
     * @param quantity
     * @return
     */
    public BigDecimal getResult(Double unitPrice,Double quantity){
        return strategy.calculate(unitPrice,quantity);
    }
}
