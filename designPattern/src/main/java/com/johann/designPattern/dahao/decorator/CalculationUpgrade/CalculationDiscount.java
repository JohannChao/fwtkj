package com.johann.designPattern.dahao.decorator.CalculationUpgrade;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** 打折收费策略
 * @ClassName: CalculationDiscount
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class CalculationDiscount extends AbstractCalculationStrategy {
    /**
     * 折扣
     */
    private double discount = 1d;

    public CalculationDiscount(){

    }

    public CalculationDiscount(double discount) {
        this.discount = discount;
    }

    /**
     * @param unitPrice
     * @param quantity
     * @return
     */
    @Override
    public BigDecimal calculate(Double unitPrice, Double quantity) {
        BigDecimal result = BigDecimal.valueOf(unitPrice).multiply(BigDecimal.valueOf(quantity)
                .multiply(BigDecimal.valueOf(discount))
                .setScale(2, RoundingMode.HALF_UP));
        return super.calculate(result.doubleValue(),1.0);
    }
}
