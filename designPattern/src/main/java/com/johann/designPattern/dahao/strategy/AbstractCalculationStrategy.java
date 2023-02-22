package com.johann.designPattern.dahao.strategy;

import java.math.BigDecimal;

/** 策略模式——策略模式公共算法基类
 * @ClassName: AbstractCalculation
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public abstract class AbstractCalculationStrategy {
    public abstract BigDecimal calculate(Double unitPrice,Double quantity);
}
