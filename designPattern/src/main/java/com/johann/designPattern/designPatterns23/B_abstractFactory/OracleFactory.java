package com.johann.designPattern.designPatterns23.B_abstractFactory;

/**
 * Oracle数据库连接工厂【具体工厂】
 */
public class OracleFactory implements IDatabaseFactory{
    @Override
    public IUserDao createUserDao() {
        return new OracleUserDao();
    }

    @Override
    public IDepartmentDao createDepartmentDao() {
        return new OracleDepartmentDao();
    }
}
