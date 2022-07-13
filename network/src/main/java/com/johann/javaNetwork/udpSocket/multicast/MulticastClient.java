package com.johann.javaNetwork.udpSocket.multicast;

import com.johann.util.ProcessProperties;

import java.io.IOException;
import java.net.*;

/**UDP多播套接字客户端
 * @ClassName: MulticastClient
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class MulticastClient {

    private static String GROUP = ProcessProperties.getProperties("multicast_group");
    private static int SERVER_PORT = Integer.valueOf(ProcessProperties.getProperties("multicast_port"));
    private DatagramSocket datagramSocket;

    public MulticastClient() throws SocketException {
        /**
         * 构造一个数据报套接字并将其绑定到本地主机上的任何可用端口。
         * 套接字将绑定到 {@link InetAddress#isAnyLocalAddress 通配符} 地址，这是内核选择的 IP 地址。
         */
        datagramSocket = new DatagramSocket();
    }

    public MulticastClient(int port, InetAddress laddr) throws SocketException {
        /**
         * 构造一个数据报套接字并将其绑定到指定的本地主机上的指定端口。
         */
        datagramSocket = new DatagramSocket(port,laddr);
    }
    public MulticastClient(int port) throws SocketException {
        /**
         * 构造一个数据报套接字并将其绑定到本地主机上的指定端口。
         */
        datagramSocket = new DatagramSocket(port);
    }

    /**
     * 向远程多播服务器发送数据
     * @param destAddress
     * @param destPort
     * @throws IOException
     */
    public void send(String destAddress,int destPort) throws IOException {
        String msg = "中华人民共和国";
        byte[] data = msg.getBytes();
        InetSocketAddress remote = new InetSocketAddress(InetAddress.getByName(destAddress),destPort);
        DatagramPacket packet = new DatagramPacket(data,data.length,remote);
        datagramSocket.send(packet);
        System.out.println("客户端 ["+datagramSocket.getLocalSocketAddress()+"] 向远程服务器 ["+remote+"] 发送消息 ["+msg+"]");
        if (datagramSocket!=null && !datagramSocket.isClosed()){
            datagramSocket.close();
        }
    }

    public static void main(String[] args) throws IOException{
        MulticastClient client = new MulticastClient();
        client.send(GROUP.split(",")[0],SERVER_PORT);
    }
}
