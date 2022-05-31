package com.johann.javaReflect.jdkProxy;

/**
 * @ClassName: UserServiceImpl
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class UserServiceImpl implements UserService{
    @Override
    public void learn() {
        System.out.println("学习！！！");
    }

    @Override
    public void work() {
        System.out.println("工作！！！");
    }
}
