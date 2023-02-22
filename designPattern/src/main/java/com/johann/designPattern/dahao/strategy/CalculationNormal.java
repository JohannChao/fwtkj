package com.johann.designPattern.dahao.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** 正常收费策略
 * @ClassName: CalculationNormal
 * @Description:
 * @Author: Johann
 * @Date: 2023/2/21
 * @Version: 1.0
 **/
public class CalculationNormal extends AbstractCalculationStrategy {
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
