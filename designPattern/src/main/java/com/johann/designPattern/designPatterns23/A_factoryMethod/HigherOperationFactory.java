package com.johann.designPattern.designPatterns23.A_factoryMethod;

/** 高阶运算算法工厂
 * @ClassName: HigherOperationFactory
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class HigherOperationFactory implements IFactory{
    /**
     * @param operator
     * @return
     */
    @Override
    public IOperation getOperation(String operator) {
        IOperation operation = null;
        switch (operator) {
            case "pow":
                operation = new Pow();
                break;
            case "log":
                operation = new Log();
                break;
            default:
                break;
        }
        return operation;
    }
}
