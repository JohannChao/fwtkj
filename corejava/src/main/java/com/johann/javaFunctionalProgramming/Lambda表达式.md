### 1,概念
##### 1.1,什么是Lambda表达式
计算机编程中的 Lambda 表达式，也称为匿名函数，是未绑定标识符的函数定义。

##### 1.2,为什么引入Lambda表达式
虽然 Java 提供了 java.util.concurrent 包以及很多第三方类库来帮助我们写出多核 CPU 上运行良好的程序，但在大数据集合的处理上面这些工具包在高效的并行操作上都有些欠缺，
我们很难通过简单的修改就能够在多核 CPU 上进行高效的运行。

为了解决上述的问题，需要在程序语言上修改现有的 Java，增加 Lambda 表达式，同时在 Java 8 也引入Stream（java.util.stream） 流—— 一个来自数据源的元素队列，
支持聚合操作，来提供对大数据集合的处理能力。

##### 1.3,Lambda表达式的优点
1. 更加紧凑的代码： Lambda 表达式可以通过省去冗余代码来减少我们的代码量，增加我们代码的可读性；
2. 更好地支持多核处理： Java 8 中通过 Lambda 表达式可以很方便地并行操作大集合，充分发挥多核 CPU 的潜能，并行处理函数如 filter、map 和 reduce；
3. 改善我们的集合操作： Java 8 引入 Stream API，可以将大数据处理常用的 map、reduce、filter 这样的基本函数式编程的概念与 Java 集合结合起来，方便我们进行大数据处理。

##### 1.4,Lambda表达式示例
```java
public class LambdaTest {
    public static void main(String[] args) {
        /**
         * Java8 以前，使用匿名内部类将行为与按钮进行关联
         */
        Button button = new Button();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("button click!");
            }
        });

        /**
         * Java8 之后，使用Lambda表达式将行为与按钮进行关联
         */
        button.addActionListener(e -> System.out.println("button click!"));
    }
}
```

### 2,Lambda表达式语法
```text
button.addActionListener(e -> System.out.println("button click!"));
参数 -> 具体实现
其包含一个 Lambda 表达式的运算符 ->，在运算符的左边是输入参数，右边则是函数主体。
```
##### 2.1,特点
1. 可选类型声明： 不需要声明参数类型，编译器可以自动识别参数类型和参数值。在上个例子中，并没有指定 e 到底是什么类型；
2. 可选的参数圆括号： 一个参数可以不用定义圆括号，但多个参数需要定义圆括号；
3. 可选的大括号： 如果函数主体只包含一个语句，就不需要使用大括号；
4. 可选的返回关键字： 如果主体只有一个表达式返回值则编译器会自动返回值，大括号需要指明表达式返回了一个数值。

##### 2.2,Lambda表达式的几种形式
```text
1,不包含参数
Runnable noArguments = () -> System.out.println("hello world");
在这个例子中，Runnable 接口，只有一个 run 方法，没有参数，且返回类型为 void，所以我们的 Lambda 表达式使用 () 表示没有输入参数。

2,有且只有一个参数
ActionListener oneArguments = event -> System.out.println("hello world");
在只有一个参数的情况下 我们可以把 () 省略。

3,有多个参数
BinaryOperator<Long> add = (x,y) -> x+y ;
当存在多个参数时，使用 () 把参数包裹起来，并用 , 来分割参数。

4,表达式主体是一个代码块
Runnable noArguments = () -> {
    System.out.print("hello");
    System.out.println("world");
}
当有多行代码的时候我们需要使用 {} 把表达式主体给包裹起来。

5,显示声明参数类型
BinaryOperator<Long> add = (Long x, Long y) -> x+y ;
通常 Lambda 的参数类型由编译器推断得出的，也可以显示的声明参数类型。
```
> 在上述示例中，并没有指定参数event,x,y的具体类型，但是编译器却知道它是什么类型。原因就在于编译器可以从程序的上下文推断出来，这里的上下文包含下面 3 种情况：
> 赋值上下文；方法调用上下文；类型转换上下文。

### 3,Lambda变量和作用域
##### 3.1,访问局部变量
```text
Lambda 表达式不会从父类中继承任何变量名，也不会引入一个新的作用域。Lambda 表达式基于词法作用域，也就是说 Lambda 表达式函数体里面的变量和它外部环境的变量具有相同的语义。
访问局部变量要注意如下 3 点：

1, 可以直接在 Lambda 表达式中访问外层的局部变量,但是这个局部变量必须是声明为 final 的或者既成事实上的 final 变量。
public static void main(String[] args) {
    //final int delta = -1;
    int delta = -1;
    
    BinaryOperator<Integer> add = (x,y) -> {
        // 在 Lambda 表达式中试图修改局部变量是不允许的，报错：Variable used in lambda expression should be final or effectively final
        // delta = 0;
        return x+y+delta;
    };
    System.out.println(add.apply(1,2));
}
如果这个变量是一个既成事实上的 final 变量的话，就可以不使用 final 关键字。所谓个既成事实上的 final 变量是指只能给变量赋值一次，
在该例子中，delta 只在初始化的时候被赋值，所以它是一个既成事实的 final 变量。

2, 在 Lambda 表达式当中被引用的变量的值不可以被更改
public static void main(String[] args) {
    //final int delta = -1;
    int delta = -1;
    
    BinaryOperator<Integer> add = (x,y) -> x+y+delta;
    System.out.println(add.apply(1,2));
    
    // 在 Lambda 表达式之后修改局部变量是不允许的，报错：Variable used in lambda expression should be final or effectively final
    // delta = 0;
}

3, 在 Lambda 表达式当中不允许声明一个与局部变量同名的参数或者局部变量
public static void main(String[] args) {
    //final int delta = -1;
    int delta = -1;
    
    // Variable 'delta' is already defined in the scope
    BinaryOperator<Integer> add2 = (delta,y) -> delta+y+delta;
}
```

##### 3.2,访问对象字段与静态变量
```text
Lambda 内部对于实例的字段和静态变量是即可读又可写的。
public class LambdaTest {
    public static int staticNum;
    private int num;
    public void doTest() {
        BinaryOperator<Integer> add = (x, y) -> {
            num = 3;
            staticNum = 4;
            return x + y + num + staticNum;
        };
        Integer apply = add.apply(1, 2);
        System.out.println(apply);
    }
}    
```
>  Lambda 表达式可以读写实例变量和类变量，只能读取局部变量。

### 4,Lambda 表达式的引用
Lambda 表达式的方法引用可以理解为 Lambda 表达式的一种快捷写法，相较于通常的 Lambda 表达式而言有着更高的可读性和重用性。

Lambda 表达式的引用分为：方法引用 和 构造器引用两类。
* 方法引用的格式为：类名::方法名。:: 是引用的运算符，其左边是类名，右边则是引用的方法名。
* 构造器引用的格式为：类名::new。同样，:: 是引用的运算符，其左边是类名，右边则是使用关键字 new 表示调用该类的构造函数。构造器引用是一种特殊的引用。

```java
public class LambdaReferenceTest {
    /**
     * 1, 静态方法引用，所谓静态方法应用就是调用类的静态方法。
     * 1). 被引用的参数列表和接口中的方法参数一致；
     * 2). 接口方法没有返回值的，引用方法可以有返回值也可以没有；
     * 3). 接口方法有返回值的，引用方法必须有相同类型的返回值。
     */
    Finder finder = StaticMethodClass::doFindStatic;

    /**
     * 2, 参数方法引用，顾名思义就是可以将参数的一个方法引用到 Lambda 表达式中。
     * 要求：接口方法和引用方法必须有相同的 参数 和 返回值。
     *
     * Finder finder1 = (s1,s2) -> s1.indexOf(s2);
     * 编译器会使用参数 s1 为引用方法的参数，将引用方法与 Finder 接口的 find 方法进行类型匹配，最终调用 String 的 indexOf 方法。
     */
    Finder finder1 = String::indexOf;

    /**
     * 3, 实例方法引用，就是直接调用实例的方法。
     * 要求：接口方法和实例的方法必须有相同的参数和返回值。
     */
    StaticMethodClass smc = new StaticMethodClass();
    Finder finder2 = smc::doFind;

    /**
     * 4, 构造器引用，即引用一个类的构造函数
     * 要求：接口方法和对象构造函数的参数必须相同。
     *
     * MyFactory create = chars -> new String(chars);
     * 它与 String(char[] chars) 这个 String 的构造函数有着相同的方法签名。这个时候我们就可以使用构造器引用了：
     */
    MyFactory create = String::new;
}

@FunctionalInterface
interface Finder {
    int find(String s1, String s2);
}
//创建一个带有静态方法的类
class StaticMethodClass{
    public static int doFindStatic(String s1, String s2){
        return s1.lastIndexOf(s2);
    }

    public int doFind(String s1, String s2){
        return s1.lastIndexOf(s2);
    }
}

interface MyFactory{
    String create(char[] chars);
}
```
### 5,Lambda表达式与匿名内部类
```java
public class Lambda_AnonymousInner_test {

    private String instanceVar = "instanceVar";
    private static String classVar = "classVar";

    public void test(){
        // 被匿名内部类或者Lambda表达式访问的局部变量，都隐式添加了final修饰符在前面
        String localVar = "Hello,localVar";

        // 匿名内部类实现，会编译出一个新的class文件 Lambda_AnonymousInner_test$1.class
        Writable writable = new Writable() {
            @Override
            public void write() {
                instanceVar = "AnonymousInner_instanceVar";
                classVar = "AnonymousInner_classVar";
                // 匿名内部类的this指向的是 其本身（Lambda_AnonymousInner_test$1）
                System.out.println("AnonymousInner write 【 this.getClass() is -- "+this.getClass()+"】");
                // 内部类可以从父类继承属性，Lambda 表达式却不能。匿名内部类实现的抽象方法的方法体允许调用接口中定义的默认方法。
                System.out.println("AnonymousInner【 "+defaultWrite()+"】");
            }
        };
        writable.write();
        System.out.println("AnonymousInner instance 【"+writable.defaultWrite()+"】");


        // Lambda表达式实现，不会编译出具体的class文件
        Writable writable1 = () -> {
            // Lambda 表达式的this指向的是 创建这个 Lambda 表达式的类对象的实例（Lambda_AnonymousInner_test）
            System.out.println("Lambda write 【 this.getClass() is -- "+this.getClass()+"】");

            // 内部类可以从父类继承属性，Lambda 表达式却不能。Lambda表达式的代码块不允许调用接口中定义的默认方法。
            // System.out.println("Lambda："+defaultWrite());
        };
        writable1.write();
        System.out.println("Lambda instance 【"+writable1.defaultWrite()+"】");
    }

    public static void main(String[] args) {
        new Lambda_AnonymousInner_test().test();
    }

}

@FunctionalInterface
interface Writable {
    /**
     * 函数式接口代表只包含一个抽象方法接口。函数式类型接口可以包含多个默认方法、类方法，但只能声明一个抽象方法。
     */
    void write();

    /**
     * java.lang.Object 中的 public 方法
     * @param obj
     * @return
     */
    public boolean equals(Object obj);
    public String toString();
    
    /**
     * 接口默认方法
     * @return string
     */
    default String defaultWrite(){
        return "defaultWrite: I'm Johann, this.getClass() is -- "+this.getClass();
    }

    /**
     * 接口类方法
     * @return string
     */
    static String staticWrite(){
        return "staticWrite: I'm Johann";
    }

    public static void main(String[] args) {
        Writable writable = () -> {
            System.out.println("函数式接口Lambda表达式");
        };
        writable.write();
        System.out.println(writable.defaultWrite());
        System.out.println(Writable.staticWrite());
        System.out.println(writable.toString());
    }
}
```
