package com.johann.javaFunctionalProgramming.jdkFunctionDemo;

import java.util.function.Function;

/** Function 接口有一个抽象方法 apply 和默认方法 andThen，通过 andThen 可以把多个 Function 接口进行组合，是适用范围最广的函数接口。
 * @ClassName: FunctionTest
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class FunctionTest {

    public static Integer doApply(Function<String,Integer> function,String str){
        return function.apply(str);
    }

    public static String andThen(Function<String,Integer> function,Function<Integer,String> after,String str){
        return function.andThen(after).apply(str);
    }

    public static void main(String[] args) {
        String str = "10";

//        doApply(new Function<String, Integer>() {
//            @Override
//            public Integer apply(String s) {
//                return Integer.parseInt(s);
//            }
//        },str);
        System.out.println(doApply(Integer::parseInt,str));

//        andThen(new Function<String, Integer>() {
//            @Override
//            public Integer apply(String s) {
//                return Integer.parseInt(s)+20;
//            }
//        }, new Function<Integer, String>() {
//            @Override
//            public String apply(Integer integer) {
//                return Integer.toString(integer*10);
//            }
//        },str);
        System.out.println(andThen(s -> Integer.parseInt(s)+20, integer -> Integer.toString(integer*10),str));
    }
}
