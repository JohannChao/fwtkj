package com.johann.javaReflect.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;

/** JDK动态代理，用户自定义的调用处理器
 * @ClassName: LogHandler
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class LogHandler implements InvocationHandler {

    private Object target;

    public LogHandler(Object target){
        this.target = target;
    }

    /**
     *
     * @param proxy 代理对象
     * @param method 代理方法
     * @param args 方法的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(target,args);
        after();
        return result;
    }

    /**
     * 返回一个具有代理类指定调用处理器的代理实例，代理类由指定的类装入器定义并实现指定的接口
     * 在这个过程中：
     *         1.JDK会通过根据传入的参数信息动态地在内存中创建和.class 文件等同的字节码
     *         2.然后根据相应的字节码转换成对应的class
     *         3.然后调用newInstance()创建代理实例
     * @return
     */
    public Object newProxyInstance(){
        return Proxy.newProxyInstance(
                //代理对象的类加载器
                target.getClass().getClassLoader(),
                //代理对象下需要实现的接口
                target.getClass().getInterfaces(),
                //方法调用的实际处理者，代理对象的方法调用都会转发到这里
                this);
    }



    private void before(){
        System.out.println(String.format("before : [%s] ",new Date()));
    }
    private void after(){
        System.out.println(String.format("after : [%s] ",new Date()));
    }
}
