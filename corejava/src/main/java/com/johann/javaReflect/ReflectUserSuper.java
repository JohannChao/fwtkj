package com.johann.javaReflect;

/**
 * @ClassName: ReflectUserSuper
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class ReflectUserSuper {

    private int superPrivate;

    protected String superProtectedString;

    String superDefaultString;

    public String superPublic;

    public void superPublicMethod(){
        System.out.println("这是一个父类的公开方法");
    }
    private void superPrivateMethod(){
        System.out.println("这是一个父类的公开方法");
    }
}
