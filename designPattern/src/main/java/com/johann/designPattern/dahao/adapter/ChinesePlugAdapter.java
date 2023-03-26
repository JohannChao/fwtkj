package com.johann.designPattern.dahao.adapter;

/**
 *
 */
public class ChinesePlugAdapter extends ChinesePlug{

    private AmericanPlug usedPlug;

    private ChinesePlugAdapter(String name){
        super(name);
        usedPlug = new AmericanPlug(name);
    }

    @Override
    public void usePlug() {
        usedPlug.usePlug();
    }
}
