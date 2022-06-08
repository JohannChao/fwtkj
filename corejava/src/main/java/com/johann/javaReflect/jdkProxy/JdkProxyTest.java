package com.johann.javaReflect.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/** JDK动态代理实现
 * @ClassName: JdkProxyTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class JdkProxyTest {

    public static void main(String[] args) {

        //JDK动态代理生成 代理类Class文件
        //方法一：设置变量，可以保存动态代理类，默认名称为 $Proxy0 格式命名。此时会在 com.sun.proxy包下生成一个 $Proxy0.class文件
        //System.getProperties().setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        //1，创建被代理的对象，即真实角色
        UserServiceImpl userService = new UserServiceImpl();
        //2，获取真实角色的类加载器
        ClassLoader classLoader = userService.getClass().getClassLoader();
        //3，获取真实角色实现的所有的接口
        Class[] interfaces = userService.getClass().getInterfaces();
        //4，创建一个用户自定义的调用处理器，这个调用处理器实现了 InvocationHandler 接口，并将作为参数被传入最终生成的代理类，用于处理代理类上的方法调用
        InvocationHandler ivHandler = new LogInvocationHandler(userService);

            // 返回一个具有代理类指定调用处理器的代理实例，代理类由指定的类装入器定义并实现指定的接口
        //5，根据上面的信息，创建代理对象
        //在这个过程中：
        //    1.JDK会通过根据传入的参数信息动态地在内存中创建和.class 文件等同的字节码
        //    2.然后根据相应的字节码转换成对应的class
        //    3.然后调用newInstance()创建代理实例
        UserService proxy =  (UserService) Proxy.newProxyInstance(classLoader,interfaces,ivHandler);

        //用于获取指定代理对象所关联的调用处理器
        //InvocationHandler iv = Proxy.getInvocationHandler(proxy);

        //返回一个代理类，代理类指定的类装入器定义并实现指定的接口
        //Class clazz = Proxy.getProxyClass(classLoader,interfaces);

        //返回 clazz 是否为一个代理类
        //boolean flag = Proxy.isProxyClass(proxy.getClass());



        //LogHandler logHandler = new LogHandler(userService);
        //UserService proxy =  (UserService)logHandler.newProxyInstance();

        proxy.learn();
        proxy.work();

        //JDK动态代理生成 代理类Class文件
        //方法二：根据类信息和提供的代理类名称，生成字节码，然后通过流的方式写到磁盘文件中
        // 保存JDK动态代理生成的代理类，类名保存为 UserServiceImplProxy
        ProxyUtils.generateProxyClass(userService.getClass(),"UserServiceImplProxy");
    }
}
