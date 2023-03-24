package com.johann.designPattern.dahao.state.candyMachineWithState;

/** 未投币状态
 * @ClassName: NoCoinState
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class NoCoinState implements IMachineState{

    private final CandyMachine machine;

    private String name;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public NoCoinState(CandyMachine machine,String name) {
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
        machine.setState(machine.inCoinState);
    }

    /**
     * @Description: 退币
     * @Param: []
     * @return: void
     * @Author: Johann
     */
    @Override
    public void eject() {
        System.out.println("先前未投币，无法退币！");
    }

    /**
     * @Description: 转动曲柄
     * @Param: []
     * @return: void
     * @Author: Johann
     */
    @Override
    public void crankUp() {
        System.out.println("未投币，转动曲柄之前请先投币！");
    }

    /**
     * @Description: 分发糖果
     * @Param: []
     * @return: void
     * @Author: Johann
     */
    @Override
    public void dispense() {
        System.out.println("未投币，无法发放糖果！");
    }
}
