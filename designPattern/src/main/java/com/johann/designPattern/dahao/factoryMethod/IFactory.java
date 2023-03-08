package com.johann.designPattern.dahao.factoryMethod;


/**
 * 工厂接口
 */
public interface IFactory {
    IOperation getOperation(String operator);
}
