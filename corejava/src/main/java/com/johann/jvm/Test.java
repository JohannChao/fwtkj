package com.johann.jvm;

public class Test {
    public static void main(String[] args) {
        Color color = new Color("black");
        Animal animal = new Animal(color,"dog");
    }
}
class Animal{
    private Color color;
    private String type;

    public Animal(Color color, String type) {
        this.color = color;
        this.type = type;
    }
}
class Color{
    private String name;

    public Color(String name) {
        this.name = name;
    }
}
