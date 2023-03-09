### StringBuilder,StringBuffer简介

#### StringBuilder
1，Java StringBuilder类用于操作字符串对象。

2，StringBuilder是可变的字符序列。

3，StringBuilder是Java 1.5中引入的。

4，StringBuilder方法不是线程安全的，因此建议在单线程环境中使用。

5，StringBuilder提供的一些常见操作是–  append，insert，reverse和delete。

#### StringBuffer
1，Java StringBuffer类用于操作字符串对象。

2，StringBuffer是线程安全的可变字符序列。

3，StringBuffer是Java 1.0本身提供的。

4，StringBuffer方法是同步的，因此建议在多线程环境中将其用于字符串处理。

5，StringBuffer提供的一些常见操作是– append，insert，reverse和delete。

### StringBuilder

StringBuilder继承自 AbstractStringBuilder抽象类，实现Serializable, CharSequence接口。不可以被继承

#### StringBuilder的构造函数

```java
    //AbstractStringBuilder抽象类
    char[] value;
    //构造函数
    AbstractStringBuilder(int capacity) {
        value = new char[capacity];
    }
    
    //StringBuilder构造函数
    public StringBuilder() {
        super(16);
    }
    public StringBuilder(int capacity) {
        super(capacity);
    }
    public StringBuilder(String str) {
        super(str.length() + 16);
        append(str);
    }  
    public StringBuilder(CharSequence seq) {
        this(seq.length() + 16);
        append(seq);
    }    
```

StringBuilder类具有4个构造函数，每次初始化StringBuilder，底层创建了一个char数组，数组的初始容量可设定，默认是16。
如果使用的是参数是String或CharSequence的构造函数，那么数组的容量是 参数的长度+16。

当数组中的元素个数，超过数组的容量[capacity]时，数组会进行扩容，每次扩容遵循的公式是 
```text
StringBuilder底层数组扩容公式

new_capacity = old_capacity*2 + 2
```

#### StringBuilder常见方法

##### append

```java
public class StringBuilderTest {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append(1);//int
        sb.append(" ");
        sb.append(222l);//long
        sb.append(" ");
        sb.append(2.2f);//float
        sb.append(" ");
        sb.append(3.333d);//double
        sb.append(" ");
        sb.append(false);//boolean 添加布尔类型的数据，实际取值为 "true"或"false"字符串，而不是"0"或"1"
        sb.append(" ");
        sb.append('c');//char
        sb.append(" ");
        sb.append("JAVA");//String
        sb.append(" ");
        CharSequence seq = new StringBuilder("String");
        sb.append(seq,1,3);// append(CharSequence,开始位置[包含],结束位置[不包含])
        sb.append(" ");
        char [] chars = {'j','a','v','a','m','e'};
        sb.append(chars,2,3);// append(CharsArray,开始位置[包含],往后截取的长度)
        System.out.println(sb.toString());       
    }
}    
```
打印结果：
```text
1 222 2.2 3.333 false c JAVA tr vam
```

##### insert
```java
public class StringBuilderTest {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        /**
         * insert 方法中，第一个参数 offset的取值范围为 0<= offset <= StringBuilder.length()
         *
         * The {@code offset} argument must be greater than or equal to
         * {@code 0}, and less than or equal to the {@linkplain #length() length}
         * of this sequence.
         *
         **/
        sb.insert(0,10); // int
        sb.insert(2,'A'); // char
        sb.insert(3,223l); // long
        sb.insert(6,2.3f); // float
        sb.insert(9,2.222); // double
        sb.insert(14,false); // boolean 插入布尔类型的数据，实际取值为 "true"或"false"字符串，而不是"0"或"1"
        System.out.println(sb.toString());
    
        // 当 offset 参数的值不等于当前字符串的长度时，可以看到此时会在指定的 offset 位置，插入新的值，
        // 原来的 offset 之后的字符会紧跟在新的值后面。而不会被覆盖
        sb.insert(17,true);
        System.out.println(sb.toString());
    
        char [] chars = new char[]{'c','h','a','r','s'};
        sb.insert(23,chars); // char[] 字符数组
        System.out.println(sb.toString());
        sb.insert(28,"  "); // 插入字符串，空字符串有几个空格，就占几个长度
        System.out.println(sb.toString());
        sb.insert(30,"11");
        System.out.println(sb.toString());
    
        CharSequence seq = new StringBuilder("String");
        sb.insert(32,seq);
        System.out.println(sb.toString());
    
        sb.insert(1,chars,2,3);// 插入char数组的子数组。 insert(在哪个位置插入，char数组，从char数组的哪个位置截取[包含]，截取的子数组长度)
        System.out.println(sb.toString());
    
        sb.insert(0,seq,0,3);// insert(在哪个位置插入，CharSequence，截取字符序列的开始位置[包含]，结束位置[不包含])
        System.out.println(sb.toString());
    }
}
```
打印结果：
```text
10A2232.32.222false
10A2232.32.222faltruese
10A2232.32.222faltruesechars
10A2232.32.222faltruesechars  
10A2232.32.222faltruesechars  11
10A2232.32.222faltruesechars  11String
1ars0A2232.32.222faltruesechars  11String
Str1ars0A2232.32.222faltruesechars  11String
```

##### delete
```java
public class StringBuilderTest {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("String123456");
        StringBuilder sb1 = sb.delete(0,3); //删除指定区间位置的元素。delete(开始位置[包含]，结束位置[不包含])
        System.out.println("sb : "+sb.toString());
        System.out.println("sb1 : "+sb1.toString());
        sb.deleteCharAt(1); //删除指定位置的元素
        System.out.println("sb : "+sb.toString());
        System.out.println("sb1 : "+sb1.toString());// b和sb1 的引用，指向的是堆中的同一个对象
    }
}
```
打印结果：
```text
sb : ing123456
sb1 : ing123456
sb : ig123456
sb1 : ig123456
```

##### replace
```java
public class StringBuilderTest {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("String123456");
        sb.replace(0,5,"1234"); //将指定区间位置的元素，替换为另外的字符串，区间范围不一定要等于待替换的字符串长度。replace(开始位置[包含]，结束位置[不包含]，带替换字符串)
        System.out.println("sb : "+sb.toString());
    }
}
```
打印结果：
```text
sb : 1234g123456
```

##### reverse
```java
public class StringBuilderTest {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("String123456");
        System.out.println("sb : "+sb.toString());
        sb.reverse(); // 字符串反转
        System.out.println("sb : "+sb.toString());
    }
}
```
打印结果：
```text
sb : String123456
sb : 654321gnirtS
```

##### trimToSize
```java
public class StringBuilderTest {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("123456789012345");
        sb.append(" ");
        System.out.println("capacity : "+sb.capacity());
        System.out.println("length : "+sb.length());
        sb.append("7");
        System.out.println("capacity : "+sb.capacity());
        System.out.println("length : "+sb.length());
        System.out.println("toString : "+sb.toString());

        /**
         * 如果当前底层char数组的长度大于当前字符串的长度，调整char数组的长度为当前字符串长度
         **/
        sb.trimToSize();
        System.out.println("capacity : "+sb.capacity());
        System.out.println("length : "+sb.length());
    }
}
```
打印结果：
```text
capacity : 16
length : 16
capacity : 34
length : 17
toString : 123456789012345 7
capacity : 17
length : 17
```

##### 其他方法
下面几个方法，底层都可以追溯到 String类的indexOf() 方法
int indexOf(String str)

indexOf(String str, int fromIndex)

int lastIndexOf(String str)

int lastIndexOf(String str, int fromIndex)



### StringBuffer

StringBuffer 继承自 AbstractStringBuilder抽象类，实现Serializable, CharSequence接口。不可以被继承

#### StringBuffer构造函数和常用方法

StringBuffer有四个构造函数，同StringBuilder。

StringBuffer的常用方法，和StringBuilder一样


#### StringBuffer和StringBuilder的 toString方法区别

在StringBuffer中，存在一个StringBuilder中所没有的数组：toStringCache。这个数组用于缓存最后一次调用toString方法时返回的值。

StringBuffer类的toString方法
```java
public final class StringBuffer
    extends AbstractStringBuilder
    implements java.io.Serializable, CharSequence
{
    /**
     * A cache of the last value returned by toString. Cleared
     * whenever the StringBuffer is modified.
     *  
     *  最后一次调用toString方法的返回值缓存，当 StringBuffer 改变时，清除这个缓存
     */
    private transient char[] toStringCache;

    @Override
    public synchronized String toString() {
        if (toStringCache == null) {
            toStringCache = Arrays.copyOfRange(value, 0, count);
        }
        return new String(toStringCache, true);
    }
}
```

StringBuilder类的toString方法
```java
public final class StringBuilder
    extends AbstractStringBuilder
    implements java.io.Serializable, CharSequence
{
    @Override
    public String toString() {
        // Create a copy, don't share the array
        return new String(value, 0, count);
    }
}
```

String类源码
```java
public final class String
    implements java.io.Serializable, Comparable<String>, CharSequence {
    
    private final char value[];  
    
    /**
     *  StringBuilder类 toString的调用方法
     **/
    public String(char value[], int offset, int count) {
        if (offset < 0) {
            throw new StringIndexOutOfBoundsException(offset);
        }
        if (count <= 0) {
            if (count < 0) {
                throw new StringIndexOutOfBoundsException(count);
            }
            if (offset <= value.length) {
                this.value = "".value;
                return;
            }
        }
        // Note: offset or count might be near -1>>>1.
        if (offset > value.length - count) {
            throw new StringIndexOutOfBoundsException(offset + count);
        }
        this.value = Arrays.copyOfRange(value, offset, offset+count);
    }
    
    /**
     *  StringBuffer类 toString的调用方法
     **/
    String(char[] value, boolean share) {
        // assert share : "unshared not supported";
        this.value = value;
    }
}
```

如果字符串没有变化，在对StringBuffer重复调用toString方法时，减少了 Arrays.copyOfRange 复制操作。

这个缓存数组在字符串变化时，会把这个缓存数组置为null，重新调用toString方法时，会再次对缓存数组赋值。StringBuilder是线程不安全的，没有这个缓存数组。