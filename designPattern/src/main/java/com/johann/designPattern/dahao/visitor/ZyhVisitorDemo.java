package com.johann.designPattern.dahao.visitor;

/**
 * 访问者模式是一种行为型设计模式，它允许你在不改变对象结构的前提下，定义对对象元素的新操作。
 * 访问者模式包括两个主要的组成部分：元素（Element）和访问者（Visitor）。元素包含一个接受访问者的方法，而访问者包含访问各种元素的方法。
 * 访问者模式适用于数据结构相对稳定的系统，它把数据结构和作用于结构上的操作之间的耦合解脱开，使得操作集合可以相对自由地演化。
 * 访问者模式的优点就是增加新的操作很容易，因为增加新的操作就意味着增加一个新的访问者。访问者模式将有关的行为集中到一个访问者对象中。
 *
 * 优点：
 * 可以在不修改对象结构的情况下向对象添加新的行为。
 * 可以将相关行为组织到一个访问者对象中，使得代码更加清晰和易于维护。
 * 访问者模式使得数据结构和数据操作分离，符合单一职责原则。
 *
 * 缺点：
 * 增加新的元素类需要修改访问者接口，破坏了开闭原则。
 * 访问者模式增加了代码的复杂度，增加了系统的维护难度。
 *
 * 符合的设计原则：
 * 开闭原则：访问者模式允许添加新的访问者，而不需要修改已有的代码。
 * 单一职责原则：访问者模式将数据结构和数据操作分离开来，符合单一职责原则。
 *
 * 违反的设计原则：
 * 迪米特法则：访问者模式在访问者中需要访问元素的内部状态，破坏了元素的封装性。
 * 依赖倒转原则：Visitor 依赖 ConcreteElement，而不是 Element
 * @author Johann
 * @version 1.0
 * @see
 **/
public class ZyhVisitorDemo {
    public static void main(String[] args) {
        ObjectStructure objectStructure = new ObjectStructure();
        objectStructure.attach(new ElementOfLosAngeles());
        objectStructure.attach(new ElementOfNewYork());

        Visitor visitorOfPurchase = new VisitorOfPurchase();
        Visitor visitorOfSell = new VisitorOfSell();
        VisitorOfAfterSell visitorOfAfterSell = new VisitorOfAfterSell();

        objectStructure.accept(visitorOfPurchase);
        objectStructure.accept(visitorOfSell);
        objectStructure.accept(visitorOfAfterSell);
    }
}
