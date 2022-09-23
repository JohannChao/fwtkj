# 数据库技术
### SQL语言
关系型数据库中专门提供了一种对数据库进行操作和查询的语言，叫作结构化查询语言，英文为Structured Query Language，简称SQL。

##### SQL语言分类
* DDL（Data Definition Language，数据定义语言）：用于定义数据库、数据表和列，可以用来创建、删除、修改数据库和数据表的结构，包含CREATE、DROP和ALTER等语句。
* DML（Data Manipulation Language，数据操作语言）：用于操作数据记录，可以对数据库中数据表的数据记录进行增加、删除和修改等操作，包含INSERT、DELETE和UPDATE等语句。
* DCL（Data Control Language，数据控制语言）：用于定义数据库的访问权限和安全级别，主要包含GRANT、REVOKE、COMMIT和ROLLBACK等语句。
* DQL（Data Query Language，数据查询语言）：用于查询数据表中的数据记录，主要包含SELECT语句。

### SQL执行流程
1. 客户端发送一条SQL语句给MySQL服务器。
2. MySQL服务器先检查查询缓存，如果查询缓存中存在待查询的结果数据，则会立刻返回查询缓存中的结果数据，否则执行下一阶段的处理。
3. MySQL服务器通过解析器和预处理器对SQL语句进行解析和预处理，并将生成的SQL语句解析树传递给查询优化器。
4. 查询优化器将SQL解析树进行进一步处理，生成对应的执行计划。
5. MySQL服务器根据查询优化器生成的执行计划，通过查询执行引擎调用存储引擎的API来执行查询操作。
6. 存储引擎查询数据库中的数据，并将结果返回给查询执行引擎。
7. 查询执行引擎将结果保存在查询缓存中，并通过数据库连接/线程处理返回给客户端。

* 查询缓存：MySQL内部的缓存区域，保存查询返回的完整结构，当查询命中缓存时，MySQL会立刻返回结果数据，不再执行后续的解析、优化和执行操作。
* 解析器：能够通过SQL关键字和SQL语法规则对SQL语句进行解析，并生成对应的SQL语句解析树。
* 预处理器：根据MySQL的相关规则，对解析器生成的SQL语句解析树进行进一步校验，比如检查数据库中的数据表是否存在，数据表中的数据列是否存在。再比如，如果SQL语句中使用了别名，还会对别名进行校验，检查别名是否存在重名和歧义等。
* 查询优化器：根据一定的规则将SQL语句解析树转化成查询性能最好的执行计划。
* 查询执行引擎：根据查询优化器生成的执行计划完成整个数据查询的过程。

### 三大范式
1. 第一范式：确保数据表中每个字段的值必须具有原子性，也就是说数据表中每个字段的值为不可再次拆分的最小数据单元。
2. 第二范式：第二范式是指在第一范式的基础上，确保数据表中除了主键之外的每个字段都必须依赖主键。
3. 第三范式：第三范式是在第二范式的基础上，确保数据表中的每一列都和主键字段直接相关，也就是说，要求数据表中的所有非主键字段不能依赖于其他非主键字段。
4. 反范式化：严格按照三大范式设计的数据表，在读取数据的时候，会产生大量的关联查询，在一定程度上影响数据库的读性能，可以通过在数据表中增加冗余字段来提高数据库的读性能。

### MySQL的存储引擎
存储引擎在MySQL底层以组件的形式提供，不同的存储引擎提供的存储机制、索引的存放方式和锁粒度等不同。

查看存储引擎命令
```sql 
--连接MySQL数据库
mysql -u root -p

--查看所有存储引擎
show engines\G
```
##### 1，InnoDB存储引擎
InnoDB存储引擎的特点如下：
* 支持事务。
* 锁级别为行锁，比MyISAM存储引擎支持更高的并发。
* 能够通过二进制日志恢复数据。
* 支持外键操作。
* 在索引存储上，索引和数据存储在同一个文件中，默认按照B+Tree组织索引的结构。同时，主键索引的叶子节点存储完整的数据记录，非主键索引的叶子节点存储主键的值。
* 在MySQL 5.6版本之后，默认使用InnoDB存储引擎。
* 在MySQL 5.6版本之后，InnoDB存储引擎支持全文索引。

##### 2，MyISAM存储引擎
MyISAM存储引擎的特点如下：
* 不支持事务。
* 锁级别为表锁，在要求高并发的场景下不太适用。
* 如果数据文件损坏，难以恢复数据。
* 不支持外键。
* 在索引存储上，索引文件与数据文件分离。
* 支持全文索引。

##### 3，MEMORY存储引擎
MEMORY存储引擎的特点如下：
* 不支持TEXT和BLOB数据类型，只支持固定长度的字符串类型。例如，在MEMORY存储引擎中，会将VARCHAR类型自动转化成CHAR类型。
* 锁级别为表锁，在高并发场景下会成为瓶颈。
* 通常会被作为临时表使用，存储查询数据时产生中间结果。
* 数据存储在内存中，重启服务器后数据会丢失。如果是需要持久化的数据，不适合存储在MEMORY存储引擎的数据表中。

##### 4，ARCHIVE存储引擎
ARCHIVE存储引擎的特点如下：
* 支持数据压缩，在存储数据前会对数据进行压缩处理，适合存储归档的数据。
* 只支持数据的插入和查询，插入数据后，不能对数据进行更改和删除，而只能查询。
* 只支持在整数自增类型的字段上添加索引。

##### 5，CSV存储引擎
CSV存储引擎的特点如下：
* 主要存储的是.csv格式的文本数据，可以直接打开存储的文件进行编辑。
* 可以将MySQL中某个数据表中的数据直接导出为.csv文件，也可以将.csv文件导入数据表中。

##### 6，其他存储引擎
https://dev.mysql.com/doc/refman/8.0/en/storage-engines.html

# MySQL开发

### 操作数据库

##### 1，创建数据库
```sql
--查看当前数据库
SHOW DATABASES;

--创建数据库
CREATE DATABASE database_name;

--如果MySQL中不存在相关的数据库，则创建数据库
CREATE DATABASE IF NOT EXISTS database_name;
```

##### 2，查看数据库
```sql
--查看当前数据库
SHOW DATABASES;

--查看当前命令行所在的数据库
SELECT DATABASE();

--查看数据库的创建信息
SHOW CREATE DATABASE database_name;
SHOW CREATE DATABASE database_name \G
```

##### 3，修改数据库名称
```sql
/**MySQL 5.1.7版本中提供了修改数据库名称的SQL语句
但是从MySQL 5.1.23版本之后，就将此SQL语句去掉了，原因是此SQL语句可能会造成数据丢失。*/
RENAME DATABASE db_name TO new_db_name;

/**通过其他方式达到修改MySQL数据库名称的效果 */

/**3.1，通过重命名数据表修改数据库名称 */
1）创建数据库，和数据表
CREATE DATABASE IF NOT EXISTS test_old;
USE test_old;
CREATE TABLE IF NOT EXISTS table_test(id int);
2）创建新的库
CREATE DATABASE IF NOT EXISTS test_new;
3）重命名数据库表，将test_old数据库下的数据表重命名到test_new数据库下。
RENAME TABLE test_old.table_test TO test_new.table_test;
4）删除旧库
USE test_new;
DROP DATABASE IF EXISTS test_old;
5）查看当前所有的数据库，查看新库下的表
SHOW DATABASES;
SHOW TABLES;

/**3.2，通过创建数据表修改数据库名称 */
1）创建数据库，和数据表
CREATE DATABASE IF NOT EXISTS test_old;
USE test_old;
CREATE TABLE IF NOT EXISTS table_test(id int);
2）创建新的库
CREATE DATABASE IF NOT EXISTS test_new;
3）在test_new数据库中创建table_test数据表，使其按照test_old数据库中的数据表进行创建。
CREATE TABLE IF NOT EXISTS test_new.table_test LIKE test_old.table_test;
4）删除旧库
USE test_new;
DROP DATABASE test_old;
5）查看当前所有的数据库，查看新库下的表
SHOW DATABASES;
SHOW TABLES;
```

##### 4，数据库编码
```sql
/**创建数据库的时候，如果没有指定字符编码，则默认使用MySQL配置文件中的默认字符编码 */
[client]
default-character-set = utf8mb4
[mysqld]
character_set_server = utf8mb4
[mysql]
default-character-set=utf8mb4

/**1，创建数据库时，指定字符编码 */
mysql> CREATE DATABASE [IF NOT EXISTS] database_name DEFAULT CHARACTER SET character_name COLLATE collate_name [DEFAULT ENCRYPTION='N'];
mysql> CREATE DATABASE IF NOT EXISTS test_character DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT ENCRYPTION='N';

/**2，查看数据库默认字符编码 */
mysql> SHOW VARIABLES LIKE '%character_set_database%';

/**3，修改数据库的字符编码 */
mysql> ALTER DATABASE database_name CHARACTER SET character_name collate collate_name;
mysql> ALTER DATABASE test_character CHARACTER SET utf8mb4 collate utf8mb4_0900_ai_ci;             
```

##### 4，删除数据库
```sql
DROP DATABASE IF EXISTS database_name;
```

### 操作数据表

##### 1，创建数据表
```sql
/**1，创建数据表语法模板 */
CREATE TABLE IF NOT EXISTS 表名(
    字段1, 数据类型 [约束条件] [默认值],
    字段2, 数据类型 [约束条件] [默认值],
    字段3, 数据类型 [约束条件] [默认值],
    ……
    [表约束条件]
);

/**表名称在Windows操作系统上不区分大小写，在Linux操作系统上区分大小写。
如果需要在Linux操作系统上不区分大小写，则需要在MySQL的配置文件my.cnf中添加一项配置。 */
lower_case_table_names=1

/**2，创建数据表时，指定主键 */
/**2.1，定义列的同时，指定主键 */
字段 数据类型 PRIMARY KEY [默认值]  --语法格式
mysql> CREATE TABLE t_goods_category2 (
    id INT PRIMARY KEY,
    t_category VARCHAR(30),
    t_remark VARCHAR(100)
);
/**2.2，定义完数据表的列之后，指定主键 */
/**MySQL中除了支持单列主键之外，还支持多个字段共同组成MySQL数据表的主键，也就是多列联合主键。
多列联合主键只能在定义完数据表的所有列之后进行指定。 */
[CONSTRAINT 约束条件名] PRIMARY KEY [字段名]  --语法格式
PRIMARY KEY [字段1, 字段2, 字段3…., 字段n]  --语法格式
mysql> CREATE TABLE t_goods_category4 (
    t_category_id INT,
    t_shop_id INT,
    t_category VARCHAR(30),
    t_remark VARCHAR(100),
    PRIMARY KEY (t_category_id, t_shop_id)  --多列联合主键
);

/**3，创建数据表时，指定外键 */
/**由外键引申出两个概念，分别是主表（父表）和从表（子表）。
·主表（父表）：两个表具有关联关系时，关联字段中主键所在的表为主表（父表）。
·从表（子表）：两个表具有关联关系时，关联字段中外键所在的表为从表（子表）。 */
[CONSTRAINT 外键名] FOREIGN KEY 字段1 [, 字段2, 字段3, …] REFERENCES 主表名 主键列1 [, 主键列2, 主键列3, …]  --语法格式
mysql> CREATE TABLE t_goods_category (
    id INT PRIMARY KEY,
    t_category VARCHAR(30),
    t_remark VARCHAR(100)
);
--一张表的外键与其关联的另一张表的主键的数据类型必须相同。
mysql> CREATE TABLE t_goods(
    id INT PRIMARY KEY,
    t_category_id INT,
    t_category VARCHAR(30),
    t_name VARCHAR(50),
    t_price DECIMAL(10,2),
    t_stock INT,
    t_upper_time DATETIME,
    CONSTRAINT foreign_category FOREIGN KEY(t_category_id) REFERENCES t_goods_category(id)
);

/**4，创建数据表时，指定字段非空 */
字段名称 数据类型 NOT NULL  --语法格式
mysql> CREATE TABLE t_goods_category5(
    id INT PRIMARY KEY,
    t_category VARCHAR(30) NOT NULL,
    t_remark VARCHAR(100)
);

/**5，创建数据表时，指定默认值 */
字段名称 数据类型 DEFAULT 默认值  --语法格式
mysql> CREATE TABLE t_goods_category9(
    t_category_id INT,
    t_shop_id INT DEFAULT 1,
    t_category VARCHAR(30) NOT NULL,
    t_remark VARCHAR(100)
);

/**6，创建数据表时，指定主键默认递增 */
字段名称 数据类型 AUTO_INCREMENT  --语法格式
mysql> CREATE TABLE t_goods_category10(           
    id INT PRIMARY KEY AUTO_INCREMENT,        
    t_category VARCHAR(30),       
    t_remark VARCHAR(100)
);

/**7，创建数据表时，指定存储引擎 */
ENGINE=存储引擎名称  --语法格式
mysql> CREATE TABLE t_goods_category11(
    id INT PRIMARY KEY AUTO_INCREMENT,
    t_category VARCHAR(30), 
    t_remark VARCHAR(100)
)ENGINE=InnoDB;

/**8，创建数据表时，指定编码 */
DEFAULT CHARACTER SET 编码 COLLATE 校对规则  --语法格式
DEFAULT CHARSET=编码 COLLATE=校对规则  --语法格式
mysql> CREATE TABLE t_goods_category12(
    id INT NOT NULL AUTO_INCREMENT,
    t_category VARCHAR(30),
    t_remark VARCHAR(100),
    PRIMARY KEY(id)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
```

##### 2，查看数据表结构
```sql
/**使用DESCRIBE/DESC语句查看表结构 */
DESCRIBE table_name;
DESCRIBE table_name\G
DESC table_name;
DESC table_name\G

/**使用SHOW CREATE TABLE语句查看表结构 */
SHOW CREATE TABLE table_name\G
```

##### 3，修改数据表
```sql
/**1，修改数据表名称 */
ALTER TABLE 原表名 RENAME TO 新表名  --语法格式
mysql> ALTER TABLE t_goods_backup RENAME TO t_goods_tmp;

/**2，添加字段 */
ALTER TABLE 表名 ADD COLUMN 新字段名 数据类型 [NOT NULL DEFAULT 默认值] [FIRST|AFTER 原有字段]  --语法格式
/**2.1，默认在最后一列添加字段 */
mysql> ALTER TABLE t_goods_tmp ADD COLUMN t_create_time DATETIME DEFAULT NULL;
/**2.2，在指定字段的后面添加字段 */
mysql> ALTER TABLE t_goods_tmp ADD COLUMN t_create_time DATETIME DEFAULT NULL FIRST;
/**2.3，在指定字段的后面添加字段*/
mysql> ALTER TABLE t_goods_tmp ADD COLUMN t_create_time DATETIME DEFAULT NULL AFTER ID;

/**3，修改字段名称 */
ALTER TABLE 表名 CHANGE 原有字段名 新字段名 新数据类型  --语法格式
mysql> ALTER TABLE t_goods_tmp CHANGE t_update_time t_last_modified DATETIME;

/**4，修改字段的数据类型 */
ALTER TABLE 表名 MODIFY 字段名 新数据类型 [DEFAULT 默认值]  --语法格式
mysql> ALTER TABLE t_goods_tmp MODIFY t_price BIGINT DEFAULT 0;

/**5，修改字段的位置 */
ALTER TABLE 表名 MIDIFY 字段名 数据类型 [FIRST|AFTER 字段2名称]  --语法格式
/**5.1，将字段的位置修改为数据库的第一个字段 */
mysql> ALTER TABLE t_goods_tmp MODIFY id int(11) NOT NULL FIRST;
/**5.2，将当前字段的位置修改到某个字段的后面 */
mysql> ALTER TABLE t_goods_tmp MODIFY t_last_modified datetime DEFAULT NULL AFTER t_create_time;

/**6，删除字段 */
ALTER TABLE 表名 DROP 字段名  --语法格式
mysql> ALTER TABLE t_goods_tmp DROP t_area;

/**7，修改已有表的存储引擎 */
ALTER TABLE 表名 ENGINE=存储引擎名称  --语法格式
mysql> ALTER TABLE t_goods_tmp ENGINE=MyISAM;

/**8，取消数据表的外键约束 */
ALTER TABLE 表名 DROP FOREIGN KEY 外键名  --语法格式
mysql> ALTER TABLE t_goods DROP FOREIGN KEY `t_goods_ibfk_1`;
```

##### 4，删除数据表
```sql
/**1，删除没有关联关系的数据表 */
DROP TABLE IF EXISTS 数据表1 [, 数据表2, …, 数据表n]  --语法格式
mysql> DROP TABLE t_goods_snapshot;

/**2，删除有外键约束的主表 */
2.1，先删除从表，再删除主表；
2.2，先解除外键约束，再删除主表。
```

##### 5，MySQL中的临时表
```sql
/**1，创建临时表 */
CREATE TEMPORARY TABLE [IF NOT EXISTS] 表名  --语法格式
mysql> CREATE TEMPORARY TABLE t_temporary_category (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    t_name VARCHAR(30)
);
/**在MySQL中，使用CREATE TEMPORARY TABLE语句创建临时表后，使用SHOW TABLES语句是无法查看到临时表的。
此时可以通过DESCRIBE/DESC或者SHOW CREATE TABLE语句查看临时表的表结构，来确定临时数据表是否创建成功。 */

/**2，删除临时表 */
DROP TABLE IF EXISTS 表名  --语法格式
mysql> DROP TABLE IF EXISTS t_temporary_category;
```

### MySQL数据类型
MySQL支持丰富的数据类型，总体上可以分为数值类型、日期和时间类型、字符串类型。数值类型包括整数类型、浮点数类型和定点数类型；字符串类型包括文本字符串类型和二进制字符串类型。

#### 1，数值类型
MySQL中的数值类型包括整数类型、浮点数类型和定点数类型。

##### 1.1，整数类型
* MySQL中的整数类型包括TINYINT、SMALLINT、MEDIUMINT、INT(INTEGER)和BIGINT。不同的整数类型，其所需要的存储空间和数值范围不尽相同。

| 整数类型      | 类型名称     | 存储空间   | 最大值                   |
| :----------- | :----------- | :-------- | :------------------------ |
| TINYINT      | 非常小的整数  | 1字节     | -128<->127               |
| SMALLINT     | 小整数        | 2字节     | -32768<->32767           |
| MEDIUMINT    | 中型大小整数  | 3字节     | -8300608<->8300607       |
| INT(INTEGER) | 一般整数      | 4字节     | -2147483648<->2147483647 |
| BIGINT       | 很大的整数    | 8字节     | -2^63 <-> 2^63-1 |

```sql
/**1，执行建表语句时，如果没有指定int类型的显示宽度，则默认的显示宽度是11，即 int(11) */
mysql> CREATE TABLE t1(id INT); 
mysql> SHOW CREATE TABLE t1 \G
*************************** 1. row ***************************
       Table: t1
Create Table: CREATE TABLE `t1` (
  `id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
1 row in set (0.09 sec)

/**2，整数类型的显示宽度与数据类型的取值范围无关。显示宽度只是指定最大显示的数字个数，如果插入的数值大于显示宽度，但是没有超过整数类型的取值范围，则依然可以正确插入数据。
整数类型的显示宽度能够配合ZEROFILL使用。ZEROFILL表示在数字的显示位数不够时，可以用字符0进行填充。 */
mysql> CREATE TABLE t3(id1 INT ZEROFILL, id2 INT(6) ZEROFILL);
mysql> INSERT INTO t3(id1, id2) VALUES(1, 1),(111111,111111);
mysql> select * from t3;
+------------+--------+
| id1        | id2    |
+------------+--------+
| 00000000001 | 000001 |
| 00000111111 | 111111 |
+------------+--------+

/**3，所有的整数类型都有一个可选的属性 UNSIGNED（无符号属性），无符号整数类型的最小取值为0，所以，如果需要在MySQL数据库中保存非负整数值时，可以将整数类型设置为无符号类型。 
特别地，如果在MySQL中创建数据表时，指定数据字段为ZEROFILL，则MySQL会自动为当前列添加UNSIGNED属性。*/
mysql> show create table t3\G
*************************** 1. row ***************************
       Table: t3
Create Table: CREATE TABLE `t3` (
  `id1` int(10) unsigned zerofill DEFAULT NULL,
  `id2` int(6) unsigned zerofill DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
--建表语句指定整数的无符号属性
mysql> create table t3_1(id int unsigned,countNum int(6) unsigned);

/**4，整数类型还有一个属性是AUTO_INCREMENT。
一个表中最多只能有一个列被设置为AUTO_INCREMENT。设置为AUTO_INCREMENT的列需要定义为NOT NULL，并且定义为PRIMARY KEY，或者定义为NOT NULL并且定义为UNIQUE。 */
mysql> CREATE TABLE t4(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, age int);
mysql> CREATE TABLE t5 (id INT NOT NULL AUTO_INCREMENT UNIQUE, age int);
```

##### 1.2，浮点数类型
* 浮点数类型主要有两种：单精度浮点数FLOAT和双精度浮点数DOUBLE。

| 浮点数类型 | 类型名称     | 存储空间 |
| ---------- | ------------ | -------- |
| FLOAT      | 单精度浮点数 | 4个字节  |
| DOUBLE     | 双精度浮点数 | 8个字节  |

```sql
/**1，浮点数类型未指定数据精度 */
mysql> CREATE TABLE t6 (f FLOAT, d DOUBLE);
--查看数据表结构
mysql> show create table t6\G
*************************** 1. row ***************************
       Table: t6
Create Table: CREATE TABLE `t6` (
  `f` float DEFAULT NULL,
  `d` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
--插入数据
mysql> insert into t6(f,d) values (3.14,5.98),(3.0123456789,5.01234567890123456789);
mysql> select * from t6;
+---------+-------------------+
| f       | d                 |
+---------+-------------------+
|    3.14 |              5.98 |
| 3.01235 | 5.012345678901235 |
+---------+-------------------+
/**浮点数类型中的FLOAT和DOUBLE类型在不指定数据精度时，默认会按照实际的计算机硬件和操作系统决定的数据精度进行显示。如果用户指定的精度超出了浮点数类型的数据精度，则MySQL会自动进行四舍五入操作。 */

/**2，浮点数类型指定数据精度 */
/**浮点数的数据精度可以使用(M,D)的方式进行表示。
(M,D)表示当前数值包含整数位和小数位一共会显示M位数字，其中，小数点后会显示D位数字。
M又被称为精度，D又被称为标度。
当指定一个浮点数数的数据精度为(M,D)时，整数位最多有 M-D位数字，超过这个范围的数据在插入时，报错“Out of range value”。插入数据的小数位可以是任意的，只不过最终会被截取到 D位，小数点后面的位数不足 D位，会进行补零 */
--建表语句，f字段整数部分最多 3位数，d字段整数部分最多 8位数。
mysql> CREATE TABLE t7 (f FLOAT(5,2),d DOUBLE(10,2));
--查看数据表结构
mysql> show create table t7\G
*************************** 1. row ***************************
       Table: t7
Create Table: CREATE TABLE `t7` (
  `f` float(5,2) DEFAULT NULL,
  `d` double(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
--插入数据（整数位超出限制）
mysql> insert into t7(f,d) values (1234,123456789);
ERROR 1264 (22003): Out of range value for column 'f' at row 1
mysql> insert into t7(f,d) values (123,123456789);
ERROR 1264 (22003): Out of range value for column 'd' at row 1
--正常插入数据
mysql> insert into t7(f,d) values (123,12345678);
mysql> select * from t7;
+--------+-------------+
| f      | d           |
+--------+-------------+
| 123.00 | 12345678.00 |
+--------+-------------+
mysql> insert into t7(f,d) values (123.123456,12345678.123456);
mysql> select * from t7;
+--------+-------------+
| f      | d           |
+--------+-------------+
| 123.00 | 12345678.00 |
| 123.12 | 12345678.12 |
+--------+-------------+
```

##### 1.3，定点数类型

* MySQL中的定点数类型只有DECIMAL一种类型。DECIMAL类型也可以使用(M,D)进行表示，其中，M被称为精度，是数据的总位数；D被称为标度，表示数据的小数部分所占的位数。整数位最多 M-D 位。
* 定点数在MySQL内部是以字符串的形式进行存储的，它的精度比浮点数更加精确，适合存储表示金额等需要高精度的数据。
* DECIMAL(M,D)类型的数据的最大取值范围与DOUBLE类型一样，但是有效的数据范围是由M和D决定的。
* DECIMAL的存储空间并不是固定的，由精度值M决定，总共占用的存储空间为M+2个字节。
* 当DECIMAL类型不指定精度和标度时，其默认为DECIMAL(10,0)。
```sql
--建表语句
mysql> create table t8(d1 DECIMAL,d2 DECIMAL(5,2),d3 DECIMAL(12,2));
Query OK, 0 rows affected (0.19 sec)
--查看表结构
mysql> show create table t8\G
*************************** 1. row ***************************
       Table: t8
Create Table: CREATE TABLE `t8` (
  `d1` decimal(10,0) DEFAULT NULL,
  `d2` decimal(5,2) DEFAULT NULL,
  `d3` decimal(12,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
--插入数据
mysql> insert into t8(d1,d2,d3) values (3.1415926,3.1415926,3.1415926), (1234567890.123,123.456,1234567890.123);
mysql> select * from t8;
+------------+--------+---------------+
| d1         | d2     | d3            |
+------------+--------+---------------+
|          3 |   3.14 |          3.14 |
| 1234567890 | 123.46 | 1234567890.12 |
+------------+--------+---------------+
```

##### 1.4，浮点数类型和定点数类型对比
* 浮点数类型中的FLOAT类型和DOUBLE类型在不指定精度时，默认会按照计算机硬件和操作系统决定的精度进行表示；而定点数类型中的DECIMAL类型不指定精度时，默认为DECIMAL(10,0)。
* 当数据类型的长度一定时，浮点数能够表示的数据范围更大，但是浮点数会引起精度问题，不适合存储高精度类型的数据。

#### 2，日期和时间类型

MySQL提供了表示日期和时间的数据类型，主要有YEAR类型、TIME类型、DATE类型、DATETIME类型和TIMESTAMP类型。
* DATE类型通常用来表示年月日；
* DATETIME类型通常用来表示年、月、日、时、分、秒；
* TIME类型通常用来表示时、分、秒。

| 日期/时间类型 | 类型名称 | 存储空间 | 日期格式            | 零值表示            | 最小值                  | 最大值                  |
| ------------- | -------- | -------- | ------------------- | ------------------- | ----------------------- | ----------------------- |
| YEAR          | 年       | 1个字节  | YYYY                | 0000                | 1901                    | 2155                    |
| TIME          | 时间     | 3个字节  | HH:MM:SS            | 00:00:00            | -838:59:59              | 838:59:59               |
| DATE          | 日期     | 3个字节  | YYYY-MM-DD          | 0000-00-00          | 1000-01-01              | 9999-12-03              |
| DATETIME      | 日期时间 | 8个字节  | YYYY-MM-DD HH:MM:SS | 0000-00-00 00:00:00 | 1000-01-01 00:00:00     | 9999-12-31 23:59:59     |
| TIMESTAMP     | 日期时间 | 4个字节  | YYYY-MM-DD HH:MM:SS | 0000-00-00 00:00:00 | 1970-01-01 00:00:01 UTC | 2038-01-19 03:14:07 UTC |

##### 2.1，YEAR类型
YEAR类型用来表示年份，在所有的日期时间类型中所占用的存储空间最小，只需要1个字节的存储空间。

在MySQL中，YEAR有以下几种存储格式：
* 以4位字符串或数字格式表示YEAR类型，其格式为YYYY，最小值为1901，最大值为2155。
* 以2位字符串格式表示YEAR类型，最小值为00，最大值为99。其中，当取值为00到69时，表示2000到2069；当取值为70到99时，表示1970到1999。如果插入的数据超出了取值范围，则MySQL会将值自动转换为2000。
* 以2位数字格式表示YEAR类型，最小值为1，最大值为99。其中，当取值为1到69时，表示2001到2069，当取值为70到99时，表示1970到1999。
* 注意：当使用两位数字格式表示YEAR类型时，数值0将被转化为0000。
```sql
--建表语句
mysql> create table t9(y YEAR);
--查看表结构
mysql> show create table t9\G
*************************** 1. row ***************************
       Table: t9
Create Table: CREATE TABLE `t9` (
  `y` year(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
--插入数据【以4位字符串或数字格式表示YEAR类型，最小值为1901，最大值为2155】
mysql> insert into t9(y) values (2018),('2018');
mysql> select * from t9;
+------+
| y    |
+------+
| 2018 |
| 2018 |
+------+
mysql> insert into t9(y) values (2156),('2156');
ERROR 1264 (22003): Out of range value for column 'y' at row 1
--插入数据【以2位字符串格式表示YEAR类型，最小值为00，最大值为99】
mysql> delete from t9;
mysql> insert into t9(y) values ('0'),('00'),('59'),('70'),('99');
mysql> select * from t9;
+------+
| y    |
+------+
| 2000 |
| 2000 |
| 2059 |
| 1970 |
| 1999 |
+------+
--插入数据【以2位数字格式表示YEAR类型,最小值为1，最大值为99】
mysql> delete from t9;
mysql> insert into t9(y) values (0),(00),(59),(99),(70);
mysql> select * from t9;
+------+
| y    |
+------+
| 0000 |
| 0000 |
| 2059 |
| 1999 |
| 1970 |
+------+
mysql> insert into t9(y) values (100);
ERROR 1264 (22003): Out of range value for column 'y' at row 1
```

##### 2.2，TIME类型
TIME类型用来表示时间，不包含日期部分。在MySQL中，需要3个字节的存储空间来存储TIME类型的数据，可以使用“HH:MM:SS”格式来表示TIME类型，其中，HH表示小时，MM表示分钟，SS表示秒。

在MySQL中，向TIME类型的字段插入数据时，也可以使用几种不同的格式：
* 可以使用带有冒号的字符串，比如D HH:MM:SS、HH:MM:SS、HH:MM、D HH:MM、D HH或SS格式，都能被正确地插入TIME类型的字段中。其中D表示天，其最小值为0，最大值为34。如果使用带有D格式的字符串插入TIME类型的字段时，D会被转化为小时，计算格式为D*24+HH。
* 可以使用不带有冒号的字符串或者数字，格式为"HHMMSS"或者HHMMSS。如果插入一个不合法的字符串或者数字，MySQL在存储数据时，会将其自动转化为00:00:00进行存储。
* 使用CURRENT_TIME或者NOW()，会插入当前系统的时间。
* 12:10表示12:10:00，而不是00:12:10；1210或者'1210'，表示00:12:10，而不是12:10:00。
```sql
--建表语句
mysql> create table t10(t TIME);
mysql> show create table t10\G
*************************** 1. row ***************************
       Table: t10
Create Table: CREATE TABLE `t10` (
  `t` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
--插入数据【D HH:MM，D会被转化为小时，计算格式为D*24+HH】
mysql> INSERT INTO t10 (t) VALUES ('2 12:30:29'), ('12:35:29'), ('12:40'), ('2 12:40'), ('45');
mysql> select * FROM t10;
+----------+
| t        |
+----------+
| 60:30:29 |
| 12:35:29 |
| 12:40:00 |
| 60:40:00 |
| 00:00:45 |
+----------+
--插入数据【HHMMSS格式数据】
mysql> DELETE FROM t10;
mysql> INSERT INTO t10 (t) VALUES ('123520'), (124011), ('0'),('1236'), (1248);;
mysql> select * from t10;
+----------+
| t        |
+----------+
| 12:35:20 |
| 12:40:11 |
| 00:00:00 |
| 00:12:36 |
| 00:12:48 |
+----------+
--插入当前时间
mysql> DELETE FROM t10;
mysql> INSERT INTO t10 (t) VALUES (NOW()), (CURRENT_TIME);
mysql> select * from t10;
+----------+
| t        |
+----------+
| 17:23:14 |
| 17:23:14 |
+----------+
```

##### 2.3，DATE类型
DATE类型表示日期，没有时间部分，格式为YYYY-MM-DD，其中，YYYY表示年份，MM表示月份，DD表示日期。需要3个字节的存储空间。

* 以YYYY-MM-DD格式或者YYYYMMDD格式表示的字符串日期，其最小取值为1000-01-01，最大取值为9999-12-03。
* 以YY-MM-DD格式或者YYMMDD格式表示的字符串日期，此格式中，年份为两位数值或字符串满足YEAR类型的格式条件为：当年份取值为00到69时，会被转化为2000到2069；当年份取值为70到99时，会被转化为1970到1999。
* 以YYYYMMDD格式表示的数字日期，能够被转化为YYYY-MM-DD格式。
* 以YYMMDD格式表示的数字日期，同样满足年份为两位数值或字符串YEAR类型的格式条件为：当年份取值为00到69时，会被转化为2000到2069；当年份取值为70到99时，会被转化为1970到1999。
* 使用CURRENT_DATE或者NOW()函数，会插入当前系统的日期。
```sql
--建表语句
mysql> create table t11(d DATE);
mysql> show create table t11\G
*************************** 1. row ***************************
       Table: t11
Create Table: CREATE TABLE `t11` (
  `d` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
--插入数据【YYYY-MM-DD，YYYYMMDD】
mysql> INSERT INTO t11 (d) VALUES ('2020-10-01'), ('20201001');
--插入数据【YYMMDD，YY-MM-DD】
mysql> DELETE FROM t11;
mysql> INSERT INTO t11 (d) VALUES ('00-01-01'), ('000101'), ('69-10-01'), ('691001'), ('70-01-01'), ('700101'), ('99-01-01'), ('990101');
mysql> select * from t11;
+------------+
| d          |
+------------+
| 2000-01-01 |
| 2000-01-01 |
| 2069-10-01 |
| 2069-10-01 |
| 1970-01-01 |
| 1970-01-01 |
| 1999-01-01 |
| 1999-01-01 |
+------------+
mysql> DELETE FROM t11;
mysql> INSERT INTO t11 (d) VALUES (000101), (691001), (700101), (990101);         
--插入当前日期【CURRENT_DATE()), (NOW()】
mysql> DELETE FROM t11;
mysql> INSERT INTO t11 (d) VALUES (CURRENT_DATE()), (NOW());
```

##### 2.4，DATETIME类型
DATETIME类型在所有的日期时间类型中占用的存储空间最大，总共需要8个字节的存储空间。在格式上为DATE类型和TIME类型的组合，可以表示为YYYY-MM-DD HH:MM:SS，其中YYYY表示年份，MM表示月份，DD表示日期，HH表示小时，MM表示分钟，SS表示秒。

在向DATETIME类型的字段插入数据时，同样需要满足一定的格式条件。
* 以YYYY-MM-DD HH:MM:SS格式或者YYYYMMDDHHMMSS格式的字符串插入DATETIME类型的字段时，最小值为10000-01-01 00:00:00，最大值为9999-12-03 23:59:59。
* 以YY-MM-DD HH:MM:SS格式或者YYMMDDHHMMSS格式的字符串插入DATETIME类型的字段时，两位数的年份规则符合YEAR类型的规则，00到69表示2000到2069；70到99表示1970到1999。
* 以YYYYMMDDHHMMSS格式的数字插入DATETIME类型的字段时，会被转化为YYYY-MM-DD HH:MM:SS格式。
* 以YYMMDDHHMMSS格式的数字插入DATETIME类型的字段时，两位数的年份规则符合YEAR类型的规则，00到69表示2000到2069；70到99表示1970到1999。
* 使用函数CURRENT_TIMESTAMP()和NOW()，可以向DATETIME类型的字段插入系统的当前日期和时间。
```sql
--建表语句
mysql> create table t12(dt DATETIME);
mysql> show create table t12\G
*************************** 1. row ***************************
       Table: t12
Create Table: CREATE TABLE `t12` (
  `dt` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
--插入数据
mysql> INSERT INTO t12 (dt) VALUES ('2020-01-01 00:00:00'), ('20200101000000');
mysql> INSERT INTO t12 (dt) VALUES ('99-01-01 00:00:00'), ('990101000000'), ('20-01-01 00:00:00'), ('200101000000');
mysql> SELECT * FROM t12;
+---------------------+
| dt                  |
+---------------------+
| 2020-01-01 00:00:00 |
| 2020-01-01 00:00:00 |
| 1999-01-01 00:00:00 |
| 1999-01-01 00:00:00 |
| 2020-01-01 00:00:00 |
| 2020-01-01 00:00:00 |
+---------------------+
--插入当前日期时间【CURRENT_TIMESTAMP()), (NOW()】
mysql> DELETE FROM t12;
mysql> INSERT INTO t12 (dt) VALUES (CURRENT_TIMESTAMP()), (NOW());
mysql> select * from t12;
+---------------------+
| dt                  |
+---------------------+
| 2022-09-22 14:22:05 |
| 2022-09-22 14:22:05 |
+---------------------+
```

##### 2.5，TIMESTAMP类型
TIMESTAMP类型也可以表示日期时间，其显示格式与DATETIME类型相同，都是YYYY-MM-DD HH:MM:SS，需要4个字节的存储空间。

但是TIMESTAMP存储的时间范围比DATETIME要小很多，只能存储“1970-01-01 00:00:01 UTC”到“2038-01-19 03:14:07 UTC”之间的时间。其中，UTC表示世界统一时间，也叫作世界标准时间。

如果向TIMESTAMP类型的字段插入的时间超出了TIMESTAMP类型的范围，则MySQL会抛出错误信息。
```sql
--建表语句
mysql> create table t13(ts TIMESTAMP);
mysql> show create table t13\G
*************************** 1. row ***************************
       Table: t13
Create Table: CREATE TABLE `t13` (
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8
--插入数据
mysql> INSERT INTO t13 (ts) VALUES ('1999-01-01 00:00:00'), ('19990101000000'), ('99-01-01 00:00:00'), ('990101000000'), ('20-01-01 00:00:00'), ('200101000000');
--插入数据【超出时间范围】
mysql> INSERT INTO t13 (ts) VALUES ('1945-01-01 00:00:00');
ERROR 1292 (22007): Incorrect datetime value: '1945-01-01 00:00:00' for column 'ts' at row 1
--插入当前日期时间
mysql> INSERT INTO t13 (ts) VALUES (CURRENT_TIMESTAMP()),(NOW());
mysql> select * from t13;
+---------------------+
| ts                  |
+---------------------+
| 2022-09-22 14:28:22 |
| 2022-09-22 14:28:22 |
+---------------------+

/**TIMESTAMP在存储数据的时候是以UTC（世界统一时间，也叫作世界标准时间）格式进行存储的，
存储数据的时候需要对当前时间所在的时区进行转换，查询数据的时候再将时间转换回当前的时区。
因此，使用TIMESTAMP存储的同一个时间值，在不同的时区查询时会显示不同的时间。 */
mysql> create table t14(ts1 TIMESTAMP,ts2 TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);
--查看当前所处的时区
mysql> SHOW VARIABLES LIKE 'time_zone'; 
+---------------+--------+
| Variable_name | Value  |
+---------------+--------+
| time_zone     | SYSTEM |
+---------------+--------+
/**time_zone时区的值为SYSTEM，也就是服务器所在的东八区。 */
--插入时间
mysql> insert into t14(ts1) values (NOW());
mysql> select * from t14;
+---------------------+---------------------+
| ts1                 | ts2                 |
+---------------------+---------------------+
| 2022-09-22 14:35:49 | 2022-09-22 14:35:49 |
+---------------------+---------------------+

--修改时区为零时区
mysql> SET time_zone = '+0:00';
mysql> SHOW VARIABLES LIKE 'time_zone';
+---------------+--------+
| Variable_name | Value  |
+---------------+--------+
| time_zone     | +00:00 |
+---------------+--------+
--查看当前表内存储的 TIMESTAMP 字段值
mysql> select * from t14;
+---------------------+---------------------+
| ts1                 | ts2                 |
+---------------------+---------------------+
| 2022-09-22 06:35:49 | 2022-09-22 06:35:49 |
+---------------------+---------------------+

--重置时区为 东八区【东部区时为+，西部区时为-】
mysql> SET time_zone = '+8:00';
```

#### 3，文本字符串类型
MySQL中，文本字符串总体上分为CHAR、VARCHAR、TINYTEXT、TEXT、MEDIUMTEXT、LONGTEXT、ENUM、SET和JSON等类型。

| 文本字符串类型 | 值的长度 | 长度范围                | 存储空间         |
| -------------- | -------- | ----------------------- | ---------------- |
| CHAR(M)        | M        | 0<=M<=255 (2^8)         | M个字节          |
| VARCHAR(M)     | M        | 0<=M<=65535 (2^16)      | M+1个字节        |
| TINYTEXT       | L        | 0<=L<=255 (2^8)         | L+1个字节        |
| TEXT           | L        | 0<=L<=65535 (2^16)      | L+2个字节        |
| MEDIUMTEXT     | L        | 0<=L<=16777215 (2^24)   | L+3个字节        |
| LONGTEXT       | L        | 0<=L<=4294967295 (2^32) | L+4个字节        |
| ENUM           | L        | 1<=L<=65535             | 1或2个字节       |
| SET            | L        | 0<=L<=64                | 1,2,3,4或8个字节 |

##### 3.1，CHAR与VARCHAR类型
CHAR字段特性：
* CHAR和VARCHAR类型都可以存储比较短的字符串。
* CHAR类型的字段长度是固定的，为创建表时声明的字段长度，最小取值为0，最大取值为255。
* 如果保存时，数据的实际长度比CHAR类型声明的长度小，则会在右侧填充空格以达到指定的长度。当MySQL检索CHAR类型的数据时，CHAR类型的字段会去除尾部的空格。
* 对于CHAR类型的数据来说，定义CHAR类型字段时，声明的字段长度即为CHAR类型字段所占的存储空间的字节数。

VARCHAR字段特性：
* VARCHAR类型修饰的字符串是一个可变长的字符串，长度的最小值为0，最大值为65535。
* 检索VARCHAR类型的字段数据时，会保留数据尾部的空格。VARCHAR类型的字段所占用的存储空间为字符串实际长度加1个字节。

```sql
--建表语句
mysql> create table t15(c CHAR(4),vc VARCHAR(4));
mysql> show create table t15\G
*************************** 1. row ***************************
       Table: t15
Create Table: CREATE TABLE `t15` (
  `c` char(4) DEFAULT NULL,
  `vc` varchar(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
--插入数据
/**MySQL在检索CHAR类型的字段时，会去除尾部的空格；而在检索VARCHAR类型的字段时，则不会去除尾部的空格。 */
mysql> insert into t15(c,vc) values ('abc','abc'),('a  ','a  ');
mysql> select c,length(c),vc,length(vc) from t15;
+------+-----------+------+------------+
| c    | length(c) | vc   | length(vc) |
+------+-----------+------+------------+
| abc  |         3 | abc  |          3 |
| a    |         1 | a    |          3 |
+------+-----------+------+------------+
mysql> SELECT CONCAT(c, 'b'),CONCAT(vc, 'b')  FROM t15;
+----------------+-----------------+
| CONCAT(c, 'b') | CONCAT(vc, 'b') |
+----------------+-----------------+
| abcb           | abcb            |
| ab             | a  b            |
+----------------+-----------------+
```

##### 3.2，TEXT类型
在MySQL中，Text用来保存文本类型的字符串，总共包含4种类型，分别为TINYTEXT、TEXT、MEDIUMTEXT和LONGTEXT类型。

在向TEXT类型的字段保存和查询数据时，不会删除数据尾部的空格，这一点和VARCHAR类型相同。其中，每种TEXT类型保存的数据长度和所占用的存储空间不同

```sql
--建表语句
mysql> create table t16(tt TINYTEXT,t TEXT,mt MEDIUMTEXT,lt LONGTEXT);
mysql> show create table t16\G
*************************** 1. row ***************************
       Table: t16
Create Table: CREATE TABLE `t16` (
  `tt` tinytext,
  `t` text,
  `mt` mediumtext,
  `lt` longtext
) ENGINE=InnoDB DEFAULT CHARSET=utf8
--插入数据
mysql> insert into t16(tt,t,mt,lt) values ("A  ","A  ","A  ","A  ");
mysql> select tt,length(tt),t,length(t),mt,length(mt),lt,length(lt) from t16;
+------+------------+------+-----------+------+------------+------+------------+
| tt   | length(tt) | t    | length(t) | mt   | length(mt) | lt   | length(lt) |
+------+------------+------+-----------+------+------------+------+------------+
| A    |          3 | A    |         3 | A    |          3 | A    |          3 |
+------+------------+------+-----------+------+------------+------+------------+
```

##### 3.3，ENUM类型
* ENUM类型也叫作枚举类型，ENUM类型的取值范围需要在定义字段时进行指定，其所需要的存储空间由定义ENUM类型时指定的成员个数决定。
* 当ENUM类型包含1～255个成员时，需要1个字节的存储空间；当ENUM类型包含256～65535个成员时，需要2个字节的存储空间。
* ENUM类型的成员个数的上限为65535个。
```sql
--建表语句
mysql> CREATE TABLE t17 (e ENUM ('A', 'B', 'C'));
mysql> show create table t17\G
*************************** 1. row ***************************
       Table: t17
Create Table: CREATE TABLE `t17` (
  `e` enum('A','B','C') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
--插入数据
mysql> INSERT INTO t17 (e) VALUES ('A'), ('B');
/**定义e字段时，ENUM类型的成员被定义为大写的A、B、C当插入小写的a和b时，MySQL会将其自动转化为大写的A和B进行存储。 */
mysql> INSERT INTO t17 (e) VALUES ('b'), ('c');
/**在ENUM类型中，第一个成员的下标为1，第二个成员的下标为2，以此类推。 */
mysql> INSERT INTO t17 (e) VALUES ('1'), ('3');
mysql> INSERT INTO t17 (e) VALUES (1), (2);
/**当ENUM类型的字段没有声明为NOT NULL时，插入NULL也是有效的。 */
mysql> INSERT INTO t17 (e) VALUES (NULL);
mysql> select * FROM t17;
+------+
| e    |
+------+
| A    |
| B    |
| B    |
| C    |
| A    |
| C    |
| A    |
| B    |
| NULL |
+------+
/**在定义字段时，如果将ENUM类型的字段声明为NULL时，NULL为有效值，默认值为NULL；
如果将ENUM类型的字段声明为NOT NULL时，NULL为无效的值，默认值为ENUM类型成员的第一个成员。
另外，ENUM类型只允许从成员中选取单个值，不能一次选取多个值。 */
```

##### 3.4，SET类型c
* SET表示一个字符串对象，可以包含0个或多个成员，但成员个数的上限为64。
* SET类型在存储数据时一定程度上，成员个数越多，其占用的存储空间越大。
* 注意：SET类型在选取成员时，可以一次选择多个成员，这一点与ENUM类型不同。

| SET成员个数范围（L表示实际成员个数） | 存储空间 |
| ------------------------------------ | -------- |
| 1<=L<=8                              | 1个字节  |
| 9<=L<=16                             | 2个字节  |
| 17<=L<=24                            | 3个字节  |
| 25<=L<=32                            | 4个字节  |
| 33<=L<=64                            | 8个字节  |

```sql
--建表语句
mysql> CREATE TABLE t18 (s SET ('A', 'B', 'C'));
mysql> show create table t18\G
*************************** 1. row ***************************
       Table: t18
Create Table: CREATE TABLE `t18` (
  `s` set('A','B','C') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
--插入数据
/**SET类型在选取成员时，可以一次选择多个成员，这一点与ENUM类型不同。 */
mysql> INSERT INTO t18 (s) VALUES ('A'), ('A,B');
/**当向表中的SET类型的字段插入重复的SET类型成员时，MySQL会自动删除重复的成员。 */
mysql> INSERT INTO t18 (s) VALUES ('A,B,C,A');
/**当向SET类型的字段插入SET成员中不存在的值时，MySQL会抛出错误 */
mysql> INSERT INTO t18 (s) VALUES ('A,B,C,D');
ERROR 1265 (01000): Data truncated for column 's' at row 1
mysql> SELECT * FROM t18;
+-------+
| s     |
+-------+
| A     |
| A,B   |
| A,B,C |
+-------+
```

##### 3.4，JSON类型
在MySQL 5.7中，就已经支持JSON数据类型。在MySQL 8.x版本中，JSON类型提供了可以进行自动验证的JSON文档和优化的存储结构，使得在MySQL中存储和读取JSON类型的数据更加方便和高效。

```sql
--建表语句
mysql> show create table t19;
mysql> show create table t19\G
*************************** 1. row ***************************
       Table: t19
Create Table: CREATE TABLE `t19` (
  `j` json DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
--插入数据
mysql> INSERT INTO t19 (j) VALUES ('{"name":"johann", "age":1, "address":{"province":"hebei", "city":"handan"}}');
mysql> INSERT INTO t19 (j) VALUES ('{"name":"jessie", "age":1, "address":{"province":"hebei", "city":"cangzhou"}}');
mysql> select * from t19;
+------------------------------------------------------------------------------------+
| j                                                                                  |
+------------------------------------------------------------------------------------+
| {"age": 1, "name": "johann", "address": {"city": "handan", "province": "hebei"}}   |
| {"age": 1, "name": "jessie", "address": {"city": "cangzhou", "province": "hebei"}} |
+------------------------------------------------------------------------------------+
/**可以使用“->”和“->>”符号，检索JSON类型的字段中数据的某个具体值 */
mysql> SELECT j->'$.name' AS name, j->'$.address.province' AS province, j->'$.address.city' AS city FROM t19;
+----------+----------+------------+
| name     | province | city       |
+----------+----------+------------+
| "johann" | "hebei"  | "handan"   |
| "jessie" | "hebei"  | "cangzhou" |
+----------+----------+------------+
```

#### 4，二进制字符串类型
MySQL中的二进制字符串类型主要存储一些二进制数据，比如可以存储图片、音频和视频等二进制数据。

MySQL中支持的二进制字符串类型主要包括BIT、BINARY、VARBINARY、TINYBLOB、BLOB、MEDIUMBLOB和LONGBLOB类型。

| 二进制字符串类型 | 值的长度 | 长度范围                | 存储空间         |
| ---------------- | -------- | ----------------------- | ---------------- |
| BIT              | M        | 1<=M<=64                | 约为(M+7)8个字节 |
| BINARY           | M        |                         | M个字节          |
| VARBINARY        | M        |                         | M+1个字节        |
| TINYBLOB         | L        | 0<=L<=255 (2^8)         | L+1个字节        |
| BLOB             | L        | 0<=L<=65535 (2^16)      | L+2个字节        |
| MEDIUMBLOB       | L        | 0<=L<=16777215 (2^24)   | L+3个字节        |
| LONGBLOB         | L        | 0<=L<=4294967295 (2^32) | L+4个字节        |

##### 4.1，BIT类型
BIT类型中，每个值的位数最小值为1，最大值为64，默认的位数为1。BIT类型中存储的是二进制值。
```sql
--建表语句
mysql> CREATE TABLE t20 (b BIT);
/**若不设置BIT长度，默认为1 */
mysql> show create table t20\G
*************************** 1. row ***************************
       Table: t20
Create Table: CREATE TABLE `t20` (
  `b` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
--插入数据
mysql> INSERT INTO t20 (b) VALUES (2), (8), (16);
ERROR 1406 (22001): Data too long for column 'b' at row 1
/**在向BIT类型的字段中插入数据时，一定要确保插入的数据在BIT类型支持的范围内。 */
/**此时需要更改BIG类型的长度 */
mysql> ALTER TABLE t20 MODIFY b BIT(10) DEFAULT NULL;
mysql> show create table t20\G
*************************** 1. row ***************************
       Table: t20
Create Table: CREATE TABLE `t20` (
  `b` bit(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/**重新插入数据，并查看数据 */
mysql> INSERT INTO t20 (b) VALUES (2), (8), (16);
mysql> select * from t20;
+------+
| b    |
+------+
|     |
|    |
|     |
+------+
/**BIT类型存储的是二进制数据。无法直接查看，可以通过 BIN()函数将数字转化为了二进制。
将存储的二进制值的结果转化为对应的二进制数字的值。 */
mysql> SELECT BIN(b+0) FROM t20;
+----------+
| BIN(b+0) |
+----------+
| 10       |
| 1000     |
| 10000    |
+----------+
/**也可以通过以下方式查询，直接显示十进制数据 */
mysql> SELECT b+0 FROM t20;
+------+
| b+0  |
+------+
|    2 |
|    8 |
|   16 |
+------+
```

##### 4.2，BINARY与VARBINARY类型
























