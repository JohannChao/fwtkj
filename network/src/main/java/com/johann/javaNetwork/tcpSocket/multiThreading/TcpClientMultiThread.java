package com.johann.javaNetwork.tcpSocket.multiThreading;

import com.johann.util.ProcessProperties;

import javax.xml.ws.soap.Addressing;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/** 多线程 TCP 客户端
 * @ClassName: TcpClientMultiThread
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class TcpClientMultiThread {
    private static final String ADDRESS = ProcessProperties.getProperties("multiThread_host");
    private static final Integer PORT = Integer.valueOf(ProcessProperties.getProperties("multiThread_port"));
    private static final Integer SO_TIMEOUT = 10000;
    private static final Integer COUNT_TIMES = 10;

    public static void sayHello(){
        Socket client = null;
        //执行次数
        int countTimes = 0;
        try {
            //无参构造客户端
            client = new Socket();
            //连接服务器，设置连接超时时间
            client.connect(new InetSocketAddress(ADDRESS,PORT),SO_TIMEOUT);

            while(true){
                //对外发送数据
                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                String sendData = "你好，服务器！";
                out.writeInt(sendData.getBytes().length);
                out.write(sendData.getBytes());
                out.flush();
                System.out.println("客户端["+client.getLocalSocketAddress()+"] 向目标服务器["+client.getRemoteSocketAddress()+"] 发送消息: ["+sendData+"]");

                //接收服务器消息
                DataInputStream in = new DataInputStream(client.getInputStream());
                int segLength = in.readInt();
                byte[] seg = new byte[segLength];
                in.read(seg);
                System.out.println("从目标服务器["+client.getRemoteSocketAddress()+"] 接收消息: ["+new String(seg)+"]");

                //超过执行次数，结束连接
                if (++countTimes >= COUNT_TIMES){
                    break;
                }

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (client != null){
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) {
        sayHello();
    }

}
