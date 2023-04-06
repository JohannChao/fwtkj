package com.johann.designPattern.designPatterns23.A_simpleFactory;

/** 简单工厂模式——工厂类
 * @ClassName: OperationFactory
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class OperationFactory {

    /**
     * 根据运算符，创建合适的子类运算对象，利用多态，返回父类引用。程序运行期间，调用运算方法时，实际调用指定的子类对象中重写的方法。
     *
     * 多态：父类引用指向子类对象，从而产生多种形态
     * 一个引用变量到底会指向哪个类的实例对象，该引用变量发出的方法调用到底是哪个类中实现的方法，必须在由程序运行期间才能决定。
     *
     * @param operator
     * @return
     */
    public static AbstractOperation getOperation(String operator){
        AbstractOperation operation = null;
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
