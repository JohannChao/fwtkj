package com.johann.designPattern.dahao.abstractFactory;

/**
 * 数据连接抽象工厂
 */
public interface IDatabaseFactory {
    IUserDao createUserDao();
    IDepartmentDao createDepartmentDao();
}
