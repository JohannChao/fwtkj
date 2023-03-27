package com.johann.designPattern.dahao.memento;

import java.util.ArrayList;
import java.util.List;

/** 管理者角色（CareTaker）
 * @ClassName: CareTaker
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class CareTaker {
    List<WorkMemento> mementos;

    public CareTaker() {
        this.mementos = new ArrayList<>();
    }

    /** 通过管理者，存储备忘录
     * @Description:
     * @Param: [memento]
     * @return: void
     * @Author: Johann
     */
    public void addMemento(WorkMemento memento){
        mementos.add(memento);
    }

    /** 通过管理者，获取备忘录
     * @Description:
     * @Param: [idx]
     * @return: WorkMemento
     * @Author: Johann
     */
    public WorkMemento getMemento(int idx){
        if (idx >= mementos.size()){
            return null;
        }
        return mementos.get(idx);
    }
}
