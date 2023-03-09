package com.johann.designPattern.dahao.prototype;

/**
 * @ClassName: ResumeUpgrade
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class ResumeUpgrade implements Cloneable{
    private String name;
    private String sex;
    private String age;
    private WorkExperience workExperience;

    public ResumeUpgrade(String name,String sex,String age){
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public void setWorkExperience(WorkExperience workExperience){
        this.workExperience = workExperience;
    }

    public WorkExperience getWorkExperience() {
        return workExperience;
    }

    public void show(){
        System.out.println(this.name+"  "+this.sex+"  "+this.age);
        System.out.println("工作经历："+this.workExperience.getTimeArea()+"  "+this.workExperience.getCompany());
    }


    @Override
    public ResumeUpgrade clone() {
        try {
            ResumeUpgrade clone = (ResumeUpgrade) super.clone();
            clone.workExperience = this.workExperience.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}