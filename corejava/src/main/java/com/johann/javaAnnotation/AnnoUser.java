package com.johann.javaAnnotation;

import lombok.Data;

import java.util.Date;

/** model类
 * @ClassName: AnnoUser
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
@Data
public class AnnoUser {

    @LabelZh("编号")
    private Integer id;

    @LabelZh("名字")
    private String name;

    @LabelZh("生日")
    @DateFormatter(pattern = "yyyy/MM/dd",timeZone = "Asia/Shanghai")
    private Date birthDay;

    public AnnoUser(){

    }
    public AnnoUser(Integer id,String name,Date birthDay){
        this.id = id;
        this.name = name;
        this.birthDay = birthDay;
    }
}
