package com.johann.designPattern.dahao.decorator.CalculationUpgrade_1;

/**【策略模式中的收费功能】使用装饰模式
 * ### 收费功能同时使用多种策略
 * 策略模式中的【收费功能】，只能单独使用某一种策略，使用装饰模式，可以将多个策略组合起来串行执行。
 * 例如：先对总价进行打折，然后再进行满返。
 *
 * ##### 1，抽象出核心组件接口
 * 当前收费功能缺少组件接口，新增【组件接口ICalculation】
 *
 * ##### 2，找到具体的基础组件
 * 装饰模式有一个重要的优点，把类中的装饰功能从类中搬移去除，这样可以简化原有的类，得到一个最基础的类。
 * 折扣、满减这些功能类，都是在基础收费功能上新增的装饰功能，因此可以将【基础收费类】选做具体的基础组件。
 *
 * ##### 3，装饰抽象类
 * 原来的【策略模式公共算法基类】可以用作装饰抽象类
 *
 * ##### 4，具体装饰类
 * 即，折扣、满减这些在基础功能上增添其他装饰功能的类
 *
 * @ClassName: ZyhDecoratorDemo
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class ZyhDecoratorDemo {
    public static void main(String[] args) {
        CalculationContext contextCopy = new CalculationContext("8z_m100f10");
        System.out.println(contextCopy.getResult(100d,12d));

        contextCopy = new CalculationContext("m100f10_8z");
        System.out.println(contextCopy.getResult(100d,12d));

        contextCopy = new CalculationContext("zc");
        System.out.println(contextCopy.getResult(100d,12d));

        contextCopy = new CalculationContext("7z");
        System.out.println(contextCopy.getResult(100d,12d));

        contextCopy = new CalculationContext("m100f10");
        System.out.println(contextCopy.getResult(100d,12d));
    }
}
