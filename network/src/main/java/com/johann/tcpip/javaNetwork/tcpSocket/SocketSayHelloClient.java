package com.johann.tcpip.javaNetwork.tcpSocket;

import com.johann.util.ProcessProperties;

import java.io.*;
import java.net.Socket;

/** Socket 客户端
 *  基于TCP 的套接字
 * @ClassName: SocketSayHelloClient
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
@SuppressWarnings("all")
public class SocketSayHelloClient {

    private static final String ADDRESS = ProcessProperties.getProperties("local_host");
    private static final Integer PORT = Integer.valueOf(ProcessProperties.getProperties("tcp_port"));

    public static void sayHello(String host,int port){
        System.out.println("连接远程主机："+host+"，对应端口："+port);
        try {
            //创建一个Socket，连接到指定的host和port。当前Socket本地host和port默认分配
            Socket client = new Socket(host,port);

//            //return the remote IP address to which this socket is connected,or {@code null} if the socket is not connected.
//            System.out.println("远程服务器IP地址 client.getInetAddress() ： "+client.getInetAddress());
//
//            //return the remote port number to which this socket is connected, or 0 if the socket is not connected yet.
//            System.out.println("远程服务器端口 client.getPort() ： "+client.getPort());
//
//            //return new InetSocketAddress(getInetAddress(), getPort());
//            System.out.println("远程服务器IP地址及端口 client.getRemoteSocketAddress() ： "+client.getRemoteSocketAddress());
//
//            //Gets the local address to which the socket is bound.
//            //return the local address to which the socket is bound,the loopback address if denied by the security manager,
//            //or the wildcard address if the socket is closed or not bound yet.
//            System.out.println("本地客户端地址 client.getLocalAddress() : "+client.getLocalAddress());
//
//            //return the local port number to which this socket is bound or -1 if the socket is not bound yet.
//            System.out.println("本地客户端端口 client.getLocalPort() ： "+client.getLocalPort());
//
//            //return new InetSocketAddress(getLocalAddress(), getLocalPort());
//            System.out.println("本地客户端地址及端口 client.getLocalSocketAddress() : "+client.getLocalSocketAddress());

            OutputStream out = client.getOutputStream();
            BufferedWriter dataOut = new BufferedWriter(new OutputStreamWriter(out));
            dataOut.write("服务器你好，[当前客户端 "+client.getLocalSocketAddress()+"] 请求连接！");
            dataOut.newLine();
            System.out.println("客户端写出："+"服务器你好，[当前客户端 "+client.getLocalSocketAddress()+"] 请求连接！");
            dataOut.flush();

            InputStream in = client.getInputStream();
            BufferedReader dataIn = new BufferedReader(new InputStreamReader(in));
            System.out.println("【服务器响应】："+dataIn.readLine());

            /** CASE 1 */
            //控制台输入
            BufferedReader  consoleIn = new BufferedReader (new InputStreamReader(System.in));
            String promptMsg = "\nPlease enter a  message  (exit  to quit):";
            System.out.print(promptMsg);
            String outMsg = null;
            while((outMsg=consoleIn.readLine())!=null){
                if (outMsg.equalsIgnoreCase("exit")){
                    break;
                }
                dataOut.write(outMsg);
                dataOut.newLine();
                dataOut.flush();
                System.out.println("【服务器响应】："+dataIn.readLine());
                System.out.print(promptMsg);
            }
            /** CASE 1 */

            /** CASE 2 */
//            dataOut.write("你是谁！");
//            dataOut.newLine();
//            System.out.println("客户端写出："+" 你是谁！");
//            dataOut.flush();
//            String inMsg;
//            int count = 0;
//            //while((inMsg=dataIn.readLine())!=null){
//            while((inMsg=dataIn.readLine())!=null && count < 10){
//                count++;
//                System.out.println("【服务器响应】："+inMsg);
//                String outMsg = "你是谁！";
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                dataOut.write(outMsg);
//                dataOut.newLine();
//                System.out.println("客户端写出："+outMsg);
//                dataOut.flush();
//            }
            /** CASE 2 */

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        sayHello(ADDRESS,PORT);
    }
}
