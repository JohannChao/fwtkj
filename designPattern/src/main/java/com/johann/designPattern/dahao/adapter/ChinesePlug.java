package com.johann.designPattern.dahao.adapter;

public class ChinesePlug{
    private String name;

    public ChinesePlug(String name) {
        this.name = name;
    }

    public ChinesePlug(String name,Plug adapter) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void usePlug(){
        System.out.println("使用国标插头");
    }
}
