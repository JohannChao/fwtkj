package com.johann.designPattern.designPatterns23.A_factoryMethod;

import java.math.BigDecimal;

/** 指数运算
 * @ClassName: Pow
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class Pow implements IOperation{
    /**
     * @param a
     * @param b
     * @return
     */
    @Override
    public BigDecimal getResult(BigDecimal a, BigDecimal b) {
        return a.pow(b.intValue());
    }
}
