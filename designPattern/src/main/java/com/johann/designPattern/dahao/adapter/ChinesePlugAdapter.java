package com.johann.designPattern.dahao.adapter;

/** 适配器类（Adapter）
 * @Description: 它是一个转换器，通过继承或引用适配者的对象，把适配者接口转换成目标接口，让客户按目标接口的格式访问适配者。
 * @Author: Johann
 */
public class ChinesePlugAdapter implements ICnPlug{

    private AmericanPlug adaptee;

    public ChinesePlugAdapter(String name){
        adaptee = new AmericanPlug(name);
    }

    @Override
    public void usePlug() {
        adaptee.usePlug();
    }
}
