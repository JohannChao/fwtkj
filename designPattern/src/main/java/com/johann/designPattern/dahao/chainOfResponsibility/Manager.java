package com.johann.designPattern.dahao.chainOfResponsibility;

/** 经理
 * @ClassName: Manager
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class Manager extends ApprovalHandler {

    private String name;

    public Manager(String name) {
        this.name = name;
    }

    @Override
    public void approval(Request request) {
        if (request.getRequestLevel() <= 2) {
            System.out.println("经理" + name + "审批通过");
        } else {
            if (nextHandler != null) {
                nextHandler.approval(request);
            } else {
                System.out.println("没有人可以审批");
            }
        }
    }
}
