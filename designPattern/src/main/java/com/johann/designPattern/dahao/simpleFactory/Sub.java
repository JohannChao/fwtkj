package com.johann.designPattern.dahao.simpleFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** 简单工程模式实现类——减法
 * @ClassName: Sub
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class Sub extends AbstractOperation{
    @Override
    public BigDecimal getResult(BigDecimal a, BigDecimal b) {
        return a.subtract(b).setScale(2, RoundingMode.HALF_UP);
    }
}
