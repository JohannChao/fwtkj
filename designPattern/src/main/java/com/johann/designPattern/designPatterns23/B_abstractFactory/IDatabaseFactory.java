package com.johann.designPattern.designPatterns23.B_abstractFactory;

/**
 * 数据连接抽象工厂
 */
public interface IDatabaseFactory {
    IUserDao createUserDao();
    IDepartmentDao createDepartmentDao();
}
