package com.johann.serialize;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @ClassName: serializeTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
@Slf4j
public class SerializeTest {

    static String directory = "D:"+File.separator+"ioTest"+File.separator+"serializeTest";
    static String fileName = "SerializePersonDto.txt";

    /**
     * 序列化
     * @throws Exception
     */
    public static void serializeObj(Serializable dto) throws Exception{

        new File(directory).mkdirs();
        new File(directory,fileName).createNewFile();
        log.info("----------序列化开始----------");
        log.info(dto.toString());
        //创建对象输出流
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(directory,fileName)));
        out.writeObject(dto);
        log.info("----------序列化结束----------");
    }

    /**
     * 反序列化
     * @throws Exception
     */
    public static void deSerializeObj() throws Exception{
        log.info("----------反序列化开始----------");
        //创建对象输入流
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(directory,fileName)));
        Serializable dto = (Serializable) in.readObject();
        log.info(dto.toString());
        log.info("----------反序列化结束----------");
    }

    /**
     * 序列化多个对象
     *
     * 序列化 N 个对象时，得到的字节文件并不是单个对象的 N 倍。
     * Java 序列化机制为了节省磁盘空间，具有特定的存储规则，当写入文件的为同一对象时，并不会再将对象的内容进行存储，而只是再次存储一份引用。
     * 反序列化时，恢复引用关系
     *
     * @throws Exception
     */
    public static void serializeMultiObj() throws Exception{
        log.info("----------序列化多个对象开始----------");
        fileName = "SerializeMultiPerson.txt";
        new File(directory,fileName).createNewFile();

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(directory,fileName)));

        //序列化，写出多个对象
        Serializable dto = new SerializePartlyPersonDto(11,"Johann",10,"HB");
        log.info(dto.toString());
        Serializable dto2 = new SerializePartlyPersonDto(12,"Jessie",10,"HB");
        log.info(dto2.toString());
        Serializable dto3 = new SerializePartlyPersonDto(13,"Steven",10,"HB");
        log.info(dto3.toString());
        out.writeObject(dto);
        out.writeObject(dto);
        out.writeObject(dto);
        log.info("----------序列化多个对象结束----------");
    }

    /**
     * 反序列化多个对象
     *
     * @throws Exception
     */
    public static void deSerializeMultiObj() throws Exception{
        log.info("----------反序列化多个对象开始----------");
        fileName = "SerializeMultiPerson.txt";

        ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(directory,fileName)));
        log.info((in.readObject()).toString());
        log.info((in.readObject()).toString());
        log.info((in.readObject()).toString());
        log.info("----------反序列化多个对象结束----------");
    }

    public static void main(String[] args) throws Exception{
        Serializable dto;
        //dto = new SerializePersonDto(11,"Johann",10);
        dto = new SerializePartlyPersonDto(11,"Johann",10,"HB");
        //dto = new ExternalizePersonDto(11,"Johann",10,"HB");
        serializeObj(dto);
        deSerializeObj();

//        serializeMultiObj();
//        deSerializeMultiObj();
    }

}

