package com.johann.designPattern.designPatterns23.P_command;

/** 命令接口[Command],定义了命令的执行、撤销、重做方法,由具体的命令实现
 * @Description: 命令接口
 * @Param:
 * @return:
 * @Author: Johann
 */
public interface OrderCommand {
    /**
     * @Description: 执行命令
     * @Param: []
     * @return: void
     * @Author: Johann
     */
    void execute();
    /**
     * @Description: 撤销命令
     * @Param: []
     * @return: void
     * @Author: Johann
     */
    void undo();
    /**
     * @Description: 重做命令
     * @Param: []
     * @return: void
     * @Author: Johann
     */
    void redo();
}
