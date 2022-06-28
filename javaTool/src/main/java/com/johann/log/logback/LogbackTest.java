package com.johann.log.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: LogbackTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class LogbackTest {
    private final static Logger logger = LoggerFactory.getLogger(LogbackTest.class);

    public static void main(String[] args) {
        logger.trace("LogbackTest trace");
        logger.debug("LogbackTest debug");
        logger.info("LogbackTest info");
        logger.warn("LogbackTest warn");
        logger.error("LogbackTest error");
    }

}
