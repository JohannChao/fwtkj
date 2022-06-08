package com.johann.javaReflect.cglibProxy.lazyLoaded;

import net.sf.cglib.proxy.LazyLoader;

/**
 * @ClassName: CreateBeanLazyLoader
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class CreateBeanLazyLoader implements LazyLoader {

    @Override
    public Object loadObject() throws Exception {
        System.out.println("lazyloader start ......");
        USerBean bean = new USerBean();
        bean.setId(1);
        bean.setName("johann");
        bean.setAddress("BJ");
        System.out.println("lazyloader end ......");
        //Object T = new Object();
        return bean;
    }
}
