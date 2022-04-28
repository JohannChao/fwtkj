package com.johann.a_objectOriented.composition;

class Engine {
    public void start() {}
    public void rev() {}
    public void stop() {}
}

class Wheel {
    public void inflate(int psi) {}
}

class Window {
    public void rollup() {}
    public void rolldown() {}
}

class Door {
    public Window window = new Window();

    public void open() {}
    public void close() {}
}

/**
 * @ClassName: Car
 * @Description: 组合
 * @Author: Johann
 * @Version: 1.0
 **/
public class Car {
    public Engine engine = new Engine();
    public Wheel[] wheel = new Wheel[4];
    public Door left = new Door(), right = new Door();

    public String name = "car";

    public Car() {
        System.out.println("init car,name : "+name);
        for (int i = 0; i < 4; i++) {
            wheel[i] = new Wheel();
        }
    }

    public static void main(String[] args) {
        Car car = new Car();
        car.left.window.rollup();
        car.wheel[0].inflate(72);
        car.engine.start();
    }
}
