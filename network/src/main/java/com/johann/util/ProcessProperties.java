package com.johann.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @ClassName: ProcessProperties
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class ProcessProperties {

    public static String getProperties(String key){
        String value = null;
        try {
            InputStream in = ProcessProperties.class.getClassLoader().getResourceAsStream("source.properties");
            Properties properties = new Properties();
            properties.load(in);

            value = properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }
        return value;
    }

    public static String getProperties(String propName,String key){
        String value = null;
        try {
            InputStream in = ProcessProperties.class.getClassLoader().getResourceAsStream(propName);
            Properties properties = new Properties();
            properties.load(in);

            value = properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }
        return value;
    }


}
