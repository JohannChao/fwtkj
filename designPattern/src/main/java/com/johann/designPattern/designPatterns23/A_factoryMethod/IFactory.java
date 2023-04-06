package com.johann.designPattern.designPatterns23.A_factoryMethod;


/**
 * 工厂接口
 */
public interface IFactory {
    IOperation getOperation(String operator);
}
