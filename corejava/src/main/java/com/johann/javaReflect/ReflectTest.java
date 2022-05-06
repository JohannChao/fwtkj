package com.johann.javaReflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/** 反射
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
     * 　　getFields()：获得类的public类型的属性（包含父类的,接口的public）。
     *
     * 　　getDeclaredFields()：获得类的所有属性。包括private 声明的。
     *
     * 　　getField(String name)：获得类的指定名称的public类型的属性。
     *
     * 　　getDeclaredField(String name)：获得类的指定名称的的属性。
     *
     * 　　getMethods()：获得类的public类型的方法（包含父类的public,实现接口的方法）。
     *
     * 　　getDeclaredMethods()：获得类的所有方法。包括private声明的 以及实现的接口方法。
     *
     * 　　getMethod(String name, Class<?>... parameterTypes)：获得类的特定方法，name参数指定方法的名字，parameterTypes 参数指定方法的参数类型。
     *
     *     getConstructors()：获得类的public类型的构造方法。
     *
     *     getConstructor(Class<?>... parameterTypes)：获得类的特定构造方法，parameterTypes 参数指定构造方法的参数类型。
     *
     *     newInstance()：通过类的不带参数的构造方法创建这个类的一个对象。
     */
    public static void classCommonMethod(Class c) throws Exception{

        String className = c.getName();
        System.out.println("getName()，类的完整名字 : "+className);

        Field[] fields = c.getFields();
        System.out.println("\ngetFields()，当前类的public类型的属性（包含父类的,接口的public） : ");
        for (Field field : fields) {
            System.out.println("   "+field.getName());
            System.out.println("   "+field.toGenericString());
        }

        Field[] declaredFields = c.getDeclaredFields();
        System.out.println("\ngetDeclaredFields()，当前类的所有属性。包括private 声明的 : ");
        for (Field field : declaredFields) {
            System.out.println("   "+field.getName());
            System.out.println("   "+field.toGenericString());
        }

        Method[] methods = c.getMethods();
        System.out.println("\ngetMethods()，类的public类型的方法（包含父类的public,实现接口的方法） ： ");
        for (Method method : methods) {
            System.out.println("   "+method.getName());
            System.out.println("   "+method.toString());
        }

        Method[] declaredMethods = c.getDeclaredMethods();
        System.out.println("\ngetDeclaredMethods()，类的所有方法。包括private声明的 以及实现的接口方法 ： ");
        for (Method method : declaredMethods) {
            System.out.println("   "+method.getName());
            System.out.println("   "+method.toString());
        }

        Method method = c.getMethod("interfaceMethod");
        System.out.println("\ngetMethod()，类的特定方法 : "+method.getName());


        Constructor[] constructors = c.getConstructors();
        System.out.println("\ngetConstructors()，类的public类型的构造方法 ： ");
        for (Constructor constructor : constructors) {
            System.out.println("   "+constructor.toString());
        }

        //根据class初始化对象，并获取对象的属性值，修改属性值
        System.out.println("\n------ 根据class初始化对象，并获取对象的属性值，修改属性值 --------");
        ReflectUser ru = (ReflectUser) c.newInstance();
        Field f2 = c.getDeclaredField("privateName");
        System.out.println("getDeclaredField(fieldName)，类的指定名称的的属性 : "+f2.getName());
        //启用和禁用访问安全检查的开关，值为 true，则表示反射的对象在使用时应该取消 java 语言的访问检查；反之不取消
        f2.setAccessible(true);
        f2.set(ru,"newJohann");
        System.out.println(f2.get(ru));

        //选择合适的构造器来初始化对象
        System.out.println("\n------- 选择合适的构造器来初始化对象 ----------");
        Constructor constructor =  c.getConstructor(new Class[]{String.class});
        ReflectUser reflectUser = (ReflectUser)constructor.newInstance("JJJJJ");
        Field fieldName = c.getDeclaredField("privateName");
        fieldName.setAccessible(true);
        System.out.println(fieldName.get(reflectUser));

        //获取修饰符信息
        System.out.println("\n-------- 获取修饰符信息 ----------");
        System.out.println(c.getModifiers());
        System.out.println(Modifier.isPublic(c.getModifiers()));
        System.out.println(Modifier.isStrict(c.getModifiers()));

        //获取包信息
        System.out.println("\n-------- 获取包信息 ----------");
        System.out.println(c.getPackage().toString());

        //获取接口信息
        System.out.println("\n--------- 获取接口信息 -----------");
        Class[] interfeces = c.getInterfaces();
        for(Class i : interfeces){
            System.out.println(i.getName());
        }




    }


    /**
     * 获取父类私有属性
     * @param sonClass
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchFieldException
     */
    public static void getSuperFields(Class sonClass) throws IllegalAccessException,InstantiationException,NoSuchFieldException{

        Class superClass = sonClass.getSuperclass();

        //父类信息
        //规范名
        System.out.println("\ngetCanonicalName : "+superClass.getCanonicalName());
        //简单名
        System.out.println("\ngetSimpleName : "+superClass.getSimpleName());

        ReflectUserSuper reflectUserSuper = (ReflectUserSuper)superClass.newInstance();
        Field field = superClass.getDeclaredField("superPrivate");
        field.setAccessible(true);
        field.set(reflectUserSuper,15);
        System.out.println("\n"+field.get(reflectUserSuper));
    }

    /**
     * invoke方法
     * @throws Exception
     */
    public static void testInvoke() throws Exception{
        Class c = ReflectUser.class;

        Constructor constructor = c.getConstructor();
        Method m1 = c.getMethod("publicStatic",new Class[]{String.class,Integer.class});
        Method m2 = c.getMethod("publicMethod");
        Method m3 = c.getDeclaredMethod("privateMethod",String.class);

        System.out.println("\n-------类方法invoke，无需传入对象，参数以 null 代替。如果有多参数的情况下，以数组代替----------");
        m1.invoke(null,new Object[]{"johann",18});
        System.out.println("\n-------成员方法invoke，需要传入实例对象，无参数的话，保持空参数即可----------");
        m2.invoke(constructor.newInstance());
        System.out.println("\n-------成员方法invoke，需要传入实例对象，单个参数，传入对应的参数即可--------");
        //启用和禁用访问安全检查的开关，值为 true，则表示反射的对象在使用时应该取消 java 语言的访问检查；反之不取消
        m3.setAccessible(true);
        m3.invoke(constructor.newInstance(),"Jessie");
    }

    /**
     * 校验当前类中有没有 Getter,Setter方法
     * @throws Exception
     */
    public static void testGetterAndSetter() throws Exception{
        Class c = ReflectUser.class;

        Method[] ms = c.getDeclaredMethods();
        System.out.println("\n------- Getter,Setter方法校验 ---------------");
        for (Method m : ms){
            if (isGetter(m)){
                System.out.println(m.getName()+"是Getter方法");
            }
            if (isSetter(m)){
                System.out.println(m.getName()+"是Setter方法");
            }
        }
    }

    /**
     * 检察当前方法，是否是 Setter方法
     *
     * setter方法特征：1，以set开头；2，参数个数为 1
     *
     * @param m
     * @return
     */
    public static boolean isSetter(Method m){
        if (!m.getName().startsWith("set")){
            return false;
        }
        if (m.getParameters().length != 1){
            return false;
        }
        return true;
    }

    /**
     * 检察当前方法，是否是 Getter方法
     *
     * getter方法特征：1，以get开头；2，参数为 0；3，返回值不为 void
     * @param m
     * @return
     */
    public static boolean isGetter(Method m){
        if (!m.getName().startsWith("get")){
            return false;
        }
        if (m.getParameters().length > 0){
            return false;
        }
        if (void.class.equals(m.getReturnType())){
            return false;
        }
        return true;
    }

    /**
     * 获取注解信息
     * @throws Exception
     */
    public static void getAnnotation() throws Exception{
        Class c = ReflectUser.class;
        System.out.println("\n------ 获取注解信息 --------------");
        //Method m = c.getMethod("interfaceMethod");
        Annotation[] annots = c.getAnnotations();
        for (Annotation annot : annots) {
            System.out.println(annot.toString());
        }
    }


    public static void main(String[] args) throws Exception{
        //getClazz();

        Class c2 = ReflectUser.class;
        classCommonMethod(c2);

        getSuperFields(c2);

        testInvoke();

        testGetterAndSetter();

        getAnnotation();
    }

}
