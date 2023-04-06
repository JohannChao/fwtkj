package com.johann.designPattern.designPatterns23.U_chainOfResponsibility;

/** 员工
 * @ClassName: Employee
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class Employee extends ApprovalHandler {

    private String name;

    public Employee(String name) {
        this.name = name;
    }

    @Override
    public void approval(Request request) {
        if (request.getRequestLevel() <= 1) {
            System.out.println("员工" + name + "审批通过");
        } else {
            if (nextHandler != null) {
                nextHandler.approval(request);
            } else {
                System.out.println("没有人可以审批");
            }
        }
    }
}
