package com.johann.designPattern.designPatterns23.D_prototype;

/**
 * @ClassName: Resume
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class Resume implements Cloneable{
    private String name;
    private String sex;
    private String age;
    private String timeArea;
    private String company;

    public Resume(String name){
        this.name = name;
    }

    public void setPersonalInfo(String sex,String age){
        this.sex = sex;
        this.age = age;
    }

    public void setWorkExperience(String timeArea,String company){
        this.timeArea = timeArea;
        this.company = company;
    }

    public void show(){
        System.out.println(this.name+"  "+this.sex+"  "+this.age);
        System.out.println("工作经历："+this.timeArea+"  "+this.company);
    }

    @Override
    public Resume clone() {
        try {
            Resume clone = (Resume) super.clone();
            // copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
