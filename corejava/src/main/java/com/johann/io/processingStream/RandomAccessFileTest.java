package com.johann.io.processingStream;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * @ClassName: RandomAccessFileTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
@Slf4j
public class RandomAccessFileTest {

    static String directory = "D:"+ File.separator+"ioTest"+File.separator+"randomAccessTest";
    static String fileName = "randomAccessFileTest.txt";

    public static void readWriteInOrder() throws Exception{
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(directory,fileName),"r");

        log.info(randomAccessFile.readByte()+"");
        log.info(randomAccessFile.getFilePointer()+" getFilePointer()");
        //log.info(randomAccessFile.readInt()+"");

        randomAccessFile.seek(5);
        log.info(randomAccessFile.readUTF()+"");
        log.info(randomAccessFile.readLine());

        randomAccessFile.seek(0);
        log.info(randomAccessFile.readByte()+"");
        log.info(randomAccessFile.readInt()+"");
        log.info(randomAccessFile.readUTF());

        randomAccessFile.close();
    }

    public static void write() throws Exception{

        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(directory,fileName),"rw");
        randomAccessFile.writeByte(65);
        randomAccessFile.writeInt(97);
        randomAccessFile.writeUTF("中国");

        log.info(randomAccessFile.length()+" length()");
        randomAccessFile.close();
    }


    public static void main(String[] args) throws Exception{
        write();
        readWriteInOrder();
    }
}
