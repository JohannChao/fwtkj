package com.johann.designPattern.dahao.abstractFactory;

/**
 * MySQL数据库连接工厂【具体工厂】
 */
public class MysqlFactory implements IDatabaseFactory{
    @Override
    public IUserDao createUserDao() {
        return new MysqlUserDao();
    }

    @Override
    public IDepartmentDao createDepartmentDao() {
        return new MysqlDepartmentDao();
    }
}
