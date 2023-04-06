package com.johann.designPattern.designPatterns23.A_simpleFactory;

import java.math.BigDecimal;

/** 简单工程模式实现类——乘法
 * @ClassName: Mul
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class Mul extends AbstractOperation{
    @Override
    public BigDecimal getResult(BigDecimal a, BigDecimal b) {
        return a.multiply(b).setScale(2,BigDecimal.ROUND_HALF_UP);
    }
}
