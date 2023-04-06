package com.johann.designPattern.designPatterns23.R_state.candyMachineWithState;

/** 糖果售尽状态
 * @ClassName: SoldOutState
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class SoldOutState implements IMachineState{
    private final CandyMachine machine;

    private String name;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SoldOutState(CandyMachine machine,String name) {
        this.machine = machine;
        this.name = name;
    }

    /**
     * @Description: 投币
     * @Param: []
     * @return: void
     * @Author: Johann
     */
    @Override
    public void insert() {
        machine.insertCoin();
        System.out.println("糖果已售尽！");
        System.out.println("本次投币即将退回！");
        machine.machineRefundCoin();
    }

    /**
     * @Description: 退币
     * @Param: []
     * @return: void
     * @Author: Johann
     */
    @Override
    public void eject() {
        System.out.println("糖果已售尽，先前未投币，无法退币！");
    }

    /**
     * @Description: 转动曲柄
     * @Param: []
     * @return: void
     * @Author: Johann
     */
    @Override
    public void crankUp() {
        System.out.println("糖果已售尽，请勿转动曲柄！");
    }

    /**
     * @Description: 分发糖果
     * @Param: []
     * @return: void
     * @Author: Johann
     */
    @Override
    public void dispense() {
        System.out.println("糖果已售尽，无法发送糖果！");
    }
}
