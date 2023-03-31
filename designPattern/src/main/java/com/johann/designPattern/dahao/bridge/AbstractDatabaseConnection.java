package com.johann.designPattern.dahao.bridge;

/** 数据库连接抽象类
 * @ClassName: AbstractDatabaseConnection
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public abstract class AbstractDatabaseConnection<T> {
    protected IDao<T> dao;

    public AbstractDatabaseConnection() {
    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }

    public abstract void insert(T t);

    public abstract void get(Integer id);

    public abstract void update(T t);
}
