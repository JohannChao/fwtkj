package com.johann.javaAnnotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented
public @interface AnnotationTest {
    int id() default 0;
    String name() default "Johann";
}
