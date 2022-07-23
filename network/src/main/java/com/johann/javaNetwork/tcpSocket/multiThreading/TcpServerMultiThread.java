package com.johann.javaNetwork.tcpSocket.multiThreading;

import com.johann.util.ProcessProperties;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/** 多线程 TCP 服务器
 * @ClassName: TcpServerMultiThread
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class TcpServerMultiThread implements Runnable{

    private static final String ADDRESS = ProcessProperties.getProperties("multiThread_host");
    private static final Integer PORT = Integer.valueOf(ProcessProperties.getProperties("multiThread_port"));
    private static final Integer BACKLOG = 1;

    private Socket socket = null;

    public TcpServerMultiThread() {
    }

    public TcpServerMultiThread(Socket socket){
        this.socket = socket;
    }

    /**
     * 服务器监听新的客户端连接，并为每个客户端分配一个新的子线程
     */
    public static void sayHello(){
        ServerSocket serverSocket = null;

        try {
            /**创建TCP服务器
             * 创建 ServerSocket 的时候，可以设置 BACKLOG 大小，这个字段用于设置待连接队列的大小
             * 下面的 while 循环中，将代码注释掉，即服务器不 accept，此时最多允许 BACKLOG 个客户端来请求连接服务器
             * 超过 BACKLOG 上限的客户端连接，将会统统被 服务器ServerSocket 拒绝掉，客户端报异常 ：java.net.ConnectException: Connection refused: connect
             *
             * 结合TCP三次握手来解释就是，这个队列存放的是 向服务器发送【SYN报文】，但是服务器还没来得及发送【SYN，ACK报文】的连接
             * 只要服务器不 accept，即不发送 【SYN,ACK报文】，这个队列就不会减少
             * 一旦服务器 accept，就会从这个队列中移除一个连接
             */
            serverSocket = new ServerSocket(PORT,BACKLOG, InetAddress.getByName(ADDRESS));
            while (true){
                //监听新的客户端连接
                Socket newConnection = serverSocket.accept();
                System.out.println("监听到一个新的远程客户端请求连接 ["+newConnection.getRemoteSocketAddress()+"]");

                //一个客户端，对应一个新的子线程
                Thread t = new Thread(new TcpServerMultiThread(newConnection));
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 每个客户端，对应一个新的子线程。此时详细的逻辑处理操作
     */
    @Override
    public void run() {

        //读取客户端
        try {
            while(true){
                DataInputStream in = new DataInputStream(socket.getInputStream());
                int segLength = in.readInt();
                byte[] seg = new byte[segLength];
                in.read(seg);
                System.out.println("从目标客户端["+socket.getRemoteSocketAddress()+"] 接收消息: ["+new String(seg)+"]");

                //向客户端发送消息
                String sendStr = "你好，客户端！";
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeInt(sendStr.getBytes().length);
                out.write(sendStr.getBytes());
                out.flush();
                System.out.println("服务器["+socket.getLocalSocketAddress()+"] 向目标客户端["+socket.getRemoteSocketAddress()+"] 发送消息: ["+sendStr+"]");
            }
        } catch (IOException e) {
            if (e instanceof EOFException){
                System.out.println("目标客户端 ["+socket.getRemoteSocketAddress()+"] 退出！！！！");
            }else{
                e.printStackTrace();
            }
        } finally {
            if (socket != null){
                try {
                    socket.close();
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
