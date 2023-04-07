package com.johann.dynamicBoundAndStaticBound;

/**
 * 动态绑定,是指在运行时刻才确定对象的实际类型【重写是动态绑定】
 * java中的动态绑定是通过方法的重写实现的,即在子类中重写父类的方法,在运行时,会根据对象的实际类型调用相应的方法
 * 多态的实现是通过动态绑定实现的,即父类引用指向子类对象,在运行时,会根据对象的实际类型调用相应的方法
 * @author Johann
 * @version 1.0
 * @see
 **/
public class DynamicBound {
    public static void main(String[] args) {
        DynamicFather father = new DynamicFather();
        DynamicFather son = new DynamicSon();
        DynamicFather daughter = new DynamicDaughter();

        father.say();
        son.say();
        daughter.say();
        //运行结果:
        //Father say
        //Son say
        //Daughter say
    }
}

class DynamicFather {
    public void say() {
        System.out.println("DynamicFather say");
    }
}

class DynamicSon extends DynamicFather {
    @Override
    public void say() {
        System.out.println("DynamicSon say");
    }
}

class DynamicDaughter extends DynamicFather {
    @Override
    public void say() {
        System.out.println("DynamicDaughter say");
    }
}
