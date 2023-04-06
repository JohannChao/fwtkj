package com.johann.designPattern.designPatterns23.I_composite;

/**
 * @ClassName: AbstractComponent
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public abstract class AbstractComponent implements IComponent{
    protected String name;

    protected Integer depth;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }
}
