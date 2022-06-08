package com.johann.javaReflect.cglibProxy.lazyLoaded;

import lombok.Data;

/** 需要延迟加载的bean
 * @ClassName: USerBean
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
@Data
public class USerBean {

    private Integer id;
    private String name;
    private String address;
}
