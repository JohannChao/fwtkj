package com.johann.javaFunctionalProgramming.jdkFunctionDemo;

import java.util.function.Supplier;

/** 用来获取一个泛型参数指定类型的对象数据（生产一个数据），我们可以把它理解为一个工厂类，用来创建对象。
 * @ClassName: SupplierTest
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class SupplierTest {

    public static String getSome(Supplier<String> supplier){
        return supplier.get();
    }

    public static void main(String[] args) {
        String str1 = "Hello,world!";
        String str2 = "I'm Johann!";

//        getSome(new Supplier<String>() {
//            @Override
//            public String get() {
//                return str1+str2;
//            }
//        });
        System.out.println(getSome(() -> str1+str2));
    }
}
