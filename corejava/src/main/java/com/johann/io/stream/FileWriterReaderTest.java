package com.johann.io.stream;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.CharBuffer;

/**
 * @ClassName: FileWriterReaderTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
@Slf4j
@SuppressWarnings("all")
public class FileWriterReaderTest {

    static String directory = "D:"+File.separator+"ioTest"+File.separator+"writerTest";
    static String fileName = "fileWriterTest.txt";
    static String fileName2 = "fileWriterTestCopy.txt";

    /**
     * FileWriter 写出
     * @throws Exception
     */
    public static void testWrite() throws Exception{
        //File dir = new File(directory);
        //dir.mkdirs();
        File target = new File(directory,fileName2);
        //target.createNewFile();

        /**
         * FileWriter(file,append)
         * 如果不添加 append参数，默认为false。即会覆盖掉之前的内容，写入新的内容（报异常的话，新内容不会写入，且旧内容也被擦除）；
         * 如果append置为true，则会在文件内容末尾新增写入的新内容
         */
        //Writer writer = new FileWriter(target,true);
        Writer writer = new FileWriter(target);
        // append()方法内部实现，就是 write()
        writer.append((char) 65);
        writer.append('C');
        writer.append("EFG");
        //写出数字
        writer.write(66);
        //写出String
        writer.write("中华人民共和国万岁！");
        //写出 char[]
        writer.write("人民万岁！".toCharArray());
        //写出String（指定起始位置，指定长度），指定长度可能越界
        writer.write("世界和平123稳定",1,3);
        //写出 char[]（指定起始位置，指定长度），指定长度可能越界
        writer.write("国际共产主义".toCharArray(),1,3);

        //不刷新缓冲区，不会写出到文件中。close()方法本身在关闭流之前会先刷新缓存区
        //writer.flush();
        writer.close();
    }

    /**
     * FileReader 读入
     */
    public static void testRead() throws Exception{
        File source = new File(directory,fileName);

        Reader reader = new FileReader(source);

        int len = -1;
        /**
         * 读取单个字符。 此方法将阻塞，直到字符可用、发生 I/O 错误或到达流的末尾。
         *
         * 打算支持有效的单字符输入的子类应覆盖此方法。
         *
         * 返回：读取的字符，作为 0 到 65535 范围内的整数
         */
//        while((len = reader.read())!=-1){
//            log.info(((char)len)+"");
//        }

        /**
         * 将字符读入数组。 此方法将阻塞，直到某些输入可用、发生 I/O 错误或到达流的末尾。
         *
         * 返回：读取的字符数，如果已到达流的末尾，则为 -1
         */
        char[] chars = new char[16];
        while((len = reader.read(chars))!=-1){
            //如果不使用 len，则本次读取的字符个数如果少于 16 个，则剩余部分的字符会显示上次读取的字符
            log.info(String.copyValueOf(chars,0,len));
            //log.info(String.copyValueOf(chars));
        }
        reader.close();
    }

    /**
     * 复制文件
     * @throws Exception
     */
    public static void copyFile() throws Exception{
        File source = new File(directory,fileName);
        File target = new File(directory,fileName2);

        Reader reader = new FileReader(source);
        Writer writer = new FileWriter(target);
        char[] chars = new char[16];

        int len = -1;
        while ((len = reader.read(chars))!=-1){
            log.info(String.copyValueOf(chars,0,len));
            writer.write(chars,0,len);
        }

        //不刷新缓冲区，不会写出到文件中。close()方法本身在关闭流之前会先刷新缓存区
        //writer.flush();
        writer.close();
        reader.close();
    }


    public static void main(String[] args) throws Exception {
        //testWrite();
        //testRead();
        copyFile();
    }
}
