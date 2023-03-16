package com.johann.designPattern.dahao.observer;

/** 具体观察者类，学生类
 * @ClassName: ObserverStudent
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class ObserverStudent implements IObserver{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObserverStudent(){

    }

    public ObserverStudent(String name){
        this.name = name;
    }

    /**
     * @param subject
     * @param arg
     */
    @Override
    public void update(AbstractSubject subject, Object arg) {
        System.out.println("通知者："+subject.getName()+"。观察者："+this.name+"。通知行为【"+subject.getSubjectState()+"】，收到通知尽快执行！");
    }
}
