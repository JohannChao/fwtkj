package com.johann.designPattern.designPatterns23.B_abstractFactory;

/**
 * 使用Mysql操作的UserDao【具体产品】
 */
public class MysqlUserDao implements IUserDao{
    @Override
    public void insertUser() {
        System.out.println("使用MySQL数据库插入一个User");
    }

    @Override
    public void getUser() {
        System.out.println("使用MySQL数据库获取一个User");
    }

    @Override
    public void updateUser() {
        System.out.println("使用MySQL数据库更新一个User");
    }
}
