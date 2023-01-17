package com.johann.javaFunctionalProgramming.lambdaDemo;

/** Lambda表达式的方法引用
 * @ClassName: LambdaReferenceTest
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class LambdaReferenceTest {
    /**
     * 静态方法引用，所谓静态方法应用就是调用类的静态方法。
     * 1. 被引用的参数列表和接口中的方法参数一致；
     * 2. 接口方法没有返回值的，引用方法可以有返回值也可以没有；
     * 3. 接口方法有返回值的，引用方法必须有相同类型的返回值。
     */
    Finder finder = StaticMethodClass::doFindStatic;

    /**
     * 参数方法引用，顾名思义就是可以将参数的一个方法引用到 Lambda 表达式中。
     * 要求：接口方法和引用方法必须有相同的 参数 和 返回值。
     * <p>
     * Finder finder1 = (s1,s2) -> s1.indexOf(s2);
     * 编译器会使用参数 s1 为引用方法的参数，将引用方法与 Finder 接口的 find 方法进行类型匹配，最终调用 String 的 indexOf 方法。
     */
    Finder finder1 = String::indexOf;

    /**
     * 实例方法引用，就是直接调用实例的方法。
     * 要求：接口方法和实例的方法必须有相同的参数和返回值。
     */
    StaticMethodClass smc = new StaticMethodClass();
    Finder finder2 = smc::doFind;

    /**
     * 构造器引用，即引用一个类的构造函数
     * 要求：接口方法和对象构造函数的参数必须相同。
     * <p>
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

