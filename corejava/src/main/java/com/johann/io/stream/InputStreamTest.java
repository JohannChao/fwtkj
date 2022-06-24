package com.johann.io.stream;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName: InputStreamTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class InputStreamTest {

    public static void testMethod() throws IOException {
//        InputStream inputStream1 = new FileInputStream(FileDescriptor.in);
//        inputStream1.read();
//        inputStream1.close();

        File source = new File("D:\\ioTest\\outputStreamTest\\OutputStreamTest.txt");
        InputStream inputStream = new FileInputStream(source);

        /**
         * FileInputStream 没有提供 mark(),markSupported(),reset()的重写
         */
        //inputStream.mark(5);
        //System.out.println(inputStream.markSupported());

        //System.out.println("inputStream.read() : "+(char)inputStream.read());

        // ABCDEFGHIJK
        byte[] buffer = new byte[5];
        /**
         * 从流中读取 buffer.length 个字节，并将它们存储到缓冲区数组。
         */
        System.out.println("inputStream.read(buffer) : "+inputStream.read(buffer));
        // buffer数组中的数据为 ：ABCDE
        System.out.println(new String(buffer));

        /**
         * 跳过 n 个字节
         */
        //跳过了字节 F
        System.out.println("inputStream.skip(1) : "+inputStream.skip(1));

        /**
         * 从流中读取 len(3) 个字节，并将它们存储到缓冲区数组，从数组的第 off(1) 个索引位置开始存储。
         */
        System.out.println(inputStream.read(buffer,1,3));
        // buffer数组中的数据为 ：AGHIE
        System.out.println(new String(buffer));

        inputStream.close();
    }

    /**
     * 复制文件
     * @throws IOException
     */
    public static void copyFile() throws IOException{
        //ABCDEFGHIJK
        File source = new File("D:\\ioTest\\outputStreamTest\\OutputStreamTest.txt");
        InputStream inputStream = new FileInputStream(source);
        File target = new File("D:\\ioTest\\outputStreamTest\\OutputStreamTestCopy.txt");
        System.out.println("target.createNewFile() : "+target.createNewFile());
        OutputStream outputStream = new FileOutputStream(target);
        //OutputStream outputStream = new FileOutputStream(target,true);

        //创建一个容量为 10 的字节数组，用于存储已经读取的数据
        byte[] buffer = new byte[10];

        int len = -1;
        while((len=inputStream.read(buffer))!= -1){
            System.out.println("读取的数据： "+new String(buffer,0,len));
            //将读取的数据写入
            outputStream.write(buffer,0,len);
            //使用write(buffer)，最终复制的结果将是： ABCDEFGHIJKBCDEFGHIJ
            //outputStream.write(buffer);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }


    /**
     * GBK   采用双字节表示，ASCII字母使用1 字节储存，其他字符包括希腊字母均使用2 字节储存。
     * UTF-8 使用可变长度字节来储存 Unicode字符，例如ASCII字母继续使用1 字节储存，重音文字、希腊字母或西里尔字母等使用2 字节来储存，而常用的汉字就要使用3 字节。辅助平面字符则使用4 字节。
     */
    public static void testEncode() throws Exception{
        String s = "ABC中华人民共和国万岁,世界大团结万岁。α";
        byte[] bs = s.getBytes("GBK");
        //byte[] bs = s.getBytes(StandardCharsets.UTF_8);
        //byte[] bs = s.getBytes(StandardCharsets.US_ASCII);
        System.out.println(bs.length);

        String s1 = new String(bs, Charset.forName("GBK"));
        //String s1 = new String(bs,StandardCharsets.UTF_8);
        //String s1 = new String(bs,StandardCharsets.US_ASCII);
        System.out.println(s1);

        char c = 210;
        System.out.println(c);
        System.out.println(System.getProperties().getProperty("file.encoding"));
    }

    public static void main(String[] args) throws Exception{
        //testMethod();
        //copyFile();
        testEncode();
    }

}
