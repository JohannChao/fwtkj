package a_objectOriented.classInitializationOrder;

/**
 * @ClassName: ClassInitializationTest
 * @Description: 类初始化及加载的顺序
 * @Author: Johann
 * @Version: 1.0
 **/
public class ClassInitializationTest {
    public static void main(String[] args) {
        System.out.println("调用  Dolphins.main() ");
        Dolphins.main(null);
    }
}

class Mammal{
    {
        System.out.println("Mammal 代码块 001");
    }
    public Behaviour behaviour = new Behaviour("胎生");
    private int m = 10;
    protected int n;
    private int o = returnInt("成员变量 Mammal.o 初始化 ");

    static {
        System.out.println("Mammal 静态代码块 001");
    }

    Mammal(){
        System.out.println("m = "+m+", n = "+n+", o = "+o);
        n = 12;
    }

    {
        System.out.println("Mammal 代码块 002");
    }

    private static int x1 = returnInt("静态变量 Mammal.x1 初始化");

    static {
        System.out.println("Mammal 静态代码块 002");
    }

    static int returnInt(String s){
        System.out.println(s);
        return 16;
    }

}

class Dolphins extends Mammal{
    {
        System.out.println("Dolphins 代码块 001");
    }
    public Behaviour behaviour = new Behaviour("胎生_群居");
    private int d = returnInt("成员变量 Dolphins.d 初始化");

    static {
        System.out.println("Dolphins 静态代码块 001");
    }

    public Dolphins(){
        System.out.println("d = "+d);
        System.out.println("n = "+n);
    }

    {
        System.out.println("Dolphins 代码块 002");
    }

    private static int x1 = returnInt("静态变量 Dolphins.x1 初始化");
    private static int x2 = returnInt("静态变量 Dolphins.x2 初始化");

    static {
        System.out.println("Dolphins 静态代码块 002");
    }

    public static void main(String[] args) {
        System.out.println("执行 Dolphins main()");
        Dolphins dolphins = new Dolphins();
    }
}

class Behaviour{
    private String name;
    public Behaviour(){

    }
    public Behaviour(String name){
        System.out.println("习性 ： "+name);
        this.name = name;
    }
}




