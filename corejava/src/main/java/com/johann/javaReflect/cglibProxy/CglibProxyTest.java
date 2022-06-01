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
        userService.learn();
        userService.work();
    }
}
