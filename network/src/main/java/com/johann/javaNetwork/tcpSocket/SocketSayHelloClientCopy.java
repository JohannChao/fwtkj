package com.johann.javaNetwork.tcpSocket;

import com.johann.util.ProcessProperties;

/**
 * @ClassName: SocketSayHelloClientCopy
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class SocketSayHelloClientCopy {
    private static final String ADDRESS = ProcessProperties.getProperties("local_host");
    private static final Integer PORT = Integer.valueOf(ProcessProperties.getProperties("tcp_port"));
    public static void main(String[] args) {
        SocketSayHelloClient.sayHello(ADDRESS,PORT);
    }
}
