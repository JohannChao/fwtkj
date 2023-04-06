package com.johann.designPattern.designPatterns23.D_prototype;

import java.io.*;

/**
 * @ClassName: ZyhPrototypeDemo
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class ZyhPrototypeDemo {
    public static void main(String[] args) {
        //instantiationByReference();
        instantiationByClone();
        //deepCopy();
    }

    /**
     * 原型模式中的深复制
     */
    public static void deepCopy(){
        ResumeUpgrade resumeUpgrade = new ResumeUpgrade("Jessie","女","22");
        resumeUpgrade.setWorkExperience(new WorkExperience("2021-2022","xxx公司"));

        ResumeUpgrade resumeUpgrade2 = resumeUpgrade.clone();
        resumeUpgrade2.getWorkExperience().setCompany("yyy公司");

        ResumeUpgrade resumeUpgrade3 = resumeUpgrade.clone();
        resumeUpgrade3.getWorkExperience().setCompany("zzz公司");

        // 此时，三个“ResumeUpgrade”对象的“WorkExperience”字段值，都变成了【2021-2022  zzz公司】
        // 在“ResumeUpgrade”类的Clone方法中，添加一段“clone.workExperience = this.workExperience.clone();”
        // 最终完成深复制。
        resumeUpgrade.show();
        resumeUpgrade2.show();
        resumeUpgrade3.show();
    }

    /**
     * 使用序列化和反序列化完成深复制，此时待复制的对象及其引用类型的属性需要实现 Serializable接口
     * @param o
     * @return
     * @param <T>
     */
    public <T extends Serializable> T deepCopy(T o){
        T copy = null;
        try {
            //序列化对象
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(o);

            //反序列化对象
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            copy = (T) ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return copy;
    }


    /**
     * 复制引用，新的对象引用与原来的对象引用指向同一个真实实例
     */
    public static void instantiationByReference(){
        Resume resume = new Resume("Jessie");
        resume.setPersonalInfo("女","22");
        resume.setWorkExperience("2021-2022","xxx公司");

        Resume resume2 = resume;
        Resume resume3 = resume;

        // 通过引用传递，此时所有的对象引用指向的是同一个真实实例
        resume.show();
        System.out.println(resume);
        resume2.show();
        System.out.println(resume2);
        resume3.show();
        System.out.println(resume3);
    }

    /**
     * 通过克隆，此时所有的对象引用指向的是不同的真实实例
     */
    public static void instantiationByClone(){
        Resume resume = new Resume("Johann");
        resume.setPersonalInfo("男","23");
        resume.setWorkExperience("2021-2022","xxx公司");

        Resume resume2 = resume.clone();
        Resume resume3 = resume.clone();

        // 通过克隆，此时所有的对象引用指向的是不同的真实实例
        resume.show();
        System.out.println(resume);
        resume2.show();
        System.out.println(resume2);
        resume3.show();
        System.out.println(resume3);
    }
}


