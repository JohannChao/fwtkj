package com.johann.javaReflect.cglibLazyLoad;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/** 需要延迟加载的bean
 * @ClassName: USerBean
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
@Getter
@Setter
public class USerBean {

    private Integer id;
    private String name;
    private String address;
}
