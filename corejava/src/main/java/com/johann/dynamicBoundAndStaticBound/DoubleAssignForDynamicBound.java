package com.johann.dynamicBoundAndStaticBound;

import java.lang.reflect.Field;

/**
 *  双重分派技术
 * 【静态分派】是指在编译期间，根据方法接收者的静态类型来决定具体调用哪个方法，这种分派方式也被称为方法重载。
 * 【动态分派】是指在运行期间，根据方法接收者的实际类型来决定具体调用哪个方法，这种分派方式也被称为方法重写。
 * 【双重分派】是指在运行期间，根据方法接收者的实际类型【动态分派】和方法参数的实际类型【静态分派】来决定具体调用哪个方法，这种分派方式也被称为双重分发。
 * @author Johann
 * @version 1.0
 * @see
 **/
public class DoubleAssignForDynamicBound {
    /**
     * 获取对象的引用的类型，而不是对象的实际类型.即使是父类的引用指向子类的对象,也是获取父类的类型。
     * 局部变量不好获取类型,因为局部变量在栈中,而不是在堆中，所以此时将对象设置为实例变量。
     */
    DoubleAssignFather father = new DoubleAssignFather();
    DoubleAssignFather son = new DoubleAssignSon();
    DoubleAssignFather daughter = new DoubleAssignDaughter();

    public static void main(String[] args) {
        DoubleAssignForDynamicBound doubleAssignForDynamicBound = new DoubleAssignForDynamicBound();
        Field[] fields = doubleAssignForDynamicBound.getClass().getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getType().getSimpleName());
        }
        //运行结果:
        //DoubleAssignFather
        //DoubleAssignFather
        //DoubleAssignFather

        Visitor visitor = new Visitor();
        //accept()方法重写,实现了动态绑定,根据运行时对象的实际类型调用相应的方法,即调用DoubleAssignFather的accept方法（第一次分派,动态绑定）
        doubleAssignForDynamicBound.father.accept(visitor); //执行步骤 1)
        //accept()方法重写,实现了动态绑定,根据运行时对象的实际类型调用相应的方法,即调用的是DoubleAssignSon的accept方法（第一次分派,动态绑定）
        doubleAssignForDynamicBound.son.accept(visitor);
        //accept()方法重写,实现了动态绑定,根据运行时对象的实际类型调用相应的方法,即调用的是DoubleAssignDaughter的accept方法（第一次分派,动态绑定）
        doubleAssignForDynamicBound.daughter.accept(visitor);
        //运行结果:
        //DoubleAssignFather say
        //DoubleAssignSon say
        //DoubleAssignDaughter say
    }
}

class DoubleAssignFather {
    public void accept(Visitor visitor) { //执行步骤 2)
        //visitor.visit()方法重载,实现了静态绑定,根据参数的类型调用相应的方法,编译器知晓this指向的是DoubleAssignFather（第二次分派,静态绑定）
        visitor.visit(this);
    }
}

class DoubleAssignSon extends DoubleAssignFather {
    @Override
    public void accept(Visitor visitor) {
        //visitor.visit()方法重载,实现了静态绑定,根据参数的类型调用相应的方法,编译器知晓this指向的是DoubleAssignFather（第二次分派,静态绑定）
        visitor.visit(this);
    }
}

class DoubleAssignDaughter extends DoubleAssignFather {
    @Override
    public void accept(Visitor visitor) {
        //visitor.visit()方法重载,实现了静态绑定,根据参数的类型调用相应的方法,编译器知晓this指向的是DoubleAssignFather（第二次分派,静态绑定）
        visitor.visit(this);
    }
}

class Visitor{
    public void visit(DoubleAssignFather doubleAssignFather) { //执行步骤 3)
        System.out.println("DoubleAssignFather say");
    }
    public void visit(DoubleAssignSon doubleAssignSon) {
        System.out.println("DoubleAssignSon say");
    }
    public void visit(DoubleAssignDaughter doubleAssignDaughter) {
        System.out.println("DoubleAssignDaughter say");
    }
}
