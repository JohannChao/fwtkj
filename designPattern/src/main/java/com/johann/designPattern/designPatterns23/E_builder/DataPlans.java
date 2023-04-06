package com.johann.designPattern.designPatterns23.E_builder;

import java.util.ArrayList;
import java.util.List;

/**
 * 手机套餐
 */
public class DataPlans {

    private String planName;

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public DataPlans(){

    }

    public DataPlans(String planName){
        this.planName = planName;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    private List<Item> itemList = new ArrayList<>();

    public void add(Item item){
        itemList.add(item);
    }

    public void show(){
        System.out.println(this.planName+"详情如下：");
        itemList.forEach(item -> System.out.println(item.name+":"+item.value));
    }
}
