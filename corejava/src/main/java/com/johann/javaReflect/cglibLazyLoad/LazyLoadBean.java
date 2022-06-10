package com.johann.javaReflect.cglibLazyLoad;

import lombok.Data;
import net.sf.cglib.proxy.Enhancer;

/** cglib中实现懒加载的方式有两种，一种是实现了LazyLoader接口，一种是实现Dispatcher接口
 *  这两个接口均继承自Callback接口，是一种回调类型。
 *  二者的区别是，LazyLoader只在第一次访问延迟加载属性时触发代理类回调方法，而Dispatcher在每次访问延迟加载属性时都会触发代理类回调方法。
 * @ClassName: LazyLoadBean
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
@Data
public class LazyLoadBean {
    private String uuid;
    private USerBean uSerBean_lazyload;
    public LazyLoadBean(){

    }

    public LazyLoadBean(String uuid){
        this.uuid = uuid;
        this.uSerBean_lazyload = newInstanceLazyload();
    }

    /**　
     *只第一次懒加载
     *
     * @return
     */
    private USerBean newInstanceLazyload(){
        /**
         * 使用cglib进行懒加载 对需要延迟加载的对象添加代理，在获取该对象属性时先通过代理类回调方法进行对象初始化。
         * 在不需要加载该对象时，只要不去获取该对象内属性，该对象就不会被初始化了
         * （在CGLib的实现中只要去访问该对象内属性的getter方法，就会自动触发代理类回调）。
         */
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(USerBean.class);
        //延迟对象的代理类
        enhancer.setCallback(new CreateBeanLazyLoader());
        return (USerBean) enhancer.create();
    }
}
