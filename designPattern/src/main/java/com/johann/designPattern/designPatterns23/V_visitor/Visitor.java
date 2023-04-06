package com.johann.designPattern.designPatterns23.V_visitor;

/**
 * 访问者抽象类,为每一个元素类ConcreteElement声明一个访问操作,访问者可以通过该操作访问它所关联的元素.
 * @author Johann
 * @version 1.0
 * @see
 **/
public abstract class Visitor {
    public abstract void visit(ElementOfLosAngeles elementOfLosAngeles);

    public abstract void visit(ElementOfNewYork elementOfNewYork);

}
