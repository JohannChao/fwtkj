package com.johann.designPattern.designPatterns23.B_abstractFactory;

/**
 * 使用Mysql操作的DepartmentDao【具体产品】
 */
public class MysqlDepartmentDao implements IDepartmentDao{
    @Override
    public void insertDepartment() {
        System.out.println("使用MySQL数据库插入一个Department");
    }

    @Override
    public void getDepartment() {
        System.out.println("使用MySQL数据库获取一个Department");
    }

    @Override
    public void updateDepartment() {
        System.out.println("使用MySQL数据库更新一个Department");
    }
}
