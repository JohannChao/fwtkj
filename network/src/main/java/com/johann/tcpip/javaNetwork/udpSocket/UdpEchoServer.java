package com.johann.tcpip.javaNetwork.udpSocket;

import com.johann.util.ProcessProperties;

import java.io.IOException;
import java.net.*;

/**基于UDP套接字的Echo服务器
 * @ClassName: UdpEchoServer
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class UdpEchoServer {

    private final static String SERVERNAME = ProcessProperties.getProperties("local_host");
    private final static Integer PORT = Integer.valueOf(ProcessProperties.getProperties("udp_port"));
    private final static Integer PACKET_LENGTH = 1024;

    public static void sayHello(){
        DatagramSocket udpServer = null;
        try {
            udpServer = new DatagramSocket(PORT, InetAddress.getByName(SERVERNAME));
            //udpServer.setSoTimeout(10000);
            System.out.println("创建 UDP 服务器 ： " + udpServer.getLocalSocketAddress());

            /***** case 1  ********/
            byte[] data = new byte[PACKET_LENGTH];
//            while (true) {
//                System.out.println("等待消息中... ");
//                DatagramPacket packetReceived = new DatagramPacket(data, data.length);
//                //接收消息
//                udpServer.receive(packetReceived);
//                processData(packetReceived);
//                udpServer.send(packetReceived);
//            }
            /***** case 1  ********/


            /***** case 2 反序列化字节消息为对象 ********/
            System.out.println("等待消息中... ");
            DatagramPacket packetReceived = new DatagramPacket(data, data.length);
            //接收消息
            udpServer.receive(packetReceived);
            UdpMessage rMsg = new UdpMessage();
            // inMessage 是一个 DatagramPacket 类型的实例
            rMsg.deserialize(packetReceived);
            System.out.println("服务器接收到消息 ："+rMsg.toString());
            /***** case 2  ********/
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        }catch (SocketTimeoutException e){
            System.out.println(e.getMessage());
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        }catch (IOException e){
            System.out.println(e.getMessage());
        }finally {
            if(udpServer!=null && !udpServer.isClosed()){
                udpServer.close();
            }
        }
    }

    /**
     * 服务端对接收的消息进行处理
     * @param packet
     */
    public static void processData(DatagramPacket packet){
        byte[] data = packet.getData();
        System.out.println("从 [IP Address "+packet.getAddress()+" , Port "+packet.getPort()+"] 接收到消息 ["+new String(data,packet.getOffset(),packet.getLength())+"]");
    }

    public static void main(String[] args) {
        sayHello();
    }

}
