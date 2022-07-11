package com.johann.javaNetwork;

import java.net.*;

/**
 * @ClassName: DatagramSocketTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class DatagramSocketTest {

    public static void test() throws SocketException, UnknownHostException {
        //DatagramSocket udpSocket = new DatagramSocket(12345);
        DatagramSocket udpSocket = new DatagramSocket(12345, InetAddress.getByName("localhost"));
        //DatagramSocket udpSocket = new DatagramSocket(new InetSocketAddress(InetAddress.getByName("localhost"),12345));
    }
}
