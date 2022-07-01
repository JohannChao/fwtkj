package com.johann.log.log4j;

//import org.apache.logging.log4j.LogManager;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

/**
 * @ClassName: Log4jTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
@Slf4j
public class Log4jTest {

    //private static final Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    //private static final Logger logger = LogManager.getLogger(Log4jTest.class);
    private static final Logger logger = LoggerFactory.getLogger(Log4jTest.class);

    public static void main(String[] args) throws Exception{

        //日志获取本地IP
        String localIp = InetAddress.getLocalHost().getHostAddress();
        System.setProperty("hostAddress", localIp );

        logger.trace("Log4jTest trace level");
        logger.debug("Log4jTest debug level");
        logger.info("Log4jTest info level");
        logger.warn("Log4jTest warn level");
        logger.error("Log4jTest error level");

        log.debug("Log4jTest lombok debug");
    }
}
