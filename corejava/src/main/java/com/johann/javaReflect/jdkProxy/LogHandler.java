package com.johann.javaReflect.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
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

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(target,args);
        after();
        return result;
    }

    private void before(){
        System.out.println(String.format("before : [%s] ",new Date()));
    }
    private void after(){
        System.out.println(String.format("after : [%s] ",new Date()));
    }
}
