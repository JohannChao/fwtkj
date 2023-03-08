package com.johann.designPattern.dahao.factoryMethod;

import java.math.BigDecimal;

/** 对数运算
 * @ClassName: Log
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class Log implements IOperation{
    /**
     * @param a
     * @param b
     * @return
     */
    @Override
    public BigDecimal getResult(BigDecimal a, BigDecimal b) {
        return BigDecimal.valueOf(Math.log(a.doubleValue())/Math.log(b.doubleValue()));
    }
}
