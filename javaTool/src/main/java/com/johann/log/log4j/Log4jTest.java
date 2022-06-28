package com.johann.log.log4j;

//import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: Log4jTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class Log4jTest {

    //private static final Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    //private static final Logger logger = LogManager.getLogger(Log4jTest.class);
    private static final Logger logger = LoggerFactory.getLogger(Log4jTest.class);

    public static void main(String[] args) throws Exception{
        logger.trace("Log4jTest trace level");
        logger.debug("Log4jTest debug level");
        logger.info("Log4jTest info level");
        logger.warn("Log4jTest warn level");
        logger.error("Log4jTest error level");
    }
}
