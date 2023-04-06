package com.johann.designPattern.designPatterns23.P_command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 服务员[Invoker],负责接收客户的订单,并将订单交给厨师[Receiver]执行
 * @ClassName: Waiter
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class Waiter {
    //订单列表
    private static Map<Customer,List<OrderCommand>> customerOrdersMap = new HashMap<>();
    //重做列表
    private static Map<Customer,List<OrderCommand>> customerRedoMap = new HashMap<>();

    /**
     * @Description: 添加订单
     * @Param: [customer, orderCommand]
     * @return: void
     * @Author: Johann
     */
    public void addOrder(Customer customer,OrderCommand orderCommand){
        if (!customerOrdersMap.containsKey(customer)){
            List<OrderCommand> orderCommands = new ArrayList<>();
            orderCommands.add(orderCommand);
            customerOrdersMap.put(customer,orderCommands);
        }else {
            customerOrdersMap.get(customer).add(orderCommand);
        }
        System.out.println(customer.getTableId()+"点了"+orderCommand);
    }

    /**
     * @Description: 提交订单
     * @Param: [customer, orderCommand]
     * @return: void
     * @Author: Johann
     */
    public void cancelOrder(Customer customer,OrderCommand orderCommand){
        if (customerOrdersMap.get(customer).contains(orderCommand)){
            customerOrdersMap.get(customer).remove(orderCommand);
            System.out.println(customer.getTableId()+"取消了"+orderCommand);
            //提交订单之前，厨师不应该知晓此操作
            //orderCommand.undo();
        }else {
            System.out.println(customer.getTableId()+"未提交的订单中，不包含"+orderCommand);
        }
    }

    /**
     * @Description: 重做订单
     * @Param: [customer, orderCommand]
     * @return: void
     * @Author: Johann
     */
    public void redoOrder(Customer customer,OrderCommand orderCommand){
        if (!customerRedoMap.containsKey(customer)) {
            List<OrderCommand> redoOrders = new ArrayList<>();
            redoOrders.add(orderCommand);
            customerRedoMap.put(customer, redoOrders);
        }else {
            customerRedoMap.get(customer).add(orderCommand);
        }
        System.out.println(customer.getTableId()+"要求重做"+orderCommand);
    }

    /**
     * @Description: 提交订单
     * @Param: [customer]
     * @return: void
     * @Author: Johann
     */
    public void submitOrder(Customer customer){
        System.out.println(customer.getTableId()+"提交订单");
        if (customerOrdersMap.containsKey(customer)){
            customerOrdersMap.get(customer).forEach(OrderCommand::execute);
            customerOrdersMap.get(customer).clear();
        }
        if (customerRedoMap.containsKey(customer)) {
            customerRedoMap.get(customer).forEach(OrderCommand::redo);
            customerRedoMap.get(customer).clear();
        }
    }
}
