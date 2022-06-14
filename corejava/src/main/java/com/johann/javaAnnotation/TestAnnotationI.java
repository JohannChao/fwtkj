package com.johann.javaAnnotation;

@AnnotationTest
@MyAnno("12")
public interface TestAnnotationI {

    @Deprecated
    void travelByCarriage();

    //@TypeUserAnno
    void travelByAutomobile();

    @TypeUserAnno
    String travelBySomething();

    default void defaultMethod(){
        System.out.println("选择合适的交通工具");
    }
}
