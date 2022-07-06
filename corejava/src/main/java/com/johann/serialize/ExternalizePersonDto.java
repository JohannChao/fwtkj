package com.johann.serialize;

import lombok.*;
import java.io.*;

/** 实现 Externalizable 接口，自定义属性字段的序列化
 *
 *  实现 Externalizable 接口的类，一定要有无参构造器
 *  （返回给定类的公共无参数构造函数，如果没有找到，则返回 null）
 *  @see java.io.ObjectStreamClass#getExternalizableConstructor(Class)
 *
 *
 * @ClassName: ExternalizePersonDto
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("all")
public class ExternalizePersonDto implements Externalizable {

    private static final long serialVersionUID = 6437314345140435668L;

    private Integer id;

    /**transient 关键字修饰的字段，不会被序列化**/
    private transient String name;

    private Integer sex;

    private String address;

    /**static 关键字修饰的字段，不会被序列化**/
    public static Integer CLASS_TYPE = 1;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(id);
        out.writeObject(name);
        out.writeObject(address);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        id = (Integer) in.readObject();
        name = (String) in.readObject();
        address = (String) in.readObject();
    }
}
