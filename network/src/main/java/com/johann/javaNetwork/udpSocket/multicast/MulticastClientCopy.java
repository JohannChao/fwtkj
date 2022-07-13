package com.johann.javaNetwork.udpSocket.multicast;

import com.johann.util.ProcessProperties;

import java.io.IOException;
import java.net.SocketException;

/**
 * @ClassName: MulticastClientCopy
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class MulticastClientCopy {
    private static String GROUP = ProcessProperties.getProperties("multicast_group");
    private static int SERVER_PORT = Integer.valueOf(ProcessProperties.getProperties("multicast_port"));
    public static void main(String[] args) throws IOException {
        MulticastClient client = new MulticastClient(9080);
        client.send(GROUP.split(",")[1],SERVER_PORT);
    }
}
