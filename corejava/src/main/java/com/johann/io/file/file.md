### 绝对路径和相对路径
* 绝对路径：从盘符开始的路径，它是一个完整的路径。
```text
D:\\ioTest\\fileTest\\newFile1.txt
D:\\ioTest\\fileTest
```

* 相对路径：它是一个简化的路径，相对指的是相对于当前项目的根（项目根```D:\\myWorkSpace\\fwtkj```）
相对于项目目录的路径，这是一个便捷的路径，开发中经常使用。
```text
/** 
*  在项目中使用相对路径创建一个File，当获取它的绝对路径时，会在前面加上项目的根路径
*/
File f11 = new File("ioTest\\f11.txt");
获取绝对路径时（f11.getAbsolutePath()），它的值是 D:\myWorkSpace\fwtkj\ioTest\f11.txt
```

### File类
File 类：文件和目录路径名的抽象表示。

注意：File 类只能操作文件的属性，文件的内容是不能操作的。

#### File类的字段
* File.pathSeparator  分隔连续多个路径字符串的分隔符。 输出“;”
* File.separator  分隔同一个路径字符串中的目录。输出“\”
* File[] listRoots()  获取机器盘符。输出“C:\ D:\ E:\”
```java
public class FileTest {
    
     public static void testFile(){
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
         * 输出“C:\ D:\ E:\”
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
}
```

#### File 类的构造方法
* new File(String pathname) 通过将给定的路径名字符串 转换为抽象路径名 来创建新的File
* new File(File parent, String child)  根据父抽象路径名 和 子路径名字符串 来创建新的File
* new File(String parent, String child)  根据父路径名字符串 和 子路径名字符串 创建新的File
```java
public class FileTest {
    
     public static void testFileConstructor(){

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
}
```

#### File 类的常用方法
##### 1，创建方法
* 1，boolean createNewFile()　:　不存在返回true 存在返回false
* 2，boolean mkdir()　:　创建目录，如果上一级目录不存在，则会创建失败。例如：D:\ioTest\fileTest这个目录，必须先创建 D:\ioTest，才能创建 D:\ioTest\fileTest
* 3，boolean mkdirs()　:　创建多级目录，如果上一级目录不存在也会自动创建

##### 2，删除方法
* 1，boolean delete()　:　删除文件或目录，如果表示目录，则目录下必须为空才能删除
* 2，boolean deleteOnExit()　:　在虚拟机终止时，请求删除由这个抽象路径名表示的文件或目录（无论目录下是否为空）。
在这个方法之后调用新建目录或文件的方法，这些新建方法执行成功，但最终目录或文件还是会被删除。
【在虚拟机终止时】，即无论此方法的位置在哪，都是最后才会执行。

##### 3，判断方法
* 1，boolean canExecute()　:　判断文件是否可执行
* 2，boolean canRead()　:　判断文件是否可读
* 3，boolean canWrite()　:　判断文件是否可写
* 4，boolean exists()　:　判断文件或目录是否存在
* 5，boolean isDirectory()　:　判断此路径是否为一个目录
* 6，boolean isFile()　:　判断是否为一个文件
* 7，boolean isHidden()　:　判断是否为隐藏文件
* 8，boolean isAbsolute()　:　判断是否是绝对路径 文件不存在也能判断

##### 4，获取方法
* 1，String getName()　:　获取此路径表示的文件或目录名称。当前文件名或最后一级目录名
* 2，String getPath()　:　将此路径名转换为路径名字符串。如果File是根据相对路径创建的，则返回相对路径               【相对路径】
* 3，String getAbsolutePath()　:　返回此抽象路径名的绝对形式。                                                【绝对路径】
* 4，String getParent()　:　如果没有父目录返回null。如果File是根据相对路径创建的，则返回父目录的相对路径          【相对路径】
* 5，long lastModified()　:　获取最后一次修改的时间
* 6，long length()　:　返回此抽象路径名表示的文件长度。如果此路径名表示目录，则返回值未指定。
* 7，boolean renameTo(File f)　:　重命名此抽象路径名表示的文件。
* 8，String[] list()　:　返回一个字符串数组，获取此抽象路径名表示目录下的所有的文件或子目录
* 9，String[] list(FilenameFilter filter)　:　返回一个字符串数组，获取此抽象路径名表示目录下的所有的文件或子目录，且这些文件或子目录满足指定文件过滤器规则。
```java
public class FileTest {
    
    public static void testFileMethod() throws IOException{
    
        File f1 = new File("D:"+File.separator+"ioTest"+File.separator+"f1.txt");
        /**
         * 以相对路径创建File，将当前项目视为“根目录”
         * f11.getAbsolutePath() : D:\MyWorkSpace\fwtkj\ioTest\f11.txt
         */
        File f11 = new File("ioTest\\f11.txt");
        File dir = new File("D:\\ioTest");
        File f13 = new File(dir,"f13.txt");


        /**
         * 获取方法
         */
//        System.out.println("========= 获取方法 =============");
//        System.out.println("f1.getName() : "+f1.getName());
//        System.out.println("f1.getPath() : "+f1.getPath());
//        System.out.println("f1.getAbsolutePath() : "+f1.getAbsolutePath());
//
//        System.out.println("\nf11.getName() : "+f11.getName());
//        System.out.println("f11.getPath() : "+f11.getPath());
//        System.out.println("f11.getAbsolutePath() : "+f11.getAbsolutePath());
//
//        System.out.println("\ndir.getName() : "+dir.getName());
//        System.out.println("dir.getPath() : "+dir.getPath());
//        System.out.println("dir.getAbsolutePath() : "+dir.getAbsolutePath());
//
//        System.out.println("\nf13.getName() : "+f13.getName());
//        System.out.println("f13.getPath() : "+f13.getPath());
//        System.out.println("f13.getAbsolutePath() : "+f13.getAbsolutePath());
//
//        System.out.println("\nf1.getParent() : "+f1.getParent());
//        System.out.println("f11.getParent() : "+f11.getParent());
//        System.out.println("dir.getParent() : "+dir.getParent());
//        System.out.println("f13.getParent() : "+f13.getParent());
//        System.out.println("f1.lastModified() : "+new Date(f1.lastModified()));

        /**
         * 返回此抽象路径名表示的文件长度。如果此路径名表示目录，则返回值未指定。
         *
         * 如果需要将I/O异常与{@code 0L}返回的情况区分开来，或者同时需要同一文件的多个属性，
         * 则可以使用{@link java.nio.file.Files#readAttributes（Path，Class，LinkOption[]）Files.readAttributes}方法。
         *
         * @return 此抽象路径名表示的文件长度（以字节为单位），如果文件不存在，则表示为0L。
         * 对于表示系统相关实体（如设备或管道）的路径名，某些操作系统可能会返回0L。
         */
        //File oldFile = new File("D:\\ioTest\\fileTest\\newFile1.txt");
        //System.out.println("oldFile.length() : "+oldFile.length());
        /**
         * 重命名此抽象路径名表示的文件。
         *
         * 此方法行为的许多方面本质上依赖于平台：重命名操作可能无法将文件从一个文件系统移动到另一个文件系统，
         * 它可能不是原子的，如果目标抽象路径名已经存在，则可能无法成功。应始终检查返回值，以确保重命名操作成功。
         *
         * 请注意，{@link java.nio.file.Files}类定义了{@link java.nio.file.Files#move move} 方法，以独立于平台的方式移动或重命名文件。
         *
         * @param dest 指定文件的新抽象路径名
         *
         * @return 当且仅当重命名成功时，返回true;否则返回false
         */
        File oldFile = new File("D:\\ioTest\\fileTest\\newFile1.txt");
        File renameFile = new File("D:\\ioTest\\fileTest\\renameFile.txt");
        System.out.println("oldFile.renameTo(renameFile) : "+oldFile.renameTo(renameFile));

        /**
         * 获取当前目录下的所有的文件或子目录
         */
        File list_dir = new File("D:\\");
        System.out.println("\n------------------------");
        Arrays.asList(list_dir.list()).forEach(action->
                System.out.println(action)
        );
        System.out.println("\n------------------------");
        Arrays.asList(list_dir.listFiles()).forEach(action->
                System.out.println(action.getName())
        );

        /**
         * 根据文件过滤器，获取目录下的文件或子目录
         */
        FilenameFilterTest filenameFilter = new FilenameFilterTest();
        System.out.println("\n------------------------");
        Arrays.asList(list_dir.list(filenameFilter)).forEach(action->
                System.out.println(action)
        );
        FileFilterTest fileFilter = new FileFilterTest("md");
        System.out.println("\n------------------------");
        Arrays.asList(list_dir.listFiles(fileFilter)).forEach(action->
                System.out.println(action.getName())
        );


//        System.out.println("========= 获取方法 =============\n");


        /**
         * 判断方法
         */
//        System.out.println("========= 判断方法 =============");
//        System.out.println("f1.exists() : "+f1.exists());
//        System.out.println("f1.isDirectory() : "+f1.isDirectory());
//        System.out.println("f1.isFile() : "+f1.isFile());
//        System.out.println("f1.isAbsolute() : "+f1.isAbsolute());
//        System.out.println("f1.isHidden() : "+f1.isHidden());
//
//        System.out.println("\nf11.isFile() : "+f11.isFile());
//        System.out.println("f11.isAbsolute() : "+f11.isAbsolute());
//        System.out.println("f13.isAbsolute() : "+f13.isAbsolute());
//        System.out.println("dir.exists() : "+dir.exists());
//        System.out.println("dir.isDirectory() : "+dir.isDirectory());
//        System.out.println("f13.isFile() : "+f13.isFile());
//        System.out.println("========= 判断方法 =============");


        /**
         * 创建方法
         */
//        System.out.println("\n========= 创建方法 =============");
//        File mkd = new File("D:\\ioTest\\fileTest");
//        File newFile = new File(mkd,"newFile1.txt");
//        File invalidFile = new File("D:\\ioTest\\fileTest2\\invalidFile.txt");
//
//        System.out.println("mkd.exists() : "+mkd.exists());
//        System.out.println("mkd.isDirectory() : "+mkd.isDirectory());
//        if(!mkd.exists() && !mkd.isDirectory()){
//            /**
//             * dir.mkdir()  如果上一级目录不存在，则新建目录失败，返回false；如果当前目录已经存在，则新建失败，返回false
//             * 例如：D:\ioTest\fileTest这个目录，必须先创建 D:\ioTest，才能创建 D:\ioTest\fileTest
//             *
//             * dir.mkdirs() 创建多级目录，即使上级目录不存在，也会自动新建所需的所有目录。如果当前目录已经存在，则新建失败，返回false
//             */
//            System.out.println("mkd.mkdir() : "+mkd.mkdir());
//            System.out.println("mkd.mkdirs() : "+mkd.mkdirs());
//        }
//
//        System.out.println("mkd.mkdir() : "+mkd.mkdir());
//        System.out.println("mkd.mkdirs() : "+mkd.mkdirs());
//
//        try {
//            /**
//             * file.createNewFile()  如果当前file不存在，则新建file后返回true；
//             *                       如果当前file已存在，则返回false；
//             *                       如果当前目录不存在，则报异常：java.io.IOException: 系统找不到指定的路径
//             */
//            System.out.println("newFile.createNewFile() : "+newFile.createNewFile());
//            System.out.println("invalidFile.createNewFile() ： "+invalidFile.createNewFile());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("========= 创建方法 =============");


        /**
         * 删除方法
         */
        System.out.println("\n========= 删除方法 =============");
        File delete_dir = new File("D:\\ioTest\\fileTest");
        File delete_file = new File("D:\\ioTest\\fileTest\\newFile1.txt");

        /**
         * 删除由这个抽象路径名表示的文件或目录。如果此路径名表示一个目录，则该目录必须为空，以便删除。
         *
         * 注意，{@link java.nio.file}类定义了{@link java.nio.file.Files#delete(Path) delete} 方法，当文件无法删除时抛出{@link IOException}。
         * 这对于错误报告和诊断无法删除文件的原因非常有用。
         */
//        System.out.println("delete_dir.delete() : "+delete_dir.delete());
//        System.out.println("delete_dir.mkdir() : "+delete_dir.mkdir());

//        System.out.println("delete_dir.mkdir() : "+delete_dir.mkdir());
//        System.out.println("delete_file.delete() : "+delete_file.delete());
//        System.out.println("delete_file.createNewFile() : "+delete_file.createNewFile());

        /**
         * 在虚拟机终止时，请求删除由这个抽象路径名表示的文件或目录。文件(或目录)的删除顺序与它们注册的顺序相反。
         * 调用此方法来删除“已注册要删除的文件或目录”是无效的。根据Java语言规范的定义，只有在正常终止虚拟机时才会尝试删除。
         *
         * 一旦请求删除，就不可能取消请求。因此，这种方法应该谨慎使用。
         *
         * 注意:这个方法应该而不是用于文件锁定，因为产生的协议不能可靠地工作。
         * 应该使用{@link java.nio.channels.FileLock FileLock}工具。
         */
//        delete_dir.deleteOnExit();
//        System.out.println("delete_dir.mkdir() : "+delete_dir.mkdir());

//        System.out.println("delete_dir.mkdir() : "+delete_dir.mkdir());
//        delete_file.deleteOnExit();
//        System.out.println("delete_file.createNewFile() : "+delete_file.createNewFile());

        System.out.println("========= 删除方法 =============");

    }
}
```

### 参考
[Java IO详解（一)------File 类](https://www.cnblogs.com/ysocean/p/6851878.html)