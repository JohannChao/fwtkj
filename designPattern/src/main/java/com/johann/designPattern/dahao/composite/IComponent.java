package com.johann.designPattern.dahao.composite;

public interface IComponent {
    void add(IComponent component);
    void remove(IComponent component);
    void display();
}
