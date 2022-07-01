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
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--
status=“warn” 日志框架本身的输出日志级别
monitorInterval="5" 自动加载配置文件的间隔时间，不低于5秒
-->
<configuration status="warn" monitorInterval="5">
    <!-- 日志处理 -->
    <appenders>
        <!-- 控制台输出 appender对象 -->
        <Console name="Console" target="SYSTEM_OUT">
            <!-- 日志消息格式 -->
            <PatternLayout pattern="[%d{HH:mm:ss.SSS}] [%t] [%-5level] %c{36}:%L --- %m%n"/>
        </Console>

        <!-- 日志文件输出 appender对象 -->
        <File name="file" fileName="./log4j_logs/log4j2.log">
            <!-- 日志消息格式 -->
            <PatternLayout pattern="[%d{yyyy-MM-dd HH:mm:ss.SSS}] [%-5level] %l %c{36} --- %m%n"/>
        </File>

        <!-- 使用随机读写流的日志文件输出，appender对象，性能提高 -->
        <RandomAccessFile name="accessFile" fileName="./log4j_logs/log4j-access.log">
            <!-- 日志消息格式 -->
            <PatternLayout pattern="[%d{yyyy-MM-dd HH:mm:ss.SSS}] [%t] [%-5level] %l %c{36} --- %m%n"/>
        </RandomAccessFile>

        <!-- 按照一定规则拆分的日志文件，appender对象 -->
        <RollingFile name="rollingFile" fileName="./log4j_logs/rollingFile.log"
                     filePattern="./log4j_logs/$${date:yyyy-MM-dd}/rolling-%d{yyyy-MM-dd-HH}.%i.log">
            <!--日志级别过滤，只接受程序中DEBUG级别的日志进行处理-->
            <ThresholdFilter level="DEBUG" onMatch="ACCEPT" onMismatch="DENY"/>
            <!-- 日志消息格式 -->
            <PatternLayout pattern="[%d{yyyy-MM-dd HH:mm:ss.SSS}] [%t] [%-5level] %l %c{36} --- %m%n"/>
            <policies>
                <!-- 在系统启动时，触发拆分规则，生成一个新的日志文件 -->
                <OnStartupTriggeringPolicy/>
                <!-- 按照文件大小拆分，10MB -->
                <SizeBasedTriggeringPolicy size="10MB"/>
                <!-- 按照时间节点拆分，规则根据 filePattern 定义的-->
                <TemiBaseTriggeringPolicy/>
            </policies>
            <!-- 防止日志文件太大，造成服务器内存溢出的问题，
                在同一个目录下（也就是每天），文件的个数限定为 30 个，超过就进行覆盖 -->
            <DefaultRolloverStrategy max="30"/>
        </RollingFile>
    </appenders>

    <!-- 定义logger，只有定义了logger并引入的appender，appender才会生效 -->
    <loggers>
        <!--默认的root的logger，使用root配置日志级别-->
        <root level="trace">
            <!--指定日志处理器-->
            <appender-ref ref="Console"/>
            <appender-ref ref="file"/>
        </root>

        <!-- 自定义logger对象 -->
        <logger name="com.johann.log.log4j" level = "debug" additivity="false">
            <appender-ref ref="Console"/>
            <!--<appender-ref ref="file"/>-->
            <appender-ref ref="accessFile"/>
            <appender-ref ref="rollingFile"/>
        </logger>

        <!-- 自定义logger对象，使用异步日志 AsyncLogger 方式
        includeLocation="false" 关闭日志记录的行号信息，开启输出行号信息性能可能会比同步记录还差-->
        <AsyncLogger name="com.johann.log.log4j_asyncLogger" level = "info" includeLocation="false" additivity="false">
            <appender-ref ref="Console"/>
            <appender-ref ref="file"/>
            <!--<appender-ref ref="accessFile"/>
            <appender-ref ref="rollingFile"/>-->
        </AsyncLogger>
    </loggers>
</configuration>
```
    
#### log4j2异步日志功能
使用log4j2的异步日志功能，需要添加以下依赖
```xml
<!-- log4j2异步日志依赖 -->
<dependency>
    <groupId>com.lmax</groupId>
    <artifactId>disruptor</artifactId>
    <version>3.4.4</version>
</dependency>
```

##### AsyncAppender 方式添加异步日志 
这种方式使用不多，因为它的性能跟logback相差无几。
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration status="warn" monitorInterval="5">
    <!-- 日志处理 -->
    <appenders>
        <!-- 日志文件输出 appender对象 -->
        <File name="file" fileName="./logs/log4j2.log">
            <!-- 日志消息格式 -->
            <PatternLayout pattern="[%d{yyyy-MM-dd HH:mm:ss.SSS}] [%-5level] %l %c{36} --- %m%n"/>
        </File>

        <!-- 使用异步日志 -->
        <!-- AsyncAppender 方式 -->
        <Async name="Async">
            <AppenderRef ref="file"/>
        </Async>
    </appenders>

    <loggers>
        <!-- 自定义logger对象 -->
        <logger name="top.mengh" level="trace" additivity="false">
            <appender-ref ref="Console"/>
            <!-- 使用异步日志 appender 对象 -->
            <AppenderRef ref="Async"/>
        </logger>
    </loggers>
</configuration>
```

##### AsyncLogger 方式添加异步日志 
AsyncLogger 是官方推荐的异步方式，它可以使得调用logger.log返回的更快，有两种选择，可以使用全局异步和混合异步。

* 全局异步：就是所有的日志都使用异步记录，在配置文件上不用做任何的改动，只需要添加一个log4j2.component.properties配置文件，并添加内容：
```text
Log4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector
```
* 混合异步（局部异步）：就是你可以在应用中同时使用同步日志和异步日志，这使得日志的配置方式更加灵活。
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--
status=“warn” 日志框架本身的输出日志级别
monitorInterval="5" 自动加载配置文件的间隔时间，不低于5秒
-->
<configuration status="warn" monitorInterval="5">
    <!-- 日志处理 -->
    <appenders>
        <!-- 控制台输出 appender对象 -->
        <Console name="Console" target="SYSTEM_OUT">
            <!-- 日志消息格式 -->
            <PatternLayout pattern="[%d{HH:mm:ss.SSS}] [%t] [%-5level] %c{36}:%L --- %m%n"/>
        </Console>

        <!-- 日志文件输出 appender对象 -->
        <File name="file" fileName="./log4j_logs/log4j2.log">
            <!-- 日志消息格式 -->
            <PatternLayout pattern="[%d{yyyy-MM-dd HH:mm:ss.SSS}] [%-5level] %l %c{36} --- %m%n"/>
        </File>
    </appenders>

    <!-- 定义logger，只有定义了logger并引入的appender，appender才会生效 -->
    <loggers>
        <!--默认的root的logger，使用root配置日志级别-->
        <root level="trace">
            <!--指定日志处理器-->
            <appender-ref ref="Console"/>
            <appender-ref ref="file"/>
        </root>
        
        <!-- 自定义logger对象，使用异步日志 AsyncLogger 方式
        includeLocation="false" 关闭日志记录的行号信息，开启输出行号信息性能可能会比同步记录还差-->
        <AsyncLogger name="com.johann.log.log4j_asyncLogger" level = "info" includeLocation="false" additivity="false">
            <appender-ref ref="Console"/>
            <appender-ref ref="file"/>
        </AsyncLogger>
    </loggers>
</configuration>
```
> （1）如果使用异步日志，AsyncAppender 、AsyncLogger 和全局异步这三种方式不要同时出现，同时出现会造成所有日志性能跟最差的AsyncAppender 方式一样。
>  
> （2）设置includeLocation="false"，打印行号位置信息会降低异步日志的性能，性能可能会比同步日志还差。

异步日志示例
```java
public class AsyncLoggerTest {

    private static final Logger logger = LoggerFactory.getLogger(AsyncLoggerTest.class);

    public static void main(String[] args) {
        System.out.println("----------- START -----------");
        System.out.println("----------- START -----------");
        for (int i = 0;i < 2;i++){
            logger.trace("AsyncLoggerTest trace level");
            logger.debug("AsyncLoggerTest debug level");
            logger.info("AsyncLoggerTest info level");
            logger.warn("AsyncLoggerTest warn level");
            logger.error("AsyncLoggerTest error level");
        }
        System.out.println("----------- END -----------");
        System.out.println("----------- END -----------");
    }
}
```

控制台打印结果如下：
```text
----------- START -----------
----------- START -----------
----------- END -----------
----------- END -----------
[14:14:35.763] [main] [INFO ] com.johann.log.log4j_asyncLogger.AsyncLoggerTest: --- AsyncLoggerTest info level
[14:14:35.764] [main] [WARN ] com.johann.log.log4j_asyncLogger.AsyncLoggerTest: --- AsyncLoggerTest warn level
[14:14:35.764] [main] [ERROR] com.johann.log.log4j_asyncLogger.AsyncLoggerTest: --- AsyncLoggerTest error level
[14:14:35.764] [main] [INFO ] com.johann.log.log4j_asyncLogger.AsyncLoggerTest: --- AsyncLoggerTest info level
[14:14:35.764] [main] [WARN ] com.johann.log.log4j_asyncLogger.AsyncLoggerTest: --- AsyncLoggerTest warn level
[14:14:35.764] [main] [ERROR] com.johann.log.log4j_asyncLogger.AsyncLoggerTest: --- AsyncLoggerTest error level
```

### SpringBoot日志框架

SpringBoot中的 “spring-boot-starter” 默认使用了以下日志依赖 “spring-boot-starter-logging”
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-logging</artifactId>
  <version>2.5.3</version>
  <scope>compile</scope>
</dependency>
```

spring-boot-starter-logging底层依赖关系
```xml
<dependency>
  <groupId>ch.qos.logback</groupId>
  <artifactId>logback-classic</artifactId>
  <version>1.2.4</version>
  <scope>compile</scope>
</dependency>
<dependency>
  <groupId>org.apache.logging.log4j</groupId>
  <artifactId>log4j-to-slf4j</artifactId>
  <version>2.14.1</version>
  <scope>compile</scope>
</dependency>
<dependency>
  <groupId>org.slf4j</groupId>
  <artifactId>jul-to-slf4j</artifactId>
  <version>1.7.32</version>
  <scope>compile</scope>
</dependency>
```
> 以上底层依赖关系表明：
>
> SpringBoot 底层默认使用 Slf4j + Logback 方式进行日志记录。
> 其中，Slf4j作为日志门面，Logback作为底层实现。

#### 搭配lombok实现日志注解

引入lombok依赖
```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```

```java
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class Log4jTest {

    public static void main(String[] args) throws Exception{
        log.info("Log4jTest lombok info");
    }
}
```
springBoot默认使用的级别是 info级别，所以只会输出含 info 以上级别的日志。

#### SpringBoot指定日志配置文件

在resource包下，放置自定义的日志配置文件```logback-spring.xml```，```logback.xml```，SpringBoot将使用这些配置文件，而不再使用默认配置。

也可以使用全局配置文件，如```application.yml``` 配置logback日志输出，```application.yml```全局配置不能和日志实现配置文件同时存在(如：```logback.xml```)，否则会报错。

#### SpringBoot改用log4j2

将日志由logback改为log4j2，此时需要剔除默认的logback依赖，然后添加log4j2依赖即可。

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <!-- 排除springboot默认的logback依赖 -->
    <exclusions>
        <exclusion>
            <artifactId>spring-boot-starter-logging</artifactId>
            <groupId>org.springframework.boot</groupId>
        </exclusion>
    </exclusions>
</dependency>

<!-- 添加log4j2依赖，该依赖底层日志门面还是使用slf4j -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
```


### 参考
[Java日志框架的使用](https://blog.csdn.net/qq_41755845/article/details/124539461)

[Log4J2 总结](https://blog.csdn.net/yin__ren/article/details/91129041)

[Log4j 2.x 简明配置(一)](https://www.jianshu.com/p/ce06b4aab704)

[CronExpression](https://logging.apache.org/log4j/2.x/log4j-core/apidocs/org/apache/logging/log4j/core/util/CronExpression.html)