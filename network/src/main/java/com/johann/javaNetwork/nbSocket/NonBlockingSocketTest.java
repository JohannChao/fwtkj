package com.johann.javaNetwork.nbSocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName: NonBlockingSocketTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class NonBlockingSocketTest {

    public static void test() throws IOException {

        /**
         * 创建一个选择器对象
         */
        Selector selector = Selector.open();

        /**
         * 新建一个 ServerSocketChannel 对象
         * 这个新建的 socket通道默认是未绑定的，它必须通过其套接字的 {@link java.net.ServerSocket#bind(SocketAddress) bind} 方法之一绑定到特定地址，然后才能接受连接。
         */
        ServerSocketChannel ssChannel = ServerSocketChannel.open();

        /**
         * {@code backlog} 参数是套接字上请求的最大挂起连接数。 它的确切语义是特定于实现的。 特别地，实现可以施加最大长度或者可以选择完全忽略参数。
         * 提供的值应大于 {@code 0}。 如果它小于或等于 {@code 0}，则将使用特定于实现的默认值(50)。
         */
        ssChannel.socket().bind(new InetSocketAddress("localhost",9413),100);

        /**
         * 默认情况下，服务器套接字通道或套接字通道是阻塞通道。要使其成为非阻塞通道，请调用以下方法。
         */
        ssChannel.configureBlocking(false);

        /**
         * 用给定的选择器注册这个通道，返回一个选择键。
         *
         * 服务器套接字必须向选择器注册才能执行某些操作
         * 有四种操作，我们可以用选择器注册一个通道。
         *      1，使用 SelectionKey.OP_CONNECT 连接操作，可以在客户端为 SocketChannel 注册。选择器将通知有关连接操作进度。
         *      2，使用 SelectionKey.OP_ACCEPT 接受操作，可以在服务器上为 ServerSocketChannel 注册。当客户端请求新连接到达时，选择器将通知。
         *      3，使用 SelectionKey.OP_READ 读取操作，可以在客户端和服务器上为 SocketChannel 注册。选择器将在通道准备好读取某些数据时通知。
         *      4，使用 SelectionKey.OP_WRITE 进行写操作，可以在客户端和服务器上为 SocketChannel 注册。选择器将在通道准备好写入某些数据时通知。
         */
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);

        /**
         * 返回一个 SelectionKey 集合
         * 当前集合中的 key 可以被移除，但是不能被添加，任何将对象添加到键集的尝试都将导致抛出 {@link UnsupportedOperationException}
         */
        Set<SelectionKey> selectionKeySet = selector.selectedKeys();
        processReadySet(selectionKeySet);

    }

    public static void processReadySet(Set<SelectionKey> selectionKeySet) throws IOException{
        Iterator<SelectionKey> iterator = selectionKeySet.iterator();
        //遍历
        while(iterator.hasNext()){
            SelectionKey key = iterator.next();
            iterator.remove();

            /**
             * 测试此键的通道是否准备好接受新的套接字连接。
             * key.isAcceptable()方法的调用形式与下述表达式完全相同  (key.readyOps() & SelectionKey.OP_ACCEPT) != 0
             */
            if(key.isAcceptable()){
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();

                SocketChannel socketChannel = serverSocketChannel.accept();
                socketChannel.configureBlocking(false);
                socketChannel.register(key.selector(),SelectionKey.OP_READ);
            }

            if (key.isReadable()){
                String msg = processRead(key);
                if (msg.length() > 0){
                    SocketChannel socketChannel = (SocketChannel)key.channel();

                    ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());

                    socketChannel.write(buffer);
                }
            }

        }
    }


    public static String processRead(SelectionKey key) throws IOException{

        SocketChannel socketChannel = (SocketChannel)key.channel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        int byteCount = socketChannel.read(byteBuffer);

        if(byteCount > 0){
            /**
             * 翻转这个缓冲区。将 limit 设置为当前位置，然后将 position 设置为零。如果 mark 被定义，那么它将被丢弃。
             */
            byteBuffer.flip();
            return new String(byteBuffer.array());
        }
        return "NoMessage";
    }


}
