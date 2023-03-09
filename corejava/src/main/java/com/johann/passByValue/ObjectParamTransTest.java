package com.johann.passByValue;

/**
 * @ClassName ObjectParamTransTest
 *  方法中传入的是引用类型，这个引用类型的数据是存储在堆内的，在栈内只是存储的这个对象的引用。所以在方法内传入的参数，其实只是这个引用的Copy，这两个引用指向同一个对象。
 *  当我们在方法内改变这个参数的时候，实际上改变的是堆中的对象，所以原来的栈内引用没有改变，但是这个引用指向的对象，却改变了。
 * @Author Johann
 **/
public class ObjectParamTransTest {

    public static void trans(Obj obj){
        int tmp = obj.m;
        obj.m = obj.n;
        obj.n = tmp;
        System.out.println("对象交换方法中：Obj.m = "+obj.m+",Obj.n = "+obj.n);
    }

//    public static void transArray(String[] param){
//        String tmp = param[0];
//        param[0] = param[1];
//        param[1] = tmp;
//        System.out.println("数组交换方法中：param[0] = "+param[0]+",param[1] = "+param[1]);
//    }

    public static void main(String[] args){
        Obj obj= new Obj(10,20);
        System.out.println("对象交换方法执行前：Obj.m = "+obj.m+",Obj.n = "+obj.n);
        trans(obj);
        System.out.println("对象交换方法执行后：Obj.m = "+obj.m+",Obj.n = "+obj.n);

//        String[] array = new String[]{"hello world","i love China"};
//        System.out.println("数组交换方法执行前：array[0] = "+array[0]+",array[1] = "+array[1]);
//        transArray(array);
//        System.out.println("数组交换方法执行后：array[0] = "+array[0]+",array[1] = "+array[1]);
    }
}

class Obj{
    Obj(){}
    Obj(int a,int b){
        m = a;
        n = b;
    }
    int m;
    int n;
}
