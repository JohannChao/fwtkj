package com.johann.javaFunctionalProgramming.jdkFunctionDemo;

import java.util.stream.IntStream;

/** SRP 单一原则
 * @ClassName: SRPDemo
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class SRPDemo {
    /**
     * 质数的数量
     * @param maxNum
     * @return
     */
    public static long countPrimes(int maxNum){
//        return IntStream.range(1,maxNum).filter(new IntPredicate() {
//            @Override
//            public boolean test(int value) {
//                return isPrime(value);
//            }
//        }).count();
        return IntStream.range(1,maxNum).filter(SRPDemo::isPrime).count();
    }

    /**
     * num是否是质数
     * @param num
     * @return
     */
    public static boolean isPrime(int num){
//            return IntStream.range(2,num).allMatch(new IntPredicate() {
//                @Override
//                public boolean test(int value) {
//                    return num%value!=0;
//                }
//            });
        return IntStream.range(2,num).allMatch(value -> num%value!=0);
    }

    public static void main(String ...s){
        System.out.println(countPrimes(100));
    }
}
