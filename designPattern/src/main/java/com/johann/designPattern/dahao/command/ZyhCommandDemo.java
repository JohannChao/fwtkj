package com.johann.designPattern.dahao.command;

/**
 * @ClassName: ZyhCommandDemo
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class ZyhCommandDemo {
    public static void main(String[] args) {
        Chef chef = new Chef();
        OrderCommand beefNoodleCommand = new BeefNoodleCommand(chef);
        OrderCommand porkNoodleCommand = new PorkNoodleCommand(chef);
        OrderCommand chickenNoodleCommand = new ChickenNoodleCommand(chef);
        Waiter waiter = new Waiter();
        Customer customerA2 = new Customer("A2");
        //A2点了牛肉面，猪肉面，鸡肉面，取消了鸡肉面，提交订单
        waiter.addOrder(customerA2,beefNoodleCommand);
        waiter.addOrder(customerA2,porkNoodleCommand);
        waiter.addOrder(customerA2,chickenNoodleCommand);
        waiter.cancelOrder(customerA2,chickenNoodleCommand);
        waiter.submitOrder(customerA2);
        //A2要求重做了猪肉面，提交订单
        waiter.redoOrder(customerA2,porkNoodleCommand);
        waiter.submitOrder(customerA2);
        //A2又点了鸡肉面，又点了鸡肉面，取消了鸡肉面，提交订单
        waiter.addOrder(customerA2,chickenNoodleCommand);
        waiter.addOrder(customerA2,chickenNoodleCommand);
        waiter.cancelOrder(customerA2,chickenNoodleCommand);
        waiter.submitOrder(customerA2);
        //A2提交订单后，又取消了鸡肉面
        waiter.cancelOrder(customerA2,chickenNoodleCommand);

    }
}
