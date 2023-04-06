package com.johann.designPattern.dahao.interpreter;

import java.util.ArrayList;
import java.util.List;

/**
 * 解释器模式(interpreter)，给定一个语言，定义它的文法的一种表示，并定义一个解释器，这个解释器使用该表示来解释语言中的句子。
 *
 * 解释器模式在实际编程中使用较少。这是因为它适用的场景比较特定，主要用于构建特定的领域语言或查询语言等场景，例如SQL解释器、数学公式解释器等。
 * 这些场景虽然在特定领域中非常重要，但在普遍的软件开发中并不是很常见。
 *
 * JAVA 中如果碰到可以用 expression4J 代替。
 * @author Johann
 */
public class SQLInterpreter {
    // 构建语法树，并解释表达式
    public static void main(String[] args) {
        // 构建上下文环境
        Context context = new Context();
        context.addVariable("name");
        context.addVariable("age");

        // 构建语法树
        Expression expression = new OrExpression(
                new TerminalExpression("name"),
                new AndExpression(
                        new TerminalExpression("age"),
                        new TerminalExpression("gender")
                )
        );

        // 解释表达式
        boolean result = expression.interpret(context);

        // 输出结果
        System.out.println(result);
    }
}


// 上下文环境类，用于存储解释器中的变量和方法
class Context {
    // 存储变量
    private List<String> variables = new ArrayList<>();

    // 添加变量
    public void addVariable(String variable) {
        variables.add(variable);
    }

    // 判断变量是否存在
    public boolean hasVariable(String variable) {
        return variables.contains(variable);
    }
}

// 抽象表达式类，定义了解释器的接口
abstract class Expression {
    // 解释器的核心方法，根据上下文环境解释表达式
    public abstract boolean interpret(Context context);
}

// 终结符表达式类，表示一个变量
class TerminalExpression extends Expression {
    private String variable;

    public TerminalExpression(String variable) {
        this.variable = variable;
    }

    // 判断变量是否存在
    public boolean interpret(Context context) {
        return context.hasVariable(variable);
    }
}

// 非终结符表达式类，表示逻辑与
class AndExpression extends Expression {
    private Expression expr1;
    private Expression expr2;

    public AndExpression(Expression expr1, Expression expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    // 判断两个表达式是否都为真
    public boolean interpret(Context context) {
        return expr1.interpret(context) && expr2.interpret(context);
    }
}

// 非终结符表达式类，表示逻辑或
class OrExpression extends Expression {
    private Expression expr1;
    private Expression expr2;

    public OrExpression(Expression expr1, Expression expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    // 判断两个表达式是否有一个为真
    public boolean interpret(Context context) {
        return expr1.interpret(context) || expr2.interpret(context);
    }
}

