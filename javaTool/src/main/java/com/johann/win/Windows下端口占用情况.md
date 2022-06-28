### Windows下查看端口占用情况

* 在命令窗口下输入以下命令，查找被占用的端口PID
```text
查看所有端口
netstat -ano

查看8080端口占用情况
netstat -aon|findstr "8080"
```
* 查看指定的PID进程
```text
输入该端口对应的PID，查找相应的进程信息
tasklist|findstr "41056"
```
* 强制结束该进程
```text
taskkill /T /F /PID 41056 
```