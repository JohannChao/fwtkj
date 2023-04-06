package com.johann.designPattern.designPatterns23.S_memento;

/** 发起人角色（Originator）
 * @ClassName: WorkState
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class WorkState {
    private String schedule;

    private String optional;

    public WorkState(String schedule) {
        this.schedule = schedule;
    }

    public WorkState(String schedule, String optional) {
        this.schedule = schedule;
        this.optional = optional;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getOptional() {
        return optional;
    }

    public void setOptional(String optional) {
        this.optional = optional;
    }

    /** 发起者存储备忘录
     * @Description:
     * @Param: []
     * @return: WorkMemento
     * @Author: Johann
     */
    public WorkMemento saveMemento(){
        return new WorkMemento(this.getSchedule());
    }

    /** 从备忘录恢复数据
     * @Description:
     * @Param: [memento]
     * @return: void
     * @Author: Johann
     */
    public void recoveryMemento(WorkMemento memento){
        this.setSchedule(memento.getSchedule());
    }

    @Override
    public String toString() {
        return "WorkState{" +
                "schedule='" + schedule + '\'' +
                ", optional='" + optional + '\'' +
                '}';
    }
}
