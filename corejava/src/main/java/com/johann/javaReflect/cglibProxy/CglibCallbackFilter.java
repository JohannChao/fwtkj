package com.johann.javaReflect.cglibProxy;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

/** 回调过滤器CallbackFilter 的使用
 * @ClassName: CglibCallbackFilter
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class CglibCallbackFilter implements CallbackFilter {

    /**
     * 过滤方法
     * 返回的值为数字，代表了Callback数组中的索引位置，要到用的Callback
     *
     *         //这个NoOp表示no operator，即什么操作也不做，代理类直接调用被代理的方法不进行拦截。
     *         Callback noOp = NoOp.INSTANCE;
     *         //回调方法使用的是当前拦截类
     *         Callback callback1 = this;
     *         //表示锁定方法返回值，无论被代理类的方法返回什么值，回调方法都返回固定值。
     *         //无论被代理类的方法是什么样，都不会执行被代理类的党费
     *         Callback fixedValue = new ResultFixed();
     *         Callback[] callbacks = new Callback[]{noOp,callback1,fixedValue};
     *
     * 当执行 “learn”方法时，回调类会被设置为 Callback数组中索引为 0 的回调类，即 noOp
     * 当执行 “work”方法时，回调类会被设置为 Callback数组中索引为 1 的回调类，即 当前代理类
     * 当执行 “play”方法时，回调类会被设置为 Callback数组中索引为 2 的回调类，即 FixedValue
     * @param method
     * @return
     */
    @Override
    public int accept(Method method) {
        if("learn".equals(method.getName())){
            return 0;
        }
        if("work".equals(method.getName())){
            return 1;
        }
        if("play".equals(method.getName())){
            return 2;
        }
        return 0;
    }
}
