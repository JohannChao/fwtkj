package com.johann.tcpip.nonBlocking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @ClassName: NonBlockingTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class NonBlockingTest {

    public static void test() throws IOException {
        /**
         * 创建一个监听 channel
         * 新通道的套接字最初是未绑定的，在接受连接之前，它必须通过其套接字的 {java.net.ServerSocket#bind(SocketAddress) bind} 方法之一绑定到特定地址
         */
        ServerSocketChannel ssc = ServerSocketChannel.open();
        /**
         * 改变此 channel 的阻塞模式
         *
         * 如果给定的阻塞模式与当前阻塞模式不同，那么这个方法调用 {#implConfigureBlocking implConfigureBlocking}方法，同时持有适当的锁，以改变模式。
         */
        ssc.configureBlocking(false);

        /**接受与此 channel 的套接字建立的连接。
         *
         * 如果当前的监听 channel 处于非阻塞模式，如果没有待处理的连接，则此方法将立即返回 <tt>null</tt>。
         * 否则它将无限期地阻塞，直到有新的连接可用或发生 I/O 错误。
         *
         * 无论此监听 channel 的阻塞模式如何，此方法返回的套接字chanel（如果有）都将处于阻塞模式，。
         */
        SocketChannel sc = ssc.accept();
        sc.configureBlocking(false);

        /**
         * 创建一个套接字channel
         */
        SocketChannel socketChannel = SocketChannel.open();
        /**
         * 打开一个套接字通道并将其连接到一个远程地址。
         */
        SocketChannel socketChannel1 = SocketChannel.open(new InetSocketAddress(InetAddress.getByName("localhost"),5540));



    }

}
