package com.johann.designPattern.dahao.state.candyMachineWithState;

/** 糖果机【context角色】
 * @ClassName: CandyMachine
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class CandyMachine {

    public IMachineState noCoinState = new NoCoinState(this,"未投币");
    public IMachineState inCoinState = new InCoinState(this,"已投币");
    public IMachineState sellingState = new SellingState(this,"售卖糖果中");
    public IMachineState soldOutState = new SoldOutState(this,"糖果售尽");

    public CandyMachine(int candyCount,int coinCount){
        this.candyCount = candyCount;
        this.coinCount = coinCount;
        if (candyCount > 0){
            setState(noCoinState);
        }else {
            setState(soldOutState);
        }
    }

    /** 糖果数量 */
    private int candyCount = 10;

    public int getCandyCount() {
        return candyCount;
    }

    public void setCandyCount(int candyCount) {
        this.candyCount = candyCount;
    }

    /** 糖果机内硬币数量 */
    private int coinCount = 0;

    public int getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    /** 糖果机状态 */
    private IMachineState state;

    public IMachineState getState() {
        return state;
    }

    public void setState(IMachineState state) {
        this.state = state;
    }

    /**
     * 糖果机投币操作
     */
    public void insertCoin(){
        System.out.println("正在投币...");
        coinCount++;
        System.out.println("投币完成！");
    }

    /**
     * 糖果机退币操作
     */
    public void machineRefundCoin(){
        System.out.println("正在退币...");
        coinCount--;
        System.out.println("退币完成！");
    }

    /**
     * 糖果机售出糖果操作
     */
    public void dispenseCandy(){
        System.out.println("糖果正在发放...");
        candyCount--;
        System.out.println("糖果已发放，请取走糖果！");
    }

    public void requestInsert(){
        state.insert();
    }

    public void requestEject(){
        state.eject();
    }

    public void requestCrankUp(){
        state.crankUp();
    }

    public void requestDispense(){
        state.dispense();
    }
}
