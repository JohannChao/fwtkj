##### ssh-keygen命令常见可选项
`ssh-keygen` 命令是一个用于生成、管理和转换 SSH 密钥的工具，它有很多可选选项，下面列举一些常用的选项及其含义：
- `-t`: 指定生成的密钥类型，常用的有 RSA、DSA、ECDSA 和 Ed25519 等。示例：`ssh-keygen -t rsa`。
- `-b`: 指定生成的密钥长度，单位为比特。示例：`ssh-keygen -b 4096`。
- `-C`: 添加注释，一般用于标识该密钥的用途或所有者等信息。示例：`ssh-keygen -C "my_key"`。
- `-f`: 指定生成的密钥文件名和路径。示例：`ssh-keygen -f ~/.ssh/my_key`。
- `-N`: 指定密钥的密码短语，用于保护私钥。示例：`ssh-keygen -N "my_passphrase"`。
- `-p`: 修改私钥的密码短语或密钥类型等信息。示例：`ssh-keygen -p -f ~/.ssh/my_key`。
- `-i`: 将其它格式的密钥转换成 SSH 格式的密钥。示例：`ssh-keygen -i -f id_rsa.pub`。
- `-y`: 显示指定公钥文件对应的私钥。示例：`ssh-keygen -y -f ~/.ssh/id_rsa.pub`。

除了上述常用选项，`ssh-keygen` 还有很多其它选项，可以通过 `man ssh-keygen` 命令查看完整的选项列表和说明。
Windows系统下，可以使用 `ssh-keygen --help` 命令来查看 `ssh-keygen` 命令的帮助信息。

##### 生成默认的github ssh key
```bash
ssh-keygen -t rsa -C "your_email1@example.com"
```
此时，在.ssh文件夹下会生成一个名为"id_rsa"的公钥和私钥。
将这个公钥添加到第一个github账户上。

##### 生成第二个github ssh key
```bash
ssh-keygen -t rsa -b 4096 -C "your_email2@example.com" -f ~/.ssh/id_rsa_two
```
此时，在.ssh文件夹下会生成另外的名为"id_rsa_two"的公钥和私钥。
将这个公钥添加到第二个github账户上。

##### 配置config文件
在.ssh文件夹下，新建一个config文件，内容如下：
```config
# GitHub account 1
Host github1(这个名字随便起，后面克隆项目时需要替换这个名字)
    HostName github.com
    User git
    IdentityFile ~/.ssh/id_rsa

# GitHub account 2
Host github2(这个名字随便起，后面克隆项目时需要替换这个名字)
    HostName github.com
    User git
    IdentityFile ~/.ssh/id_rsa_two
```

##### 测试配置
```bash
ssh -T git@github1

ssh -T git@github2

返回结果: You've successfully authenticated, but GitHub does not provide shell access.
这个结果表明你已经成功通过 SSH 认证，但是 GitHub 不提供 shell 访问。
```

##### 克隆新项目
```bash
# 克隆第一个github账户的项目
git clone git@github1:username/repo.git

# 克隆第二个github账户的项目
git clone git@github2:username/repo.git
```

##### 设置本地提交代码时，以哪个账户的身份提交
```bash
# CD到项目根目录下，打开git bash窗口，执行以下命令。
# 按照要求替换为自己的用户名和邮箱
git config user.name "github1"
git config user.email "your_email1@example.com"
```
