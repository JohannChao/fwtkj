package com.johann.tcpip.javaNetwork.udpSocket.multicast;

import com.johann.util.ProcessProperties;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**UDP多播套接字服务器
 * @ClassName: MulticastServer
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class MulticastServer {

    private static String GROUP = ProcessProperties.getProperties("multicast_group");
    private static int SERVER_PORT = Integer.valueOf(ProcessProperties.getProperties("multicast_port"));

    public void receive(){

        MulticastSocket mcSocketServer = null;
        String[] groupName = GROUP.split(",");
        InetAddress mcGroup;
        try {
            //创建UDP多播套接字
            mcSocketServer = new MulticastSocket(SERVER_PORT);
            System.out.println("UDP多播套接字服务器创建...");
            System.out.println("服务器IP/Port ["+mcSocketServer.getLocalSocketAddress()+"]");

            //添加一个多播组，发送到该组(IP)的任何数据包数据包将被传递到此套接字。
            for (String name : groupName) {
                mcGroup = InetAddress.getByName(name);
                mcSocketServer.joinGroup(mcGroup);
                System.out.println("多播组 ["+mcGroup+"] 加入到当前多播套接字下");
            }

            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data,data.length);
            System.out.println("等待消息...");

            while (true){
                mcSocketServer.receive(packet);
                System.out.println("UDP多播套接字服务器从 [IP Address "+packet.getAddress()+" , Port "+packet.getPort()+"] 接收到消息 ["+new String(data,packet.getOffset(),packet.getLength())+"]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(mcSocketServer!=null){
                try {
                    //将一个多播组从当前多播套接字中移除
                    for (String name : groupName) {
                        mcGroup = InetAddress.getByName(name);
                        mcSocketServer.leaveGroup(mcGroup);
                        System.out.println("多播组 ["+mcGroup+"] 从当前多播套接字下移除");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!mcSocketServer.isClosed()){
                    mcSocketServer.close();
                }
            }
        }
    }



    public static void main(String[] args) {
        new MulticastServer().receive();
    }
}
