package com.johann.io.stream;

import java.io.*;

/**
 * @ClassName: OutputStreamTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
@SuppressWarnings("all")
public class OutputStreamTest {

    public static void testConstructor() throws IOException {
        File target = new File("D:\\ioTest\\outputStreamTest\\OutputStreamTest.txt");
        //创建新的文件
        //target.createNewFile();

        /**
         * FileOutputStream(File file)
         * FileOutputStream(File file, boolean append)
         * FileOutputStream(String name)
         * FileOutputStream(String name, boolean append)
         * 如果 append 值为<code>true</code>，那么字节将被写入文件的末尾而不是开头，即这个字段用于控制写入是否被覆盖
         *
         */
        OutputStream outputStream = new FileOutputStream(target);
        OutputStream outputStream1 = new FileOutputStream("D:\\ioTest\\outputStreamTest\\OutputStreamTest.txt");
        OutputStream outputStream2 = new FileOutputStream(target,true);
        OutputStream outputStream3 = new FileOutputStream("D:\\ioTest\\outputStreamTest\\OutputStreamTest.txt",true);

        /**
         * FileDescriptor，顾名思义是文件描述符，FileDescriptor 可以被用来表示开放文件、开放套接字等
         *
         * FileDescriptor 表示文件来说: 当 FileDescriptor 表示文件时，我们可以通俗的将 FileDescriptor 看成是该文件。
         * 但是，我们不能直接通过 FileDescriptor 对该文件进行操作。
         *
         * 若需要通过 FileDescriptor 对该文件进行操作，则需要新创建 FileDescriptor 对应的 FileOutputStream或者是 FileInputStream，
         * 再对文件进行操作，应用程序不应该创建他们自己的文件描述符
         *
         * FileDescriptor 有三种输出方式：in、out、error，分别代表
         *
         * 一个标准输入流的句柄。通常情况下，这个文件描述符不会直接使用，而是通过称为System.in的输入流。
         * public static final FileDescriptor in = new FileDescriptor(0);
         *
         * 一个标准的输出流句柄。通过 System.out 来使用
         * public static final FileDescriptor out = new FileDescriptor(1);
         *
         * 一个标准的错误流句柄。通过 System.err 来使用
         * public static final FileDescriptor err = new FileDescriptor(2);
         *
         *
         * 我们可以通过如下的方式来创建文件描述符
         *
         *   FileInputStream fileInputStream = new FileInputStream(FileDescriptor.in);
         *   fileInputStream.read();
         *   fileInputStream.close();
         *
         * 这段代码创建了一个标准输入流，让你可以从控制台输入信息，它的作用等同于System.in
         *
         * 类似的，out 和 error 都是文件输出流，你可以按照下面这种方式创建
         *
         *   FileOutputStream out = new FileOutputStream(FileDescriptor.out);
         *   out.write('A');
         *   out.close();
         *
         * 它们用于向控制台输出消息，out 的作用等于 System.out，err 的作用等于 System.err。
         *
         * 因此，我们可以等价的将上面的程序转换为如下代码：System.out.print('A'); System.err.print('A')；
         *
         */
        OutputStream outputStream4 = new FileOutputStream(FileDescriptor.out);
        outputStream4.write('C');
        outputStream4.flush();
        outputStream4.close();
    }


    public static void testWrite(){



    }

    public static void main(String[] args) throws IOException {
        testConstructor();
    }

}
