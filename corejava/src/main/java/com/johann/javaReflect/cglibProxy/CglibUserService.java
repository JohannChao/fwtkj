package com.johann.javaReflect.cglibProxy;

/** CJLIB委托类
 * @ClassName: CglibUserService
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class CglibUserService {
    public String learn() {
        System.out.println("CglibUserService 学习！！！");
        return "method learn";
    }

    public String work() {
        System.out.println("CglibUserService 工作！！！");
        return "method work";
    }

    public String play() {
        System.out.println("CglibUserService 玩耍！！！");
        return "method play";
    }
}
