# I/O流

### 流的分类

#### 根据流向
I/O流根据流向，分为输入流和输出流。

注意输入流和输出流是相对于程序而言的。

* 输出：把程序(内存)中的内容输出到磁盘、光盘等存储设备中
* 输入：读取外部数据（磁盘、光盘等存储设备的数据）到程序（内存）中

#### 根据传输单位
I/O流根据传输单位，分为字节流和字符流。

传输单位和流向分类，排列组合后组成了 Java I/O流中的四大基流。

1. 字节输入流（InputStream）
2. 字节输出流（OutputStream）
3. 字符输入流（Reader）
4. 字符输出流（Writer）

#### 根据功能
根据功能分为节点流和处理流。

* 节点流：可以从或向一个特定的地方(节点)读写数据。如FileReader.
* 处理流：是对一个已存在的流的连接和封装，通过所封装的流的功能调用实现数据读写。如BufferedReader.处理流的构造方法总是要带一个其他的流对象做参数。一个流对象经过其他流的多次包装，称为流的链接。

#### 操作I/O流的流程
* 1，创建源或目标对象
  - 输入：把文件中的数据流向到程序中，此时文件是 源，程序是目标
  - 输出：把程序中的数据流向到文件中，此时文件是目标，程序是源
* 2，创建 IO 流对象
  - 输入：创建输入流对象
  - 输出：创建输出流对象
* 3，具体的 IO 操作
* 4，关闭资源
  - 输入：输入流的 close() 方法
  - 输出：输出流的 close() 方法
  
注意：程序中打开的文件 IO 资源不属于内存里的资源，垃圾回收机制无法回收该资源。如果不关闭该资源，那么磁盘的文件将一直被程序引用着，不能删除也不能更改。
所以应该手动调用 close() 方法关闭流资源。

### 字节输出流
#### OutputStream
OutputStream是字节输出流的所有类的超类。 输出流接收输出字节并将其发送到某个接收器。
```java
public abstract class OutputStream implements Closeable, Flushable {
      
    /** 
    * 将指定字节写入此输出流。 <code>write</code> 的一般约定是将一个字节写入输出流。 
    * 要写入的字节是参数 <code>b</code> 的低八位。 <code>b</code> 的高 24 位被忽略。
    *  
    * <code>OutputStream</code> 的子类必须提供此方法的实现。
    */ 
    public abstract void write(int b) throws IOException;
    
    /** 
    *  将指定字节数组中的 <code>b.length</code> 字节写入此输出流。 
    *  <code>write(b)</code> 的一般约定是它应该与调用 <code>write(b, 0, b.length)</code> 具有完全相同的效果。
    */
    public void write(byte b[]) throws IOException {
        write(b, 0, b.length);
    }
    
    /** 
    * 从偏移量 <code>off</code> 开始的指定字节数组中写入 <code>len</code> 个字节到此输出流。 
    * <code>write(b, off, len)</code> 的一般约定是数组 <code>b</code> 中的一些字节按顺序写入输出流； 
    * 元素 <code>b[off]</code> 是写入的第一个字节，<code>b[off+len-1]</code> 是此操作写入的最后一个字节。
    * 
    * <code>OutputStream</code> 的 <code>write</code> 方法在对每个字节写出时都要调用 有一个参数的 write 方法(write(b[off + i]);)。 
    * 鼓励子类重写此方法并提供更有效的实现。
    * 
    * 如果 <code>b</code> 为 <code>null</code>，则抛出 <code>NullPointerException</code>。
    * 
    * 如果<code>off</code>为负数，或<code>len</code>为负数，或<code>off+len</code>大于数组<code>b</code的长度 >，然后抛出 <tt>IndexOutOfBoundsException</tt>。
    * 
    * @param  b  字节数组
    * 
    * @param  off  字节数组的起始偏移量
    * 
    * @param  len  要写入的字节数
    * 
    */ 
    public void write(byte b[], int off, int len) throws IOException {
        if (b == null) {
            throw new NullPointerException();
        } else if ((off < 0) || (off > b.length) || (len < 0) ||
                   ((off + len) > b.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return;
        }
        for (int i = 0 ; i < len ; i++) {
            write(b[off + i]);
        }
    }
        
    /** 
    * 刷新此输出流并强制写出任何已缓冲的输出字节。flush的一般约定是，调用它表示，如果之前写入的任何字节已经被输出流的实现缓冲了，那么这些字节应该立即写入它们预期的目的地。
    *  
    * 如果该流的预期目的地是底层操作系统提供的一个抽象，例如一个文件，那么刷新流只能保证先前写入流的字节被传递给操作系统进行写入;它不能保证它们实际上被写入物理设备，比如磁盘驱动器。
    *  
    * OutputStream的flush方法不执行任何操作。
    */ 
    public void flush() throws IOException {
    } 
    
    /** 
    * 关闭此输出流并释放与此流相关的任何系统资源。close的一般约定是关闭输出流。关闭的流不能执行输出操作，也不能重新打开。
    * 
    * close方法OutputStream不执行任何操作。 
    */ 
    public void close() throws IOException {
    } 
}
```

#### FileOutputStream
官方API对这个类的解释如下：
> 文件输出流是一个输出流，用于向 file 或FileDescriptor写入数据。文件是否可用或是否可以创建取决于底层平台。
特别是，一些平台允许一次只有一个FileOutputStream(或其他文件写入对象)打开文件进行写入。
在这种情况下，如果涉及的文件已经打开，该类中的构造函数将失败。
>
> FileOutputStream用于写入原始字节流，如图像数据。对于写入字符流，考虑使用FileWriter。

##### FileOutputStream构造函数
* FileOutputStream(File file)
* FileOutputStream(File file, boolean append)    如果 append 值为<code>true</code>，那么字节将被写入文件的末尾而不是开头，即这个字段用于控制写入是否被覆盖
* FileOutputStream(String name)
* FileOutputStream(String name, boolean append)  如果 append 值为<code>true</code>，那么字节将被写入文件的末尾而不是开头，即这个字段用于控制写入是否被覆盖
* FileOutputStream(FileDescriptor fdObj)
```java
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
}    
```





### 参考
[认知IO流之 — FileDescriptor](https://cloud.tencent.com/developer/article/1513524)