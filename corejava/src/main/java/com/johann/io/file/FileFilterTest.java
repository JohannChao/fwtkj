package com.johann.io.file;

import java.io.File;
import java.io.FileFilter;

/** 文件过滤器(根据后缀过滤)
 * @ClassName: FileFilterTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class FileFilterTest implements FileFilter {

    private String suffix;
    public FileFilterTest (String suffix){
        this.suffix = suffix;
    }

    @Override
    public boolean accept(File pathname) {

        // 过滤文件类型
        if (pathname.isDirectory()){
            return false;
        }
        String name = pathname.getName();
        int index = name.lastIndexOf(".");
        if (index == -1){
            return false;
        }
        if (name.length()-1 == index){
            return false;
        }
        return suffix.equals(name.substring(index+1));
    }
}
