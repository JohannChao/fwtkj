package com.johann.javaAnnotation;

/**
 * @ClassName: TestAnnotation
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class TestAnnotation implements TestAnnotationI{

    @Override
    @SuppressWarnings("deprecation")
    public void travelByCarriage() {
        System.out.println("马车旅行");
    }

    @Override
    public void travelByAutomobile() {
        System.out.println("汽车旅行");
    }

    @Override
    public String travelBySomething() {
        return null;
    }
}
