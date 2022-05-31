package com.johann.javaReflect.cglibProxy;

import net.sf.cglib.proxy.Enhancer;

/**
 * @ClassName: CglibProxyTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class CglibProxyTest {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        UserDao userDao = new UserDao();
        enhancer.setSuperclass(Dao.class);
        enhancer.setCallback(userDao);

        Dao dao = (Dao)enhancer.create();
        dao.select();
        dao.update();
    }
}
