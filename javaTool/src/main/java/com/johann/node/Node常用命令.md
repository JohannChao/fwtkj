## Node安装模块

```bash
npm install xxx       利用 npm 安装xxx模块到当前命令行所在目录
npm install -g xxx    利用npm安装全局模块xxx
```

本地安装时将模块写入package.json中：

```bash
npm install xxx                 安装但不写入package.json
npm install xxx –-save/-S       安装并写入package.json的”dependencies”中
npm install xxx -–save-dev/-D   安装并写入package.json的”devDependencies”中
```

## Node删除模块

```bash
npm uninstall xxx      删除xxx模块
npm uninstall -g xxx   删除全局模块xxx
```

## 查看全局的包的安装路径

```bash
npm root -g
```

## 查看npm安装的版本

```bash
npm -v：
```

## 更新node模块

```bash
npm update moduleName
```

## 项目初始化

会引导你创建一个package.json文件，包括名称、版本、作者这些信息等

- 一直回车就行

```bash
npm init
```

## 查看当前包的安装路径

```bash
npm root
```

## 查看node模块的package.json文件夹

注意事项：如果想要查看package.json文件夹下某个标签的内容，可以使用$npm view moduleName labelName

```bash
npm view moduleNames
```

## 查看当前目录下已安装的node包

- 注意事项：Node模块搜索是从代码执行的当前目录开始的，搜索结果取决于当前使用的目录中的node_modules下的内容。$ npm list

```bash
npm list： parseable=true可以目录的形式来展现当前安装的所有node包
```

## 查看帮助命令

```bash
npm help
```

## 查看包的依赖关系

```bash
npm view moudleName dependencies
```

## 查看包的源文件地址

```bash
npm view moduleName repository.url
```

## 查看包所依赖的Node的版本

```bash
npm view moduleName engines
```

## 查看npm使用的所有文件夹

```bash
npm help folders
```

## 用于更改包内容后进行重建

```bash
npm rebuild moduleName
```

## 检查包是否已经过时

此命令会列出所有已经过时的包，可以及时进行包的更新

```bash
npm outdated
```

## 访问npm的json文件夹

一个npm包是包含了package.json的文件夹，package.json描述了这个文件夹的结构。访问npm的json文件夹的方法如下

```bash
npm help json 
```

此命令会以默认的方式打开一个网页，如果更改了默认打开程序则可能不会以网页的形式打开。

## 检验某个包名是否已存在

发布一个npm包的时候，需要检验某个包名是否已存在

```bash
npm search packageName
```
