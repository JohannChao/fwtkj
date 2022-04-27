package javaGenerics;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: GenericsTypeTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class GenericsTypeTest<T> {

    private T t;

    public T get() {
        return t;
    }

    public void set(T t) {
        this.t = t;
    }

    public void getType(){
        System.out.println("T的类型为："+t.getClass().getName());
    }

    /**
     * 泛型通配符
     * ? 可以用来代替任意类型
     * @param list
     * @return
     */
    public  List getList(List<?> list){
        return list;
    }

    /**
     * 泛型的上限
     * 上限： 语法（？ extends className）,即只能为 className 或 className 的子类
     * @param list
     * @return
     */
    public List getUpperList(List<? extends Number> list){
        for (Number n:list){
            System.out.println(n);
        }
        return list;
    }

    /**
     * 泛型的下限
     * 下限： 语法（？ super className）,即只能为 className 或 className 的父类
     * @param list
     * @return
     */
    public List getLowerList(List<? super Number> list){
        return list;
    }


    public static void main(String[] args){
        GenericsTypeTest type = new GenericsTypeTest();
        type.set("Johann");
        type.getType();
        type.set(10);
        type.getType();
        System.out.println(type.get());

//        List<String> l1 = new ArrayList<>();
//        type.getList(l1);
//        List<Integer> l2 = new ArrayList<>();
//        type.getList(l2);
//        List<Double> l3 = new ArrayList<>();
//        type.getList(l3);
//        List<Object> l4 = new ArrayList<>();
//        type.getList(l4);
//        List l5 = new ArrayList<>();
//        type.getList(l5);

//        List<Integer> l1 = new ArrayList<Integer>();
//        type.getUpperList(l1);
//        List<Double> l2 = new ArrayList<Double>();
//        type.getUpperList(l2);
//        List<Long> l3 = new ArrayList<Long>();
//        type.getUpperList(l3);
        List<String> str = new ArrayList<String>();
        str.add("s2");
        type.getUpperList(str);



    }
}
