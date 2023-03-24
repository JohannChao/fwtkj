package com.johann.designPattern.dahao.state;

/** 不使用状态模式下的糖果机
 * @ClassName: CandyMachineWithoutStatePattern
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class CandyMachineWithoutStatePattern {

    // 没有投币状态
    private final static int NO_COIN = 0;
    // 已投币状态
    private final static int IN_COIN = 1;
    // 糖果售出状态
    private final static int SELLING = 2;
    // 糖果售尽状态
    private final static int SOLD_OUT = 3;

    /** 糖果机状态 */
    private int state = NO_COIN;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    /** 糖果数量 */
    private int candyCount;

    public int getCandyCount() {
        return candyCount;
    }

    public void setCandyCount(int candyCount) {
        this.candyCount = candyCount;
    }

    /** 糖果机内硬币数量 */
    private int coinCount;

    public int getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    public CandyMachineWithoutStatePattern(int candyCount){
        this.candyCount = candyCount;
        coinCount = 0;
        if(candyCount > 0){
            state = NO_COIN;
        }else {
            state = SOLD_OUT;
        }
    }

    /**
     * 顾客投币
     */
    public void insert(){
        insertCoin();
        if (state == NO_COIN){
            //System.out.println("先前未投币，本次已完成投币！");
            state = IN_COIN;
        } else if (state == IN_COIN) {
            System.out.println("先前已投币，请勿重复投币！");
            System.out.println("本次投币即将退回！");
            machineRefundCoin();
        } else if (state == SELLING) {
            System.out.println("先前已投币，请等待糖果！");
            System.out.println("本次投币即将退回！");
            machineRefundCoin();
        } else if (state == SOLD_OUT) {
            System.out.println("糖果已售尽！");
            System.out.println("本次投币即将退回！");
            machineRefundCoin();
        }

    }

    /**
     * 顾客退币
     */
    public void eject(){
        if (state == NO_COIN){
            System.out.println("先前未投币，无法退币！");
        } else if (state == IN_COIN) {
            System.out.println("先前投币后，请求退币，请等待退币！");
            machineRefundCoin();
            state = NO_COIN;
        } else if (state == SELLING) {
            System.out.println("正在发放糖果，无法退币！");
        } else if (state == SOLD_OUT) {
            System.out.println("糖果已售尽，先前未投币，无法退币！");
        }
    }

    /**
     * 转动曲柄
     */
    public void crankUp(){
        if (state == NO_COIN){
            System.out.println("未投币，转动曲柄之前请先投币！");
        } else if (state == IN_COIN) {
            System.out.println("转动曲柄，准备发放糖果！");
            state = SELLING;
        } else if (state == SELLING) {
            System.out.println("糖果正在发放，请勿重复转动曲柄！");
        } else if (state == SOLD_OUT) {
            System.out.println("糖果已售尽，请勿转动曲柄！");
        }
    }

    /**
     * 发放糖果
     */
    public void dispense(){
        if (state == NO_COIN){
            System.out.println("未投币，无法发放糖果！");
        }else if (state == IN_COIN){
            System.out.println("已投币，需转动曲柄后，才能发放糖果！");
        }else if (state == SELLING){
            if (candyCount > 0){
                dispenseCandy();
                if (candyCount > 0){
                    state = NO_COIN;
                }else {
                    state = SOLD_OUT;
                }
            }else {
                System.out.println("糖果已售尽，无法发送糖果！");
                state = SOLD_OUT;
            }
        } else if (state == SOLD_OUT) {
            System.out.println("糖果已售尽，无法发送糖果！");
        }
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

    public static void main(String[] args) {
        CandyMachineWithoutStatePattern machine = new CandyMachineWithoutStatePattern(10);
        show(machine);
        machine.insert();show(machine);
        //machine.eject();show(machine);
        machine.crankUp();show(machine);
        machine.dispense();show(machine);
    }
    public static void show(CandyMachineWithoutStatePattern machine){
        System.out.println("state: "+machine.getState()+", candyCount: "+machine.getCandyCount()+", coinCount: "+machine.getCoinCount()+"\n");
    }
}
