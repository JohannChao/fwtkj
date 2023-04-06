package com.johann.designPattern.designPatterns23.A_simpleFactory;

import java.math.BigDecimal;

/**
 * @ClassName: ZyhSimpleFactoryDemo
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class ZyhSimpleFactoryDemo {

    public static void main(String[] args) {
        AbstractOperation add = OperationFactory.getOperation("+");
        BigDecimal result = add.getResult(new BigDecimal("10"),new BigDecimal("20"));
        System.out.println("运算结果："+result);
    }
}
