package com.johann.tcpip.javaNetwork.udpSocket;

import java.net.*;

/**
 * @ClassName: UdpDatagramSocketTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class UdpDatagramSocketTest {

    public static void constructor() throws SocketException, UnknownHostException {

        /**
         * 1，DatagramSocket 构造函数绑定本地地址
         *      case1：
         *      public DatagramSocket(SocketAddress bindaddr) throws SocketException {
         *         // create a datagram socket.
         *         createImpl();
         *         if (bindaddr != null) {
         *             try {
         *                 bind(bindaddr);
         *             } finally {
         *                 if (!isBound())
         *                     close();
         *             }
         *         }
         *      }
         *
         *      case2：
         *      public DatagramSocket(int port) throws SocketException {
         *         this(port, null);
         *      }
         *
         *      case3：
         *      public DatagramSocket(int port, InetAddress laddr) throws SocketException {
         *         this(new InetSocketAddress(laddr, port));
         *      }
         *
         *
         *   构造一个数据报套接字并将其绑定到本地主机上的任何可用端口。
         *   套接字将绑定到 {@link InetAddress#isAnyLocalAddress 通配符} 地址，这是内核选择的 IP 地址。
         *
         *   DatagramSocket udpClient = new DatagramSocket();
         */
        //DatagramSocket udpClient = new DatagramSocket();
        //DatagramSocket udpSocket = new DatagramSocket(new InetSocketAddress(InetAddress.getByName("localhost"),12345));
        //DatagramSocket udpSocket = new DatagramSocket(12345);
        DatagramSocket udpSocket = new DatagramSocket(12345, InetAddress.getByName("localhost"));


        /**
         * 1，DatagramPacket 构造函数创建一个数据包来接收数据
         *      case1：
         *      public DatagramPacket(byte buf[], int length) {
         *          this (buf, 0, length);
         *      }
         *
         *      case2：
         *      public DatagramPacket(byte buf[], int offset, int length) {
         *         setData(buf, offset, length);
         *         this.address = null;
         *         this.port = -1;
         *      }
         *
         *
         * 2，DatagramPacket 构造函数创建一个数据包来发送数据，以下地址均为远程主机地址和端口(InetAddress address, int port)
         *      case1：
         *      public DatagramPacket(byte buf[], int offset, int length,
         *                           InetAddress address, int port) {
         *          setData(buf, offset, length);
         *          setAddress(address);
         *          setPort(port);
         *      }
         *
         *      case2：
         *      public DatagramPacket(byte buf[], int offset, int length, SocketAddress address) {
         *         setData(buf, offset, length);
         *         setSocketAddress(address);
         *      }
         *
         *      case3：
         *      public DatagramPacket(byte buf[], int length,
         *                           InetAddress address, int port) {
         *         this(buf, 0, length, address, port);
         *      }
         *
         *      case4：
         *      public DatagramPacket(byte buf[], int length, SocketAddress address) {
         *         this(buf, 0, length, address);
         *      }
         */
        byte[]  data = new byte[1024];
        //创建一个数据包以接收1024字节的数据
        DatagramPacket dpReceive = new DatagramPacket(data,data.length);
        //创建一个包的缓冲区大小为1024，并从偏移量8开始接收数据，它将只接收32字节的数据
        DatagramPacket dpReceive2 = new DatagramPacket(data,8,32);

        //创建一个数据包以发送1024字节的数据，目标地址为 localhost:6543
        DatagramPacket dpSend = new DatagramPacket(data,data.length,InetAddress.getByName("localhost"),6543);

        /**
         * 返回这个数据报将要被发送的机器IP （接下来要使用 send方法，发送这个数据包）
         * 或者，返回这个数据报的来源机器IP （这个数据报是通过 receive方法接收到的）
         */
        InetAddress address = dpReceive.getAddress();
    }



}
