package com.johann.designPattern.designPatterns23.D_prototype;

/**
 * @ClassName: WorkExperience
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class WorkExperience implements Cloneable{
    private String timeArea;
    private String company;

    public WorkExperience(){

    }
    public WorkExperience(String timeArea,String company){
        this.timeArea = timeArea;
        this.company = company;
    }

    public String getTimeArea() {
        return timeArea;
    }

    public void setTimeArea(String timeArea) {
        this.timeArea = timeArea;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public WorkExperience clone() {
        try {
            WorkExperience clone = (WorkExperience) super.clone();
            // copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}