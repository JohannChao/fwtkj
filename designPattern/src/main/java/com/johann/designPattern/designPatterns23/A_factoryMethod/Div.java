package com.johann.designPattern.designPatterns23.A_factoryMethod;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** 除法
 * @ClassName: Div
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class Div implements IOperation {
    /**
     * @param a
     * @param b
     * @return
     */
    @Override
    public BigDecimal getResult(BigDecimal a, BigDecimal b) {
        if (b.compareTo(BigDecimal.ZERO)==0){
            throw new ArithmeticException("除数为零！");
        }
        return a.divide(b,2, RoundingMode.HALF_UP);
    }
}
