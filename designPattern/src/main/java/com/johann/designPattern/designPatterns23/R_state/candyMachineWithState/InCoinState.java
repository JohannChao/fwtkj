package com.johann.designPattern.designPatterns23.R_state.candyMachineWithState;

/** 已投币状态
 * @ClassName: InCoinState
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class InCoinState implements IMachineState{

    private final CandyMachine machine;

    private String name;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InCoinState(CandyMachine machine, String name) {
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
        System.out.println("先前已投币，请勿重复投币！");
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
        System.out.println("先前投币后，请求退币，请等待退币！");
        machine.machineRefundCoin();
        machine.setState(machine.noCoinState);
    }

    /**
     * @Description: 转动曲柄
     * @Param: []
     * @return: void
     * @Author: Johann
     */
    @Override
    public void crankUp() {
        System.out.println("转动曲柄，准备发放糖果！");
        machine.setState(machine.sellingState);
    }

    /**
     * @Description: 分发糖果
     * @Param: []
     * @return: void
     * @Author: Johann
     */
    @Override
    public void dispense() {
        System.out.println("已投币，需转动曲柄后，才能发放糖果！");
    }
}