https://www.cnblogs.com/Sinte-Beuve/p/5758971.html

https://blog.csdn.net/autfish/article/details/51203709
https://blog.csdn.net/autfish/article/details/51244787

https://blog.csdn.net/qq_41755845/article/details/124539461

https://www.jianshu.com/p/ce06b4aab704
https://www.jianshu.com/p/a344409cf08a

https://blog.csdn.net/yin__ren/article/details/91129041

### log4j2单独使用

#### 引入依赖
```xml
<!--单独使用log4j2-->
<!-- log4j2日志门面 -->
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-api</artifactId>
    <version>2.17.2</version>
</dependency>
<!-- log4j2日志实现 -->
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.17.2</version>
</dependency>
<!--单独使用log4j2-->
```

代码中单独使用 log4j
```java
package com.johann.log.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @ClassName: log4jTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class log4jTest {

    //private static final Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    private static final Logger logger = LogManager.getLogger(log4jTest.class);

    public static void main(String[] args) throws Exception{
        logger.trace("trace level");
        logger.debug("debug level");
        logger.info("info level");
        logger.warn("warn level");
        logger.error("error level");
        logger.fatal("fatal level");
    }
}
```

### slf4j+log4j2

#### 引入依赖
```xml
<!--slf4j+log4j2-->
<!-- 使用slf4j作为日志门面 -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.36</version>
</dependency>
<!-- 使用log4j的适配器进行绑定 -->
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-slf4j-impl</artifactId>
    <version>2.17.2</version>
</dependency>
<!-- log4j2日志门面 -->
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-api</artifactId>
    <version>2.17.2</version>
</dependency>
<!-- log4j2日志实现 -->
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.17.2</version>
</dependency>
<!--slf4j+log4j2-->
```

#### log4j2 配置


















### 参考
[Java日志框架的使用](https://blog.csdn.net/qq_41755845/article/details/124539461)