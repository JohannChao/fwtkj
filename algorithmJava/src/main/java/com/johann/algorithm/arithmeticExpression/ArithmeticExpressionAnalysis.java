package com.johann.algorithm.arithmeticExpression;

import com.johann.dataStructures.b_stack.JohannExtendedStack;

import java.util.Scanner;

/**
 * 算术表达式解析
 * @ClassName: ArithmeticExpressionAnalysis
 * @Description:
 *   前缀表达式，中缀表达式，后缀表达式：以 3+4-5为例
 *     ①、前缀表达式：操作符在操作数的前面，比如 +-543
 * 　　②、中缀表达式：操作符在操作数的中间，这也是人类最容易识别的算术表达式 3+4-5
 * 　　③、后缀表达式：操作符在操作数的后面，比如 34+5-
 * @Author: Johann
 * @Version: 1.0
 **/
public class ArithmeticExpressionAnalysis {

    /**
     * 中缀表达式转换为后缀表达式：
     * 1，准备两个栈，S1用于存储运算符，S2用于存储中间结果
     * 2，从算术表达式左侧开始遍历：
     *     2.1，如果是操作数，直接压入到 S2 中；
     *     2.2，如果是左括号 （，直接压入到 S1 中；
     *     2.3，如果是运算符：
     *         2.3.1，如果此时 S1 是空栈或栈顶元素是左括号 （，则直接压入到 S1中；
     *         2.3.2，如果此时该运算符优先级高于 S1 栈顶元素运算符，则将此运算符直接压入 S1；
     *         2.3.3，如果此时该运算符优先级不高于（低于或者等于） S1 栈顶元素运算符，此时将 S1栈顶运算符弹出，压入到 S2中。
     *                然后，继续重复2.3步骤；
     *     2.4，如果是右括号 ），此时将 S1 栈中元素依次弹出，并压入到 S2 中，直到在 S1 中遇到左括号 （ 为止。此时左右括号抵消
     * 3，算术表达式遍历遍历完成后，若此时 S1 不是空栈，依次将 S1 栈中元素弹出，压入到 S2 中。
     * 4，将 S2 栈中元素，依次弹出，结果的逆序，即是该中缀表达式转换为后缀表达式。
     */
    final static String INFIX_POSTFIX = "InfixConvertToPostfix";

    //运算符栈
    private JohannExtendedStack opeStack;
    //中间结果栈
    private JohannExtendedStack tempStack;

    private char leftParen = '(';
    private char rightParen = ')';
    private char plusSign = '+';
    private char minusSign = '-';
    private char multipleSign = '*';
    private char divisionSign = '/';

    /**
     * 中缀表达式转换为后缀表达式
     * @param str
     * @return
     */
    public JohannExtendedStack InfixConvertToPostfix(String str){

        System.out.println("输入的算术表达式是 ： "+str);

        opeStack = new JohannExtendedStack(str.length());
        tempStack = new JohannExtendedStack(str.length());
        char[] chars = str.toCharArray();

        for (char aChar : chars) {

            System.out.print("运算符栈[opeStack]数据为 ： "+printStack(opeStack));
            System.out.println();
            System.out.print("中间栈[tempStack]数据为 ： "+printStack(tempStack));
            System.out.println();
            System.out.println("此时处理的字符为 ： "+aChar);
            System.out.println("==========");

            switch (aChar){
                case '+':
                case '-':
                    dealOperator(aChar,1,opeStack,tempStack);
                    break;
                case '*':
                case '/':
                    dealOperator(aChar,2,opeStack,tempStack);
                    break;
                case '(':
                    //左括号，直接压入 运算符存储栈
                    opeStack.push(aChar);
                    break;
                case ')':
                    //右括号处理
                    dealRightParen(opeStack,tempStack);
                    break;
                default:
                    //操作数，直接压入 中间栈
                    tempStack.push(aChar);
                    break;
            }
        }
        //中缀表达式遍历遍历完成后，若此时 运算符存储栈 不是空栈，依次将 运算符存储栈 栈中元素弹出，压入到 中间栈 中。
        while (!opeStack.isEmpty()){
            tempStack.push(opeStack.pop());
        }
        return tempStack;
    }

    /**
     * 运算符处理逻辑
     * @param c
     * @param priority
     * @param opeStack
     * @param tempStack
     */
    public void dealOperator(char c,int priority,JohannExtendedStack opeStack,JohannExtendedStack tempStack){
        //如果运算符存储栈为空或栈顶元素是左括号 ( ，该运算符直接压入到 运算符存储栈内
        if (opeStack.isEmpty() || (char)opeStack.peak()==leftParen){
            opeStack.push(c);
        }else {
            char peakChar = (char)opeStack.peak();
            int peakPriority = 1;
            if (peakChar==plusSign && peakChar==minusSign){
                peakPriority = 1;
            }else if (peakChar==multipleSign && peakChar==divisionSign){
                peakPriority = 2;
            }
            //如果该运算符的优先级高于栈顶运算符，该运算符直接压入到 运算符存储栈内
            if (priority > peakPriority){
                opeStack.push(c);
            //如果该运算符的优先级不高于栈顶运算符， 此时，将 运算符存储栈栈顶运算符弹出，压入到中间栈内。然后，继续判断运算符存储栈内其他运算符【此处用的递归调用，也可也采用循环处理】
            }else {
                tempStack.push(opeStack.pop());
                //递归调用
                dealOperator(c,priority,opeStack,tempStack);
            }
        }
    }

    /**
     * 如果是右括号 ），此时将 运算符存储栈 栈中元素依次弹出，并压入到 中间栈 中，直到在 运算符存储栈 中遇到左括号 （ 为止。此时左右括号抵消
     * @param opeStack
     * @param tempStack
     */
    public void dealRightParen(JohannExtendedStack opeStack,JohannExtendedStack tempStack){
        while (!opeStack.isEmpty() && (char)opeStack.peak() != leftParen){
            tempStack.push(opeStack.pop());
        }
        opeStack.pop();
    }

    /**
     * 栈内数据，按照弹出顺序，打印字符串
     * @param stack
     * @return
     */
    public String printStack(JohannExtendedStack stack){
        StringBuilder stackString = new StringBuilder();
        int i = stack.getElementCount()-1;
        while (i >= 0){
            stackString.append(stack.peakAt(i)+" ");
            i--;
        }
        return stackString.toString();
    }

    /**
     * 栈内数据，按照弹出顺序，逆向打印字符串
     * @param stack
     * @return
     */
    public String printStackReverse(JohannExtendedStack stack){
        StringBuilder stackString = new StringBuilder();
        int i = 0;
        while (i <= stack.getElementCount()-1){
            stackString.append(stack.peakAt(i));
            i++;
        }
        return stackString.toString();
    }


    /**
     * 后缀表达式，计算机时如何进行计算的
     *     1，从左向右扫描
     *     2，遇到数字，压入到栈内
     *     3，遇到运算符，弹出栈顶两个数字，并用运算符进行相应计算。计算完成后，将结果压入栈内
     *     4，重复上述步骤，直到抵达后缀表达式最右端
     *
     * @param postfixExpression
     * @return
     */
    public Object calculatePostfixExpression(String postfixExpression){
        char[] chars = postfixExpression.toCharArray();
        JohannExtendedStack stack = new JohannExtendedStack(chars.length);
        for (char aChar : chars){
            //遇到数字，压入到栈内
            if (aChar >= '0' && aChar <= '9'){
                stack.push(aChar-'0');
            //遇到运算符，弹出栈顶两个数字，并用运算符进行相应计算。计算完成后，将结果压入栈内
            }else {
                int i1 = (int)stack.pop();
                int i2 = (int)stack.pop();
                int temp;
                switch (aChar){
                    case '+':
                        temp = i1+i2;
                        break;
                    case '-':
                        temp = i1-i2;
                        break;
                    case '*':
                        temp = i1*i2;
                        break;
                    case '/':
                        temp = i1/i2;
                        break;
                    default:
                        temp = 0;
                        break;
                }//end switch
                stack.push(temp);
            }//end else
        }//end for
        //重复上述步骤，直到抵达后缀表达式最右端。此时，栈顶为最终计算结果
        return stack.pop();
    }


    public static void main(String[] args) {
        //Scanner scanner = new Scanner(System.in);
        //String input = scanner.nextLine();
        //String input = "A+B+C*D+(E+F)*G";
        String input = "2+4+8*6+(3+4)*5";
        ArithmeticExpressionAnalysis deal = new ArithmeticExpressionAnalysis();
        JohannExtendedStack stack = deal.InfixConvertToPostfix(input);
        System.out.println("中缀表达式为 : "+input);
        System.out.println("转换后的结果为 : "+deal.printStack(stack));
        System.out.println("转换后的结果逆序，即正确的后缀表达式为 : "+deal.printStackReverse(stack));
        System.out.println("后缀表达式，运算后的结果是： "+deal.calculatePostfixExpression(deal.printStackReverse(stack)));
    }

}
