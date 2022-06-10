package com.johann.javaReflect.cglibProxy;

import net.sf.cglib.core.DebuggingClassWriter;

/**
 * @ClassName: CglibProxyTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class CglibProxyTest {

    public static void main(String[] args) {
        //生成 CGLIB 的代理对象class文件
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"D:\\MyWorkSpace\\fwtkj\\a_ignoreClasses\\cjlibClass");

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
