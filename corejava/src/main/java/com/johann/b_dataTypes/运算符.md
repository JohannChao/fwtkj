Java运算符用于执行数学运算，操作变量，可以大致分为以下几类：
- 算术运算符
- 关系运算符
- 位运算符
- 逻辑运算符
- 赋值运算符
- 其他运算符

### 算术运算符

```text
+   加法 
-   减法 
*   乘法 
/   除法 【c = a/b （整数商  -7/4 = -1）】
%   取余 【r = a-b*c (余数 -7%4 = -3）】
++  自增 【a++和++a，两种写法，对变量a产生的影响都是，最终结果为a+1】 
--  自减 【a--和--a，两种写法，对变量a产生的影响都是，最终结果为a-1】 
```
```java
public class JavaOperatorTest {
    public static void main(String[] args) {
        //除法和取余运算
        int a = -7;
        int b = 4;
        int c = a/b; // c1 = -1
        int r = a%b; // r1 = -3
        System.out.println(c);
        System.out.println(r);

        //取模运算的商和结果
        int c1 = Math.floorDiv(a,b); // c2 = -2
        int r1 = Math.floorMod(a,b); // r2 = 1 
        System.out.println(c1);
        System.out.println(r1);

        // ++ --
        int m1 = 10;
        int m2 = 10;
        int n = m1++; //  m1 = 11 ; n = 10 先赋值，再自增
        //int n = ++m1; // m1 = 11 ; n = 11 先自增，再赋值
        System.out.println(m1);
        System.out.println(n);
        int l = m2--; // m2 = 9 ; l = 10 先赋值，再自减
        //int l = --m2; // m2 = 9 ; l = 9 先自减，再赋值
        System.out.println(m2);
        System.out.println(l);
    }
}
```

#### java中取模运算和取余运算的区别
```text
#### java中取模运算和取余运算的区别 ####

1，如果被除数（a）和除数（b）正负号相同时，此时取模运算和取余运算是等同的；

2，当被除数（a）和除数（b）正负号不同时，取余的结果与被除数（a）同号，取模的结果与除数（b）同号；

3，取模运算，在获取整数商的时候，商的取值向无穷小方向舍入。取余运算，在获取整数商的时候，商的取值向 0 方向舍入。
```

### 关系运算符

```text
'=='    是否相等
'!='    是否不相等
'>'     大于号比较
'<'     小于号比较          
'>='    大于等于号比较    
'<='    小于等于号比较    
```

### 位运算符

Java定义了位运算符，应用于整数类型(int)，长整型(long)，短整型(short)，字符型(char)，和字节型(byte)等类型。

位运算符是将数值转换为二进制格式的数值进行操作的，如果参与比较的两个数值二进制位数不同，则参与运算的数以补码方式出现。
```text
'&'     按位与，双目运算符。如果对应的位数都是1，则结果为1，否则为0
'|'     按位或，双目运算符。如果对应的位数都是0，则结果为0，否则为1
'^'     按位异或，双目运算符。如果对应的位数相异时，即不同时为1或0时，结果为1，否则为0
'~'     按位取反，单目运算符。对参与运算的的数，各个二进制位取反。
'<<'    按位左移，双目运算符。左移n位，就是乘以2的n次方。'<<<' 向左移动n位，超过该数值类型的最大位数时，高位舍弃，低位补0          
'>>'    按位右移，双目运算符。右移n位，就是除以2的n次方。'>>>' 向右移动n位，高位补位，低位舍弃。
```

```java
public class JavaOperatorTest {
    public static void main(String[] args) {
        int m = 50;
        int n = 15;
        String mBin = Integer.toBinaryString(m);// 0011 0010
        String nBin = Integer.toBinaryString(n);// 0000 1111
        System.out.println(m+"； 二进制："+mBin);
        System.out.println(n+"； 二进制："+nBin);
        System.out.println("##############################");

        //按位与
        int yu = m&n;
        System.out.println(m+"&"+n+" 按位与："+yu);// 2
        System.out.println(m+"&"+n+" 按位与二进制："+Integer.toBinaryString(yu));// 10
        System.out.println("##############################");

        //按位或
        int huo = m|n;
        System.out.println(m+"|"+n+" 按位或："+huo);// 63
        System.out.println(m+"|"+n+" 按位或二进制："+Integer.toBinaryString(huo)); // 111111
        System.out.println("##############################");

        //按位异或
        int yihuo = m^n;
        System.out.println(m+"^"+n+" 按位异或："+yihuo);// 61
        System.out.println(m+"^"+n+" 按位异或二进制："+Integer.toBinaryString(yihuo));// 111101
        System.out.println("##############################");

        //按位取反
        int mfan = ~m;
        int nfan = ~n;
        System.out.println(m+" 按位取反："+mfan);// -51
        System.out.println(m+" 按位取反二进制："+Integer.toBinaryString(mfan));// 11111111111111111111111111001101
        System.out.println(n+" 按位取反："+nfan);// -16
        System.out.println(n+" 按位取反二进制："+Integer.toBinaryString(nfan));//11111111111111111111111111110000
        System.out.println("##############################");

        //按位左移
        int mleft = m<<2;
        int nleft = n<<3;
        System.out.println(m+"<<2"+" 按位左移："+mleft);// 200
        System.out.println(m+"<<2"+" 按位左移二进制："+Integer.toBinaryString(mleft));// 11001000
        System.out.println(n+"<<3"+" 按位左移："+nleft);// 120
        System.out.println(n+"<<3"+" 按位左移二进制："+Integer.toBinaryString(nleft));// 1111000
        System.out.println("##############################");

        //按位右移
        int mright = m>>3;
        int nright = n>>2;
        System.out.println(m+">>3"+" 按位右移："+mright);// 6
        System.out.println(m+">>3"+" 按位右移二进制："+Integer.toBinaryString(mright));// 110
        System.out.println(n+">>2"+" 按位右移："+nright);// 3
        System.out.println(n+">>2"+" 按位右移二进制："+Integer.toBinaryString(nright));// 11        
    }
}
```

#### java 原码，反码，补码
原码：正数的原码，是该数值按绝对值大小转换为二进制数；

负数的原码，是该数值按绝对值大小转换为二进制数，最高位，即符号位补1。

反码：正数的反码，等于其原码；

负数的反码，对该数的原码，出符号位以外，各个位数取反（即1变成0，0变成1）。

补码：正数的补码等于其原码；

负数的补码，结果为 其反码+1。


### 逻辑运算符
```text
'&&'      逻辑与运算符，只有两边的的条件都为true时，结果才为ture。如果第一个条件为false，第二个条件就不再去执行判断步骤。又被称为短路与。
'||'      逻辑或运算符，只要两边的条件有一个为true，则结果为true。如果左边的第一个条件为true，则第二个条件不再去执行判断步骤。
'!'       逻辑非运算符，反转后面变量的逻辑状态，即true变为false，false变为true。
```
```java
public class JavaOperatorTest {
    public static void main(String[] args) {
        //逻辑与
        int m = 100;
        boolean yu1 = (false)&&(m++>=101);//第一个条件为false，第二个条件不执行
        System.out.println("逻辑与："+yu1);// false
        System.out.println("m = "+m);// 没有执行第二个条件，m仍为 100
        boolean yu2 = (true)&&(m++>=101);
        System.out.println("逻辑与："+yu2);// false
        System.out.println("m = "+m);// 第二个条件执行，由于m++是先用未自增前的m作比较，即100>=101，得出结果为false；然后m再自增，所以最终结果为false， m = 101

        //逻辑或
        int n = 100;
        boolean huo1 = (true)||(n++>=101);//第一个条件为true，第二个条件不执行
        System.out.println("逻辑或："+huo1);// true
        System.out.println("n = "+n);// 没有执行第二个条件，m仍为 100
        boolean huo2 = (false)||(n++<=100);
        System.out.println("逻辑或："+huo2);// true
        System.out.println("n = "+n);// 第二个条件执行，由于m++是先用未自增前的m作比较，即100<=100，得出结果为ftrue；然后m再自增，所以最终结果为true， m = 101

        //逻辑非
        boolean fei1 = !(true);// false
        boolean fei2 = !(false);// true
        System.out.println("fei1 : "+fei1+" --- fei2 : "+fei2);
    }
}
```

### 赋值运算符

java语言中的赋值运算符，除了一个新增的 '='赋值运算符，剩下的全部都是上面看到的运算符与赋值运算符组合起来，构成了一个新的赋值运算符。

```text
'='     赋值运算符，右侧变量的值赋值给左侧变量。
'+='    加赋值，左侧变量与右侧变量相加，再赋值给左侧变量。a += b -----> a = a + b.
'-='    减赋值，左侧变量与右侧变量相减，再赋值给左侧变量。a -= b -----> a = a - b.
'*='    乘赋值，左侧变量与右侧变量相乘，再赋值给左侧变量。a *= b -----> a = a * b;      
'/='    除赋值，左侧变量与右侧变量相除，再赋值给左侧变量。a /= b -----> a = a / b;
'%='    余赋值，左侧变量与右侧变量取余数，再赋值给左侧变量。a %= b -----> a = a % b;      
'<<='   左移位赋值，左侧变量向左移动n位，再赋值给左侧变量。 a <<= 2 -----> a = a<<2
'>>='   右移位赋值，左侧变量向右移动n位，再赋值给左侧变量。 a >>= 2 -----> a = a>>2
'&='    按位与赋值，左侧变量和右侧变量按位与运算符运算，得到的结果再赋值给左侧变量。  a &= b -----> a = a & b;
'|='    按位或赋值，左侧变量和右侧变量按位或运算符运算，得到的结果再赋值给左侧变量。  a |= b -----> a = a | b;
'^='    按位异或赋值，左侧变量和右侧变量按位异或运算符运算，得到的结果再赋值给左侧变量。a ^= b -----> a = a ^ b;
```
```java
public class JavaOperatorTest {
    public static void main(String[] args) {
        int m = 50;
        int n = 15;
        int oldm = 0;
        
        oldm = m;
        m += n;
        System.out.println(oldm+" += "+n+" -----> "+m);

        oldm = m;
        m -= n;
        System.out.println(oldm+" -= "+n+" -----> "+m);

        oldm = m;
        m *= n;
        System.out.println(oldm+" *= "+n+" -----> "+m);

        oldm = m;
        m /= n;
        System.out.println(oldm+" /= "+n+" -----> "+m);

        oldm = m;
        m %= n;
        System.out.println(oldm+" %= "+n+" -----> "+m);

        oldm = m;
        m <<= n;
        System.out.println(oldm+" <<= "+n+" -----> "+m);

        oldm = m;
        m >>= n;
        System.out.println(oldm+" >>= "+n+" -----> "+m);

        System.out.println(m+"； 二进制："+Integer.toBinaryString(m));
        System.out.println(n+"； 二进制："+Integer.toBinaryString(n));

        System.out.println("///////////////////////////////////////////////");
        oldm = m;
        m &= n;
        System.out.println(oldm+" &= "+n+" -----> "+m);
        System.out.println(m+"； 二进制："+Integer.toBinaryString(m));
        System.out.println(n+"； 二进制："+Integer.toBinaryString(n));

        System.out.println("///////////////////////////////////////////////");
        oldm = m;
        m |= n;
        System.out.println(oldm+" |= "+n+" -----> "+m);
        System.out.println(m+"； 二进制："+Integer.toBinaryString(m));
        System.out.println(n+"； 二进制："+Integer.toBinaryString(n));

        System.out.println("///////////////////////////////////////////////");
        oldm = m;
        m ^= n;
        System.out.println(oldm+" ^= "+n+" -----> "+m);
        System.out.println(m+"； 二进制："+Integer.toBinaryString(m));
        System.out.println(n+"； 二进制："+Integer.toBinaryString(n));
    }
}
```

### 其他运算符

```text
1，'? :'    三目运算符。格式为：变量m = 条件a ? 变量或表达式b : 变量或表达式c
如果条件a 为true，则变量m 赋值为 变量或表达式b，否则赋值为 变量或表达式c。

2，'instanceof'    instanceof 运算符。格式为：(Object reference variable) instanceof  (class/interface type)
该运算符用于操作对象实例，检查该对象是否是一个特定类型（类类型或接口类型）。
```

### Java中各类运算符的优先等级

类别 | 操作符 |关联性
---|--- | --- 
后缀|	    () [] . (点操作符)	   |         左到右
一元|	    +(正号) -(负号) !(逻辑非) ~(按位取反)	           |         从右到左
乘性| 	    * / %               |         左到右
加性| 	    + -	                   |         左到右
移位| 	    >>(位右移) >>>(位右移补零)  <<(左移) 	           |         左到右
关系| 	    >(大于) >=(不小于) <(小于) <=(不大于) 	           |         左到右
相等| 	    ==(相等)  !=(不相等)	               |         左到右
按位与|	    &                   |         左到右
按位异或|	^	                   |         左到右
按位或|	    &#124;	                   |         左到右
逻辑与|	    &&	                   |         左到右
逻辑或|	    &#124;&#124;	                   |         左到右
条件|	    ？：	               |         从右到左
赋值|	    =(A) +=(B) -=(C) *=(D) /=(E) %=(F) >>=(G) <<=(H) &=(I) ^=(J) &#124;=(K)	| 从右到左
逗号|	    ,	                   |         左到右