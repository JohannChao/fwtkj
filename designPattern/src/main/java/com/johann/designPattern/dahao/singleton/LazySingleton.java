package com.johann.designPattern.dahao.singleton;

/**
 * @ClassName LazySingleton
 * @Description 懒汉式单例模式
 * @Author Johann
 **/
public class LazySingleton {

    /**
     * @Author Johann
     * @Description 懒汉模式，类加载的时候，不创建实例，只有首次调用 getInstance() 方法的时候，才会首次创建实例对象。
     *  如果是在多线程中，应该保留 "volatile" 和 "synchronized"关键字，这将确保线程的安全性。这两个关键字的存在，使得每次访问都需要同步，影响了性能。
     *  "volatile" 保证 instance 在所有线程中同步
     * @Param 
     * @return 
     **/        
    private static volatile LazySingleton instance = null;

    private LazySingleton(){};

    /**
     * 线程不安全的，如果两个线程同时进入if(instance == null)，都判断是null，那么就会得到两个实例。
     */
//    public static LazySingleton getInstance(){
//        if(instance == null){
//            instance = new LazySingleton();
//        }
//        System.out.println("我是简单的懒汉式单例！");
//        return instance;
//    }

/******************************************** 我是分割线 ************************************************/

    /**
     * 加锁的方式来实现互斥，从而保证线程安全
     * 对getInstance()方法加锁，这会影响我们程序的性能，我们只需要在第一次调用getInstance()方法时加锁就可以了。
     */
//    public static synchronized LazySingleton getInstance(){
//        if(instance == null){
//            instance = new LazySingleton();
//        }
//        System.out.println("我是同步法懒汉式单例！");
//        return instance;
//    }

/******************************************** 我是分割线 ************************************************/

    /**
     * 只需要保持 instance = new LazySingleton() 是线程互斥的即可，既解决线程安全问题，又解决程序性能问题。
     *
     * 存在“无序写”问题
     **/
//    public static LazySingleton getInstance(){
//        if(instance == null){
//            synchronized (LazySingleton.class){}{
//                if(instance == null){
//                    instance = new LazySingleton();
//                }
//            }
//        }
//        System.out.println("我是双重锁定懒汉式单例！");
//        return instance;
//    }

/******************************************** 我是分割线 ************************************************/

    /**
     *  这次看起来既解决了线程安全的问题，又解决了程序性能的问题。但是，仍存在问题。
     *
     *  java平台内存模型中有一个叫“无序写”（out-of-order writes）的机制，正是这个机制导致了双重检查加锁方法的失效。
     *  instance = new LazySingleton(); 这行其实做了两个事情：1、调用构造方法，创建了一个实例。2、把这个实例赋值给instance这个实例变量
     *  问题就是，这两步jvm是不保证顺序的。也就是说。可能在调用构造方法之前，instance已经被设置为非空了。
     *
     *  假设：
     *      有两个线程A、B
     *      1、线程A进入getInstance()方法。
     *
     *      2、因为此时instance为空，所以线程A进入synchronized块。
     *
     *      3、线程A执行 instance = new Singleton(); 把实例变量instance设置成了非空。（注意，是在调用构造方法之前。）
     *
     *      4、线程A退出，线程B进入。
     *
     *      5、线程B检查instance是否为空，此时不为空（第三步的时候被线程A设置成了非空）。线程B返回instance的引用。（问题出现了，这时instance的引用并不是Singleton的实例，因为没有调用构造方法。）
     *
     *      6、线程B退出，线程A进入。
     *
     *      7、线程A继续调用构造方法，完成instance的初始化，再返回。
     *
     *
     **/
    public static LazySingleton getInstance(){
        if(instance == null){
            synchronized (LazySingleton.class){}{//1
                LazySingleton temp = instance;//2
                if(temp == null){
                    synchronized (LazySingleton.class) {} {//3
                        temp = new LazySingleton();//4
                    }
                    instance = temp;
                }
            }
        }
        System.out.println("我是解决无序写问题的懒汉式单例！");
        return instance;
    }
    /**
     * 1、线程A进入getInstance()方法。
     *
     * 2、因为instance是空的 ，所以线程A进入位置//1的第一个synchronized块。
     *
     * 3、线程A执行位置//2的代码，把instance赋值给本地变量temp。instance为空，所以temp也为空。
     *
     * 4、因为temp为空，所以线程A进入位置//3的第二个synchronized块。（后来想想这个锁有点多余）
     *
     * 5、线程A执行位置//4的代码，把temp设置成非空，但还没有调用构造方法！（“无序写”问题）
     *
     * 6、如果线程A阻塞，线程B进入getInstance()方法。
     *
     * 7、因为instance为空，所以线程B试图进入第一个synchronized块。但由于线程A已经在里面了。所以无法进入。线程B阻塞。
     *
     * 8、线程A激活，继续执行位置//4的代码。调用构造方法。生成实例。
     *
     * 9、将temp的实例引用赋值给instance。退出两个synchronized块。返回实例。
     *
     * 10、线程B激活，进入第一个synchronized块。
     *
     * 11、线程B执行位置//2的代码，把instance实例赋值给temp本地变量。
     *
     * 12、线程B判断本地变量temp不为空，所以跳过if块。返回instance实例。
     **/

    /******************************************** 我是分割线 ************************************************/

    /**
     *  以上我们分析的问题是解决了，但是代码感觉很冗余。
     *  此时，我们可以考虑使用 饿汉式单例模式 见同包下的HungrySingleton类
     *  饿汉式单例模式是线程安全的，因为它是预先声明了一个实例，且该实例之后不再改变，故是线程安全的。
     *  缺点是：如果构造的单例很大，构造完又迟迟不使用，会导致资源浪费
     **/

    /******************************************** 我是分割线 ************************************************/

    /**
     *  更为完美的解决办法，是通过内部类来实现懒汉式单例模式，见同包下的 InnerClassLazySingleton
     **/

    /******************************************** PS ************************************************/

    /**
     * "无序写"机制
     *
     *      这是jvm的特性，比如声明两个变量，String a; String b; jvm可能先加载a也可能先加载b。
     *  同理，instance = new Singleton();可能在调用Singleton的构造函数之前就把instance置成了非空。
     *  这是很多人会有疑问，说还没有实例化出Singleton的一个对象，那么instance怎么就变成非空了呢？
     *  它的值现在是什么呢？想了解这个问题就要明白instance = new Singleton();这句话是怎么执行的，下面用一段伪代码向大家解释一下：
     *  ```java
     *  mem = allocate();             //为Singleton对象分配内存。
     *  instance = mem;               //注意现在instance是非空的，但是还没有被初始化。
     *  ctorSingleton(instance);    //调用Singleton的构造函数，传递instance.
     *  ```
     *  由此可见当一个线程执行到instance = mem; 时instance已为非空，如果此时另一个线程进入程序判断instance为非空，那么直接就跳转到return instance;
     *  而此时Singleton的构造方法还未调用instance，现在的值为allocate();返回的内存对象。
     *  所以第二个线程得到的不是Singleton的一个对象，而是一个内存对象。
     *
     **/

    // 参考：https://blog.csdn.net/liushuijinger/article/details/9069801
}
