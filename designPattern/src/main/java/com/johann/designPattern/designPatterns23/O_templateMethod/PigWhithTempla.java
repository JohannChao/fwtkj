package com.johann.designPattern.designPatterns23.O_templateMethod;

/**
 * @ClassName: PigWhithTempla
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class PigWhithTempla extends AbstractAnimal{

    public PigWhithTempla(){
        this.name = "小猪";
    }

    /**
     *
     */
    @Override
    public String eat() {
        return "饲料";
    }

    /**
     *
     */
    @Override
    public String product() {
        return "猪肉";
    }
}
