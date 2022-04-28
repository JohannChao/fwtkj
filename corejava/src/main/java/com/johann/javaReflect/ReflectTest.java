package com.johann.javaReflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @ClassName: ReflectTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class ReflectTest {

    /**
     * 获取Class的三种方式
     */
    public static void getClazz()throws ClassNotFoundException{
        //1，直接通过 【对象.getClass()】方法获取
        ReflectUser ru = new ReflectUser();
        Class c = ru.getClass();

        //2，直接通过 【类名.class】 获取。【任何一个类都有一个隐含的静态成员变量 class】
        Class c2 = ReflectUser.class;

        //3，通过 【Class.forName("")】静态方法获取
        Class c3 = Class.forName("com.johann.javaReflect.ReflectUser");

        //一个类在 JVM 中只会有一个 Class 实例,即我们对上面获取的 c,c2,c3进行比较（==），发现都是true
        System.out.println(c==c2);
        System.out.println(c2==c3);
    }


    /**
     * Class类常见方法
     *
     *     getName()：获得类的完整名字。
     *
     * 　　getFields()：获得类的public类型的属性。
     *
     * 　　getDeclaredFields()：获得类的所有属性。包括private 声明的和继承类
     *
     * 　　getField(String name)：获得类的指定名称的public类型的属性。
     *
     * 　　getDeclaredField(String name)：获得类的指定名称的的属性。
     *
     * 　　getMethods()：获得类的public类型的方法。
     *
     * 　　getDeclaredMethods()：获得类的所有方法。包括private 声明的和继承类
     *
     * 　　getMethod(String name, Class<?>... parameterTypes)：获得类的特定方法，name参数指定方法的名字，parameterTypes 参数指定方法的参数类型。
     *
     *     getConstructors()：获得类的public类型的构造方法。
     *
     *     getConstructor(Class<?>... parameterTypes)：获得类的特定构造方法，parameterTypes 参数指定构造方法的参数类型。
     *
     *     newInstance()：通过类的不带参数的构造方法创建这个类的一个对象。
     */
    public static void classCommonMethod(Class c) throws NoSuchMethodException,IllegalAccessException,InstantiationException,NoSuchFieldException{

        String className = c.getName();
        System.out.println("getName()，类的完整名字 : "+className);

        Field[] fields = c.getFields();
        System.out.println("getFields()，类的public类型的属性 : ");
        for (Field field : fields) {
            System.out.println("----"+field.getName());
        }

        Field[] declaredFields = c.getDeclaredFields();
        System.out.println("getDeclaredFields()，类的所有属性。包括private 声明的和继承类 : ");
        for (Field field : declaredFields) {
            System.out.println("----"+field.getName());
        }

        Method[] methods = c.getMethods();
        System.out.println("getMethods()，类的public类型的方法 ： ");
        for (Method method : methods) {
            System.out.println("----"+method.getName());
        }

        Method[] declaredMethods = c.getDeclaredMethods();
        System.out.println("getDeclaredMethods()，类的所有方法。包括private 声明的和继承类 ： ");
        for (Method method : declaredMethods) {
            System.out.println("----"+method.getName());
        }

        Method method = c.getMethod("publicMethod");
        System.out.println("getMethod()，类的特定方法 : "+method.getName());


        Constructor[] constructors = c.getConstructors();
        System.out.println("getConstructors()，类的public类型的构造方法 ： ");
        for (Constructor constructor : constructors) {
            System.out.println("----"+constructor.getName());
        }


        ReflectUser ru = (ReflectUser) c.newInstance();

        Field f2 = c.getDeclaredField("name");
        System.out.println("getDeclaredField()，类的指定名称的的属性 : "+f2.getName());
        //启用和禁用访问安全检查的开关，值为 true，则表示反射的对象在使用时应该取消 java 语言的访问检查；反之不取消
        f2.setAccessible(true);
        f2.set(ru,"newJohann");
        System.out.println(f2.get(ru));
    }




    public static void main(String[] args) throws Exception{
        //getClazz();

        Class c2 = ReflectUser.class;
        classCommonMethod(c2);
    }

}
