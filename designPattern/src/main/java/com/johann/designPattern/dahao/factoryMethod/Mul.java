package com.johann.designPattern.dahao.factoryMethod;

import java.math.BigDecimal;

/** 乘法
 * @ClassName: Mul
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class Mul implements IOperation {
    @Override
    public BigDecimal getResult(BigDecimal a, BigDecimal b) {
        return a.multiply(b).setScale(2,BigDecimal.ROUND_HALF_UP);
    }
}
