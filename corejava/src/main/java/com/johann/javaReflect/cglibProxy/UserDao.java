package com.johann.javaReflect.cglibProxy;

/**
 * @ClassName: UserDao
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class UserDao implements Dao{
    @Override
    public void select(){
        System.out.println("This is a select method！");
    }
    @Override
    public void update(){
        System.out.println("This is an update method！");
    }

}
