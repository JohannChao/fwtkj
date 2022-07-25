package com.johann.tcpip.nonBlocking;

import com.johann.util.ProcessProperties;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**非阻塞式 Socket客户端
 * @ClassName: NonBlockTcpClient
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
@SuppressWarnings("all")
public class NonBlockTcpClient {
    private static final String ADDRESS = ProcessProperties.getProperties("local_host");
    private static final Integer PORT = Integer.valueOf(ProcessProperties.getProperties("nb_port=6547"));


    public static void sayHello(){
        SocketChannel socketChannel = null;

        try {
            SocketAddress serverAddr = new InetSocketAddress(ADDRESS,PORT);
            //打开一个套接字通道并将其连接到一个远程地址
            socketChannel = SocketChannel.open(serverAddr);
            //创建一个阻塞式客户端，因此此处不需要
            //socketChannel.configureBlocking(false);

            /**
             * 创建用于收发数据的 ByteBuffer，分配 1024 字节大小的 buffer
             *
             * 通过 SocketChannel 的 read 方法读取数据，读取的数据保存在 recvBuff 中
             * SocketChannel 的 read 方法返回值如果是大于 0，表示读取的字节数；返回值如果是 -1，表示数据读取结束。对于非阻塞式 Socket，返回值可能是 0
             *
             * 通过 SocketChannel 的 write 方法向 Socket 写入需要发送的数据，需要提前写入 sendBuff 中。
             */
            /**
             * 分配一个新的字节缓冲区。
             * 新缓冲区的位置将为零，其限制将是其容量，其标记将未定义，并且其每个元素将被初始化为零。
             * 它将有一个backing array ，并且它的array offset为零。
             */
            ByteBuffer recvBuff = ByteBuffer.allocate(1024);
            ByteBuffer sendBuff = ByteBuffer.allocate(1024);

            int request_nums = 10;

            while(true){
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String requestTime = "Request time : "+df.format(new Date());

                /**
                 * 发送的消息格式是 ：length+消息体
                 * 即，先写入一个int，用于标识消息体的字节长度
                 * 然后，再写入具体的消息
                 */

                /**
                 * 用于写入 int 值的相对put方法（可选操作） 。
                 * 以当前字节顺序将包含给定 int 值的四个字节写入此缓冲区的当前位置，然后将该位置增加四。
                 */
                sendBuff.putInt(requestTime.getBytes().length);
                /**
                 * 相对批量put方法（可选操作） 。
                 *
                 * 此方法将字节从给定的源数组传输到此缓冲区。
                 * 如果要从数组复制的字节数多于此缓冲区中的剩余字节数，即如果length > remaining() ，则不传输任何字节并抛出BufferOverflowException 。
                 *
                 * 否则，此方法将给定数组中的 length 个字节复制到此缓冲区中，从数组中的给定偏移量和此缓冲区的当前位置开始。
                 * 然后这个缓冲区的位置增加length 。
                 *
                 * 换句话说，调用这种形式为dst.put(src, off, len)的方法与循环调用 put(byte b) 具有完全相同的效果
                 *     for (int i = off; i < off + len; i++)
                 *            dst.put(a[i]);
                 *
                 * 除了它首先检查此缓冲区中是否有足够的空间并且它可能更有效。
                 */
                sendBuff.put(requestTime.getBytes(),0,requestTime.getBytes().length);

                /**
                 * 翻转此缓冲区。limit 设置为当前位置，然后 position 设置为零。如果 mark 已定义，则将其丢弃。
                 * 在一系列通道读取或放置操作之后，调用此方法以准备一系列通道写入或相关获取操作。例如：
                 *        buf.put(magic);    // Prepend header
                 *        in.read(buf);      // Read data into rest of buffer
                 *        buf.flip();        // Flip buffer
                 *        out.write(buf);    // Write header + data to channel
                 * 在将数据从一个地方传输到另一个地方时，此方法通常与compact方法结合使用。
                 */
                sendBuff.flip();

                /**
                 * 向 Socket 写入需要发送的数据
                 */
                socketChannel.write(sendBuff);

                System.out.println("Send msg to Server...");

                /**
                 * 消息体的字节长度
                 */
                int bodyLen = -1;
                boolean isFlip = true;
                /**
                 * 回退这个缓冲区。position 设置为零并且 mark 被丢弃。
                 * 在一系列通道写入或获取操作之前调用此方法，假设已经适当地设置了 limit。例如：
                 *        out.write(buf);    // Write remaining data
                 *        buf.rewind();      // Rewind buffer
                 *        buf.get(array);    // Copy data into array
                 */
                recvBuff.rewind();

                while(true){
                    /**
                     * 读取数据，读取的数据保存在 recvBuff 中
                     */
                    int readBytes = socketChannel.read(recvBuff);
                    /**
                     * SocketChannel 的 read 方法返回值如果是大于 0，表示读取的字节数；返回值如果是 -1，表示数据读取结束。
                     * 对于非阻塞式 Socket，返回值可能是 0。
                     */
                    if(readBytes == -1){
                        /**
                         * 关闭此频道。
                         * 如果通道已经关闭，则此方法立即返回。否则，它将通道标记为关闭，然后调用implCloseChannel方法以完成关闭操作。
                         */
                        socketChannel.close();
                        return;
                    }

                    /**
                     * 读取消息的字节长度，是个 int 值
                     */
                    if(bodyLen == -1){
                        if(readBytes < 4){
                            continue;
                        }
                        recvBuff.flip();

                        /**
                         * 用于读取 int 值的相对get方法。
                         * 在此缓冲区的当前位置读取接下来的四个字节，根据当前字节顺序将它们组合成一个 int 值，然后将该位置增加四
                         */
                        bodyLen = recvBuff.getInt();
                        isFlip = false;
                    }

                    if(isFlip){
                        recvBuff.flip();
                    }

                    /**
                     * 返回 position 和 limit 之间的元素数
                     */
                    if (recvBuff.remaining() < bodyLen){
                        /**
                         * 压缩此缓冲区（可选操作） 。
                         *
                         * 缓冲区的 position 和它的 limit 之间的字节（如果有的话）被复制到缓冲区的开头。也就是说，索引p = position()处的字节
                         * 被复制到索引 0，索引p + 1 处的字节被复制到索引 1，依此类推，直到索引 limit() - 1 处的字节被复制到索引n = limit() - 1 - p 。
                         * 然后将缓冲区的位置设置为n+1 ，并将其 limit 设置为其容量capacity。mark（如果已定义）将被丢弃。
                         *
                         * 缓冲区的 position 设置为复制的字节数，而不是零，以便调用此方法后可以立即调用另一个相对put方法。
                         *
                         * 从缓冲区写入数据后调用此方法，以防写入不完整。例如，以下循环通过缓冲区buf将字节从一个通道复制到另一个通道：
                         *
                         *    buf.clear();          // Prepare buffer for use
                         *    while (in.read(buf) >= 0 || buf.position != 0) {
                         *        buf.flip();
                         *        out.write(buf);
                         *        buf.compact();    // In case of partial write
                         *    }
                         */
                        recvBuff.compact();
                        continue;
                    }

                    byte[] body = new byte[bodyLen];
                    /**
                     * 相对批量get方法。
                     * 此方法将此缓冲区中的字节传输到给定的目标数组中。如果缓冲区中剩余的字节数少于满足请求所需的字节数，
                     * 也就是说，如果length > remaining() ，则不传输任何字节并抛出BufferUnderflowException 。
                     *
                     * 否则，此方法将此缓冲区中的 length 个字节复制到给定数组中，
                     * 从此缓冲区的当前位置position和数组中的给定偏移量offset开始。然后这个缓冲区的位置增加length 。
                     *
                     * 换句话说，调用src.get(dst, off, len)形式的此方法与循环具有完全相同的效果
                     *
                     *      for (int i = off; i < off + len; i++)
                     *          dst[i] = src.get():
                     *
                     * 除了它首先检查此缓冲区中是否有足够的字节并且它可能更有效。
                     */
                    recvBuff.get(body,0,body.length);

                    System.out.println("Recv from Server : "+new String(body,0,bodyLen));
                    break;
                }

                if ((request_nums-- == 0)){
                    break;
                }

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                /**
                 * 回退这个缓冲区。position 设置为零并且 mark 被丢弃。
                 * 在一系列通道写入或获取操作之前调用此方法，假设已经适当地设置了 limit。例如：
                 *        out.write(buf);    // Write remaining data
                 *        buf.rewind();      // Rewind buffer
                 *        buf.get(array);    // Copy data into array
                 */
                sendBuff.rewind();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(socketChannel != null){
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
