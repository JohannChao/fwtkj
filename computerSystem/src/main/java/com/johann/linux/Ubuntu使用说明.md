# TODO
问题汇总

##### 查看安装的Linux系统信息
```shell
# `-a` 或 `--all`：显示所有信息，包括内核名称、主机名、操作系统版本、处理器类型和硬件架构等。
# `-m` 或 `--machine`：显示处理器类型。
# `-n` 或 `--nodename`：显示主机名。
# `-r` 或 `--release`：显示内核版本号。
# `-s` 或 `--sysname`：显示操作系统名称。
# `-v`：显示操作系统的版本。
# `--help`：显示帮助信息。
# `--version`：显示`uname`命令的版本信息。
# `-p`：显示处理器类型（与`-m`选项相同）。

# 显示系统的完整信息
uname -a

# 显示处理器类型
uname -m
```

##### 查询当前的IP地址
```shell
ip addr show
```

##### 使用APT安装、更新、删除软件
APT，即Advanced Packaging Tool，是一种用于管理Debian及其衍生系统（如Ubuntu和Linux Mint）的软件包的集合。 APT通过使用仓库（或称为存储库,这些仓库是包含软件包集合的特殊目录）来工作。当你使用APT（Advanced Package Tool）来安装或更新软件包时，它会处理依赖关系并自动下载所需的软件包。

APT可以被视为dpkg的前端，而dpkg执行的是对单个软件包的操作，而APT管理它们之间的关系，以及高级版本控制决策的来源和管理。

APT安装的软件包位于`/var/cache/apt/archives/`目录下，用于存储已下载的软件包，而实际的程序文件则安装在`/usr/bin/, /usr/sbin/, /bin/, /sbin/, /lib/, /lib/x86_64-linux-gnu/, /usr/lib/, /usr/local/bin/`等目录下。
```shell
# 更新软件源列表，使得后续安装或升级软件包时能够获取到最新的软件包信息。
sudo apt update

# 搜索符合特定模式的软件包
sudo apt search <package-name>

# 安装软件包  
sudo apt install <package-name>

# 显示指定软件包的详细信息
sudo apt show <package-name>

# 查看apt缓存的信息，如已下载但未安装的软件包
sudo apt-cache policy

# 清理apt缓存中的已下载但未安装的软件包
sudo apt-get autoclean
# 完全清空apt缓存
sudo apt-get clean

# 列出可用的、已安装的以及可升级的软件包
sudo apt list

# 列出可更新的软件包
sudo apt list --upgradable

# 更新可更新的全部软件包
sudo apt upgrade

# 更新特定的软件包 
sudo apt --only-upgrade install <package-name>

# 查看已安装的软件包
sudo apt list --installed
sudo apt list --installed | less
sudo apt list --installed | grep <package-name>

# 卸载软件包
# apt remove 命令只是卸载软件包而保留其配置文件，以便以后重新安装时可以恢复原有的设置
sudo apt remove <package-name>

# 自动移除不再需要的软件包
sudo apt autoremove

# 完全卸载指定的软件包，并且还会删除与该软件包相关的配置文件
# apt purge会做以下几件事： 1，完全卸载软件包。2，删除软件包的配置文件。3，不会删除由该软件包引入的依赖关系。
sudo apt purge <package-name>
```

##### apt和apt-get的区别
在Ubuntu系统中，`apt`和`apt-get`都是用于管理软件包的命令行工具，它们允许你安装、更新、删除软件包等。虽然它们在功能上非常相似，但存在一些关键区别：

- **用户界面和易用性**：`apt`提供了一个更高级别的用户界面，设计目标是为最终用户提供更直观的命令、行为和安全功能。相比之下，`apt-get`是一个低级别接口，更紧密地与核心Linux进程通信，这使得`apt`成为一个更易于使用的软件包管理器[1]。

- **发布时间**：`apt-get`首次发布于1998年，随着Debian 2.0（Hamm）发行版；而`apt`则在2014年，随着Debian 8（Jessie）发行版推出。从那时起，`apt`逐渐取代`apt-get`成为所有基于Debian的Linux发行版的默认软件包管理器工具[1]。

- **搜索功能**：`apt`引入了一种新的搜索功能，允许用户通过`apt search <package_name>`命令按名称搜索软件包。而`apt-get`没有这个功能，需要使用`apt-cache`命令来实现同样的功能。

- **依赖关系解析**：`apt`在处理依赖关系解析方面表现出了优势。它能够更有效地识别并解决复杂的依赖关系链，推荐安装必要的软件包。此外，`apt`的性能优于`apt-get`，特别是在处理复杂依赖关系时。

- **文件系统上的包版本**：当使用`apt upgrade`命令升级软件包时，`apt`会自动清理不再需要的旧版本软件包，从而释放系统资源。而`apt-get upgrade`命令则不会自动清理旧版本，这可能导致磁盘空间浪费。

- **输出信息**：`apt`和`apt-get`都会将状态信息打印到终端，但`apt`提供了更多的细节，包括每个任务的进度条，这对于理解操作过程非常有帮助。

总结来说，`apt`作为`apt-get`的后继者，提供了更友好的用户界面、更强大的搜索功能、更优秀的依赖关系解析能力以及更有效的资源管理策略。尽管`apt-get`仍然广泛使用，但`apt`已经成为现代基于Debian的Linux发行版的标准软件包管理工具。

##### 使用.deb软件包安装
在Ubuntu系统中，dpkg是一个用于处理.deb格式软件包的工具。它允许您安装、更新或删除软件包。

DPKG是Debian基础系统中的包管理器，它直接处理二进制包文件。使用DPKG安装的软件包位于`/var/cache/apt/archives/`目录下，类似于APT，但它不处理依赖关系。如果一个软件包需要其他软件包作为依赖，那么开发者必须手动解决这个问题。在`/var/lib/dpkg/status`文件中可以找到关于软件包状态的信息。

虽然dpkg提供了一些基本的软件包管理功能，但对于更复杂的依赖关系和更新操作，使用apt-get通常是更方便和有效的方法
```shell
# 安装.deb软件包
sudo dpkg -i <package-name>.deb

# 如果遇到依赖问题，可以使用apt来解决
sudo apt --fix-broken install

# 查看已安装的软件包
dpkg --get-selections | grep -v deinstall

# 查看系统中已安装的软件包列表
dpkg -l

# 查看关于特定软件包的信息
dpkg -I <package-name>.deb

# 删除软件包
sudo dpkg -r <package-name>
# 连同配置文件一起删除
sudo dpkg -P <package-name>
```

##### 使用Snap软件包安装
在Ubuntu系统中，Snap是一种用于打包和分发应用程序的格式，它允许开发者将应用程序及其所有依赖项一起封装，从而简化了跨不同Linux发行版的部署过程。Snap包提供了一种沙盒环境，使得应用程序可以安全地运行，同时减少了与系统其他部分的冲突。

Snap是另一种包管理工具，它允许用户以沙箱模式运行应用程序。使用Snap安装的软件包位于`/snap/`目录下。每个Snap应用都有自己的子目录，例如`/snap/core/、/snap/gnome-3-28-1804/`等。这些目录包含了应用程序及其所有依赖项。
```shell
# Snap是Ubuntu 16.04及以上版本支持的一种软件分发方式，提供了跨发行版的兼容性。

# 查找包含指定关键词的所有可用Snap包
snap find <name>

# 安装Snap软件包
sudo snap install <snap-package-name>

# 查看当前系统上已安装的所有Snap包
snap list

# 手动检查并更新所有已安装的Snap包
sudo snap refresh

# 卸载Snap软件包
sudo snap remove <snap-package-name>
```
