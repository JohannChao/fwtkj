package com.johann.javaNetwork.udpSocket;

import com.johann.util.ProcessProperties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**基于UDP套接字的Echo客户端
 * @ClassName: UdpEchoClient
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
@SuppressWarnings("all")
public class UdpEchoClient {

    private final static String REMOTE_SERVERNAME = ProcessProperties.getProperties("local_host");
    private final static Integer REMOTE_PORT = Integer.valueOf(ProcessProperties.getProperties("udp_port"));
    private final static Integer PACKET_LENGTH = 1024;

    public static void sayHello(){
        DatagramSocket udpClient = null;
        try {
            /**
             * 构造一个数据报套接字并将其绑定到本地主机上的任何可用端口。
             * 套接字将绑定到 {@link InetAddress#isAnyLocalAddress 通配符} 地址，这是内核选择的 IP 地址。
             */
            udpClient = new DatagramSocket();
            /**
             * 构造一个数据报套接字并将其绑定到指定的本地主机上的指定端口。
             */
            //udpClient = new DatagramSocket(9080, InetAddress.getByName("localhost"));
            /**
             * 构造一个数据报套接字并将其绑定到本地主机上的指定端口。
             */
            //udpClient = new DatagramSocket(9080);

            System.out.println("创建 UDP 客户端 ："+udpClient.getLocalSocketAddress());
            System.out.println("UDP 客户端对应的远程 ："+udpClient.getRemoteSocketAddress());

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("请输入待发送的消息(exit退出)...");
            String inMsg;
            while((inMsg = br.readLine())!=null){
                if("exit".equalsIgnoreCase(inMsg)){
                    break;
                }
                DatagramPacket packet = preparePacket(inMsg,REMOTE_SERVERNAME,REMOTE_PORT);
                udpClient.send(packet);
                udpClient.receive(packet);
                processData(packet);
                System.out.print("请输入待发送的消息(exit退出)...");
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(udpClient!=null && !udpClient.isClosed()){
                udpClient.close();
            }
        }
    }

    /**
     * 客户端对接收的消息进行处理
     * @param packet
     */
    public static void processData(DatagramPacket packet){
        byte[] data = packet.getData();
        System.out.println("从 [IP Address "+packet.getAddress()+" , Port "+packet.getPort()+"] 接收到消息 ["+new String(data,packet.getOffset(),packet.getLength())+"]");
    }

    /**
     * 准备待发送的数据
     * @param msg
     * @return
     * @throws UnknownHostException
     */
    public static DatagramPacket preparePacket(String msg,String destAddress,int destPort) throws UnknownHostException {
        byte[] data = msg.getBytes();
        int dataLen = data.length > PACKET_LENGTH ? PACKET_LENGTH : data.length;

        DatagramPacket packet = new DatagramPacket(data,dataLen);
        //设置将此数据报发送到的机器的IP地址
        packet.setAddress(InetAddress.getByName(destAddress));
        //设置将此数据报发送到的远程主机上的端口号
        packet.setPort(destPort);
        System.out.println("待发送的数据报 [目标 IP Address "+packet.getAddress()+" , Port "+packet.getPort()+"] 消息内容 ["+new String(data,packet.getOffset(),packet.getLength())+"]");
        return packet;
    }

    public static void main(String[] args) {
        sayHello();
    }

}
