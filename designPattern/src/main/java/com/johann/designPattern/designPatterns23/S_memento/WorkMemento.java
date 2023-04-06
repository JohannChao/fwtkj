package com.johann.designPattern.designPatterns23.S_memento;

/** 备忘录角色（Memento）
 * @ClassName: WorkMemento
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class WorkMemento {
    private String schedule;

    public WorkMemento(String schedule) {
        this.schedule = schedule;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
