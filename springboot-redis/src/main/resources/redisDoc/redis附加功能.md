# 第二部分：附加功能
### 10, 数据库
所有Redis键，无论它们是什么类型，都会被存储到一个名为数据库的容器中。因为Redis是一个键值对数据库服务器，所以它的数据库与之前介绍过的散列键一样，都可以根据键的名字对数据库中的键值对进行索引。

一个Redis服务器可以同时拥有多个数据库，在默认情况下，Redis服务器在启动时将创建16个数据库，并使用数字0～15对其进行标识。每个数据库都拥有一个独立的命名空间。也就是说，同名的键可以出现在不同数据库中。

```shell
1,SELECT：切换至指定的数据库。
        SELECT index
        SELECT 1        #从默认的 0号数据库切换至 1号数据库
● 切换成功后，客户端提示符末尾的[1]表示当前正在使用 1号数据库。


2,KEYS：KEYS命令接受一个全局匹配符作为参数，然后返回数据库中所有与这个匹配符相匹配的键作为结果。
        KEYS pattern
        keys article*        #* 匹配零个或任意多个任意字符
        keys article?        #? 匹配任意单个字符
        keys article.[abc]   #[] 匹配给定字符串中的单个字符
        keys article.[a-d]   #[?-?] 匹配给定范围内的单个字符
● 当数据库包含的键数量比较大时，使用KEYS命令可能会导致服务器被阻塞。


3,SCAN：以渐进方式迭代数据库中的键。
        SCAN cursor
● SCAN命令的cursor参数用于指定迭代时使用的游标，游标记录了迭代的轨迹和进度。在开始一次新的迭代时，用户需要将游标设置为0。

SCAN命令的执行结果由两个元素组成：
● 第一个元素是进行下一次迭代所需的游标，如果这个游标为0，那么说明客户端已经对数据库完成了一次完整的迭代。
● 第二个元素是一个列表，这个列表包含了本次迭代取得的数据库键；如果SCAN命令在某次迭代中没有获取到任何键，那么这个元素将是一个空列表。

关于SCAN命令返回的键列表，有两点需要注意：
● SCAN命令可能会返回重复的键，用户如果不想在结果中包含重复的键，那么就需要自己在客户端中进行检测和过滤。
● SCAN命令返回的键数量是不确定的，有时甚至会不返回任何键，但只要命令返回的游标不为0，迭代就没有结束。

SCAN命令的迭代保证：
● 从迭代开始到迭代结束的整个过程中，一直存在于数据库中的键总会被返回。
● 如果一个键在迭代的过程中被添加到数据库中，那么这个键是否会被返回是不确定的。
● 如果一个键在迭代的过程中被移除了，那么SCAN命令在它被移除之后将不再返回这个键，但是这个键在被移除之前仍然有可能被SCAN命令返回。● 无论数据库如何变化，迭代总是有始有终的，不会出现循环迭代或者其他无法终止迭代的情况。

游标的使用：
● SCAN命令的游标不需要申请，也不需要释放，它们不占用任何资源，每个客户端都可以使用自己的游标独立地对数据库进行迭代。
● 用户可以随时在迭代的过程中停止迭代，或者随时开始一次新的迭代，这不会浪费任何资源，也不会引发任何问题。

迭代与给定匹配符相匹配的键
        SCAN cursor [MATCH pattern]
        scan 0 match article*   #迭代过程只返回符合 article* 条件的键

指定返回键的期望数量
        SCAN cursor [COUNT number]    #不设置期望数量，默认是 10
        scan 0 match article* count 5    #迭代过程只返回符合 article* 条件的键，每次迭代期望返回 5 个
● COUNT选项只是提供了一个期望值，告诉SCAN命令我们希望返回多少个键，但每次迭代返回的键数量仍然是不确定的。
● 不过在通常情况下，设置一个较大的COUNT值将有助于获得更多键，这一点是可以肯定的。
● 在用户没有显式地使用COUNT选项的情况下，SCAN命令将使用10作为COUNT选项的默认值

3-1,渐进式散列迭代命令
HSCAN 命令可以以渐进的方式迭代给定散列包含的键值对。
        HSCAN hash cursor [MATCH pattern] [COUNT number]
        hscan article.10086 0 match title* count 15
● 当散列包含较多键值对的时候，应该尽量使用HSCAN代替 HKEYS、HVALS和HGETALL，以免造成服务器阻塞。

3-2,渐进式集合迭代命令
SSCAN 命令可以以渐进的方式迭代给定集合包含的元素
        SSCAN set cursor [MATCH pattern] [COUNT number]
        sscan johannset3 0 match s? count 15
● 当集合包含较多元素的时候，我们应该尽量使用SSCAN代替 SMEMBERS，以免造成服务器阻塞。

3-3,渐进式有序集合迭代命令
ZSCAN 命令可以以渐进的方式迭代给定有序集合包含的成员和分值
        ZSCAN sorted_set cursor [MATCH pattern] [COUNT number]
        zscan zset_store1 0 match j* count 15
● 当有序集合包含较多成员的时候，我们应该尽量使用ZSCAN去代替 ZRANGE 以及其他可能会返回大量成员的范围型获取命令，以免造成服务器阻塞。


4,RANDOMKEY：可以从数据库中随机地返回一个键
        RANDOMKEY
● RANDOMKEY命令不会移除被返回的键，它们会继续留在数据库中。当数据库为空时，RANDOMKEY命令将返回一个空值


5,SORT：对列表元素、集合元素或者有序集合成员进行排序
可以通过执行SORT命令对列表元素、集合元素或者有序集合成员进行排序。这个命令还提供了非常多的可选项，使得用户能够以不同的方式进行排序。
        SORT key
● 如果我们以不给定任何可选项的方式直接调用SORT命令，那么命令将对指定键存储的元素执行数字值排序。此时，如果这个集合中的值不全是数字类型，返回："(error) ERR One or more scores can't be converted into doubl"

5-1,指定排序方式
        SORT key [ASC|DESC]

5-2,对字符串值进行排序
        SORT key [ALPHA] 
        sort zset_store1 alpha       

5-3,只获取部分排序结果
        SORT key [LIMIT offset count]
        sort johannset3 alpha limit 1 2   #获取排序后的集合中的第二，第三个元素
● offset参数用于指定返回结果之前需要跳过的元素数量，而count参数则用于指定需要获取的元素数量。

5-4,获取外部键的值作为结果
在默认情况下，SORT命令将返回被排序的元素作为结果，但如果用户有需要，也可以使用GET选项去获取其他值作为排序结果
        SORT key [[GET pattern] [GET pattern] ...]
一个SORT命令可以使用任意多个GET pattern选项，其中pattern参数的值可以是：
● 包含'*'符号的字符串。
● 包含'*'符号和'->'符号的字符串。
● 一个单独的'#'符号。

5-4-1,获取字符串键的值
当pattern参数的值是一个包含'*'符号的字符串时，SORT命令将把被排序的元素与'*'符号进行替换，构建出一个键名，然后使用GET命令去获取该键的值。
示例：
    sadd fruits "cherry" "banana" "apple" "mango" "dragon fruit" "watermelon"
    mset "apple-price" 8.5 "banana-price" 4.5 "cherry-price" 7
    mset "dragon fruit-price" 6 "mango-price" 5 "watermelon-price" 3.5
    ---
    sort fruits alpha get *-price

5-4-2,获取散列中的键值
当pattern参数的值是一个包含'*'符号和'->'符号的字符串时，SORT命令将使用'->'左边的字符串为散列名，'->'右边的字符串为字段名，调用HGET命令，从散列中获取指定字段的值。此外，用户传入的散列名还需要包含'*'符号，这个＊符号将被替换成被排序的元素。
示例：
    hset apple-info inventory 1000
    hset banana-info inventory 300
    hset cherry-info inventory 50
    hset "dragon fruit-info" inventory 500
    hset mango-info inventory 250
    hset watermelon-info inventory 324
    ---
    sort fruits alpha get *-info->inventory

5-4-3,获取被排序元素本身
当pattern参数的值是一个'#'符号时，SORT命令将返回被排序的元素本身。
因为'SORT key'命令和'SORT key GET #'命令返回的是完全相同的结果，所以单独使用'GET #'并没有任何实际作用。
因此，我们一般只会在同时使用多个GET选项时，才使用'GET #'获取被排序的元素。
    'sort fruits alpha get # get *-price get *-info->inventory'

5-5,使用外部键的值作为排序权重
在默认情况下，SORT命令将使用被排序元素本身作为排序权重，但在有需要时，用户可以通过可选的BY选项指定其他键的值作为排序的权重。
        SORT key [BY pattern]
● pattern参数的值既可以是包含'*'符号的字符串，也可以是包含'*'符号和'->'符号的字符串，这两种值的作用和效果与使用GET选项的作用和效果一样：前者用于获取字符串键的值，而后者则用于从散列中获取指定字段的值。
示例：
    'sort fruits by *-price'    #根据水果价格作为权重进行排序
    'sort fruits by *-price get # get *-price'    #根据水果价格作为权重进行排序，并使用两个get获取水果名称和价格

5-6,保存排序结果
在默认情况下，SORT命令会直接将排序结果返回给客户端，但如果用户有需要，也可以通过可选的STORE选项，以列表形式将排序结果存储到指定的键中。带有STORE选项的SORT命令在成功执行之后将返回被存储的元素数量作为结果。
        SORT key [STORE destination]
        'sort fruits by *-price get # get *-price store sorted-fruits'    #将按照水果价格排序的水果极其价格，保存到新的列表
        'sort fruits alpha store sorted-fruits'    #将排序后的水果保存到新的列表



6, EXISTS：检查给定的一个或多个键是否存在于当前正在使用的数据库中,EXISTS命令将返回存在的给定键数量作为返回值。
        EXISTS key [key ...]
        exists salary salary1 salary2 salary3
● 此前的版本中，只能接受单个键作为输入，从Redis 3.0.3版本开始接受多个键作为输入。


7, DBSIZE：获取当前使用的数据库包含了多少个键值对
        DBSIZE


8, TYPE：查看给定键的类型
        TYPE key
● TYPE 命令针对各种类型的键，其返回结果如下：
字符串键 --- String；散列键 --- hash；列表键 --- list；集合键 --- set；有序集合键 --- zset；     
HyperLogLog --- String；位图 --- String；地理位置 --- String；流 --- stream


9, RENAME、RENAMENX：修改键名，修改成功后返回OK
        RENAME origin new    #如果新的键名已存在，此时会先移除新健名指定的那个键，再执行改名操作
        RENAMENX origin new    #只在新键名尚未被占用的情况下进行改名，RENAMENX命令在改名成功时返回1，失败时返回0


10, MOVE：将一个键从当前数据库移动至目标数据库，成功返回1，失败返回0
        MOVE key db
● 如果给定键并不存在于当前数据库，或者目标数据库中存在与给定键同名的键，那么MOVE命令将不做动作，只返回0表示移动失败。


11, DEL：从当前正在使用的数据库中移除指定的一个或多个键，以及与这些键相关联的值，返回成功移除的键数量
        DEL key [key ...]


12, UNLINK：以异步方式移除指定的键，返回被移除键的数量
        UNLINK key [key ...]
● DEL命令会以同步方式执行移除操作，所以如果待移除的键非常庞大或者数量众多，那么服务器在执行移除操作的过程中就有可能被阻塞。
● UNLINK命令与DEL命令一样，都可以用于移除指定的键，但它与DEL命令的区别在于，UNLINK只会在数据库中移除对该键的引用（reference），而对键的实际移除操作则会交给后台线程执行，因此UNLINK命令将不会造成服务器阻塞。
● UNLINK命令从Redis 4.0版本开始可用。


13, FLUSHDB：清空当前数据库
        redis> FLUSHDB        
        OK
● FLUSHDB命令会遍历用户正在使用的数据库，移除其中包含的所有键值对，然后返回OK表示数据库已被清空。
        redis> FLUSHDB async    #异步方式清空当前数据库        
        OK
● 与DEL命令一样，FLUSHDB命令也是一个同步移除命令，为了解决阻塞问题，Redis 4.0给FLUSHDB命令新添加了一个async选项，如果用户在调用● ● FLUSHDB命令时使用了async选项，那么实际的数据库清空操作将放在后台线程中以异步方式进行。
● FLUSHDB命令从Redis 1.0.0版本开始可用；带有async选项的FLUSHDB命令从Redis 4.0版本开始可用


14, FLUSHALL：清空Redis服务器包含的所有数据库
        redis> FLUSHALL        
        OK
        redis> FLUSHALL async    #异步方式清空所有数据库  
        OK
● FLUSHALL命令从Redis 1.0.0版本开始可用；带有async选项的FLUSHALL命令从Redis 4.0版本开始可用。


15, SWAPDB：互换数据库，可以用于实现在线的数据库替换操作
        SWAPDB x y
● 在SWAPDB命令执行完毕之后，原本存储在数据库x中的键值对将出现在数据库y中，而原本存储在数据库y中的键值对将出现在数据库x中。
● 因为互换数据库这一操作可以通过调整指向数据库的指针来实现，这个过程不需要移动数据库中的任何键值对，所以SWAPDB命令的复杂度是O(1)而不是O(N)，并且执行这个命令也不会导致服务器阻塞。
● Redis 4.0版本开始可用
```

### 11, 自动过期
Redis提供了自动的键过期功能（key expiring）。通过这个功能，用户可以让特定的键在指定的时间之后自动被移除，从而避免了需要在指定时间内手动执行删除操作的麻烦。

```shell
1, EXPIRE、PEXPIRE：设置生存时间
为键设置一个生存时间（Time To Live, TTL）：键的生存时间在设置之后就会随着时间的流逝而不断地减少，当一个键的生存时间被消耗殆尽时，Redis就会移除这个键。
        EXPIRE key seconds    #设置秒级精度的生存时间
        PEXPIRE key milliseconds    #设置毫秒级精度的生存时间
● 对一个已经带有生存时间的键执行EXPIRE命令或PEXPIRE命令时，键原有的生存时间将会被移除，并设置新的生存时间。
● EXPIRE命令从Redis 1.0.0版本开始可用，PEXPIRE命令从Redis 2.6.0版本开始可用。


2, SET命令的EX选项和PX选项
Redis从2.6.12版本开始为SET命令提供EX选项和PX选项，用户可以通过使用这两个选项的其中一个来达到同时执行SET命令和EXPIRE/PEXPIRE命令的效果。
        SET key value [EX seconds] [PX milliseconds]
        'set userName "Johann" ex 60'
        'set userName "Johann" px 600000'
● 使用带有EX选项或PX选项的SET命令，保证了操作的原子性
● 带有EX选项和PX选项的SET命令从Redis 2.6.12版本开始可用


3, EXPIREAT、PEXPIREAT：设置过期时间（expire at在某个时间过期）
通过设置过期时间（expire time），让Redis在指定UNIX时间来临之后自动移除给定的键。
        EXPIREAT key seconds_timestamp    #接受一个秒级精度的UNIX时间戳为参数
        'expireat userName 1661940390'
        PEXPIREAT key milliseconds_timestamp    #接受一个毫秒级精度的UNIX时间戳为参数
        'pexpireat userName 1661940500000'
● 如果指定键已有过期时间，则再次执行此命令会先移除已有的过期时间，然后再为其重新设置新的过期时间。
● EXPIREAT命令从Redis 1.2.0版本开始可用，PEXPIREAT命令从Redis 2.6.0版本开始可用
● 获取UNIX时间戳 
    redis> time    #获取当前时间的 UNIX时间戳
    1) "1661939717"    #从1970年1月1日（UTC/GMT的午夜）开始所经过的秒数
    2) "969325"        #当前这一秒已经过去的微秒数


4, TTL、PTTL：获取键的剩余生存时间，即键还有多久才会因为过期而被移除
        TTL key    #以秒为单位返回键的剩余生存时间
        PTTL key    #以毫秒为单位返回键的剩余生存时间
● 如果给定的键存在，但是并没有设置生存时间或者过期时间，那么TTL命令和PTTL命令将返回-1。
● 如果给定的键并不存在，那么TTL命令和PTTL命令将返回-2。
● 如果给定键的剩余生存时间不足1s，TTL返回 0。
● TTL命令从Redis 1.0.0版本开始可用，PTTL命令从Redis 2.6.0版本开始可用
```

### 12, 流水线与事务
##### 12.1, 流水线
在一般情况下，用户每执行一个Redis命令，Redis客户端和Redis服务器就需要执行以下步骤：
    1）客户端向服务器发送命令请求。
    2）服务器接收命令请求，并执行用户指定的命令调用，然后产生相应的命令执行结果。
    3）服务器向客户端返回命令的执行结果。
    4）客户端接收命令的执行结果，并向用户进行展示。

与大多数网络程序一样，执行Redis命令所消耗的大部分时间都用在了发送命令请求和接收命令结果上面：Redis服务器处理一个命令请求通常只需要很短的时间，但客户端将命令请求发送给服务器以及服务器向客户端返回命令结果的过程却需要花费不少时间。

在通常情况下，程序需要执行的Redis命令越多，需要进行的网络通信次数也会越多，程序的执行速度也会变得越慢。通过使用Redis的流水线特性，程序可以一次把多个命令发送给Redis服务器，这可以将执行多个命令所需的网络通信次数从原来的N次降低为1次，从而使得程序的执行效率得到显著提升。

虽然Redis服务器并不会限制客户端在流水线中包含的命令数量，但是却会为客户端的输入缓冲区设置默认值为1GB的体积上限：当客户端发送的数据量超过这一限制时，Redis服务器将强制关闭该客户端。
```java
public Map<String, Object> testPipeline() {
    Long start = System.currentTimeMillis();
    List List = redisTemplate.executePipelined(new RedisCallback<Long>() {
        @Override
        public Long doInRedis(RedisConnection connection) throws DataAccessException {
            connection.openPipeline();
            for (int i = 10001; i < 20000; i++) {
                String key = "pipeline_" + i;
                String value = "value_" + i;
                connection.set(key.getBytes(), value.getBytes());
            }
            return null;
        }
    });

    Long end = System.currentTimeMillis();
    System.out.println("Pipeline插入10000条记录耗时：" + (end - start) + "毫秒。");//Pipeline插入10000条记录耗时：605毫秒。

    Map<String, Object> map = new HashMap<>();
    map.put("success", true);
    return map;
}
```

##### 12.2, 事务
事务特性：
    ● 事务可以将多个命令打包成一个命令来执行，当事务成功执行时，事务中包含的所有命令都会被执行。
    ● 相反，如果事务没有成功执行，那么它包含的所有命令都不会被执行。

```shell
1, MULTI：开启事务
通过执行MULTI命令来开启一个新的事务，这个命令在成功执行之后将返回OK
        redis> MULTI        OK       
        redis> SET title "Hand in Hand"        QUEUED       
        redis> SADD fruits "apple" "banana" "cherry"        QUEUED       
        redis> RPUSH numbers 123456 789        QUEUED
● 当一个客户端执行MULTI命令之后，它就进入了事务模式，这时用户输入的所有数据操作命令都不会立即执行，而是会按顺序放入一个事务队列中，等待事务执行时再统一执行。

2, EXEC：执行事务
当事务成功执行时，EXEC命令将返回一个列表作为结果，这个列表会按照命令的入队顺序依次包含各个命令的执行结果。
        redis> MULTI                            #1) 开启事务        
        OK       
        redis> SET title "Hand in Hand"     #2) 命令入队        
        QUEUED       
        redis> SADD fruits "apple" "banana" "cherry"        
        QUEUED       
        redis> RPUSH numbers 123456 789        
        QUEUED       
        redis> EXEC           #3)执行事务        
        1) OK                  # SET命令的执行结果        
        2) (integer) 3       # SADD命令的执行结果        
        3) (integer) 3       # RPUSH命令的执行结果

3, DISCARD：放弃事务
如果用户在开启事务之后，不想执行事务而是想放弃事务，那么只需要执行以下命令即可
        redis> MULTI        OK       
        redis> SET page_counter 10086        QUEUED       
        redis> SET download_counter 12345        QUEUED       
        redis> DISCARD        OK
```
事务的安全性：
    ● Redis事务总是具有ACID性质中的原子性、一致性和隔离性，至于是否具有耐久性则取决于Redis使用的持久化模式。

事务对服务器造成的影响：
    ● 因为事务在执行时会独占服务器，所以用户应该避免在事务中执行过多命令，更不要将一些需要进行大量计算的命令放入事务中，以免造成服务器阻塞。

事务与流水线：
    ● 流水线与事务虽然在概念上有相似之处，但它们并不相等：流水线的作用是打包发送多条命令，而事务的作用则是打包执行多条命令。
    ● 为了优化事务的执行效率，很多Redis客户端都会把待执行的事务命令缓存在本地，然后在用户执行EXEC命令时，通过流水线一次把所有事务命令发送至Redis服务器。

##### 12.3, 带有乐观锁的事务
乐观锁机制：
    ● 我们需要一种机制，它可以保证如果锁键的值在GET命令执行之后发生了变化，那么后续命令将不会被执行。在Redis中，这种机制被称为乐观锁。
```shell
1, WATCH：对指定的键进行监视
客户端可以通过执行WATCH命令，要求服务器对一个或多个数据库键进行监视，如果在客户端尝试执行事务之前，这些键的值发生了变化，那么服务器将拒绝执行客户端发送的事务，并向它返回一个空值。
        WATCH key [key ...]

2, UNWATCH：取消对所有键的监视
服务器在接收到客户端发送的UNWATCH命令之后，将不会再对之前WATCH命令指定的键实施监视，这些键也不会再对客户端发送的事务造成任何影响。
        redis> WATCH "lock_key" "user_id_counter" "msg"        
        OK       
        redis> UNWATCH     # 取消对以上3个键的监视        
        OK
● 除了显式地执行UNWATCH命令之外，使用EXEC命令执行事务和使用DISCARD命令取消事务，同样会导致客户端撤销对所有键的监视，这是因为这两个命令在执行之后都会隐式地调用UNWATCH命令。
```
通过同时使用WATCH命令和事务，用户可以构建一种乐观锁机制，这种机制可以确保事务只会在指定键没有发生任何变化的情况下执行。


### 13, Lua脚本
Lua脚本特性的出现使得Redis用户能够按需扩展Redis服务器功能。

Lua脚本与事务一样，都可以以原子方式执行多个Redis命令，并且由于Lua脚本是在服务器端执行的，所以它可以实现一些使用事务无法完成的操作。

通过Lua脚本缓存，我们可以将需要重复执行的Lua脚本缓存在服务器中，然后通过EVALSHA命令来执行已缓存的脚本，从而将执行Lua脚本所需耗费的网络带宽降至最低。
```shell
1, EVAL：执行脚本
        EVAL script numkeys key [key ...] arg [arg ...]
● script参数用于传递脚本本身。因为Redis目前内置的是Lua 5.1版本的解释器，所以用户在脚本中也只能使用Lua 5.1版本的语法。
● numkeys参数用于指定脚本需要处理的键数量，而之后的任意多个key参数则用于指定被处理的键。通过key参数传递的键可以在脚本中通过KEYS数组进行访问。根据Lua的惯例，KEYS数组的索引将以1为开始：访问KEYS[1]可以取得第一个传入的key参数，访问KEYS[2]可以取得第二个传入的key参数，以此类推。
● 任意多个arg参数用于指定传递给脚本的附加参数，这些参数可以在脚本中通过ARGV数组进行访问。与KEYS参数一样，ARGV数组的索引也是以1为开始的。
示例：
        eval "return 'hello world'" 0

1-1, 使用脚本执行Redis命令
Lua脚本的强大之处在于它可以让用户直接在脚本中执行Redis命令，这一点可以通过在脚本中调用 'redis.call()'函数或者'redis.pcall()'函数来完成。
        redis.call(command, ...)       
        redis.pcall(command, ...)
● 这两个函数接受的第一个参数都是被执行的Redis命令的名字，而后面跟着的则是任意多个命令参数。
示例：
        eval "return redis.call('set',keys[1],argv[1])" 1 "message" "hello world"
        eval "return redis.call('ZADD',KEYS[1],ARGV[1],ARGV[2],ARGV[3],ARGV[4])" 1 "fruits-price" 8.5 "apple" 3.5 "banana"

● 'redis.call()'函数和'redis.pcall()'函数都可以用于执行Redis命令，它们之间唯一不同的就是处理错误的方式。前者在执行命令出错时会引发一个Lua错误，迫使EVAL命令向调用者返回一个错误；而后者则会将错误包裹起来，并返回一个表示错误的Lua表格。
示例：
        # Lua的type()函数用于查看给定值的类型        
        redis> EVAL "return type(redis.call('WRONG_COMMAND'))" 0        
        (error)  ERR  Error  running  script  (call  to  f_2c59998e8c4eb7f9fdb467ba67ba43dfaf    8a6592): @user_script:1: @user_script: 1: Unknown Redis command called from Lua script       
        
        redis> EVAL "return type(redis.pcall('WRONG_COMMAND'))" 0        
        "table"

1-2, 值转换
在EVAL命令出现之前，Redis服务器中只有一种环境，那就是Redis命令执行器所处的环境，这一环境接受Redis协议值作为输入，然后返回Redis协议值作为输出。

但是随着EVAL命令以及Lua解释器的出现，使得Redis服务器中同时出现了两种不同的环境：一种是Redis命令执行器所处的环境，而另一种则是Lua解释器所处的环境。因为这两种环境使用的是不同的输入和输出，所以在这两种环境之间传递值将引发相应的转换操作。
    1）Lua脚本通过函数执行Redis命令时，传入的Lua值将被转换成Redis协议值；
    2）函数执行完Redis命令时，命令返回的Redis协议值将被转换成Lua值；
    3）当Lua脚本执行完毕并向EVAL命令的调用者返回结果时，Lua值将被转换为Redis协议值。

然引发转换的情况有3种，但转换操作说到底只有“将Redis协议值转换成Lua值”以及“将Lua值转换成Redis协议值”这2种，表14-1和表14-2分别展示了这2种情况的具体转换规则。

    Redis协议值转Lua值    
    1）Redis协议值为整数、字符串的情况下，转换后Lua值不变；
    2）Redis多行字符串，被转换为Lua表格；
    3）Redis状态回复（OK），转换为一个只包含ok字段的表格（ok="OK"）；
    4）Redis错误回复（错误信息），转换为一个只包含err字段的表格（err="错误信息"）；
    5）Redis空回复（nil），转换为Lua布尔值false。

    Lua值转Redis协议值
    1）Lua数字被转换为Redis整数回复（小数的话，整数部分保留，小数部分被舍弃）；
    2）Lua字符串被转换为Redis字符串；
    3）Lua表格被转换为Redis多行回复；如果表格中包含nil，那么回复只会包含nil之前的元素；
    3）只包含ok或err字段的Lua表格，被转换为Redis的状态回复或错误回复；
    4）Lua的布尔值false，被转换为空回复（nil）；
    5）Lua的布尔值true，被转换为值为1的整数回复。
示例：
        redis> EVAL "return 3.14" 0        
        (integer) 3
        redis> EVAL "return tostring(3.14)" 0    #若想要小数保留，可以使用Lua内置的tostring()函数将它转换为字符串  
        "3.14"

1-3, 全局变量保护
为了防止预定义的Lua环境被污染，Redis只允许用户在Lua脚本中创建局部变量而不允许创建全局变量，尝试在脚本中创建全局变量将引发一个错误。
示例：
        redis> EVAL "local database='redis'; return database" 0        
        "redis"
        redis> EVAL "number=10" 0    #定义一个全局变量number，那么Redis将返回一个错误    
        (error) ERR Error running script (call to f_a2754fa2d614ad76ecfd143acc06993bedf1f691):    @enable_strict_lua:8: user_script:1: Script attempted to create global variable 'number'

1-4, 在脚本中切换数据库
与普通Redis客户端一样，Lua脚本也允许用户通过执行SELECT命令来切换数据库，但需要注意的是，不同版本的Redis在脚本中执行SELECT命令的效果并不相同：
● 在Redis 2.8.12版本之前，用户在脚本中切换数据库之后，客户端使用的数据库也会进行相应的切换。
● 在Redis 2.8.12以及之后的版本中，脚本执行的SELECT命令只会对脚本自身产生影响，客户端的当前数据库不会发生变化。
示例：
        redis> SET dbnumber 0      # 将0号数据库的dbnumber键的值设置为0        
        OK       
        redis> SELECT 1             # 切换至1号数据库        
        OK       
        redis[1]> SET dbnumber 1  # 将1号数据库的dbnumber键的值设置为1        
        OK       
        redis[1]> SELECT 0          # 切换回0号数据库        
        OK       
        redis> EVAL "redis.call('SELECT', ARGV[1]); return redis.call('GET', KEYS[1])" 1 "dbnumber" 1        
        "1"     # 在脚本中切换至1号数据库，并获取dbnumber键的值       
        redis> GET dbnumber        
        "0"     # dbnumber 键的值为0，这表示客户端的当前数据库仍然是0号数据库

1-5, 脚本的原子性
Redis的Lua脚本与Redis的事务一样，都是以原子方式执行的：在Redis服务器开始执行EVAL命令之后，直到EVAL命令执行完毕并向调用者返回结果之前，Redis服务器只会执行EVAL命令给定的脚本及其包含的Redis命令调用，至于其他客户端发送的命令请求则会被阻塞，直到EVAL命令执行完毕为止。

基于上述原因，用户在使用Lua脚本的时候，必须尽可能地保证脚本能够高效、快速地执行，从而避免因为独占服务器而给其他客户端造成影响。

1-6, 以命令行方式执行脚本
用户除了可以在redis-cli客户端中使用EVAL命令执行给定的脚本之外，还可以通过redis-cli客户端的eval选项，以命令行方式执行给定的脚本文件。

在使用eval选项执行Lua脚本时，用户不需要像执行EVAL命令那样指定传入键的数量，只需要在传入键和附加参数之间使用逗号进行分割即可。

示例：
        #set_and_get.lua
        redis.call('SET', KEYS[1], ARGV[1])        
        return redis.call('GET', KEYS[1])

        λ redis-cli --eval set_and_get.lua "userName" , "Johann"


2, SCRIPT LOAD和 EVALSHA：缓存并执行脚本
Redis提供了Lua脚本缓存功能，这一功能允许用户将给定的Lua脚本缓存在服务器中，然后根据Lua脚本的SHA1校验和直接调用脚本，从而避免了需要重复发送相同脚本的麻烦。

命令SCRIPT LOAD可以将用户给定的脚本缓存在服务器中，并返回脚本对应的SHA1校验和作为结果：
        SCRIPT LOAD script
示例：
        redis> SCRIPT LOAD "return 'hello world'"
        返回值
        "5332031c6b470dc5a0dd9b4bf2030dea6d65de91"     
        
        redis> SCRIPT LOAD  "redis.call('SET',KEYS[1],ARGV[1]); return redis.call('GET', KEYS[1])"
        返回值        
        "4fe9248a84efd9e7827b438eab9034c7fdce16b3"

用户就可以通过EVALSHA命令来执行已被缓存的脚本
        EVALSHA sha1 numkeys key [key ...] arg [arg ...]
示例：
        127.0.0.1:6379> evalsha "5332031c6b470dc5a0dd9b4bf2030dea6d65de91" 0
        "hello world"
        127.0.0.1:6379> evalsha "4fe9248a84efd9e7827b438eab9034c7fdce16b3" 1 "msg" "Jessie"
        "Jessie"


3, 脚本管理
除了SCRIPT LOAD命令之外，Redis还提供了SCRIPT EXISTS、SCRIPT FLUSH和SCRIPT KILL这3个命令来管理脚本以及脚本缓存。

3-1, SCRIPT EXISTS：检查脚本是否已被缓存
        SCRIPT EXISTS sha1 [sha1 ...]
示例：
        127.0.0.1:6379> script exists "5332031c6b470dc5a0dd9b4bf2030dea6d65de91" "4fe9248a84efd9e7827b438eab9034c7fdce16b3"
        1) (integer) 1
        2) (integer) 1

3-2, SCRIPT FLUSH：移除所有已缓存脚本，移除成功后返回OK
        SCRIPT FLUSH

3-3, SCRIPT KILL：强制停止正在运行的脚本
Lua脚本在执行时会独占整个服务器，所以如果Lua脚本的运行时间过长，又或者因为编程错误而导致脚本无法退出，那么就会导致其他客户端一直无法执行命令。

配置选项lua-time-limit的值定义了Lua脚本可以不受限制运行的时长，这个选项的默认值为5000：
        lua-time-limit <milliseconds>

当脚本的运行时间低于lua-time-limit指定的时长时，其他客户端发送的命令请求将被阻塞；
相反，当脚本的运行时间超过lua-time-limit指定的时长时，向服务器发送请求的客户端将得到一个错误回复，提示用户可以使用SCRIPT KILL或者SHUTDOWN NOSAVE命令来终止脚本或者直接关闭服务器。
        SCRIPT KILL        #如果Lua脚本没有执行过写命令，此时可以使用 SCRIPT KILL命令停止运行的脚本
        SHUTDOWN nosave    #如果Lua脚本已经执行过写命令，只能用 SHUTDOWN nosave命令停止运行的脚本

SCRIPT KILL 和 SHUTDOWN nosave 区别：
● 如果正在运行的Lua脚本尚未执行过任何写命令，此时执行SCRIPT KILL命令，那么服务器将终止该脚本，然后回到正常状态，继续处理客户端的命令请求。
● 如果正在运行的Lua脚本已经执行过写命令，并且因为该脚本尚未执行完毕，所以它写入的数据可能是不完整或者错误的，为了防止这些脏数据被保存到数据库中，服务器是不会直接终止脚本并回到正常状态的。在这种情况下，用户只能使用SHUTDOWN nosave命令，在不执行持久化操作的情况下关闭服务器，然后通过手动重启服务器来让它回到正常状态。
```


### 14,持久化
Redis与传统数据库的一个主要区别在于，Redis把所有数据都存储在内存中，而传统数据库通常只会把数据的索引存储在内存中，并将实际的数据存储在硬盘中。

Redis的持久化功能可以将存储在内存中的数据库数据以文件形式存储到硬盘，并在有需要时根据这些文件的内容实施数据恢复。

为了满足不同的持久化需求，Redis提供了RDB持久化、AOF持久化和RDB-AOF混合持久化等多种持久化方式以供用户选择。如果用户有需要，也可以完全关闭持久化功能，让服务器处于无持久化状态。

```shell
1, RDB持久化
RDB持久化是Redis默认使用的持久化功能，该功能可以创建出一个经过压缩的二进制文件，其中包含了服务器在各个数据库中存储的键值对数据等信息。RDB持久化产生的文件都以．rdb后缀结尾，其中rdb代表Redis DataBase（Redis数据库）。

1-1, SAVE：阻塞服务器并创建RDB文件
通过执行SAVE命令，要求Redis服务器以同步方式创建出一个记录了服务器当前所有数据库数据的RDB文件。
SAVE命令是一个无参数命令，它在创建RDB文件成功时将返回OK作为结果。
        redis> SAVE        
        OK
● 在SAVE命令执行期间，Redis服务器将阻塞，直到RDB文件创建完毕为止。
● 如果Redis服务器在执行SAVE命令时已经拥有了相应的RDB文件，那么服务器将使用新创建的RDB文件代替已有的RDB文件

1-2, BGSAVE：以非阻塞方式创建RDB文件
BGSAVE命令与SAVE命令一样都是无参数命令，它与SAVE命令的不同之处在于，BGSAVE不会直接使用Redis服务器进程创建RDB文件，而是使用子进程创建RDB文件。
        redis> BGSAVE        
        Background saving started
● 虽然BGSAVE命令不会像SAVE命令那样一直阻塞Redis服务器，但由于执行BGSAVE命令需要创建子进程，所以父进程占用的内存数量越大，创建子进程这一操作耗费的时间也会越长，因此Redis服务器在执行BGSAVE命令时，仍然可能会由于创建子进程而被短暂地阻塞。

1-3, 通过配置选项自动创建RDB文件
可以通过设置save选项，让Redis服务器在满足指定条件时自动执行BGSAVE命令。
        save <seconds> <changes>
        save 60 10000    #当服务器在60s秒之内至少执行了10000次修改，服务器就会自动执行一次BGSAVE命令
● Redis允许用户同时向服务器提供多个save选项，当给定选项中的任意一个条件被满足时，服务器就会执行一次BGSAVE。
        save 6000 1        #在6000s（100min）之内，服务器对数据库执行了至少1次修改        
        save 600 100       #在600s（10min）之内，服务器对数据库执行了至少100次修改        
        save 60 10000      #在60s（1min）之内，服务器对数据库执行了至少10000次修改
● 为了避免服务器过于频繁地执行BGSAVE命令，每次成功创建RDB文件之后，负责自动触发BGSAVE命令的时间计数器以及修改次数计数器都会被清零并重新开始计数。
● RDB持久化是Redis默认使用的持久化方式，windows下存放在 redis.windows.conf 配置文件中。

1-4, SAVE命令和BGSAVE命令的选择
● 线上Redis服务器，使用BGSAVE命令，可以使其在创建RDB文件的同时继续为其他客户端服务。
● 维护离线的Redis服务器，使用SAVE命令，它不会因为创建子进程而消耗额外的内存。

1-5, RDB文件结构



































```































