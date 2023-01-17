package com.johann.javaFunctionalProgramming.lambdaDemo;

/**Lambda表达式和匿名内部类
 * @ClassName: LambdaAnonymousInnerTest
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class LambdaAnonymousInnerTest {

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
                //System.out.println(instanceVar+" -- "+classVar+" -- "+localVar);
                // 匿名内部类实现的抽象方法的方法体允许调用接口中定义的默认方法，而Lambda表达式的代码块不允许调用接口中定义的默认方法。
                System.out.println("AnonymousInner【 "+defaultWrite()+"】");
                // Static method may be invoked on containing interface class only(静态方法只能在包含接口类上调用)
                //System.out.println("AnonymousInner【 "+Writable.staticWrite()+"】");
            }
        };
        writable.write();
        System.out.println("AnonymousInner instance 【"+writable.defaultWrite()+"】");
        System.out.println(" =========== ");

        // Lambda表达式实现，不会编译出具体的class文件
        Writable writable1 = () -> {
            // Lambda 表达式的this指向的是 创建这个 Lambda 表达式的类对象的实例（Lambda_AnonymousInner_test）
            System.out.println("Lambda write 【 this.getClass() is -- "+this.getClass()+"】");
            //System.out.println(instanceVar+" -- "+classVar+" -- "+localVar);

            // 匿名内部类实现的抽象方法的方法体允许调用接口中定义的默认方法，而Lambda表达式的代码块不允许调用接口中定义的默认方法。
            // Cannot resolve method 'defaultWrite' in 'LambdaAnonymousInner'(无法解析“LambdaAnonymousInner”中的方法“defaultWrite”)
            // System.out.println("Lambda："+defaultWrite());
            //System.out.println("Lambda【"+Writable.staticWrite()+"】");
        };
        writable1.write();
        System.out.println("Lambda instance 【"+writable1.defaultWrite()+"】");
    }

    public static void main(String[] args) {
        new LambdaAnonymousInnerTest().test();
    }
}