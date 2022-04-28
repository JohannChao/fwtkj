package com.johann.javaGenerics;

import java.util.ArrayList;
import java.util.List;

/**泛型示例
 * @ClassName: GenericsTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class GenericsTest <E>{

    /**
     * 不使用泛型的情况
     */
    public static void noGenerics(){
        List list = new ArrayList();
        list.add(12);
        list.add("johann");

        for (Object obj : list){
            // 编译期不报错，可以正常编译
            // 运行时报错 ： java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.String
            System.out.println((String)obj);
        }

    }

    /**
     * 使用泛型
     */
    public static void useGenerics(){
        List<Integer> list = new ArrayList<>();
        list.add(12);
        //list.add("johann"); //compiler error 编译期报错
    }

    /**
     * 交换数组元素【不使用泛型】
     * @param array
     * @param pre
     * @param next
     */
    public static void swap(Integer[] array,int pre,int next){
        Integer temp = array[pre];
        array[pre] = array[next];
        array[next] = temp;
    }

    /**泛型方法
     * 使用泛型，可以最大程度的减少代码的冗余重复，提高代码的复用。
     * @param array 参数类型 T
     * @param pre
     * @param next
     * @param <T> 表示定义了一个类型 为 T 的类型，否则没人知道 T 是什么，编译期也不知道
     */
    public static <T> void swapUseGenerics(T[] array,int pre,int next){
        T temp = array[pre];
        array[pre] = array[next];
        array[next] = temp;
    }

    /**
     * 在编译期，我们无法知晓泛型的具体类型，只有在运行期，才会根据实际传入的值，来确定泛型类型
     * @param list
     * @param <E>
     */
    public static <E> void listAdd(List<E> list){
        //add(E) in List cannot be applied to (int)
        //list.add(12);
        E e = list.get(0);
    }

    public static <E> void listAdd(List<E> list,E e){
        list.add(e);
    }

    /**
     * 通配符 ? 和泛型类型 T 还是有区别的，它不能直接作为一个类型名称来使用。只能作为一个泛型类型的占位符使用。
     * @param list
     */
    public void wildcard(List<?> list){
        //Unexpected token
        //? e = list.get(0);
        //E e = list.get(0);

        //不能使用  ? e = list.get(0); 这种方式书写。只能使用 Object老祖宗类作为参数类型来接收
        Object o = list.get(0);
    }

    /**
     * <? extend E> 表示有上限的通配符，能接受其类型和其子类的类型 E 指上边界
     * @param list
     */
    public static void wildcardUpper(List<? extends Father> list){

    }

    /**
     * <？super E> 表示有下限的通配符。也就说能接受指定类型及其父类类型，E 即泛型类型的下边界
     * @param list
     */
    public static void wildcardLower(List<? super Son> list){

    }

    /**
     * 泛型擦除
     */
    public static void genericsErasure() throws Exception{
        List<Integer> list = new ArrayList<>();
        listAdd(list,12);
        //此时禁止添加一个字符串
        //listAdd(list,"str");

        //通过反射机制，可以在一个泛型类型为 Integer 的list中添加一个 字符串元素。编译通过，且运行通过
        System.out.println("通过反射机制，可以在一个泛型类型为 Integer 的list中添加一个 字符串元素。编译通过，且运行通过");
        System.out.println("泛型类型在编译期被擦除");
        list.getClass().getMethod("add", Object.class).invoke(list, "str");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("value = " + list.get(i));
        }

        System.out.println("----- 非泛型的类型是不能通过反射修改类型赋值 ------");

        Father father = new Father();
        father.setAge(44);
        System.out.println("father.age = "+father.getAge());
        // java.lang.NoSuchMethodException: com.johann.javaGenerics.Father.setAge(java.lang.Object)
        father.getClass().getMethod("setAge",Object.class).invoke(father,"48");
        System.out.println("father.age = "+father.getAge());


        // 无法创建泛型数组 Generic array creation
        //List<Integer>[] listArray = new ArrayList<Integer>[]{};
        //可以创建通配符数组
        List<?>[] listArray2 = new ArrayList<?>[]{};
    }





    public static void main(String[] args) throws Exception{
        //noGenerics();

//        Integer[] intArray = {1,2,3,4,5};
//        swap(intArray,0,4);
//        System.out.println(Arrays.toString(intArray));
//
//        String[] strArray = {"aa","bb","cc","dd","ee"};
//        //编译期错误：Found: 'java.lang.String[]', required: 'java.lang.Integer[]'
//        //swap(strArray,0,5);

        List<Father> fatherList = new ArrayList<>();
        List<Son> sonList = new ArrayList<>();
        List<Daughter> daughterList = new ArrayList<>();
        List<Mother> motherList = new ArrayList<>();

        wildcardUpper(fatherList);
        wildcardUpper(sonList);
        wildcardUpper(daughterList);
        //(java.util.List<? extends com.johann.javaGenerics.Father>) in GenericsTest cannot be applied to (java.util.List<com.johann.javaGenerics.Mother>)
        //wildcardUpper(motherList);


        wildcardLower(fatherList);
        wildcardLower(sonList);
        //(java.util.List<? super com.johann.javaGenerics.Son>) in GenericsTest cannot be applied to (java.util.List<com.johann.javaGenerics.Daughter>)
        //wildcardLower(daughterList);
        //wildcardLower(motherList);

        genericsErasure();
    }

}

class Father{
    private Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
class Son extends Father{}
class Daughter extends Father{}
class Mother{}