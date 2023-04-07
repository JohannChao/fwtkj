package com.johann.designPrinciple;

import java.util.HashMap;
import java.util.Map;

/** 里氏替换原则(Liskov Substitution Principle, LSP)
 * 1. 子类必须完全实现父类的方法。
 * 2. 子类可以有自己的属性和方法。也正因如此，在子类出现的地方，父类未必就可以代替。
 * 3. 覆盖或实现父类的方法时，子类方法的输入参数可以被放大。
 * 4. 覆盖或实现父类的方法时，输出结果可以被缩小。
 * @ClassName: LspDemo
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class LspDemo {
    public static void main(String[] args) {
        LspFather father = new LspFather("Lee");
        LspSon son = new LspSon("Bruce","Lee");
        HashMap hashMap = new HashMap();
        // 【重载时，子类方法的输入参数被放大】父类存在的地方，子类可以替换
        father.paramMethod1(hashMap);
        son.paramMethod1(hashMap);

        // 【重载时，子类方法的输入参数被缩小】父类存在的地方，子类不可以替换
        father.paramMethod2(hashMap);
        son.paramMethod2(hashMap);
    }

}
class LspFather{
    public String familyName;
    //public LspFather(){}
    public LspFather(String familyName){
        this.familyName = familyName;
    }

    public void paramMethod1(HashMap hashMap){
        System.out.println("包含参数的方法，父类方法被执行");
    }
    public void paramMethod2(Map map){
        System.out.println("包含参数的方法，父类方法被执行");
    }

    public Map returnMethod1(HashMap hashMap){
        System.out.println("包含返回值的方法，父类方法被执行");
        return hashMap;
    }
    public HashMap returnMethod2(HashMap hashMap){
        System.out.println("包含返回值的方法，父类方法被执行");
        return hashMap;
    }

}

class LspSon extends LspFather{
    public String givenName;
    public LspSon(String familyName,String givenName){
        // 子类是不继承父类的构造器（构造方法或者构造函数）的，它只是调用（隐式或显式）。
        // 子类会隐式调用父类的无参构造器，若父类没有无参构造器，子类需要显示调用，且在第一行。
        super(familyName);
        this.givenName = givenName;
    }

    /**
     * 覆盖或实现父类的方法时，子类方法的输入参数可以被放大。即，在子类中方法重载时，其参数范围应大于父类。
     */
    //@Override
    public void paramMethod1(Map map){
        System.out.println("子类重载后扩大参数范围：包含参数的方法，子类方法被执行");
    }

    /**
     * 不符合LSP原则的反例，子类方法重载时，子类方法参数范围小于父类
     */
    //@Override
    public void paramMethod2(HashMap hashMap){
        System.out.println("子类重载后缩小参数范围：包含参数的方法，子类方法被执行");
    }

    /**
     * 覆盖或实现父类的方法时，输出结果可以被缩小。
     */
    @Override
    public HashMap returnMethod1(HashMap hashMap){
        System.out.println("子类重载后缩小返回值范围：包含返回值的方法，父类方法被执行");
        return hashMap;
    }

    /**
     * 不符合LSP原则的反例，覆盖或实现父类的方法时，输出结果被扩大，此时编译不通过“clashes with”
     */
//    @Override
//    public Map returnMethod2(HashMap hashMap){
//        System.out.println("子类重载后扩大了返回值范围：包含返回值的方法，父类方法被执行");
//        return hashMap;
//    }
}