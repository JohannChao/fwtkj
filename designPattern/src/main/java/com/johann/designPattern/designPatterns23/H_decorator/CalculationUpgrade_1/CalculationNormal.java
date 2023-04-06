package com.johann.designPattern.designPatterns23.H_decorator.CalculationUpgrade_1;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** 正常收费策略（装饰模式的基础功能）
 * @ClassName: CalculationNormal
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class CalculationNormal implements ICalculation {
    /**
     * @param unitPrice
     * @param quantity
     * @return
     */
    @Override
    public BigDecimal calculate(Double unitPrice, Double quantity) {
        return BigDecimal.valueOf(unitPrice).multiply(BigDecimal.valueOf(quantity)
                .setScale(2, RoundingMode.HALF_UP));
    }
}
