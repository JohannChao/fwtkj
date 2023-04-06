package com.johann.designPattern.designPatterns23.V_visitor;

/**
 * 具体访问者类,实现Visitor接口中访问各种元素的方法,并且在访问元素时,实现对应的功能.
 * @author Johann
 * @version 1.0
 * @see
 **/
public class VisitorOfSell extends Visitor {
    @Override
    public void visit(ElementOfLosAngeles elementOfLosAngeles) {
        elementOfLosAngeles.losAngeles();
        System.out.println("---销售商品");
    }

    @Override
    public void visit(ElementOfNewYork elementOfNewYork) {
        elementOfNewYork.newYork();
        System.out.println("---销售商品");
    }
}
