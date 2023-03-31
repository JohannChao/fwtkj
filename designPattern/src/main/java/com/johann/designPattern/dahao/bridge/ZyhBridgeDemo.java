package com.johann.designPattern.dahao.bridge;

/**
 * @ClassName: ZyhBridgeDemo
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class ZyhBridgeDemo {
    public static void main(String[] args) {
        IDao<User> userDao = new UserDao();
        IDao<Department> departmentDao = new DepartmentDao();

        AbstractDatabaseConnection mysqlConnectionForUser = MysqlConnection.getInstance();
        mysqlConnectionForUser.setDao(userDao);
        mysqlConnectionForUser.insert(new User(1,"johann"));
        mysqlConnectionForUser.get(1);
        mysqlConnectionForUser.update(new User(1,"jessie"));
        AbstractDatabaseConnection mysqlConnectionForDepartment = MysqlConnection.getInstance();
        System.out.println(mysqlConnectionForUser == mysqlConnectionForDepartment);
        mysqlConnectionForDepartment.setDao(departmentDao);
        mysqlConnectionForDepartment.insert(new Department(1,"IT"));
        mysqlConnectionForDepartment.get(1);
        mysqlConnectionForDepartment.update(new Department(1,"IT2"));

        AbstractDatabaseConnection oracleConnection = OracleConnection.getInstance();
        oracleConnection.setDao(userDao);
        oracleConnection.insert(new User(1,"johann"));
        oracleConnection.get(1);
        oracleConnection.update(new User(1,"jessie"));
        oracleConnection.setDao(departmentDao);
        oracleConnection.insert(new Department(1,"IT"));
        oracleConnection.get(1);
        oracleConnection.update(new Department(1,"IT2"));
    }
}
