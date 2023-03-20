package com.johann.designPattern.dahao.decorator.CalculationUpgrade_1;

import java.math.BigDecimal;

/** 策略模式——策略模式公共算法基类【装饰抽象类】
 * @ClassName: AbstractCalculationStrategyDecorator
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public abstract class AbstractCalculationStrategyDecorator implements ICalculation{

    private ICalculation calculation;

    /**
     * 添加装饰功能组件
     * @param calculation
     */
    public void decorate(ICalculation calculation){
        this.calculation = calculation;
    }


    public BigDecimal calculate(Double unitPrice,Double quantity){
        BigDecimal result = BigDecimal.ZERO;
        if (calculation != null){
            result = this.calculation.calculate(unitPrice,quantity);
        }
        return result;
    };
}
