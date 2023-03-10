package com.johann.designPattern.dahao.facade;

/** 图形门面
 * @ClassName: ShapeFacade
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class ShapeFacade {

    private final Circle circle;
    private final Rectangle rectangle;
    private final Square square;

    public ShapeFacade(){
        this.circle = new Circle();
        this.rectangle = new Rectangle();
        this.square = new Square();
    }

    public void drawCircle(){
        circle.draw();
    }
    public void drawRectangle(){
        rectangle.draw();
    }
    public void drawSquare(){
        square.draw();
    }

}
