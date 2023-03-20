package com.johann.designPattern.dahao.abstractFactory;

/**
 * 使用Oracle操作的DepartmentDao【具体产品】
 */
public class OracleDepartmentDao implements IDepartmentDao{
    @Override
    public void insertDepartment() {
        System.out.println("使用Oracle数据库插入一个Department");
    }

    @Override
    public void getDepartment() {
        System.out.println("使用Oracle数据库获取一个Department");
    }

    @Override
    public void updateDepartment() {
        System.out.println("使用Oracle数据库更新一个Department");
    }
}
