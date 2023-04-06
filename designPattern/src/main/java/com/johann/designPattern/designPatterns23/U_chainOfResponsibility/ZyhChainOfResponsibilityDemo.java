package com.johann.designPattern.designPatterns23.U_chainOfResponsibility;

/** 责任链模式Demo
 * @ClassName: ZyhChainOfResponsibilityDemo
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class ZyhChainOfResponsibilityDemo {
    public static void main(String[] args) {
        //创建请求
        Request request = new Request();
        request.setRequestId("001");
        request.setRequestName("请假");
        request.setRequestType("请假");
        request.setRequestLevel(3);
        request.setRequestContent("请假一天");

        //创建处理者
        ApprovalHandler employee = new Employee("张三");
        ApprovalHandler manager = new Manager("李四");
        ApprovalHandler boss = new GeneralManager("王五");

        //设置责任链
        employee.setNextHandler(manager);
        manager.setNextHandler(boss);

        //提交请求
        employee.approval(request);
    }
}
