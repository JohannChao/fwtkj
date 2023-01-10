package com.johann.javaFunctionalProgramming.jdkFunctionDemo;

import java.util.function.Consumer;

/** Consumer 接口包含一个抽象方法 accept 以及默认方法 andThen 。这样 Consumer 接口可以通过 andThen 来进行组合满足我们不同的数据消费需求。
 最常用的 Consumer 接口就是我们的 for 循环，for 循环里面的代码内容就是一个 Consumer 对象的内容。
 * @ClassName: ConsumerTest
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class ConsumerTest {

    public static void acceptSome(Consumer<String> consumer,String obj){
        consumer.accept(obj);
    }
    public static void andThen(Consumer<String> consumer,Consumer<String> after,String obj) {
        consumer.andThen(after).accept(obj);
    }

    public static void main(String[] args) {
        String str = "Hello,world!";

//        acceptSome(new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                System.out.println(s.length());
//            }
//        },str);
        acceptSome(s -> System.out.println(s.length()),str);

//        andThen(new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                System.out.println(s.toLowerCase());
//            }
//        }, new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                System.out.println(s.toUpperCase());
//            }
//        },str);
        andThen(s -> System.out.println(s.toLowerCase()), s -> System.out.println(s.toUpperCase()),str);
    }
}
