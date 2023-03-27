package com.johann.designPattern.dahao.state;

import com.johann.designPattern.dahao.state.candyMachineWithState.CandyMachine;

/**
 * 不使用状态模式：
 *     需要在各种动作方法下，针对不同的状态，做出差异化处理，此时有大量的  if...else 条件选择语句。
 * 使用状态模式：
 *     定义一个State接口，用于封装与状态相关的行为。
 *     定义N个具体状态子类，每一个状态类代表一种状态，这些状态类实现了State接口，针对不同的行为，以及当前自己的状态，对这些行为进行差异化处理，
 * 处理完成后。将Context的状态改为下一种状态。
 *     定义一个Context类，这个Context中，维护了这些具体状态子类。
 * 【在行为受状态约束的时候使用状态模式，而且状态不超过 5 个。】
 * @ClassName: ZyhStateDemo
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class ZyhStateDemo {
    public static void main(String[] args) {
        CandyMachine machine = new CandyMachine(1,0);
        System.out.println("糖果机初始状态如下");show(machine);
        machine.requestInsert();show(machine);
        machine.requestEject();show(machine);
        machine.requestCrankUp();show(machine);
        machine.requestDispense();show(machine);

        machine.requestInsert();show(machine);
        machine.requestCrankUp();show(machine);
        machine.requestDispense();show(machine);

        machine.requestInsert();show(machine);
        machine.requestCrankUp();show(machine);
        machine.requestDispense();show(machine);
    }
    public static void show(CandyMachine machine){
        System.out.println("状态: 【"+machine.getState().getName()+"】, 当前糖果数: "+machine.getCandyCount()+", 当前硬币数: "+machine.getCoinCount()+"\n");
    }
}
