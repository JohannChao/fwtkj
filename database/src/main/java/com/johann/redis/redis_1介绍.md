# 安装配置
Windows 下安装
下载地址：https://github.com/tporadowski/redis/releases

Windows 下启动redis，打开一个cmd窗口，切换到解压目录下执行
`redis-server.exe redis.windows.conf`
可以把 redis 的路径加到系统的环境变量里,这样就省得再输路径了,后面的那个 redis.windows.conf 可以省略,如果省略,会启用默认的。

连接redis服务器，新打开一个cmd窗口，执行以下命令
`redis-cli.exe -h 127.0.0.1 -p 6379`

* Redis注册为服务：`redis-server.exe --service-install redis.windows.conf --loglevel verbose --service-name newServiceName`
* 开启服务：`redis-server --service-start`
* 关闭服务：`redis-server --service-stop`
* 卸载服务：`redis-server --service-uninstall`

# 第一部分：Redis的数据结构
### 1,字符串

字符串（string）键是Redis最基本的键值对类型，这种类型的键值对会在数据库中把单独的一个键和单独的一个值关联起来，被关联的键和值既可以是普通的文字数据，也可以是图片、视频、音频、压缩文件等更为复杂的二进制数据。

```shell
1,SET：为字符串键设置值,成功后返回OK作为结果
        SET key value
        SET key value NX  #只会在键不存在的情况下执行
        SET key value XX  #只会在键已经有值的情况下执行

2,GET：获取字符串键的值,返回与该键相关联的值
        GET key

3,GETSET：获取旧值并设置新值,首先获取字符串键目前已有的值,接着为键设置新值,最后把之前获取到的旧值返回;
        GETSET key new_value
● 如果被设置的键并不存在于数据库,那么GETSET命令将返回空值作为键的旧值        

4,MSET：一次为多个字符串键设置值,成功后返回OK,如果原来的字符串建有值,会覆盖旧的值
        MSET key value [key value ...]

5,MGET：一次获取多个字符串键的值,返回一个列表作为结果
        MGET key [key ...]

6,MSETNX：只在键不存在的情况下,一次为多个字符串键设置值,成功后返回1;
        MSETNX key value [key value ...]
● 只要有一个键存在,该命令放弃执行,此时返回0

7,STRLEN：获取字符串值的字节长度,返回字符串键存储的值的字节长度
        STRLEN key

> 字符串值的正数索引以0为开始,从字符串的开头向结尾不断递增;
> 字符串值的负数索引以-1为开始,从字符串的结尾向开头不断递减.

8,GETRANGE：获取字符串值指定索引范围上的内容
        GETRANGE key start end

9,SETRANGE：对字符串值的指定索引范围进行设置,成功后返回字符串值当前的长度作为结果;
        SETRANGE key index substitute
● 将字符串键的值从索引index开始的部分替换为指定的新内容,被替换内容的长度取决于新内容的长度;
● 当用户给定的新内容比被替换的内容更长时,SETRANGE命令就会自动扩展被修改的字符串值;
● 当用户给定的index索引超出字符串值的长度时,字符串值末尾直到索引index-1之间的部分将使用空字节进行填充,换句话说,这些字节的所有二进制位都会被设置为0

10,APPEND：追加新内容到值的末尾,返回字符串值当前的长度
        APPEND key suffix
● 如果字符串键不存在,则相当于 Set 操作

11,INCRBY、DECRBY：对整数值执行加法操作和减法操作,并返回键在执行加减法操作之后的值;
        INCRBY key increment
● 要求键对应的值是整数,且增减量也必须是整数;
● 当键不存在时,命令会先将键的值初始化为0,然后再执行相应的加法操作或减法操作.

12,INCR、DECR：对整数值执行加1操作和减1操作,除此外,其他同INCRBY、DECRBY
        INCR key

13,INCRBYFLOAT：对数字值执行浮点数加法操作,返回操作后的数值;
        INCRBYFLOAT key increment
● 键对应的值可以是浮点数也可以是整数,增减量可以是整数也可以是浮点数;
● 当键不存在时,命令会先将键的值初始化为0,然后再执行相应的加法操作或减法操作;
● 执行该命令后,最多只会保留计算结果小数点后的17位数字,超过这个范围的小数将被截断.

set article.10086.price 100.01234567890123456789
incrbyfloat article.10086.price 0
"100.01234567890124"
```

### 2,散列
散列（hash）键是一种,能够真正地把相关联的数据打包起来存储的数据结构。

```shell
1,HSET：为字段设置值,成功后返回1
        HSET hash field value

2,HSETNX：只在字段不存在的情况下为它设置值,成功后返回1,如果字段已存在,设置失败返回0
        HSETNX hash field value

3,HGET：获取字段的值
        HGET hash field

4,HINCRBY：对字段存储的整数值执行加法或减法操作,返回增减后的值;
        HINCRBY hash field increment
● 字段对应的值必须是整数,且增减量也必须是整数

5,HINCRBYFLOAT：对字段存储的数字值执行浮点数加法或减法操作,返回增减后的值;
        HINCRBYFLOAT hash field increment
● 字段对应的值必须是整数或浮点值,且增减量也必须是整数或浮点值

6,HSTRLEN：获取字段值的字节长度,字段或散列不存在,返回0
        HSTRLEN hash field

7,HEXISTS：检查字段是否存在,字段存在返回1,否则命令返回0
        HEXISTS hash field

8,HDEL：删除散列中的指定字段及其相关联的值,成功后返回1
        HDEL hash field

9,HLEN：获取散列包含的字段数量,散列不存在,返回0
        HLEN hash

10,HMSET：一次为多个字段设置值,成功返回1
        HMSET hash field value [field value ...]

11,HMGET：一次获取多个字段的值
        HMGET hash field [field ...]

12,HKEYS、HVALS、HGETALL：获取所有字段、所有值、所有字段和值
        HKEYS hash      #获取散列的所有字段名 
        HVALS hash      #获取散列的所有字段值  
        HGETALL hash    #获取散列的所有字段名和字段值,每两个连续的元素就代表了散列中的一对字段和值,其中奇数位置上的元素为字段,偶数位置上的元素则为字段的值
```

##### 字符串键和散列键对比
* 资源占用： 字符串键在数量较多的情况下，将占用大量的内存和CPU时间。与此相反，将相关联的多个数据项存储到同一个散列中可以有效地减少内存和CPU消耗。
* 支持的操作： 散列键支持的所有命令，几乎都有相应的字符串键版本，但字符串键支持的 SETRANGE，GETRANGE 等操作命令，在散列键中并不支持。
* 过期时间： 字符串键可以为每个键单独设置过期时间，独立删除某个数据项，而散列键一旦到期，会将包含在散列内的所有字段和值全部删除。
* 如何选择合适的键： ①如果程序需要为每个数据项单独设置过期时间，那么使用字符串键；②如果程序需要存储的数据项比较多，并且你希望尽可能地减少存储数据所需的内存，就应该优先考虑使用散列键；③如果多个数据项在逻辑上属于同一组或者同一类，那么应该优先考虑使用散列键。


### 3,列表
Redis的列表（list）是一种线性的有序结构，可以按照元素被推入列表中的顺序来存储元素，这些元素既可以是文字数据，又可以是二进制数据，并且列表中的元素可以重复出现。

```shell
1,LPUSH：将元素推入列表左端,返回当前列表包含的元素数量，当推入多个元素时，按照给定顺序，从左到右依次将元素推到列表的左端;
        LPUSH list item [item item ...]
● Redis 2.4.0版本开始，支持推入多个元素

2,RPUSH：将元素推入列表右端，返回当前列表包含的元素数量，当推入多个元素时，按照给定顺序，从左到右依次将元素推到列表的右端;
        RPUSH list item [item item ...]
● Redis 2.4.0版本开始，支持推入多个元素

3,LPUSHX、RPUSHX：只对已存在的列表执行推入操作,返回当前列表包含的元素数量，仅支持推入单个元素;
        LPUSHX list item       
        RPUSHX list item
● 如果列表不存在，该命令拒绝执行        

4,LPOP：弹出列表最左端的元素，并返回该元素
        LPOP list

5,RPOP：弹出列表最右端的元素，并返回该元素
        RPOP list

6,RPOPLPUSH：将source右端弹出的元素推入target左端，返回被弹出的元素作为结果;
        RPOPLPUSH source target
● 首先使用RPOP命令将源列表source最右端的元素弹出，然后使用LPUSH命令将被弹出的元素推入目标列表target左端;
● 允许源列表与目标列表相同，此时相当于将该列表最右侧的元素移动到最左侧;
● 源列表为空，此时不执行，目标列表为空，正常执行

7,LLEN：获取列表的长度,列表不存在返回0
        LLEN list

8,LINDEX：获取指定索引上的元素，返回列表在给定索引上的元素;
        LINDEX list index
● 索引既可以是正数，也可以是负数，超出索引范围，返回 nil

9,LRANGE：获取指定索引范围上的元素，0 -1 可以用于获取列表全部元素;
        LRANGE list start end        
● 如果起始和结束索引均超出范围，则返回空;
● 有一个索引超出范围，则会对这个索引进行修正，根据实际范围返回结果

10,LSET：为指定索引设置新元素，设置成功后返回OK;索引超出范围，不执行
        LSET list index new_element

11,LINSERT：将一个新元素插入列表某个指定元素的前面或者后面，插入成功后返回列表的长度;
        LINSERT list BEFORE|AFTER target_element new_element
● 如果列表键不存在，返回0;如果指定的目标元素不存在，此时返回-1;
● 如果列表中有多个重复的指定元素，此时会在第一个指定元素的前面或后面插入新的元素

12,LTRIM：修剪列表，命令接受一个列表和一个索引范围作为参数，并移除列表中位于给定索引范围之外的所有元素，只保留给定范围之内的元素;
        LTRIM list start end
● 如果有一个索引超出范围，则会对这个索引进行修正，根据实际范围保留列表元素        

13,LREM：从列表中移除指定元素，执行成功后返回被移除的元素数量;
        LREM list count element
● count参数的正负用于标识检查方向，其绝对值用于标识移除的数量
● 如果count参数的值等于0，那么LREM命令将移除列表中包含的所有指定元素。
● 如果count参数的值大于0，那么LREM命令将从列表的左端开始向右进行检查，并移除最先发现的count个指定元素。
● 如果count参数的值小于0，那么LREM命令将从列表的右端开始向左进行检查，并移除最先发现的abs(count)个指定元素（abs(count)即count的绝对值）。


14,BLPOP：阻塞式左端弹出操作,BLPOP命令是带有阻塞功能的左端弹出操作，它接受任意多个列表以及一个秒级精度的超时时限作为参数;
        BLPOP list [list ...] timeout
● 执行成功后，返回一个包含两个元素的数组，数组第一个元素标识被弹出元素的来源列表名，数组第二个元素则是被弹出元素本身;       
● BLPOP命令会按照从左到右的顺序依次检查用户给定的列表，并对最先遇到的非空列表执行左端元素弹出操作，即只会对一个列表进行弹出操作;
● 如果给定的列表，都是空列表，此时将阻塞客户端开始等待，直到向某个给定列表非空，或者等待时间超出给定阻塞时限为止;
● 如果多个客户端被同一个列表阻塞，此时将按照“先阻塞先服务”的规则，依次为客户端解除阻塞状态;
● BLPOP命令的阻塞效果只对执行这个命令的客户端有效，其他客户端以及Redis服务器本身并不会因为这个命令而被阻塞

15,BRPOP：阻塞式右端弹出操作（同 BLPOP）
        BRPOP list [list ...] timeout

16,BRPOPLPUSH：阻塞式弹出并推入操作;
        BRPOPLPUSH source target timeout
● BRPOPLPUSH命令是RPOPLPUSH命令的阻塞版本，BRPOPLPUSH命令接受一个源列表、一个目标列表以及一个秒级精度的超时时限作为参数
● 如果源列表非空，那么 BRPOPLPUSH 命令的行为就和 RPOPLPUSH 命令的行为一样;
● 如果源列表为空，那么 BRPOPLPUSH 命令将阻塞执行该命令的客户端，然后在给定的时限内等待可弹出的元素出现，或者等待时间超过给定时限为止
```

### 4,集合
Redis的集合（set）键允许用户将任意多个各不相同的元素存储到集合中，这些元素既可以是文本数据，也可以是二进制数据。

集合与列表的区别：
● 列表可以存储重复元素，而集合只会存储非重复元素，尝试将一个已存在的元素添加到集合将被忽略。
● 列表以有序方式存储元素，而集合则以无序方式存储元素。

集合与列表的时间复杂度差异：
● 在执行像LINSERT和LREM这样的列表命令时，即使命令只针对单个列表元素，程序有时也不得不遍历整个列表以确定指定的元素是否存在，因此这些命令的复杂度都为O(N)。
● 对于集合来说，因为所有针对单个元素的集合命令都不需要遍历整个集合，所以复杂度都为O(1)。

```shell
1,SADD：将元素添加到集合,返回成功添加的新元素数量作为返回值;
● 该命令会忽略添加已存在元素
        SADD set element [element ...]

2,SREM：从集合中移除元素，返回被移除的元素数量;
● 该命令会忽略不存在的元素，只移除存在的元素
        SREM set element [element ...]

3,SMOVE：将元素从一个集合移动到另一个集合，将指定的元素从源集合移动到目标集合;
● 源集合中不存在的元素，此时不执行;
● 目标集合已存在该元素，此时从源集合中移除该元素，覆盖目标集合中的元素;
● 如果目标集合不存在，将指定元素加入到新集合中
        SMOVE source target element

4,SMEMBERS：获取集合包含的所有元素
        SMEMBERS set

5,SCARD：获取集合包含的元素数量
        SCARD set        

6,SISMEMBER：检查给定元素是否存在于集合，存在则返回1，不存在则返回0
        SISMEMBER set element

7,SRANDMEMBER：可以从集合中随机地获取指定数量的元素
        SRANDMEMBER set [count]
● 如果 count 不指定，默认随机返回一个元素。
● 如果 count 为正数，则随机返回 count 个不重复的元素; count 大于集合元素数时，返回集合全部元素。
● 如果 count 为负数，则随机返回 abs(count) 个有可能重复的元素; abs(count) 大于集合元素数时，返回abs(count)个元素。

8,SPOP：随机地从集合中移除指定数量的元素，返回被移除的元素
        SPOP set [count]
● 如果 count 不指定，默认随机移除一个元素。
● count 只能为正数，count 大于集合元素数时，移除集合全部元素。


9,SINTER、SINTERSTORE：对集合执行交集计算
● SINTER 命令可以计算出用户给定的所有集合的交集，然后返回这个交集包含的所有元素
        SINTER set [set ...]
● SINTERSTORE 命令可以把给定集合的交集计算结果存储到指定的键里面
        SINTERSTORE destination_key set [set ...]
● 如果指定的键已经存在，则 SINTERSTORE 会先删除已有的键，再把集合的交集元素存储到该键


10,SUNION、SUNIONSTORE：对集合执行并集计算
● SUNION命令可以计算出用户给定的所有集合的并集，然后返回这个并集包含的所有元素
        SUNION set [set ...]
● SUNIONSTORE 命令可以把给定集合的并集计算结果存储到指定的键中，并在键已经存在的情况下自动覆盖已有的键
        SUNIONSTORE destination_key set [set ...]


11,SDIFF、SDIFFSTORE：对集合执行差集计算【集合A相对于集合B的差集 = 集合A - (集合A与集合B的交集)】
● SDIFF命令可以计算出给定集合之间的差集，并返回差集包含的所有元素
        SDIFF set [set ...]
计算超过两个集合的差集，如s1,s2,s3的差集，此时先对s1,s2进行差集计算，得到一个临时集合，再将这个临时集合与s3进行差集计算 
● SDIFFSTORE 命令可以把给定集合之间的差集计算结果存储到指定的键中，并在键已经存在的情况下自动覆盖已有的键
        SDIFFSTORE destination_key set [set ...]
```
##### 执行集合计算的注意事项

因为对集合执行交集、并集、差集等集合计算需要耗费大量的资源，所以用户应该尽量使用SINTERSTORE等命令来存储并重用计算结果，而不要每次都重复进行计算。

此外，当集合计算涉及的元素数量非常大时，Redis服务器在进行计算时可能会被阻塞。这时，我们可以考虑使用Redis的复制功能，通过从服务器来执行集合计算任务，从而确保主服务器可以继续处理其他客户端发送的命令请求。

### 5,有序集合
Redis的有序集合（sorted set）同时具有“有序”和“集合”两种性质，这种数据结构中的每个元素都由一个成员和一个与成员相关联的分值组成，其中成员以字符串方式存储，而分值则以64位双精度浮点数格式存储。

需要注意的是，虽然同一个有序集合不能存储相同的成员，但不同成员的分值却可以是相同的。当两个或多个成员拥有相同的分值时，Redis将按照这些成员在字典序中的大小对其进行排列。

```shell
1,ZADD：添加或更新成员,返回成功添加的新成员数量（可以通过设置  CH 选项，将返回值设定为“被修改成员”的数量）
        ZADD sorted_set score member [score member ...]
● 执行 ZADD 命令时，如果给的成员已经存在，且分值和现有的分值不同，那么此时会更新该成员。

        ZADD sorted_set [XX|NX] score member [score member ...]
● 如果给定的选项是 NX，此时只会向有序集合添加新成员，而不会对已有的成员进行任何更新。     
● 如果给定的选项是 XX，此时只会对有序集合已有的成员进行更新，而不会向有序集合添加任何新成员。

        ZADD sorted_set [CH] score member [score member ...]
● 通过 CH 选项，可以将返回值修改为 “被修改成员”的数量。“被修改成员”指的是新添加到有序集合的成员，以及分值被更新了的成员。
 示例: 
        zadd salary xx ch 6500 "johann" 7000 "jessie" 4200 "peter"

2,ZREM：移除指定的成员,返回被移除成员的数量
        ZREM sorted_set member [member ...]
● 如果用户给定的某个成员并不存在于有序集合中，那么ZREM将自动忽略该成员。        

3,ZSCORE：获取与给定成员相关联的分值
        ZSCORE sorted_set member
● 如果用户给定的有序集合并不存在，或者有序集合中并未包含给定的成员，那么ZSCORE命令将返回空值

4,ZINCRBY：对指定成员的分值执行自增或自减操作，返回给定成员执行完自增或自减操作后当前的分值
        ZINCRBY sorted_set increment member
● 如果给定成员不存在，则会直接把给定的成员加入到当前的有序集合，再把这个自增量设置为该成员的分值。
● 如果有序集合键不存在，那么会创建给定名称的有序集合，然后再把给定成员加入到这个集合中，分值为自增量。

5,ZCARD：获取有序集合的大小
        ZCARD sorted_set

6,ZRANK、ZREVRANK：获取指定成员在有序集合中的排名索引
        ZRANK sorted_set member               #升序排名    
        ZREVRANK sorted_set member            #降序排名
● 无论是升序还是降序排名，排名索引数值均是从 0 开始递增。
● 如果给定成员或者给定的有序集合键不存在，返回 nil。

7,ZRANGE、ZREVRANGE：获取指定索引范围内的成员
        ZRANGE sorted_set start end      #升序排名   
        ZREVRANGE sorted_set start end   #降序排名
● 索引均是闭区间。
● 支持使用负数索引。整数索引从指定方向从 0 开始，负数索引从反方向从 -1 开始。
        ZRANGE sorted_set start end [WITHSCORES]       
        ZREVRANGE sorted_set start end [WITHSCORES]
● 给定 WITHSCORES 选项，即可在返回索引范围内的成员时，一并返回他们的分值。


8,ZRANGEBYSCORE、ZREVRANGEBYSCORE：获取指定分值范围内的成员，用户可以以升序排列或者降序排列的方式获取有序集合中分值介于指定范围内的成员
        ZRANGEBYSCORE sorted_set min max       
        ZREVRANGEBYSCORE sorted_set max min
● 升序排列，参数区间为 min---max；降序排列，参数区间是 max---min。        
        ZRANGEBYSCORE sorted_set min max [WITHSCORES]       
        ZREVRANGEBYSCORE sorted_set max min [WITHSCORES]
● 给定 WITHSCORES 选项，获取成员对应的分值
        ZRANGEBYSCORE sorted_set min max [LIMIT offset count]       
        ZREVRANGEBYSCORE sorted_set max min [LIMIT offset count]
● 可以使用 LIMIT 选项来限制命令返回的成员数量，其中offset参数用于指定命令在返回结果之前需要跳过的成员数量，而count参数则用于指示命令最多可以返回多少个成员。
示例：
        zrangebyscore salary 3000 7000 withscores limit 1 2    #该命令将范围分值在 3000--7000 区间内的第2个和第3个成员。
        ZRANGEBYSCORE sorted_set (min (max       
        ZREVRANGEBYSCORE sorted_set (max (min
        zrangebyscore salary (3000 (7000 withscores
● 在默认情况下，ZRANGEBYSCORE命令和ZREVRANGEBYSCORE命令接受的分值范围都是闭区间分值范围，在分值前加一个 "(" 可以将区间范围改为开区间。
        ZRANGEBYSCORE sorted_set -inf +inf       
        ZREVRANGEBYSCORE sorted_set +inf -inf
● 区间范围，除了可以是普通的分值，还可以是特殊值 "-inf"和"+inf"，分别表示负无穷大和正无穷大。
 

9,ZCOUNT：统计出有序集合中分值介于指定范围之内的成员数量
        ZCOUNT sorted_set min max
● 分值区间默认是闭区间，可以在分值前加一个 "(" 可以将区间范围改为开区间。
● 分值除了可以是普通分值外，还可以是特殊值 "-inf"和"+inf"。

10,ZREMRANGEBYRANK：可以从升序排列的有序集合中移除位于指定排名范围内的成员，然后返回被移除成员的数量
        ZREMRANGEBYRANK sorted_set start end
● 支持使用负数索引。整数索引从指定方向从 0 开始，负数索引从反方向从 -1 开始。

11,ZREMRANGEBYSCORE：可以从有序集合中移除位于指定分值范围内的成员，并在移除操作执行完毕返回被移除成员的数量
        ZREMRANGEBYSCORE sorted_set min max
● 分值区间默认是闭区间，可以在分值前加一个 "(" 可以将区间范围改为开区间。
● 分值除了可以是普通分值外，还可以是特殊值 "-inf"和"+inf"。


12,ZUNIONSTORE、ZINTERSTORE：有序集合的并集运算和交集运算。
        ZUNIONSTORE destination numbers sorted_set [sorted_set ...]       
        ZINTERSTORE destination numbers sorted_set [sorted_set ...]
● destination参数，运算结果存储在 destination键对应的有序集合中。
● numbers参数，用于指定参与计算的有序集合数量，这个数值必须与之后参与运算的集合数量相同，否则 (error) ERR syntax error。
● 默认情况下，给定有序集合中的同名成员，他们的分值相加（SUM）得到的分值，作为结果集中该成员新的分值。
        ZUNIONSTORE destination numbers sorted_set [sorted_set ...] [AGGREGATE SUM|MIN|MAX]       
        ZINTERSTORE destination numbers sorted_set [sorted_set ...] [AGGREGATE SUM|MIN|MAX]
        zunionstore zset_store1 3 salary salary2 salary3 aggregate max
● 通过给定 AGGREGATE 选项，用户可以决定使用哪个聚合函数来计算结果有序集合成员的分值。即，各个有序集合中的同名成员在结果集中的新分值，如何计算？
● SUM：结果集中的分值，由给定集合中同名成员的分值相加得到；MAX/MIN：结果集中的分值，是给定集合中同名成员中的最大/最小分值。    
        ZUNIONSTORE destination numbers sorted_set [sorted_set ...] [WEIGHTS weight [weight ...]]       
        ZINTERSTORE destination numbers sorted_set [sorted_set ...] [WEIGHTS weight [weight ...]]  
        zunionstore zset_store1 3 salary salary2 salary3 weights 1 1 0.8 aggregate sum
● 通过给定 WEIGHTS 选项，可以为每个有序集合分别设置一个权重，执行 并集、交集运算时，会将集合中的各个成员的分值与集合的权重值相乘，得出成员新的分值，然后再执行聚合计算。

注意：
● ZUNIONSTORE和ZINTERSTORE除了可以使用有序集合作为输入之外，还可以使用集合（set）作为输入：在默认情况下，这两个命令将把给定集合看作所有成员的分值都为1的有序集合来进行计算。剩下的 权重选项 WEIGHTS 以及聚合函数选项 AGGREGATE，同样适用于普通集合（set）。


13,ZRANGEBYLEX、ZREVRANGEBYLEX：对于按照字典序排列的有序集合，该命令可以返回有序集合中位于字典序指定范围内的成员
        ZRANGEBYLEX sorted_set min max
● min、max参数如果带有 "[" 则表示这是一个闭区间，"(" 表示这是一个开区间。
● 加号"+"表示无穷大，减号"-"表示无穷小。 

示例：
        zadd words 0 address 0 after 0 apple 0 bamboo 0 banana 0 bear 0 book 0 candy 0 cat 0 client #创建一个字典序排列的有序集合
        zrangebylex words [afu (b    #返回成员的前三个字符在“afu”之后，且首个字符在“b”之前的成员
        
        ZREVRANGEBYLEX sorted_set max min
● ZREVRANGEBYLEX命令是逆序版的ZRANGEBYLEX命令，且min、max参数顺序相反。其他和ZRANGEBYLEX命令完全相同。
        ZRANGEBYLEX sorted_set min max [LIMIT offset count]       
        ZREVRANGEBYLEX sorted_set max min [LIMIT offset count]
● 可以使用 LIMIT 选项来限制命令返回的成员数量，其中offset参数用于指定命令在返回结果之前需要跳过的成员数量，而count参数则用于指示命令最多可以返回多少个成员。


14,ZLEXCOUNT：对于按照字典序排列的有序集合，可以使用ZLEXCOUNT命令统计有序集合中位于字典序指定范围内的成员数量
        ZLEXCOUNT sorted_set min max
        ZLEXCOUNT words [a (b
● min，max参数同 ZRANGEBYLEX 命令 

15,ZREMRANGEBYLEX：对于按照字典序排列的有序集合，可以使用ZREMRANGEBYLEX命令去移除有序集合中位于字典序指定范围内的成员
        ZREMRANGEBYLEX sorted_set min max
● min，max参数同 ZRANGEBYLEX 命令   

16,ZPOPMAX、ZPOPMIN：分别用于移除并返回有序集合中分值最大和最小的N个元素，若没有显示的给定count参数，默认只会移除一个元素。
          ZPOPMAX sorted_set [count]          
          ZPOPMIN sorted_set [count]
● Redis 5.0版本新添加的两个命令

17,BZPOPMAX、BZPOPMIN：阻塞式最大/最小元素弹出操作，返回一个包含3个项的列表，这3个项分别为被弹出元素所在的有序集合、被弹出元素的成员以及被弹出元素的分值。
        BZPOPMAX sorted_set [sorted_set ...] timeout       
        BZPOPMIN sorted_set [sorted_set ...] timeout
● 是ZPOPMAX命令以及ZPOPMIN命令的阻塞版本。
● 接收到参数的BZPOPMAX命令和BZPOPMIN命令会依次检查用户给定的有序集合，并从它遇到的第一个非空有序集合中弹出指定的元素。
● timeout 用于设置超时时限，0 意味着该命令会一直阻塞，直到可弹出的元素出现为止。
```

### 6,HyperLogLog
HyperLogLog是【一个专门为了计算集合的基数而创建的概率算法】，对于一个给定的集合，HyperLogLog可以计算出这个集合的近似基数：近似基数并非集合的实际基数，它可能会比实际的基数小一点或者大一点，但是估算基数和实际基数之间的误差会处于一个合理的范围之内，因此那些不需要知道实际基数或者因为条件限制而无法计算出实际基数的程序就可以把这个近似基数当作集合的基数来使用。

HyperLogLog的优点在于【它计算近似基数所需的内存并不会因为集合的大小而改变，无论集合包含的元素有多少个，HyperLogLog进行计算所需的内存总是固定的，并且是非常少的】。具体到实现上，Redis的每个HyperLogLog只需要使用12KB内存空间，就可以对接近：264个元素进行计数，而算法的标准误差仅为0.81%，因此它计算出的近似基数是相当可信的。

```shell
1, PFADD：使用HyperLogLog对给定的一个或多个集合元素进行计数。根据给定的元素是否已经进行过计数，PFADD命令可能返回0，也可能返回1。
        PFADD hyperloglog element [element ...]
● 如果给定的所有元素都已经进行过计数，那么PFADD命令将返回0，表示HyperLog-Log计算出的近似基数没有发生变化。
● 与此相反，如果给定的元素中出现了至少一个之前没有进行过计数的元素，导致HyperLogLog计算出的近似基数发生了变化，那么PFADD命令将返回1。
示例：        
        redis> PFADD alphabets "a" "b" "c"        (integer) 1
        redis> PFADD alphabets "a"        (integer) 0


2, PFCOUNT：PFCOUNT命令来获取HyperLogLog为集合计算出的近似基数。
        PFCOUNT hyperloglog [hyperloglog ...]
● 如果对多个 HyperLogLog，执行PFCOUNT命令，此时将返回并集的近似基数
示例：
        redis> PFADD alphabets1 "a" "b" "c"        (integer) 1       
        redis> PFADD alphabets2 "c" "d" "e"        (integer) 1
        redis> PFCOUNT alphabets1 alphabets2        (integer) 5
● HyperLogLog执行并集计算的效果与多个集合首先执行并集计算，然后再使用HyperLogLog去计算并集集合的近似基数的效果类似。


3, PFMERGE：对多个给定的HyperLogLog执行并集计算，然后把计算得出的并集HyperLogLog保存到指定的键中。如果指定的键已经存在，那么PFMERGE命令将覆盖已有的键。PFMERGE命令在成功执行并集计算之后将返回OK作为结果。
        PFMERGE destination hyperloglog [hyperloglog ...]
上述PFCOUNT示例中类似于：
        redis> PFMERGE temp-hyperloglog alphabets1 alphabets2        OK      
        redis> PFCOUNT temp-hyperloglog        (integer) 5
```

### 7,位图
Redis的位图（bitmap）是由多个二进制位组成的数组，数组中的每个二进制位都有与之对应的偏移量（也称索引），用户通过这些偏移量可以对位图中指定的一个或多个二进制位进行操作。

```shell
1, SETBIT：为位图指定偏移量上的二进制位设置值,返回二进制位被设置之前的旧值
        SETBIT bitmap offset value
● 如果位图不存在或者位图当前的大小无法满足设置操作，那么Redis将对被设置的位图进行扩展。
● 如果设置的是索引 < 8,那么这个位图是 8位的；同理，8*(n-1) <= 索引 < 8*n,那么这个位图是 8*n位的。Redis会将其他未被设置的二进制位的值初始化为0。
● 位图设置的偏移量（索引）只能为正数。

2, GETBIT：获取位图指定偏移量上的二进制位的值
        GETBIT bitmap offset
● 与SETBIT命令一样，GETBIT命令也只能接受正数作为偏移量。
● 如果输入的偏移量超过了位图目前拥有的最大偏移量，此时返回 0。换句话说，GETBIT命令会把位图中所有不存在的二进制位的值都看作0。

3, BITCOUNT：统计位图中值为1的二进制位数量
        BITCOUNT key
        BITCOUNT bitmap [start end]    #只统计位图指定字节范围内的二进制位        
● 注意：此处的 [start end] 参数用来指定的是【字节偏移量】，而不是二进制位偏移量，和二进制位偏移量一样，都是从左向右从 0 开始计数。
● 此处的 [start end] 除了可以是正数字节偏移量，还可以是负数字节偏移量，倒数第一个字节负数偏移量是 -1 ，以此往右类推。BITCOUNT bitmap -2 -1。 


4, BITPOS：在位图中查找第一个被设置为指定值的二进制位，并返回这个二进制位的偏移量
        BITPOS bitmap value
● bitpos bitmap001 0 返回 1；在 bitmap001 位图中，第一个被设置为 0 的索引号是 1 
● bitpos bitmap001 1 返回 0；在 bitmap001 位图中，第一个被设置为 1 的索引号是 0

        BITPOS bitmap value [start end]    #只在指定的字节范围内进行查找，返回的是【绝对偏移量】，而非相对偏移量
● bitpos bitmap003 0 0 0 返回 0；在 bitmap003 位图的第一个字节中，第一个被设置为 0 的索引号是 0。 
● bitpos bitmap003 0 1 1 返回 8；在 bitmap003 位图的第二个字节中，第一个被设置为 0 的索引号是 8。
● bitpos bitmap003 0 2 2 返回 16；在 bitmap003 位图的第三个字节中，第一个被设置为 0 的索引号是 16。              
● 字节范围，同样可以使用负数字节偏移量。
● 如果一个位图不存在，或者在指定的字节范围内，没有查到指定值，此时返回 -1。 
● 有一个例外，如果一个位图 bitmapAll-1 的有效索引范围是[0--7]，当前位图的二进制位是 [11111111]。此时不选定字节范围，执行 bitpos bitmapAll-1 0，得到的结果是 8 。这是因为它在对位图中偏移量从0到7的8个二进制位进行检查之后，都没有找到值为0的二进制位，于是它继续移动指针，尝试去检查偏移量为8的二进制位，但是由于偏移量8已经超出了位图的有效偏移量范围，而Redis又会把位图中不存在的二进制位的值看作0，所以BITPOS命令最后就把偏移量8作为结果返回。


5, BITOP：对一个或多个位图执行指定的二进制位运算，并将运算结果存储到指定的键，返回被存储位图的字节长度
        BITOP operation result_key bitmap [bitmap ...]
● operation参数：AND---逻辑并操作，OR---逻辑或操作，XOR---逻辑异或操作，以上三者允许输入任意数量的位图。NOT---逻辑非操作，只允许输入一个位图。
● 多个位图进行异或操作时，先将前两个进行异或操作，在将临时结果与第三个进行异或操作，以此类推至最后一个。
● 多个位图进行逻辑操作时，长度较短的那个位图，将在其右侧进行补 0 ，使其与最长的那个位图长度相同。


6,BITFIELD：允许用户在位图中的任意区域（field）存储指定长度的整数值，并对这些整数值执行加法或减法操作。
BITFIELD命令支持SET、GET、INCRBY、OVERFLOW这4个子命令。

6-1, BITFIELD SET子命令，会返回指定区域被设置之前的旧值作为执行结果
        BITFIELD bitmap SET type offset value
● offset参数用于指定设置的起始偏移量。
● type参数用于指定被设置值的类型，这个参数的值需要以i或者u为前缀，后跟被设置值的位长度，其中i表示被设置的值为有符号整数，而u则表示被设置的值为无符号整数。BITFIELD的各个子命令目前最大能够对64位长的有符号整数（i64）和63位长的无符号整数（u63）进行操作。
● value参数用于指定被设置的整数值，这个值的类型应该和type参数指定的类型一致。如果给定值的长度超过了type参数指定的类型，那么SET命令将根据type参数指定的类型截断给定值。
● BITFIELD命令允许用户在一次调用中执行多个 SET子命令。
6-1-1,除了根据偏移量对位图进行设置之外，SET子命令还允许用户根据给定类型的位长度，对位图在指定索引上存储的整数值进行设置
        BITFIELD bitmap SET type #index value    #根据索引对区域进行设置
示例： 
        bitfield bitmap_overflow set u4 #0 15 set u4 #1 15 set u8 #2 15
● 上述命令中 u4 是4个二进制位为一组，'#0'是第一个“4位组”（相当于起始偏移量是 0），'#1'是第二个“4位组”（相当于起始偏移量是 4）；u8 是8个二进制位为一组，'#2'是第三个“8位组”（相当于起始偏移量是 16）。

6-2, BITFIELD GET子命令，从给定的偏移量或者索引中取出指定类型的整数值
        BITFIELD bitmap GET type offset       
        BITFIELD bitmap GET type #index    #根据索引对区域进行设置
● BITFIELD命令允许用户在一次调用中执行多个 GET子命令。

6-3, BITFIELD INCRBY子命令，对位图存储的整数值执行加法操作或者减法操作，返回执行加减操作后当前位置的最新值
        BITFIELD bitmap INCRBY type offset increment       
        BITFIELD bitmap INCRBY type #index increment    #根据索引对区域进行设置

6-4, BITFIELD OVERFLOW子命令，处理溢出。
● 使用INCRBY子命令执行完加减操作后，新的数值可能超出指定类型的长度，发生溢出，OVERFLOW WRAP|SAT|FAIL 用于处理溢出，如果未设置，默认使用WRAP方式处理计算溢出。
● OVERFLOW子命令只会对同一个BITFIELD调用中排在它之后的那些INCRBY子命令产生效果，所以用户必须把OVERFLOW子命令放到它想要影响的INCRBY子命令之前。
        BITFIELD bitmap OVERFLOW WRAP|SAT|FAIL INCRBY type #index increment
● WRAP表示使用回绕（wrap around）方式处理溢出，这也是C语言默认的溢出处理方式。在这一模式下，向上溢出的整数值将从类型的最小值开始重新计算，而向下溢出的整数值则会从类型的最大值开始重新计算。
● SAT表示使用饱和运算（saturation arithmetic）方式处理溢出，在这一模式下，向上溢出的整数值将被设置为类型的最大值，而向下溢出的整数值则会被设置为类型的最小值。
● FAIL表示让INCRBY子命令在检测到计算会引发溢出时拒绝执行计算，并返回空值表示计算失败。
示例：
        bitfield bitmap_overflow set u4 #0 15 set u4 #1 15 set u4 #2 15    #新增一个新的位图
        bitfield bitmap_overflow overflow wrap incrby u4 #0 1 overflow sat incrby u4 #1 1 overflow fail incrby u4 #2 1    #处理溢出
        1) (integer) 0    2) (integer) 15    3) (nil)    #最终结果


```
##### 使用位图实现紧凑计数器
为每个玩家创建一个计数器，记录用户当月登录的次数，使用位图实现。
1，使用16位无符号整数计数器，该计数器的存储上限是 2^16=65536，这个数值可以满足我们的计数需求。
2，用户的ID是一个整数类型，这个ID可以作为计数器的索引键，即 ID=1的用户，对应的是第1个16位整数计数器，ID=10086的用户，对应的是第10086个16位整数计数器。
3，使用 SAT方式处理溢出，即使是极端情况，用户本月登录了 65536次，此时计数器只会被设置为 65535 而不会继续新增。
```shell
        BITFIELD 202208_login_counter SET u16 #0 0        #初始化一个计数器位图
        bitfield 202208_login_counter overflow SAT incrby u16 #10086 1        #ID=10086的用户第一次登录
        bitfield 202208_login_counter overflow SAT incrby u16 #10086 1        #ID=10086的用户第二次登录
        bitfield 202208_login_counter GET u16 #10086        #获取D=10086的用户的登录次数
```

##### 使用位图存储整数的原因

在一般情况下，当用户使用字符串或者散列去存储整数的时候，Redis都会为被存储的整数分配一个long类型的值（通常为32位长或者64位长），并使用对象去包裹这个值，然后再把对象关联到数据库或者散列中。

与此相反，BITFIELD命令允许用户自行指定被存储整数的类型，并且不会使用对象去包裹这些整数，因此当我们想要存储长度比long类型短的整数，并且希望尽可能地减少对象包裹带来的内存消耗时，就可以考虑使用位图来存储整数。

### 8,地理坐标
Redis GEO[插图]是Redis在3.2版本中新添加的特性，通过这一特性，用户可以将经纬度格式的地理坐标存储到Redis中，并对这些坐标执行距离计算、范围查找等操作。

### 9,流
TODO