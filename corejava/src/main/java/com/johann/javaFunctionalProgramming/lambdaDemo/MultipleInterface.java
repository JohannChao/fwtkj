package com.johann.javaFunctionalProgramming.lambdaDemo;

/** 接口的多重继承，出现接口默认方法冲突时，如何解决
 * @ClassName: MultipleInterface
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class MultipleInterface {

}
/**
 * ColorBox inherits unrelated defaults for color() from types Redbox and Greenbox
 * 类胜于接口：如果在继承链中有声明的方法，那么就可以忽略接口中定义的方法 （这样可以让我们的代码向后兼容）；
 * 子类胜于父类：如果一个接口继承了另外一个接口，而且两个接口都定义了一个默认方法，那么子类中定义的方法将生效；
 * 如果上述两条都不适用，那么子类要么需要实现该方法，要么将该方法声明成抽象方法 （ abstract ）。
 */
class ColorBox implements Redbox,Greenbox{
    @Override
    public String color() {
        return Greenbox.super.color();
    }
}
abstract class AbstractColorBox implements Redbox,Greenbox{
    public abstract String color();
}

interface Redbox{
    default String color(){
        return "red";
    }
}
interface Greenbox{
    default String color(){
        return "green";
    }
}
