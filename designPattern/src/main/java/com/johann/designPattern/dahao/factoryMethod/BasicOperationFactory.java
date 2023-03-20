package com.johann.designPattern.dahao.factoryMethod;

/** 基本运算算法工厂
 * @ClassName: BasicOperationFactory
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class BasicOperationFactory implements IFactory {

    /**
     * @param operator
     * @return
     */
    @Override
    public IOperation getOperation(String operator) {
        IOperation operation = null;
        switch (operator) {
            case "+":
                operation = new Add();
                break;
            case "-":
                operation = new Sub();
                break;
            case "*":
                operation = new Mul();
                break;
            case "/":
                operation = new Div();
                break;
            default:
                break;
        }
        return operation;
    }
}
