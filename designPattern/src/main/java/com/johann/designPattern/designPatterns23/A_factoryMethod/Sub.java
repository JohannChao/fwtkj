package com.johann.designPattern.designPatterns23.A_factoryMethod;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** 减法
 * @ClassName: Sub
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class Sub implements IOperation {
    @Override
    public BigDecimal getResult(BigDecimal a, BigDecimal b) {
        return a.subtract(b).setScale(2, RoundingMode.HALF_UP);
    }
}
