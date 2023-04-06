package com.johann.designPattern.designPatterns23.I_composite;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Composite
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class Composite extends AbstractComponent{
    private List<IComponent> children;
    private boolean leafFlag = true;

    public Composite(){
        this("",1,true);
    }

    public Composite(String name,Integer depth,boolean leafFlag){
        this.name = name;
        this.leafFlag = leafFlag;
        this.depth = depth;
        if (!leafFlag){
            children = new ArrayList<>();
        }
    }
    /**添加子节点
     * @param component
     */
    @Override
    public void add(IComponent component) {
        if (!leafFlag) {
            children.add(component);
        }else {
            System.out.println("["+this.name+"] is a leaf node, does not support the [add] operation!");
        }
    }

    /**移除子节点
     * @param component
     */
    @Override
    public void remove(IComponent component) {
        if (!leafFlag) {
            children.remove(component);
        }else {
            System.out.println("["+this.name+"] is a leaf node, does not support the [remove] operation!");
        }
    }

    /** 遍历当前节点及其子节点
     * @Description:
     * @Param: []
     * @return: void
     * @Author: Johann
     */
    @Override
    public void display() {
        //显示当前节点的名称
        for (int i = 0; i < this.depth; i++) {
            System.out.print("-");
        }
        System.out.println(this.name);
        if (!leafFlag){
            //遍历子节点
            children.forEach(component -> component.display());
        }
    }
}
