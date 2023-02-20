package com.johann.UML;

/** 鸟
 * @ClassName: Bird
 **/
public abstract class Bird extends Animal{
    public String feather;
    public String beak;
    public final Wing wing;
    public Bird(){
        // 组合关系，强聚合关系，翅膀离开大雁无意义。
        // 初始化时，实例化翅膀对象，创建鸟对象时一并创建翅膀对象，它俩生命周期一致。
        wing = new Wing();
    }
    public abstract void layEggs();
}
