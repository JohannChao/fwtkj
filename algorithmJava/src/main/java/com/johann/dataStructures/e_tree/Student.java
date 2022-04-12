package com.johann.dataStructures.e_tree;

/**
 * @ClassName: Student
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class Student implements Comparable {

    private String name;

    private Integer age;

    public Student(){

    }
    public Student(String name,Integer age){
        this.name = name;
        this.age = age;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    @Override
    public int compareTo(Object o) {
        if (this.getAge() > ((Student)o).getAge()){
            return 1;
        }else if(this.getAge() < ((Student)o).getAge()){
            return -1;
        }else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
