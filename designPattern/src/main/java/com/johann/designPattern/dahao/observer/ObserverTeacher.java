package com.johann.designPattern.dahao.observer;

/** 具体观察者类，其他老师类
 * @ClassName: ObserverStudent
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class ObserverTeacher implements IObserver{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObserverTeacher(){

    }

    public ObserverTeacher(String name){
        this.name = name;
    }

    /**
     * @param subject
     * @param arg
     */
    @Override
    public void update(AbstractSubject subject, Object arg) {
        System.out.println("通知者："+subject.getName()+"。观察者："+this.name+"。通知行为【"+subject.getSubjectState()+"】，收到通知请尽快通知本班学生！");
    }
}
