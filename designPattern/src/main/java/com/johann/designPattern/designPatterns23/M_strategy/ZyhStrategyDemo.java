package com.johann.designPattern.designPatterns23.M_strategy;

/**
 * @ClassName: ZyhStrategyDemo
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class ZyhStrategyDemo {

    public CalculationContext selectStrategy(String type){
        CalculationContext context = null;
        switch (type) {
            case "zc":
                context = new CalculationContext(new CalculationNormal());
                break;
            case "7z":
                context = new CalculationContext(new CalculationDiscount(0.7d));
                break;
            case "9z":
                context = new CalculationContext(new CalculationDiscount(0.9d));
                break;
            case "m100f10":
                context = new CalculationContext(new CalculationRebate(100d,10d));
                break;
            default:
                break;
        }
        return context;
    }

    public static void main(String[] args) {
        // 1，【基本策略模式】客户端承担——选择具体策略——职责
        ZyhStrategyDemo demo = new ZyhStrategyDemo();
        CalculationContext context = demo.selectStrategy("m100f10");
        System.out.println(context.getResult(15d,14d));

        // 2，【策略模式+简单工厂】Context承担——选择具体策略——职责
        CalculationContext contextCopy = new CalculationContext("m100f10");
        System.out.println(contextCopy.getResult(15d,14d));
    }

}
