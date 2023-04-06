package com.johann.designPattern.designPatterns23.I_composite;

public interface IComponent {
    void add(IComponent component);
    void remove(IComponent component);
    void display();
}
