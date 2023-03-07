package com.johann.designPrinciple;

/** 接口隔离原则（Interface Segregation Principle, ISP）
 * @ClassName: IspDemo
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class IspDemo {
    public static void main(String[] args){
        IOrderForPortal orderForPortal =Order.getOrderForPortal();
        IOrderForOtherSys orderForOtherSys =Order.getOrderForOtherSys();
        IOrderForAdmin orderForAdmin = Order.getOrderForAdmin();
        System.out.println("Portal门户调用方法:"+orderForPortal.getOrder());
        System.out.println("OtherSys外部系统调用方法:"+orderForOtherSys.insertOrder());
        System.out.println("Admin管理后台调用方法:"+orderForAdmin.getOrder()+";"+orderForAdmin.insertOrder()+";"+orderForAdmin.updateOrder()+";"+orderForAdmin.deleteOrder());
    }
}

interface IOrderForPortal{
    String getOrder();
}
interface IOrderForOtherSys{
    String insertOrder();
    String getOrder();
}

/**
 * 这儿不用接口继承,因为可能出现修改了父接口影响了子接口
 */
interface IOrderForAdmin{ //extendsIOrderForPortal,IOrderForOtherSys
    String deleteOrder();
    String updateOrder();
    String insertOrder();
    String getOrder();
}
class Order implements IOrderForPortal,IOrderForOtherSys,IOrderForAdmin{
    private Order(){
        // 什么都不干,就是为了不让直接 new,防止客户端直接New,然后访问它不需要的方法.
    }
    //返回给Portal
    public static IOrderForPortal getOrderForPortal(){
        return (IOrderForPortal)new Order();
    }
    //返回给OtherSys
    public static IOrderForOtherSys getOrderForOtherSys(){
        return (IOrderForOtherSys)new Order();
    }
    //返回给Admin
    public static IOrderForAdmin getOrderForAdmin(){
        return (IOrderForAdmin)new Order();
    }

    public String getOrder(){
        return "implemented getOrder";
    }
    public String insertOrder(){
        return "implemented insertOrder";
    }
    public String updateOrder(){
        return "implemented updateOrder";
    }
    public String deleteOrder(){
        return "implemented deleteOrder";
    }
}
