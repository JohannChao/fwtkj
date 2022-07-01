package com.johann.log.log4j_asyncLogger;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: AsyncLoggerTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
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
