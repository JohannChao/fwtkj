### Java URL 处理

URL（Uniform Resource Locator）中文名为统一资源定位符，有时也被俗称为网页地址。表示为互联网上的资源，如网页或者 FTP 地址。

URL 可以分为如下几个部分。
```text
protocol://host:port/path?query#fragment
```
protocol(协议)可以是 HTTP、HTTPS、FTP 和 File，port 为端口号，path为文件路径及文件名。

HTTP 协议的 URL 实例如下:
```text
http://www.runoob.com/index.html?language=cn#j2se
```
URL 解析：
* 协议为(protocol)：http
* 主机为(host:port)：www.runoob.com
* 端口号为(port): 80 ，以上URL实例并未指定端口，因为 HTTP 协议默认的端口号为 80。
* 文件路径为(path)：/index.html
* 请求参数(query)：language=cn
* 定位位置(fragment)：j2se，定位到网页中 id 属性为 j2se 的 HTML 元素位置 。

### URL 类方法
在java.net包中定义了URL类，该类用来处理有关URL的内容。对于URL类的创建和使用，下面分别进行介绍。

java.net.URL提供了丰富的URL构建方式，并可以通过java.net.URL来获取资源。
```text
1，public URL(String protocol, String host, int port, String file) throws MalformedURLException.
通过给定的参数(协议、主机名、端口号、文件名)创建URL。

2，public URL(String protocol, String host, String file) throws MalformedURLException
使用指定的协议、主机名、文件名创建URL，端口使用协议的默认端口。

3，public URL(String url) throws MalformedURLException
通过给定的URL字符串创建URL

4，public URL(URL context, String url) throws MalformedURLException
使用基地址和相对URL创建
```

URL类中包含了很多方法用于访问URL的各个部分，具体方法及描述如下：
```text
1，public String getPath()
返回URL路径部分。

2，public String getQuery()
返回URL查询部分。

3，public String getAuthority()
获取此 URL 的授权部分。

4，public int getPort()
返回URL端口部分

5，public int getDefaultPort()
返回协议的默认端口号。

6，public String getProtocol()
返回URL的协议

7，public String getHost()
返回URL的主机

8，public String getFile()
返回URL文件名部分

9，public String getRef()
获取此 URL 的锚点（也称为"引用"）。

10，public URLConnection openConnection() throws IOException
打开一个URL连接，并运行客户端访问资源。
```

##### 示例
```java
public class UrlTest {

    public static void testUrl(){
        try {
            URL url = new URL("http://www.runoob.com/index.html?language=cn#j2se");
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

    public static void main(String[] args) {
        testUrl();
    }
}
```

### URLConnections 类方法

openConnection() 返回一个 java.net.URLConnection。

如果你连接HTTP协议的URL, openConnection() 方法返回 HttpURLConnection 对象。

如果你连接的URL为一个 JAR 文件, openConnection() 方法将返回 JarURLConnection 对象。

URLConnection 方法列表如下：
```text
1，Object getContent()
检索URL链接内容

2，Object getContent(Class[] classes)
检索URL链接内容

3，String getContentEncoding()
返回头部 content-encoding 字段值。

4，int getContentLength()
返回头部 content-length字段值

5，String getContentType()
返回头部 content-type 字段值

6，int getLastModified()
返回头部 last-modified 字段值。

7，long getExpiration()
返回头部 expires 字段值。

8，long getIfModifiedSince()
返回对象的 ifModifiedSince 字段值。

9，public void setDoInput(boolean input)
URL 连接可用于输入和/或输出。如果打算使用 URL 连接进行输入，则将 DoInput 标志设置为 true；如果不打算使用，则设置为 false。默认值为 true。

10，public void setDoOutput(boolean output)
URL 连接可用于输入和/或输出。如果打算使用 URL 连接进行输出，则将 DoOutput 标志设置为 true；如果不打算使用，则设置为 false。默认值为 false。

11，public InputStream getInputStream() throws IOException
返回URL的输入流，用于读取资源

12，public OutputStream getOutputStream() throws IOException
返回URL的输出流, 用于写入资源。

13，public URL getURL()
返回 URLConnection 对象连接的URL
```

##### 示例
```java
public class UrlTest {

    /**URLConnection 方法
     * @see java.net.URLConnection
     */
    public static void testUrlConnection(){

        try {
            URL url = new URL("https://www.runoob.com");
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
        testUrlConnection();
    }
}
```