package com.johann.passByValue.deepCopy;

import lombok.*;

/**
 * @author Administrator
 */
@Setter
@Getter
@NoArgsConstructor // 无参构造器。如果有 @AllArgsConstructor 或者 @RequiredArgsConstructor 注解，则 java 不会给我们自动生成无参构造器
@AllArgsConstructor // 全参构造器
//@RequiredArgsConstructor // 生成一个包含 "特定参数" 的构造器，特定参数指的是那些有加上 final 修饰词的变量们
@ToString
@EqualsAndHashCode(exclude = "english")
// @Data 整合包，等于这些之和：@Setter @Getter @EqualsAndHashCode @RequiredArgsConstructor
// @Value 整合包，几乎等于 @Data. 有一点不同是，它会把所有的变量都设成 final 的，适合加在值不希望被改变的类上
@Builder // 自动生成流式 set 值写法，便于快速设定对象值。虽然如此，但是setter 还是必须要写不能省略的，因为 Spring 或是其他框架有很多地方都会用到对象的 getter/setter 对他们取值/赋值。
// 自动生成该类的 log 静态常量，要打日志就可以直接打，不用再手动 new log 静态常量了。还支持其他日志框架
//@Slf4j
//@Log
//@Log4j
public class CopyPerson implements Cloneable{
    private Integer id;
    private String name;
    private Subjects subject;

    @Override
    public CopyPerson clone() throws CloneNotSupportedException {
        CopyPerson cp = (CopyPerson) super.clone();
        cp.setSubject(this.subject.clone());
        return cp;
    }
}
