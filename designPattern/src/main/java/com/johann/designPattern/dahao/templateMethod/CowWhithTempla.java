package com.johann.designPattern.dahao.templateMethod;

/**
 * @ClassName: CowWhithTempla
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class CowWhithTempla extends AbstractAnimal{

    public CowWhithTempla(){
        this.name = "奶牛";
    }

    /**
     *
     */
    @Override
    public String eat() {
        return "青草";
    }

    /**
     *
     */
    @Override
    public String product() {
        return "牛奶";
    }
}
