package com.johann.javaReflect.cglibProxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Date;

/** LogInterceptor 用于对方法调用拦截以及回调
 * @ClassName: LogInterceptor
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class LogInterceptor implements MethodInterceptor {

    /**
     * CGLIB 增强类对象，代理类对象是由 Enhancer 类创建的，
     * Enhancer 是 CGLIB 的字节码增强器，可以很方便的对类进行拓展
     */
    private Enhancer enhancer = new Enhancer();

    /**
     * 实现 MethodInterceptor 接口, 用来处理对代理类上所有方法的请求
     * @param o 被代理的对象
     * @param method 代理的方法
     * @param args 方法的参数
     * @param proxy CGLIB方法代理对象
     * @return cglib生成用来代替Method对象的一个对象，使用MethodProxy比调用JDK自身的Method直接执行方法效率会有提升
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        before();
        Object returnObject =proxy.invokeSuper(o,args);
        after();
        return returnObject;
    }

    /**
     * 使用动态代理创建一个代理对象
     * @param c
     * @return
     */
    public Object newProxyInstance(Class<?> c){
        /**
         * 设置产生的代理对象的父类,增强类型
         */
        enhancer.setSuperclass(c);
        /**
         * 定义代理逻辑对象为当前对象，要求当前对象实现 MethodInterceptor 接口
         */
        enhancer.setCallback(this);
        /**
         * 使用默认无参数的构造函数创建目标对象,这是一个前提,被代理的类要提供无参构造方法
         */
        return enhancer.create();

    }



    private void before(){
        System.out.println(String.format("before : [%s] ",new Date()));
    }
    private void after(){
        System.out.println(String.format("after : [%s] ",new Date()));
    }
}
