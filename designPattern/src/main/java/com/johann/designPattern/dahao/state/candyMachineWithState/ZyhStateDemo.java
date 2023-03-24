package com.johann.designPattern.dahao.state.candyMachineWithState;

/**
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
