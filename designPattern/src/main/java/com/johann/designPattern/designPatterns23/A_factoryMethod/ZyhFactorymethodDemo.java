package com.johann.designPattern.designPatterns23.A_factoryMethod;


import java.math.BigDecimal;

/**
 * @ClassName: ZyhFactorymethodDemo
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class ZyhFactorymethodDemo {

    public static void main(String[] args) {
        IOperation pow = OperationFactory.getOperation("pow");
        BigDecimal result = pow.getResult(new BigDecimal("2"),new BigDecimal("8"));
        System.out.println("运算结果："+result);
    }
}
