package com.johann.designPattern.designPatterns23.G_bridge;

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
