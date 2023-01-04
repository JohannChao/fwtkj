package com.johann.javaFunctionalProgramming;

import java.util.*;
import java.util.stream.Stream;

/** 集合常规的外部迭代和流操作的内部迭代
 * @ClassName: StreamIterationTest
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class StreamIterationTest {

    /**
     * 外部迭代
     * @param list
     */
    private void externalIteration(List<Integer> list){
        Iterator<Integer> iterator = list.iterator();
        int evenCounter = 0;
        while (iterator.hasNext()){
            if (iterator.next() % 2 == 0) {
                evenCounter++;
            }
        }
        System.out.println("偶数个数: "+evenCounter);
    }

    /**
     * 内部迭代
     * @param list
     */
    private void internalIteration(List<Integer> list) {
        long evenCounter = list.stream().filter(i -> i%2==0).count();
        System.out.println("偶数个数: "+evenCounter);
    }


    /**
     * Stream 对象不是一个新的集合，而是创建新集合的配方。
     * 我们通常对于这种不产生集合的方法叫做 惰性求值方法（intermediate operation），相对应的类似于 count() 这种返回结果的方法我们叫做 及早求值方法（terminal operation）。
     * 我们可以把多个惰性求值方法组合起来形成一个惰性求值链，最后通过及早求值操作返回想要的结果。
     * @param list
     */
    private void streamFilterTest(List<Integer> list){
        Stream<Integer> s1 = list.stream();
        Stream<Integer> s2 = list.stream().filter(i -> i%2==0);
        // 获取到Stream对象后，删除list中的某个元素
        list.remove(1);
        // 此时发现Stream对象和list同步发生变化，这说明 Stream 对象不是一个新的集合
        System.out.println("偶数个数: "+list.stream().filter(i -> i%2==0).count());
        System.out.println("偶数个数: "+s1.filter(i -> i%2==0).count());
        System.out.println("偶数个数: "+s2.count());
    }


    public static void main(String[] args) {
        StreamIterationTest test = new StreamIterationTest();
        List<Integer> list = new ArrayList<Integer>();
        Collections.addAll(list, 1,2,3,4,5,6,7);
        //externalIteration(list);
        //internalIteration(list);
        test.streamFilterTest(list);
    }
}
