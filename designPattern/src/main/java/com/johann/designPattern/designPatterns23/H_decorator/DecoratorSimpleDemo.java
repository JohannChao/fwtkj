package com.johann.designPattern.designPatterns23.H_decorator;

/** 装饰模式Demo
 * @ClassName: DecoratorSimpleDemo
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class DecoratorSimpleDemo {
    public static void main(String[] args) {
        /**
         * 有一个需求，需要执行一连串功能【功能A——功能B——基础功能】
         */
        Decorator concreteDecoratorA = new ConcreteDecoratorA();
        Decorator concreteDecoratorB = new ConcreteDecoratorB();
        Component component = new ConcreteComponent();

        // 先用【装饰功能B】来包装【基础功能】
        concreteDecoratorB.decorate(component);
        // 再用【装饰功能A】来包装【装饰功能B】
        concreteDecoratorA.decorate(concreteDecoratorB);
        concreteDecoratorA.operation();
    }
}

/**
 * 核心组件接口
 */
interface Component{
    void operation();
}

/**
 * 具体的基础组件，基础组件定义了最基本的功能
 * 如何选择基础组件？
 * 将所有的装饰功能剥离，简化后剩余下来的最基本的功能，这个类可以当做具体组件。
 * 以策略模式中的计算收费举例，打折、满减这些都是在基础收费功能上添加的装饰功能，因此基础收费类可以作为基础组件使用。
 */
class ConcreteComponent implements Component{

    /**
     * 最基本的功能
     */
    @Override
    public void operation() {
        System.out.println("执行基础的功能！");
    }
}

/**
 * 装饰抽象类，所有的具体装饰功能类都需要继承它
 */
abstract class Decorator implements Component{

    protected Component component;

    /**
     * 添加装饰功能组件
     * @param component
     */
    public void decorate(Component component){
        this.component = component;
    }

    public void operation(){
        if (component != null) {
            this.component.operation();
        }
    };
}

/**
 * 具体装饰类A，包含装饰功能A
 */
class ConcreteDecoratorA extends Decorator{

    @Override
    public void operation(){
        String attribute = "装饰功能A！";
        System.out.println("执行"+attribute);
        super.operation();
    }
}

/**
 * 具体装饰类B，包含装饰功能B
 */
class ConcreteDecoratorB extends Decorator{

    private void functionB(){
        System.out.println("执行装饰功能B！");
    }

    @Override
    public void operation(){
        functionB();
        super.operation();
    }
}
