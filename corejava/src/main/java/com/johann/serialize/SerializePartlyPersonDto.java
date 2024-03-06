package com.johann.serialize;

import lombok.*;

import java.io.*;

/** 序列化对象，只有部分属性字段会被序列化。注意，当自定义需序列化的字段时，默认方式会失效
 *
 * 实现方式1：通过 ObjectStreamField[] serialPersistentFields 数组来声明类需要序列化的字段
 * @see java.io.ObjectStreamClass#serialPersistentFields
 *
 * 实现方式2：编写私有方法<code>writeObject</code>和<code>readObject</code>，完成指定属性字段的序列化。
 * （该方法会检查，待序列化的对象是否有私有的，无返回值的writeObject()方法。如果有，其会委托该方法进行对象序列化。
 *   该方法会检查，待序列化的对象是否有私有的，readObject()方法。如果有，其会委托该方法进行对象反序列化。）
 * @see java.io.ObjectStreamClass#ObjectStreamClass(Class)
 *
 *
 *
 * @ClassName: SerializePartlyPersonDto
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("all")
public class SerializePartlyPersonDto implements Serializable {

    private static final long serialVersionUID = -5346595827984592609L;

    private Integer id;

    /**transient 关键字修饰的字段，不会被序列化**/
    private transient String name;

    private Integer sex;

    private String address;

    /**static 关键字修饰的字段，不会被序列化**/
    public static Integer CLASS_TYPE = 1;

    /**
     * 自定义ObjectStreamField[] serialPersistentFields 数组来声明类需要序列化的字段
     * 即需要序列化的字段是 id, name, CLASS_TYPE
     *
     * transient 关键字修饰的字段，可以通过在数组中添加该字段，来实现序列化
     * static 关键字修饰的字段，添加到数组中的话，会报错 unmatched serializable field(s) declared
     */
//    private static final ObjectStreamField[] serialPersistentFields
//            = {new ObjectStreamField("name",String.class)};

    /**
     * @see java.io.ObjectStreamClass#ObjectStreamClass(Class)
     * 该方法会检查，待序列化的对象是否有私有的，无返回值的writeObject()方法。如果有，其会委托该方法进行对象序列化。
     *
     *
     * @param out
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        //out.writeObject(id);
        out.defaultWriteObject();
        out.writeObject(name);
    }

    /**
     * @see java.io.ObjectStreamClass#ObjectStreamClass(Class)
     * 该方法会检查，待序列化的对象是否有私有的，readObject()方法。如果有，其会委托该方法进行对象反序列化。
     *
     *
     * @param in
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private void readObject(ObjectInputStream in) throws ClassNotFoundException,IOException{
        //id = (Integer) in.readObject();
        in.defaultReadObject();
        name = (String) in.readObject();
    }

    @Override
    public String toString() {
        return "SerializePartlyPersonDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", address='" + address + '\'' +
                '}';
    }
}
