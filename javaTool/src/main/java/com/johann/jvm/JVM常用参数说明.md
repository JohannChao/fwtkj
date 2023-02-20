### X参数
* -Xms：设置堆的最小空间大小。
* -Xmx：设置堆的最大空间大小。
* -Xmn：新生代大小。
* -Xss：设置每个线程的堆栈大小。
* -Xloggc:/usr/local/gc_%t_%p.log：将gc信息打印到指定的文件中，通过时间戳生成文件名

### XX参数
* -XX:NewSize：设置新生代最小空间大小。
* -XX:MaxNewSize：设置新生代最大空间大小。
* -XX:PermSize：设置永久代最小空间大小。
* -XX:MaxPermSize：设置永久代最大空间大小。

* -XX:CMSInitiatingPermOccupancyFraction：当永久区占用率达到这一百分比时，启动CMS回收
* -XX:CMSInitiatingOccupancyFraction：设置CMS收集器在老年代空间被使用多少后触发
* -XX:+CMSClassUnloadingEnabled：允许对类元数据进行回收
* -XX:CMSFullGCsBeforeCompaction：设定进行多少次CMS垃圾回收后，进行一次内存压缩
* -XX:NewRatio:新生代和老年代的比
* -XX:ParallelCMSThreads：设定CMS的线程数量
* -XX:ParallelGCThreads：设置用于垃圾回收的线程数
* -XX:SurvivorRatio：设置eden区大小和survivior区大小的比例
* -XX:+UseParNewGC：在新生代使用并行收集器
* -XX:+UseParallelGC ：新生代使用并行回收收集器
* -XX:+UseParallelOldGC：老年代使用并行回收收集器
* -XX:+UseSerialGC：在新生代和老年代使用串行收集器
* -XX:+UseConcMarkSweepGC：新生代使用并行收集器，老年代使用CMS+串行收集器
* -XX:+UseCMSCompactAtFullCollection：设置CMS收集器在完成垃圾收集后是否要进行一次内存碎片的整理
* -XX:UseCMSInitiatingOccupancyOnly：表示只在到达阀值的时候，才进行CMS回收
* -XX:-OmitStackTraceInFastThrow：关闭（省略异常栈从而快速抛出），默认开启。如果想将所有异常信息都抛出，建议关闭。
* -XX:+HeapDumpOnOutOfMemoryError：表示当JVM发生OOM时，自动生成DUMP文件。
* -XX:HeapDumpPath=/usr/local/dump：dump文件路径或者名称。如果不指定文件名，默认为：java_<pid>_<date>_<time>_heapDump.hprof

* -XX:NewRatio:设置年轻代和年老代的比值。如:为3，表示年轻代与年老代比值为1：3，年轻代占整个年轻代年老代和的1/4
* -XX:SurvivorRatio:年轻代中Eden区与两个Survivor区的比值。注意Survivor区有两个。如：3，表示Eden：Survivor=3：2，一个Survivor区占整个年轻代的1/5