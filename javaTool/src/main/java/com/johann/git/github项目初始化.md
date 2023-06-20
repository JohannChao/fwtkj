1. 在 GitHub 上创建一个新的仓库（如果还没有）。

2. 打开终端或 Git Bash，导航到本地项目的根目录。

3. 初始化 Git 仓库。在终端中输入以下命令：`git init`

4. 将项目的文件添加到 Git 仓库。在终端中输入以下命令：`git add .` 

5. 提交更改。在终端中输入以下命令：`git commit -m "Initial commit"` 

6. 将本地仓库与 GitHub 仓库关联。在终端中输入以下命令：`git remote add origin <GitHub 仓库的 URL>` （注意，这里的 `<GitHub 仓库的 URL>` 是你在步骤1中创建的 GitHub 仓库的 URL）

7. 将本地更改推送到 GitHub。在终端中输入以下命令：`git push -u origin master` （注意，这里的 `master` 是本地仓库的主分支，如果你使用的是其他分支，请将其替换为相应的分支名称）

> git push -u origin master
> 这条指令的意思是将本地的代码推送到名为"origin"的远程仓库的"master"分支，并将本地的"master"分支与远程的"master"分支关联起来。
> 其中，"-u"参数表示将本地的"master"分支与远程的"master"分支关联起来，这样在以后的推送中，就可以直接使用"git push"命令，而不需要再指定远程仓库和分支名了。