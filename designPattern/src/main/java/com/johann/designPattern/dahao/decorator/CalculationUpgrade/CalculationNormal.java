package com.johann.designPattern.dahao.decorator.CalculationUpgrade;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** 正常收费策略
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
