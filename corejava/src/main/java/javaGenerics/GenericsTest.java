package javaGenerics;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: GenericsTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class GenericsTest {

    public static void main(String[] args) {
        // 不使用泛型
        List list = new ArrayList();
        list.add("abc");
        // 编译正常，不报错
        list.add(new Integer(5));

        for(Object obj : list){
            //运行时，报：类型转换错误异常
            // java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.String
            String str=(String) obj;
        }

        // 使用泛型
        List<String> list1 = new ArrayList<String>();
        list1.add("abc");
        //list1.add(new Integer(5));  //compiler error

        for(String str : list1){
            //no type casting needed, avoids ClassCastException
        }
    }

}
