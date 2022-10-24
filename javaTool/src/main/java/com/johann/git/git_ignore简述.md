### gitignore匹配语法
```html
空格不匹配任意文件，可作为分隔符，可用反斜杠转义。开头的文件标识注释，可以使用反斜杠进行转义
! 开头的模式标识否定，该文件将会再次被包含，如果排除了该文件的父级目录，则使用 !  也不会再次被包含。可以使用反斜杠进行转义
/ 结束的模式只匹配文件夹以及在该文件夹路径下的内容，但是不匹配该文件
/ 开始的模式匹配项目跟目录
如果一个模式不包含斜杠，则它匹配相对于当前 .gitignore 文件路径的内容，如果该模式不在 .gitignore 文件中，则相对于项目根目录
** 匹配多级目录，可在开始，中间，结束
?  通用匹配单个字符
*  通用匹配零个或多个字符
[]  通用匹配单个字符列表
```

### 示例
```html
bin/: 忽略当前路径下的bin文件夹，该文件夹下的所有内容都会被忽略，不忽略 bin 文件
/bin: 忽略根目录下的bin文件
/*.c: 忽略 cat.c，不忽略 build/cat.c
debug/*.obj: 忽略 debug/io.obj，不忽略 debug/common/io.obj 和 tools/debug/io.obj
**/foo: 忽略/foo, a/foo, a/b/foo等
a/**/b: 忽略a/b, a/x/b, a/x/y/b等
!/bin/run.sh: 不忽略 bin 目录下的 run.sh 文件
*.log: 忽略所有 .log 文件
config.php: 忽略当前路径的 config.php 文件
```

### gitignore修改完未生效
如果本地文件已经被提交到远程仓库，此时更新了 gitignore 文件，如何删除远程分支上需要过滤的文件。
```html
### 1,清除本地暂存区的git追踪
### 执行完该命令，本地暂存区将为空
git rm -f --cached .

### 2,依据修改后的gitignore文件，将需要追踪的文件加入到本地暂存区
### 新加入暂存区的文件中，将不再包含gitignore中过滤掉的文件
git add .

### 3,提交到本地仓库
git commit -m "gitignore update"

### 4,提交到远程仓库
git push 
```



### 常见ignore文件模板
[gitignore模板](https://github.com/github/gitignore)