package com.johann.designPattern.dahao.abstractFactory;

/**
 * 使用Oracle操作的UserDao【具体产品】
 */
public class OracleUserDao implements IUserDao{
    @Override
    public void insertUser() {
        System.out.println("使用Oracle数据库插入一个User");
    }

    @Override
    public void getUser() {
        System.out.println("使用Oracle数据库获取一个User");
    }

    @Override
    public void updateUser() {
        System.out.println("使用Oracle数据库更新一个User");
    }
}
