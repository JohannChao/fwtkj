package com.johann.javaReflect.cglibProxy.lazyLoaded;

import lombok.Data;
import net.sf.cglib.proxy.Enhancer;

/**
 * @ClassName: LazyLoadBean
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
@Data
public class LazyLoadBean {
    private String uuid;
    private USerBean uSerBean_lazyload;
    private USerBean uSerBean_dispatcher;
    public LazyLoadBean(){

    }

    public LazyLoadBean(String uuid){
        this.uuid = uuid;
        this.uSerBean_lazyload = newInstanceLazyload();
        this.uSerBean_dispatcher = newInstanceDispatcher();
    }


    private USerBean newInstanceLazyload(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(USerBean.class);
        enhancer.setCallback(new CreateBeanLazyLoader());
        return (USerBean) enhancer.create();
    }

    private USerBean newInstanceDispatcher(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(USerBean.class);
        enhancer.setCallback(new CreateBeanDispatcher());
        return (USerBean) enhancer.create();
    }
}
