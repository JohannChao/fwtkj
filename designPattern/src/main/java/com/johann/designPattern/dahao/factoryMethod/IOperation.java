package com.johann.designPattern.dahao.factoryMethod;

import java.math.BigDecimal;

/**
 * 产品接口
 */
public interface IOperation {
    BigDecimal getResult(BigDecimal a, BigDecimal b);
}
