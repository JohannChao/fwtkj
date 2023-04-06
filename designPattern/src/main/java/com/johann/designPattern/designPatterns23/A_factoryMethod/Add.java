package com.johann.designPattern.designPatterns23.A_factoryMethod;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** 加法
 * @ClassName: Add
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class Add implements IOperation {
    @Override
    public BigDecimal getResult(BigDecimal a, BigDecimal b) {
        return a.add(b).setScale(2, RoundingMode.HALF_UP);
    }
}
