package com.johann.io.file;

import java.io.File;
import java.io.FilenameFilter;

/** 文件过滤器(根据文件名和目录名过滤)
 * @ClassName: FilenameFilterTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class FilenameFilterTest implements FilenameFilter {

    @Override
    public boolean accept(File dir, String name) {

        if (name.toLowerCase().endsWith("md")||name.toLowerCase().endsWith("txt")){
            return true;
        }
        return false;
    }
}
