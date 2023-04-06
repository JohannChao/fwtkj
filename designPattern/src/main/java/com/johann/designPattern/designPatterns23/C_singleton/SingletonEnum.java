package com.johann.designPattern.designPatterns23.C_singleton;

/** 使用enum实现单例模式，可以防止反序列化重新创建新的对象
 * @Description:
 * @Param:
 * @return:
 * @Author: Johann
 */
public enum SingletonEnum {

    /**
     * SingletonEnum的定义利用的enum是一种特殊的class.代码中的第一行INSTANCE会被编译器编译为SingletonEnum本身的一个对象.
     * 当第一次访问SingletonEnum.INSTANCE时会创建该对象, 并且enum变量的创建是线程安全的.
     */
    INSTANCE;
    int value;
    private SingletonEnum() {
        System.out.println("INSTANCE now created!");
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
}
