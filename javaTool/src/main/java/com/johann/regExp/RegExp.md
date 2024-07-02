### 正则表达式基础
##### 字符
* `[abc]`: 字符集，匹配集合中所含的任一字符
* `[^abc]`: 否定字符集，匹配除了集合中所含的任一字符之外的所有字符
* `[a-zA-Z]`: 字符范围，匹配指定范围内的任意字符
* ` . `: 匹配除了换行符以外的任意字符
* `\`: 转义字符，表示特殊字符，如` \n `表示换行符，` \t `表示制表符，`\r`表示回车符，`\f`表示换页符，`\v`表示垂直制表符
* `\w`: 匹配数字、字母、下划线。（等价于`[A-Za-z0-9_]`）
* `\W`: 匹配除了数字、字母、下划线以外的任意字符（等价于`[^A-Za-z0-9_]`）
* `\d`: 匹配任何数字（等价于`[0-9]`）
* `\D`: 匹配除了数字以外的任意字符（等价于`[^0-9]`）
* `\s`: 匹配空白字符，包括空格、制表符、换行符等
* `\S`: 匹配非空白字符，等价于`[^ \t\n\r\f\v]`

##### 分组和引用
* `()`: 分组，匹配括号里的整个表达式【圆括号()用于创建分组，这允许我们将多个元素视为一个单一的单元，对这个单元应用量词（如*, +, ?, {n}等）】。
将正则表达式分组，可以对分组进行引用，如`(\d{3})-(\d{3,8})`，其中`(\d{3})`表示匹配3位数字，`(\d{3,8})`表示匹配3到8位数字
* `(?:expression)`: 非捕获分组，匹配括号里的整个表达式，但不捕获分组的内容
* `\num`: 对前面所匹配分组的引用，比如`(\d)\1`可以匹配两个相同的数字，`(Code)(Sheep)\1\2`则可以匹配`CodeSheepCodeSheep`。

##### 锚点、边界
* `^`: 匹配字符串或行开头。
* `$`: 匹配字符串或行结尾。
* `\b`: 匹配单词边界。比如`Sheep\b`可以匹配`CodeSheep`末尾的`Sheep`，不能匹配`CodeSheepCode中的Sheep`
* `\B`: 匹配非单词边界。比如`Code\B`可以匹配`HelloCodeSheep`中的`Code`，不能匹配`HelloCode`中的`Code`

##### 数量表示
* `?`: 匹配前面的表达式0个或1个。
* `*`: 匹配前面的表达式0个或多个。
* `+`: 匹配前面的表达式至少1个。
* `|`: 或运算符。并集，可以匹配符号前后的表达式。
* `{m}`: 匹配前面的表达式m个。
* `{m,}`: 匹配前面的表达式最少m个。
* `{m,n}`: 匹配前面的表达式最少m个，最多n个。

##### 预查断言
* `(?=expression)`: 正向预查。比如`Code(?=Sheep)`能匹配`CodeSheep`中的`Code`，但不能匹配`CodePig`中的`Code`。
* `(?!=expression)`: 正向否定预查。比如`Code(?!Sheep)`不能匹配`CodeSheep`中的`Code`，但能匹配`CodePig`中的`Code`。
* `(?<=expression)`: 反向预查。比如`(?<=Code)Sheep`能匹配`CodeSheep`中的`Sheep`，但不能匹配`ReadSheep`中的`Sheep`。
* `(?<!expression)`: 反向否定预查。比如`(?<!Code)Sheep`不能匹配`CodeSheep`中的`Sheep`，但能匹配`ReadSheep`中的`Sheep`。

##### 特殊标志
* `/.../i`: 忽略大小写。
* `/.../g`: 全局搜索，匹配所有符合条件的内容。
* `/.../m`: 多行匹配。