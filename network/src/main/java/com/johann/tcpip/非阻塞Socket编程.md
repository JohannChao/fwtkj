### 阻塞式与非阻塞式模型
我们以 Linux 系统为例，介绍阻塞式与非阻塞式的概念。Linux 程序的执行模式分为用户态和内核态，应用程序逻辑运行在用户态，访问系统资源的逻辑运行在内核态。其实现代操作系统都是这种模式。

当程序的执行逻辑从用户态切换到内核态时，会引发上下文的切换，会涉及到数据从用户态到内核态，或者是从内核态到用户态拷贝的问题。这时，系统 API 会提供阻塞式和非阻塞式两种调用方式。
比如，我们调用 recv 函数接收 Socket 数据，recv 函数可以选择阻塞式或者是非阻塞式调用模式，不同的模式，编程风格是完全不同。
假如 Socket 的接收缓冲区没有准备好要接收的数据，如果选择阻塞式调用，那么应用线程会被阻塞在 recv 调用上，不能继续执行，线程会处于等待状态，直到系统准备好数据；
如果选择非阻塞式调用，那么应用线程不会被阻塞，recv 函数会立即返回。当系统准备好数据以后，会触发一个读事件，这就要求我们必须通过某种机制监听读事件，一般都是通过 I/O 多路复用机制来解决。

我们通过两张图来感受一下阻塞式和非阻塞式的差异。

[阻塞式](pic/阻塞式编程.png)

[非阻塞式](pic/非阻塞式编程.png)

从以上两张图可以看出，如果 read 函数采用阻塞式调用 ，当内核没有准备好的数据时，应用线程会被阻塞到 read 调用上，进入等待状态，直到有数据可以读取才返回。
如果 read 函数采用非阻塞式调用，当内核没有准备好数据时，read 函数会返回 EAGAIN，线程不会被阻塞。当系统准备好数据以后，会触发一个读事件。

对于逻辑比较简单的场景，比如逻辑简单的客户端程序，可以采用阻塞式编程模型，这样实现简单，容易理解。对于逻辑比较复杂的场景，比如高性能服务器，
必须采用非阻塞式编程模型，而且要配合 I/O 多路复用机制。

### Java 非阻塞式 Socket 编程

介绍 Java 非阻塞式 Socket 编程，就得介绍 Java NIO。Java NIO 是 Java New IO API，有时也解释为 Java Non-blocking IO。通过 Java NIO 可以实现 Java 非阻塞 Socket 编程。

Java NIO 是 Java 1.4 支持的，它将 Socket 数据流抽象为一个 Channel（管道），Socket 数据读写是通过 Channel 实现的，并且提供了 Buffer 机制，提高数据读写的性能。
Java NIO 通常用来编写高性能 Java 服务器程序。在 Java 1.7 以后，Java NIO 对磁盘文件处理得到了增强，可以将 Socket I/O 和 文件 I/O 融合在 Java NIO 中。

#### Java NIO 常用类介绍
```text
ServerSocketChannel ：表示服务端 TCP Socket 的监听 Channel。ServerSocketChannel 提供的工厂方法 open，用于创建它的实例；同时它提供了 accept 
                    方法用于在服务器中接收新的客户端连接请求，返回值是 SocketChannel 类的实例。
                    
SocketChannel : SocketChannel 表示一个 TCP 通信 Channel，可以通过它的 open 方法创建，也可以通过 ServerSocketChannel 的 accept 方法创建。

Selector : Java I/O 事件多路复用机制，用于同时监听多个 Channel 的读、写、监听事件

SelectionKey : 用于表示具体的事件对象

ByteBuffer : 通过 SocketChannel 进行数据读写，依赖 ByteBuffer
```

ServerSocketChannel 和 SocketChannel 同时支持阻塞式和非阻塞式，默认是阻塞式。可以通过如下的方法，打开非阻塞式。
```text
// 配置监听 ServerSocketChannel 为非阻塞模式
ServerSocketChannel serverChannel = ServerSocketChannel.open();
serverChannel.configureBlocking(false);

// 配置服务器新建立的 SocketChannel 为非阻塞模式
SocketChannel newSock = serverChannel.accept();
newSock.configureBlocking(false);


SocketAddress serverAddr = new InetSocketAddress("127.0.0.1", PORT);
SocketChannel sock = SocketChannel.open(serverAddr);
// 配置客户端 SocketChannel 为非阻塞
sock.configureBlocking(false);
```
