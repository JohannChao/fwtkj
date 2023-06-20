# Eclipse

### Eclipse中的“package explorer”，“navigator”，“project explorer”导航器的区别
Eclipse IDE 目前有 3 个主要的导航器
1. Project Explorer: Project Explorer使用了更接近Eclipse项目的视图。
例如，它知道并列出外部依赖项，它调用源目录并在包名称中显示Java源文件。
2. Package Explorer: 由 Java 开发工具 (JDT) UI 项目提供，它提供了工作区的 Java 类视图。
一般而言，用于 Java 对象的 Package Explorer 和 Project Explorer 的表示基本相似。JDT 不用于 Java 以外的项目。
3. Navigator: 此视图是 org.eclipse.ui.ide 插件中提供的现已弃用（自 3.5 起）的 org.eclipse.ui.views.navigator.ResourceNavigator 类的实现。
Navigator提供了一个纯粹的项目目录层次结构视图，不支持显示其他内容的扩展性。

### Eclipse中的包名路径
```text
Java项目<JohannTest>中, Hello.java的文件夹路径为<JohannTest/src/johann/zhao/org/common/core/orm>
在Eclipse中,Hello.java的包名被设置为<org.common.core.orm>
原因是,在该项目中,Source Folder被设置为</src/johann/zhao>(见.classpath文件).
如果将Source Folder设置为</src/johann>,那么Hello.java的包名就应该设置为<zhao.org.common.core.orm>
```

### Java web项目在Eclipse中的项目结构
```text
ProjectName/
├── build/
│   ├── classes/
│   └── libs/
├── src/
│   ├── main/
│   │   ├── java/
│   │   ├── resources/
│   │   └── webapp/
│   └── test/
│       ├── java/
│       └── resources/
├── WebContent/
│   ├── META-INF/
│   ├── WEB-INF/
│   └── other resources and files
├── .classpath
├── .project
├── build.xml
└── other project files

`ProjectName` 是您的项目名称，
`src` 目录包含您的源代码，
`WebContent` 目录包含您的Web资源，
`build` 目录包含您的编译输出，
`.classpath` 文件包含您的项目类路径，
`.project` 文件包含您的项目设置，
`build.xml` 文件是一个Ant构建脚本，用于自动化构建和部署您的项目。
```

### Eclipse中的java web项目下，"build.xml", ".project", ".classpath" 文件作用
```text
1. build.xml文件：是Ant构建工具的构建脚本文件，用于自动化构建Java web项目。
在build.xml文件中，可以定义项目的编译、打包、部署等操作，以及依赖库的管理等。通过执行build.xml文件，可以自动化地完成项目的构建过程。

2. .project文件：是Eclipse项目文件，用于描述Eclipse项目的基本信息。
在.project文件中，可以定义项目的名称、类型、构建方式、依赖库等。通过.project文件，Eclipse可以正确地识别Java web项目，并提供相应的开发工具和功能。

3. .classpath文件：是Eclipse项目的类路径文件，用于描述项目的依赖库和类路径。
在.classpath文件中，可以定义项目的依赖库、源代码目录、输出目录等。通过.classpath文件，Eclipse可以正确地加载项目的依赖库和源代码，以及编译和运行项目。

这三个文件都是Java web项目在Eclipse中必须的文件，它们分别用于自动化构建、描述项目基本信息和类路径。如果缺少其中任何一个文件，都可能导致项目无法正常构建、编译和运行。
```

### Maven项目结构
```text
如果项目是使用 Maven 进行构建的，那么 build.xml 文件可以省略，因为 Maven 使用 pom.xml 文件来管理项目的构建和依赖关系。pom.xml 文件是 Maven 项目的核心文件，
它包含了项目的基本信息、依赖关系、插件配置等。在 Maven 中，所有的构建任务都是通过插件来实现的，而不是像 Ant 那样使用 build.xml 文件来定义任务。

除了 pom.xml 文件之外，Maven 项目中还会有一些其他的文件和目录，例如：

- src/main/java：Java 代码的主目录。
- src/main/resources：资源文件的主目录，例如配置文件、图片等。
- src/test/java：Java 代码的测试目录。
- src/test/resources：测试资源文件的目录。
- target：Maven 构建生成的目标文件的输出目录。

这些文件和目录的作用和命名规则都是由 Maven 约定俗成的，开发人员只需要按照约定来组织项目结构和文件即可。
```

### Eclipse项目中的".settings"目录和"META-INF"目录
```text
1. ".settings" 目录是 Eclipse 项目的配置目录，用于存储项目的各种配置信息。该目录下的所有文件都是 Eclipse 自动生成的，开发人员不需要手动修改这些文件。
下面是该目录下常见的文件及其作用：

- org.eclipse.jdt.core.prefs：Java 项目的编译器配置文件，包括编译选项、编码格式等。
- org.eclipse.wst.common.component：Web 项目的组件配置文件，用于指定项目的 Web 组件和依赖关系。
- org.eclipse.wst.common.project.facet.core.xml：Web 项目的 Facet 配置文件，用于指定项目的 Facet 版本和依赖关系。
- org.eclipse.jst.common.project.facet.core.xml：通用的 Facet 配置文件，用于指定项目的 Facet 版本和依赖关系。
- org.eclipse.m2e.core.prefs：Maven 插件的配置文件，用于指定 Maven 的配置信息和插件版本等。

2. "META-INF" 目录是 Java Web 应用程序的元数据目录，用于存储应用程序的元数据信息。
该目录下的文件通常是由开发人员手动创建的，下面是该目录下常见的文件及其作用：

- MANIFEST.MF：Java 应用程序的清单文件，用于指定应用程序的版本、依赖关系、入口类等信息。
- context.xml：Tomcat 容器的上下文配置文件，用于指定应用程序的上下文路径、数据库连接等信息。
- web.xml：Java Web 应用程序的部署描述文件，用于指定应用程序的 Servlet、Filter、Listener 等组件和配置信息。
- persistence.xml：Java Persistence API (JPA) 的配置文件，用于指定应用程序的实体类、数据库连接等信息。
- spring.xml：Spring 框架的配置文件，用于指定应用程序的 Bean、AOP、事务等配置信息。
```