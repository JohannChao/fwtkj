package com.johann.dataStructures.b_stack;

/**
 * 利用栈实现字符串逆序
 * @ClassName: StringReversalTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class StringReversalTest {


    public static void main(String[] args) {
        String str = "Hello,World!";
        System.out.println(str);
        System.out.println(stringReversal(str));
    }

    /**
     * 字符串反转
     * @param str
     * @return
     */
    public static String stringReversal(String str){
        char[] chars = str.toCharArray();
        JohannExtendedStack stack = new JohannExtendedStack();
        for (char aChar : chars) {
            stack.push(aChar);
        }
        StringBuilder newStr = new StringBuilder();
        while(!stack.isEmpty()){
            newStr.append(stack.pop());
        }
        return newStr.toString();
    }
}
