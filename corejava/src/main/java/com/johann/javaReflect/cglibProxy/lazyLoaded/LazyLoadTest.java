package com.johann.javaReflect.cglibProxy.lazyLoaded;

/**
 * @ClassName: LazyLoadTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class LazyLoadTest {
    public static void main(String[] args) {
        LazyLoadBean lz = new LazyLoadBean("12121212");
        System.out.println(lz.getUSerBean_lazyload());
        System.out.println(lz.getUSerBean_lazyload());
        System.out.println(lz.getUSerBean_dispatcher());
        System.out.println(lz.getUSerBean_dispatcher());
    }

}
