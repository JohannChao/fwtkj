package com.johann.javaNetwork.tcpSocket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

/**
 * @ClassName: ServerSocketTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class ServerSocketTest {

    public static void test(){
        try {
            InetAddress address = InetAddress.getByName("localhost");

            /**
             * ServerSocket(int port, int backlog, InetAddress bindAddr)
             * backlog: 请求的传入连接队列的最大长度
             */
            ServerSocket serverSocket = new ServerSocket(6543,100,address);

            serverSocket.accept();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
