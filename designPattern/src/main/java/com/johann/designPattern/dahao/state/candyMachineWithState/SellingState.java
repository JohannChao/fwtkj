package com.johann.designPattern.dahao.state.candyMachineWithState;

/** 售卖糖果中状态
 * @ClassName: SellingState
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class SellingState implements IMachineState{
    private final CandyMachine machine;

    private String name;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SellingState(CandyMachine machine,String name) {
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
        System.out.println("先前已投币，请等待糖果！");
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
        System.out.println("正在发放糖果，无法退币！");
    }

    /**
     * @Description: 转动曲柄
     * @Param: []
     * @return: void
     * @Author: Johann
     */
    @Override
    public void crankUp() {
        System.out.println("糖果正在发放，请勿重复转动曲柄！");
    }

    /**
     * @Description: 分发糖果
     * @Param: []
     * @return: void
     * @Author: Johann
     */
    @Override
    public void dispense() {
        if (machine.getCandyCount() > 0){
            machine.dispenseCandy();
            if (machine.getCandyCount() > 0){
                machine.setState(machine.noCoinState);
            }else {
                machine.setState(machine.soldOutState);
            }
        }else {
            System.out.println("糖果已售尽，无法发送糖果！");
            machine.setState(machine.soldOutState);
        }
    }
}
