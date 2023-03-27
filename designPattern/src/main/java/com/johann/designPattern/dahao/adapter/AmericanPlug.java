package com.johann.designPattern.dahao.adapter;

/** 适配者类（Adaptee）
 * @Description: 它是被访问及适配的现存组件库中的组件接口
 * @Author: Johann
 */
public class AmericanPlug{

    private String name;

    public AmericanPlug() {
    }

    public AmericanPlug(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void usePlug(){
        System.out.println(this.getName()+": 使用美标插头");
    }
}
