package com.johann.designPattern.designPatterns23.G_bridge;

/** UserDao
 * @ClassName: UserDao
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class UserDao implements IDao<User> {

    public UserDao() {
    }

    @Override
    public void insert(User user) {
        System.out.println("插入User: "+user.toString());
    }

    @Override
    public void get(Integer id) {
        System.out.println("查询User: "+id);
    }

    @Override
    public void update(User user) {
        System.out.println("更新User: "+user.toString());
    }
}
