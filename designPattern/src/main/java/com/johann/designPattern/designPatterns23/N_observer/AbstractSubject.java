package com.johann.designPattern.designPatterns23.N_observer;

import java.util.ArrayList;
import java.util.List;

/** 抽象通知者
 * @ClassName: AbstractSubject
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public abstract class AbstractSubject {
    /**
     * 观察者列表
     */
    private final List<IObserver> obs;

    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 通知者行为状态
     */
    protected String subjectState;

    public String getSubjectState() {
        return subjectState;
    }

    public void setSubjectState(String subjectState) {
        this.subjectState = subjectState;
    }

    public AbstractSubject(){
        obs = new ArrayList<>();
    }

    public AbstractSubject(String name){
        this();
        this.name = name;
    }

    /**
     * 添加观察者
     * @param o
     */
    public void addObserver(IObserver o){
        obs.add(o);
    }

    /**
     * 移除观察者
     * @param o
     */
    public void deleteObserver(IObserver o){
        obs.remove(o);
    }

    /**
     * 通知观察者
     * @param o
     */
    public void notifyObserver(Object o){
        obs.forEach(observer -> observer.update(this,o));
    }

}
