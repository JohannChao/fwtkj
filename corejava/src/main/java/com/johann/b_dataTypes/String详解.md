### 字符串概述;

#### 介绍
- 字符串（String类）是java常用类之一，位于java.util包下。
- 字符串是字符序列，它不是基本数据类型，而是对象。
- String字符串是不可变的，一旦创建变无法更改。
- 字符串可以通过使用双引号来快捷创建其对象，这些字符串对象也被成为字符串常量。
- 字符串是唯一支持运算符重载的Java类，我们可以使用“ +”运算符来拼接两个字符串。
- Java将字符串对象存储在特定的预定义区域中，字符串池是Java堆空间中存储字符串的一部分。
- 字符串本质上是可序列化的，并实现可序列化接口。

String对象两种不同的创建方式

1，使用双引号创建。这是最简单也是首选的创建字符串对象的方式，它将在字符串常量池中创建一个字符串对象。

2，使用new关键字创建。使用new创建的字符串对象，存储在堆内存中，对象指向的也是堆内存中的对象，而不是字符串池中的同值对象。

```java
1, String s1 = "hello，java"; 
2, String s2 = new String("hello，java");
```

#### 字符串常见的方法
```text
1, length()：返回字符串对象的长度。
2, charAt(int index)：返回给定索引处的字符值。
3, toCharArray()：此方法从该字符串创建一个字符数组。
4, getBytes(String charsetName)：用于从此字符串创建字节数组。
5, equals(Object anObject)：用于将该字符串与另一个对象进行比较。
6, equalsIgnoreCase(String anotherString)：用于将此字符串与不区分大小写的另一个字符串进行比较。
7, compareTo(String anotherString), compareToIgnoreCase(String str)：按字典顺序将此字符串与另一个字符串进行比较。第一个区分大小写，第二个进行不区分大小写的比较。
8, startsWith(String prefix)：如果此字符串以给定的字符串开头，则返回true。
9, endsWith(String suffix)：如果此字符串以给定的字符串结尾，则返回true。
10, substring(int beginIndex, int endIndex)：返回此字符串的子字符串。
11, concat(String str)：将给定的字符串连接到该字符串的末尾并返回它。
12, replace(char oldChar, char newChar)：用newChar替换oldChar之后，返回一个新字符串。
13, matches(String regex)：检查此字符串是否与给定的正则表达式匹配。
14, split(String regex)：使用正则表达式参数将此字符串拆分为字符串数组。
15, join(CharSequence delimiter, CharSequence... elements)：一种实用程序方法，用于使用指定的分隔符将许多字符串连接到新字符串中。我们可以使用此方法从字符串数组创建CSV记录。
16, toLowerCase(), toUpperCase()：用于获取此字符串的小写和大写版本。
17, trim()：用于从此字符串中删除前导和尾随空格。
18, strip(), stripLeading(), stripTrailing()：从该字符串中删除空格后，返回新字符串。
19, isBlank()：如果字符串为空或仅包含空格，则返回true。
20, lines()：Java 11中引入的：从该字符串返回一行流。
21, indent(int n)：Java 12中引入的：根据参数值返回缩进字符串。
22, transform(Function<? super String, ? extends R> f)：在Java 12中引入，将功能应用于此字符串。该函数应接受单个字符串参数并返回R。
23, format(String format, Object... args)：使用指定的格式和参数返回格式化的字符串。
24, valueOf(Object obj)：返回给定对象的字符串表示形式。有重载版本可以使用原始数据类型，数组和对象。
25, intern()：从字符串常量池池返回字符串。
26, repeat(int count)：将此字符串连接指定的次数后，返回一个新的字符串。
27, describeConstable(), resolveConstantDesc(MethodHandles.Lookup lookup)：为Java 12 Constants API实现。
```

#### 字符串的不可变性带来的好处

1，由于字符串是不可变的，这为字符串池的实现提供了可能，字符串池可以节省了内存并提高性能。

2，由于我们无法更改字符串对象的值，因此更加安全。

3，在多线程环境中使用字符串时的线程安全性。

4，作为加载类的参数进行传递的字符串值是不可改变的，因此类加载更加安全。

### 字符串字面量

一个字符串字面量是由双引号（“”）括起来的零个或多个字符。

**字符串字面量示例**
（JDK1.8的环境下）
```java
package testPackage;
class Test {
    public static void main(String[] args) {
        String hello = "Hello", lo = "lo";
        System.out.print((hello == "Hello") + " ");             //true
        System.out.print((Other.hello == hello) + " ");         //true
        System.out.print((other.Other.hello == hello) + " ");   //true
        System.out.print((hello == ("Hel"+"lo")) + " ");        //true
        System.out.print((hello == ("Hel"+lo)) + " ");          //false
        System.out.println(hello == ("Hel"+lo).intern());       //true
    }
}
class Other { static String hello = "Hello"; }
```
```java
package other;
public class Other { public static String hello = "Hello"; }
```
以上程序运行结果为：
```text
true true true true false true
```

### 字符串常量池

又叫全局字符串常量池，存在于Java Heap中（jdk1.6以及之前的版本，String Pool存在于PermGen中）。

String的String Pool的底层实现是一个Hashtable，默认值大小长度是1009，如果放进String Pool的String非常多，就会造成Hash冲突严重，从而导致链表会很长，
而链表长了后直接会造成的影响就是当调用String.intern时性能会大幅下降（因为要一个一个找）。

#### 字符串池工作方式

1，当我们创建一个字符串时，它存储在字符串池中。

2，如果字符串池中已经有一个具有相同值的字符串，则不会创建新的字符串对象。返回对现有字符串对象的引用。

3，Java字符串池是字符串对象的缓存。因为字符串是不可变的使得这一实现成为可能。

4，如果我们使用new关键字创建一个string对象，它将在堆内存中创建。如果要将它移动到字符串池，可以使用intern()方法。

5，字符串池是Flyweight设计模式的一个实现例子。


### 探讨String#intern

它的大体实现结构就是: JAVA 使用 jni 调用c++实现的StringTable的intern方法, StringTable的intern方法跟Java中的HashMap的实现是差不多的, 只是不能自动扩容。默认大小是1009。

在 jdk6中StringTable是固定的，就是1009的长度，所以如果常量池中的字符串过多就会导致效率下降很快。在jdk7中，StringTable的长度可以通过一个参数指定：
```text
-XX:StringTableSize=99991
```

源码中对intern方法的解释（JDK1.8）
```text
    /**
     * When the intern method is invoked, if the pool already contains a
     * string equal to this {@code String} object as determined by
     * the {@link #equals(Object)} method, then the string from the pool is
     * returned. Otherwise, this {@code String} object is added to the
     * pool and a reference to this {@code String} object is returned.
     *
     * 当intern方法被调用时候，如果字符串池中已经包含了一个与当前 string(A) 值相等的 string(B)，
     * 那么，会返回这个池中的 string(B)。若不包含，则当前的这个 string(A)  会被添加到字符串池中，
     * 并且返回这个 string(A) 的引用。
     */
    public native String intern();
```


以下我们会通过几组不同的示例（JDK1.8下），来探讨String#intern方法以及String Pool。


#### 示例一 new String("hello")
```java
public class StringInternTest {

    public static void main(String[] args) {
        // 1，创建了两个对象，一个是存储在常量池中的“hello”字符串，另一个是存储在Heap中的String对象。s1指向Heap中的对象
        String s1 = new String("hello");
        // 2，s2指向字符串池中“hello”字符串
        String s2 = "hello";
        // 3，字符串池中已有“hello”字符串，所以这一句没有产生任何影响。
        s1.intern();
        System.out.println("s1==s2 : "+(s1==s2)); //false

    }
}
```

#### 示例二 String s1 = "hello"+"world";
```java
public class StringInternTest {

    public static void main(String[] args) {
         // 编译阶段优化为 "helloworld",在字符串常量池中创建一个字面量为 "helloworld"的字符串
         String s1 = "hello"+"world";
         String s0 = "helloworld";
         System.out.println(s1==s0);// true
    }
}
```


#### 示例三 new String("hel") + new String("lo")
```java
public class StringInternTest {

    public static void main(String[] args) {
        // 1，在堆中创建了“hel”和“lo”对象，并将“hel”，“lo”字符串加入到了字符串常量池中。
        //    在堆中创建一个“hello”对象,字面量为“hello”的字符串没有加入到全局字符串常量池中。s1指向的是堆中的“hello”对象
        String s1 = new String("hel") + new String("lo");
        // 2，字符串常量池中没有“hello”，在字符串常量池中生成一个“hello”字符串，s2指向字符串常量池中的“hello”
        String s2 = "hello";
        // 3，字符串常量池中已经有一个“hello”字符串。返回字符串常量池中的“hello”字符串，无影响。
        s1.intern();
        System.out.println("s1 == s2 : "+(s1 == s2)); //false

        System.out.println("======================");

        // 1，在堆中创建了“go”和“od”对象，并将“go”，“od”字符串加入到了字符串常量池中。
        //    在堆中创建一个“good”对象,字面量为“good”的字符串没有加入到全局字符串常量池中。s1指向的是堆中的“good”对象
        String s3 = new String("go") +new String("od");

        // 2，字符串常量池中不存在“good”字符串，字符串常量池中直接保存字面量为“good”的堆中对象的引用，即此时常量池中的引用指向s1引用的对象。
        // ### 如果此时为JDK1.6，JDK1.6中，字符串常量池保存在PermGen中，此时会在字符串常量池中生成一个字面量为“good”的字符串。###
        s3.intern();

        // 3，s4的引用指向字符串常量池中的s1引用对象的引用
        // ### JDK1.6及以前，s4指向PermGen中字符串常量池里的“good”字符串。###
        String s4 = "good";
        System.out.println("s3 == s4 : "+(s3 == s4)); //JDK1.7、JDK1.8结果为 true；JDK1.6及以前，结果为 false
    }
}
```

#### 示例四 new String("lo"+"ve")
```java
public class StringInternTest {

    public static void main(String[] args) {
        // 1，这一句我们只知道在堆中创建了“love”对象，并不知道在常量池中是否加入了“love”字符串
        String s15 = new String("lo"+"ve");

        //如果上一句执行完，如果在StringTable中放入了"love"，那么下面的执行结果就是false；如果在StringTable中没有放入了"love"，执行该句，会把堆中“love”对象的引用放入到StringTable中，即下面的结果是 true
        // 2，上一句执行完，如果在字符串常量池中没有加入“love”字符串，那么执行完 s15.intern(); 会在字符串常量池中加入字面量为“love”的堆中对象的引用。
        
        //   如果字符串中已经加入了“love”字符串，此时无影响。
        s15.intern();
        // 3, s16为字符串常量池中的对象引用
        String s16 = "love";

        System.out.println("s15 == s16 : "+(s15==s16));// fasle 说明执行 new String("lo"+"ve"); 时，在字符串常量池中已经加入了“love”，即在StringTable中已经放入了“love”

        System.out.println("=================================");

        // 1，通过javap分析，我们可以知道这一句代码在编译的时候，会把“mi”，“ne”合并为一个对象“mine”保存在常量池中。
        //   即这一句，只在堆中创建了“mine”对象，在字符串常量池中创建了“mine”字符串。没有“mi”，“ne”对象
        String s17 = new String("mi"+"ne");

        // 2，只会在堆中创建"mi",在StringTable不放入“mi”
        String s18 = new String("m")+new String("i");
        // 2，只会在堆中创建"mi",在StringTable不放入“mi”
        //String s18 = new StringBuilder().append("m").append("i").toString();

        System.out.println(s18.intern()==s18); // true    new String("mi"+"ne");时，不会在堆中创建 "mi"和"ne"对象，也不会在字符串常量池中创建相同字面量的字符串

        System.out.println("=================================");

        // 1，在堆中创建了“yo”对象，在字符串常量池中创建了字面量为“yo”的字符串
        String s19 = new String("yo");

        // 2，只会在堆中创建"yo",在StringTable不放入“yo”
        String s20 = new String("y")+new String("o");
        // 2，只会在堆中创建"yo",在StringTable不放入“yo”
        //String s20 = new StringBuilder().append("y").append("o").toString();

        System.out.println(s20.intern()==s20); // false   new String("yo"); 时，已经在字符串常量池中加入了 "yo",所以s20.intern()指向的字符串常量池中的 "yo"，s20指向堆中的一个"yo"对象
    }
}
```

#### 示例五 String s2 = "nihao"; String s3 = "java";  String s4 = s2+s3;
```java
public class StringInternTest {

    public static void main(String[] args) {
        String s2 = "nihao";
        String s3 = "java";
        // 下面这一句，实际执行步骤是：创建一个StringBuilder对象，将所相加的字符串append起来，最后调用StringBuilder的toString方法，在堆中创建一个"nihaojava"对象
        // 但是，在字符串常量池中没有加入 "nihaojava"字符串。s4引用指向堆中对象
        String s4 = s2+s3;
        // 在字符串常量池中加入 字面量为“nihaojava”的堆中对象的引用
        s4.intern();
        // s5指向字符串常量池中字面量为 "nihaojava"的字符串，实际上这个字符串是堆中字面量为 "nihaojava"对象的引用
        String s5 = "nihaojava";
        System.out.println(s4==s5); // true
    }
}
```

#### 示例六 String a = "some"; String param = new String("param" + a);
```java
public class StringInternTest {

    public static void main(String[] args) {
        // 字符串常量池中加入 "some"字符串
        String a = "some";
        // 在堆中创建 "some"对象，在字符串常量池中加入字面量为"some"的字符串
        //String a = new String("some");
        // 下面这一句，实际执行步骤是：创建一个StringBuilder对象，将所相加的字符串append起来，最后调用StringBuilder的toString方法，在堆中创建一个"paramsome"对象,没有加入字符串常量池
        String param = "param" + a;
        // 下面这一句，实际执行步骤是：创建一个StringBuilder对象，将所相加的字符串append起来，最后调用StringBuilder的toString方法，在堆中创建一个"paramsome"对象,没有加入字符串常量池
        //String param = new String("param" + a);
        param.intern();
        String paramSome = "paramsome";
        System.out.println(param == paramSome);// true
    }
}
```

### 全局字符串常量池中存储的到底是对象还是引用？

根据[@R大](https://www.zhihu.com/people/rednaxelafx/activities)在[参考4](https://www.zhihu.com/question/36908414/answer/69724311)与[参考5](https://www.zhihu.com/question/36908414/answer/69724311)中的答案，知晓了在Java中，字符串常量池中存放的是引用，而不是把这些对象直接存储在全局常量池中。

但是，这一说法与[参考2](https://tech.meituan.com/2014/03/06/in-depth-understanding-string-intern.html)中,关于字符串常量池存储类型的解释有出入，这篇文章中写明的是字符串常量池既可以存储对象字符串，又可以存储引用。

在参考中，可以了解到以下几点：

1，String a = "123"; 对象存储在堆中，引用存储在全局字符串常量池中【string pool / StringTable】。
可以认为在堆中存在一个字面量为"123"的字符串对象，但在字符串常量池中存在其引用。

String b = new String("456"); 按照上述的说法，那么使用new关键字会创建两个字面量为"456"的字符串对象，这两个对象的都存储在堆中，只不过其中一个的引用被 b 持有，另外一个的引用存储在字符串常量池【StringTable】中。

2，HotSpot VM的StringTable的本体在native memory里。它持有String对象的引用而不是String对象的本体。
被引用的String还是在Java heap里。一直到JDK6，这些被intern的String在permgen里，JDK7开始改为放在普通Java heap里。

关于字符串常量池，看的有点头大，今天暂时探讨到这里，以后再做进一步的思考。

### 有意思的几个问题

#### System.out.print(null);
打印结果是 ：
```text
null
``` 
为什么呢？
```java
    // print方法源码
    public void print(String s) {
        if (s == null) {
            s = "null";
        }
        write(s);
    }
```
#### String a = null+""; System.out.print(a);
打印结果是 ： 
```text
null
```
为什么呢？

null+""的实际执行步骤是：创建一个StringBuilder对象，将所相加的字符串append起来，最后调用StringBuilder的toString方法。
即 new StringBuilder().append(null).append("");
```java
    // StringBuilder类中源码
    public StringBuilder append(Object obj) {
        return append(String.valueOf(obj));
    }
    
    public StringBuilder append(String str) {
        super.append(str);
        return this;
    }
    
    // String类中的源码
    public static String valueOf(Object obj) {
        return (obj == null) ? "null" : obj.toString();
    }
    
    // StringBuilder父类 AbstractStringBuilder 类中源码
    public AbstractStringBuilder append(String str) {
        if (str == null)
            return appendNull();
        int len = str.length();
        ensureCapacityInternal(count + len);
        str.getChars(0, len, value, count);
        count += len;
        return this;
    }
    
    private AbstractStringBuilder appendNull() {
        int c = count;
        ensureCapacityInternal(c + 4);
        final char[] value = this.value;
        value[c++] = 'n';
        value[c++] = 'u';
        value[c++] = 'l';
        value[c++] = 'l';
        count = c;
        return this;
    }
```
由源码可知

String.valueOf(null) 结果是 "null";

使用StringBuilder和StringBuffer的 append 方法时，要注意如果传入的是null，是会直接在字符串上拼接一个"null".

#### String.concat()方法
```java
    String s001 = "zhong";
    String s002 = s001.concat("guo");
    //s002.intern();
    System.out.println(s001);
    System.out.println(s002);
    String s003 = "zhongguo";
    System.out.println(s002==s003);
```
打印结果是：
```text
zhong
zhongguo
false
```

```java
    String s001 = "zhong";
    String s002 = s001.concat("guo");
    s002.intern();
    System.out.println(s001);
    System.out.println(s002);
    String s003 = "zhongguo";
    System.out.println(s002==s003);
```
打印结果是：
```text
zhong
zhongguo
true
```
通过以上结果对比，我们可知：

1，String.concat()方法执行完后，会生成一个新的字符串，我们需要一个新的变量名来接收结果。原字符串没有改变，这符合String字符串不可变的特性。

2，String.concat()方法执行完后，在堆中创建了一个新的字符串，但是在字符串变量池中不存在同值的字符串。



### 参考：

[1，Java语言规范 3.10.5. String Literals](https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-CharacterLiteral)

[2，深度解析String＃intern ---美团技术团队](https://tech.meituan.com/2014/03/06/in-depth-understanding-string-intern.html)

[3，Java 中new String("字面量") 中 "字面量" 是何时进入字符串常量池的? ---知乎](https://www.zhihu.com/question/55994121/answer/147296098)

[4，new一个String对象的时候，如果常量池没有相应的字面量真的会去它那里创建一个吗？ ---知乎](https://www.zhihu.com/question/36908414/answer/69724311)

[5，JVM 常量池中存储的是对象还是引用呢？ ---知乎](https://www.zhihu.com/question/57109429/answer/151717241)

[6，Java String](https://www.javastring.net/)