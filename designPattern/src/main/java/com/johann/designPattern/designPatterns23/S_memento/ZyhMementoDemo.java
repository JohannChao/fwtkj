package com.johann.designPattern.designPatterns23.S_memento;

/** 备忘录模式Demo
 * @ClassName: ZyhMementoDemo
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class ZyhMementoDemo {
    public static void main(String[] args) {
        WorkState workState = new WorkState("进度50%","非必要信息");
        System.out.println("初始状态："+workState.toString());
        // 保存备忘录
        CareTaker careTaker = new CareTaker();
        careTaker.addMemento(workState.saveMemento());
        workState.setSchedule("进度65%");
        workState.setOptional("非必要信息2");
        careTaker.addMemento(workState.saveMemento());

        // 恢复备忘录
        workState.recoveryMemento(careTaker.getMemento(0));
        System.out.println("第一次备忘录信息："+workState.toString());
        workState.recoveryMemento(careTaker.getMemento(1));
        System.out.println("第二次备忘录信息："+workState.toString());
    }
}
