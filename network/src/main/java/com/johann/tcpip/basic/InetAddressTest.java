package com.johann.tcpip.basic;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**InetAddress 类实现了完整的 IP 地址和域名之间的相互解析机制
 * @ClassName: InetAddressTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
@SuppressWarnings("all")
public class InetAddressTest {

    /**public static InetAddress getByName(String host){}
     * 确定给定主机名（域名）的主机的IP地址
     *
     * public static InetAddress[] getAllByName(String host){}
     * 确定给定主机名（域名）的主机的所有IP地址
     *
     */
    public static void getByNameTest() throws UnknownHostException {
        InetAddress addr = InetAddress.getByName("baidu.com");
        //InetAddress addr = InetAddress.getByName("220.181.38.148");
        System.out.println(addr.getHostAddress()+" / "+addr.getHostName());

        InetAddress[] addresses = InetAddress.getAllByName("baidu.com");
        for (InetAddress address : addresses) {
            System.out.println(address.getHostAddress()+" / "+address.getHostName());
        }
    }

    /**
     * InetAddress getByAddress(byte[] addr){}
     * 返回给定原始IP地址的{@code InetAddress}对象。参数按网络字节顺序排列：地址的最高顺序字节位于{@code getAddress（）[0]}。
     *
     * InetAddress getByAddress(String host, byte[] addr){}
     * 基于提供的主机名和IP地址创建InetAddress。未检查姓名服务地址的有效性。
     * 主机名可以是机器名，例如“{@code java.sun.com}”，也可以是其IP地址的文本表示。
     * 也没有对主机名进行有效性检查。
     */
    public static void getByAddressTest() throws UnknownHostException{
        byte[] baidu = new byte[]{(byte)220,(byte)181,38,(byte)148};

        InetAddress addr = InetAddress.getByAddress(baidu);
        System.out.println(addr.getHostAddress()+" / "+addr.getHostName());

        InetAddress address = InetAddress.getByAddress("baidu.com",baidu);
        System.out.println(address.toString());
    }

    public static void main(String[] args) throws IOException {
        getByNameTest();
        getByAddressTest();
    }
}
