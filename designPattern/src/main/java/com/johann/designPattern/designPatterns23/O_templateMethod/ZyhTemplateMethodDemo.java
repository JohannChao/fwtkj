package com.johann.designPattern.designPatterns23.O_templateMethod;

/** 模板模式Demo
 * @ClassName: ZyhTemplateMethodDemo
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class ZyhTemplateMethodDemo {
    public static void main(String[] args) {
        // 封装不变部分，扩展可变部分。
        // 行为由父类控制，子类实现。
        AbstractAnimal pig = new PigWhithTempla();
        pig.show();
        AbstractAnimal cow = new CowWhithTempla();
        cow.show();
    }
}
