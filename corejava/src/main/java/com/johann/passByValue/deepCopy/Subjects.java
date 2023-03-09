package com.johann.passByValue.deepCopy;

/**
 * @author Administrator
 */
public class Subjects implements Cloneable{
    private String sub;
    private Integer math;
    private Integer english;
    private Integer chinese;

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public Integer getMath() {
        return math;
    }

    public void setMath(Integer math) {
        this.math = math;
    }

    public Integer getEnglish() {
        return english;
    }

    public void setEnglish(Integer english) {
        this.english = english;
    }

    public Integer getChinese() {
        return chinese;
    }

    public void setChinese(Integer chinese) {
        this.chinese = chinese;
    }

    @Override
    public String toString() {
        return "Subjects{" +
                "sub='" + sub + '\'' +
                ", math=" + math +
                ", english=" + english +
                ", chinese=" + chinese +
                '}';
    }

    @Override
    public Subjects clone() throws CloneNotSupportedException {
        return (Subjects) super.clone();
    }
}
