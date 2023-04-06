package com.johann.designPattern.designPatterns23.U_chainOfResponsibility;

/**请求处理的抽象基类
 * @ClassName: ApprovalHandler
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public abstract class ApprovalHandler {
    /**
     * 下一个处理者
     */
    protected ApprovalHandler nextHandler;

    /**
     * 设置下一个处理者
     * @param nextHandler
     */
    public void setNextHandler(ApprovalHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    /**
     * 审批请求
     * @param request
     */
    public abstract void approval(Request request);
}
