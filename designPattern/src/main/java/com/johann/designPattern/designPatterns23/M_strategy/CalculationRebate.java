package com.johann.designPattern.designPatterns23.M_strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** 满减收费策略
 * @ClassName: CalculationRebate
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class CalculationRebate extends AbstractCalculationStrategy {
    /**
     * 满多少
     */
    private double costCondition = 0d;
    /**
     * 减多少
     */
    private double costRebate = 0d;

    public CalculationRebate(double costCondition, double costRebate) {
        this.costCondition = costCondition;
        this.costRebate = costRebate;
    }

    public CalculationRebate(){

    }

    /**
     * @param unitPrice
     * @param quantity
     * @return
     */
    @Override
    public BigDecimal calculate(Double unitPrice, Double quantity) {
        BigDecimal total = BigDecimal.valueOf(unitPrice).multiply(BigDecimal.valueOf(quantity)
                .setScale(2, RoundingMode.HALF_UP));
        BigDecimal cc = BigDecimal.valueOf(costCondition);
        if (total.compareTo(cc) >= 0){
            BigDecimal totolRebate = total.divide(cc,0, RoundingMode.FLOOR).multiply(BigDecimal.valueOf(costRebate));
            total = total.subtract(totolRebate).setScale(2,RoundingMode.HALF_UP);
        }
        return total;
    }
}
