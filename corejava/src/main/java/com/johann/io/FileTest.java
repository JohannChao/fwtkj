package com.johann.io;

import java.io.File;
import java.util.Arrays;
import java.util.Date;

/**
 * @ClassName: FileTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class FileTest {

    /**
     * File的构造函数
     */
    public static void testFileConstructor(){
        /**
         * File.pathSeparator
         * 分隔连续多个路径字符串的分隔符
         * 输出“;”
         */
        System.out.println(File.pathSeparator);
        /**
         * File.separator
         * 分隔同一个路径字符串中的目录
         * 输出“\”
         */
        System.out.println(File.separator);
        /**
         * File[] listRoots()
         * 获取机器盘符
         */
        Arrays.asList(File.listRoots()).forEach(action->{
            System.out.println(action);
        });

        /**
         * new File(String pathname)
         * 通过将给定的路径名字符串 转换为抽象路径名 来创建新的File
         */
        File f1 = new File("D:\\fileTest\\f1.txt");
        // 使用 Java 提供的分隔符
        File f2 = new File("D:"+File.separator+"fileTest"+File.separator+"f2.txt");
        System.out.println(f1);
        System.out.println(f2);

        /**
         * new File(File parent, String child)
         * 根据父抽象路径名 和 子路径名字符串 来创建新的File
         */
        File f3 = new File("D:\\fileTest");
        File f4 = new File(f3,"f4.txt");
        System.out.println(f3);
        System.out.println(f4);

        /**
         * new File(String parent, String child)
         * 根据父路径名字符串 和 子路径名字符串 创建新的File
         */
        File f5 = new File("D:\\fileTest","f5.txt");
        System.out.println(f5);
    }

    /**
     * File的常见方法
     */
    /**
     * ①、创建方法
     *
     * 　　　　1.boolean createNewFile() 不存在返回true 存在返回false
     * 　　　　2.boolean mkdir() 创建目录，如果上一级目录不存在，则会创建失败
     * 　　　　3.boolean mkdirs() 创建多级目录，如果上一级目录不存在也会自动创建
     *
     *
     *
     * 　　②、删除方法
     *
     * 　　　　1.boolean delete() 删除文件或目录，如果表示目录，则目录下必须为空才能删除
     * 　　　　2.boolean deleteOnExit() 文件使用完成后删除
     *
     *
     *
     * 　　③、判断方法
     *
     * 　　　　1.boolean canExecute()判断文件是否可执行
     * 　　　　2.boolean canRead()判断文件是否可读
     * 　　　　3.boolean canWrite() 判断文件是否可写
     * 　　　　4.boolean exists() 判断文件或目录是否存在
     * 　　　　5.boolean isDirectory()  判断此路径是否为一个目录
     * 　　　　6.boolean isFile()　　判断是否为一个文件
     * 　　　　7.boolean isHidden()　　判断是否为隐藏文件
     * 　　　　8.boolean isAbsolute()判断是否是绝对路径 文件不存在也能判断
     *
     *
     *
     *  　　④、获取方法
     *
     * 　　　　1.String getName() 获取此路径表示的文件或目录名称。当前文件名或最后一级目录名
     * 　　　　2.String getPath() 将此路径名转换为路径名字符串。        【相对路径】
     * 　　　　3.String getAbsolutePath() 返回此抽象路径名的绝对形式。  【绝对路径】
     * 　　　　4.String getParent()//如果没有父目录返回null。          【相对路径】
     * 　　　　5.long lastModified()//获取最后一次修改的时间
     * 　　　　6.long length() 返回由此抽象路径名表示的文件的长度。
     * 　　　　7.boolean renameTo(File f) 重命名由此抽象路径名表示的文件。
     * 　　　　8.String[] list()  返回一个字符串数组，命名由此抽象路径名表示的目录中的文件和目录。
     * 　　　　9.String[] list(FilenameFilter filter) 返回一个字符串数组，命名由此抽象路径名表示的目录中满足指定过滤器的文件和目录。
     */
    public static void testFileMethod(){

        File f1 = new File("D:"+File.separator+"fileTest"+File.separator+"f1.txt");
        /**
         * 以相对路径创建File，将当前项目视为“根目录”
         * f11.getAbsolutePath() : D:\MyWorkSpace\fwtkj\fileTest\f11.txt
         */
        File f11 = new File("fileTest\\f11.txt");
        File f12 = new File("D:\\fileTest");
        File f13 = new File(f12,"f13.txt");
        /**
         * 获取方法
         */
        System.out.println("========= 获取方法 =============");
        System.out.println("f1.getName() : "+f1.getName());
        System.out.println("f1.getPath() : "+f1.getPath());
        System.out.println("f1.getAbsolutePath() : "+f1.getAbsolutePath());

        System.out.println("\nf11.getName() : "+f11.getName());
        System.out.println("f11.getPath() : "+f11.getPath());
        System.out.println("f11.getAbsolutePath() : "+f11.getAbsolutePath());

        System.out.println("\nf12.getName() : "+f12.getName());
        System.out.println("f12.getPath() : "+f12.getPath());
        System.out.println("f12.getAbsolutePath() : "+f12.getAbsolutePath());

        System.out.println("\nf13.getName() : "+f13.getName());
        System.out.println("f13.getPath() : "+f13.getPath());
        System.out.println("f13.getAbsolutePath() : "+f13.getAbsolutePath());

        System.out.println("\nf1.getParent() : "+f1.getParent());
        System.out.println("f11.getParent() : "+f11.getParent());
        System.out.println("f12.getParent() : "+f12.getParent());
        System.out.println("f13.getParent() : "+f13.getParent());
        System.out.println("f1.lastModified() : "+new Date(f1.lastModified()));
        System.out.println("f1.length() : "+f1.length());
        String[] ss = f1.list();
        File renameFile = new File("D:"+File.separator+"fileTest"+File.separator+"renameFile"+File.separator+"f1.txt");
        System.out.println(f1.renameTo(renameFile));
        System.out.println("========= 获取方法 =============\n");

        /**
         * 判断方法
         */
        System.out.println("========= 判断方法 =============");
        System.out.println("f1.exists() : "+f1.exists());
        System.out.println("f1.isDirectory() : "+f1.isDirectory());
        System.out.println("f1.isFile() : "+f1.isFile());
        System.out.println("f1.isAbsolute() : "+f1.isAbsolute());
        System.out.println("f13.isAbsolute() : "+f13.isAbsolute());

        System.out.println("f11.isAbsolute() : "+f11.isAbsolute());
        System.out.println("f1.isHidden() : "+f1.isHidden());
        System.out.println("========= 判断方法 =============");

        System.out.println();
        System.out.println();



        File f2 = new File("D:\\fileTest");
        File f3 = new File(f2,"f3.txt");
    }

    public static void main(String[] args) {
        //testFileConstructor();
        testFileMethod();
    }

}
