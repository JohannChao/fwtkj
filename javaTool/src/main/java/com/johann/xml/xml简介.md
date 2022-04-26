### XML文件定义
　　XML即可扩展标记语言（EXtensible Markup Language）。标记是指计算机所能理解的信息符号，通过此种标记，计算机之间可以处理包含各种信息的文章等。

　　通俗的讲，XML文件一般用来保存有关系的数据。除此之外，在程序开发中，我们通常用来做各种框架的配置文件(PS:经过时代发展，目前xml替代方案有很多，比如更加轻量级的json，yml,各有优缺点，这里暂时不做对比)。

　　归纳如下：

　　①、XML 指可扩展标记语言（EXtensible Markup Language）；

　　②、XML 是一种标记语言，很类似 HTML；

　　③、XML 的设计宗旨是传输数据，而非显示数据（HTML作用是显示数据）；

　　④、XML 标签没有被预定义，需要自行定义标签；

　　⑤、XML 被设计为具有自我描述性；

　　⑥、XML 是 W3C 的推荐标准。

### XML文件的组成

  1. 文档声明
  2. 元素
  3. 属性
  4. 注释
  5. CDATA区、特殊字符
  6. 处理指令（processing intruction)
  
##### 1，文档声明
　　在编写 XML 文档时，需要首先使用文档声明，声明 XML 文档的类型。
```html
①、最简单的语法：
<?xml version="1.0" ?>

②、用 encoding 属性说明文档的字符编码：
<?xml version="1.0" encoding="utf-8" ?>
常见的字符编码：gbk,gb2312,utf-8,基本上我们使用utf-8，全世界通用，不会出现乱码的现象。

③、用 standalone 属性说明文档是否独立：
<?xml version="1.0" encoding="utf-8" standalone="no" ?>
standalone有两个属性，yes和no。如果是yes，则表示这个XML文档时独立的，不能引用外部的DTD规范文件；如果是no，则该XML文档不是独立的，表示可以用外部的DTD规范文档。
```

##### 2，元素
```html
①、XML 元素指的是 XML 文件中出现的标签，一个标签分为开始标签和结束标签，分为两种写法：
  1、包含标签体：<user>Tom</user>
  2、不包含标签体：<user></user>，也可以简写为<user/>

②、一个标签中也可以嵌套其他的若干个子标签。但所有的标签必须合理的嵌套，绝不允许交叉嵌套：
  合理写法：
         <users>
　　　　　　　<name>Tom</name>
　　　　　</users>
错误写法：
        <users><name>Tom</users></name>

③、格式良好的 XML 文档必须有且仅有一个根标签，其他的标签都是这个标签的子孙标签。

④、对于 XML 标签中出现的所有空格和换行，XML 解析都会当做标签内容进行处理。比如下面两个是不一样的
  1、<name>Tom</name>
  2、<name>

　　　　　Tom

　　 </name>
注意：由于在 XML 中，空格和换行都会作为原始内容被处理，所以在编写 XML 文件的时候使用换行和缩进等方式来让原文件中的内容清晰可读的良好习惯可能要被迫改变。
这和 HTML 标签的书写是有点区别的。

⑤、元素的命名规范：
  1、区分大小写，例如：<P>和<p>是不一样的
  2、不能以数字或下划线“-”开头
  3、不能以 xml（或XML,Xml）作为开头
  4、不能包含空格
  5、名称中间不能包含冒号“：”
```

##### 3，属性
```html
①、一个元素可以有多个属性，每个属性都有它自己的名称和取值，比如：
   <input name="tom" />

②、属性值一定要用双引号或单引号引起来

③、属性的命名规范和元素的命名规范一样

④、XML 文件中，元素属性所代表的信息，也可以改为用子元素来表示，比如：
    <input name="tom" />
可以写为：
    <input>
        <name>tom</name>
    </input>
```

##### 4，注释
```html
①、XML 文件中注释采用："<!-- 注释 -->" 这样的格式

②、XML 声明之前不能有注释

③、注释不能嵌套，比如下面不合规范：
    <!-- 全局注释   <!-- 局部注释 -->-->
```

##### 5，CDATA区和特殊字符
```html
①、在编写 XML 文档时，有些内容可能不想让解析引擎解析执行，而是当做原始内容处理，那么我们就可以把这些内容放到 CDATA区里面，
对于 CDATA 区里面的内容，XML 解析程序不会处理，而是原封不动的输出。
    语法：
        <![CDATA[内容]]>        
    比如：
        <![CDATA[
            <input>
                <name>tom</name>
            </input>
        ]]>
注意：CDATA和[内容]之间不能有空格

②、转义字符：对于一些单个字符，如果想显示其原始样式，可以使用转义的形式。[转义字符](https://dev.w3.org/html5/html-author/charref)
    
    特殊字符    替代符号
      &          &amp;
      <          &lt;
      >          &gt;
      "          &quot; (quotation)
      '          &apos; (apostrophe)
```

##### 6，处理指令processing instruction
```html
①、用来解析引擎如何解析 XML 文档内容
比如：在 XML 文档中可以使用 xml-stylesheet 指令，通知 XML 解析引擎，应用 CSS 文件显示 XML 文档内容
    <?xml-stylesheet type="text/css" href="a.css"?>
处理指令必须以<? 开头，以 ？> 结尾 
```

### XML约束

　　由于xml的标签由用户自己定义，因此在开发的时候，每个人都可以根据自己的需求来定义xml标签，这样导致项目中的xml难以维护，
因此需要使用一定的规范机制来约束xml文件中的标签书写。

#### 1，XML DTD（Document Type Definition）约束

##### 1.1 DTD引入
```html
DTD主要用来约束xml文件，DTD可以单独写在文件中，也可以直接定义在xml中，可以在xml中引入第三方的公共DTD。

1，外部DTD的引入方式：外部DTD主要指的一个独立的DTD文件,扩展名为dtd。
首先要书写DTD文件，然后在要被约束的xml文件中引入。
在xml文件中有多少个标签，就在dtd中书写多少个ELEMENT标签

    <!DOCTYPE 文档根结点 SYSTEM "DTD文件的URL">
    ①，文档根结点 指的是当前xml中的根标签。
    ②，SYSTEM  引入的系统中存在文件
    ③，"DTD文件的URL" DTD存放的位置
    
    引入公共的DTD：
    <!DOCTYPE 文档根结点 PUBLIC "DTD名称" "DTD文件的URL">
    ①，文档根结点 指的是当前xml中的根标签。
    ②，PUBLIC  表示当前引入的DTD是公共的DTD


2，在xml中直接书写DTD
    <!DOCTYPE  根标签名 [
    具体的标签的约束
    ]>
```

示例：
```html
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE users[
    <!ELEMENT users (user+) >    
    <!ELEMENT user (name,age,addr) >    
    <!ELEMENT name (#PCDATA) >    
    <!ELEMENT age (#PCDATA) >    
    <!ELEMENT addr (#PCDATA) >    
]>
<users>
    <user>
        <name>zhangsan</name>
        <age>23</age>
        <addr>shanghai</addr>
    </user>
    <user>
        <name>lisi</name>
        <age>24</age>
        <addr>beijing</addr>
    </user>
</users>
```

##### 1.2 DTD的语法介绍
```html
1，元素
当定义DTD约束xml时候，这时需要在DTD中使用ELEMENT来定义当前xml中可以出现的标签名称。
格式：
    <!ELEMENT 标签名 约束>  
    
约束来限定当前标签中可以有的子标签，或者当前标签中可以书写的内容。

在定义标签的时候，约束中可以使用一些符号标签具体出现次数：
    ?    零次或者一次
    *  零次或者多次
    +    一次或者多次   users (user+)   表示当前的users标签下可以有一个或者多个user标签
    ,   用来限定当前的子标签出现的顺序user (name,age,addr)  user标签下只能有name age addr 子标签，并且必须按照name  age  addr的顺序书写
    |  user (name|age,addr) user下可以name或者age ，但必须有addr，并且addr必须name或age后面
    #PCDATA        表明该元素可包含任何字符数据，但不能在其中包含任何子元素。只有 PCDATA 的元素通过圆括号中的 #PCDATA 进行声明
    EMPTY    表明该元素不能有任何子元素或文本，仅可以使用属性。
    ANY        表该元素中可以包含任何DTD中定义的元素内容 如：<!ELEMENT note ANY>
    <!ELEMENT age EMPTY >  当前的age标签是个空标签，它不能有文本内容。


2，属性
在xml中的标签上是可以书写属性的，在DTD中就需要对属性进行约束。
格式：
    <!ATTLIST 标签名 属性名 属性的类型  属性的约束>
    
如果一个标签上有多个属性
<!ATTLIST 标签名 属性名 属性的类型  属性的约束  
属性名 属性的类型  属性的约束 
属性名 属性的类型  属性的约束>

<标签名 属性1=””  属性2=””  属性3=”” >

    2.1，属性的类型：
    类型    描述
    CDATA    值为字符数据 (character data)，属性的value值可以是文本数据
    (en1|en2|..)    此值是枚举列表中的一个值。(值1 | 值2 | 值3....   )，表示当前的属性的value值只能是当前括号中的值
    ID    值为唯一的 id。ID 表示唯一。对当前标签上的id属性进行限定，并且同一个xml中id不能重复
    IDREF    值为另外一个元素的 id
    IDREFS    值为其他 id 的列表
    NMTOKEN    值为合法的 XML 名称
    NMTOKENS    值为合法的 XML 名称的列表
    ENTITY    值是一个实体
    ENTITIES    值是一个实体列表
    NOTATION    此值是符号的名称
    xml:    值是一个预定义的 XML 值

    2.2，属性的约束：
    REQUIRED 属性是必须书写的。
    Implied 属性是可选得。
    #fixed value 属性的value是固定的值。
    “value”  代表属性的默认值。
    示例：
        <!ATTLIST 标签名 属性名 属性的类型  属性的约束>
        user name CDATA  fixed “zhangsan” 
        <user name=”zhangsan”>
        user name CDATA “张三”
        <user />
        
3，实体
实体用于为一段内容创建一个别名，以后在XML文档中就可以使用别名引用这段内容了。
在DTD定义中，<!ENTITY …>  语句用于定义一个实体。
实体可以理解成Java中预先定义好的一个常量，然后xml文件中就可以引入当前这个定义的实体。
    示例：
    <?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE users[
        <!ELEMENT users (user+) >    
        <!ELEMENT user (name,age,addr) >    
        <!ELEMENT name (#PCDATA) >    
        <!ELEMENT age EMPTY >    
        <!ELEMENT addr (#PCDATA) >    
        <!ATTLIST user id ID #REQUIRED >
        <!ENTITY  abc "上海传智播客123123">
    ]>
    <users>
        <user id="u001">
            <name>zhangsan</name>
            <age></age>
            <addr>&abc;</addr>
        </user>
        <user id="u002">
            <name>lisi</name>
            <age/>
            <addr>&abc;</addr>
        </user>
    </users>
```

#### 2，Schema约束

##### 2.1，xml Schema介绍
```html
Schema它也来约束xml文件的，DTD在约束xml的时候一个xml中只能引入一个DTD，同时DTD它无法对属性以及标签中的数据做数据类型的限定。

Schema它是用来代替DTD来约束xml。

Schema文件本身就是使用xml文件书写的，同时它对需要约束的xml中的数据有严格的限定。学习Schema主要来学习W3C组织定义的如何在Schema中去约束xml的标签以及属性，还有属性的数据类型，以及标签中子标签的顺序。

要定义一个Schema文件，这时它的扩展名必须是.xsd。在这个文件中根元素必须是schema。

使用Schema来约束xml，Schema在书写的时候，只需要使用W3C组织提前定义的限定标签的，以及限定的属性的那个标签即可。
```

##### 2.2 定义schema文件
```html
1，在定义Schema文件的时候，由于这个Schema文件本身就是xml，它也要受到别的约束。而这个约束是W3C组织提前定义好的，

在Schema文件中需要提前引入进来在根标签中使用属性进行进入：

<schema  xmlns="http://www.w3.org/2001/XMLSchema"   引入W3C定义的schema书写的规范
targetNamespace="http://www.itcast.org/book" 给当前的Schema文件起名字（命名空间）
作用是当哪个xml要引入这个schema约束的时候，必须通过当前targetNamespace 后面书写的uri地址来引入

示例：

<?xml version="1.0" encoding="UTF-8"?>
<schema xmlns="http://www.w3.org/2001/XMLSchema" 
    targetNamespace="http://www.itcast.org/book" 
    elementFormDefault="qualified">
    <element name="books">
        <complexType>
            <sequence>
                <element name="book">
                    <complexType>
                        <sequence>
                            <element name="name"></element>
                            <element name="author"></element>
                            <element name="price"></element>
                        </sequence>
                    </complexType>
                </element>
            </sequence>
        </complexType>
    </element>
</schema>

2，在xml文件中引入当前的这个Schema
<books xmlns="http://www.itcast.org/book"   它是schema文件中的targetNamespace 属性后面的值
        xsi:schemaLocation="http://www.itcast.org/book book.xsd"   这个是在引入当前的schema文件的真实路径
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"   说明当前的xml是schema一个实例文档
>

3，Schema的名称空间
在定义Schema文件的时候，需要在<schema>根标签中使用
targetNamespace  属性定义当前schema定义名称（只是一个分配的名字，根本没有指向任何文件），在被约束的xml文件中先根据这个名称引入当前的schema文件，然后在使用
xsi：schemaLocation=””    引入具体的schema文件。（因为targetNamespace属性定义的schema名称，只是一个名称而已，所以在xml文件中需要通过schemaLocation来声明指定所遵循的Schema文件的具体位置）
（xsi：schemaLocation 使用它引入某个schema时，遵循的原则是：名称空间——空格——文件名）

名称空间主要功能是用于来
elementFormDefault="qualified|unqualified"
在schema中书写qualified ，在限定xml中的定义的标签名必须使用定义的名称空间。
unqualified 要求根元素必须使用名称空间，而子元素不能使用名称空间。
```

##### 2.3 Schema中的标签解释
```html
Book2.xsd：

<?xml version="1.0" encoding="UTF-8"?>
<schema xmlns="http://www.w3.org/2001/XMLSchema" 
    targetNamespace="http://www.itcast2.org/book" 
    elementFormDefault="qualified"> <!-- 在schema中书写qualified ，在限定xml中的定义的标签名必须使用定义的名称空间。
                                         unqualified 要求根元素必须使用名称空间，而子元素不能使用名称空间。-->
    <element name="books">  <!--name代表当前的xml中可以书写标签名称  type数据类型-->
        <complexType ><!-- complexType 当前的element声明的标签是复杂标签时 ，需要使用complexType来声明子标签-->
            <sequence>  <!-- 复杂标签是指有属性，或者有子标签，或者有属性有子标签的标签 
                                简单标签是指只有文本内容的标签
                                <name>zhangsan</name>  简单标签
                                <name id="u001"></name>  复杂标签
                                sequence 代表当前子标签的顺序
                            -->
                <element name="book" maxOccurs="unbounded">
                    <complexType mixed="true"><!—mixed属性值为true，book元素间就可以出现字符文本数据了-->
                        <sequence>
                            <element name="name"></element>
                            <element name="author"></element>
                            <element name="price" type="integer"></element>
                            <any></any>
                        </sequence>
                    </complexType>
                </element>
            </sequence>
        </complexType>
    </element>
</schema>

Name.xsd：

<schema xmlns="http://www.w3.org/2001/XMLSchema" 
    targetNamespace="http://www.example.org/name" 
    elementFormDefault="qualified">
    <element name="name"></element>
</schema>


Book2.xml：

<?xml version="1.0" encoding="UTF-8"?>
<aa:books xmlns:aa="http://www.itcast2.org/book"
    xmlns:bb="http://www.example.org/name"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.itcast2.org/book book2.xsd 
                        http://www.example.org/name name.xsd"
    >
    <aa:book>
        <aa:suibianxie></aa:suibianxie>    
        <aa:name >JavaWEB</aa:name>
        <aa:author >老毕</aa:author>
        <aa:price>182</aa:price>
        <bb:name>sdgs</bb:name>
    </aa:book>
</aa:books>

```

### 参考
1. [XML简介](https://www.cnblogs.com/ysocean/p/6901008.html)
2. [xml语法、DTD约束xml、Schema约束xml、DOM解析xml ](https://www.cnblogs.com/cb0327/p/4967782.html#_label2)

