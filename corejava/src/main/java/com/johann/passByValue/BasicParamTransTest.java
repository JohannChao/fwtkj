package com.johann.passByValue;

/**
 * @ClassName BasicParamTransTest
 *  方法中传入的参数是基本类型，经过方法对参数处理后，原来的变量值不变。
 *  其实，传入方法内的参数，只是原来变量的一份Copy，即在栈内再次开辟一个空间，用于存储这个传入的参数。在方法内改变参数的值，当然不会影响本体的值。
 * @Author Johann
 **/
public class BasicParamTransTest {

    /**
     * @Author Johann
     * @Description //基本类型 Java只有值传递
     * @Date 14:52 2019-10-8
     * @Param [m, n]
     * @return void
     **/
    public static void basicTest(int m,int n){
        int tmp = m;
        m = n;
        n = tmp;

        System.out.println("基本类型处理结果：m = "+m+",n = "+n);
    }

    public static void main(String[] args){
        int m = 10;
        int n = 20;
        System.out.println("方法执行前：m = "+m+",n = "+n);
        basicTest(m,n);
        System.out.println("方法执行后：m = "+m+",n = "+n);
    }
}
