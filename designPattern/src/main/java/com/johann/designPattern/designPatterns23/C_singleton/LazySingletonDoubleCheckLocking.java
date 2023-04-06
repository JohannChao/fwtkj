package com.johann.designPattern.designPatterns23.C_singleton;

/** 懒汉式单例模式三,双重检查锁定
 * @ClassName: LazySingletonDoubleCheckLocking
 * @Description: 双重检查锁定, 线程安全, 效率高, 推荐使用
 *    只需要保持 instance = new LazySingletonThree() 是线程互斥的即可，既解决线程安全问题，又解决程序性能问题。
 *    存在“无序写”问题，即在构造函数中，如果存在对成员变量的写操作，可能会出现“无序写”问题。
 *    “无序写”问题的解决方案是：将成员变量声明为 volatile 类型。
 * @Author: Johann
 * @Version: 1.0
 **/
public class LazySingletonDoubleCheckLocking {
    private static LazySingletonDoubleCheckLocking singleton = null;

    private LazySingletonDoubleCheckLocking() {
    }

    /**
     * 双重检查锁定,线程安全,效率高,推荐使用
     * 只需要保持 instance = new LazySingletonThree() 是线程互斥的即可，既解决线程安全问题，又解决程序性能问题。
     * 但是，存在“无序写”问题。
     * java平台内存模型中有一个叫“无序写”（out-of-order writes）的机制，正是这个机制导致了双重检查加锁方法的失效。
     * instance = new LazySingletonThree(); 这行其实做了两个事情：1、调用构造方法，创建了一个实例。2、把这个实例赋值给instance这个实例变量
     * 问题就是，这两步jvm是不保证顺序的。也就是说。可能在调用构造方法之前，instance已经被设置为非空了。
     * “无序写”问题示例：
     *      有两个线程A、B
     *      1、线程A进入getInstance()方法。
     *      2、因为此时instance为空，所以线程A进入synchronized块。
     *      3、线程A执行 instance = new Singleton(); 把实例变量instance设置成了非空。（注意，是在调用构造方法之前。）
     *      4、线程A退出，线程B进入。
     *      5、线程B检查instance是否为空，此时不为空（第三步的时候被线程A设置成了非空）。线程B返回instance的引用。（问题出现了，这时instance的引用并不是Singleton的实例，因为没有调用构造方法。）
     *      6、线程B退出，线程A进入。
     *      7、线程A继续调用构造方法，完成instance的初始化，再返回。
     * @Param: []
     * @return: LazySingletonDoubleCheckLocking
     * @Author: Johann
     */
    public static LazySingletonDoubleCheckLocking getInstance() {
        if (singleton == null) {
            synchronized (LazySingletonDoubleCheckLocking.class) {
                if (singleton == null) {
                    singleton = new LazySingletonDoubleCheckLocking();
                }
            }
        }
        System.out.println("我是双重检查锁定懒汉式单例！");
        return singleton;
    }

    /**
     * 双重检查锁定无序写问题解决方案
     * 当一个共享变量被volatile修饰时，它会保证修改的值会立即被更新到主存，当有其他线程需要读取时，它会去内存中读取新值
     */
    private volatile static LazySingletonDoubleCheckLocking singleton2 = null;

    /**
     * @Description: 双重检查锁定解决无序写问题,线程安全,效率高,推荐使用
     * @Param: []
     * @return: LazySingletonDoubleCheckLocking
     * @Author: Johann
     */
    public static LazySingletonDoubleCheckLocking getInstance2() {
        if (singleton2 == null) {
            synchronized (LazySingletonDoubleCheckLocking.class) {
                if (singleton2 == null) {
                    singleton2 = new LazySingletonDoubleCheckLocking();
                }
            }
        }
        System.out.println("我是双重检查锁定懒汉式单例,且解决了无序写问题！");
        return singleton2;
    }

}
