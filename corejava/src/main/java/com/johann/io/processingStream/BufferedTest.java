package com.johann.io.processingStream;

import com.johann.io.stream.FileWriterReaderTest;
import com.johann.io.stream.InputStreamTest;
import com.johann.io.stream.OutputStreamTest;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/** 缓冲流（包装流）
 * BufferedInputStream
 * BufferedOutputStream
 * BufferedReader
 * BufferedWriter
 *
 * @ClassName: BufferedTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
@Slf4j
@SuppressWarnings("all")
public class BufferedTest {

    static String directory = "D:"+File.separator+"ioTest"+ File.separator+"bufferTest";
    static String fileName = "bufferStream.txt";
    static String fileName2 = "bufferStreamCopy.txt";

    /**
     * @see        InputStreamTest#testRead()
     * @see        FileWriterReaderTest#testRead()
     */
    public static void testRead() throws Exception{
        File source = new File(directory,fileName);
        //source.createNewFile();

        /**
         * <code>BufferedInputStream</code>为另一个输入流添加了功能，即缓冲输入并支持<code>mark</code> 和 <code>reset</code>方法的能力。创建BufferedInputStream时，会创建一个内部缓冲区数组。
         * (默认字节缓冲大小：private static int DEFAULT_BUFFER_SIZE = 8192;)
         * 当读取或跳过流中的字节时，根据需要从包含的输入流中重新填充内部缓冲区，每次填充许多字节。
         * <code>mark</code>操作记住输入流中的一个点，<code>reset</code>操作导致在从包含的输入流中提取新字节之前，重新读取自最近的<code>mark</code>操作以来读取的所有字节。
         */
        InputStream inputStream = new BufferedInputStream(new FileInputStream(source),1024);
        //InputStream inputStream = new FileInputStream(source);

        byte[] buffer = new byte[256];
        int len;
        /**
         * FileInputStream 与 BufferedInputStream 结果对比
         * DEBUG执行，断点在循环上
         * 在循环操作执行的第一遍，即第一次read完成，删除 source 文件中的内容。然后继续执行，此时
         *
         * BufferedInputStream 作为输入流时，读取了 1024 个字节，即总共执行了 4 遍 read()；
         * FileInputStream 作为输入流时，只读取了 256 个字节，即只执行了 1 遍 read()；
         *
         */
        while((len = inputStream.read(buffer,0,buffer.length)) != -1){
            log.debug(len+"");
            log.info(new String(buffer,0,len));
        }
        inputStream.close();
    }

    /**
     * @see        FileWriterReaderTest#testRead()
     */
    public static void testRead2() throws Exception{
        fileName = "bufferReader.txt";
        File source = new File(directory,fileName);

        BufferedReader bufferedReader = new BufferedReader(new FileReader(source),512);

//        log.info((char)bufferedReader.read()+""); ;
//
//        char[] chars = new char[256];
//        int len;
//        while((len = bufferedReader.read(chars)) != -1){
//            log.debug(len+"");
//            log.debug(String.valueOf(chars,0,len));
//        }

        /**
         * 读取一行文本。 一行被认为是由换行符 ('\n')、回车符 ('\r') 或紧跟回车符的换行符('\r\n')中的任何一个终止的。
         *
         * @param      ignoreLF  如果为true，将跳过下一个 '\n'
         *
         * @return   包含行内容的字符串，不包括任何行终止字符，如果已到达流的末尾，则为 null
         */
        //bufferedReader.readLine(false);
        String readLine;
        while((readLine = bufferedReader.readLine())!=null){
            log.debug(readLine.length()+"");
            log.info(readLine);
        }
        bufferedReader.close();
    }


    /**
     * @see        OutputStreamTest#testWrite()
     * @see        FileWriterReaderTest#testWrite()
     */
    public static void testWrite() throws Exception{
        File source = new File(directory,fileName);
        File target = new File(directory,fileName2);
        //target.createNewFile();

        InputStream inputStream = new BufferedInputStream(new FileInputStream(source),1024);
        /**
         * public BufferedOutputStream(OutputStream out) {
         *         this(out, 8192);
         *     }
         */
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(target),2048);

        byte[] buffer = new byte[256];
        int len;
        /**
         * BufferedInputStream 的 write()方法，并不会直接调用底层的写出操作，而是会去校验，当前待写出的字节数量，是否超出BufferedInputStream的内置缓冲数组buf[]上限
         * 只有待写出的字节数超出了内置的缓存数组上限，才会调用 flushBuffer()来执行底层的写出操作
         */
        while((len = inputStream.read(buffer,0,buffer.length)) != -1){
            log.debug(len+"");
            log.info(new String(buffer,0,len));
            outputStream.write(buffer,0,len);
        }

        outputStream.close();
        inputStream.close();
    }

    /**
     * @see        FileWriterReaderTest#testWrite()
     */
    public static void testWrite2() throws Exception{
        fileName = "bufferReader.txt";
        fileName2 = "bufferReaderCopy.txt";
        File source = new File(directory,fileName);
        File target = new File(directory,fileName2);

        BufferedReader bufferedReader = new BufferedReader(new FileReader(source),512);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(target));

        /**
         * 读取一行文本。 一行被认为是由换行符 ('\n')、回车符 ('\r') 或紧跟回车符的换行符('\r\n')中的任何一个终止的。
         *
         * @param      ignoreLF  如果为true，将跳过下一个 '\n'
         *
         * @return   包含行内容的字符串，不包括任何行终止字符，如果已到达流的末尾，则为 null
         */
        //bufferedReader.readLine(false);
        String readLine;
        while((readLine = bufferedReader.readLine())!=null){
            log.debug(readLine.length()+"");
            log.info(readLine);
            bufferedWriter.write(readLine);
            bufferedWriter.newLine();
        }

        bufferedWriter.close();
        bufferedReader.close();
    }

    public static void main(String[] args) throws Exception{
        //testRead();
        //testRead2();
        //testWrite();
        testWrite2();
    }

}
