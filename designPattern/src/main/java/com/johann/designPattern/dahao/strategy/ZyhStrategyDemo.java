package com.johann.designPattern.dahao.strategy;

import java.math.BigDecimal;

/**
 * @ClassName: ZyhStrategyDemo
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class ZyhStrategyDemo {

    private CalculationContext context;

    public void selectStrategy(String type){
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
    }

    public static void main(String[] args) {
        // 1，【基本策略模式】客户端承担——选择具体策略——职责
        ZyhStrategyDemo demo = new ZyhStrategyDemo();
        demo.selectStrategy("m100f10");
        BigDecimal result = demo.context.getResult(15d,14d);
        System.out.println(result);

        // 2，【策略模式+简单工厂】Context承担——选择具体策略——职责
        CalculationContext contextCopy = new CalculationContext("m100f10");
        System.out.println(contextCopy.getResult(15d,14d));
    }

}