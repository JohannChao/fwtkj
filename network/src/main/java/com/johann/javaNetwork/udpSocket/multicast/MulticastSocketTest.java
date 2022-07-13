package com.johann.javaNetwork.udpSocket.multicast;

/**
 * Java使用MulticastSocket类来创建UDP多播套接字，以接收发送到多播IP地址的数据包。
 * 组播套接字基于组成员资格。创建并绑定组播套接字后，调用其joinGroup（InetAddress multiCastIPAddress）方法加入多播组，
 * 发送到该组的任何数据包数据包将被传递到此套接字。
 * 要离开组，请调用leaveGroup（InetAddress multiCastIPAddress）方法。
 *
 * 在IPv4中，范围224.0.0.0到239.255.255.255中的任何IP地址都可以用作组播地址来发送数据报。
 * IP地址224.0.0.0保留，您不应在您的应用程序中使用它。
 *
 * @ClassName: MulticastSocketTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class MulticastSocketTest {
}
