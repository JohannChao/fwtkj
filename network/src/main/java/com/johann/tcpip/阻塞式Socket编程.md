### 创建Java TCP Socket
TCP 的英文全称是 Transmission Control Protocol，翻译成中文叫做传输控制协议，它是 TCP/IP 协议族中非常重要的一个传输层协议。
TCP 是一个面向连接的、面向字节流的、可靠的传输层协议，有丢包重传机制、有流控机制、有拥塞控制机制。TCP 保证数据包的顺序，并且对重复包进行过滤。
相比不可靠传输协议 UDP，TCP 完全是相反的。

对于可靠性要求很高的应用场景来说，选择可靠 TCP 作为传输层协议肯定是正确的。例如，著名的 HTTP 协议和 FTP 协议都是采用 TCP 进行传输。
当然 TCP 为了保证传输的可靠性，引入了非常复杂的保障机制，比如：TCP 连接建立时的三次握手和连接关闭时的四次挥手机制，滑动窗口机制，发送流控机制，慢启动和拥塞避免机制等。
当然，操作系统的网络协议栈已经实现了这些复杂的机制，

[TCP客户端与服务器](pic/TCP客户端与服务器.png)

[TCP协议连接过程](TCP协议连接过程.md)

[TCP Socket示例](javaNetwork/tcpSocket)

TCP 是面向字节流的协议，TCP 传输数据的时候并不保证消息边界，消息边界需要程序员设计应用层协议来保证。将字节流解析成自定义的协议格式，需要借助 java.io.* 中提供的工具类。
一般情况下，java.io.DataInputStream 、java.io.DataOutputStream 和 java.io.BufferedInputStream、java.io.BufferedOutputStream 四个类就足以满足你的需求了。
DataInputStream 和 DataOutputStream 类主要是用以读写 java 相关的数据类型，BufferedInputStream 和 BufferedOutputStream 解决缓冲读写的问题，目的是提高读写效率。

#### ServerSocket 中 BACKLOG 是什么？
创建 ServerSocket 的时候，可以设置 BACKLOG 大小，这个字段用于设置待连接队列的大小

结合TCP三次握手来解释就是：

* 这个队列存放的是 向服务器发送【SYN报文】，但是服务器还没来得及发送【SYN，ACK报文】的连接。
* 客户端每新建一个 socket连接，向服务器发送了【SYN报文】，这个队列就会增加 1。服务器ServerSocket accept，即发送 【SYN,ACK报文】后，就会从这个队列中取出一个连接。
* 超过 BACKLOG 上限的客户端连接，将会统统被 服务器ServerSocket 拒绝掉，客户端报异常 ：java.net.ConnectException: Connection refused: connect


### Java UDP Socket
UDP 的英文全称是：User Datagram Protocol，翻译成中文叫用户数据报协议，它是 TCP/IP 协议族中一个非常重要的传输层协议。
UDP 是一个无连接的、不可靠的传输层协议，没有丢包重传机制、没有流控机制、没有拥塞控制机制。UDP 不保证数据包的顺序，UDP 传输经常出现乱序，UDP 不对重复包进行过滤。

既然 UDP 这么多缺点，我们还有学习的必要吗？其实不然，正是因为 UDP 没有提供复杂的各种保障机制，才使得它具有实时、高效的传输特性。那么 UDP 到底有哪些优势呢？

* 第一，UDP 是面向消息的传输协议，保证数据包的边界，不需要进行消息解析，处理逻辑非常简单。
* 第二，UDP 具有实时、高效的传输特性。
* 第三，协议栈没有对 UDP 进行过多的干预，这给应用层带来了很多便利，应用程序可以根据自己的需要对传输进行控制。比如，自己实现优先级控制、流量控制、可靠性机制等。当然还有其他一些优势，我就不再一一列举。

UDP 在音视频会议、VOIP、音视频实时通信等行业有着广泛的应用。

[UDP客户端与服务器](pic/UDP客户端与服务器.png)

[UDP Socket示例](javaNetwork/udpSocket)


### 多线程TCP服务器
采用单线程模型、阻塞式模型实现的TCP服务器，java.net.ServerSocket 的 accept 方法会阻塞线程，java.net.Socket 的 recv 和 send 方法也会阻塞线程，
因此，在同一时刻，服务器只能和一个客户端通信。

要想服务器同时和多个客户端进行通信：
* 要么采用非阻塞式 Socket 编程，通过 I/O 多路复用机制 实现此目的；
* 要么采用多线程编程模型。当然，在非阻塞式 Socket 编程模型下，往往也采用多线程编程。

以下示例，主要介绍阻塞式 Socket 编程中常用的两种线程模型：
##### 1，[多线程模型](javaNetwork/tcpSocket/multiThreading)
[多线程模型结构](pic/多线程模型结构.png)

从图中可以看出，每线程模型的程序结构如下：
* 创建一个监听线程，通常会采用 Java 主线程作为监听线程。
* 创建一个 java.net.ServerSocket 实例，调用它的 accept 方法等待客户端的连接。
* 当有新的客户端和服务器建立连接，accept 方法会返回，创建一个新的线程和客户端通信。此时监听线程返回，继续调用 accept 方法，等待新的客户端连接。
* 在新线程中调用 java.net.Socket 的 recv 和 send 方法和客户端进行数据收发。
* 当数据收发完成后，调用 java.net.Socket 的 close 方法关闭连接，同时线程退出。

##### 2，[线程池模型](javaNetwork/tcpSocket/threadPool)
[线程池模型结构](pic/线程池模型结构.png)

从图中可以看出，线程池模型的程序结构如下：

* 创建一个监听线程，通常会采用 Java 主线程作为监听线程。
* 创建一个 java.net.ServerSocket 实例，调用它的 accept 方法等待客户端的连接。
* 服务器预先创建一组线程，叫做线程池。线程池中的线程，在服务运行过程中，一直运行，不会退出。
* 当有新的客户端和服务器建立连接，accept 方法会返回 java.net.Socket 对象，表示新的连接。服务器一般会创建一个处理 java.net.Socket 逻辑的任务，
并且将此任务投递给线程池去处理。然后，监听线程返回，继续调用 accept 方法，等待新的客户端连接。
* 线程池调度空闲的线程去处理任务。
* 在新任务中调用 java.net.Socket 的 recv 和 send 方法和客户端进行数据收发。
* 当数据收发完成后，调用 java.net.Socket 的 close 方法关闭连接，任务完成。
* 线程重新回归线程池，等待调度。

### Java Socket选项
下面，我们对 Socket 编程中常用的 Socket 选项重点介绍。

##### 1,SO_REUSEADDR
TCP 连接关闭过程中，主动关闭的一方会处于 TIME_WAIT 状态，要等待 2MSL 时间。而服务器在工作过程中有可能由于配置的改变而要重启，
或者是由于程序异常奔溃要重新启动。在这种情况下，如果服务器监听的 Socket 处于 TIME_WAIT 状态，那么调用 bind 方法绑定 Socket 就会失败。
如果要等待 2MSL 时间，对于服务器来说是难以接受的。要想解决此问题，需要给监听 Socket 设置 SO_REUSEADDR 选项。

Java 的 java.net.ServerSocket 类提供了 setReuseAddress 方法，可以用以设置 SO_REUSEADDR 选项

##### 2,SO_KEEPALIVE
SO_KEEPALIVE 是协议栈提供的一种连接保活机制，一般是用在 TCP 协议中。主要目的是当通信双方长时间没有数据交互，
然而 Socket还没有被关闭，协议栈会向对方发送一个 Heartbeat 消息期望对方回复一个 ACK，如果对方能回复说明连接是正常的，
如果对方不能回复，尝试几次以后就会关闭连接。系统保活的时间一般是 2 小时。

Java 的 java.net.Socket 类提供了 setKeepAlive 方法，可以用以设置 SO_KEEPALIVE 选项

##### 3,SO_LINGER
SO_LINGER 是用来设置“连接关闭以后，未发送完的数据包还可以在协议栈逗留的时间”。
java.net.Socket 提供了 setSoLinger 方法可以设置 SO_LINGER 选项。原型如下：
```text
public void setSoLinger(boolean on, int linger) throws SocketException

注：参数 linger 的单位是秒。
```
* 如果设置 on 为 false，则该选项的值被忽略，协议栈会采用默认行为。
close 调用会立即返回给调用者，协议栈会尽可能将 Socket 发送缓冲区未发送的数据发送完成。
* 如果设置 on 为 true，但是 linger 为 0，当你调用了close()方法以后，协议栈将丢弃保留在 Socket 发送缓冲区中未发送完的数据，然后向对方发送一个 RST。
这样连接很快会被关闭，不会进入 TIME_WAIT 状态，这也是一个避免“由于大量 TIME_WAIT 状态的 Socket 导致连接失败“的解决办法。
* 如果设置 on 为 true ，但是 linger 的取值大于 0，当你调用了 close() 方法以后，如果 Socket 发送缓冲区还有未发送完的数据，
那么系统会等待一个指定的时间，close() 才返回。注意，这种情况下 close() 方法返回，并不能保证 Socket 发送缓冲区中未发送的数据被成功发送完。

##### 4,SO_RCVBUF
SO_RCVBUF 很好理解，用于设置 Socket 的接收缓冲区大小。TCP 一般不需要设置，UDP 可能需要设置。
java.net.Socket 类提供了 setReceiveBufferSize 方法可以设置接收缓冲区的大小。
    
##### 5,SO_SNDBUF
SO_SNDBUF 也很好理解，用于设置 Socket 的发送缓冲区大小。一般不需要设置，采用系统默认大小即可。
java.net.Socket 类提供了 setSendBufferSize 方法可以设置发送缓冲区的大小。

##### 6,SO_OOBINLINE
SO_OOBINLINE 用于设置将“带外数据”作为普通数据流来处理。
java.net.Socket 类提供了 setOOBInline 方法可以设置 SO_OOBINLINE 选项。

##### 7,TCP_NODELAY
TCP_NODELAY 用于关闭 Nagle 算法，一般是用在实时性要求比较高的场景。
java.net.Socket 提供了 setTcpNoDelay 方法用于设置 TCP_NODELAY 选项。

##### 8,总结
文中列出了常用 Socket 选项的应用场景。
* SO_REUSEADDR 是服务器必须要设置的一个选项，也只有服务器才需要此功能。
* TCP_NODELAY 是在开发实时性要求很高的程序时，必须要设置的，比如音视频通信系统。
* SO_LINGER 是在服务器端解决“由于 TIME_WAIT 过多，导致连接失败的问题”时的一个常用方法。

其他选项，可以根据需要选择是否开启。