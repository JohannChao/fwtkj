package com.johann.javaReflect.cglibProxy.lazyLoaded;

import net.sf.cglib.proxy.Dispatcher;

/**
 * @ClassName: CreateBeanDispatcher
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class CreateBeanDispatcher implements Dispatcher {
    @Override
    public Object loadObject() throws Exception {
        System.out.println("dispatcher lazyload start ......");
        USerBean bean = new USerBean();
        bean.setId(2);
        bean.setName("jessie");
        bean.setAddress("HB");
        System.out.println("dispatcher lazyload end ......");
        return bean;
    }
}
