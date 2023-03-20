package com.johann.designPattern.dahao.factoryMethod.CalculationUpgrade_2;

import java.math.BigDecimal;

/**
 * 装饰模式组件接口
 */
public interface ICalculation {

    BigDecimal calculate(Double unitPrice, Double quantity);
}
