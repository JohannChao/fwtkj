package com.johann.designPrinciple;

import java.util.ArrayList;
import java.util.List;

/** 迪米特法则（Law of Demeter, LOD）又叫作最少知识原则（The Least Knowledge Principle）。
 * 1. 只和直接的朋友交流；
 * 2. 减少对朋友的了解。
 * @ClassName: LodDemo
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class LodDemo {

    public static void main(String[] args) {
        lod1();
        lod2();
    }

    /**
     * 1, 只和直接的朋友交流
     */
    public static void lod1(){
        List<Student> allStudent = new ArrayList<>();
        allStudent.add(new Student());
        allStudent.add(new Student());
        allStudent.add(new Student());
        allStudent.add(new Student());
        allStudent.add(new Student());
        // 老师类
        ITeacher teacher = new Teacher();
        // 班长
        IGroupLeader groupLeader = new GroupLeader(allStudent);
        teacher.command(groupLeader);
    }

    /**
     * 2, 减少对朋友的了解
     */
    public static void lod2(){
        ICoffeeMachine coffeeMachine = new CoffeeMachine();
        Man man = new Man(coffeeMachine);
        man.makeCoffee();
    }
}

interface ITeacher {
    void command(IGroupLeader groupLeader);
}

class Teacher implements ITeacher{
    /**
     * 直接的朋友：出现在成员变量、方法的输入输出参数中的类就是直接的朋友。
     * Teacher的直接朋友是 IGroupLeader，此时却在该方法中与非直接朋友 Student 产生了关联，违背了迪米特法则
     */
//    @Override
//    public void command(IGroupLeader groupLeader) {
//        // 全班同学
//        List<Student> allStudent = new ArrayList<>();
//        allStudent.add(new Student());
//        allStudent.add(new Student());
//        allStudent.add(new Student());
//        allStudent.add(new Student());
//        allStudent.add(new Student());
//        // 班长清点人数
//        groupLeader.count(allStudent);
//    }

    // 修改代码，使其符合迪米特法则
    public void command(IGroupLeader groupLeader){
        groupLeader.count();
    }

}

interface IGroupLeader {

    // 班长清点人数
    //void count(List<Student> students);

    // 修改代码，使其符合迪米特法则
    void count();
}

/**
 * 班长类
 */
class GroupLeader implements IGroupLeader{
    /**
     * 班长清点人数
     * @param students
     */
//    @Override
//    public void count(List<Student> students) {
//        // 班长清点人数
//        System.out.println("上课的学生人数是: " + students.size());
//    }

    // 修改代码，使其符合迪米特法则
    private List<Student> allStudent;
    public GroupLeader(List<Student> allStudent){
        this.allStudent = allStudent;
    }
    @Override
    public void count() {
        // 班长清点人数
        System.out.println("上课的学生人数是: " + allStudent.size());
    }
}

/**
 * 学生类
 */
class Student {
}

/**
 * 咖啡机抽象接口
 */
interface ICoffeeMachine {

    //加咖啡豆
    //void addCoffeeBean();

    //加水
    //void addWater();

    //制作咖啡
    //void makeCoffee();

    // 修改代码，减少对朋友类的了解，只需要暴露一个制作咖啡的接口即可
    void work();
}
/**
 * 咖啡机实现类
 */
class CoffeeMachine implements ICoffeeMachine{

//    //加咖啡豆
//    public void addCoffeeBean() {
//        System.out.println("放咖啡豆");
//    }
//    //加水
//    public void addWater() {
//        System.out.println("加水");
//    }
//    //制作咖啡
//    public void makeCoffee() {
//        System.out.println("制作咖啡");
//    }

    // 修改代码，将咖啡的具体制作步骤设为私有，只暴露一个制作咖啡的接口即可
    //加咖啡豆
    private void addCoffeeBean() {
        System.out.println("放咖啡豆");
    }
    //加水
    private void addWater() {
        System.out.println("加水");
    }
    //制作咖啡
    private void makeCoffee() {
        System.out.println("制作咖啡");
    }

    public void work(){
        addCoffeeBean();
        addWater();
        makeCoffee();
    }
}
/**
 * 人制作咖啡
 */
class Man {
    private ICoffeeMachine coffeeMachine;

    public Man(ICoffeeMachine coffeeMachine) {
        this.coffeeMachine = coffeeMachine;
    }

    /**
     * 制作咖啡的时候，Man对CoffeeMachine了解的太多了，其实并不需要关注咖啡的具体制作步骤。
     * 且当咖啡的制作步骤发生变更时，Man中制作咖啡的步骤也需要做相应变更，此时违背了迪米特法则。
     */
    public void makeCoffee() {
//        coffeeMachine.addWater();
//        coffeeMachine.addCoffeeBean();
//        coffeeMachine.makeCoffee();

        coffeeMachine.work();
    }
}


