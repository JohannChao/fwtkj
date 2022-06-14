package com.johann.javaAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 时区，时间格式标签
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DateFormatter {
    String pattern() default "yyyy-MM-dd";
    String timeZone() default "GMT+8";
}
