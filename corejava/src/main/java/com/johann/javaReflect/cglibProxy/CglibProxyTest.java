package com.johann.javaReflect.cglibProxy;

/**
 * @ClassName: CglibProxyTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class CglibProxyTest {

    public static void main(String[] args) {

        LogInterceptor logInterceptor = new LogInterceptor();
        CglibUserService userService = (CglibUserService)logInterceptor.newProxyInstance(CglibUserService.class);
        System.out.println("--------------------");
        System.out.println(userService.learn());
        System.out.println("--------------------");
        System.out.println(userService.work());
        System.out.println("--------------------");
        System.out.println(userService.play());
        System.out.println("--------------------");
    }
}
