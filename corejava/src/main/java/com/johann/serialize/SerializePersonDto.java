package com.johann.serialize;

import lombok.*;

import java.io.Serializable;

/**序列化对象
 * 采用默认方式：实现 Serializable 接口的类，它的实例化对象中非 static 静态和非 transient 修饰的字段都会被序列化。
 *
 * @ClassName: SerializePersonDto
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
@Getter
@Setter
@ToString
/**无参构造器。如果有 @AllArgsConstructor 或者 @RequiredArgsConstructor 注解，则 java 不会给我们自动生成无参构造器**/
@NoArgsConstructor
/**全参构造器**/
@AllArgsConstructor
/**生成一个包含 "特定参数" 的构造器，特定参数指的是那些有加上 @NonNull 的变量们**/
//@RequiredArgsConstructor
/**自动生成流式 set 值写法，便于快速设定对象值。虽然如此，但是setter 还是必须要写不能省略的，因为 Spring 或是其他框架有很多地方都会用到对象的 getter/setter 对他们取值/赋值**/
@Builder
@SuppressWarnings("all")
public class SerializePersonDto implements Serializable {

    /**序列化版本号**/
    private static final long serialVersionUID = -1289483710465821240L;

    private Integer id;

    private final String finalStr = "昭昭天命";

    /**transient 关键字修饰的字段，不会被序列化**/
    private transient String name;

    private Integer sex;

    /**static 关键字修饰的字段，不会被序列化**/
    public static Integer CLASS_TYPE = 1;

}
