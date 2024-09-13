# 第三部分：多机功能
### 16,主从复制
复制功能是Redis提供的多机功能中最基础的一个，这个功能是通过主从复制（master-slave replication）模式实现的，它允许用户为存储着目标数据库的服务器创建出多个拥有相同数据库副本的服务器，其中存储目标数据库的服务器被称为主服务器（master server），而存储数据库副本的服务器则被称为从服务器（slave server，或者称为replica）。

Redis提供的复制功能是通过主从复制模式实现的，一个主服务器可以有多个从服务器，但每个从服务器只能有一个主服务器。即，Redis提供的是单主复制功能，而不是多主复制功能。

在默认情况下，处于复制模式的主服务器既可以执行写操作也可以执行读操作，而从服务器则只能执行读操作。

#### 16.1，Redis复制功能的优点
1. 首先，在性能方面，Redis的复制功能可以给系统的读性能带来线性级别的提升。从理论上来说，用户每增加一倍数量的从服务器，整个系统的读性能就会提升一倍。
2. 其次，通过增加从服务器的数量，用户可以降低系统在遭遇灾难故障时丢失数据的可能性。具体来说，如果用户只有一台服务器存储着目标数据库，那么当这个服务器遭遇灾难故障时，目标数据库很有可能会随着服务器故障而丢失。但如果用户为Redis服务器（即主服务器）设置了从服务器，那么即使主服务器遭遇灾难故障，用户也可以通过从服务器访问数据库。从服务器的数量越多，因为主服务器遭遇灾难故障而出现数据库丢失的可能性就越低。
3. 最后，通过同时使用Redis的复制功能和Sentinel功能，用户可以为整个Redis系统提供高可用特性。具有这一特性的Redis系统在主服务器停机时，将会自动挑选一个从服务器作为新的主服务器，以此来继续为客户提供服务，避免造成整个系统停机。

#### 16.2，REPLICAOF：将服务器设置为从服务器
> 复制命令的命名变化
> 在很长的一段时间里，Redis一直使用SLAVEOF作为复制命令，但是从5.0.0版本开始，Redis正式将SLAVEOF命令改名为REPLICAOF命令并逐渐废弃原来的SLAVEOF命令。

```shell
1, 通过执行REPLICAOF命令，将接收这个命令的Redis服务器设置为另一个Redis服务器的从服务器
        REPLICAOF host port
● 命令的host参数用于指定主服务器的地址，而port参数则用于指定主服务器的端口号。
● 因为Redis的复制操作是以异步方式进行的，所以收到REPLICAOF命令的服务器在记录主服务器的地址和端口之后就会向客户端返回OK，至于实际的复制操作则会在后台开始执行。
示例：
        127.0.0.1:6380> REPLICAOF 127.0.0.1 6379
        OK
● 在接收到REPLICAOF命令之后，主从服务器将执行数据同步操作：从服务器原有的数据将被清空，取而代之的是主服务器传送过来的数据副本。数据同步完成之后，主从服务器将拥有相同的数据。

2, 通过配置选项设置从服务器
        replicaof <masterip> <masterport>
● Windows下，将从服务器的 redis.windows.conf 配置文件中的 'replicaof <masterip> <masterport>' 修改为对应的主服务器地址端口即可。

3, 取消复制
        REPLICAOF no one
● 以上命令可以让从服务器停止复制，重新变回主服务器。
● 服务器在停止复制之后不会清空数据库，而是会继续保留复制产生的所有数据。

4, 通过ROLE命令，查看服务器当前担任的角色
        ROLE
示例：
1）主服务器执行 ROLE 命令
    127.0.0.1:6379> role
    1) "master"
    2) (integer) 996
    3) 1) 1) "127.0.0.1"
        2) "6380"
        3) "982"
    2) 1) "127.0.0.1"
        2) "6381"
        3) "996"
● 数组的第1个元素是字符串"master"，它表示这个服务器的角色为主服务器。
● 数组的第2个元素是这个主服务器的复制偏移量（replication offset），它是一个整数，记录了主服务器目前向复制数据流发送的数据数量。
● 数组的第3个元素是一个数组，它记录了这个主服务器属下的所有从服务器。这个数组的每个元素都由3个子元素组成，第1个子元素为从服务器的IP地址，第2个子元素为从服务器的端口号，而第3个子元素则为从服务器的复制偏移量，记录了从服务器通过复制数据流接收到的复制数据数量。
● 当从服务器的复制偏移量与主服务器的复制偏移量保持一致时，它们的数据就是一致的。

2）从服务器执行 ROLE 命令
    127.0.0.1:6380> role
    1) "slave"
    2) "127.0.0.1"
    3) (integer) 6379
    4) "connected"
    5) (integer) 828
● 数组的第1个元素是字符串"slave"，它表示这个服务器的角色是从服务器。
● 数组的第2个元素和第3个元素记录了这个从服务器正在复制的主服务器的IP地址和端口号。
● 数组的第4个元素是主服务器与从服务器当前的连接状态，这个状态的值及其表示的意义如下：
    ○ "none"：主从服务器尚未建立连接。
    ○ "connect"：主从服务器正在握手。
    ○ "connecting"：主从服务器成功建立了连接。
    ○ "sync"：主从服务器正在进行数据同步。
    ○ "connected"：主从服务器已经进入在线更新状态。
    ○ "unknown"：主从服务器连接状态未知。
● 数组的第5个元素是从服务器当前的复制偏移量。
```

#### 16.3，数据同步
当用户将一个服务器设置为从服务器，让它去复制另一个服务器的时候，主从服务器需要通过数据同步机制来让两个服务器的数据库状态保持一致。

##### 16.3.1），完整同步
当一个Redis服务器接收到REPLICAOF命令，开始对另一个服务器进行复制的时候，主从服务器会执行以下操作：
- 1）主服务器执行BGSAVE命令，生成一个RDB文件，并使用缓冲区存储起在BGSAVE命令之后执行的所有写命令。
- 2）当RDB文件创建完毕，主服务器会通过套接字将RDB文件传送给从服务器。
- 3）从服务器在接收完主服务器传送过来的RDB文件之后，就会载入这个RDB文件，从而获得主服务器在执行BGSAVE命令时的所有数据。
- 4）当从服务器完成RDB文件载入操作，并开始上线接受命令请求时，主服务器就会把之前存储在缓存区中的所有写命令发送给从服务器执行。

这个通过创建、传送并载入RDB文件来达成数据一致的步骤，我们称之为完整同步操作。每个从服务器在刚开始进行复制的时候，都需要与主服务器进行一次完整同步。
> 在进行数据同步时重用RDB文件
> 
> 为了提高数据同步操作的执行效率，如果主服务器在接收到REPLICAOF命令之前已经完成了一次RDB创建操作，并且它的数据库在创建RDB文件之后没有发生过任何变化，那么主服务器将直接向从服务器发送已有的RDB文件，以此来避免无谓的RDB文件生成操作。
>
> 此外，如果在主服务器创建RDB文件期间，有多个从服务器向主服务器发送数据同步请求，那么主服务器将把发送请求的从服务器全部放入队列中，等到RDB文件创建完毕之后，再把它发送给队列中的所有从服务器，以此来复用RDB文件并避免多余的RDB文件创建操作。
##### 16.3.2），在线更新
主从服务器在执行完完整同步操作之后，它们的数据就达到了一致状态，但这种一致并不是永久的。为了让主从服务器的数据一致性可以保持下去，让它们一直拥有相同的数据，Redis会对从服务器进行在线更新：
- 1）每当主服务器执行完一个写命令之后，它就会将相同的写命令或者具有相同效果的写命令发送给从服务器执行。
- 2）因为完整同步之后的主从服务器在执行最新出现的写命令之前，两者的数据库是完全相同的，而导致两者数据库出现不一致的正是最新被执行的写命令，因此从服务器只要接收并执行主服务器发来的写命令，就可以让自己的数据库重新与主服务器数据库保持一致。
>异步更新引起的数据不一致
>
>需要注意的是，因为在线更新是异步进行的，所以在主服务器执行完写命令之后，直到从服务器也执行完相同写命令的这段时间里，主从服务器的数据库将出现短暂的不一致，因此要求强一致性的程序可能需要直接读取主服务器而不是读取从服务器。
>
>此外，因为主服务器可能在执行完写命令并向从服务器发送相同写命令的过程中因故障而下线，所以从服务器在主服务器下线之后可能会丢失主服务器已经执行的一部分写命令，导致从服务器的数据库与下线之前的主服务器数据库处于不一致状态。
##### 16.3.3），部分同步
当因故障下线的从服务器重新上线时，主从服务器的数据通常已经不再一致，因此它们必须重新进行同步，让两者的数据库再次回到一致状态。

在Redis 2.8版本以前，重同步操作是通过直接进行完整同步来实现的，但是这种重同步方法在从服务器只是短暂下线的情况下是非常浪费资源的。

Redis从2.8版本开始使用新的重同步功能去代替原来的重同步功能：
- 1）当一个Redis服务器成为另一个服务器的主服务器时，它会把每个被执行的写命令都记录到一个特定长度的先进先出队列中。
- 2）当断线的从服务器尝试重新连接主服务器的时候，主服务器将检查从服务器断线期间，被执行的那些写命令是否仍然保存在队列里面。如果是，那么主服务器就会直接把从服务器缺失的那些写命令发送给从服务器执行，从服务器通过执行这些写命令就可以重新与主服务器保持一致，这样就避免了重新进行完整同步的麻烦。
- 3）如果从服务器缺失的那些写命令已经不存在于队列当中，那么主从服务器将进行一次完整同步。

部分同步使用到的先进先出队列的体积越大，它能够记录的命令就越多，从服务器断线重连后快速恢复到一致状态的机会就越大。Redis为这个队列设置的默认大小为1MB，用户也可以根据自己的需要，通过配置选项```repl-backlog-size```来修改这个队列的大小。
```shell
        repl-backlog-size 1mb
```

#### 16.4，无须硬盘的复制
Redis从2.8.18版本开始引入无须硬盘的复制特性（diskless replication）：启用了这个特性的主服务器在接收到REPLICAOF命令时将不会再在本地创建RDB文件，而是会派生出一个子进程，然后由子进程通过套接字直接将RDB文件写入从服务器。这样主服务器就可以在不创建RDB文件的情况下，完成与从服务器的数据同步。
```shell
        repl-diskless-sync <yes|no>
要使用无须硬盘的复制特性，我们只需要将repl-diskless-sync配置选项的值设置为yes即可
```
>最后要注意的是，无须硬盘的复制特性只是避免了在主服务器上创建RDB文件，但仍然需要在从服务器上创建RDB文件。

#### 16.5，降低数据不一致情况出现的概率
因为复制的在线更新操作以异步方式进行，所以当主从服务器之间的连接不稳定，或者从服务器未能收到主服务器发送的更新命令时，主从服务器就会出现数据不一致的情况。

为了尽可能地降低数据不一致的出现概率，Redis从2.8版本开始引入了两个以min-replicas开头的配置选项：
```shell
        min-replicas-max-lag <seconds>       
        min-replicas-to-write <numbers>
● 主服务器只会在从服务器的数量大于等于min-replicas-to-write选项的值，并且这些从服务器与主服务器最后一次成功通信的间隔不超过min-replicas-max-lag选项的值时才会执行写命令

示例：
        min-replicas-max-lag 10       
        min-replicas-to-write 3
主服务器只在拥有至少3个从服务器，并且这些从服务器与主服务器最后一次成功通信的间隔不超过10s的情况下才执行写命令
```
通过使用这两个配置选项，我们可以让主服务器只在主从服务器连接良好的情况下执行写命令，但是它只会减少，而无法完全杜绝数据不一致的情况出现。

#### 16.6，可写的从服务器
从Redis 2.6版本开始，Redis的从服务器在默认状态下只允许执行读命令。
```shell
        replica-read-only <yes|no>
将replica-read-only配置选项的值设置为 no 来打开从服务器的写功能
```
>使用可写从服务器的注意事项
>
>1）在主从服务器都可写的情况下，程序必须将写命令发送到正确的服务器上，不能把需要在主服务器执行的写命令发送给从服务器执行，也不能把需要在从服务器执行的写命令发送给主服务器执行，否则就会出现数据错误。
>
>2）从服务器执行写命令得到的数据，可能会被主服务器发送的写命令覆盖。基于这个原因，客户端在从服务器上面执行写命令时，应该尽量避免与主服务器发生键冲突。
>
>3）当从服务器与主服务器进行完整同步时，从服务器数据库包含的所有数据都将被清空，其中包括客户端写入的数据。
>
>4）为了减少内存占用，降低键冲突发生的可能性，并确保主从服务器的数据同步操作可以顺利进行，客户端写入从服务器的数据应该在使用完毕之后尽快删除。一个比较简单的方法是在客户端向从服务器写入数据的同时，为数据设置一个比较短的过期时间，使得这些数据可以在使用完毕之后自动被删除。

#### 16.7，脚本复制
Redis服务器拥有两种不同的脚本复制模式，第一种是从Redis 2.6版本开始支持的脚本传播模式（whole script replication），而另一种则是从Redis 3.2版本开始支持的命令传播模式（script effect replication）。

脚本传播模式是Redis复制脚本时默认使用的模式。

##### 16.7.1，脚本传播模式
处于脚本传播模式的主服务器会将被执行的脚本及其参数（也就是EVAL命令本身）复制到AOF文件以及从服务器中。因为带有副作用的函数在不同服务器上运行时可能会产生不同的结果，从而导致主从服务器不一致，所以在这一模式下执行的脚本必须是纯函数，换句话说，对于相同的数据集，相同的脚本以及参数必须产生相同的效果。

为了保证脚本的纯函数性质，Redis对处于脚本传播模式的Lua脚本设置了以下限制：
* 1）脚本不能访问Lua的时间模块、内部状态或者除给定参数之外的其他外部信息。
* 2）在Redis的命令当中，存在着一部分带有随机性质的命令，这些命令对于相同的数据集以及相同的参数可能会返回不同的结果。如果脚本在执行这类带有随机性质的命令之后，尝试继续执行写命令，那么Redis将拒接执行该命令并返回一个错误。带有随机性质的Redis命令分别为：SPOP、SRANDMEMBER、SCAN、SSCAN、ZSCAN、HSCAN、RANDOMKEY、LASTSAVE、PUBSUB、TIME。
* 3）当用户在脚本中调用SINTER、SUNION、SDIFF、SMEMBERS、HKEYS、HVALS、KEYS这7个会以随机顺序返回结果元素的命令时，为了消除其随机性质，Lua环境在返回这些命令的结果之前会先对结果中包含的元素进行排序，以此来确保命令返回的元素总是有序的。
* 4）Redis会确保每个被执行的脚本都拥有相同的随机数生成器种子，这意味着如果用户不主动修改这一种子，那么所有脚本在默认情况下产生的伪随机数列都将是相同的。
```shell
示例
如果我们在启用了脚本传播模式的主服务器执行以下命令：
        EVAL "redis.call('SET', KEYS[1], 'hello  world'); redis.call('SET', KEYS[2], 10086); redis.call('SADD', KEYS[3], 'apple', 'banana', 'cherry')" 3 'msg' 'number' 'fruits'
那么主服务器将向从服务器发送完全相同的EVAL命令：
        EVAL "redis.call('SET', KEYS[1], 'hello  world'); redis.call('SET', KEYS[2], 10086); redis.call('SADD', KEYS[3], 'apple', 'banana', 'cherry')" 3 'msg' 'number' 'fruits'
```

##### 16.7.2，命令传播模式
处于命令传播模式的主服务器会将执行脚本产生的所有写命令用事务包裹起来，然后将事务复制到AOF文件以及从服务器中。因为命令传播模式复制的是写命令而不是脚本本身，所以即使脚本本身包含副作用，主服务器给所有从服务器复制的写命令仍然是相同的，因此处于命令传播模式的主服务器能够执行带有副作用的非纯函数脚本。

除了脚本可以不是纯函数之外，与脚本传播模式相比，命令传播模式对Lua环境还有以下放松：
- 1）用户可以在执行RANDOMKEY、SRANDMEMBER等带有随机性质的命令之后继续执行写命令。
- 2）脚本的伪随机数生成器在每次调用之前，都会随机地设置种子。换句话说，被执行的每个脚本在默认情况下产生的伪随机数列都是不一样的。

除了以上两点之外，命令传播模式与脚本传播模式的Lua环境限制是一样的，比如，即使在命令传播模式下，脚本还是无法访问Lua的时间模块以及内部状态。

为了开启命令传播模式，用户在使用脚本执行任何写操作之前，需要先在脚本中调用以下函数：```redis.replicate_commands()```。

redis.replicate_commands()只对调用该函数的脚本有效：在使用命令传播模式执行完当前脚本之后，服务器将自动切换回默认的脚本传播模式。
```shell
示例：
如果我们在主服务器执行以下命令：
        EVAL "redis.replicate_commands(); redis.call('SET',  KEYS[1],  'hello  world'); redis.call('SET',  KEYS[2],  10086); redis.call('SADD', KEYS[3], 'apple', 'banana', 'cherry')"  3 'msg' 'number' 'fruits'
那么主服务器将向从服务器复制以下命令：
        MULTI        
        SET "msg" "hello world"       
        SET "number" "10086"        
        SADD "fruits" "apple" "banana" "cherry"        
        EXEC
```

##### 16.7.3，选择性命令传播
为了进一步提升命令传播模式的作用，Redis允许用户在脚本中选择性地打开或者关闭命令传播功能，这一点可以通过在脚本中调用```redis.set_repl()```函数并向它传入以下4个值来完成：
- 1）redis.REPL_ALL --- 默认值，将写命令传播至AOF文件以及所有从服务器。
- 2）redis.REPL_AOF --- 只将写命令传播至AOF文件。
- 3）redis.REPL_SLAVE --- 只将写命令传播至所有从服务器。
- 4）redis.REPL_NONE --- 不传播写命令。

与redis.replicate_commands()函数一样，redis.set_repl()函数也只对执行该函数的脚本有效。用户可以通过这一功能来定制被传播的命令序列，以此来确保只有真正需要的命令才会被传播至AOF文件以及从服务器。
```shell
示例：
1）union_random.Lua脚本
        #打开命令传播模式        
        #以便在执行 SRANDMEMBER 之后继续执行DEL        
        redis.replicate_commands()    

        #第 4）步新增函数调用
        #因为这个脚本即使不向从服务器传播SUNIONSTORE命令和DEL命令        
        #也不会导致主从服务器数据不一致，所以我们可以把命令传播功能关掉        
        redis.set_repl(redis.REPL_NONE)

        #集合键        
        local set_a = KEYS[1]        
        local set_b = KEYS[2]        
        local result_key = KEYS[3]       
        #随机元素的数量        
        local count = tonumber(ARGV[1])        
        #计算并集，随机选出指定数量的并集元素，然后删除并集        
        redis.call('SUNIONSTORE', result_key, set_a, set_b)        
        local elements = redis.call('SRANDMEMBER', result_key, count)        
        redis.call('DEL', result_key)       
        #返回随机选出的并集元素        
        return elements
2）在主服务器执行union_random.Lua脚本
        redis-cli --eval union_random.lua set_a set_b union_random , 3
3）那么主服务器将向从服务器复制以下写命令：
        MULTI        
        SUNIONSTORE "union_random" "set_a" "set_b"        
        DEL "union_random"        
        EXEC
4）仔细观察发现，在这个例子中，即使主服务器不将SUNIONSTORE命令和DEL命令复制给从服务器，主从服务器包含的数据也是相同的。此时，可以在脚本中新增函数"redis.set_repl(redis.REPL_NONE)"，这样主服务器在执行新脚本时将不会向从服务器复制任何命令。
```

##### 16.7.4，模式的选择
既然存在着两种不同的脚本复制模式，那么如何选择正确的模式来复制脚本就显得至关重要了。一般来说，用户可以根据以下情况来判断应该使用哪种复制模式：
- 1）如果脚本的体积不大，执行的计算也不多，但却会产生大量命令调用，那么使用脚本传播模式可以有效地节约网络资源。
- 2）相反，如果一个脚本的体积非常大，执行的计算非常多，但是只会产生少量命令调用，那么使用命令传播模式可以通过重用已有的计算结果来节约计算资源以及网络资源。
```shell
示例：
1）在节日给符合条件的一批用户增加指定数量的金币，脚本如下：
        local user_balance_keys = KEYS        
        local increment = ARGV[1]       
        --遍历所有给定的用户余额键，对它们执行INCRBY操作        
        for i = 1, #user_balance_keys do            
            redis.call('INCRBY', user_balance_keys[i], increment)        
        end
当前脚本，会对所有满足条件的用户执行 'INCRBY'命令，如果采用命令传播模式，将会发送 N 多条命令，耗费大量的网络资源，因此，此时使用脚本传播模式复制这个脚本则会是一件非常容易的事。

2）正在开发一个数据聚合脚本，它包含了一个需要进行大量聚合计算以及大量数据库读写操作的 'aggregate_work()' 函数，脚本如下：
        local result_key = KEYS[1]        
        local aggregate_work =        
            function()            
                #聚合计算具体实现   
            end        
        redis.call('SET', result_key, aggregate_work())
执行 'aggregate_work()'函数需要耗费大量计算资源，所以如果我们直接复制整个脚本，那么相同的操作就要在每个从服务器上面都执行一遍。
如果我们使用命令传播模式来复制这个脚本，那么主服务器在执行完这个脚本之后，就可以通过SET命令直接将函数的计算结果复制给各个从服务器。
```

### 17,哨兵（Sentinel）
使用正常服务器替换下线服务器以维持系统正常运转的操作，一般被称为故障转移（failover）。

为了给Redis服务器提供自动化的故障转移功能，从而提高主从服务器的可用性，Redis为用户提供了Redis Sentinel这一工具。Redis Sentinel可以通过心跳检测的方式监视多个主服务器以及它们属下的所有从服务器，并在某个主服务器下线时自动对其实施故障转移。

#### 17.1，启动Sentinel
Redis Sentinel的程序文件名为redis-sentinel，它通常和普通Redis服务器redis-server位于同一个文件夹。因为用户需要在配置文件中指定想要被Sentinel监视的主服务器，并且Sentinel也需要在配置文件中写入信息以记录主从服务器的状态，所以用户在启动Sentinel的时候必须传入一个可写的配置文件作为参数，就像这样：
```shell
1, 如何启动redis sentinel
        $ redis-sentinel /etc/sentinel.conf
● Redis Sentinel实际上就是一个运行在特殊模式下的Redis服务器，所以用户也可以使用以下命令去启动一个Sentinel：这里的--sentinel参数用于指示Redis服务器进入Sentinel模式，从而变成一个Redis Sentinel而不是普通的Redis服务器。
        redis-server sentinel.conf --sentinel

2, sentinel.conf配置文件参考
# 当前Sentinel服务运行的端口
port 26379
# 哨兵监听的主服务器 后面的1表示主机挂掉以后进行投票，只需要1票就可以从机变主机
# 指定Sentinel要监视的主服务器，同时监视多个主服务器，只需要指定多个 sentinel monitor选项即可
# sentinel monitor <master-name> <ip> <port> <quorum>  
sentinel monitor mymaster 127.0.0.1 6379 2
# 3s内mymaster无响应,则认为mymaster宕机了
# sentinel down-after-milliseconds mymaster 3000
# 如果10秒后,mysater仍没启动过来,则启动failover
# sentinel failover-timeout mymaster 10000
# 执行故障转移时, 最多有1个从服务器同时对新的主服务器进行同步
# sentinel config-epoch mymaster 0
# 设置哨兵sentinel 连接主从的密码 注意必须为主从设置一样的验证密码,没有的话不用设置
# sentinel auth-pass mymaster 123456

3, Sentinel 启动以后，就会去监视主服务器，以及主服务器下的从服务器
示例：
    [115800] 07 Sep 18:24:54.935 # +monitor master mymaster 127.0.0.1 6379 quorum 2
    [115800] 07 Sep 18:24:54.937 * +slave slave 127.0.0.1:6380 127.0.0.1 6380 @ mymaster 127.0.0.1 6379
    [115800] 07 Sep 18:24:54.937 * +slave slave 127.0.0.1:6381 127.0.0.1 6381 @ mymaster 127.0.0.1 6379

4, 处理重新上线的旧主服务器
Sentinel在对下线的主服务器实施故障转移之后，仍然会继续对它进行心跳检测，当这个服务器重新上线的时候，Sentinel将把它转换为当前主服务器的从服务器。

5, 设置从服务器优先级
        replica-priority 100
● replica-priority的默认值为100，这个值越小，从服务器的优先级就越高。
● replica-priority值为0的从服务器永远不会被选为主服务器
● 因为主服务器在下线并重新上线之后也会变成从服务器，所以用户也应该为主服务器设置相应的优先级。  

6, 新主服务器的挑选规则
● 先从候选名单中剔除不符合条件的从服务器：
    1）否决所有已经下线以及长时间没有回复心跳检测的疑似已下线从服务器。
    2）否决所有长时间没有与主服务器通信，数据状态过时的从服务器。
    3）否决所有优先级为0的从服务器。
● 再从剩余的候选服务器中选出新的主服务器：
    1）优先级最高的从服务器获胜。
    2）如果优先级最高的从服务器有两个或以上，那么复制偏移量最大的那个从服务器获胜。   
    3）如果符合上述两个条件的从服务器有两个或以上，那么选出它们当中运行ID（运行ID是服务器启动时自动生成的随机ID，这条规则可以确保条件完全相同的多个从服务器最终得到一个有序的比较结果）最小的那一个。
```

#### 17.2，Sentinel网络

为了避免单点故障，并让Sentinel能够给出真实有效的判断结果，我们可以使用多个Sentinel组建一个分布式Sentinel网络，网络中的各个Sentinel可以通过互通消息来更加准确地判断服务器的状态。

当Sentinel网络中的其中一个Sentinel认为某个主服务器已经下线时，它会将这个主服务器标记为主观下线（Subjectively Down, SDOWN），然后询问网络中的其他Sentinel，是否也认为该服务器已下线。当同意主服务器已下线的Sentinel数量达到sentinel monitor配置选项中quorum参数所指定的数量时，Sentinel就会将相应的主服务器标记为客观下线（objectively down, ODOWN），然后开始对其进行故障转移。

因为Sentinel网络使用客观下线机制来判断一个主服务器是否真的已经下线了，所以为了让这种机制能够有效地运作，用户需要将quorum参数的值设置为Sentinel数量的半数以上，从而形成一种少数服从多数的投票机制。

在实际应用中，用户应该将Sentinel和被监视的Redis服务器放到不同的机器上运行，并且各个Sentinel也应该放到不同的机器上运行，这样Sentinel网络才能够更准确、有效地判断出服务器的实际状态。

#### 17.3，Sentinel管理命令
```shell
1, SENTINEL masters：获取所有被监视主服务器的信息
        SENTINEL masters
● SENTINEL masters命令返回的各个字段及其意义
        字段                                意义
    1)name                              服务器的名字
    2)ip                                服务器ip
    3)port                              服务器端口号
    4)runid                             服务器运行id
    5)flags                             服务器的角色以及状态
    6)link-pending-commands             Sentinel向服务器发送命令以后，仍在等待回复的命令数量
    7)link-refcount                     Redis实例的拥有者数量，用于内部实现
    8)last-ping-sent                    距离Sentinel最后一次向服务器发送 PING 命令之后消逝的毫秒数
    9)last-ok-ping-reply                服务器最后一次向Sentinel返回有效 PING 命令回复之后消逝的毫秒数
    10)last-ping-reply                  服务器最后一次向Sentinel返回 PING 命令回复之后消逝的毫秒数
    11)down-after-milliseconds          Sentinel的 down-after-milliseconds 配置项（N 毫秒内主服务器无响应,则认为主服务器宕机了）
    12)info-refresh                     服务器最后一次向Sentinel返回 INFO 命令回复之后消逝的毫秒数
    13)role-reported                    服务器向Sentinel汇报它自身的角色
    14)role-reported-time               服务器最后一次向Sentinel汇报它自身的角色之后消逝的毫秒数
    15)config-epoch                     当前Sentinel网络所处的配置纪元，用于实现投票机制
    16)num-slaves                       主服务器下的从服务器数量
    17)num-other-sentinels              其他的正在监视这个主服务器的Sentinel数量
    18)quorum                           判断服务器下线所需要的 Sentinel 的数量
    19)failover-timeout                 Sentinel的 failover-timeout 配置项（N 毫秒后,主服务器仍没启动过来,则启动failover）
    20)parallel-syncs                   Sentinel的 parallel-syncs 配置项

2, SENTINEL master：获取指定被监视主服务器的信息
        SENTINEL master <master-name>

3, SENTINEL slaves：获取指定的被监视主服务器的从服务器信息
        SENTINEL slaves <master-name>
● 在SENTINEL slaves命令返回的字段当中，大部分字段都与SENTINEL masters命令返回的字段相同，也有一些它的专属信息项。
        字段                                意义
    1)master-link-down-time             主从服务器连接断开的时长（毫秒）
    2)master-link-status                主从服务器的连接状态
    3)master-host                       主服务器地址
    4)master-port                       主服务器端口号
    5)slave-priority                    从服务器的优先级
    6)slave-repl-offset                 从服务器的复制偏移量

4, SENTINEL sentinels：获取监视同一主服务器的其他所有Sentinel的相关信息
        SENTINEL sentinels <master-name>
● SENTINEL sentinels命令返回的大部分信息项都与SENTINEL masters命令以及SENTINEL slaves命令相同，也有一些Sentinel专有信息项。
        字段                                意义
    1)last-hello-message                距离当前Sentinel最后一次从这个Sentinel那里收到问候信息之后，消逝的毫秒数
    2)voted-leader                      Sentinel网络当前票选出来的首领（leader）。'?'表示目前无首领
    3)voted-leader-epoch                Sentinel首领当前所处的配置纪元

5, SENTINEL get-master-addr-by-name：获取给定主服务器的IP地址和端口号
        SENTINEL get-master-addr-by-name <master-name>
        127.0.0.1:26379> SENTINEL get-master-addr-by-name website_db        
        1) "127.0.0.1"       
        2) "6379"
● 如果Sentinel正在对给定主服务器执行故障转移操作，或者原本的主服务器已经因为故障转移而被新的主服务器替换掉了，那么这个命令将返回新主服务器的IP地址和端口号。

6, SENTINEL ckquorum：检查可用Sentinel的数量 
检查Sentinel网络当前可用的Sentinel数量是否达到了判断主服务器客观下线并实施故障转移所需的数量
        SENTINEL ckquorum <master-name>
        127.0.0.1:26379> sentinel ckquorum mymaster
        OK 3 usable Sentinels. Quorum and failover authorization can be reached

7, SENTINEL reset：重置主服务器状态，返回被重置主服务器的数量
SENTINEL reset命令接受一个glob风格的模式作为参数，接收到该命令的Sentinel将重置所有与给定模式相匹配的主服务器
        SENTINEL reset <pattern>
● 接收到SENTINEL reset命令的Sentinel除了会清理被匹配主服务器的相关信息之外，还会遗忘被匹配主服务器目前已有的所有从服务器，以及正在监视被匹配主服务器的所有其他Sentinel。在此之后，这个Sentinel将会重新搜索正在监视被匹配主服务器的其他Sentinel，以及该服务器属下的各个从服务器，并与它们重新建立连接。
● 因为SENTINEL reset命令可以让Sentinel忘掉主服务器之前的记录，并重新开始对主服务器进行监视，所以它通常只会在Sentinel网络或者被监视主从服务器的结构出现重大变化时使用。

8, SENTINEL failover：强制执行故障转移
可以强制对指定的主服务器实施故障转移，就好像它已经下线了一样
        SENTINEL failover <master-name>
● 接收到这一命令的Sentinel会直接对主服务器执行故障转移操作，而不会像平时那样，先在Sentinel网络中进行投票，然后再根据投票结果决定是否执行故障转移操作。
        127.0.0.1:26379> SENTINEL failover mymaster        
        OK

9, SENTINEL flushconfig：强制写入配置文件
用户可以通过向Sentinel发送以下命令，让Sentinel将它的配置文件重新写入硬盘中
        SENTINEL flushconfig
● 因为Sentinel在被监视服务器的状态发生变化时就会自动重写配置文件，所以这个命令的作用就是在配置文件基于某些原因或错误而丢失时，立即生成一个新的配置文件。此外，当Sentinel的配置选项发生变化时，Sentinel内部也会使用这个命令创建新的配置文件来替换原有的配置文件。
● 注意，只有接收到SENTINEL flushconfig命令的Sentinel才会重写配置文件，Sentinel网络中的其他Sentinel并不会受到这个命令的影响。
```

#### 17.4，在线配置Sentinel
Redis从2.8.4版本开始为SENTINEL命令新添加了一组子命令，这些子命令可以在线地修改Sentinel对于被监视主服务器的配置选项，并把修改之后的配置选项保存到配置文件中，整个过程完全不需要停止Sentinel，也不需要手动修改配置文件，非常方便。

需要注意的是，各个在线配置命令只会对接收到命令的单个Sentinel生效，但并不会对同一个Sentinel网络的其他Sentinel产生影响。

```shell
1, SENTINEL monitor：让Sentinel开始监视一个新的主服务器
        SENTINEL monitor <master-name> <ip> <port> <quorum>
● SENTINEL monitor命令本质上就是SENTINEL monitor配置选项的命令版本，当我们想要让Sentinel监视一个新的主服务器，但是又不想重启Sentinel并手动修改Sentinel配置文件时就可以使用这个命令。

2, SENTINEL remove：取消对给定主服务器的监视
        SENTINEL remove <masters-name>
● 接收到这个命令的Sentinel会停止对给定主服务器的监视，并删除Sentinel内部以及Sentinel配置文件中与给定主服务器有关的所有信息，然后返回OK表示操作执行成功。

3, SENTINEL set：在线修改Sentinel配置文件中与主服务器相关的配置选项值
        SENTINEL set <master-name> <option> <value>
        127.0.0.1:26379> SENTINEL set mymaster quorum 3        
        OK
● 只要是Sentinel配置文件中与主服务器有关的配置选项，都可以使用SENTINEL set命令在线进行配置。命令在成功修改给定的配置选项值之后将返回OK作为结果
● Redis Sentinel允许用户为Sentinel网络中的每个Sentinel分别设置主服务器的quorum值，而不是让所有Sentinel都共享同一个quorum值，这种做法使得用户可以在有需要时，灵活地根据各个Sentinel所处的环境来调整自己的quorum值。
```

### 18,集群（Cluster）
Redis集群是Redis 3.0版本开始正式引入的功能，它给用户带来了在线扩展Redis系统读写性能的能力，而Redis 5.0更是在集群原有功能的基础上，进一步添加了更多新功能，并且对原有功能做了相当多的优化，使得整个集群系统更简单、易用和高效。

#### 18.1，集群特性
Redis集群提供了非常丰富的特性供用户使用，本节将对这些特性做进一步的介绍。

##### 18.1.1，复制和高可用

Redis集群与单机版Redis服务器一样，也提供了主从复制功能。在Redis集群中，各个Redis服务器被称为节点（node），其中主节点（master node）负责处理客户端发送的读写命令请求，而从节点（replica/slave node）则负责对主节点进行复制。

除了复制功能之外，Redis集群还提供了类似于单机版Redis Sentinel的功能，以此来为集群提供高可用特性。简单来说，集群中的各个节点将互相监视各自的运行状况，并在某个主节点下线时，通过提升该节点的从节点为新主节点来继续提供服务。

##### 18.1.2，分片与重分片
与单机版Redis将整个数据库放在同一台服务器上的做法不同，Redis集群通过将数据库分散存储到多个节点上来平衡各个节点的负载压力。

具体来说，Redis集群会将整个数据库空间划分为16384个槽（slot）来实现数据分片（sharding），而集群中的各个主节点则会分别负责处理其中的一部分槽。当用户尝试将一个键存储到集群中时，客户端会先计算出键所属的槽，接着在记录集群节点槽分布的映射表中找出处理该槽的节点，最后再将键存储到相应的节点中。

当用户想要向集群添加新节点时，只需要向Redis集群发送几条简单的命令，集群就会将相应的槽以及槽中存储的数据迁移至新节点。与此类似，当用户想要从集群中移除已存在的节点时，被移除的节点也会将自己负责处理的槽以及槽中数据转交给集群中的其他节点负责。

最重要的是，无论是向集群添加新节点还是从集群中移除已有节点，整个重分片（reshard）过程都可以在线进行，Redis集群无须因此而停机。

##### 18.1.3，高性能

Redis集群采用无代理模式，客户端发送的所有命令都会直接交由节点执行，并且对于经过优化的集群客户端来说，客户端发送的命令在绝大部分情况下都不需要实施转向，或者仅需要一次转向，因此在Redis集群中执行命令的性能与在单机Redis服务器上执行命令的性能非常接近。

除了节点之间互通信息带来的性能损耗之外，单个Redis集群节点处理命令请求的性能与单个Redis服务器处理命令请求的性能几乎别无二致。从理论上来讲，集群每增加一倍数量的主节点，集群对于命令请求的处理性能就会提高一倍。

#### 18.2，搭建集群
要使用Redis集群，首先要做的就是搭建一个完整的集群，Redis为此提供了两种方法：一种是使用源码附带的集群自动搭建程序，另一种则是使用配置文件手动搭建集群。

##### 18.2.1，快速搭建集群
Redis在它的源码中附带了集群自动搭建程序create-cluster，这个程序可以快速构建起一个完整可用的集群以供用户测试。

create-cluster程序位于源码的utils/create-cluster/create-cluster位置，通过不给定任何参数来执行它，我们可以看到该程序的具体用法：
```shell
        $ ./create-cluster
        Usage: ./create-cluster [start|create|stop|watch|tail|clean]
        start         # Launch Redis Cluster instances.
        create        # Create a cluster using redis-cli --cluster create.
        stop          # Stop Redis Cluster instances.
        watch         # Show CLUSTER NODES output (first 30 lines) of first node.
        tail <id>    # Run tail -f of instance at base port + ID.
        clean         # Remove all instances data, logs, configs.
        clean-logs   # Remove just instances logs.

1，首先，我们可以通过执行start命令来创建出6个节点，这6个节点的IP地址都为本机，而端口号则为30001～30006：
        $ ./create-cluster start
        Starting 30001
        Starting 30002
        Starting 30003
        Starting 30004
        Starting 30005
        Starting 30006
2，接着，我们需要使用create命令，把上述6个节点组合成一个集群，其中包含3个主节点和3个从节点：
        $ ./create-cluster create
        >>> Performing hash slots allocation on 6 nodes...
        Master[0] -> Slots 0-5460
        Master[1] -> Slots 5461-10922     
        Master[2] -> Slots 10923-16383
        Adding replica 127.0.0.1:30004 to 127.0.0.1:30001
        Adding replica 127.0.0.1:30005 to 127.0.0.1:30002
        Adding replica 127.0.0.1:30006 to 127.0.0.1:30003
        >>> Trying to optimize slaves allocation for anti-affinity
        [WARNING] Some slaves are in the same host as their master
        M: 9e2ee45f2a78b0d5ab65cbc0c97d40262b47159f 127.0.0.1:30001
            slots:[0-5460] (5461 slots) master
        M: b2c7a5ca5fa6de72ac2842a2196ab2f4a5c82a6a 127.0.0.1:30002
            slots:[5461-10922] (5462 slots) master
        M: a80b64eedcd15329bc0dc7b71652ecddccf6afe8127.0.0.1:30003
            slots:[10923-16383] (5461 slots) master
        S: ab0b79f233efa0afa467d9ef1700fe5b24154992127.0.0.1:30004
            replicates a80b64eedcd15329bc0dc7b71652ecddccf6afe8
        S: f584b888fcc0e7648bd838cb3b0e2d1915ac0ad7127.0.0.1:30005
            replicates 9e2ee45f2a78b0d5ab65cbc0c97d40262b47159f
        S: 262acdf22f4adb6a20b8116982f2940890693d0b 127.0.0.1:30006
            replicates b2c7a5ca5fa6de72ac2842a2196ab2f4a5c82a6a
        Can I set the above configuration? (type 'yes' to accept):  、
3，create命令会根据现有的节点制定出一个相应的角色和槽分配计划，然后询问你的意见。以上面打印出的计划为例：
    ● 节点30001、30002和30003将被设置为主节点，并且分别负责槽0～5460、槽5461～10922和槽10923～16383。
    ● 节点30004、30005和30006分别被设置为以上3个主节点的从节点。 
4，如果你同意程序给出的这个分配计划，那么只需要输入yes并按下Enter键，程序就会按计划组建集群了。
5，连接并使用集群
        # 连接本机端口30001上的集群节点，并向它发送PING命令
        $ redis-cli -c -p 30001
        127.0.0.1:30001> PING
        PONG
如果接收到命令请求的节点并非负责处理命令所指键的节点，那么客户端将根据节点提示的转向信息再次向正确的节点发送命令请求，Redis集群把这个动作称为“转向”（redirect）
        # 发送至节点30001的命令请求被转向节点30002
        127.0.0.1:30001> SET msg "hi"
        ->  Redirected  to  slot  [6257]  located  at
        127.0.0.1:30002
        OK
如果客户端发送的命令请求正好是由接收命令请求的节点负责处理，那么节点将直接向客户端返回命令执行结果，就像平时向单机服务器发送命令请求一样：
        # 因为键number所属的槽7743正好是由节点30002负责
        # 所以命令请求可以在不转向的情况下直接执行
        127.0.0.1:30002> SET number 10086
        OK
6，最后，在使用完这个测试集群之后，我们可以通过以下命令关闭集群并清理各个集群节点的相关信息：
        $ ./create-cluster stop
        Stopping 30001
        Stopping 30002
        Stopping 30003
        Stopping 30004
        Stopping 30005
        Stopping 30006

       $ ./create-cluster clean
```

##### 18.2.2，手动搭建集群
快速搭建的集群通常只能够用于测试，但是无法应用在实际的生产环境中。为了搭建真正能够在生产环境中使用的Redis集群，我们需要创建相应的配置文件，并使用集群管理命令对集群进行配置和管理。

为了保证集群的各项功能可以正常运转，一个集群至少需要3个主节点和3个从节点。

本次我们将搭建一个由5个主节点和5个从节点组成的Redis集群，为此，我们需要先创建出10个文件夹，用于存放相应节点的数据以及配置文件：
```shell
        # 集群节点 redis.conf 配置文件
        cluster-enabled yes    # cluster-enabled选项的值为yes表示将Redis实例设置成集群节点而不是单机服务器
        port 30001             # port选项则用于为每个节点设置不同的端口号。在本例中，我们为10个节点分别设置了从30001～30010的端口号。

1，启动各个文件夹中的集群节点：
        redis-server redis.cluster.conf 

2，集群节点启动以后，这些集群并未互联互通，因此，我们接下来要做的就是连接这10个集群节点并为它们分配槽，这可以通过执行以下命令来完成：
        redis-cli --cluster create 127.0.0.1:30001 127.0.0.1:30002 127.0.0.1:30003 127.0.0.1:30004 127.0.0.1:30005 127.0.0.1:30006 127.0.0.1:30007 127.0.0.1:30008 --cluster-replicas 1
● redis-cli --cluster是Redis客户端附带的集群管理工具，它的create子命令接受任意多个节点的IP地址和端口号作为参数，然后使用这些节点组建起一个Redis集群。
● create子命令允许使用多个可选参数，其中可选参数cluster-replicas用于指定集群中每个主节点的从节点数量。
● 为了方便演示手动搭建集群的方法，我们把集群的所有节点都放在了同一台主机上面，但是在实际的生产环境中，多个节点通常会分布在多台主机之上，而不是集中在同一台主机中。
```

#### 18.3，散列标签
在默认情况下，Redis将根据用户输入的整个键计算出该键所属的槽，然后将键存储到相应的槽中。但是在某些情况下，出于性能方面的考虑，或者为了在同一个节点上对多个相关联的键执行批量操作，我们也会想要将一些原本不属于同一个槽的键放到相同的槽里面。

为了满足这一需求，Redis为用户提供了散列标签（hash tag）功能，该功能会找出键中第一个被大括号{}包围并且非空的字符串子串（sub string），然后根据子串计算出该键所属的槽。
    1）第一个大括号包围的字符串；
    2）包围的字符串是非空。
```shell
        # 使用CLUSTER KEYSLOT命令查看给定键所属的槽
       127.0.0.1:30002> CLUSTER KEYSLOT user::10086
        (integer) 14982  #该键属于14982槽
       127.0.0.1:30002> CLUSTER KEYSLOT user::10087
        (integer) 10919  #该键属于10919槽

        #使用散列标签功能，查看给定键所属槽
        127.0.0.1:30002> CLUSTER KEYSLOT {user}::10086
        (integer) 5474
       127.0.0.1:30002> CLUSTER KEYSLOT {user}::10087
        (integer) 5474       
● 虽然从逻辑上来讲，我们把user::10086和{user}::10086看作同一个键，但由于散列标签只是Redis集群对键名的一种特殊解释，因此这两个键在实际中并不相同，它们可以同时存在于数据库，并且不会引发任何键冲突。
```

#### 18.4，打开/关闭从节点的读命令执行权限
集群的从节点在默认情况下只会对主节点进行复制，但是不会处理客户端发送的任何命令请求：每当从节点接收到命令请求的时候，它只会向客户端发送转向消息，引导客户端向某个主节点重新发送命令请求。
```shell
        127.0.0.1:30005> GET num
        -> Redirected to slot [2765] located at 127.0.0.1:30001
        "10086"

        127.0.0.1:30001>
```

##### 18.4.1，READONLY：打开读命令执行权限
```shell
        127.0.0.1:30005> READONLY
        OK

       127.0.0.1:30005> GET num
        "10086"
● READONLY命令只对执行了该命令的客户端有效，它并不影响正在访问相同从节点的其他客户端。
```

##### 18.4.2，READWRITE：关闭读命令执行权限
```shell
        127.0.0.1:30005> READWRITE
        OK

       127.0.0.1:30005> GET num
        -> Redirected to slot [2765] located at 127.0.0.1:30001
        "10086"

       127.0.0.1:30001>
```

#### 18.5，集群管理工具redis-cli
Redis官方为管理集群提供了两种工具，一种是redis-cli客户端附带的集群管理程序，而另一种则是Redis内置的集群管理命令。

本节中我们将对redis-cli客户端附带的集群管理程序做详细的介绍。

```shell
        $ redis-cli --cluster help
        Cluster Manager Commands:
          create            host1:port1 ... hostN:portN
                          --cluster-replicas <arg>
          check             host:port
          info              host:port
          fix                host:port
          reshard          host:port
                          --cluster-from <arg>
                          --cluster-to <arg>
                          --cluster-slots <arg>
                          --cluster-yes
                          --cluster-timeout <arg>
                          --cluster-pipeline <arg>
          rebalance        host:port
                          --cluster-weight <node1=w1...nodeN=wN>
                          --cluster-use-empty-masters
                          --cluster-timeout <arg>
                          --cluster-simulate
                          --cluster-pipeline <arg>
                          --cluster-threshold <arg>
          add-node         new_host:new_port existing_host:existing_port
                          --cluster-slave
                          --cluster-master-id <arg>
          del-node         host:port node_id
          call              host:port command arg arg .. arg
          set-timeout     host:port milliseconds
          import            host:port
                          --cluster-from <arg>
                          --cluster-copy
                          --cluster-replace
          help
```
##### 18.5.1，创建集群
```shell
前文中的手动创建集群的命令：
        create <ip1>:<port1> ... <ipN>:<portN>
        
        redis-cli --cluster create 127.0.0.1:30001 127.0.0.1:30002 127.0.0.1:30003 127.0.0.1:30004 127.0.0.1:30005 127.0.0.1:30006 127.0.0.1:30007 127.0.0.1:30008 --cluster-replicas 1

        --cluster-replicas <num>    #每个主节点配备多少个从节点
```
##### 18.5.2，查看集群信息
```shell
        info <ip>:<port>    #用户需要向命令提供集群中任意一个节点的地址作为参数

        $ redis-cli --cluster info 127.0.0.1:30001
        127.0.0.1:30001 (4979f858...) -> 1 keys | 5461 slots | 1 slaves.
        127.0.0.1:30002 (4ff303d9...) -> 4 keys | 5462 slots | 1 slaves.
        127.0.0.1:30003 (07e23080...) -> 3 keys | 5461 slots | 1 slaves.
        [OK] 8 keys in 3 masters.
        0.00 keys per slot on average.

命令返回信息
● 主节点的地址以及运行ID，它们存储的键数量以及负责的槽数量，以及它们拥有的从节点数量。
● 集群包含的数据库键数量以及主节点数量，以及每个槽平均存储的键数量。
```
##### 18.5.3，检查集群
```shell
通过cluster选项的check子命令，用户可以检查集群的配置是否正确，以及全部16384个槽是否已经全部指派给了主节点。与info子命令一样，check子命令也接受集群其中一个节点的地址作为参数：
        check <ip>:<port>

        $ redis-cli --cluster check 127.0.0.1:30001
        127.0.0.1:30001 (4979f858...) -> 0 keys | 5461 slots | 1 slaves.
        127.0.0.1:30002 (4ff303d9...) -> 0 keys | 5462 slots | 1 slaves.
        127.0.0.1:30003 (07e23080...) -> 0 keys | 5461 slots | 1 slaves.
        [OK] 0 keys in 3 masters.
        0.00 keys per slot on average.
        >>> Performing Cluster Check (using node 127.0.0.1:30001)
        M: 4979f8583676c46039672fb7319e917e4b303707 127.0.0.1:30001
            slots:[0-5460] (5461 slots) master
            1 additional replica(s)
        S: 4788fd4d92387fc5d38a2cd12f0c0d80fc0f6609 127.0.0.1:30004
            slots: (0 slots) slave
            replicates 4979f8583676c46039672fb7319e917e4b303707
        S: b45a7f4355ea733a3177b89654c10f9c31092e92 127.0.0.1:30005
            slots: (0 slots) slave
            replicates 4ff303d96f5c7436ce8ce2fa6e306272e82cd454
        S: 7c56ffba63e3758bc4c2e9b6a55caf294bb21650 127.0.0.1:30006
            slots: (0 slots) slave
            replicates 07e230805903e4e1657743a2e4d8811a59e2f32f
        M: 4ff303d96f5c7436ce8ce2fa6e306272e82cd454 127.0.0.1:30002
            slots:[5461-10922] (5462 slots) master
            1 additional replica(s)
        M: 07e230805903e4e1657743a2e4d8811a59e2f32f 127.0.0.1:30003
            slots:[10923-16383] (5461 slots) master
            1 additional replica(s)
        [OK] All nodes agree about slots configuration.
        >>> Check for open slots...
        >>> Check slots coverage...
        [OK] All 16384 slots covered.
```
##### 18.5.4，修复槽错误
```shell
当集群在重分片、负载均衡或者槽迁移的过程中出现错误时，执行cluster选项的fix子命令，可以让操作涉及的槽重新回到正常状态：
        fix <ip>:<port>
fix命令会检查各个节点中处于“导入中”和“迁移中”状态的槽，并根据情况，将槽迁移至更合理的一方。
        
举个例子，假设我们现在正在将节点30001的5460槽迁移至节点30002，并且为了模拟迁移中断导致出错的情况，我们在迁移完成之后并没有清理相应节点的“导入中”和“迁移中”状态。
        $ redis-cli --cluster fix 127.0.0.1:30001
        127.0.0.1:30001 (4979f858...) -> 1 keys | 5461 slots | 1 slaves.
        127.0.0.1:30002 (4ff303d9...) -> 4 keys | 5462 slots | 1 slaves.
        127.0.0.1:30003 (07e23080...) -> 3 keys | 5461 slots | 1 slaves.
        [OK] 8 keys in 3 masters.
        0.00 keys per slot on average.
        >>> Performing Cluster Check (using node 127.0.0.1:30001)
        M: 4979f8583676c46039672fb7319e917e4b303707127.0.0.1:30001
            slots:[0-5460] (5461 slots) master
            1 additional replica(s)
        S: 4788fd4d92387fc5d38a2cd12f0c0d80fc0f6609127.0.0.1:30004
            slots: (0 slots) slave
            replicates 4979f8583676c46039672fb7319e917e4b303707
        S: b45a7f4355ea733a3177b89654c10f9c31092e92127.0.0.1:30005
            slots: (0 slots) slave
            replicates 4ff303d96f5c7436ce8ce2fa6e306272e82cd454
        S: 7c56ffba63e3758bc4c2e9b6a55caf294bb21650127.0.0.1:30006
            slots: (0 slots) slave
            replicates 07e230805903e4e1657743a2e4d8811a59e2f32f
        M: 4ff303d96f5c7436ce8ce2fa6e306272e82cd454127.0.0.1:30002
            slots:[5461-10922] (5462 slots) master
            1 additional replica(s)
        M: 07e230805903e4e1657743a2e4d8811a59e2f32f 127.0.0.1:30003
            slots:[10923-16383] (5461 slots) master
            1 additional replica(s)
        [ERR]Nodes dont agree about configuration!
        >>>Check for open slots...
        [WARNING]Node 127.0.0.1:30001 has slots in migrating state 5460.
        [WARNING]Node 127.0.0.1:30002 has slots in importing state 5460.
        [WARNING]The following slots are open: 5460.
        >>>Fixing open slot 5460
        Set as migrating in: :30001
        Set as importing in: 127.0.0.1:30002
        Moving slot 5460 from 127.0.0.1:30001 to 127.0.0.1:30002:
        >>> Check slots coverage...
        [OK] All 16384 slots covered.

如果fix命令在检查集群之后没有发现任何异常，那么它将不做其他动作，直接退出。
```
##### 18.5.5，重分片
```shell
通过cluster选项的reshard子命令，用户可以将指定数量的槽从原节点迁移至目标节点，被迁移的槽将交由后者负责，并且槽中已有的数据也会陆续从原节点转移至目标节点:
        reshard <ip>:<port>
                --cluster-from <id>                # 源节点的ID
                --cluster-to <id>                  # 目标节点的ID
                --cluster-slots <num>             # 需要迁移的槽数量
                --cluster-yes                       # 直接确认
                --cluster-timeout <time>          # 迁移的最大时限
                --cluster-pipeline <yes/no>      # 是否使用流水线

假设现在节点30001正在负责从槽0到槽5460的5461个槽，而我们又想将其中的10个槽迁移至节点30007，那么可以执行以下命令：
        $  redis-cli  --cluster  reshard  127.0.0.1:30001  --cluster-from  4979f8583676c46039672fb7319e917e4b303707  --cluster-to  1cd87d132101893b7aa2b81cf333b2f7be9e1b75 --cluster-slots 10

        >>> Performing Cluster Check (using node 127.0.0.1:30001)
        M: 4979f8583676c46039672fb7319e917e4b303707127.0.0.1:30001
            slots:[0-5460] (5461 slots) master
            1 additional replica(s)
        S: 4788fd4d92387fc5d38a2cd12f0c0d80fc0f6609127.0.0.1:30004
            slots: (0 slots) slave
            replicates 4979f8583676c46039672fb7319e917e4b303707
        S: b45a7f4355ea733a3177b89654c10f9c31092e92127.0.0.1:30005
            slots: (0 slots) slave
            replicates 4ff303d96f5c7436ce8ce2fa6e306272e82cd454
        M: 1cd87d132101893b7aa2b81cf333b2f7be9e1b75127.0.0.1:30007
            slots: (0 slots) master
        S: 7c56ffba63e3758bc4c2e9b6a55caf294bb21650127.0.0.1:30006
            slots: (0 slots) slave
            replicates 07e230805903e4e1657743a2e4d8811a59e2f32f
        M: 4ff303d96f5c7436ce8ce2fa6e306272e82cd454127.0.0.1:30002
            slots:[5461-10922] (5462 slots) master
            1 additional replica(s)
        M: 07e230805903e4e1657743a2e4d8811a59e2f32f 127.0.0.1:30003
            slots:[10923-16383] (5461 slots) master
            1 additional replica(s)
        [OK] All nodes agree about slots configuration.
        >>> Check for open slots...
        >>> Check slots coverage...
        [OK] All 16384 slots covered.

       Readytomove10slots.
          Sourcenodes:
              M: 4979f8583676c46039672fb7319e917e4b303707 127.0.0.1:30001
                slots:[0-5460](5461slots)master
                1additionalreplica(s)
          Destinationnode:
              M: 1cd87d132101893b7aa2b81cf333b2f7be9e1b75 127.0.0.1:30007
                slots: (0slots)master
          Reshardingplan:
              Moving slot0 from 4979f8583676c46039672fb7319e917e4b303707
              Movings lot1 from 4979f8583676c46039672fb7319e917e4b303707
              Movings lot2 from 4979f8583676c46039672fb7319e917e4b303707
              Movings lot3 from 4979f8583676c46039672fb7319e917e4b303707
              Movings lot4 from 4979f8583676c46039672fb7319e917e4b303707
              Movings lot5 from 4979f8583676c46039672fb7319e917e4b303707
              Movings lot6 from 4979f8583676c46039672fb7319e917e4b303707
              Movings lot7 from 4979f8583676c46039672fb7319e917e4b303707
              Movings lot8 from 4979f8583676c46039672fb7319e917e4b303707
              Movings lot9 from 4979f8583676c46039672fb7319e917e4b303707
        Do you want to proceed with the proposed reshard plan (yes/no)?

可以看到，reshard命令会先用check子命令检查一次集群，确保集群和槽都处于正常状态，然后再给出一个重分片计划，并询问我们的意见。在输入yes并按下Enter键之后，命令就会实施预定好的重分片计划：
        Do you want to proceed with the proposed reshard plan (yes/no)? yes
        Moving slot 0 from 127.0.0.1:30001 to 127.0.0.1:30007:
        Moving slot 1 from 127.0.0.1:30001 to 127.0.0.1:30007:
        Moving slot 2 from 127.0.0.1:30001 to 127.0.0.1:30007:
        Moving slot 3 from 127.0.0.1:30001 to 127.0.0.1:30007:
        Moving slot 4 from 127.0.0.1:30001 to 127.0.0.1:30007:
        Moving slot 5 from 127.0.0.1:30001 to 127.0.0.1:30007:
        Moving slot 6 from 127.0.0.1:30001 to 127.0.0.1:30007:
        Moving slot 7 from 127.0.0.1:30001 to 127.0.0.1:30007:
        Moving slot 8 from 127.0.0.1:30001 to 127.0.0.1:30007:
        Moving slot 9 from 127.0.0.1:30001 to 127.0.0.1:30007:

通过执行info子命令，我们可以确认，之前指定的10个槽已经被迁移并指派给了节点30007负责：
        $ redis-cli --cluster info 127.0.0.1:30001
        127.0.0.1:30001 (4979f858...) -> 1 keys | 5451 slots | 1 slaves.
        127.0.0.1:30007(1cd87d13...)->0keys|10slots   |0slaves.
        127.0.0.1:30002 (4ff303d9...) -> 4 keys | 5462 slots | 1 slaves.
        127.0.0.1:30003 (07e23080...) -> 3 keys | 5461 slots | 1 slaves.
```
##### 18.5.6，负载均衡
```shell
cluster选项的rebalance子命令允许用户在有需要时重新分配各个节点负责的槽数量，从而使得各个节点的负载压力趋于平衡：
        rebalance <ip>:<port>

举个例子，假设我们现在的集群有30001、30002和30003这3个主节点，它们分别被分配了2000、11384和3000个槽：
        $ redis-cli --cluster info 127.0.0.1:30001
        127.0.0.1:30001 (61d1f17b...) -> 0 keys | 2000 slots | 1 slaves.
        127.0.0.1:30002 (101fbae9...) -> 0 keys | 11384 slots | 1 slaves.
        127.0.0.1:30003 (31822e0a...) -> 0 keys | 3000 slots | 1 slaves.
        [OK] 0 keys in 3 masters.
        0.00 keys per slot on average.

因为节点30002负责的槽数量明显比其他两个节点要多，所以它的负载将比其他两个节点要重。为了解决这个问题，我们需要执行以下这个命令，对3个节点的槽数量进行平衡：
        $ redis-cli --cluster rebalance 127.0.0.1:30001
        >>> Performing Cluster Check (using node 127.0.0.1:30001)
        [OK] All nodes agree about slots configuration.
        >>> Check for open slots...
        >>> Check slots coverage...
        [OK] All 16384 slots covered.
        >>>Rebalancing across 3 nodes.Total weight=3.00
        Moving 3462 slots from 127.0.0.1:30002 to 127.0.0.1:30001
        #####...#####
        Moving 2461 slots from 127.0.0.1:30002 to 127.0.0.1:30003
        #####...#####

在rebalance命令执行之后，3个节点的槽数量将趋于平均：
        $ redis-cli --cluster info 127.0.0.1:30001
        127.0.0.1:30001 (61d1f17b...) -> 0 keys | 5462 slots | 1 slaves.
        127.0.0.1:30002 (101fbae9...) -> 0 keys | 5461 slots | 1 slaves.
        127.0.0.1:30003 (31822e0a...) -> 0 keys | 5461 slots | 1 slaves.
        [OK] 0 keys in 3 masters.
        0.00 keys per slot on average.

rebalance命令提供了很多可选项，它们可以让用户更精确地控制负载均衡操作的具体行为。
        # 通过以下可选项，用户可以为不同的节点设置不同的权重，而权重较大的节点将被指派更多槽。
        # 在没有显式地指定权重的情况下，每个节点的默认权重为1.0。将一个节点的权重设置为0将导致它被撤销所有槽指派，成为一个空节点。
        --cluster-weight <node_id1>=<weight1> <node_id2>=<weight2> ...

        #在执行负载均衡操作时，想要为尚未被指派槽的空节点也分配相应的槽，那么可以使用以下可选项
        --cluster-use-empty-masters

rebalance命令在执行时会根据各个节点目前负责的槽数量以及用户给定的权重计算出每个节点应该负责的槽数量（期望槽数量），如果这个槽数量与节点目前负责的槽数量之间的比率超过了指定的阈值，那么就会触发槽的重分配操作。触发重分配操作的阈值默认为2.0，也就是期望槽数量与实际槽数量之间不能相差超过两倍。
        # 通过以下可选项来指定自己想要的阈值
        --cluster-threshold <value>

        # 通过以下可选项来设置负载均衡操作是否使用流水线
        --cluster-pipeline <yes/no>

        # 通过以下可选项设置负载均衡操作的最大可执行时限
        --cluster-timeout <time>

        # rebalance命令在执行负载均衡操作的时候，通常会一个接一个地对节点的槽数量进行调整，但如果用户想要同时对多个节点实施调整，那么只需要给定以下可选项即可
        --cluster-simulate
```
##### 18.5.7，添加节点
```shell
cluster选项的add-node子命令允许用户将给定的新节点添加到已有的集群当中，用户只需要依次给定新节点的地址以及集群中某个节点的地址即可：
        add-node <new_host>:<port> <existing_host>:<port>

在默认情况下，add-node命令添加的新节点将作为主节点存在。如果用户想要添加的新节点为从节点，那么可以在执行命令的同时，通过给定以下两个可选项来将新节点设置为从节点：
        --cluster-slave
        --cluster-master-id <id>

        $ redis-cli --cluster add-node 127.0.0.1:30007 127.0.0.1:30001
        >>> Adding node 127.0.0.1:30007 to cluster 127.0.0.1:30001
        >>> Performing Cluster Check (using node 127.0.0.1:30001)
        M: 4979f8583676c46039672fb7319e917e4b303707127.0.0.1:30001
            slots:[0-5460] (5461 slots) master
            1 additional replica(s)
        S: 4788fd4d92387fc5d38a2cd12f0c0d80fc0f6609127.0.0.1:30004
            slots: (0 slots) slave
            replicates 4979f8583676c46039672fb7319e917e4b303707
        S: b45a7f4355ea733a3177b89654c10f9c31092e92127.0.0.1:30005
            slots: (0 slots) slave
            replicates 4ff303d96f5c7436ce8ce2fa6e306272e82cd454
        S: 7c56ffba63e3758bc4c2e9b6a55caf294bb21650127.0.0.1:30006
            slots: (0 slots) slave
            replicates 07e230805903e4e1657743a2e4d8811a59e2f32f
        M: 4ff303d96f5c7436ce8ce2fa6e306272e82cd454127.0.0.1:30002
            slots:[5461-10922] (5462 slots) master
            1 additional replica(s)
        M: 07e230805903e4e1657743a2e4d8811a59e2f32f 127.0.0.1:30003
            slots:[10923-16383] (5461 slots) master
            1 additional replica(s)
        [OK] All nodes agree about slots configuration.
        >>> Check for open slots...
        >>> Check slots coverage...
        [OK] All 16384 slots covered.
        >>>Send CLUSTERMEET to node 127.0.0.1:30007 to make it join the cluster.
        [OK]New node added correctly.
```
##### 18.5.8，移除节点
```shell
当用户不再需要集群中的某个节点时，可以通过cluster选项的del-node子命令来移除该节点：
        del-node <ip>:<port> <node_id>
        $ redis-cli --cluster del-node 127.0.0.1:30001 e1971eef02709cf4698a6fcb09935a9
    10892ab3b
        >>>  Removing  node  e1971eef02709cf4698a6fcb09935a910982ab3b  from  cluster
    127.0.0.1:30001
        >>> Sending CLUSTER FORGET messages to the cluster...
        >>> SHUTDOWN the node.
```
##### 18.5.9，执行命令
```shell
通过cluster选项的call子命令，用户可以在整个集群的所有节点上执行给定的命令：
        call host:port command arg arg .. arg

        $ redis-cli --cluster call 127.0.0.1:30001 PING
        >>> Calling PING
        127.0.0.1:30001: PONG
       127.0.0.1:30004: PONG
       127.0.0.1:30005: PONG
       127.0.0.1:30006: PONG
       127.0.0.1:30002: PONG
       127.0.0.1:30003: PONG

        $ redis-cli --cluster call 127.0.0.1:30001 DBSIZE
        >>> Calling DBSIZE
        127.0.0.1:30001: (integer) 0
       127.0.0.1:30004: (integer) 0
       127.0.0.1:30005: (integer) 3
       127.0.0.1:30006: (integer) 0
       127.0.0.1:30002: (integer) 3
       127.0.0.1:30003: (integer) 0  
```
##### 18.5.10，设置超时时间
```shell
通过cluster选项的set-timeout子命令，用户可以为集群的所有节点重新设置cluster-node-timeout选项的值：
        set-timeout <host>:<port> <milliseconds>

        $ redis-cli --cluster set-timeout 127.0.0.1:700050000
        >>> Reconfiguring node timeout in every cluster node...
        *** New timeout set for 127.0.0.1:7000
        *** New timeout set for 127.0.0.1:7001
        *** New timeout set for 127.0.0.1:7005
        *** New timeout set for 127.0.0.1:7004
        *** New timeout set for 127.0.0.1:7003
        *** New timeout set for 127.0.0.1:7002
        >>> New node timeout set. 6 OK, 0 ERR.
```
##### 18.5.11，导入数据
```shell
用户可以通过cluster选项的import子命令，将给定单机Redis服务器的数据导入集群中：
        import <node-host>:<port>                       # 集群入口节点的IP地址和端口号
            --cluster-from <server-host>:<port>  # 单机服务器的IP地址和端口号
            --cluster-copy                            # 使用复制导入
            --cluster-replace                        # 覆盖同名键

        $  redis-cli  --cluster  import  127.0.0.1:30001  --cluster-from  127.0.0.1:6379  --cluster-copy --cluster-replace
        >>>Importingdatafrom127.0.0.1:6379tocluster127.0.0.1:30001
        >>> Performing Cluster Check (using node 127.0.0.1:30001)
        M: 4979f8583676c46039672fb7319e917e4b303707127.0.0.1:30001
            slots:[0-5460] (5461 slots) master
            1 additional replica(s)
        S: 4788fd4d92387fc5d38a2cd12f0c0d80fc0f6609127.0.0.1:30004
            slots: (0 slots) slave
            replicates 4979f8583676c46039672fb7319e917e4b303707
        S: b45a7f4355ea733a3177b89654c10f9c31092e92127.0.0.1:30005
            slots: (0 slots) slave
            replicates 4ff303d96f5c7436ce8ce2fa6e306272e82cd454
        S: 7c56ffba63e3758bc4c2e9b6a55caf294bb21650127.0.0.1:30006
            slots: (0 slots) slave
            replicates 07e230805903e4e1657743a2e4d8811a59e2f32f
        M: 4ff303d96f5c7436ce8ce2fa6e306272e82cd454127.0.0.1:30002
            slots:[5461-10922] (5462 slots) master
            1 additional replica(s)
        M: 07e230805903e4e1657743a2e4d8811a59e2f32f 127.0.0.1:30003
            slots:[10923-16383] (5461 slots) master
            1 additional replica(s)
        [OK] All nodes agree about slots configuration.
        >>> Check for open slots...
        >>> Check slots coverage...
        [OK] All 16384 slots covered.
        *** Importing 3 keys from DB0
        Migrating alphabets to 127.0.0.1:30002: OK
        Migrating number to 127.0.0.1:30002: OK
        Migrating msg to 127.0.0.1:30002: OK

在这个例子中，程序将从单机服务器127.0.0.1:6379向节点127.0.0.1:30001所在的集群导入数据。在这个过程中，集群中的同名键会被覆盖，而单机服务器原有的数据库则会被保留。
```

#### 18.6，集群管理命令
除了集群管理程序之外，Redis还提供了一簇以CLUSTER开头的集群命令，这些命令可以根据它们的作用分为集群管理命令和槽管理命令，前者管理的是集群及其节点，而后者管理的则是节点的槽分配情况。

需要注意的是，因为Redis的集群管理程序redis-cli--cluster实际上就是由CLUSTER命令实现的，所以这两者之间存在着千丝万缕的关系，某些redis-cli--cluster子命令甚至直接与某个CLUSTER子命令对应。

##### 18.6.1，CLUSTER MEET：将节点添加至集群
```shell
用户可以通过执行以下命令，将给定的节点添加至当前节点所在的集群中：
        CLUSTER MEET ip port

举个例子，假设我们现在启动了30001、30002和30003这3个节点，并且打算使用这3个节点组成一个集群，那么可以首先执行以下命令，将节点30001和30002放到同一个集群中：
        127.0.0.1:30001> CLUSTER MEET 127.0.0.1 30002
        OK
然后执行以下命令，将节点30003也添加到集群中：
        127.0.0.1:30001> CLUSTER MEET 127.0.0.1 30003
        OK

1,添加多个节点
当用户执行CLUSTER MEET命令，尝试将一个给定的节点添加到当前节点所在的集群时，如果给定节点已经位于一个包含多个节点的集群当中，那么不仅给定节点会被添加到当前节点所在的集群，给定节点原集群内的其他节点也会自动合并到当前集群中。
```
##### 18.6.2，CLUSTER NODES：查看集群内所有节点的相关信息
```shell
用户可以通过执行以下命令，查看集群内所有节点的相关信息：
        CLUSTER NODES

        127.0.0.1:30001> CLUSTER NODES
        5f99406c27403564f34f4b5e39410714881ad98e 127.0.0.1:30005@40005 slave 9cd23534bf654a47a2d4d8a4b2717c495ee31b40 
        0 1541751161088 5 connected
        309871e77eaccc0a4e260cf393547bf51ba11983  127.0.0.1:30002@40002  master  -  
        0 1541751161088 2 connected 5461-10922
        db3a54cfe722264bd91caef4d4af9701bf02223f 127.0.0.1:30006@40006 slave 309871e77eaccc0a4e260cf393547bf51ba11983 
        0 1541751161694 6 connected
        27493691b04fccc230c7ac4e20836c081a6f33aa  127.0.0.1:30003@40003  master  -  
        0 1541751161088 3 connected 10923-16383
        bf0d4857c921750b9d149241255a7ae777b93539 127.0.0.1:30004@40004 slave 27493691b04fccc230c7ac4e20836c081a6f33aa 
        0 1541751161694 4 connected
        9cd23534bf654a47a2d4d8a4b2717c495ee31b40 127.0.0.1:30001@40001 myself, master -
        0 1541751161000 1 connected 0-5460

CLUSTER NODES含义:
1，节点ID：记录节点的运行ID；
2，地址和端口：节点的IP地址及端口号；
3，角色和状态：记录节点当前担任的角色以及目前所处的状态。角色以及状态的详述见下文；
4，主节点ID：如果当前节点是从节点，显示主节点ID，如果当前节点是主节点，显示 -；
5，发送PING消息的时间：节点最近一次向其他节点发送PING消息时的UNIX时间戳，格式为毫秒。如果该节点和其他节点连接正常，并且它发送的PING消息也没有被阻塞，那么这个值将被设置为 0；
6，收到PONG消息的时间：节点最近一次接收到其他节点发送的PONG消息时的UNIX时间戳，格式为毫秒。对于客户端正在连接的节点来说，这个项的值总为0；
7，配置纪元：节点所处的配置纪元；
8，连接状态：节点集群总线的连接状态。connected表示连接正常，disconnected表示连接已断开；
9，负责的槽：显示节点目前负责处理的槽，以及这些槽所处的状态。如果是一个从节点或者没有负责任何槽的主节点，改值为空。详述见下文。

节点的角色以及状态：
1，myself：客户端目前正在连接的节点；
2，master：主节点；
3，slave：从节点；
4，fail?：节点正处于疑似下线状态；
5，fail：节点已下线；
6，nofailover：这个节点开启了cluster-replica-no-failover配置选项，带有这个标志的从节点即使主节点下线，也不会主动执行故障转移操作；
7，handshake：集群正在和这个节点握手，尚未确认它的状态；
8，noaddr：目前尚不清楚这个节点的具体地址；
9，noflags：目前尚不清楚这个节点担任的角色以及它所处的状态。

槽的数字以及状态：
1，连续的槽：当遇到连续槽号时，会以 start_slot-end_slot 格式打印节点的负责的槽。例如0-5460；
2，不连续的槽：当遇到不连续的槽号时，就会单独地打印出这些不连续的槽号；
3，正在导入的槽：如果一个节点正在从另一个节点导入某个槽，那么CLUSTER NODES命令将以{slot_number-<-node_id}的格式打印出被导入的槽以及该槽原来所处的节点；
4，正在迁移的槽：如果节点正在将自己的某个槽导入到其他节点，那么CLUSTER NODES命令将以{slot_number->-node_id}的格式打印出被迁移的槽以及该槽正在迁移的目标节点。
```
##### 18.6.3，CLUSTER MYID：查看当前节点的运行ID
```shell
不少集群命令都需要使用节点的运行ID作为参数，所以当我们需要对正在连接的节点执行某个使用运行ID作为参数的操作时，就可以使用CLUSTER MYID命令快速地获得节点的ID。
        CLUSTER MYID

        127.0.0.1:30001> CLUSTER MYID
        "9cd23534bf654a47a2d4d8a4b2717c495ee31b40"
```
##### 18.6.4，CLUSTER INFO：查看集群信息
```shell
用户可以通过执行CLUSTER INFO命令，查看与集群以及当前节点有关的状态信息：
        CLUSTER INFO

        127.0.0.1:30001> CLUSTER INFO
        cluster_state:ok                                      --集群目前处于在线状态
        cluster_slots_assigned:16384                        --有16384个槽已经被指派
        cluster_slots_ok:16384                               --有16384个槽处于在线状态
        cluster_slots_pfail:0                                --没有槽处于疑似下线状态
        cluster_slots_fail:0                                  --没有槽处于已下线状态
        cluster_known_nodes:6                                --集群包含6个节点
        cluster_size:3                                         --集群中有3个节点被指派了槽
        cluster_current_epoch:6                              --集群当前所处的纪元为6
        cluster_my_epoch:1                                    --节点当前所处的配置纪元为1
        cluster_stats_messages_ping_sent:774301           --节点发送PING消息的数量
        cluster_stats_messages_pong_sent:774642           --节点发送PONG消息的数量
        cluster_stats_messages_sent:1548943                --节点目前总共发送了1548943条消息
        cluster_stats_messages_ping_received:774637      --节点接收PING消息的数量
        cluster_stats_messages_pong_received:774301      --节点接收PONG消息的数量
        cluster_stats_messages_meet_received:5            --节点接收MEET消息的数量
        cluster_stats_messages_received:1548943           --节点目前总共接收了1548943条消息
```
##### 18.6.5，CLUSTER FORGET：从集群中移除节点
```shell
当用户不再需要集群中的某个节点时，可以通过执行以下命令将其移除：
        CLUSTER FORGET node-id

与CLUSTER MEET命令引发的节点添加消息不一样，CLUSTER FORGET命令引发的节点移除消息并不会通过Gossip协议传播至集群中的其他节点：
当用户向一个节点发送CLUSTER FORGET命令，让它去移除集群中的另一个节点时，接收到命令的节点只是暂时屏蔽了用户指定的节点，但这个被屏蔽的节点对于集群中的其他节点仍然是可见的。
为此，要让集群真正地移除一个节点，用户必须向集群中的所有节点都发送相同的CLUSTER FORGET命令，并且这一动作必须在60s之内完成，否则被暂时屏蔽的节点就会因为Gossip协议的作用而被重新添加到集群中。
        127.0.0.1:30001> CLUSTER FORGET 5f99406c27403564f34f4b5e39410714881ad98e   -- 节点30005的运行ID
        OK
        127.0.0.1:30002> CLUSTER FORGET 5f99406c27403564f34f4b5e39410714881ad98e
        OK
        127.0.0.1:30003> CLUSTER FORGET 5f99406c27403564f34f4b5e39410714881ad98e
        OK
        127.0.0.1:30004> CLUSTER FORGET 5f99406c27403564f34f4b5e39410714881ad98e
        OK
        127.0.0.1:30006> CLUSTER FORGET 5f99406c27403564f34f4b5e39410714881ad98e
        OK

如果觉得重复发送5个CLUSTER FORGET命令太麻烦，那么可以使用之前介绍的Redis集群管理程序的call子命令，一次在整个集群的所有节点中执行CLUSTER FORGET命令：
        $ redis-cli --cluster call 127.0.0.1:30001 CLUSTER FORGET 5f99406c27403564f34f4b5e39410714881ad98e
        >>> Calling CLUSTER FORGET 5f99406c27403564f34f4b5e39410714881ad98e
        127.0.0.1:30001: OK
        127.0.0.1:30003: OK
        127.0.0.1:30004: OK
        127.0.0.1:30002: OK
        127.0.0.1:30005: ERR I tried hard but I cant forget myself...
        127.0.0.1:30006: OK

虽然30005因为不能对本身执行CLUSTER FORGET而出错了，但这个错误并不会妨碍整个移除操作。
```
##### 18.6.6，CLUSTER REPLICATE：将节点变为从节点
```shell
CLUSTER REPLICATE命令接受一个主节点ID作为参数，并将执行该命令的节点变成给定主节点的从节点：
        CLUSTER REPLICATE master-id

用户给定的主节点必须与当前节点位于相同的集群当中。此外，根据当前节点角色的不同，CLUSTER REPLICATE命令在执行时的情况也会有所不同：
● 如果当前节点是一个主节点，那么它必须是一个没有被指派任何槽的主节点，并且它的数据库中也不能有任何数据，这样它才可以转换成一个从节点。
● 如果当前节点已经是一个从节点，那么它将清空数据库中已有的数据，并开始复制用户给定的节点。

CLUSTER REPLICATE命令在成功执行时将返回OK作为结果。与单机版本的REPLICAOF命令一样，CLUSTER REPLICATE命令引发的复制操作也是异步执行的。
        127.0.0.1:30005> CLUSTER REPLICATE 9cd23534bf654a47a2d4d8a4b2717c495ee31b40
        OK

● 只能对主节点进行复制
在使用单机版本的Redis时，用户可以让一个从服务器去复制另一个从服务器，以此来构建一系列链式复制的服务器。
与这种做法不同，Redis集群只允许节点对主节点而不是从节点进行复制，如果用户尝试使用CLUSTER REPLICATE命令让一个节点去复制一个从节点，那么命令将返回一个错误：
        127.0.0.1:30001>  CLUSTER  REPLICATE  db3a54cfe722264bd91caef4d4af9701bf02223f --向命令传入一个从节点ID
        (error) ERR I can only replicate a master, not a replica.
```
##### 18.6.7，CLUSTER REPLICAS：查看给定节点的所有从节点
```shell
CLUSTER REPLICAS命令接受一个节点ID作为参数，然后返回该节点属下所有从节点的相关信息：
        CLUSTER REPLICAS node-id

        #首先获取本节点的ID
        127.0.0.1:30001> CLUSTER MYID
        "9cd23534bf654a47a2d4d8a4b2717c495ee31b40"

        #然后再使用这个ID查看该节点属下从节点的信息
        127.0.0.1:30001> CLUSTER REPLICAS 9cd23534bf654a47a2d4d8a4b2717c495ee31b40
        1) "5f99406c27403564f34f4b5e39410714881ad98e 127.0.0.1:30005@40005 slave 9cd23534bf654a47a2d4d8a4b2717c495ee31b40 0 1541931897080 1 connected"

● CLUSTER REPLICAS命令是从Redis 5.0.0版本开始启用的，在较旧的Redis版本中（不低于3.0.0版本），同样的工作可以通过执行CLUSTER SLAVES命令来完成。
```
##### 18.6.8，CLUSTER FAILOVER：强制执行故障转移
```shell
用户可以通过向从节点发送以下命令，让它发起一次对自身主节点的故障转移操作：
        CLUSTER FAILOVER

因为接收到该命令的从节点会先将自身的数据库更新至与主节点完全一致，然后再执行后续的故障转移操作，所以这个过程不会丢失任何数据。
        #执行CLUSTER FAILOVER命令前的节点配置
        #节点30005为30001从节点
        127.0.0.1:30005> CLUSTER NODES
        ...
        5f99406c27403564f34f4b5e39410714881ad98e  127.0.0.1:30005@40005  myself,slave
    9cd23534bf654a47a2d4d8a4b2717c495ee31b40 0 1542078342000 5 connected
        ...
        9cd23534bf654a47a2d4d8a4b2717c495ee31b40  127.0.0.1:30001@40001  master  
        -  0 1542078342138 1 connected 0-5460

        #执行故障转移操作
        127.0.0.1:30005> CLUSTER FAILOVER
        OK

        #故障转移实施之后的节点配置
        #节点30005已经成为了新的主节点，而30001则变成了该节点的从节点
        127.0.0.1:30005> CLUSTER NODES
        ...
        5f99406c27403564f34f4b5e39410714881ad98e 127.0.0.1:30005@40005 myself,master 
        - 0 1542078351000 7 connected 0-5460
        ...
        9cd23534bf654a47a2d4d8a4b2717c495ee31b40127.0.0.1:30001@40001 slave 
        5f99406c27403564f34f4b5e39410714881ad98e 0 1542078351095 7 connected

CLUSTER FAILOVER命令在执行成功时将返回OK作为结果。如果用户尝试向主节点发送该命令，那么命令将返回一个错误：
        #尝试再次向已经成为主节点的30005发送CLUSTER FAILOVER命令
        127.0.0.1:30005> CLUSTER FAILOVER
        (error) ERR You should send CLUSTER FAILOVER to a replica

用户可以通过可选的FORCE选项和TAKEOVER选项来改变CLUSTER FAILOVER命令的行为：
        CLUSTER FAILOVER [FORCE|TAKEOVER]

● 在给定了FORCE选项时，从节点将在不尝试与主节点进行握手的情况下，直接实施故障转移。这种做法可以让用户在主节点已经下线的情况下立即开始故障转移。
● 即使用户给定了FORCE选项，从节点对主节点的故障转移操作仍然要经过集群中大多数主节点的同意才能够真正执行。
● 但如果用户给定了TAKEOVER选项，那么从节点将在不询问集群中其他节点意见的情况下，直接对主节点实施故障转移。
```
##### 18.6.9，CLUSTER RESET：重置节点
```shell
用户可以通过在节点上执行CLUSTER RESET命令来重置该节点，以便在集群中复用该节点：
        CLUSTER RESET [SOFT|HARD]
● 这个命令接受SOFT和HARD两个可选项作为参数，用于指定重置操作的具体行为（软重置和硬重置）。如果用户在执行CLUSTER RESET命令的时候没有显式地指定重置方式，那么命令默认将使用SOFT选项。

CLUSTER RESET命令在执行时，将对节点执行以下操作：
1）遗忘该节点已知的其他所有节点。
2）撤销指派给该节点的所有槽，并清空节点内部的槽-节点映射表。
3）如果执行该命令的节点是一个从节点，那么将它转换成一个主节点。
4）如果执行的是硬重置，那么为节点创建一个新的运行ID。
5）如果执行的是硬重置，那么将节点的纪元和配置纪元都设置为0。
6）通过集群节点配置文件的方式，将新的配置持久化到硬盘上。

需要注意的是，CLUSTER RESET命令只能在数据库为空的节点上执行，如果节点的数据库非空，那么命令将返回一个错误：
        127.0.0.1:30002> CLUSTER RESET
        (error) ERR CLUSTER RESET cant be called with master nodes containing keys

在正常情况下，CLUSTER RESET命令在正确执行之后将返回OK作为结果：
        #清空数据库
        127.0.0.1:30002> FLUSHALL
        OK
        #执行（软）重置
        127.0.0.1:30002> CLUSTER RESET
        OK

        #节点在重置之后将遗忘之前发现过的所有节点
        127.0.0.1:30002> CLUSTER NODES
        309871e77eaccc0a4e260cf393547bf51ba11983 127.0.0.1:30002@40002 myself, master 
        - 0 1542090339000 2 connected

        #执行硬重置
        127.0.0.1:30002> CLUSTER RESET HARD
        OK

        #节点在硬重置之后获得了新的运行ID
        127.0.0.1:30002> CLUSTER NODES
        b24d4a41c6a9c5633eb93caca15faed75398dd54 127.0.0.1:30002@40002 myself, master 
        - 0 1542090339000 0 connected       
```

#### 18.7，槽管理命令
本节将对CLUSTER命令中与槽管理有关的命令，如CLUSTER SLOTS、CLUSTER ADDSLOTS、CLUSTER FLUSHSLOTS等进行介绍。

##### 18.7.1，CLUSTER SLOTS：查看槽与节点之间的关联信息
```shell
用户可以通过执行以下命令，获知各个槽与集群节点之间的关联信息：
        CLUSTER SLOTS
命令会返回一个嵌套数组，数组中的每个项记录了一个槽范围（slot range）及其处理者的相关信息，其中包括：
        ● 槽范围的起始槽。
        ● 槽范围的结束槽。
        ● 负责处理这些槽的主节点信息。
        ● 零个或任意多个主节点属下从节点的信息。
其中，每一项节点信息都由以下3项信息组成：
        ● 节点的IP地址。
        ● 节点的端口号。
        ● 节点的运行ID。

        127.0.0.1:30001> CLUSTER SLOTS
        1) 1) (integer) 0        #起始槽
           2) (integer) 5460     #结束槽
           3) 1) "127.0.0.1"     #主节点信息
              2) (integer) 30001
              3) "9e2ee45f2a78b0d5ab65cbc0c97d40262b47159f"
           4) 1) "127.0.0.1"     #从节点信息
              2) (integer) 30005
              3) "f584b888fcc0e7648bd838cb3b0e2d1915ac0ad7"
        2) 1) (integer) 10923
           2) (integer) 16383
           3) 1) "127.0.0.1"
              2) (integer) 30003
              3) "a80b64eedcd15329bc0dc7b71652ecddccf6afe8"
           4) 1) "127.0.0.1"
              2) (integer) 30004
              3) "ab0b79f233efa0afa467d9ef1700fe5b24154992"
        3) 1) (integer) 5461
           2) (integer) 10922
           3) 1) "127.0.0.1"
              2) (integer) 30002
              3) "b2c7a5ca5fa6de72ac2842a2196ab2f4a5c82a6a"
           4) 1) "127.0.0.1"
              2) (integer) 30006
              3) "262acdf22f4adb6a20b8116982f2940890693d0b"
● 版本较旧的Redis集群在执行CLUSTER SLOTS命令时只会返回节点的IP地址和端口号，不会返回节点的运行ID。
```
##### 18.7.2，CLUSTER ADDSLOTS：把槽指派给节点
```shell
通过在节点上执行以下命令，我们可以将给定的一个或任意多个槽指派给当前节点进行处理：
        CLUSTER ADDSLOTS slot [slot ...]

        127.0.0.1:30001> CLUSTER ADDSLOTS 0 1 2 3 4 5    #将尚未被指派的槽0～5指派给节点30001负责
        OK

CLUSTER ADDSLOTS只能对尚未被指派的槽执行指派操作，如果用户给定的槽已经被指派，那么命令将返回一个错误：
        #尝试指派已被指派的槽，命令报错
        127.0.0.1:30001> CLUSTER ADDSLOTS 0 1 2 3 4 5
        (error) ERR Slot 0 is already busy
```
##### 18.7.3，CLUSTER DELSLOTS：撤销对节点的槽指派
```shell
在使用CLUSTER ADDSLOTS命令将槽指派给节点负责之后，用户可以在有需要的情况下，通过执行以下命令撤销对节点的槽指派：
        CLUSTER DELSLOTS slot [slot ...]

        127.0.0.1:30001> CLUSTER DELSLOTS 0 1 2 3 4 5    #对节点30001执行以下命令，撤销对该节点的槽0～5的指派
        OK

在执行CLUSTER DELSLOTS命令时，用户给定的必须是已经指派给当前节点的槽，尝试撤销一个未指派的槽将引发一个错误：
        127.0.0.1:30001> CLUSTER DELSLOTS 0 1 2 3 4 5
        (error) ERR Slot 0 is already unassigned
```
##### 18.7.4，CLUSTER FLUSHSLOTS：撤销对节点的所有槽指派
```shell
通过在一个节点上执行以下命令，我们可以撤销对该节点的所有槽指派，让它不再负责处理任何槽：
        CLUSTER FLUSHSLOTS

执行这个命令相当于对该节点负责的所有槽执行CLUSTER DELSLOTS命令。
        127.0.0.1:30001> CLUSTER FLUSHSLOTS
        OK

需要注意的是，用户在执行CLUSTER FLUSHSLOTS命令之前，必须确保节点的数据库为空，否则节点将拒绝执行命令并返回一个错误：
        #尝试对非空节点30002执行CLUSTER FLUSHSLOTS

        127.0.0.1:30002> DBSIZE
        (integer) 3

        127.0.0.1:30002> CLUSTER FLUSHSLOTS
        (error) ERR DB must be empty to perform CLUSTER FLUSHSLOTS.
```
##### 18.7.5，CLUSTER KEYSLOT：查看键所属的槽
```shell
通过对给定键执行以下命令，我们可以知道该键所属的槽：
        CLUSTER KEYSLOT key

如果我们想要知道message键以及counter::12345键属于哪个槽，那么可以执行以下命令：
        127.0.0.1:30001> CLUSTER KEYSLOT message
        (integer) 11537  # message键属于槽11537

        127.0.0.1:30001> CLUSTER KEYSLOT counter::12345
        (integer) 12075  # counter::12345键属于槽12075

        #两个带有相同散列标签{user}的键
        127.0.0.1:30001> CLUSTER KEYSLOT {user}::256
        (integer) 5474
        127.0.0.1:30001> CLUSTER KEYSLOT {user}::10086
        (integer) 5474
```
##### 18.7.6，CLUSTER COUNTKEYSINSLOT：查看槽包含的键数量
```shell
通过执行以下命令，用户可以查看给定槽包含的键数量：
        CLUSTER COUNTKEYSINSLOT slot

        127.0.0.1:30001> CLUSTER COUNTKEYSINSLOT 523    #查看槽523包含了多少个键
        (integer) 2

1,只对当前节点进行计数
如果执行命令的节点并不是负责处理给定槽的节点，那么命令将找不到任何属于给定槽的键。在这种情况下，命令只会单纯地返回0作为执行结果。        
        127.0.0.1:30001> CLUSTER COUNTKEYSINSLOT 10087    #槽10087，不属于节点 30001
        (integer) 0
        127.0.0.1:30002> CLUSTER COUNTKEYSINSLOT 10087    #槽10087，属于节点 30002
        (integer) 42
```
##### 18.7.7，CLUSTER GETKEYSINSLOT：获取槽包含的键
```shell
用户可以通过执行以下命令，获取指定槽包含的键：
        CLUSTER GETKEYSINSLOT slot count
● 命令的slot参数用于指定槽，而count参数则用于指定命令允许返回的最大键数量。

        #想要从槽523中获取最多10个键，那么可以执行以下命令
        127.0.0.1:30001> CLUSTER GETKEYSINSLOT 523 10
        1) "article::1750"
        2) "article::4022"
1,只获取当前节点包含的键
如果执行命令的节点并不是负责处理给定槽的节点，那么命令将无法找到任何可以返回的键。因此，为了正确地获取槽包含的键，用户必须向正确的节点发送CLUSTER GETKEYSINSLOT命令。     
```
##### 18.7.8，CLUSTER SETSLOT：改变槽的状态
```shell
CLUSTER SETSLOT命令拥有4个子命令，它们可以改变给定槽在节点中的状态，从而实现节点之间的槽迁移以及集群重分片：
        CLUSTER SETSLOT slot IMPORTING source-node-id

        CLUSTER SETSLOT slot MIGRATING destination-node-id

        CLUSTER SETSLOT slot NODE node-id

        CLUSTER SETSLOT slot STABLE

1,导入槽
通过在节点上执行IMPORTING子命令，用户可以让节点的指定槽进入“导入中”（importing）状态，处于该状态的槽允许从源节点中导入槽数据：
        CLUSTER SETSLOT slot IMPORTING source-node-id

举个例子，如果我们想要让节点30002的槽5460进入“导入中”状态，并允许它从ID为3f2b7eea74079afe6b57ce1d2627228990582d04的节点30001中导入槽数据，那么可以执行以下命令：
        127.0.0.1:30002> CLUSTER SETSLOT 5460 IMPORTING 3f2b7eea74079afe6b57ce1d2627228990582d04
        OK

2,迁移槽
通过在节点上执行MIGRATING子命令，用户可以让节点的指定槽进入“迁移中”（migrating）状态，处于该状态的槽允许向目标节点转移槽数据：
        CLUSTER SETSLOT slot MIGRATING destination-node-id

举个例子，如果我们想要让节点30001的槽5460进入“迁移中”状态，并允许它向ID为1e8f55652d95a05d21b2afc5243a438b848f5966的节点30002迁移数据，那么可以执行以下命令：
        127.0.0.1:30001> CLUSTER SETSLOT 5460 MIGRATING 1e8f55652d95a05d21b2afc5243a438b848f5966
        OK

3,将槽指派给节点
在将槽数据从源节点迁移至目标节点之后，用户可以在集群的任一节点执行以下命令，正式将槽指派给目标节点负责：
        CLUSTER SETSLOT slot NODE node-id

比如，在将槽5460的数据从节点30001迁移至节点30002之后，我们可以执行以下命令，将槽5460正式指派给节点30002负责：   
        #1e8f5...f5966为节点30002的ID
        127.0.0.1:30002> CLUSTER  SETSLOT  5460  NODE  1e8f55652d95a05d21b2afc5243a438b84855966
        OK     
集群的其中一个节点在执行了NODE子命令之后，对给定槽的新指派信息将被传播至整个集群，目标节点在接收到这一信息之后将移除给定槽的“导入中”状态，而源节点在接收到这一信息之后将移除给定槽的“迁移中”状态。

4,移除槽的导入/迁移状态
通过执行以下命令，用户可以清除节点指定槽的“导入中”或“迁移中”状态：
        CLUSTER SETSLOT slot STABLE

        127.0.0.1:30001> CLUSTER SETSLOT 5460 STABLE
        OK
正如之前所说，因为槽在成功迁移之后会由于NODE子命令的作用而自动移除相应节点的“导入中”和“迁移中”状态，所以在正常情况下，用户并不需要执行STABLE子命令。
STABLE子命令的唯一作用，就是在槽迁移出错或者重分片出错时，手动移除相应节点的槽状态。
```



































