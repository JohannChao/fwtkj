package com.johann.javaReflect.cglibLazyLoad;

import net.sf.cglib.proxy.Dispatcher;

/**
 * @ClassName: CreateBeanDispatcher
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class CreateBeanDispatcher implements Dispatcher {
    /**
     * 使用cglib进行懒加载 对需要延迟加载的对象添加代理，在获取该对象属性时先通过代理类回调方法进行对象初始化。
     * 在不需要加载该对象时，只要不去获取该对象内属性，该对象就不会被初始化了
     * （在CGLib的实现中只要去访问该对象内属性的getter方法，就会自动触发代理类回调）。
     */
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
