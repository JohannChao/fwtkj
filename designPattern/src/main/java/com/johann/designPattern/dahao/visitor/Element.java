package com.johann.designPattern.dahao.visitor;

/** 抽象元素类[稳定的数据结构],定义一个accept操作,它以一个访问者为参数.
 * @author Johann
 * @version 1.0
 * @see
 **/
public abstract class Element {
    public abstract void accept(Visitor visitor);
}
