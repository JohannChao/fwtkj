package com.johann.javaAnnotation;

/** 需要 @JianCha 注解的类
 * @ClassName: Z_JianchaTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class Z_JianchaClass {

    public void addition(){
        System.out.println(12+1);
    }

    @JianCha
    public void subtraction(){
        System.out.println(12-1);
    }

    @JianCha
    public void multiplication(){
        System.out.println(12*1);
    }

    @JianCha
    public void division(){
        System.out.println(12/0);
    }

}
