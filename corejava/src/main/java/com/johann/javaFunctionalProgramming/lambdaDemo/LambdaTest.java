package com.johann.javaFunctionalProgramming.lambdaDemo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.BinaryOperator;

/**
 * @ClassName: LambdaTest
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class LambdaTest {

    public static int staticNum;
    private int num;

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
        button.addActionListener(e -> {
            System.out.println("button click!");
        });

        int m = 2;
        BinaryOperator<Integer> addOriginal = new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer x, Integer y) {
                // Variable 'm' is accessed from within inner class, needs to be final or effectively final
                // m = 3;
                return x+y+m;
            }
        };

        new LambdaTest().localVariableTest();
        new LambdaTest().ivarCvarTest();
    }

    /**
     * Lambda表达式中 访问局部变量要注意如下 3 点：
     */
    public void localVariableTest() {
        // 1，可以直接在 Lambda 表达式中访问外层的局部变量,但是这个局部变量必须是声明为 final 的或者既成事实上的 final 变量。
        // final int delta = -1;
        int delta = -1;
        BinaryOperator<Integer> add = (x,y) -> {
            // 在 Lambda 表达式中试图修改局部变量是不允许的，报错：Variable used in lambda expression should be final or effectively final
            // delta = 0;
            return x+y+delta;
        };
        System.out.println(add.apply(1,2));

        // 2, 在 Lambda 表达式当中被引用的变量的值不可以被更改
        // 在 Lambda 表达式之后修改局部变量是不允许的，报错：Variable used in lambda expression should be final or effectively final
        // delta = 0;

        // 3, 在 Lambda 表达式当中不允许声明一个与局部变量同名的参数或者局部变量
        // Variable 'delta' is already defined in the scope
        // BinaryOperator<Integer> add2 = (delta,y) -> delta+y+delta;
    }

    /**
     * Lambda 内部对于实例变量和类变量是即可读又可写的。
     */
    public void ivarCvarTest() {
        BinaryOperator<Integer> add = (x, y) -> {
            num = 3;
            staticNum = 4;
            return x + y + num + staticNum;
        };
        Integer apply = add.apply(1, 2);
        System.out.println(apply);
    }
}
