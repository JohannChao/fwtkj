package com.johann.designPattern.dahao.bridge;

/** 数据库表操作接口
 * @Description:
 * @Param:
 * @return:
 * @Author: Johann
 */
public interface IDao<T> {
    void insert(T t);
    void get(Integer id);
    void update(T t);
}
