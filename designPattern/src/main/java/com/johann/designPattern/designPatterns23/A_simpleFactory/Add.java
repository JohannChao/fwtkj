package com.johann.designPattern.designPatterns23.A_simpleFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** 简单工程模式实现类——加法
 * @ClassName: Add
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class Add extends AbstractOperation{
    @Override
    public BigDecimal getResult(BigDecimal a, BigDecimal b) {
        return a.add(b).setScale(2, RoundingMode.HALF_UP);
    }
}
