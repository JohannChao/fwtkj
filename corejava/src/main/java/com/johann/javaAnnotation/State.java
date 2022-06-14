package com.johann.javaAnnotation;

import java.lang.annotation.*;

@Repeatable(StateArray.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface State {
    String stateValue() default "";
}
