package com.johann.passByValue.deepCopy;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeanUtils;
import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

/**
 * @ClassName: DeepCopyAndShallowCopy
 * @Description: 深拷贝和浅拷贝
 * @Author: Johann
 **/
public class DeepCopyAndShallowCopy {

    /**
    *  浅拷贝（Shallow Copy）：
     *  ①对于数据类型是基本数据类型的成员变量，浅拷贝会直接进行值传递，也就是将该属性值复制一份给新的对象。
     *  因为是两份不同的数据，所以对其中一个对象的该成员变量值进行修改，不会影响另一个对象拷贝得到的数据。
     *  ②对于数据类型是引用数据类型的成员变量，比如说成员变量是某个数组、某个类的对象等，那么浅拷贝会进行引用传递，也就是只是将该成员变量的引用值（内存地址）复制一份给新的对象。
     *  因为实际上两个对象的该成员变量都指向同一个实例。在这种情况下，在一个对象中修改该成员变量会影响到另一个对象的该成员变量值。
     *
     *
     *  深拷贝（Deep Copy）：
     *  首先介绍对象图的概念。设想一下，一个类有一个对象，其成员变量中又有一个对象，该对象指向另一个对象，另一个对象又指向另一个对象，直到一个确定的实例，这就形成了对象图。
     *  那么，对于深拷贝来说，不仅要复制对象的所有基本数据类型的成员变量值，还要为所有引用数据类型的成员变量申请存储空间，并复制每个引用数据类型成员变量所引用的对象，直到该对象可达的所有对象。
     *  也就是说，对象进行深拷贝要对整个对象图进行拷贝！
     *
     *  简单地说，深拷贝对引用数据类型的成员变量的对象图中所有的对象都开辟了内存空间；而浅拷贝只是传递地址指向，新的对象并没有对引用数据类型创建内存空间。
    */
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {

        Subjects sb_orig = new Subjects();
        sb_orig.setSub("001");
        sb_orig.setMath(80);
        sb_orig.setChinese(70);
        sb_orig.setEnglish(60);

        Subjects sb_tgt = new Subjects();
        // apache 下的 BeanUtils.copyProperties(target,source)
        BeanUtils.copyProperties(sb_tgt,sb_orig);
        sb_orig.setSub("003");
        System.out.println(sb_orig);
        System.out.println(sb_tgt);

        CopyPerson cp = CopyPerson.builder()
        .id(1).name("Johann")
        .subject(sb_orig)
        .build();
        CopyPerson cp_tgt = new CopyPerson();

        // apache 下的 BeanUtils.copyProperties(target,source)
        // apache的BeanUtils，要求对象对应的类必须是 public类
        // 浅拷贝
        BeanUtils.copyProperties(cp_tgt,cp);

        // spring 下的 BeanUtils.copyProperties(source,target)
        // 浅拷贝
        //BeanUtils.copyProperties(cp,cp_tgt);

        System.out.println(cp);
        System.out.println(cp_tgt);

        sb_orig.setSub("002");

        System.out.println(cp);
        System.out.println(cp_tgt);

    }
}
