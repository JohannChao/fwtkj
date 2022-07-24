package com.johann.tcpip.javaNetwork.tcpSocket.threadPool;

import com.johann.util.ProcessProperties;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** 使用线程池实现TCP服务器
 * (客户端与多线程TCP客户端一样)
 * @ClassName: TcpServerThreadPool
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
@SuppressWarnings("all")
public class TcpServerThreadPool {
    private static final String ADDRESS = ProcessProperties.getProperties("multiThread_host");
    private static final Integer PORT = Integer.valueOf(ProcessProperties.getProperties("multiThread_port"));
    private static final Integer BACKLOG = 1;
    //线程池中的容量
    private static final Integer THREAD_POOL_NUM = 10;

    private static ExecutorService pool = null;

    /**
     * 服务器监听新的客户端连接，并为每个客户端在线程池中分配一个线程
     */
    public static void sayHello(){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket();
            System.out.println("serverSocket.getReuseAddress() : "+serverSocket.getReuseAddress());
            /**
             * 启用/禁用SO_REUSEADDR套接字选项。
             * 当 TCP 连接关闭时，连接可能会在连接关闭后的一段时间内保持超时状态（通常称为TIME_WAIT状态或2MSL等待状态）。
             * 对于使用众所周知的套接字地址或端口的应用程序，如果存在涉及套接字地址或端口的处于超时状态的连接，则可能无法将套接字绑定到所需的SocketAddress 。
             * 在使用bind(SocketAddress)绑定套接字之前启用SO_REUSEADDR允许绑定套接字，即使先前处于超时状态的连接。
             * 创建ServerSocket时，未定义SO_REUSEADDR的初始设置。应用程序可以使用getReuseAddress()来确定SO_REUSEADDR的初始设置。
             */
            serverSocket.setReuseAddress(true);
            serverSocket.bind(new InetSocketAddress(InetAddress.getByName(ADDRESS),PORT),BACKLOG);

            //新建线程池
            pool = Executors.newFixedThreadPool(THREAD_POOL_NUM);

            while(true){
                //监听新的连接
                Socket newConnection = serverSocket.accept();
                System.out.println("监听到一个新的远程客户端请求连接 ["+newConnection.getRemoteSocketAddress()+"]");

                newConnection.setSoLinger(true,0);
                //提交一个新的任务
                pool.submit(new SocketTask(newConnection));
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

    public static void main(String[] args) {
        sayHello();
    }

    //新建一个socket task
    private static class SocketTask implements Callable<Void> {

        private Socket socket = null;

        public SocketTask(Socket socket){
            this.socket = socket;
        }

        @Override
        public Void call() throws Exception {
            try {
                while(true){
                    //读取客户端数据
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    int segLength = in.readInt();
                    byte[] seg = new byte[segLength];
                    in.read(seg);
                    System.out.println("从目标客户端["+socket.getRemoteSocketAddress()+"] 接收消息: ["+new String(seg)+"]");
                    /**
                     * 将此套接字的输入流放在“流的末尾”。任何发送到套接字输入流端的数据都会被确认，然后被静默丢弃。
                     * 如果在套接字上调用此方法后从套接字输入流中读取，则流的available方法将返回 0，其read方法将返回-1 （流结束）。
                     */
                    //socket.shutdownInput();

                    //向客户端发送消息
                    String sendStr = "你好，客户端！";
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    out.writeInt(sendStr.getBytes().length);
                    out.write(sendStr.getBytes());
                    out.flush();
                    System.out.println("服务器["+socket.getLocalSocketAddress()+"] 向目标客户端["+socket.getRemoteSocketAddress()+"] 发送消息: ["+sendStr+"]");
                    /**
                     * 禁用此套接字的输出流。对于 TCP 套接字，任何先前写入的数据都将按照 TCP 的正常连接终止顺序发送。
                     * 如果在套接字上调用 shutdownOutput() 后写入套接字输出流，则该流将引发 IOException
                     */
                    //socket.shutdownOutput();
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
            return null;
        }
    }


}
