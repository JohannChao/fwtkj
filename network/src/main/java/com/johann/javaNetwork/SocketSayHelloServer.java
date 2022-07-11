package com.johann.javaNetwork;

import java.io.*;
import java.net.*;

/** ServerSocket 服务器
 *  基于TCP 的套接字
 * @ClassName: SocketSayHelloServer
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
@SuppressWarnings("all")
public class SocketSayHelloServer extends Thread{

    private ServerSocket serverSocket;
    private final String ADDRESS = "localhost";
    private final Integer BACKLOG = 10;
    private final Integer SO_TIMEOUT = 10000;

    public SocketSayHelloServer() throws IOException{
        serverSocket = new ServerSocket();
    }

    public SocketSayHelloServer(int port) throws IOException {
        /**
         * ServerSocket(int port, int backlog, InetAddress bindAddr)
         * backlog: 请求的传入连接队列的最大长度
         */
        serverSocket = new ServerSocket(port,BACKLOG,InetAddress.getByName(ADDRESS));
        serverSocket.setSoTimeout(SO_TIMEOUT);
    }

    @Override
    public void run() {
        while(true){
            serverSayHello();
        }
    }

    public void serverSayHello(){
        try {
            System.out.println("等待远程客户端连接，端口号为：" + serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
            System.out.println("远程客户端地址：" + server.getRemoteSocketAddress());
            System.out.println("本地服务器地址：" + server.getLocalSocketAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            System.out.println("【客户端响应】 ："+in.readLine());

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
            out.write("客户端你好，[当前服务器 " + server.getLocalSocketAddress()+"] 你已连接上！");
            out.newLine();
            System.out.println("服务器写出："+"客户端你好，[当前服务器 " + server.getLocalSocketAddress()+"] 你已连接上！");
            out.flush();

            String inMsg;
            while((inMsg=in.readLine())!=null){
                System.out.println("【客户端响应】 ："+inMsg);
                //String outMsg = "你先问的，你先说！";
                String outMsg = inMsg;
                out.write(outMsg);
                out.newLine();
                System.out.println("服务器写出："+outMsg);
                out.flush();
            }

            server.close();
        }catch (SocketException s){
            System.out.println("Connection reset!");
        }catch (SocketTimeoutException s){
            System.out.println("Socket timed out with "+SO_TIMEOUT+"!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Thread t = new SocketSayHelloServer(6543);
            t.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
