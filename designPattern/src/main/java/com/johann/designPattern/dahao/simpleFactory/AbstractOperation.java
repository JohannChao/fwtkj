package com.johann.designPattern.dahao.simpleFactory;

import java.math.BigDecimal;

/** 简单工厂模式的抽象类
 * @ClassName: AbstractOperation
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public abstract class AbstractOperation {

    public abstract BigDecimal getResult(BigDecimal a, BigDecimal b);
}
