package com.johann.designPattern.dahao.visitor;

/** 具体元素类[稳定的数据结构],实现accept操作,通常是一个visitor访问该元素本身的一个操作
 * @author Johann
 * @version 1.0
 * @see
 **/
public class ElementOfNewYork extends Element {
    /**
     * 双分派
     * 首先在客户端程序中,将具体访问者作为参数传递到ElementOfLosAngeles中(第一次分派)
     * 然后,ElementOfLosAngeles类调用作为参数的"具体访问者"的visit()方法,同时将自己(this)作为参数传递进去。(第二次分派)
     *
     * 双分派意味着得到执行的操作决定于请求的种类和两个接收者的类型。
     * '接受'方法就是一个双分派的操作，它得到执行的操作不仅决定于'访问者'类的具体状态，还决定于它访问的'元素'的类别。"
     * @params [visitor]
     * @return void
     * @author Johann
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void newYork() {
        System.out.println("纽约分公司");
    }
}
