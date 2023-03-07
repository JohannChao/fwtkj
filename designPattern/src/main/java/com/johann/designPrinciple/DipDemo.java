package com.johann.designPrinciple;

/** 依赖倒置原则（Dependence Inversion Principle, DIP）
 * 1. 模块间的依赖通过抽象发生，实现类之间不直接发生依赖关系，其依赖关系是通过接口或抽象类产生的；
 * 2. 接口或抽象类不依赖于实现类；
 * 3. 实现类依赖接口或抽象类。
 * @ClassName: DipDemo
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class DipDemo {
    public static void main(String[] args) {
        AutoSystem autoSystem = new AutoSystem(new BmwCar());
        autoSystem.runCar();
        autoSystem.turnCar();
        autoSystem.stopCar();
        autoSystem = new AutoSystem(new HondaCar());
        autoSystem.runCar();
        autoSystem.turnCar();
        autoSystem.stopCar();
    }
}
interface ICar
{
    void run();
    void turn();
    void stop();
}
class BmwCar implements ICar {
    public void run() {
        System.out.println("宝马开始启动了");
    }
    public void turn() {
        System.out.println("宝马开始转弯了");
    }
    public void stop() {
        System.out.println("宝马开始停车了");
    }
}
class HondaCar implements ICar {
    public void run() {
        System.out.println("本田开始启动了");
    }
    public void turn() {
        System.out.println("本田开始转弯了");
    }
    public void stop() {
        System.out.println("本田开始停车了");
    }
}
class AutoSystem {
    private ICar icar;
    public AutoSystem(ICar icar) {
        this.icar = icar;
    }
    public void runCar() {
        icar.run();
    }
    public void turnCar() {
        icar.turn();
    }
    public void stopCar() {
        icar.stop();
    }
}