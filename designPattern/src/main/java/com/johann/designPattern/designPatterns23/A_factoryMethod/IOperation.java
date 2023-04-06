package com.johann.designPattern.designPatterns23.A_factoryMethod;

import java.math.BigDecimal;

/**
 * 产品接口
 */
public interface IOperation {
    BigDecimal getResult(BigDecimal a, BigDecimal b);
}
