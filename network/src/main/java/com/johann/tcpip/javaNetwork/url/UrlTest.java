package com.johann.tcpip.javaNetwork.url;

import com.johann.util.ProcessProperties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: UrlTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class UrlTest {

    /**java.net.URL 方法
     * @see java.net.URL
     */
    public static void testUrl(){
        try {
            URL url = new URL(ProcessProperties.getProperties("test_url"));
            System.out.println("协议："+url.getProtocol());
            System.out.println("主机名："+url.getHost());
            System.out.println("端口："+url.getPort());
            System.out.println("默认端口："+url.getDefaultPort());
            System.out.println("路径："+url.getPath());
            System.out.println("请求参数："+url.getQuery());
            System.out.println("文件名及请求参数："+url.getFile());
            System.out.println("定位位置："+url.getRef());
            System.out.println("验证信息："+url.getAuthority());
            System.out.println("内容："+url.getContent());
            System.out.println("用户信息："+url.getUserInfo());
            /**
             * 返回与此 URL 等效的 {@link java.net.URI}。 此方法的功能与 {@code new URI (this.toString())} 相同。
             * 请注意，任何符合 RFC 2396 的 URL 实例都可以转换为 URI。 但是，一些不严格遵守的 URL 不能转换为 URI。
             */
            System.out.println("URI："+url.toURI());
            System.out.println("URL："+url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**URLConnection 方法
     * @see java.net.URLConnection
     */
    public static void testUrlConnection(){

        try {
            URL url = new URL(ProcessProperties.getProperties("test_connection_url"));
            URLConnection urlConnection = url.openConnection();
            if(urlConnection instanceof HttpURLConnection){
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder content = new StringBuilder();
                String readLine;
                while ((readLine = in.readLine())!=null){
                    content.append(readLine+"\n");
                }
                System.out.println(content);
                System.out.println("\n ===================== \n");

                /**
                 * 返回连接超时的设置
                 */
                System.out.println("超时设置: "+urlConnection.getConnectTimeout());
                System.out.println("连接内容："+urlConnection.getContent());
                /**
                 * header属性
                 */
                Map<String, List<String>> headerMap = urlConnection.getHeaderFields();
                Iterator<Map.Entry<String,List<String>>> iterator = headerMap.entrySet().iterator();
                while(iterator.hasNext()){
                    Map.Entry<String,List<String>> entry = iterator.next();
                    System.out.println("Key："+entry.getKey());
                    System.out.print("Value：{");
                    for (String s : entry.getValue()) {
                        System.out.print(s+" ");
                    }
                    System.out.println("}");
                }

                System.out.println("ContentEncoding："+urlConnection.getContentEncoding());
                System.out.println("ContentLength()："+urlConnection.getContentLength());
                System.out.println("ContentType："+urlConnection.getContentType());
                System.out.println("Expiration："+urlConnection.getExpiration());

                System.out.println("用户缓存："+urlConnection.getUseCaches());
                System.out.println("permission："+urlConnection.getPermission());
                System.out.println("ifModifiedSince："+urlConnection.getIfModifiedSince());
                System.out.println("URL："+urlConnection.getURL());
                System.out.println("RequestMethod："+((HttpURLConnection) urlConnection).getRequestMethod());

                ((HttpURLConnection) urlConnection).disconnect();
                /**
                 * IllegalStateException if already connected
                 */
                Map<String, List<String>> requestProperties = urlConnection.getRequestProperties();
                requestProperties.forEach((key,value)-> System.out.println("key："+key+" --- value："+value));


                /**
                 * 从 HTTP 响应消息中获取状态代码
                 */
                System.out.println("HTTP响应状态码："+((HttpURLConnection) urlConnection).getResponseCode());
                /**
                 * 获取从服务器返回的HTTP 响应消息（如果有）以及响应代码。
                 */
                System.out.println("HTTP 响应："+((HttpURLConnection) urlConnection).getResponseMessage());

            }else{
                System.out.println("It's not a HttpURLConnection");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        testUrl();
        testUrlConnection();
    }
}
