package com.johann.javaFunctionalProgramming.utilFunction;

import java.util.function.Predicate;

/** 条件判断并返回一个Boolean值,包含一个抽象方法 (test) 和常见的三种逻辑关系 与 (and) 、或 (or) 、非 (negate) 的默认方法。
 * Predicate 接口通过不同的逻辑组合能够满足我们常用的逻辑判断的使用场景。
 * @ClassName: PredicateTest
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class PredicateTest {

    private static void doTest(Predicate<String> predicate,String str){
        System.out.println("字符串长度超过指定长度了吗？ " + predicate.test(str));
    }
    private static void doNegate(Predicate<String> predicate,String str){
        System.out.println("字符串长度没有超过指定长度，是吧？ " + predicate.negate().test(str));
    }
    private static boolean doAnd(Predicate<String> pre1, Predicate<String> pre2,String str) {
        return pre1.and(pre2).test(str);
    }
    private static boolean doOr(Predicate<String> pre1, Predicate<String> pre2,String str) {
        return pre1.or(pre2).test(str);
    }

    public static void main(String[] args) {

        String str = "Hello,world!";

//        doTest(new Predicate<String>() {
//            @Override
//            public boolean test(String s) {
//                return s.length() > 5;
//            }
//        },str);
        doTest((s -> s.length() > 5),str);

//        doNegate(new Predicate<String>() {
//            @Override
//            public boolean test(String s) {
//                return s.length() > 5;
//            }
//        },str);
        doNegate((s -> s.length() > 5),str);

//        doAnd(new Predicate<String>() {
//            @Override
//            public boolean test(String s) {
//                return s.contains("H");
//            }
//        }, new Predicate<String>() {
//            @Override
//            public boolean test(String s) {
//                return s.contains("w");
//            }
//        },str);
        System.out.println("逻辑与： "+doAnd(s -> s.contains("H"), s -> s.contains("w"),str));

//        doOr(new Predicate<String>() {
//            @Override
//            public boolean test(String s) {
//                return s.contains("H");
//            }
//        }, new Predicate<String>() {
//            @Override
//            public boolean test(String s) {
//                return s.contains("w");
//            }
//        },str);
        System.out.println("逻辑或： "+doOr(s -> s.contains("H"), s -> s.contains("w"),str));
    }
}
