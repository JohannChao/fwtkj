[每天一个linux命令](https://www.cnblogs.com/peida/archive/2012/12/05/2803591.html)

##### ls

格式：`ls [选项][目录名]`

ls 命令就是 list 的缩写。 ls 用来打印出当前目录的清单。

常见参数：

* -a；–all 列出目录下的所有文件，包括以 . 开头的隐含文件
* -A；同-a，但不列出“.”(表示当前目录)和“..”(表示当前目录的父目录)。

* -l；除了文件名之外，还将文件的权限、所有者、文件大小等信息详细列出来

* -h；–human-readable 以容易理解的格式列出文件大小（例如 1K 234M 2G)

* -t；以文件修改时间排序

示例：

```shell
# 列出/home文件夹下的所有文件和目录的详细资料。d开头的表示目录（directory）， -开头的表示文件
$ ls -a -l /home
$ ls -al /home

# 列出当前目录中所有以”s”开头的文件目录的详细内容
$ ls -l s*

# 以容易理解的格式列出/home目录中所有以”s”开头的文件目录的大小
$ ls -lh /home/s*
```

##### cd

格式：`cd [目录名]`

cd 命令是 change directory 的缩写，切换当前目录至指定的目录。

示例：

```shell
# 进入系统根目录。 
$ cd /

# 进入当前用户主目录
$ cd ~

# 进入父目录
$ cd ..

# 进入上一次所在目录
$ cd -
```

##### pwd

格式：`pwd [选项]`

pwd 命令是 Print Working Directory 的缩写。用于查看“当前工作目录”的完整路径。

参数：

* -P；显示实际物理路径，而非使用连接（link）路径
* -L；当目录为连接路径时，显示连接路径

示例：

```shell
# 显示当前目录所在路径
$ pwd

# 显示当前目录的物理路径
$ pwd -P

# 显示当前目录的连接路径
$ pwd -L
```

##### mkdir

mkdir 命令是 make directory 的缩写。用来创建指定名称的目录，要求创建目录的用户在当前目录中具有写权限，并且指定的目录名不能是当前目录中已有的目录。

格式：`mkdir [选项]目录`

参数：

* -m；--mode=模式，设定权限<模式> (类似 chmod)
* -p；--parent，可以是一个路径名称。此时若路径中的某些目录尚不存在,加上此选项后,系统将自动建立好那些尚不存在的目录,即一次可以建立多个目录
* -v；--verbose，每次创建新目录都显示信息

示例：

```shell
# 创建空目录
$ mkdir test

# 递归创建多个目录
$ mkdir -p zhao/test

# 创建权限为777的目录
$ mkdir -m 777 zhao/test2
$ ll #test2的权限为 drwxrwxrwx，而不是 drwxrwxr-x

# 创建目录时显示信息
$ mkdir -p -v zhao2/test
#mkdir: 已创建目录 'zhao2'
#mkdir: 已创建目录 'zhao2/test'

# 创建目录结构
$ mkdir -vp johann/{lib/,bin/,doc/{info,product},service/deploy/{info,product}}
$ tree johann/                                                                                                       
johann
├── bin
├── doc
│   ├── info
│   └── product
├── lib
└── service
    └── deploy
        ├── info
        └── product
```

##### rm

rm 命令是 remove 的缩写。该命令的功能为删除一个目录中的一个或多个文件或目录，它也可以将某个目录及其下的所有文件及子目录均删除。对于链接文件，只会删除链接，原文件均保持不变。

格式：`rm [选项] 文件或目录`

参数：

* -f --force；忽略不存在的文件，从不给提示
* -i --interactive；进行交互式删除
* -r --recursive；指示rm将参数中列出的全部目录和子目录均递归地删除
* -v --verbose；详细显示进行的步骤

示例：

```shell
# 删除文件，默认情况下系统会先询问是否删除
$ sudo touch johann.log 
$ rm johann.log

# 强行删除
$ rm -f johann.log

# 删除任何 .log 文件
$ rm -i *.log

# 递归删除目录及其下面的子目录
$ rm -r test1
```

##### mv

mv 命令是 move 的缩写。用来移动文件或更改文件名。mv 命令根据第二个参数类型（目标是一个文件还是目录），决定执行将文件重命名或将其移至一个新的目录中。当第二个参数类型是文件时，mv 命令完成文件重命名，此时，源文件只能有一个（也可以是源目录名），它将所给的源文件或目录重命名为给定的目标文件名。当第二个参数是已存在的目录名称时，源文件或目录参数可以有多个，mv 命令将各参数指定的源文件均移至目标目录中。