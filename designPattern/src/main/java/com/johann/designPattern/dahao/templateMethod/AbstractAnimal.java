package com.johann.designPattern.dahao.templateMethod;

/** 模板基类，用于封装公共不变部分，控制行为（行为的具体实现由子类完成）
 * @ClassName: AbstractAnimal
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public abstract class AbstractAnimal {

    protected String name;

    /**
     * 防止恶意操作，基类的模板方法加 final 关键词。
     */
    public final void show(){
        System.out.println("当前动物是："+this.name);
        System.out.println("吃什么？");
        System.out.println("吃【"+eat()+"】");
        System.out.println("产什么？");
        System.out.println("产【"+product()+"】");
    }

    protected abstract String eat();

    protected abstract String product();

}
