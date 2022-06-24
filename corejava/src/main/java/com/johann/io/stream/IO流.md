# I/O流

### 流的分类

#### 根据流向
I/O流根据流向，分为输入流和输出流。

注意输入流和输出流是相对于程序而言的。

* 输出：把程序(内存)中的内容输出到磁盘、光盘等存储设备中
* 输入：读取外部数据（磁盘、光盘等存储设备的数据）到程序（内存）中

#### 根据传输单位
I/O流根据传输单位，分为字节流和字符流。

* 字节流： 它处理单元为1个字节（byte），操作字节和字节数组，存储的是二进制文件，如果是音频文件、图片、歌曲，就用字节流好点（1byte = 8位）；

* 字符流： 它处理的单元为2个字节的Unicode字符，分别操作字符、字符数组或字符串，字符流是由Java虚拟机将字节转化为2个字节的Unicode字符为单位的字符而成的，
如果是关系到中文（文本）的，用字符流好点（1Unicode = 2字节 = 16位）；
>为什么使用字符流？ 
>
>因为使用字节流操作汉字或特殊符号语言的时候容易乱码，因为汉字不止一个字节，为了解决这个问题，建议使用字符流。

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
OutputStream是字节输出流的所有类的超类。 输出流接收输出字节并将其发送到某个接收器。程序需要定义一个OutputStream子类，且必须始终提供至少一个“写入单个字节输出”的方法。
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
> 文件输出流是一个输出流，用于向 file 或 FileDescriptor 写入数据。文件是否可用或是否可以创建取决于底层平台。
特别是，一些平台允许一次只有一个FileOutputStream(或其他文件写入对象)打开文件进行写入。
在这种情况下，如果涉及的文件已经打开，该类中的构造函数将失败。
>
> FileOutputStream用于写入原始字节流，如图像数据。对于写入字符流，考虑使用FileWriter。

##### FileOutputStream的构造函数
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
##### FileOutputStream的方法
```java
public class OutputStreamTest {
    /**
     * 写入方法
     * @throws IOException
     */
    public static void testWrite() throws IOException{
        File target = new File("D:\\ioTest\\outputStreamTest\\OutputStreamTest.txt");
//        //创建新的文件
//        System.out.println("target.createNewFile() : "+target.createNewFile());
//        //创建文件的字节流输出对象
//        OutputStream outputStream = new FileOutputStream(target,true);
//
//        //IO操作，将字节写入文件
//        outputStream.write(65);
//        outputStream.write("CDEFG".getBytes());
//        outputStream.write("---GGGGG".getBytes(),1,5);
//        //执行完上述步骤，文件中被写入了 “ACDEFG--GGG”
//
//        outputStream.flush();
//        outputStream.close();

        /**
         * 覆盖之前写入的数据  FileOutputStream(target) = FileOutputStream(target,false) “append”形参默认值是false
         */
        OutputStream outputStream1 = new FileOutputStream(target);
        outputStream1.write("YYYYY".getBytes());
        outputStream1.write("ZZZZZ".getBytes());
        outputStream1.flush();
        outputStream1.close();

    }
}
```

### 字节输入流
InputStream抽象类是表示输入字节流的所有类的超类。程序需要定义一个InputStream子类，且必须始终提供“返回输入的下一个字节”的方法。
```java
public abstract class InputStream implements Closeable {
    
    /** 
    *  MAX_SKIP_BUFFER_SIZE 用于确定调用skip()方法时使用的最大缓冲区大小。
    */
    private static final int MAX_SKIP_BUFFER_SIZE = 2048;
    
    /** 
    *  从输入流中读取数据的下一个字节。 值字节作为 <code>0</code> 到 <code>255</code> 范围内的 <code>int</code> 返回。 
    *  如果由于到达流的末尾而没有可用的字节，则返回值 <code>-1</code>。 此方法会一直阻塞，直到输入数据可用、检测到流结束或引发异常。
    *  
    *  子类必须提供此方法的实现。
    *  
    *  @return 数据的下一个字节，如果到达流的末尾，则返回 <code>-1</code>。
    */
    @Range(from=-1,to=255)public abstract int read() throws IOException;
    
    /** 
    *  从输入流中读取一些字节并将它们存储到缓冲区数组 <code>b</code> 中。实际读取的字节数以整数形式返回。
    *  在输入数据可用、检测到文件结尾或引发异常之前，此方法会一直阻塞。
    *  
    *  如果 <code>b</code> 的长度为零，则不读取字节并返回 <code>0</code>；否则，将尝试读取至少一个字节。
    *  如果由于流位于文件末尾而没有可用字节，则返回值 <code>-1</code>；否则，至少读取一个字节并将其存储到 <code>b</code> 中。
    *  
    *  读取的第一个字节存储在元素 b[0] 中，下一个字节存储在 b[1] 中，依此类推。读取的字节数最多等于 <code>b</code> 的长度。
    *  令 k 为实际读取的字节数；这些字节将存储在元素 b[0] 到 b[k-1] 中，留下元素 b[k] 到 b[b.length-1] 不受影响。
    *  
    *  <code>InputStream</code> 类的 <code>read(b)</code> 方法与：read(b, 0, b.length) 具有相同的效果。
    *  
    *  
    *  @param b 读取数据的缓冲区。
    *  @return 读入缓冲区的字节总数，如果由于到达流的末尾而没有更多数据，则返回 <code>-1</code>。
    */
    @Range(from=-1,to=java.lang.Integer.MAX_VALUE)public int read(byte b[]) throws IOException {
        return read(b, 0, b.length);
    }
    
    /** 
    *  从输入流中读取最多 len 个字节的数据到字节数组中。尝试读取尽可能多的字节(最多len)，但可能会读取较小的字节数。实际读取的字节数以整数形式返回。
    * 
    *  在输入数据可用、检测到文件结尾或引发异常之前，此方法会一直阻塞。
    *  
    *  如果 len 为零，则不读取任何字节并返回 0；否则，将尝试读取至少一个字节。如果由于流位于文件末尾而没有可用字节，则返回值 -1；否则，至少读取一个字节并将其存储到 b 中。
    *  
    *  读取的第一个字节存储在元素 b[off] 中，下一个字节存储在 b[off+1] 中，以此类推。读取的字节数最多等于 len。
    *  令 k为实际读取的字节数；这些字节将存储在元素 b[off] 到 b[off+k-1] 中，留下元素b[off+k] 到 b[off+len-1] 不受影响。
    *  
    *  在任何情况下，元素 b[0] 到 b[off] 和元素 b[off+len] 到 b[b.length-1] 不受影响。
    *  
    *  类 InputStream 的 read(b,off,len) 方法只是重复调用方法 read()。如果第一个这样的调用导致了一个IOException，该异常会从read(b,off,len)方法调用中返回。
    *  如果后续调用read()导致IOException，则捕获异常并将其视为文件结束；到该点为止读取的字节存储在 b 中，并返回发生异常之前读取的字节数。
    *  此方法的默认实现会阻塞，直到读取了请求的输入数据量 len、检测到文件结尾或抛出异常。鼓励子类提供此方法的更有效实现。
    *  
    *  
    *  @param b 读取数据的缓冲区。
    *  @param off 数组 <code>b</code> 中写入数据的起始偏移量。
    *  @param len 要读取的最大字节数。
    *  @return 读入缓冲区的字节总数，如果由于到达流的末尾而没有更多数据，则返回 <code>-1</code>。
    *  
    */
    @Range(from=-1,to=java.lang.Integer.MAX_VALUE)public int read(byte b[], int off, int len) throws IOException {
        if (b == null) {
            throw new NullPointerException();
        } else if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return 0;
        }

        int c = read();
        if (c == -1) {
            return -1;
        }
        b[off] = (byte)c;

        int i = 1;
        try {
            for (; i < len ; i++) {
                c = read();
                if (c == -1) {
                    break;
                }
                b[off + i] = (byte)c;
            }
        } catch (IOException ee) {
        }
        return i;
    }
    
    /** 
    *  跳过并丢弃此输入流中的 n 个字节的数据。由于各种原因，skip 方法最终可能会跳过一些更小的字节数，可能是 0。
    *  这可能是由多种情况中的任何一种造成的；在跳过 n 个字节之前到达文件末尾只是一种可能性。实际跳过的字节数被返回。
    *  如果 {@code n} 为负数，则 {@code InputStream} 类的 {@code skip} 方法始终返回 0，并且不跳过任何字节。
    *  子类可能以不同方式处理负值。
    *  
    *  此类的 skip方法创建一个字节数组，然后反复读入其中，直到读取了 n 个字节或到达流的末尾。
    *  鼓励子类提供此方法的更有效实现。例如，实现可能取决于寻找的能力。
    *  
    *  
    *  @param n 要跳过的字节数。
    *  @return  实际跳过的字节数。
    *  
    */
    @Range(from=-1,to=java.lang.Long.MAX_VALUE)public long skip(long n) throws IOException {
    
        long remaining = n;
        int nr;

        if (n <= 0) {
            return 0;
        }

        int size = (int)Math.min(MAX_SKIP_BUFFER_SIZE, remaining);
        byte[] skipBuffer = new byte[size];
        while (remaining > 0) {
            nr = read(skipBuffer, 0, (int)Math.min(size, remaining));
            if (nr < 0) {
                break;
            }
            remaining -= nr;
        }

        return n - remaining;
    }
    
    /** 
    *  返回可以从此输入流中读取（或跳过）的字节数的估计值，而不会被下一次调用此输入流的方法阻塞。下一次调用可能是同一个线程或另一个线程。
    *  单次读取或跳过这么多字节不会阻塞，但可能会读取或跳过更少的字节。
    *  
    *  请注意，虽然 {@code InputStream} 的某些实现会返回流中的字节总数，但很多不会。使用此方法的返回值来分配“用于保存此流中的所有数据”的缓冲区是不正确的。
    *  
    *  如果此输入流已通过调用 {@link #close()} 方法关闭，则此方法的子类实现可以选择抛出 {@link IOException}。
    *  
    *  {@code InputStream} 类的 {@code available} 方法总是返回 {@code 0}。
    *  
    *  这个方法应该被子类覆盖。
    *  
    *  @return 当到达输入流的末尾时，可以从该输入流中读取（或跳过）的字节数的估计值而不会阻塞或 {@code 0}。
    *  
    */
    public int available() throws IOException {
        return 0;
    }
    
    /** 
    *  关闭此输入流并释放与该流相关的任何系统资源。
    *  
    *  InputStream 类的 close 方法不执行任何操作。
    */
    public void close() throws IOException {}
    
    /** 
    *  标记此输入流中的当前位置。后续调用reset方法将该流重新定位到最后标记的位置，以便后续读取重新读取相同的字节。
    *  
    *  readlimit参数告诉输入流允许在标记位置失效之前读取这么多字节。
    *  
    *  mark的一般规则是，如果方法markSupported返回true，流以某种方式记住调用mark之后读取的所有字节，并随时准备在调用方法reset时再次提供相同的字节。
    *  但是，如果在调用reset之前，从流中读取的字节数超过readlimit字节，则流根本不需要记住任何数据。
    *  
    *  标记一个关闭的流不会对流产生任何影响。
    *  
    *  InputStream的mark方法不执行任何操作。
    *  
    *  @param readlimit 在标记位置无效之前可以读取的最大字节数。
    */
    public synchronized void mark(int readlimit) {}
    
    /** 
    *  将此流重新定位到上次在此输入流上调用 <code>mark</code> 方法时的位置。
    *  
    *  <p> <code>reset</code> 的一般合约是：
    *  <ul>
    *  <li> 如果方法 <code>markSupported</code> 返回 <code>true</code>，则：
    *      
    *      <ul><li> 如果方法 <code>mark</code> 自创建流以来尚未调用，或者 <code>mark</code> 方法自上次调用以来从流中读取的字节数大于上次调用时<code>mark</code>的参数，
    *      那么可能会抛出 <code>IOException</code>。
    *      
    *      <li> 如果没有抛出这样的 <code>IOException</code>，则流被重置为这样的状态，
    *      即自最近一次调用 <code>mark</code> 以来（或是从文件开始，如果 <code>mark</code> 没有被调用）读取的所有字节都将重新提供给后续<code>read</code> 方法调用者，
    *      后续的任何字节则将成为在调用 <code>reset</code>方法时的下一次输入的数据
    *      </ul>
    *      
    *  <li> 如果方法 <code>markSupported</code> 返回 <code>false</code>，则：
    *  
    *      <ul><li> 对 <code>reset</code> 的调用可能会引发 <code>IOException</code>。
    *      
    *      <li> 如果没有抛出 <code>IOException</code>，则流将重置为固定状态，该状态取决于输入流的特定类型及其创建方式。
    *      将提供给 <code>read</code> 方法的后续调用者的字节取决于输入流的特定类型。
    *      </ul>
    *  </ul>
    *  
    *  <p><code>InputStream</code> 类的<code>reset</code> 方法除了抛出<code>IOException</code> 之外什么都不做。
    *  
    *  @exception IOException 如果此流尚未被标记或标记已失效。
    *  
    */
    public synchronized void reset() throws IOException {
        throw new IOException("mark/reset not supported");
    }
    
    /** 
    *  测试此输入流是否支持 <code>mark</code> 和 <code>reset</code> 方法。 是否支持 <code>mark</code> 和 <code>reset</code> 是特定输入流实例的不变属性。 
    *  <code>InputStream</code> 的<code>markSupported</code> 方法返回<code>false</code>。
    *  
    *  @return  如果此流实例支持 mark 和 reset 方法，返回 <code>true</code>；否则返回 <code>false</code>
    *  
    */
    public boolean markSupported() {
        return false;
    }
}
```

##### FileInputStream的方法
>FileInputStream从文件系统中的文件中获取输入字节。哪些文件可用取决于主机环境。
>
>FileInputStream用于读取原始字节流，如图像数据。对于读取字符流，考虑使用FileReader。

```java
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


    public static void main(String[] args) throws IOException{
        //testMethod();
        copyFile();
    }

}
```

### 字符输出流



### 字符输入流


### 参考
[认知IO流之 — FileDescriptor](https://cloud.tencent.com/developer/article/1513524)