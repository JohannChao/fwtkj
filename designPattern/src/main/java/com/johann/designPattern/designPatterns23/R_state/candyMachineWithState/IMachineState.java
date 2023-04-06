package com.johann.designPattern.designPatterns23.R_state.candyMachineWithState;

/** 糖果机状态
 * @Description:
 * @Param:
 * @return:
 * @Author: Johann
 */
public interface IMachineState {

    String getName();
    /**
     * @Description: 投币
     * @Param: []
     * @return: void
     * @Author: Johann
     */
    void insert();
    /**
     * @Description: 退币
     * @Param: []
     * @return: void
     * @Author: Johann
     */
    void eject();
    /**
     * @Description: 转动曲柄
     * @Param: []
     * @return: void
     * @Author: Johann
     */
    void crankUp();
    /**
     * @Description: 分发糖果
     * @Param: []
     * @return: void
     * @Author: Johann
     */
    void dispense();
}
