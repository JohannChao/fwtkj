package com.johann.dynamicBoundAndStaticBound;

import java.io.File;
import java.lang.reflect.Field;

/**
 * 静态绑定,是指在编译时刻就确定对象的实际类型【重载是静态绑定】
 * java中的静态绑定是通过方法的重载实现的,即在同一个类中定义多个同名的方法,在编译时,会根据参数的类型调用相应的方法
 * 重载的方法【静态绑定的方法】调用是在编译时完成的,不会根据参数的实际类型调用相应的方法
 * 要想在静态绑定的方法中调用动态绑定的方法,需要使用instanceof关键字
 * @author Johann
 * @version 1.0
 * @see
 **/
public class StaticBound {
    /**
     * 获取对象的引用的类型，而不是对象的实际类型.即使是父类的引用指向子类的对象,也是获取父类的类型。
     * 局部变量不好获取类型,因为局部变量在栈中,而不是在堆中，所以此时将对象设置为实例变量。
     * * 类变量（静态变量）：独立于方法之外的变量，使用 static 关键字修饰。被存放在JVM的方法区。
     * * 实例变量（成员变量）：独立于方法之外的变量，无 static 关键字修饰。存放于JVM的堆内存中。
     * * 局部变量：存在于方法体内。存放于栈内存中。
     */
    private StaticFather father = new StaticFather();
    private StaticFather son = new StaticSon();
    private StaticFather daughter = new StaticDaughter();

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        StaticBound staticBound = StaticBound.class.newInstance();
        Field[] fields = staticBound.getClass().getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getType().getSimpleName());
        }
        //运行结果:
        //StaticFather
        //StaticFather
        //StaticFather

        StaticVisitor staticVisitor = new StaticVisitor();
        staticVisitor.print(staticBound.father);
        staticVisitor.print(staticBound.son);
        staticVisitor.print(staticBound.daughter);
        //运行结果:
        //StaticFather: StaticFather say
        //StaticSon: StaticFather say
        //StaticDaughter: StaticFather say
    }



}

class StaticFather {
}

class StaticSon extends StaticFather {
}

class StaticDaughter extends StaticFather {
}

class StaticVisitor{
    public void print(StaticFather staticFather){
        System.out.println(staticFather.getClass().getSimpleName()+": StaticFather say");

        //要想在静态绑定的方法中调用动态绑定的方法,需要使用instanceof关键字
//        if (staticFather instanceof StaticSon) {
//            print((StaticSon) staticFather);
//        } else if (staticFather instanceof StaticDaughter) {
//            print((StaticDaughter) staticFather);
//        } else if (staticFather instanceof StaticFather) {
//            print(staticFather);
//        }
    }
    //overload
    public void print(StaticSon staticSon){
        System.out.println(staticSon.getClass().getSimpleName()+": StaticSon say");
    }
    //overload
    public void print(StaticDaughter staticDaughter){
        System.out.println(staticDaughter.getClass().getSimpleName()+": StaticDaughter say");
    }
}