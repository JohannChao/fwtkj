package com.johann.designPattern.dahao.adapter;

public class AmericanPlug{

    private String name;

    public AmericanPlug() {
    }

    public AmericanPlug(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void usePlug(){
        System.out.println(this.getName()+"使用美标插头");
    }
}
