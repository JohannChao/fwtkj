package com.johann.designPattern.designPatterns23.G_bridge;

/** OracleConnection
 * @ClassName: OracleConnection
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class OracleConnection<T> extends AbstractDatabaseConnection<T>{

    private OracleConnection() {
        System.out.println("OracleConnection...");
    }

    /**
     * 静态内部类
     * @param <T>
     */
    public static class OracleConnectionBuilder<T> {
        private static final OracleConnection oracleConnection = new OracleConnection();
    }

    /**
     * 静态内部类实现单例模式
     * @return
     */
    public static OracleConnection getInstance() {
        return OracleConnectionBuilder.oracleConnection;
    }

    @Override
    public void insert(T t) {
        System.out.println("OracleConnection insert");
        dao.insert(t);
    }

    @Override
    public void get(Integer id) {
        System.out.println("OracleConnection get");
        dao.get(id);
    }

    @Override
    public void update(T t) {
        System.out.println("OracleConnection update");
        dao.update(t);
    }
}
