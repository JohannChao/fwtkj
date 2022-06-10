package com.johann.javaReflect.cglibProxy;

import net.sf.cglib.proxy.*;

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

        //额外设置 CallbackFilter 来指定每个方法使用的是哪个 Callback
        CglibCallbackFilter cglibCallbackFilter = new CglibCallbackFilter();

        //这个NoOp表示no operator，即什么操作也不做，代理类直接调用被代理的方法不进行拦截。
        Callback noOp = NoOp.INSTANCE;
        //回调方法使用的是当前拦截类
        Callback callback1 = this;
        //表示锁定方法返回值，无论被代理类的方法返回什么值，回调方法都返回固定值。
        //无论被代理类的方法是什么样，都不会被执行。
        Callback fixedValue = new ResultFixed();
        Callback[] callbacks = new Callback[]{noOp,callback1,fixedValue};

        enhancer.setCallbacks(callbacks);
        enhancer.setCallbackFilter(cglibCallbackFilter);

        /**
         * 定义代理逻辑对象为当前对象，要求当前对象实现 MethodInterceptor 接口
         */
        //enhancer.setCallback(this);


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
