package com.johann.designPattern.dahao.abstractFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 抽象工厂模式Demo
 * 读取配置文件，使用反射转为在运行期（而非编译期）动态获取需要的工厂
 */
public class ZyhAbstractFactoryDemo {
    public static void main(String[] args) {
        //IDatabaseFactory factory = new MysqlFactory();
        //IDatabaseFactory factory = new OracleFactory();

        //System.out.println(System.getProperty("user.dir"));
        //System.getProperties().entrySet().forEach(objectObjectEntry -> System.out.println("【"+objectObjectEntry.getKey()+"】: "+objectObjectEntry.getValue()));
        // 读取配置文件
        Properties properties = new Properties();
        InputStream in = ZyhAbstractFactoryDemo.class.getClassLoader().getResourceAsStream("source.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String databaseName = properties.getProperty("abstraFactory.databaseName");
        // 使用反射获取所需工厂
        IDatabaseFactory factory = getDatabaseFactory(databaseName);

        IUserDao userDao = factory.createUserDao();
        userDao.insertUser();
        userDao.getUser();
        userDao.updateUser();
        IDepartmentDao departmentDao = factory.createDepartmentDao();
        departmentDao.insertDepartment();
        departmentDao.getDepartment();
        departmentDao.updateDepartment();
    }

    /**
     * 使用反射，运行期获取所需的工厂
     * @param database
     * @return
     */
    public static IDatabaseFactory getDatabaseFactory(String database){
        IDatabaseFactory factory = null;
        StringBuilder className = new StringBuilder("com.johann.designPattern.dahao.abstractFactoryMethod.");
        className.append(database).append("Factory");
        try {
            factory = (IDatabaseFactory) Class.forName(className.toString()).newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            return factory;
        }
    }
}
