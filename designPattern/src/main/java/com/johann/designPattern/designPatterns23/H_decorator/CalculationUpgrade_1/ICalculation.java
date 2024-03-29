package com.johann.designPattern.designPatterns23.H_decorator.CalculationUpgrade_1;

import java.math.BigDecimal;

/**
 * 装饰模式组件接口
 */
public interface ICalculation {

    BigDecimal calculate(Double unitPrice, Double quantity);
}
