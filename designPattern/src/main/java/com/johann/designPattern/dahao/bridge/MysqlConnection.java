package com.johann.designPattern.dahao.bridge;

/** MysqlConnection
 * @ClassName: MysqlConnection
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class MysqlConnection<T> extends AbstractDatabaseConnection<T> {

    private MysqlConnection() {
        System.out.println("MysqlConnection...");
    }

    /**
     * 静态内部类
     * @param <T>
     */
    public static class MysqlConnectionBuilder<T> {
        private static final MysqlConnection mysqlConnection = new MysqlConnection();
    }

    /**
     * 静态内部类实现单例模式
     * @return
     */
    public static MysqlConnection getInstance() {
        return MysqlConnectionBuilder.mysqlConnection;
    }

    @Override
    public void insert(T t) {
        System.out.println("MysqlConnection insert");
        dao.insert(t);
    }

    @Override
    public void get(Integer id) {
        System.out.println("MysqlConnection get");
        dao.get(id);
    }

    @Override
    public void update(T t) {
        System.out.println("MysqlConnection update");
        dao.update(t);
    }
}
