package com.johann.designPattern.designPatterns23.P_command;

/** 厨师[Receiver],负责执行命令,制作菜品
 * @ClassName: Chef
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class Chef {
    public void makeBeefNoodle(){
        System.out.println("厨师制作牛肉面");
    }
    public void redoBeefNoodle(){
        System.out.println("厨师接到重做命令：重做牛肉面");
        this.makeBeefNoodle();
    }
//    public void cancelBeefNoodle(OrderCommand orderCommand){
//        System.out.println("厨师接到撤销命令：取消制作牛肉面");
//    }


    public void makeChickenNoodle(){
        System.out.println("厨师制作鸡肉面");
    }
    public void redoChickenNoodle(){
        System.out.println("厨师接到重做命令：重做鸡肉面");
        this.makeChickenNoodle();
    }
//    public void cancelChickenNoodle(){
//        System.out.println("厨师接到撤销命令：取消制作鸡肉面");
//    }


    public void makePorkNoodle(){
        System.out.println("厨师制作猪肉面");
    }
    public void redoPorkNoodle(){
        System.out.println("厨师接到重做命令：重做猪肉面");
        this.makePorkNoodle();
    }
//    public void cancelPorkNoodle(){
//        System.out.println("厨师接到撤销命令：取消制作猪肉面");
//    }

    public void cancelMeal(OrderCommand orderCommand){
        System.out.println("厨师接到撤销命令：取消制作"+orderCommand.toString());
    }

}
