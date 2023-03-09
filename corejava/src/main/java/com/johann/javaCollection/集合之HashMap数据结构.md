
HashMap是一个由key-value键值对组成的集合。

### 数据结构
1，从结构实现来讲，HashMap是数组+链表+红黑树（JDK1.8增加了红黑树部分）实现的。
HashMap外层是一个数组（哈希桶数组），初始化map的时候，可以设定数组的容量，如果不设置，默认是 16。
数组中的每个位置用于存储entry键值对，称之为“桶”。桶的内部数据结构可能是链表，可能是红黑树(1.8新增)。

2，HashMap就是使用哈希表来存储的。哈希表为解决冲突，可以采用开放地址法和链地址法等来解决问题，Java中HashMap采用了链地址法。链地址法，简单来说，就是数组加链表的结合。
在每个数组元素上都一个链表结构，当数据被Hash后，得到数组下标，把数据放在对应下标元素的链表上。
```text
//HashMap源码中有几个字段我们需要了解

//哈希桶数组的长度，可以自己设定，初始默认 16
1，int capacity; 

//负载因子，这个值可以自己设定，初始默认 0.75。可以大于 1
2，float loadFactor;

//HashMap内部结构发生变化的次数
3，int modCount; 

//HashMap中元素个数
4，int size; 

//capacity*loadFactor 扩容阈值，size超过这个值数组就会扩容
5，int threshold; 
```
### put方法分析

在新增一个元素的时候，会执行以下几步

1，无论是put还是remove，get元素，第一步都是通过hash算法定位元素在哈希桶数组中的位置。
```java
    /**
     * 获取key的hashcode值，并对这个hashcode做高位异运算
     */
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
    /**
     * 获取index值，取模运算[1.8中该方法没有单独列出，是直接使用的]
     */
    static int indexFor(int h, int length) {
        return h & (length-1);
    }
    
    /**
     * Returns a power of two size for the given target capacity.
     * 哈希桶数组的长度总是2的n次方
     */
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
```
确定哈希桶数组的位置简单来说，就是以下三步：
取key的hashcode值；
对这个hashcode值做高16位异运算；
取模运算。

**Hash算法：**

为了避免发生Hash碰撞，我们在对hash()方法获取到的hashcode值，对数组长度进行取模运算（h%length），尽量使得元素分布的均匀一些。
但是取模运算比较比较耗时间,我们可以看到HashMap中是使用的另外的一种方法来代替取模运算的，这是由于数组的长度总是2的n次方，
所以使用这种方法得到的结果和对数组长度进行取模运算得到的结果是一样的。


2，具体的put步骤

①.判断键值对数组table[i]是否为空或为null，否则执行resize()进行扩容(更适合的说法是：初始化)；

②.根据键值key计算hash值得到插入的数组索引i，如果table[i]==null，直接新建节点添加，转向⑥，如果table[i]不为空，转向③；

③.判断table[i]的首个元素是否和key一样，如果相同直接覆盖value，否则转向④，这里的相同指的是hashCode以及equals；

④.判断table[i] 是否为treeNode，即table[i] 是否是红黑树，如果是红黑树，则直接在树中插入键值对，否则转向⑤；

⑤.遍历table[i]，判断链表长度是否大于8，大于8的话把链表转换为红黑树，在红黑树中执行插入操作，否则进行链表的插入操作；遍历过程中若发现key已经存在直接覆盖value即可；

⑥.插入成功后，判断实际存在的键值对数量size是否超多了最大容量threshold，如果超过，进行扩容。
```text
1，1.8中链表红黑树转化：
链表转化为红黑树的条件，除了要求链表长度大于8以外，还会判断哈希桶数组的长度是否小于64，如果数组长度小于64的话，此时只会扩容，而不会把链表转化为红黑树。
这样做的目的是，避免同一个index上有太多的node节点，尽量使元素在哈希桶数组中分布的离散一些。
当index上node的个数减少到6个时，红黑树会再次转化为链表。这个步骤只有在调用 resize()扩容方法的时候，才会进行。

2，1.7和1.8链表插入的元素的区别：
1.7使用的是头插法，快速，不考虑当前链表的长度；1.8使用的是尾插法，next到链表尾部，插入新的元素，这是因为需要知道链表的长度，以便转化红黑树。
```

1.8源码
```java
public V put(K key, V value) {
 2     // 对key的hashCode()做hash
 3     return putVal(hash(key), key, value, false, true);
 4 }
 5 
 6 final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
 7                boolean evict) {
 8     Node<K,V>[] tab; Node<K,V> p; int n, i;
 9     // 步骤①：tab为空则创建（初始化哈希桶数组）
10     if ((tab = table) == null || (n = tab.length) == 0)
11         n = (tab = resize()).length;
12     // 步骤②：计算index，并对null做处理 
13     if ((p = tab[i = (n - 1) & hash]) == null) 
14         tab[i] = newNode(hash, key, value, null);
15     else {
16         Node<K,V> e; K k;
17         // 步骤③：节点key存在，直接覆盖value
18         if (p.hash == hash &&
19             ((k = p.key) == key || (key != null && key.equals(k))))
20             e = p;
21         // 步骤④：判断该链为红黑树
22         else if (p instanceof TreeNode)
23             e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
24         // 步骤⑤：该链为链表
25         else {
26             for (int binCount = 0; ; ++binCount) {
                    // 遍历到最后的节点
27                 if ((e = p.next) == null) {
                        // 在当前链表的后面，新加一个node[1.8区别于1.7]
28                     p.next = newNode(hash, key,value,null);
                        //链表长度大于等于8转换为红黑树进行处理
29                     if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st  
30                         //跳进该方法内部，可以看到，如果哈希桶数组小于 64，只会扩容而不会转化为红黑树
                           treeifyBin(tab, hash);
31                     break;
32                 }
                    // key已经存在直接覆盖value
33                 if (e.hash == hash &&
34                     ((k = e.key) == key || (key != null && key.equals(k)))) 
35							break;
36                 p = e;
37             }
38         }
39         
40         if (e != null) { // existing mapping for key
41             V oldValue = e.value;
42             if (!onlyIfAbsent || oldValue == null)
43                 e.value = value;
44             afterNodeAccess(e);
45             return oldValue;
46         }
47     }

48     ++modCount;
49     // 步骤⑥：超过最大容量 就扩容
50     if (++size > threshold)
51         resize();
52     afterNodeInsertion(evict);
53     return null;
54 }
```

