package com.johann.javaReflect.cglibLazyLoad;

import net.sf.cglib.core.DebuggingClassWriter;

/** LazyLoader只在第一次访问延迟加载属性时触发代理类回调方法，而Dispatcher在每次访问延迟加载属性时都会触发代理类回调方法。
 *
 *  每次触发代理类的回调方法，都会生成新的委托对象。所以，多次访问延迟加载属性时，LazyLoader只会生成一个委托对象，而Dispatcher会生成多个不同的委托对象。
 *  但是，有一点是相同的，即这两种接口，都只会生成一个代理对象 USerBean$$EnhancerByCGLIB$$哈希值.class ，这个代理类是委托类的子类
 *
 * @ClassName: LazyLoadTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class LazyLoadTest {
    public static void main(String[] args) {
        //生成 CGLIB 的代理对象class文件
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"D:\\MyWorkSpace\\fwtkj\\a_ignoreClasses\\cjlibLazyloadClass");

        LazyLoadBean lz = new LazyLoadBean("12121212");
        System.out.println("初始化 LazyLoadBean 不获取属性");
        USerBean u1 = lz.getUSerBean_lazyload();
        System.out.println(u1 == null);
        System.out.println(u1.getClass().hashCode());
        System.out.println(u1.getClass().getName()+" $$ "+u1.hashCode());
        System.out.println(u1);
        System.out.println("\n--------\n");
        System.out.println("获取了属性："+u1.getAddress());
        System.out.println("\n--------\n");
        USerBean u11 = lz.getUSerBean_lazyload();
        System.out.println(u11.getClass().hashCode());
        System.out.println(u11.getClass().getName()+" $$ "+u11.hashCode());
        System.out.println(u11);
        System.out.println(u11.getId()+" "+u11.getName()+" "+u11.getAddress());
        System.out.println("\n--------\n");
        USerBean u12 = lz.getUSerBean_lazyload();
        u12.setAddress("FFF");
        System.out.println(u12.getClass().hashCode());
        System.out.println(u12.getClass().getName()+" $$ "+u12.hashCode());
        System.out.println(u12);
        System.out.println(u12.getId()+" "+u12.getName()+" "+u12.getAddress());

        System.out.println("\n##################\n");

        LazyloadDiapatcherBean lzd = new LazyloadDiapatcherBean("33333333");
        System.out.println("初始化 LazyloadDiapatcherBean 不获取属性");
        USerBean u2 = lzd.getUSerBean_dispatcher();
        System.out.println(u2==null);
        System.out.println(u2.getClass().getName()+" $$ "+u2.hashCode());
        System.out.println(u2.hashCode());
        System.out.println(u2);
        System.out.println("\n--------\n");
        System.out.println("获取了属性："+u2.getAddress());
        System.out.println("\n--------\n");
        USerBean u22 = lzd.getUSerBean_dispatcher();
        System.out.println(u22.getClass().hashCode());
        System.out.println(u22.getClass().getName()+" $$ "+u22.hashCode());
        System.out.println("\n--------");
        USerBean u23 = lzd.getUSerBean_dispatcher();
        System.out.println(u23.getClass().hashCode());
        System.out.println(u23.getClass().getName()+" $$ "+u23.hashCode());
        System.gc();
    }

}
