# Java中的不可变对象
### 什么是不可变对象
> 不可变对象(Immutable Object)：对象一旦被创建后，对象所有的状态及属性在其生命周期内不会发生任何变化。

```java
/** 
*  ImmutableObject类，只提供了 getter 方法，没有提供 setter 方法，且成员变量 value 是基本数据类型。所以，一旦 ImmutableObject 实例化，就无法再对其进行修改。
*/ 
class ImmutableObject {
    private int values;
    public ImmutableObject(int values) {
        this.values = values;
    }
    public int getValues() {
        return this.values;
    }
}
```

常见的 String 对象，包装器对象都是不可变对象。

对 String 对象，进行一些操作函数处理，最终返回的是一个新的对象，原来的对象并没有发生改变。


### 不可变对象存在的意义

#### 1，支持并发编程

大多数情况下，对于资源互斥访问的场景，都是采用加锁的方式来实现对资源的串行访问，来保证并发安全，如synchronize关键字，Lock锁等。
但是这种方案最大的一个难点在于：在进行加锁和解锁时需要非常地慎重。如果加锁或者解锁时机稍有一点偏差，就可能会引发重大问题。

事实上，引起线程安全问题的根本原因在于：多个线程需要同时访问同一个共享资源。假如没有共享资源，那么多线程安全问题就自然解决了，Java中提供的ThreadLocal机制就是采取的这种思想。

不可变对象是一种在创建之后就不再变更的对象，多个线程间并发读取这个对象，无论何时总是能获取到一致的、完整的资源状态，这种特性使得它们天生支持线程安全。

#### 2，消除不必要的副作用

对一个对象的属性值进行判断校验的时候，可能会对这个对象的属性值进行修改，此时这个对象就不再是我们最初创建的那个了。

#### 3，减少容器使用过程出错的概率

我们在使用HashSet时，如果HashSet中元素对象的状态可变，就会出现元素丢失的情况。所以在Java中，对于String、包装器这些类，我们经常会用他们来作为HashMap的key。

### 如何创建不可变对象

通常来说，创建不可变类原则有以下几条：

1. 所有成员变量必须是private
2. 最好同时用final修饰(非必须)
3. 不提供能够修改原有对象状态的方法。
    最常见的方式是不提供setter方法；如果提供修改方法，需要新创建一个对象，并在新创建的对象上进行修改
4. 通过构造器初始化所有成员变量，引用类型的成员变量必须进行深拷贝(deep copy)
5. getter方法不能对外泄露this引用以及成员变量的引用
6. 最好不允许类被继承(非必须)


#### 不可变的集合

JDK 自带的方法，创建不可变集合
```text
Collections.unmodifiableList(list);
```
Google 的 Guava 包中提供方法
```text
ImmutableList.copyOf(list);
```

这两种虽然都是创建的不可变的集合，但是是有区别的。

JDK 提供的方法创建出来的集合不是真正意义上的不可变集合。


```text
// Collections.unmodifiableList 源码：
static class UnmodifiableList<E> extends UnmodifiableCollection<E>
                                  implements List<E> {
        private static final long serialVersionUID = -283967356065247728L;

        final List<? extends E> list;

        // 实际上UnmodifiableList是将入参list的引用复制了一份
        UnmodifiableList(List<? extends E> list) {
            super(list);
            this.list = list;
        }

        public boolean equals(Object o) {return o == this || list.equals(o);}
        public int hashCode()           {return list.hashCode();}

        public E get(int index) {return list.get(index);}
        //所有的修改方法抛出UnsupportedOperationException
        public E set(int index, E element) {
            throw new UnsupportedOperationException();
        }
        //所有的修改方法抛出UnsupportedOperationException
        public void add(int index, E element) {
            throw new UnsupportedOperationException();
        }
        //所有的修改方法抛出UnsupportedOperationException
        public E remove(int index) {
            throw new UnsupportedOperationException();
        }
}        
```

>实际上UnmodifiableList是将入参list的引用复制了一份，同时将所有的修改方法抛出UnsupportedOperationException。因此如果在外部修改了入参list，实际上会影响到UnmodifiableList，而Guava包提供的ImmutableList是真正意义上的不可变集合，它实际上是对入参list进行了深拷贝。

```java
public class ImmutableObjectTets {

    public static void main(String[] args) {
        // 不可变集合
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        System.out.println("list: "+list);
        // JDK 自带的不可变集合
        //List list2 = Arrays.asList(list.toArray());
        //List list3 = new ArrayList(list); // elementData = c.toArray();
        List unModifiableList = Collections.unmodifiableList(list);
        //List unModifiableList2 = Collections.unmodifiableList(list2);
        //List unModifiableList3 = Collections.unmodifiableList(list3);
    
        // 使用Google的Guava包中提供的 ImmutableList
        ImmutableList immutableList = ImmutableList.copyOf(list);
    
        list.add(2);
        System.out.println("unModifiableList: "+unModifiableList);
        //System.out.println("unModifiableList2: "+unModifiableList2);
        //System.out.println("unModifiableList3: "+unModifiableList3);
        System.out.println("immutableList: "+immutableList);
    }
}
```

### 不可变对象真的“不可变”吗？

> 不可变对象虽然具备不可变性，但是不是"完全不可变"的，这里打上引号是因为通过反射的手段是可以改变不可变对象的状态的。

如果真要靠通过反射来改变一个对象的状态，此时编写代码的人也应该会意识到此类在设计的时候就不希望其状态被更改。

```java
public class ImmutableObjectTets {

    public static void main(String[] args) {
        // 反射的方法 修改不可变对象
        try{
            ImmutableObject immutableObject1 = new ImmutableObject(20);
            Field objectFileds = ImmutableObject.class.getDeclaredField("values");
            // 如果setAccessible(false)【默认即为 false】，则 java 会对这个反射对象进行校验，并禁止对 “private”修饰的字段，进行修改
            // java.lang.IllegalAccessException: Class com.johann.javaProblem.ImmutableObjectTets can not access a member of class com.johann.javaProblem.ImmutableObject with modifiers "private"
            objectFileds.setAccessible(true);
            int v = objectFileds.getInt(immutableObject1);
            System.out.println(immutableObject1.toString() + "; v="+v);
            objectFileds.setInt(immutableObject1,22);
            System.out.println(immutableObject1.toString());
            
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
 }   
```

