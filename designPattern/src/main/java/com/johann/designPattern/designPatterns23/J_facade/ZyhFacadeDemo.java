package com.johann.designPattern.designPatterns23.J_facade;

/**
 * @ClassName: ZyhFacadeDemo
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class ZyhFacadeDemo {
    public static void main(String[] args) {
        // 只需要一个门面对象，即可画出各种图形，无需关注各种图形的具体实现，符合迪米特法则
        ShapeFacade shapeFacade = new ShapeFacade();
        shapeFacade.drawCircle();
        shapeFacade.drawRectangle();
        shapeFacade.drawSquare();

    }
}
