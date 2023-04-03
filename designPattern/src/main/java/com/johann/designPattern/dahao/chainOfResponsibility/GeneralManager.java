package com.johann.designPattern.dahao.chainOfResponsibility;

/** 总经理
 * @ClassName: GeneralManager
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class GeneralManager extends ApprovalHandler {

    private String name;

    public GeneralManager(String name) {
        this.name = name;
    }

    @Override
    public void approval(Request request) {
        if (request.getRequestLevel() <= 3) {
            System.out.println("总经理" + name + "审批通过");
        } else {
            if (nextHandler != null) {
                nextHandler.approval(request);
            } else {
                System.out.println("没有人可以审批");
            }
        }
    }
}
