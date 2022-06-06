package com.johann.javaReflect.cglibProxy;

import net.sf.cglib.proxy.FixedValue;

/**表示锁定方法返回值，无论被代理类的方法返回什么值，回调方法都返回固定值。
 * @ClassName: ResultFixed
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class ResultFixed implements FixedValue {

    /**
     * 该类实现FixedValue接口，同时锁定回调值为 “001”
     * 代理类的方法返回值是String类型，所以此处使用的返回值也是String类型
     * @return
     * @throws Exception
     */
    @Override
    public Object loadObject() throws Exception {
        System.out.println("锁定返回结果！");
        Object o = "001";
        return o;
    }
}
