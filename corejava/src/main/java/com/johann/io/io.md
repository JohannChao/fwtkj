### 绝对路径和相对路径
所谓的绝对路径：通俗一点讲 ，他是一个完整的路径，他是以盘符（比如电脑里面的c盘，d盘）开始的路径：
绝对路径：从盘符开始的路径，这是一个完整的路径。

c:\\a.txt

C:\\Users\itcast\\IdeaProjects\\shungyuan\\123.txt

D:\\demo\\b.txt

像上面这些路径都是绝对路径。

而相对路径 ：
相对路径：相对于项目目录的路径，这是一个便捷的路径，开发中经常使用。

它是一个简化的路径
相对指的是相对于当前项目的根(C:\\Users\itcast\\IdeaProjects\\shungyuan)
如果使用当前项目的根目录，路径可以简化书写
C:\\Users\itcast\\IdeaProjects\\shungyuan\\123.txt-->简化为： 123.txt(可以省略项目的根目录)

### File类
File 类：文件和目录路径名的抽象表示。

注意：File 类只能操作文件的属性，文件的内容是不能操作的。

#### File类的字段

https://www.cnblogs.com/ysocean/p/6851878.html