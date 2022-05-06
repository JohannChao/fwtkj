package com.johann.javaReflect;

/**
 * @ClassName: ReflectUser
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/

import lombok.Getter;
import lombok.Setter;

/**
 * strictfp 关键字可应用于类、接口或方法。使用 strictfp 关键字声明一个方法时，该方法中所有的float和double表达式都严格遵守FP-strict的限制,符合IEEE-754规范。
 * 当对一个类或接口 使用 strictfp 关键字时，该类中的所有代码，包括嵌套类型中的初始设定值和代码，都将严格地进行计算。
 * 严格约束意味着所有表达式的结果都必须是 IEEE 754 算法对操作数预期的结果，以单精度和双精度格式表示。
 */
@Getter
@Setter
public strictfp class ReflectUser extends ReflectUserSuper implements ReflectUserInterface{

    private String privateName = "Johann";

    protected String protectedString;

    String defaultString;

    public String publicAlias = "小六子";

    public Double publicDouble = Double.MAX_VALUE-1;

    public ReflectUser(){
        super();
    }

    public ReflectUser(String name){
        super();
        this.privateName = name;
    }

    public String getPrivateName() {
        return privateName;
    }

    public void setPrivateName(String privateName) {
        this.privateName = privateName;
    }

    private void privateMethod(String name){
        System.out.println("这是一个私有方法 : "+name);
    }

    public void publicMethod(){
        System.out.println("这是一个公开方法");
    }

    public static void publicStatic(String name,Integer age){
        System.out.println("这是一个公开的类方法 : "+name+"--"+age);
    }

    @Override
    public void interfaceMethod() {
        System.out.println("子类实现接口的方法");
    }
}
