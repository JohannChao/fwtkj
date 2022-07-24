# 服务器为何会产生大量的TIME_WAIT连接

### TIME_WAIT 状态：
1. TCP 连接中，主动关闭连接的一方出现的状态；（收到 【FIN,ACK报文】后，进入 TIME_WAIT 状态，并返回 ACK 命令）
2. 保持 2 个 MSL 时间，即4 分钟；（MSL 为 30秒，1分钟，或 2分钟）

### time_wait 状态的影响
* 在socket的TIME_WAIT状态结束之前，该socket所占用的本地端口号将一直无法释放。TCP 端口数量，上限是 6.5w（65535，16 bit）。
* 在高并发（每秒几万qps）并且采用短连接方式进行交互的系统中运行一段时间后，系统中就会存在大量的time_wait状态，如果time_wait状态
把系统所有可用端口都占完了，且尚未被系统回收时，就会出现无法向服务端创建新的socket连接的情况。此时系统几乎停转，任何链接都不能建立。
* 大量的time_wait状态也会占用系统一定的fd，内存和cpu资源，当然这个量一般比较小，并不是主要危害

### 问题分析
其本质原因是，大量的短连接存在，特别是 HTTP 请求中，如果 connection 头部取值被设置为 close 时，基本都由「服务端」发起主动关闭连接。
而TCP 四次挥手关闭连接机制中，为了保证 ACK 重发和丢弃延迟数据，设置 time_wait 为 2 倍的 MSL（报文最大存活时间）

# 优化方式
### 1，调整系统内核参数
修改/etc/sysctl.conf文件，一般涉及下面的几个参数：
```text
1. net.ipv4.tcp_syncookies = 1 
#表示开启SYN Cookies。当出现SYN等待队列溢出时，启用cookies来处理，可防范少量SYN攻击，默认为0，表示关闭；

2. net.ipv4.tcp_tw_reuse = 1 
#表示开启重用。允许将TIME-WAIT sockets重新用于新的TCP连接，默认为0，表示关闭；

3. net.ipv4.tcp_tw_recycle = 1 
#表示开启TCP连接中TIME-WAIT sockets的快速回收，默认为0，表示关闭。

4. net.ipv4.tcp_fin_timeout = ? 
#修改系统默认的 TIMEOUT 时间

5. net.ipv4.tcp_max_tw_buckets = 5000 
#表示系统同时保持TIME_WAIT套接字的最大数量，(默认是18000). 当TIME_WAIT连接数量达到给定的值时，所有的TIME_WAIT连接会被立刻清除，并打印警告信息。
但这种粗暴的清理掉所有的连接，意味着有些连接并没有成功等待2MSL，就会造成通讯异常。一般不建议调整

6. net.ipv4.tcp_timestamps = 1
#(默认即为1)60s内同一源ip主机的socket connect请求中的timestamp必须是递增的。也就是说服务器打开了 tcp_tw_reccycle了，就会检查时间戳，
如果对方发来的包的时间戳是乱跳的或者说时间戳是滞后的，那么服务器就会丢掉不回包，现在很多公司都用LVS做负载均衡，通常是前面一台LVS，
后面多台后端服务器，这其实就是NAT，当请求到达LVS后，它修改地址数据后便转发给后端服务器，但不会修改时间戳数据，对于后端服务器来说，
请求的源地址就是LVS的地址，加上端口会复用，所以从后端服务器的角度看，原本不同客户端的请求经过LVS的转发，就可能会被认为是同一个连接，
加之不同客户端的时间可能不一致，所以就会出现时间戳错乱的现象，于是后面的数据包就被丢弃了，具体的表现通常是是客户端明明发送的SYN，
但服务端就是不响应ACK，还可以通过下面命令来确认数据包不断被丢弃的现象，所以根据情况使用

其他优化：

7. net.ipv4.ip_local_port_range = 1024 65535 
#增加可用端口范围，让系统拥有的更多的端口来建立链接（缺省情况下很小：32768到61000，改为1024到65000）。
这里有个问题需要注意，对于这个设置系统就会从1025~65535这个范围内随机分配端口来用于连接，如果我们服务的使用端口比如8080刚好在这个范围之内，
在升级服务期间，可能会出现8080端口被其他随机分配的链接给占用掉，这个原因也是文章开头提到的端口被占用的另一个原因。

8. net.ipv4.ip_local_reserved_ports = 7005,8001-8100 
#针对上面的问题，我们可以设置这个参数来告诉系统给我们预留哪些端口，不可以用于自动分配。

9. net.ipv4.tcp_keepalive_time = 1200 
#表示当keepalive起用的时候，TCP发送keepalive消息的频度。缺省是2小时，改为20分钟。

10. net.ipv4.tcp_max_syn_backlog = 8192 
#表示SYN队列的长度，默认为1024，加大队列长度为8192，可以容纳更多等待连接的网络连接数。
```
优化完内核参数后，可以执行 sysctl -p 命令，来激活上面的设置永久生效

### 2，调整短链接为长链接
[短连接和长连接](https://www.cnblogs.com/gotodsp/p/6366163.html) 工作方式的区别：
```text
短连接
连接->传输数据->关闭连接
HTTP是无状态的，浏览器和服务器每进行一次HTTP操作，就建立一次连接，但任务结束就中断连接。
也可以这样说：短连接是指SOCKET连接后发送后接收完数据后马上断开连接。

长连接
连接->传输数据->保持连接 -> 传输数据-> 。。。->关闭连接。
长连接指建立SOCKET连接后不管是否使用都保持连接，但安全性较差。
```
从区别上可以看出，长连接比短连接从根本上减少了关闭连接的次数，减少了TIME_WAIT状态的产生数量。
在高并发的系统中，这种方式的改动非常有效果，可以明显减少系统TIME_WAIT的数量。

当使用nginx作为反向代理时，为了支持长连接，需要做到两点：

#### 2.1，从client到nginx的连接是长连接

默认情况下，nginx已经自动开启了对client连接的keep alive支持（同时client发送的HTTP请求要求keep alive）。一般场景可以直接使用，
但是对于一些比较特殊的场景，还是有必要调整个别参数（keepalive_timeout和keepalive_requests）。
```text
http {
    keepalive_timeout  120s 120s;
    keepalive_requests 10000;
}
```
keepalive_timeout: 第一个参数：设置keep-alive客户端连接在服务器端保持开启的超时值（默认75s）；值为0会禁用keep-alive客户端连接；
第二个参数：可选、在响应的header域中设置一个值“Keep-Alive: timeout=time”；通常可以不用设置；

keepalive_requests：keepalive_requests 指令用于设置一个keep-alive连接上可以服务的请求的最大数量，当最大请求数量达到时，连接被关闭。默认是100。
这个参数的真实含义，是指一个keep alive建立之后，nginx就会为这个连接设置一个计数器，记录这个keep alive的长连接上已经接收并处理的客户端请求的数量。
如果达到这个参数设置的最大值时，则nginx会强行关闭这个长连接，逼迫客户端不得不重新建立新的长连接。

#### 2.2，从nginx到server的连接是长连接
为了让nginx和后端server（nginx称为upstream）之间保持长连接，典型设置如下：（默认nginx访问后端都是用的短连接(HTTP1.0)，
一个请求来了，Nginx 新开一个端口和后端建立连接，后端执行完毕后主动关闭该链接）Nginx 1.1以上版本的 upstream 已经支持keep-alive的，
所以我们可以开启Nginx proxy的keep-alive来减少tcp连接：
```text
upstream http_backend {
 server 127.0.0.1:8080;

 keepalive 1000;//设置nginx到upstream服务器的空闲keepalive连接的最大数量
}

server {
 ...

location /http/ {
 proxy_pass http://http_backend;
 proxy_http_version 1.1;//开启长链接
 proxy_set_header Connection "";
 ...
 }
}
```
HTTP协议中对长连接的支持是从1.1版本之后才有的，因此最好通过 proxy_http_version 指令设置为"1.1"，而"Connection" header应该被清理。
清理的意思，是清理从 client 过来的http header，因为即使是client和nginx之间是短连接，nginx和upstream之间也是可以开启长连接的。
这种情况下必须清理来自client请求中的"Connection" header。但这个地方需要注意如果有一些认证鉴权的cookie或者session信息在head里面，
不建议开启此选项，或者对需要保留的header要保存下来，否则这些信息可能会丢掉从而发不到上游upstream的服务器上。

### 补充
#### 1，什么是2MSL
MSL是Maximum Segment Lifetime英文的缩写，中文可以译为“报文最大生存时间”，他是任何报文在网络上存在的最长时间，超过这个时间报文将被丢弃。
因为tcp报文（segment）是ip数据报（datagram）的数据部分，而ip头中有一个TTL域，TTL是time to live的缩写，中文可以译为“生存时间”，
这个生存时间是由源主机设置初始值但不是存的具体时间，而是存储了一个ip数据报可以经过的最大路由数，每经过一个处理他的路由器此值就减1，
当此值为0则数据报将被丢弃，同时发送ICMP报文通知源主机。RFC 793中规定MSL为2分钟，实际应用中常用的是30秒，1分钟和2分钟等。

2MSL即两倍的MSL，TCP的TIME_WAIT状态也称为2MSL等待状态，当TCP的一端发起主动关闭，在发出最后一个ACK包后，即第3次握手完成后发送了第四次握手的ACK包后
就进入了TIME_WAIT状态，必须在此状态上停留两倍的MSL时间，等待2MSL时间主要目的是怕最后一个ACK包对方没收到，那么对方在超时后将重发第三次握手的FIN包，
主动关闭端接到重发的FIN包后可以再发一个ACK应答包。在TIME_WAIT状态时两端的端口不能使用，要等到2MSL时间结束才可继续使用。
当连接处于2MSL等待阶段时任何迟到的报文段都将被丢弃。

不过在实际应用中，可以通过设置SO_REUSEADDR选项，达到不必等待2MSL时间结束，即可使用此端口。

#### 2，time_wait 是「服务器端」的状态？or 「客户端」的状态？
time_wait 是「主动关闭 TCP 连接」一方的状态，可能是「客服端」的，也可能是「服务器端」的。也可以理解为，主动断开方是服务端。

一般情况下，都是「客户端」所处的状态；「服务器端」一般设置「不主动关闭连接」。

但 HTTP 请求中，http 头部 connection 参数，可能设置为 close，则服务端处理完请求会主动关闭 TCP 连接，

#### 3，避免通过设置 SO_LINGER 来减少 TIMW_WAIT 连接
我们可以通过设置 SO_LINGER 来避免主动关闭方处于time-wait状态，但是这其实是通过直接发送RST包关闭连接，而不是通过正常四次挥手的方式
来避免主动关闭方处于time-wait状态，而发送RST报文来关闭连接时，可能会出现数据不完整的现象。





### 参考
1. [解决TIME_WAIT过多造成的问题](https://www.cnblogs.com/dadonggg/p/8778318.html)
2. [如何优化高并发TCP链接中产生的大量的TIME_WAIT的状态](https://cloud.tencent.com/developer/article/1589962)
3. [终于搞懂了服务器为啥产生大量的TIME_WAIT！](https://zhuanlan.zhihu.com/p/415307243)