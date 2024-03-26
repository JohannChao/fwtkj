### git 提交记录
Git 的提交记录保存的是目录下所有文件的快照，条件允许的情况下，它会将当前版本与仓库中的上一个版本进行对比，并把所有的差异打包到一起作为一个提交记录。

Git 还保存了提交的历史记录。即大多数提交记录上面的 parent 节点，parent 节点是当前提交中变更的基础。

### git 分支
Git 的分支非常轻量，它们只是简单地指向某个提交纪录（Git 的分支是基于提交记录的快照，而提交记录是基于文件快照的）

现在只要记住使用分支其实就相当于在说：“我想基于这个提交以及它所有的 parent 提交进行新的工作。”

```git
# 创建分支
git branch <branchname>
# 切换分支
git checkout <branchname>
git switch <branchname> 【Git 2.23 版本中引入】

# 创建并切换分支
git checkout -b <branchname>
```

### git 分支合并
##### 第一种合并方法: git merge
在 Git 中合并两个分支时会产生一个特殊的提交记录，它有两个 parent 节点。 翻译成自然语言相当于：“我要把这两个 parent 节点本身及它们所有的祖先都包含进来。”
```git
# 合并分支（将指定的分支合并到当前分支）
git merge <branch_tobe_merged>

# 当前有 分支A、分支B，两个分支都有单独的提交，将 分支B 合并到 分支A
git checkout branchA
git merge branchB
# 此时 分支A 指向一个新的特殊的提交记录，这个提交记录拥有两个parent节点，即分支A和分支B的提交记录

# 如果 分支B 已经合并到 分支A，那么可以删除 分支B
git branch -d branchB

# 也可以将 分支A 的提交记录合并到 分支B
git checkout branchB
git merge branchA
# 因为 branchA 继承自 branchB，Git 什么都不用做，只是简单地把 branchB 移动到 branchA 所指向的那个提交记录。
```
##### 第二种合并方法: git rebase
Rebase 实际上就是取出一系列的提交记录，复制它们，然后在另外一个地方逐个的放下去。

Rebase 的优势就是可以创造更线性的提交历史，这听上去有些难以理解。如果只使用 Rebase 的话，代码库的提交历史将会变得异常清晰。
```git
# branchA，branchB 从 C1 记录开始分离，branchA 经过三次提交 C1--C1a--C2a--C3a，branchB 经过三次提交 C1--C1b--C2b--C3b
# 将 branchB 的提交记录复制到 branchA
git checkout branchB
git rebase branchA
# 此时 branchB 的提交记录包含了 branchA 的提交记录: C1a--C2a--C3a--C1b--C2b--C3b

# 此时可以删除 branchA
git branch -d branchA

# 也可以将 branchA `rebase` 到 branchB
git checkout branchA
git rebase branchB
# 由于 branchB 继承自 branchA，Git 什么都不用做，只是简单地把 branchA 的引用向前移动一下：C1a--C2a--C3a--C1b--C2b--C3b

```