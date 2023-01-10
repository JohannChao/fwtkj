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

/**9，使用旧表结构创建新表 */
CREATE TABLE IF NOT EXISTS table_new LIKE table_old;
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
/**2.2，在表的第一列添加字段 */
mysql> ALTER TABLE t_goods_tmp ADD COLUMN t_create_time DATETIME DEFAULT NULL FIRST;
/**2.3，在指定字段的后面添加字段*/
mysql> ALTER TABLE t_goods_tmp ADD COLUMN t_create_time DATETIME DEFAULT NULL AFTER ID;

/**3，修改字段名称 */
ALTER TABLE 表名 CHANGE 原有字段名 新字段名 新数据类型  --语法格式
mysql> ALTER TABLE t_goods_tmp CHANGE t_update_time t_last_modified DATETIME;
--主键设置自增
mysql> alter table t_goods change id id int not null auto_increment;

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
/**8，取消数据表的外键约束 */
ALTER TABLE 表名 DROP FOREIGN KEY 外键名  --语法格式
mysql> ALTER TABLE t_goods DROP FOREIGN KEY `t_goods_ibfk_1`;
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
特别地，如果在MySQL中创建数据表时，指定数据字段为 ZEROFILL，则MySQL会自动为当前列添加 UNSIGNED属性。*/
mysql> show create table t3\G
*************************** 1. row ***************************
       Table: t3
Create Table: CREATE TABLE `t3` (
  `id1` int(10) unsigned zerofill DEFAULT NULL,
  `id2` int(6) unsigned zerofill DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
--建表语句指定整数的无符号属性
mysql> create table t3_1(id int unsigned,countNum int(6) unsigned);

/**4，整数类型还有一个属性是 AUTO_INCREMENT。
一个表中最多只能有一个列被设置为 AUTO_INCREMENT。设置为 AUTO_INCREMENT的列需要定义为 NOT NULL，并且定义为 PRIMARY KEY，或者定义为 NOT NULL并且定义为 UNIQUE。 */
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

##### 3.4，SET类型
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
BINARY类型为定长的二进制类型，当插入的数据未达到指定的长度时，将会在数据后面填充“\0”字符，以达到指定的长度。同时BINARY类型的字段的存储空间也为固定的值。

VARBINARY类型为变长的二进制类型，长度的最小值为0，最大值为定义VARBINARY类型的字段时指定的长度值，其存储空间为数据的实际长度值加1。

```sql
--建表语句
mysql> create table t21(b BINARY(8),vb VARBINARY(8));
mysql> show create table t21\G
*************************** 1. row ***************************
       Table: t21
Create Table: CREATE TABLE `t21` (
  `b` binary(8) DEFAULT NULL,
  `vb` varbinary(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
--插入数据
mysql> insert into t21(b,vb) values (16,16);
mysql> select length(b),b,length(vb),vb from t21;
+-----------+----------+------------+------+
| length(b) | b        | length(vb) | vb   |
+-----------+----------+------------+------+
|         8 | 16       |          2 | 16   |
+-----------+----------+------------+------+
```

##### 4.3，BLOB类型
MySQL中的BLOB类型包括TINYBLOB、BLOB、MEDIUMBLOB和LONGBLOB 4种类型，可以存储一个二进制的大对象，比如图片、音频和视频等。

需要注意的是，在实际工作中，往往不会在MySQL数据库中使用BLOB类型存储大对象数据，通常会将图片、音频和视频文件存储到服务器的磁盘上，并将图片、音频和视频的访问路径存储到MySQL中。


### MySQL运算符

#### 1，算术运算符

MySQL的算术运算符包含加（+）、减（-）、乘（*）、除（/）和求模（求余）运算。

* 一个整数类型的值对浮点数进行加法和减法操作，结果是一个浮点数；
* 一个数乘以浮点数1和除以浮点数1后变成浮点数，数值与原数相等；
* 一个数除以整数后，不管是否能除尽，结果都为一个浮点数；
* 一个数除以另一个数，除不尽时，结果为一个浮点数，并保留到小数点后4位；

| 算术数运算符            | 示例                           |
| ----------------------- | ------------------------------ |
| + (加法运算)            | SELECT A + B                   |
| - (减法运算)            | SELECT A - B                   |
| * (乘法运算)            | SELECT A * B                   |
| /或DIV (除法运算)       | SELECT A / B 或 SELECT A DIV B |
| %或MOD (求模/求余 运算) | SELECT A % B 或 SELECT A MOD B |

#### 2，比较运算符
比较运算符用来对表达式左边的操作数和右边的操作数进行比较，比较的结果为真则返回1，比较的结果为假则返回0，其他情况则返回NULL。

##### 2.1，等号运算符（=）
等号运算符（=）判断等号两边的值、字符串或表达式是否相等，如果相等则返回1，不相等则返回0。

* 如果等号两边的值、字符串或表达式中有一个为NULL，则比较结果为NULL；
* 如果等号两边的值、字符串或表达式都为字符串，则MySQL会按照字符串进行比较，其比较的是每个字符串中字符的ANSI编码是否相等。
* 如果等号两边的值都是整数，则MySQL会按照整数来比较两个值的大小。
* 如果等号两边的值一个是整数，另一个是字符串，则MySQL会将字符串转化为数字进行比较。
```sql
mysql> SELECT 1 = 1, 1 = '1', 1 = 0, 'a' = 'a', (5 + 3) = (2 + 6), '' = NULL , NULL = NULL;
+-------+---------+-------+-----------+-------------------+-----------+-------------+
| 1 = 1 | 1 = '1' | 1 = 0 | 'a' = 'a' | (5 + 3) = (2 + 6) | '' = NULL | NULL = NULL |
+-------+---------+-------+-----------+-------------------+-----------+-------------+
|     1 |       1 |     0 |         1 |                 1 |      NULL |        NULL |
+-------+---------+-------+-----------+-------------------+-----------+-------------+
```

##### 2.2，安全等于运算符（<=>）
安全等于运算符（<=>）与等于运算符（=）的操作相同，但是安全等于运算符（<=>）能够比较NULL值。如果进行比较的两个值都为NULL，则结果返回1。
```sql
mysql> SELECT 1 <=> 1, 1 <=> '1', 1 <=> 0, 'a' <=> 'a', (5 + 3) <=> (2 + 6), '' <=> NULL , NULL <=> NULL;
+---------+-----------+---------+-------------+---------------------+-------------+---------------+
| 1 <=> 1 | 1 <=> '1' | 1 <=> 0 | 'a' <=> 'a' | (5 + 3) <=> (2 + 6) | '' <=> NULL | NULL <=> NULL |
+---------+-----------+---------+-------------+---------------------+-------------+---------------+
|       1 |         1 |       0 |           1 |                   1 |           0 |             1 |
+---------+-----------+---------+-------------+---------------------+-------------+---------------+
```

##### 2.3，不等于运算符（<>和!=）
不等于运算符（<>和!=）用于判断两边的数字、字符串或者表达式的值是否不相等，如果不相等则返回1，相等则返回0。不等于运算符不能判断NULL值。

如果两边的值有任意一个为NULL，或两边都为NULL，则结果为NULL。
```sql
mysql> SELECT 1 <> 1, 1 != 2, 'a' != 'b', (3+4) <> (2+6), 'a' != NULL, NULL <> NULL;
+--------+--------+------------+----------------+-------------+--------------+
| 1 <> 1 | 1 != 2 | 'a' != 'b' | (3+4) <> (2+6) | 'a' != NULL | NULL <> NULL |
+--------+--------+------------+----------------+-------------+--------------+
|      0 |      1 |          1 |              1 |        NULL |         NULL |
+--------+--------+------------+----------------+-------------+--------------+
```

##### 2.4，小于运算符，小于或等于运算符，大于运算符，大于或等于运算符
这四个运算符，不能用于NULL值判断。如果两边的值有任意一个为NULL，或两边都为NULL，则结果为NULL。

##### 2.5，空运算符（IS NULL或者ISNULL），非空运算符（IS NOT NULL）
空运算符（IS NULL或者ISNULL）判断一个值是否为NULL，如果为NULL则返回1，否则返回0。

非空运算符（IS NOT NULL）判断一个值是否不为NULL，如果不为NULL则返回1，否则返回0。

##### 2.6，最小值运算符（LEAST），最大值运算符（GREATEST）
最小值运算符（LEAST）用于获取参数列表中的最小值，如果参数列表中有任意一个值为NULL，则结果返回NULL。

最大值运算符（GREATEST）用于获取参数列表中的最大值，如果参数列表中有任意一个值为NULL，则结果返回NULL。
```sql
mysql> SELECT LEAST (1,0,2), LEAST('b','a','c'), LEAST(1,NULL,2);
+---------------+--------------------+-----------------+
| LEAST (1,0,2) | LEAST('b','a','c') | LEAST(1,NULL,2) |
+---------------+--------------------+-----------------+
|             0 | a                  |            NULL |
+---------------+--------------------+-----------------+

mysql> SELECT GREATEST(1,0,2), GREATEST('b','a','c'), GREATEST(1,NULL,2);
+-----------------+-----------------------+--------------------+
| GREATEST(1,0,2) | GREATEST('b','a','c') | GREATEST(1,NULL,2) |
+-----------------+-----------------------+--------------------+
|               2 | c                     |               NULL |
+-----------------+-----------------------+--------------------+
```

##### 2.7，BETWEEN AND运算符
BETWEEN运算符使用的格式通常为```SELECT D FROM TABLE WHERE C BETWEEN A AND B```，此时，当C大于或等于A，并且C小于或等于B时，结果为1，否则结果为0。

cIN运算符，NOT IN运算符
IN运算符用于判断给定的值是否是IN列表中的一个值，如果是则返回1，否则返回0。如果给定的值为NULL，则结果为NULL。

NOT IN运算符用于判断给定的值是否不是IN列表中的一个值，如果不是IN列表中的一个值，则返回1，否则返回0。如果给定的值为NULL，或者IN列表中存在NULL，则结果为NULL。
```sql
mysql> SELECT 'a' IN ('a','b','c'), 1 IN (2,3), NULL IN ('a','b'), 'a' IN ('a', NULL);
+----------------------+------------+-------------------+--------------------+
| 'a' IN ('a','b','c') | 1 IN (2,3) | NULL IN ('a','b') | 'a' IN ('a', NULL) |
+----------------------+------------+-------------------+--------------------+
|                    1 |          0 |              NULL |                  1 |
+----------------------+------------+-------------------+--------------------+

mysql> SELECT 'a' NOT IN ('a','b','c'), 1 NOT IN (2,3), NULL NOT IN ('a','b'), 'b' NOT IN ('a', NULL);
+--------------------------+----------------+-----------------------+------------------------+
| 'a' NOT IN ('a','b','c') | 1 NOT IN (2,3) | NULL NOT IN ('a','b') | 'b' NOT IN ('a', NULL) |
+--------------------------+----------------+-----------------------+------------------------+
|                        0 |              1 |                  NULL |                   NULL |
+--------------------------+----------------+-----------------------+------------------------+
```

##### 2.8，LIKE运算符
LIKE运算符主要用来匹配字符串，通常用于模糊匹配，如果满足条件则返回1，否则返回0。

如果给定的值或者匹配条件为NULL，则返回结果为NULL。

LIKE运算符通常使用如下通配符。
* “%”：匹配0个或多个字符。
* “_”：只能匹配一个字符。

```sql
mysql> SELECT 'binghe' LIKE 'b%', 'binghe' LIKE 'bingh_', 'binghe' LIKE '%e', 'binghe' LIKE '_inghe';
+--------------------+------------------------+--------------------+------------------------+
| 'binghe' LIKE 'b%' | 'binghe' LIKE 'bingh_' | 'binghe' LIKE '%e' | 'binghe' LIKE '_inghe' |
+--------------------+------------------------+--------------------+------------------------+
|                  1 |                      1 |                  1 |                      1 |
+--------------------+------------------------+--------------------+------------------------+


mysql> SELECT NULL LIKE 'abc', 'abc' LIKE NULL;  
+-----------------+-----------------+
| NULL LIKE 'abc' | 'abc' LIKE NULL |
+-----------------+-----------------+
|          NULL   |          NULL   |
+-----------------+-----------------+
```

##### 2.9，REGEXP运算符
REGEXP运算符用于匹配字符串，通常与正则表达式一起使用。如果满足条件则返回1，否则返回0。如果给定的值或者匹配条件为NULL，则结果返回NULL。

REGEXP运算符常用的匹配规则如下：
* 匹配以“^”后面的字符开头的字符串；
* 匹配以“$”前面的字符结尾的字符串；
* “.”匹配任意一个单字符；
* “[…]”匹配方括号内的任意字符；
* 匹配零个或多个在“*”前面的字符。

```sql
mysql> SELECT 'binghe' REGEXP '^b', 'binghe' REGEXP 'e$', 'binghe' REGEXp 'bing';
+----------------------+----------------------+------------------------+
| 'binghe' REGEXP '^b' | 'binghe' REGEXP 'e$' | 'binghe' REGEXp 'bing' |
+----------------------+----------------------+------------------------+
|                  1   |                   1  |                    1   |
+----------------------+----------------------+------------------------+
```

#### 3，逻辑运算符
逻辑运算符主要用来判断表达式的真假，在MySQL中，逻辑运算符的返回结果为1、0或者NULL。

| 逻辑运算符          | 示例                             |
| ------------------- | -------------------------------- |
| NOT 或 ! (逻辑非)   | SELECT NOT A                     |
| AND 或 && (逻辑与)  | SELECT A AND B 或 SELECT A && B  |
| OR 或 \|\| (逻辑或) | SELECT A OR B 或 SELECT A \|\| B |
| XOR (逻辑异或)      | SELECT A XOR B                   |


##### 3.1，逻辑非运算符
逻辑非（NOT或!）运算符表示当给定的值为0时返回1；当给定的值为非0值时返回0；当给定的值为NULL时，返回NULL。
```sql
mysql> SELECT NOT 1, NOT 0, NOT(1+1), NOT !1, NOT NULL;
+-------+-------+----------+--------+----------+
| NOT 1 | NOT 0 | NOT(1+1) | NOT !1 | NOT NULL |
+-------+-------+----------+--------+----------+
|     0 |     1 |        0 |      1 |     NULL |
+-------+-------+----------+--------+----------+
```

##### 3.2，逻辑与运算符
* 逻辑与（AND或&&）运算符是当给定的所有值均为非0值，并且都不为NULL时，返回1；
* 当给定的一个值或者多个值为0时则返回0；否则返回NULL。
* 逻辑与优先级：0 > NULL > 1
```sql
mysql> SELECT 1 AND -1, 0 AND 1, 0 AND NULL, 1 AND NULL;
+----------+---------+------------+------------+
| 1 AND -1 | 0 AND 1 | 0 AND NULL | 1 AND NULL |
+----------+---------+------------+------------+
|        1 |       0 |          0 |       NULL |
+----------+---------+------------+------------+
```

##### 3.3，逻辑或运算符
* 逻辑或（OR或||）运算符是当给定的值都不为NULL，并且任何一个值为非0值时，则返回1，否则返回0；
* 当一个值为NULL，并且另一个值为非0值时，返回1，否则返回NULL；
* 当两个值都为NULL时，返回NULL。
* 逻辑或优先级：0 < NULL < 1
```sql
mysql> SELECT 1 OR -1, 1 OR 0, 1 OR NULL, 0 || NULL, NULL || NULL;
+---------+--------+-----------+-----------+--------------+
| 1 OR -1 | 1 OR 0 | 1 OR NULL | 0 || NULL | NULL || NULL |
+---------+--------+-----------+-----------+--------------+
|       1 |      1 |         1 |      NULL |         NULL |
+---------+--------+-----------+-----------+--------------+    
```

##### 3.4，逻辑异或运算符
* 逻辑异或（XOR）运算符是当给定的值中任意一个值为NULL时，则返回NULL；
* 如果两个非NULL的值都是0或者都不等于0时，则返回0；
* 如果一个值为0，另一个值不为0时，则返回1。
* 同值为0，异值为1
```sql
mysql> SELECT 1 XOR -1, 1 XOR 0, 0 XOR 0, 1 XOR NULL, 1 XOR 1 XOR 1, 0 XOR 0 XOR 0;
+----------+---------+---------+------------+---------------+---------------+
| 1 XOR -1 | 1 XOR 0 | 0 XOR 0 | 1 XOR NULL | 1 XOR 1 XOR 1 | 0 XOR 0 XOR 0 |
+----------+---------+---------+------------+---------------+---------------+
|        0 |       1 |       0 |       NULL |             1 |             0 |
+----------+---------+---------+------------+---------------+---------------+
```

#### 4，位运算符
位运算符主要是操作二进制字节中的位，对二进制字节中的位进行逻辑运算，最终得出相应的结果数据。

| 位运算符            | 示例          |
| ------------------- | ------------- |
| & 按位与（位AND）   | SELECT A & B  |
| \| 按位或（位OR）   | SELECT A \| B |
| ^ 按位异或（位XOR） | SELECT A ^ B  |
| ~ 按位取反          | SELECT ~A     |
| >> 按位右移         | SELECT A >> 2 |
| << 按位左移         | SELECT A << 2 |

##### 4.1，按位与运算符
按位与（&）运算符将给定值对应的二进制数逐位进行逻辑与运算。当给定值对应的二进制位的数值都为1时，则该位返回1，否则返回0。
```sql
mysql> SELECT 1 & 10, 20 & 30;
+--------+---------+
| 1 & 10 | 20 & 30 |
+--------+---------+
|      0 |      20 |
+--------+---------+
--1的二进制数为0001，10的二进制数为1010，所以1 & 10的结果为0000，对应的十进制数为0。
--20的二进制数为10100，30的二进制数为11110，所以20 & 30的结果为10100，对应的十进制数为20。
```

##### 4.2，按位或运算符
按位或（|）运算符将给定的值对应的二进制数逐位进行逻辑或运算。当给定值对应的二进制位的数值有一个或两个为1时，则该位返回1，否则返回0。
```sql
mysql> SELECT 1 | 10, 20 | 30; 
+--------+---------+
| 1 | 10 | 20 | 30 |
+--------+---------+
|     11 |      30 |
+--------+---------+
--1的二进制数为0001，10的二进制数为1010，所以1 | 10的结果为1011，对应的十进制数为11。
--20的二进制数为10100，30的二进制数为11110，所以20 | 30的结果为11110，对应的十进制数为30。
```

##### 4.3，按位异或运算符
按位异或（^）运算符将给定的值对应的二进制数逐位进行逻辑异或运算。当给定值对应的二进制位的数值不同时，则该位返回1，否则返回0。
```sql
mysql> SELECT 1 ^ 10, 20 ^ 30; 
+--------+---------+
| 1 ^ 10 | 20 ^ 30 |
+--------+---------+
|     11 |      10 |
+--------+---------+
--1的二进制数为0001，10的二进制数为1010，所以1 ^ 10的结果为1011，对应的十进制数为11。
--20的二进制数为10100，30的二进制数为11110，所以20 ^ 30的结果为01010，对应的十进制数为10。
```

##### 4.4，按位取反运算符
按位取反（~）运算符将给定的值的二进制数逐位进行取反操作，即将1变为0，将0变为1。
```sql
mysql> SELECT 10 & ~1;
+---------+
| 10 & ~1 |
+---------+
|      10 |
+---------+
--由于按位取反（~）运算符的优先级高于按位与（&）运算符的优先级，所以10 &~1，首先，对数字1进行按位取反操作，结果除了最低位为0，其他位都为1，然后与10进行按位与操作，结果为10。
```

##### 4.5，按位右移运算符（缩小2*n倍）
按位右移（>>）运算符将给定的值的二进制数的所有位右移指定的位数。右移指定的位数后，右边低位的数值被移出并丢弃，左边高位空出的位置用0补齐。
```sql
mysql> SELECT 1 >> 2, 4 >> 2;
+--------+--------+
| 1 >> 2 | 4 >> 2 |
+--------+--------+
|      0 |      1 |
+--------+--------+
--1的二进制数为0000 0001，右移2位为0000 0000，对应的十进制数为0。
--4的二进制数为0000 0100，右移2位为0000 0001，对应的十进制数为1。
```

##### 4.6，按位左移运算符（放大2*n倍）
按位左移（<<）运算符将给定的值的二进制数的所有位左移指定的位数。左移指定的位数后，左边高位的数值被移出并丢弃，右边低位空出的位置用0补齐。
```sql
mysql> SELECT 1 << 2, 4 << 2;  
+--------+--------+
| 1 << 2 | 4 << 2 |
+--------+--------+
|      4 |     16 |
+--------+--------+
--1的二进制数为0000 0001，左移两位为0000 0100，对应的十进制数为4。
--4的二进制数为0000 0100，左移两位为0001 0000，对应的十进制数为16。
```

#### 5，运算符的优先级

数字编号越大，优先级越高，优先级高的运算符先进行计算。

| 优先级 | 运算符                                                       |
| ------ | ------------------------------------------------------------ |
| 1      | =, :=（赋值）                                                |
| 2      | \|\|, OR, XOR                                                |
| 3      | &&, AND                                                      |
| 4      | NOT                                                          |
| 5      | BETWEEN, CASE, WHEN, THEN, ELSE                              |
| 6      | =(比较运算符), <=>, >=, >, <=, <, <>, !=, IS, LIKE, REGEXP, IN |
| 7      | \|                                                           |
| 8      | &                                                            |
| 9      | <<, >>                                                       |
| 10     | -, +                                                         |
| 11     | *, /, DIV, %, MOD                                            |
| 12     | ^                                                            |
| 13     | -(负号) 和 ~(按位取反)                                       |
| 14     | !                                                            |
| 15     | ( )，使用“()”括起来的表达式的优先级最高                      |


### MySQL函数

#### 1，数学函数
MySQL内置的数学函数主要对数字进行处理，主要分为绝对值函数、圆周率函数、获取整数的函数、返回列表中的最大值与最小值函数、角度与弧度互换函数、三角函数、乘方与开方函数、对数函数、随机函数、四舍五入与数字截取函数、符号函数、数学运算函数。

##### 1.1，绝对值函数
```sql
-- ABS(X)
```

##### 1.2，圆周率函数
```sql
-- PI()
```

##### 1.3，获取整数的函数
```sql
-- 1，CEIL(X)函数与CEILING(X)，获取大于或等于某值的最小整数
-- 2，FLOOR(X)函数，获取小于或等于某值的最大整数
```

##### 1.4，返回列表中的最大值与最小值函数
```sql
-- 1，LEAST(e1,e2,e3…)函数用于获取列表中的最小值，列表中的数据可以由数字组成，也可以由字符串组成。
-- 2，GREATEST(e1,e2,e3…)函数用于获取列表中的最大值，列表中的数据可以由数字组成，也可以由字符串组成。
```

##### 1.5，角度与弧度互换函数
```sql
-- 1，RADIANS(X)函数用于将角度转化为弧度，其中，参数X为角度值。
-- 2，DEGREES(X)函数可将弧度转化为角度，其中，参数X为弧度值。
```

##### 1.6，三角函数
```sql
-- 1，SIN(X)函数返回X的正弦值，其中，参数X为弧度值。
-- 2，ASIN(X)函数返回X的反正弦值，即获取正弦为X的值，如果X的值不在-1到1之间，则结果返回NULL。
-- 3，COS(X)函数返回X的余弦值，其中，参数X为弧度值
-- 4，ACOS(X)函数返回X的反余弦值，即返回余弦值为X的值，如果X的值不在-1到1之间，则返回NULL。
-- 5，TAN(X)函数返回X的正切值，其中，参数X为弧度值。
-- 6，ATAN(X)函数返回X的反正切值，即返回正切值为X的值。
-- 7，ATAN2(M,N)函数返回两个参数的反正切值。
  /**与ATAN(X)函数相比，ATAN2(M,N)需要两个参数，例如有两个点point(x1,y1)和point(x2,y2)，使用ATAN(X)函数计算反正切值为ATAN((y2-y1)/(x2-x1))，使用ATAN2(M,N)计算反正切值则为ATAN2(y2-y1,x2-x1)。由使用方式可以看出，当x2-x1等于0时，ATAN(X)函数会报错，而ATAN2(M,N)函数则仍然可以计算。*/
-- 8，COT(X)函数返回X的余切值，其中，X为弧度值。
```

##### 1.7，乘方与开方函数
```sql
-- 1，POW(X,Y)函数返回X的Y次方；POWER(X,Y)函数的作用与POW(X,Y)函数相同
-- 2，EXP(X)函数返回e的X次方，其中e是一个常数，在MySQL中这个常数e的值为2.718281828459045。
-- 3，SQRT(X)函数返回X的平方根，当X的值为负数时，返回NULL。
```

##### 1.8，对数函数
```sql
-- 1，LN(X)函数返回以e为底的X的对数，当X的值小于或者等于0时，返回的结果为NULL。
-- 2，LOG(X)函数的作用与LN(X)函数相同
-- 3，LOG10(X)函数返回以10为底的X的对数，当X的值小于或者等于0时，返回的结果为NULL。
-- 4，LOG2(X)函数返回以2为底的X的对数，当X的值小于或等于0时，返回NULL。
```

##### 1.9，随机函数
```sql
-- 1，RAND()函数返回一个0到1之间的随机数。
-- 2，RAND(X)函数返回一个范围在0到1之间的随机数，其中X的值用作种子值，相同的X值会产生重复的随机数。
mysql> SELECT RAND(10),RAND(10),RAND(0), RAND(-10);
+--------------------+--------------------+---------------------+--------------------+
| RAND(10)           | RAND(10)           | RAND(0)             | RAND(-10)          |
+--------------------+--------------------+---------------------+--------------------+
| 0.6570515219653505 | 0.6570515219653505 | 0.15522042769493574 | 0.6533893371498113 |
+--------------------+--------------------+---------------------+--------------------+
```

##### 1.10，四舍五入与数字截取函数
```sql
-- 1，ROUND(X)函数返回一个对X的值进行四舍五入后，最接近于X的整数。
-- 2，ROUND(X,Y)函数返回一个对X的值进行四舍五入后最接近X的值，并保留到小数点后面Y位。如果Y的值为0，作用与ROUND(X)函数相同，如果Y的值为负数，则保留到小数点左边Y位。
mysql> SELECT ROUND(3.745,2), ROUND(3.745, 0), ROUND(1308.789, -2);
+----------------+-----------------+---------------------+
| ROUND(3.745,2) | ROUND(3.745, 0) | ROUND(1308.789, -2) |
+----------------+-----------------+---------------------+
|           3.75 |               4 |                1300 |
+----------------+-----------------+---------------------+
-- 3，TRUNCATE(X,Y)函数对X的值进行截断处理，保留到小数点后Y位。如果Y的值为0，则保留整数部分，如果Y的值为负数，则保留到小数点左边Y位。
mysql> SELECT TRUNCATE(3.745,2), TRUNCATE(3.745, 0), TRUNCATE(156.1516, -2);
+-------------------+--------------------+------------------------+
| TRUNCATE(3.745,2) | TRUNCATE(3.745, 0) | TRUNCATE(156.1516, -2) |
+-------------------+--------------------+------------------------+
|              3.74 |                  3 |                    100 |
+-------------------+--------------------+------------------------+
/**
  注意：ROUND(X,Y) 和 TRUNCATE(X,Y) 的区别
 */
```

##### 1.11，符号函数
```sql
-- SIGN(X)函数将返回X的符号。如果X的值是一个正数，则结果返回1；如果X的值为0，则结果返回0；如果X的值是一个负数，则结果返回-1。
mysql> SELECT SIGN(100), SIGN(0), SIGN(-100); 
+-----------+---------+------------+
| SIGN(100) | SIGN(0) | SIGN(-100) |
+-----------+---------+------------+
|         1 |       0 |         -1 |
+-----------+---------+------------+
```

##### 1.12，数学运算函数
```sql
-- 1，DIV函数的使用方式为M DIV N，表示的含义为获取M除以N的整数结果值，当N为0时，将返回NULL。
mysql> SELECT 16 DIV 5, 16 DIV -5, -16 DIV 5, -16 DIV -5, 16 DIV 0;
+----------+-----------+-----------+------------+----------+
| 16 DIV 5 | 16 DIV -5 | -16 DIV 5 | -16 DIV -5 | 16 DIV 0 |
+----------+-----------+-----------+------------+----------+
|        3 |        -3 |        -3 |          3 |     NULL |
+----------+-----------+-----------+------------+----------+
-- 2，MOD(X,Y)函数返回X除以Y后的余数。当X能被Y整除时，返回0；当Y的值为0时，返回NULL。
mysql> SELECT MOD(16,5), MOD(16,-5), MOD(-16,5), MOD(-16,-5), MOD(16,0);
+-----------+------------+------------+-------------+-----------+
| MOD(16,5) | MOD(16,-5) | MOD(-16,5) | MOD(-16,-5) | MOD(16,0) |
+-----------+------------+------------+-------------+-----------+
|         1 |          1 |         -1 |          -1 |      NULL |
+-----------+------------+------------+-------------+-----------+

/**
  取余运算：取余运算在取商的值时，向 0 方向舍入(fix()函数)；
  取模运算：取模运算在计算商的值时，向负无穷方向舍入(floor()函数)。

  由此可见，MySQL中的 DIV 函数和 MOD(X,Y)函数，实际执行的是取余运算。
 */
```

#### 2，字符串函数
字符串函数主要用于处理数据库中的字符串数据

##### 2.1，ASCII(S)函数
```sql
-- ASCII(S)函数返回字符串S中的第一个字符的ASCII码值。
mysql> SELECT ASCII('abc');
+--------------+
| ASCII('abc') |
+--------------+
|           97 |
+--------------+
```
##### 2.2，CHAR_LENGTH(S)函数
```sql
-- CHAR_LENGTH(S)函数返回字符串S中的字符个数，与CHARACTER_LENGTH(S)函数相同
mysql> SELECT CHAR_LENGTH('hello'), CHAR_LENGTH('你好'), CHAR_LENGTH(' ');
+----------------------+-----------------------+------------------+
| CHAR_LENGTH('hello') | CHAR_LENGTH('你好')   | CHAR_LENGTH(' ') |
+----------------------+-----------------------+------------------+
|                    5 |                     2 |                1 |
+----------------------+-----------------------+------------------+
```
##### 2.3，LENGTH(S)函数
```sql
-- LENGTH(S)函数返回字符串S的长度，这里的长度指的是字节数。
-- 当MySQL使用UTF-8编码或utf8mb4编码时，一个字母占用的长度为1个字节、一个汉字占用的长度为3个字节、一个空格占用的长度为1个字节。
mysql> SELECT LENGTH('hello'), LENGTH('你好'), LENGTH(' ');               
+-----------------+------------------+-------------+
| LENGTH('hello') | LENGTH('你好')   | LENGTH(' ') |
+-----------------+------------------+-------------+
|               5 |                6 |           1 |
+-----------------+------------------+-------------+
```
##### 2.4，CONCAT(S1,S2,…,Sn)函数
```sql
-- CONCAT(S1,S2,…Sn)函数将字符串S1,S2,…,Sn合并为一个字符串。
-- 当函数中的任何一个字符串为NULL时，结果返回NULL。
mysql> SELECT CONCAT('hello', ' ', 'world'),CONCAT('hello', NULL, 'world');
+-------------------------------+--------------------------------+
| CONCAT('hello', ' ', 'world') | CONCAT('hello', NULL, 'world') |
+-------------------------------+--------------------------------+
| hello world                   | NULL                           |
+-------------------------------+--------------------------------+
```
##### 2.5，CONCAT_WS(X,S1,S2,…,Sn)函数
```sql
-- CONCAT_WS(X,S1,S2,…,Sn)函数将字符串S1,S2,…,Sn拼接成一个以X分隔的字符串，其中，X可以是一个字符串，也可以是其他合法的参数。
-- 如果分隔符X为NULL，则结果返回NULL。
-- 如果字符串S1,S2,…,Sn中的任何一个字符串为NULL，则函数会忽略为NULL的字符串。
mysql> SELECT CONCAT_WS(',','a','b'),CONCAT_WS(NULL,'a','b'),CONCAT_WS(',','a',NULL,'b');
+------------------------+-------------------------+-----------------------------+
| CONCAT_WS(',','a','b') | CONCAT_WS(NULL,'a','b') | CONCAT_WS(',','a',NULL,'b') |
+------------------------+-------------------------+-----------------------------+
| a,b                    | NULL                    | a,b                         |
+------------------------+-------------------------+-----------------------------+
```
##### 2.6，INSERT(oldstr,x,y,replacestr)函数
```sql
-- INSERT(oldstr,x,y,replacestr)函数将字符串oldstr从第x位置开始的y个字符长度的子字符串替换为replacestr。
-- MySQL 初始位置从 1 开始
mysql> SELECT INSERT('hello world',1,5,'hi'); 
+--------------------------------+
| INSERT('hello world',1,5,'hi') |
+--------------------------------+
| hi world                        |
+--------------------------------+
```
##### 2.7，LOWER(S)函数，UPPER(S)函数
```sql
-- LOWER(S)函数将字符串S转化为小写。LCASE(S)函数的作用与LOWER(S)函数相同。
-- UPPER(S)函数将字符串S转化为大写。UCASE(S)函数的作用与UPPER(S)函数相同。
mysql> SELECT LOWER('HELLO WORLD'), LOWER('Hello World'),LCASE('HELLO WORLD'), LCASE('Hello World'),UPPER('hello world'), UPPER('Hello World');
+----------------------+----------------------+----------------------+----------------------+----------------------+----------------------+
| LOWER('HELLO WORLD') | LOWER('Hello World') | LCASE('HELLO WORLD') | LCASE('Hello World') | UPPER('hello world') | UPPER('Hello World') |
+----------------------+----------------------+----------------------+----------------------+----------------------+----------------------+
| hello world          | hello world          | hello world          | hello world          | HELLO WORLD          | HELLO WORLD          |
+----------------------+----------------------+----------------------+----------------------+----------------------+----------------------+

```
##### 2.8，LEFT(str,x)函数，RIGHT(str,x)函数
```sql
-- LEFT(str,x)函数返回字符串str最左边的x个字符组成的字符串，如果x的值为NULL，则返回NULL。
-- RIGHT(str,x)函数返回字符串str最右边的x个字符组成的字符串，如果x的值为NULL，则返回NULL。
mysql> SELECT LEFT('hello world', 5), LEFT('hello world', NULL),RIGHT('hello world', 5), RIGHT('hello world', NULL);
+------------------------+---------------------------+-------------------------+----------------------------+
| LEFT('hello world', 5) | LEFT('hello world', NULL) | RIGHT('hello world', 5) | RIGHT('hello world', NULL) |
+------------------------+---------------------------+-------------------------+----------------------------+
| hello                  | NULL                      | world                   | NULL                       |
+------------------------+---------------------------+-------------------------+----------------------------+
```
##### 2.9，RPAD(str,n,pstr)函数
```sql
-- RPAD(str,n,pstr)函数使用字符串pstr对字符串str最右边进行填充，直到str字符串的长度达到n为止。
mysql> SELECT RPAD('hello', 14, ' world');
+-----------------------------+
| RPAD('hello', 14, ' world') |
+-----------------------------+
| hello world wo              |
+-----------------------------+
```
##### 2.10，LTRIM(S)函数，RTRIM(S)函数，TRIM(S)函数
```sql
-- LTRIM(S)函数用于去除字符串S左边的空格。
-- RTRIM(S)函数用于去除字符串S右边的空格。
-- TRIM(S)函数用于去除字符串S两边的空格。
mysql> SELECT LTRIM('  hello World '),RTRIM('  hello World '),TRIM('  hello World ');
+-------------------------+-------------------------+------------------------+
| LTRIM('  hello World ') | RTRIM('  hello World ') | TRIM('  hello World ') |
+-------------------------+-------------------------+------------------------+
| hello World             |   hello World           | hello World            |
+-------------------------+-------------------------+------------------------+
```
##### 2.11，REPEAT(str,x)函数
```sql
-- REPEAT(str,x)函数用于返回重复x次str的结果数据。
mysql> SELECT REPEAT('hello World! ', 3);
+-----------------------------------------+
| REPEAT('hello World! ', 3)              |
+-----------------------------------------+
| hello World! hello World! hello World!  |
+-----------------------------------------+
```
##### 2.12，REPLACE(S,A,B)函数
```sql
-- REPLACE(S,A,B)函数用字符串B替换字符串S中出现的所有字符串A，并返回替换后的字符串。
mysql> SELECT REPLACE('hello world, hello mysql', 'hello', 'hi');
+----------------------------------------------------+
| REPLACE('hello world, hello mysql', 'hello', 'hi') |
+----------------------------------------------------+
| hi world, hi mysql                                  |
+----------------------------------------------------+
```
##### 2.13，STRCMP(S1,S2)函数
```sql
-- STRCMP(S1,S2)函数用于比较字符串S1和字符串S2的ASCII码值的大小。
-- 如果S1的ASCII码值比S2的ASCII码值小，则返回-1；如果S1的ASCII码值与S2的ASCII码值相等，则返回0；如果S1的ASCII码值大于S2的ASCII码值，则返回1。
mysql> SELECT STRCMP('abc', 'abd'), STRCMP('acd', 'abc'), STRCMP('abc','abc');
+----------------------+----------------------+---------------------+
| STRCMP('abc', 'abd') | STRCMP('acd', 'abc') | STRCMP('abc','abc') |
+----------------------+----------------------+---------------------+
|                   -1 |                    1 |                   0 |
+----------------------+----------------------+---------------------+
```
##### 2.14，SUBSTR(S,X,Y)函数，SUBSTRING(S,X,Y)函数，MID(S,X,Y)函数
```sql
-- SUBSTR(S,X,Y)函数返回从字符串S中从第X个位置开始，长度为Y的子字符串。当X的值小于0时，则是倒数第几个位置。
-- SUBSTRING(S,X,Y)函数，MID(S,X,Y)函数的作用与SUBSTR(S,X,Y)函数相同
mysql> SELECT SUBSTR('helloWorld',1,5), SUBSTR('helloWorld',0,5);
+--------------------------+--------------------------+
| SUBSTR('helloWorld',1,5) | SUBSTR('helloWorld',0,5) |
+--------------------------+--------------------------+
| hello                    |                          |
+--------------------------+--------------------------+

mysql> SELECT SUBSTR('helloWorld',-5,4), SUBSTR('helloWorld', -5, 999);
+---------------------------+-------------------------------+
| SUBSTR('helloWorld',-5,4) | SUBSTR('helloWorld', -5, 999) |
+---------------------------+-------------------------------+
| Worl                      | World                         |
+---------------------------+-------------------------------+
```
##### 2.15，SUBSTRING_INDEX(string, delimiter, number)函数
```sql
-- SUBSTRING_INDEX() 函数返回一个字符串在出现指定数量的分隔符之前的子字符串。
-- number是正数，返回delimiter左边的子字符串；number是负数，返回delimiter右边的子字符串；
mysql> SELECT SUBSTRING_INDEX("www.w3schools.com", ".", 1),SUBSTRING_INDEX("www.w3schools.com", ".", 2);
+----------------------------------------------+----------------------------------------------+
| SUBSTRING_INDEX("www.w3schools.com", ".", 1) | SUBSTRING_INDEX("www.w3schools.com", ".", 2) |
+----------------------------------------------+----------------------------------------------+
| www                                          | www.w3schools                                |
+----------------------------------------------+----------------------------------------------+

mysql> SELECT SUBSTRING_INDEX("www.w3schools.com", ".", -1),SUBSTRING_INDEX("www.w3schools.com", ".", -2);
+-----------------------------------------------+-----------------------------------------------+
| SUBSTRING_INDEX("www.w3schools.com", ".", -1) | SUBSTRING_INDEX("www.w3schools.com", ".", -2) |
+-----------------------------------------------+-----------------------------------------------+
| com                                           | w3schools.com                                 |
+-----------------------------------------------+-----------------------------------------------+

mysql> SELECT SUBSTRING_INDEX("www.w3schools.com", ".", 3),SUBSTRING_INDEX("www.w3schools.com", ".", -3);
+----------------------------------------------+-----------------------------------------------+
| SUBSTRING_INDEX("www.w3schools.com", ".", 3) | SUBSTRING_INDEX("www.w3schools.com", ".", -3) |
+----------------------------------------------+-----------------------------------------------+
| www.w3schools.com                            | www.w3schools.com                             |
+----------------------------------------------+-----------------------------------------------+
```

##### 2.16，SPACE(X)函数
```sql
-- SPACE(X)函数返回一个由X个空格组成的字符串。
mysql> SELECT CONCAT('*', SPACE(6), '*');
+----------------------------+
| CONCAT('*', SPACE(6), '*') |
+----------------------------+
| *       *                  |
+----------------------------+
```
##### 2.17，LOCATE(substr,str)函数，POSITION(substr IN str)函数，INSTR(str,substr)函数
```sql
-- LOCATE(substr,str)函数返回字符串substr在字符串str中的位置。
-- POSITION(substr IN str)函数作用与LOCATE(substr,str)函数相同，返回字符串substr在字符串str中的位置。
-- INSTR(str,substr)函数的作用与LOCATE(substr,str)函数相同，只不过需要注意参数的先后顺序
mysql> SELECT LOCATE('hello', 'hello,hello'),POSITION('hello' IN 'hello,hello'),INSTR('hello,hello', 'hello');
+--------------------------------+------------------------------------+-------------------------------+
| LOCATE('hello', 'hello,hello') | POSITION('hello' IN 'hello,hello') | INSTR('hello,hello', 'hello') |
+--------------------------------+------------------------------------+-------------------------------+
|                              1 |                                  1 |                             1 |
+--------------------------------+------------------------------------+-------------------------------+
```
##### 2.18，ELT(M,S1,S2,…,Sn)函数
```sql
-- ELT(M,S1,S2,…,Sn)函数返回指定指定位置的字符串，如果M=1，则返回S1，如果M=2，则返回S2，如果M=n，则返回Sn。
mysql> SELECT ELT(2, 'hello','world'),ELT(0, 'hello','world'),ELT(3, 'hello','world');
+-------------------------+-------------------------+-------------------------+
| ELT(2, 'hello','world') | ELT(0, 'hello','world') | ELT(3, 'hello','world') |
+-------------------------+-------------------------+-------------------------+
| world                   | NULL                    | NULL                    |
+-------------------------+-------------------------+-------------------------+
```
##### 2.19，FIELD(S,S1,S2,…,Sn)函数
```sql
-- FIELD(S,S1,S2,…,Sn)函数返回字符串S在字符串列表中第一次出现的位置。当字符串列表中不存在S时，则返回0；当S为NULL时，则返回0。
mysql> SELECT FIELD('hello', 'hello', 'world'), FIELD('hi', 'hello', 'world'), FIELD(NULL, 'hello', 'world');
+----------------------------------+-------------------------------+-------------------------------+
| FIELD('hello', 'hello', 'world') | FIELD('hi', 'hello', 'world') | FIELD(NULL, 'hello', 'world') |
+----------------------------------+-------------------------------+-------------------------------+
|                                1 |                             0 |                             0 |
+----------------------------------+-------------------------------+-------------------------------+
```
##### 2.20，FIND_IN_SET(S1,S2)函数
```sql
-- FIND_IN_SET(S1,S2)函数返回字符串S1在字符串S2中出现的位置。
-- 其中，字符串S2是一个以逗号分隔的字符串。如果S1不在S2中，或者S2为空字符串，则返回0。当S1或S2为NULL时，返回NULL。
mysql> SELECT FIND_IN_SET('hello', 'hello,world'),FIND_IN_SET('he', 'hello,world'),FIND_IN_SET(NULL, 'hello,world');
+-------------------------------------+----------------------------------+----------------------------------+
| FIND_IN_SET('hello', 'hello,world') | FIND_IN_SET('he', 'hello,world') | FIND_IN_SET(NULL, 'hello,world') |
+-------------------------------------+----------------------------------+----------------------------------+
|                                   1 |                                0 |                             NULL |
+-------------------------------------+----------------------------------+----------------------------------+
```
##### 2.21，REVERSE(S)函数
```sql
-- REVERSE(S)函数返回与字符串S顺序完全相反的字符串，即将字符串S反转。 
mysql> SELECT REVERSE('hello,world!');
+-------------------------+
| REVERSE('hello,world!') |
+-------------------------+
| !dlrow,olleh            |
+-------------------------+
```
##### 2.22，NULLIF(value1,value2)函数
```sql
-- NULLIF(value1,value2)函数用于比较两个字符串，如果value1与value2相等，则返回NULL，否则返回value1。
mysql> SELECT NULLIF('mysql','mysql'),NULLIF('mysql', '');
+-------------------------+---------------------+
| NULLIF('mysql','mysql') | NULLIF('mysql', '') |
+-------------------------+---------------------+
| NULL                    | mysql               |
+-------------------------+---------------------+
```
##### 2.23，GROUP_CONCAT()函数
```sql
-- GROUP_CONCAT()函数将组中的字符串连接成为具有各种选项的单个字符串。
-- GROUP_CONCAT(DISTINCT X ORDER BY Y ASC SEPARATOR '字符')函数

-- 插入数据
Create table If Not Exists Activities (sell_date date, product varchar(20));
insert into Activities (sell_date, product) values 
('2020-05-30', 'Headphone')
,('2020-06-01', 'Pencil')
,('2020-06-02', 'Mask')
,('2020-05-30', 'Basketball')
,('2020-06-01', 'Bible')
,('2020-06-02', 'Mask')
,('2020-05-30', 'T-Shirt')
;
-- GROUP_CONCAT()函数使用
mysql> select 
sell_date,
count(DISTINCT product) AS num_sold,
-- ORDER BY product ASC 可以省略，此时默认升序排列;
-- SEPARATOR ',' 可以省略，此时默认使用','分割
GROUP_CONCAT(DISTINCT product ORDER BY product ASC SEPARATOR ',') AS products
from Activities 
group by sell_date;
+------------+----------+------------------------------+
| sell_date  | num_sold | products                     |
+------------+----------+------------------------------+
| 2020-05-30 |        3 | Basketball,Headphone,T-Shirt |
| 2020-06-01 |        2 | Bible,Pencil                 |
| 2020-06-02 |        1 | Mask                         |
+------------+----------+------------------------------+
```

#### 3，日期和时间函数

##### 3.1，CURDATE()函数，CURRENT_DATE()函数
```sql
-- CURDATE()函数用于返回当前日期，只包含年、月、日部分，格式为YYYY-MM-DD。
```
##### 3.2，CURTIME()函数，CURRENT_TIME()函数
```sql
-- CURTIME()函数用于返回当前时间，只包含时、分、秒部分，格式为HH:MM:SS。
```
##### 3.3，NOW()函数，CURRENT_TIMESTAMP()函数，LOCALTIME()函数，LOCALTIMESTAMP()函数，SYSDATE()函数
```sql
-- 返回当前日期和时间，包含年、月、日、时、分、秒，格式为YYYY-MM-DD HH:MM:SS
```
##### 3.4，UNIX_TIMESTAMP(date)函数
```sql
-- 将date转化为UNIX时间戳。
mysql> SELECT UNIX_TIMESTAMP(now());
+-----------------------+
| UNIX_TIMESTAMP(now()) |
+-----------------------+
|            1668523299 |
+-----------------------+
```
##### 3.5，FROM_UNIXTIME(timestamp)函数
```sql
-- FROM_UNIXTIME(timestamp)函数将UNIX时间戳转化为日期时间，格式为YYYY-MM-DD HH:MM:SS，与UNIX_TIMESTAMP(date)函数互为反函数。
mysql> select FROM_UNIXTIME(1668523299);
+---------------------------+
| FROM_UNIXTIME(1668523299) |
+---------------------------+
| 2022-11-15 22:41:39       |
+---------------------------+
```
##### 3.6，UTC_DATE()函数，UTC_TIME()函数
```sql
-- UTC_DATE()函数用于返回UTC日期。
-- UTC_TIME()函数用于返回UTC时间。
mysql> select UTC_DATE(),UTC_TIME();
+------------+------------+
| UTC_DATE() | UTC_TIME() |
+------------+------------+
| 2022-11-15 | 14:43:25   |
+------------+------------+
```
##### 3.7，YEAR(date)函数，MONTH(date)函数，DAY(date)函数
```sql
-- YEAR(date)函数用于返回日期所在的年份，取值返回为1970～2069。
-- MONTH(date)函数用于返回日期对应的月份，取值返回为1～12。
-- DAY(date)函数只返回日期。
mysql> select YEAR('2022-11-15'),MONTH('2022-11-15'),DAY('2022-11-15');
+-------------+--------------+------------+
| YEAR('2022-11-15') | MONTH('2022-11-15') | DAY('2022-11-15') |
+-------------+--------------+------------+
|        2022 |           11 |         15 |
+-------------+--------------+------------+
```
##### 3.8，MONTHNAME(date)函数，DAYNAME(date)函数
```sql
-- MONTHNAME(date)函数用于返回日期所在月份的英文名称。
-- DAYNAME(date)函数用于返回日期对应星期的英文名称。
mysql> select monthname('2022-11-15'),dayname('2022-11-15');
+------------------+----------------+
| monthname('2022-11-15') | dayname('2022-11-15') |
+------------------+----------------+
| November         | Tuesday        |
+------------------+----------------+
```
##### 3.9，WEEK(date)函数，WEEKDAY(date)函数，DAYOFWEEK(date)函数
```sql
-- WEEK(date)函数返回给定日期是一年中的第几周。
-- WEEKDAY(date)函数返回日期对应的一周中的索引值。0表示星期一，1表示星期二，以此类推。
-- DAYOFWEEK(date)函数用于返回日期对应的一周中的索引值。1表示星期日，2表示星期一，以此类推。
mysql> select week('2022-11-15'),weekday('2022-11-15'),dayofweek('2022-11-15');
+--------------------+-----------------------+-------------------------+
| week('2022-11-15') | weekday('2022-11-15') | dayofweek('2022-11-15') |
+--------------------+-----------------------+-------------------------+
|                 46 |                     1 |                       3 |
+--------------------+-----------------------+-------------------------+
```
##### 3.10，WEEKOFYEAR(date)函数，DAYOFYEAR(date)函数，DAYOFMONTH(date)函数
```sql
-- WEEKOFYEAR(date)函数返回日期位于一年中的第几周。
-- DAYOFYEAR(date)函数返回日期是一年中的第几天。
-- DAYOFMONTH(date)函数返回日期位于所在月份的第几天。
mysql> select weekofyear('2022-11-15'),dayofyear('2022-11-15'),dayofmonth('2022-11-15');
+--------------------------+-------------------------+--------------------------+
| weekofyear('2022-11-15') | dayofyear('2022-11-15') | dayofmonth('2022-11-15') |
+--------------------------+-------------------------+--------------------------+
|                       46 |                     319 |                       15 |
+--------------------------+-------------------------+--------------------------+
```
##### 3.11，QUARTER(date)函数
```sql
-- QUARTER(date)函数返回日期对应的季度，范围为1～4。
mysql> select QUARTER('2022-11-15');
+-----------------------+
| QUARTER('2022-11-15') |
+-----------------------+
|                     4 |
+-----------------------+
```
##### 3.11，HOUR(time)函数，MINUTE(time)函数，SECOND(time)函数
```sql
-- HOUR(time)函数返回指定时间的小时。
-- MINUTE(time)函数返回指定时间的分钟，取值范围0～59。
-- SECOND(time)函数返回指定时间的秒数，取值范围0～59。
mysql> select hour('22:58:58'),minute('22:58:58'),second('22:58:58');
+------------------+--------------------+--------------------+
| hour('22:58:58') | minute('22:58:58') | second('22:58:58') |
+------------------+--------------------+--------------------+
|               22 |                 58 |                 58 |
+------------------+--------------------+--------------------+
```
##### 3.12，EXTRACT(type FROM date)函数
```sql
-- EXTRACT(type FROM date)函数返回指定日期中特定的部分，type指定返回的值。
/** type值
  1,MICROSECOND  2,SECOND  3,MINUTE  4,HOUR  5,DAY
  6,WEEK  7,MONTH  8,QUARTER  9,YEAR
 */
mysql> select extract(year_month_day from '2022-11-15');
+---------------------------------------+
| extract(year_month from '2022-11-15') |
+---------------------------------------+
|                                202211 |
+---------------------------------------+
```
##### 3.13，TIME_TO_SEC(time)函数，SEC_TO_TIME(seconds)函数
```sql
-- TIME_TO_SEC(time)函数将time转化为秒并返回结果值。转化的公式为：小时*3600+分钟*60+秒。
-- SEC_TO_TIME(seconds)函数将seconds描述转化为包含小时、分钟和秒的时间。
mysql> select TIME_TO_SEC('22:58:58'),SEC_TO_TIME(82738);
+-------------------------+--------------------+
| TIME_TO_SEC('22:58:58') | SEC_TO_TIME(82738) |
+-------------------------+--------------------+
|                   82738 | 22:58:58           |
+-------------------------+--------------------+
```
##### 3.14，DATE_ADD(date,INTERVAL expr type)函数
```sql
-- DATE_ADD(date,INTERVAL expr type)函数返回与date相差INTERVAL时间间隔的日期，本质上是日期的加操作。
-- ADDDATE(date,INTERVAL expr type)函数与DATE_ADD(date,INTERVAL expr type)函数的作用相同。
-- DATE_SUB(date,INTERVAL expr type)函数，日期减操作。
-- SUBDATE(date,INTERVAL expr type)函数与DATE_SUB(date,INTERVAL expr type)函数作用相同。
-- 这四个均可以指定负值
/** type值
  1,YEAR  2,MONTH  3,DAY  4,HOUR  5,MINUTE  6,SECOND
  7,YEAR_MONTH  8,DAY_HOUR  9,DAY_MINUTE  10,DAY_SECOND
  11,HOUR_MINUTE  12,HOUR_SECOND  13,MINUTE_SECOND
 */
mysql> select DATE_ADD('2022-10-15',INTERVAL 0002 YEAR_MONTH);
+-------------------------------------------------+
| DATE_ADD('2022-10-15',INTERVAL 0002 YEAR_MONTH) |
+-------------------------------------------------+
| 2022-12-15                                      |
+-------------------------------------------------+
```
##### 3.15，ADDTIME(time1,time2)函数，SUBTIME(time1,time2)函数
```sql
-- ADDTIME(time1,time2)函数返回time1加上time2的时间。其中，time2是一个表达式，也可以是一个数字，当time2为一个数字时，代表的是秒。
-- SUBTIME(time1,time2)函数返回time1减去time2后的时间。其中，time2是一个表达式，也可以是一个数字，当time2为一个数字时，代表的是秒。
mysql> select ADDTIME('2022-11-15 22:45:45','1:1:1'),ADDTIME('2022-11-15 22:45:45',10);
+----------------------------------------+-----------------------------------+
| ADDTIME('2022-11-15 22:45:45','1:1:1') | ADDTIME('2022-11-15 22:45:45',10) |
+----------------------------------------+-----------------------------------+
| 2022-11-15 23:46:46                    | 2022-11-15 22:45:55               |
+----------------------------------------+-----------------------------------+
    
mysql> select SUBTIME('2022-11-15 22:45:45','1:1:1'),SUBTIME('2022-11-15 22:45:45',10);
+----------------------------------------+-----------------------------------+
| SUBTIME('2022-11-15 22:45:45','1:1:1') | SUBTIME('2022-11-15 22:45:45',10) |
+----------------------------------------+-----------------------------------+
| 2022-11-15 21:44:44                    | 2022-11-15 22:45:35               |
+----------------------------------------+-----------------------------------+
```
##### 3.16，DATEDIFF(date1,date2)函数
```sql
-- DATEDIFF(date1,date2)函数计算两个日期之间相差的天数。 date1 - date2
mysql> select DATEDIFF('2022-11-15','2022-10-15'),DATEDIFF('2022-10-15','2022-11-15');
+-------------------------------------+-------------------------------------+
| DATEDIFF('2022-11-15','2022-10-15') | DATEDIFF('2022-10-15','2022-11-15') |
+-------------------------------------+-------------------------------------+
|                                  31 |                                 -31 |
+-------------------------------------+-------------------------------------+
```
##### 3.17，FROM_DAYS(N)函数
```sql
-- FROM_DAYS(N)函数返回从0000年1月1日起，N天以后的日期。
mysql> select FROM_DAYS(500),FROM_DAYS(365),FROM_DAYS(100);
+----------------+----------------+----------------+
| FROM_DAYS(500) | FROM_DAYS(365) | FROM_DAYS(100) |
+----------------+----------------+----------------+
| 0001-05-15     | 0000-00-00     | 0000-00-00     |
+----------------+----------------+----------------+
```
##### 3.18，LAST_DAY(date)函数
```sql
-- LAST_DAY(date)函数返回date所在月份的最后一天的日期。
mysql> select LAST_DAY('2022-11-15');
+------------------------+
| LAST_DAY('2022-11-15') |
+------------------------+
| 2022-11-30             |
+------------------------+
```
##### 3.19，MAKEDATE(year,n)函数
```sql
-- MAKEDATE(year,n)函数针对给定年份与所在年份中的天数返回一个日期。
mysql> select MAKEDATE(2022,1),MAKEDATE(2022,365);
+------------------+--------------------+
| MAKEDATE(2022,1) | MAKEDATE(2022,365) |
+------------------+--------------------+
| 2022-01-01       | 2022-12-31         |
+------------------+--------------------+
```
##### 3.20，MAKETIME(hour,minute,second)函数
```sql
-- 将给定的小时、分钟和秒组合成时间并返回。
mysql> select MAKETIME(22,55,55);
+--------------------+
| MAKETIME(22,55,55) |
+--------------------+
| 22:55:55           |
+--------------------+
```
##### 3.21，PERIOD_ADD(time,n)函数
```sql
-- PERIOD_ADD(time,n)函数返回time加上n后的时间。
```
##### 3.22，TO_DAYS(date)函数
```sql
-- TO_DAYS(date)函数返回日期date距离0000年1月1日的天数。
mysql> select TO_DAYS('2022-11-15');
+-----------------------+
| TO_DAYS('2022-11-15') |
+-----------------------+
|                738839 |
+-----------------------+
```
##### 3.23，DATE_FORMAT(date,format)函数
```sql
-- DATE_FORMAT(date,format)函数按照指定的格式format来格式化日期date。
/** format常用的格式符 【%Y-%m-%d %H:%i:%s】
  1,%a 星期名缩写      
  2,%b 月名缩写   
  3,%c 月数值        
  4,%D 带有应为前缀的月份中的天  
  5,%d 月的天（00-31）
  6,%e 月的天（00-31） 
  7,%f 微妙      
  8,%H 小时（00-23）  
  9,%h 小时（01-12）           
  10,%I 小时（01-12） 
  11,%i 分钟（00-59）  
  12,%j 年的天（001-366）  
  13,%k 小时（00-23）  
  14,%l 小时（01-12）  
  15,%M 月名 
  16,%m 月（00-12）    
  17,%p AM或PM  
  18,%r 时间，12小时（hh:mm:ss AM或PM）  
  19,%S 秒（00-59）  
  20,%s 秒（00-59）
  21,%T 时间，24小时（hhh:mm:ss）  
  22,%U 周（00-53）星期日是一周的第一天  
  23,%u 周（00-53）星期一是一周的第一天  
  24,%V 周（01-53）星期日是一周的第一天，与%X使用   
  25,%v 周（01-53）星期一是一周的第一天，与%x使用 
  26,%X 年，其中的星期日是周的第一天，4位，与%V使用  
  27,%x 年，其中的星期一是周的第一天，4位，与%v使用
  28,%W 星期名  
  29,%w 周的天（0=星期日）  
  30,%Y 年，4位  
  31,%y 年，2位
 */
mysql> select DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s');
+----------------------------------------+
| DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s') |
+----------------------------------------+
| 2022-11-15 22:15:34                    |
+----------------------------------------+
```
##### 3.24，TIME_FORMAT(time,format)函数
```sql
-- TIME_FORMAT(time,format)函数按照指定的格式format来格式化日期date。
```
##### 3.25，STR_TO_DATE(str,format)函数
```sql
-- STR_TO_DATE(str,format)函数将字符串str按照format格式转化为日期或时间。
mysql> select STR_TO_DATE('2022-11-15 22:15:15','%Y-%m-%d %H:%i:%s');
+--------------------------------------------------------+
| STR_TO_DATE('2022-11-15 22:15:15','%Y-%m-%d %H:%i:%s') |
+--------------------------------------------------------+
| 2022-11-15 22:15:15                                    |
+--------------------------------------------------------+
```
##### 3.26，GET_FORMAT(date_type,format_type)函数
```sql
-- GET_FORMAT(date_type,format_type)函数返回日期字符串的显示格式，其中date_type表示日期类型，format_type表示格式化类型。
mysql> select GET_FORMAT(DATE,'USA'),GET_FORMAT(DATETIME,'USA'),GET_FORMAT(DATE,'ISO'),GET_FORMAT(DATETIME,'ISO');
+------------------------+----------------------------+------------------------+----------------------------+
| GET_FORMAT(DATE,'USA') | GET_FORMAT(DATETIME,'USA') | GET_FORMAT(DATE,'ISO') | GET_FORMAT(DATETIME,'ISO') |
+------------------------+----------------------------+------------------------+----------------------------+
| %m.%d.%Y               | %Y-%m-%d %H.%i.%s          | %Y-%m-%d               | %Y-%m-%d %H:%i:%s          |
+------------------------+----------------------------+------------------------+----------------------------+
```

#### 4，流程处理函数
流程处理函数可以根据不同的条件，执行不同的处理流程，可以在SQL语句中实现不同的条件选择。MySQL中的流程处理函数主要包括IF()、IFNULL()和CASE()函数。

##### 4.1，IF(value,value1,value2)函数
```sql
-- 如果value的值为TRUE，则IF()函数返回value1，否则返回value2。
mysql> SELECT IF(1<2, 1, 0), IF(1 > 2, 'yes', 'no');
+---------------+------------------------+
| IF(1<2, 1, 0) | IF(1 > 2, 'yes', 'no') |
+---------------+------------------------+
|              1 | no                     |
+---------------+------------------------+
```

##### 4.2，IFNULL(value1,value2)函数
```sql
-- 如果value1不为NULL，则IFNULL()函数返回value1，否则返回value2。
mysql> SELECT IFNULL('hello', 'mysql'), IFNULL(NULL, 'mysql'), IFNULL(10/0, 'mysql'); 
+--------------------------+-----------------------+-----------------------+
| IFNULL('hello', 'mysql') | IFNULL(NULL, 'mysql') | IFNULL(10/0, 'mysql') |
+--------------------------+-----------------------+-----------------------+
| hello                     | mysql                  | mysql                 |
+--------------------------+-----------------------+-----------------------+
```

##### 4.3，CASE WHEN THEN函数
```sql
-- CASE WHEN THEN函数
mysql> SELECT CASE WHEN 1 > 0 THEN 'yes' WHEN 1 <= 0 THEN 'no' ELSE 'unknown' END;
+---------------------------------------------------------------------+
| CASE WHEN 1 > 0 THEN 'yes' WHEN 1 <= 0 THEN 'no' ELSE 'unknown' END |
+---------------------------------------------------------------------+
| yes                                                                  |
+---------------------------------------------------------------------+

-- CASE expr WHEN函数
mysql> SELECT CASE 1 WHEN 0 THEN 0 WHEN 1 THEN 1 ELSE -1 END;
+------------------------------------------------+
| CASE 1 WHEN 0 THEN 0 WHEN 1 THEN 1 ELSE -1 END |
+------------------------------------------------+
|                                               1 |
+------------------------------------------------+
```

#### 5，聚合函数
聚合函数是一类对数据库中的数据进行聚合统计的函数。MySQL中提供的聚合函数主要包括COUNT函数、MAX函数、MIN函数、SUM函数和AVG函数。
聚合函数不能嵌套调用。

##### 5.1，COUNT(*、字段名、1)函数
```sql
/**1、COUNT(expr) ，返回SELECT语句检索的行中expr的值不为NULL的数量。结果是一个BIGINT值。
   2、如果查询结果没有命中任何记录，则返回0
   3、但是，值得注意的是，COUNT(*) 的统计结果中，会包含值为NULL的行数。
 */
```
> 1，count(1)、count(*) 与 count(列名)的区别？
> 1.1), COUNT(*)是SQL92定义的标准统计行数的语法，因为他是标准语法，所以MySQL数据库对他进行过很多优化。
> 1.2), COUNT(1)和 COUNT(*)，MySQL的优化是完全一样的，根本不存在谁比谁快。阿里巴巴Java开发手册中强制要求不让使用 COUNT(列名)或 COUNT(常量)来替代 COUNT(*)。
> 1.3), COUNT(字段)，他的查询简单粗暴，就是进行全表扫描，然后判断指定字段的值是不是为NULL，不为NULL则累加。相比COUNT(*)，COUNT(字段)多了一个步骤就是判断所查询的字段是否为NULL，所以他的性能要比COUNT(*)慢。
>
> 2，MySQL对COUNT(*)的优化？
> 2.1), 因为MyISAM的锁是表级锁，所以同一张表上面的操作需要串行进行，所以，MyISAM做了一个简单的优化，那就是它可以把表的总行数单独记录下来，如果从一张表中使用COUNT(*)进行查询的时候，可以直接返回这个记录下来的数值就可以了，【当然，前提是不能有where条件】。
> 2.2), InnoDB不能使用这种缓存操作，因为支持事务，大部分操作都是行级锁，行可能被并行修改，那么缓存记录不准确。但是，InnoDB还是针对COUNT(*)语句做了些优化的，【前提是查询语句中不包含WHERE或GROUP BY等条件】。
> COUNT(*)的目的只是为了统计总行数，所以，他根本不关心自己查到的具体值，所以，他如果能够在扫表的过程中，选择一个成本较低的索引进行的话，那就可以大大节省时间。
> InnoDB中索引分为聚簇索引（主键索引）和非聚簇索引（非主键索引），聚簇索引的叶子节点中保存的是整行记录，而非聚簇索引的叶子节点中保存的是该行记录的主键的值。
> 相比之下，非聚簇索引要比聚簇索引小很多，所以MySQL会优先选择最小的非聚簇索引来扫表。所以，当我们建表的时候，除了主键索引以外，创建一个非主键索引还是有必要的。

##### 5.2，MAX(字段名称)函数
```sql
-- MAX(字段名称)函数返回数据表中某列的最大值
```

##### 5.3，MIN(字段名称)函数
```sql
-- MIN(字段名称)函数返回数据表中某列的最小值
```

##### 5.4，SUM(字段名称)函数
```sql
-- SUM(字段名称)函数返回数据表中某列数据的求和结果
mysql> SELECT SUM(case when age > 17 then 1 else 0 end) FROM employee;
+----------+
| SUM(case when age > 17 then 1 else 0 end) |
+----------+
|      5 |
+----------+
```

##### 5.5，AVG(字段名称)函数
```sql
-- AVG(字段名称)函数返回数据表中某列数据的平均值。
mysql> SELECT AVG(age) FROM employee;
+----------+
| AVG(age) |
+----------+
|  22.2000 |
+----------+
```

#### 6，获取MySQL信息函数
MySQL中内置了一些可以查询MySQL信息的函数，这些函数主要用于帮助数据库开发或运维人员更好地对数据库进行维护工作。

##### 6.1，VERSION()函数
```sql
-- VERSION()函数返回当前MySQL的版本号。
mysql> SELECT VERSION();
+-----------+
| VERSION() |
+-----------+
| 8.0.18    |
+-----------+
```

##### 6.2，CONNECTION_ID()函数
```sql
-- CONNECTION_ID()函数返回当前MySQL服务器的连接数。
mysql> SELECT CONNECTION_ID();
+-----------------+
| CONNECTION_ID() |
+-----------------+
|               8 |
+-----------------+
```

##### 6.3，DATABASE()函数、SCHEMA()函数
```sql
-- DATABASE()函数返回MySQL命令行当前所在的数据库。
mysql> SELECT DATABASE();
+------------+
| DATABASE() |
+------------+
| test       |
+------------+
-- SCHEMA()函数的作用与DATABASE()函数相同。
mysql> SELECT SCHEMA();
+----------+
| SCHEMA() |
+----------+
| test     |
+----------+
```

##### 6.4，USER()函数
```sql
-- USER()函数返回当前连接MySQL的用户名，返回结果格式为“主机名@用户名”。
mysql> SELECT USER();
+----------------+
| USER()          |
+----------------+
| root@localhost |
+----------------+
```

##### 6.5，LAST_INSERT_ID()函数
```sql
-- LAST_INSERT_ID()函数返回自增列最新的值。
mysql> SELECT LAST_INSERT_ID();
+------------------+
| LAST_INSERT_ID() |
+------------------+
|                1 |
+------------------+
```

##### 6.6，CHARSET(value)函数
```sql
-- CHARSET(value)函数用于查看MySQL使用的字符集。
mysql> SELECT CHARSET('ABC');
+----------------+
| CHARSET('ABC') |
+----------------+
| utf8mb4        |
+----------------+
```

##### 6.7，COLLATION(value)函数
```sql
-- COLLATION(value)函数用于返回字符串value的排序方式。
mysql> SELECT COLLATION('ABC');
+--------------------+
| COLLATION('ABC')   |
+--------------------+
| utf8mb4_general_ci |
+--------------------+
```

#### 7，加密与解密函数
加密与解密函数主要用于对数据库中的数据进行加密和解密处理，以防止数据被他人窃取。MySQL中提供了内置的数据加密和解密函数，主要包括PASSWORD(value)函数、MD5(value)函数、ENCODE(value,password-seed)函数和DECODE(value,password-seed)函数。

##### 7.1，PASSWORD(value)函数
```sql
-- PASSWORD(value)函数将明文密码value的值进行加密，返回加密后的密码字符串。如果value的值为NULL，则返回的结果为空。加密结果是单向、不可逆的。
mysql> SELECT PASSWORD('mysql'), PASSWORD(NULL);
+-------------------------------------------+----------------+
| PASSWORD('mysql')                         | PASSWORD(NULL) |
+-------------------------------------------+----------------+
| *E74858DB86EBA20BC33D0AECAE8A8108C56B17FA |                |
+-------------------------------------------+----------------+
```

##### 7.2，MD5(value)函数
```sql
-- MD5(value)函数返回对value进行MD5加密后的结果值。如果value的值为NULL，则返回NULL。
mysql> SELECT MD5('mysql'), MD5(NULL);
+----------------------------------+-----------+
| MD5('mysql')                     | MD5(NULL) |
+----------------------------------+-----------+
| 81c3b080dad537de7e10e0987a4bf52e | NULL      |
+----------------------------------+-----------+
```

##### 7.3，ENCODE(value,password_seed)函数
```sql
-- ENCODE(value,password_seed)函数返回使用password_seed作为密码加密value的结果值。
mysql> SELECT ENCODE('mysql', 'mysql');
+--------------------------+
| ENCODE('mysql', 'mysql') |
+--------------------------+
| íg　¼　ìÉ                  |
+--------------------------+
```

##### 7.4，DECODE(value,password_seed)函数
```sql
-- DECODE(value,password_seed)函数返回使用password_seed作为密码解密value的结果值。
-- ENCODE(value,password_seed)函数与DECODE(value,password_seed)函数互为反函数。
mysql> SELECT DECODE(ENCODE('mysql','mysql'),'mysql');
+-----------------------------------------+
| DECODE(ENCODE('mysql','mysql'),'mysql') |
+-----------------------------------------+
| mysql                                   |
+-----------------------------------------+
```

#### 8，加锁与解锁函数
MySQL中提供了对数据进行加锁和解锁的函数，这些函数包括GET_LOCK(value,timeout)、RELEASE_LOCK(value)、IS_FREE_LOCK(value)和IS_USED_LOCK(value)函数。

##### 8.1，GET_LOCK(value,timeout)函数
```sql
/** GET_LOCK(value,timeout)函数使用字符串value给定的名字获取锁，持续timeout秒。
如果成功获取锁，则返回1；如果获取锁超时，则返回0；如果发生错误，则返回NULL。
使用GET_LOCK(value,timeout)函数获取的锁，当执行RELEASE_LOCK(value)或断开数据库连接（包括正常断开和非正常断开），锁都会被解除。
 */
mysql> SELECT GET_LOCK('mysql',1000);
+------------------------+
| GET_LOCK('mysql',1000) |
+------------------------+
|                      1 |
+------------------------+
```

##### 8.2，RELEASE_LOCK(value)函数
```sql
/** RELEASE_LOCK(value)函数将以value命名的锁解除。
如果解除成功，则返回1；如果线程还没有创建锁，则返回0；如果以value命名的锁不存在，则返回NULL。

锁不存在包括两种情况。
·从未被GET_LOCK(value,timeout)函数获取过。
·锁已经被调用RELEASE_LOCK(value)函数释放过。
 */
mysql> SELECT RELEASE_LOCK('mysql');
+-----------------------+
| RELEASE_LOCK('mysql') |
+-----------------------+
|                     1 |
+-----------------------+
```

##### 8.3，IS_FREE_LOCK(value)函数
```sql
/** IS_FREE_LOCK(value)函数判断以value命名的锁是否可以被使用。
如果可以被使用，则返回1；如果不能使用，也就是说正在被使用，则返回0；如果发生错误，则返回NULL。 
*/
mysql> SELECT IS_FREE_LOCK('mysql');
+-----------------------+
| IS_FREE_LOCK('mysql') |
+-----------------------+
|                     1 |
+-----------------------+
```

##### 8.4，IS_USED_LOCK(value)函数
```sql
/** IS_USED_LOCK(value)函数判断以value命名的锁是否正在被使用，如果正在被使用，则返回使用该锁的数据库连接ID，否则返回NULL。
 */
mysql> SELECT IS_USED_LOCK('mysql'), IS_USED_LOCK('test');
+-----------------------+----------------------+
| IS_USED_LOCK('mysql') | IS_USED_LOCK('test') |
+-----------------------+----------------------+
|                  NULL |                    8 |
+-----------------------+----------------------+
```

#### 9，窗口函数
MySQL从8.0版本开始支持窗口函数，其中，窗口可以理解为数据的集合。窗口函数也就是在符合某种条件或者某些条件的记录集合中执行的函数，窗口函数会在每条记录上执行。

窗口函数可以分为静态窗口函数和动态窗口函数，其中，静态窗口函数的窗口大小是固定的，不会因为记录的不同而不同；动态窗口函数的窗口大小会随着记录的不同而变化。

```
1，窗口函数的基本用法格式如下：函数名 ([expr]) over子句
2，over关键字指定函数窗口的范围，如果省略后面括号中的内容，则窗口会包含满足WHERE条件的所有记录，窗口函数会基于所有满足WHERE条件的记录进行计算。如果over关键字后面的括号不为空，则可以使用如下语法设置窗口。
  ·window_name：为窗口设置一个别名，用来标识窗口。
  ·PARTITION BY子句：指定窗口函数按照哪些字段进行分组。分组后，窗口函数可以在每个分组中分别执行。
  ·ORDER BY子句：指定窗口函数按照哪些字段进行排序。执行排序操作使窗口函数按照排序后的数据记录的顺序进行编号。
  ·FRAME子句：为分区中的某个子集定义规则，可以用来作为滑动窗口使用。
```

```sql
--窗口函数测试数据
mysql> CREATE TABLE `t_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `t_category_id` int(11) DEFAULT NULL,
  `t_category` varchar(30) DEFAULT NULL,
  `t_name` varchar(50) DEFAULT NULL,
  `t_price` decimal(10,2) DEFAULT NULL,
  `t_stock` int(11) DEFAULT NULL,
  `t_upper_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB

mysql> INSERT INTO t_goods (t_category_id, t_category, t_name, t_price, t_stock, t_upper_time) VALUES 
    (1, '女装/女士精品', 'T恤', 39.90, 1000, '2020-11-10 00:00:00'),
    (1, '女装/女士精品', '连衣裙', 79.90, 2500, '2020-11-10 00:00:00'),
    (1, '女装/女士精品', '卫衣', 89.90, 1500, '2020-11-10 00:00:00'),
    (1, '女装/女士精品', '牛仔裤', 89.90, 3500, '2020-11-10 00:00:00'),
    (1, '女装/女士精品', '百褶裙', 29.90, 500, '2020-11-10 00:00:00'),
    (1, '女装/女士精品', '呢绒外套', 399.90, 1200, '2020-11-10 00:00:00'),
    (2, '户外运动', '自行车', 399.90, 1000, '2020-11-10 00:00:00'),
    (2, '户外运动', '山地自行车', 1399.90, 2500, '2020-11-10 00:00:00'),
    (2, '户外运动', '登山杖', 59.90, 1500, '2020-11-10 00:00:00'),
    (2, '户外运动', '骑行装备', 399.90, 3500, '2020-11-10 00:00:00'),
    (2, '户外运动', '运动外套', 799.90, 500, '2020-11-10 00:00:00'),
    (2, '户外运动', '滑板', 499.90, 1200, '2020-11-10 00:00:00');
```
##### 9.1，序号函数
```sql
/** 序号函数分为：ROW_NUMBER()函数，RANK()函数，DENSE_RANK()函数 
区别为：
    1）ROW_NUMBER()函数是顺序排序，结果为 1,2,3,4；
    2）RANK()函数是并列排序，即同值情况下序号相同，且会跳过重复的序号，排序结果可能为 1,2,2,4；
    3）DENSE_RANK()函数也是并列排序，即同值情况下序号相同，但它不会跳过重复的序号，排序结果可能为 1,2,2,3；
*/

-- 1，ROW_NUMBER()函数
mysql> SELECT * FROM
    -> (
    -> SELECT
    -> ROW_NUMBER() OVER(PARTITION BY t_category_id ORDER BY t_price DESC) AS row_num,
    -> id, t_category_id, t_category, t_name, t_price, t_stock
    -> FROM t_goods) t
    -> WHERE row_num <= 3;
+---------+----+---------------+---------------------+-----------------+---------+---------+
| row_num | id | t_category_id | t_category          | t_name          | t_price | t_stock |
+---------+----+---------------+---------------------+-----------------+---------+---------+
|       1 |  6 |             1 | 女装/女士精品       | 呢绒外套        |  399.90 |    1200 |
|       2 |  3 |             1 | 女装/女士精品       | 卫衣            |   89.90 |    1500 |
|       3 |  4 |             1 | 女装/女士精品       | 牛仔裤          |   89.90 |    3500 |
|       1 |  8 |             2 | 户外运动            | 山地自行车      | 1399.90 |    2500 |
|       2 | 11 |             2 | 户外运动            | 运动外套        |  799.90 |     500 |
|       3 | 12 |             2 | 户外运动            | 滑板            |  499.90 |    1200 |
+---------+----+---------------+---------------------+-----------------+---------+---------+

-- 2，RANK()函数
mysql> SELECT * FROM
    -> (
    -> SELECT
    -> RANK() OVER(PARTITION BY t_category_id ORDER BY t_price DESC) AS row_num,
    -> id, t_category_id, t_category, t_name, t_price, t_stock
    -> FROM t_goods) t
    -> WHERE t_category_id = 1 AND row_num <= 4;
+---------+----+---------------+---------------------+--------------+---------+---------+
| row_num | id | t_category_id | t_category          | t_name       | t_price | t_stock |
+---------+----+---------------+---------------------+--------------+---------+---------+
|       1 |  6 |             1 | 女装/女士精品       | 呢绒外套     |  399.90 |    1200 |
|       2 |  3 |             1 | 女装/女士精品       | 卫衣         |   89.90 |    1500 |
|       2 |  4 |             1 | 女装/女士精品      | 牛仔裤       |   89.90 |    3500 |
|       4 |  2 |             1 | 女装/女士精品      | 连衣裙       |   79.90 |    2500 |
+---------+----+---------------+---------------------+--------------+---------+---------+

-- 3，DENSE_RANK()函数
mysql> SELECT * FROM                                                                
-> (
    -> SELECT
    -> DENSE_RANK() OVER(PARTITION BY t_category_id ORDER BY t_price DESC) AS row_num,
    -> id, t_category_id, t_category, t_name, t_price, t_stock
    -> FROM t_goods) t
    -> WHERE t_category_id = 1 AND row_num <= 3;
+---------+----+---------------+---------------------+--------------+---------+---------+
| row_num | id | t_category_id | t_category          | t_name       | t_price | t_stock |
+---------+----+---------------+---------------------+--------------+---------+---------+
|       1 |  6 |             1 | 女装/女士精品       | 呢绒外套     |  399.90 |    1200 |
|       2 |  3 |             1 | 女装/女士精品       | 卫衣         |   89.90 |    1500 |
|       2 |  4 |             1 | 女装/女士精品       | 牛仔裤       |   89.90 |    3500 |
|       3 |  2 |             1 | 女装/女士精品       | 连衣裙       |   79.90 |    2500 |
+---------+----+---------------+---------------------+--------------+---------+---------+
```

##### 9.2，分布函数
```sql
-- 1，PERCENT_RANK()函数 
-- PERCENT_RANK()函数是等级值百分比函数。按照如下方式进行计算：  (rank - 1) / (rows - 1)
mysql> SELECT
    -> RANK() OVER w AS r,
    -> PERCENT_RANK() OVER w AS pr,
    -> id, t_category_id, t_category, t_name, t_price, t_stock
    -> FROM t_goods
    -> WHERE t_category_id = 1
    -> WINDOW w AS (PARTITION BY t_category_id ORDER BY t_price DESC);
+---+-----+----+---------------+---------------------+--------------+---------+---------+
| r | pr  | id | t_category_id | t_category          | t_name       | t_price | t_stock |
+---+-----+----+---------------+---------------------+--------------+---------+---------+
| 1 |   0 |  6 |             1 | 女装/女士精品       | 呢绒外套     |  399.90 |  1200   |
| 2 | 0.2 |  3 |             1 | 女装/女士精品       | 卫衣         |   89.90 |  1500   |
| 2 | 0.2 |  4 |             1 | 女装/女士精品       | 牛仔裤       |   89.90 |  3500   |
| 4 | 0.6 |  2 |             1 | 女装/女士精品       | 连衣裙       |   79.90 |  2500   |
| 5 | 0.8 |  1 |             1 | 女装/女士精品       | T恤          |   39.90 |  1000   |
| 6 |   1 |  5 |             1 | 女装/女士精品       | 百褶裙       |   29.90 |   500   |
+---+-----+----+---------------+---------------------+--------------+---------+---------+

-- 2，CUME_DIST()函数
-- CUME_DIST()函数主要用于查询小于或等于某个值的比例。
mysql> SELECT
    -> CUME_DIST() OVER(PARTITION BY t_category_id ORDER BY t_price DESC) AS cd,
    -> id, t_category, t_name, t_price
    -> FROM t_goods;
+---------------------+----+---------------------+-----------------+---------+
| cd                  | id | t_category          | t_name          | t_price |
+---------------------+----+---------------------+-----------------+---------+
| 0.16666666666666666 |  6 | 女装/女士精品       | 呢绒外套        |  399.90 |
|                 0.5 |  3 | 女装/女士精品       | 卫衣            |   89.90 |
|                 0.5 |  4 | 女装/女士精品       | 牛仔裤          |   89.90 |
|  0.6666666666666666 |  2 | 女装/女士精品       | 连衣裙          |   79.90 |
|  0.8333333333333334 |  1 | 女装/女士精品       | T恤             |   39.90 |
|                   1 |  5 | 女装/女士精品       | 百褶裙          |   29.90 |
| 0.16666666666666666 |  8 | 户外运动            | 山地自行车      | 1399.90 |
|  0.3333333333333333 | 11 | 户外运动            | 运动外套        |  799.90 |
|                 0.5 | 12 | 户外运动            | 滑板            |  499.90 |
|  0.8333333333333334 |  7 | 户外运动            | 自行车          |  399.90 |
|  0.8333333333333334 | 10 | 户外运动            | 骑行装备        |  399.90 |
|                   1 |  9 | 户外运动            | 登山杖          |   59.90 |
+---------------------+----+---------------------+-----------------+---------+
```

##### 9.3，前后函数
```sql
-- 1，LAG(expr,n)函数
/** lag ：[læɡ] 掉队，落后于。形象的理解就是把数据从上向下推，上端出现空格
LAG(expr,n)函数返回当前行的前n行的expr的值 */
mysql> SELECT id, t_category, t_name, t_price, pre_price, 
    -> t_price - pre_price AS diff_price
    -> FROM (
    -> SELECT  id, t_category, t_name, t_price,
    -> LAG(t_price,1) OVER w AS pre_price
    -> FROM t_goods
    -> WINDOW w AS (PARTITION BY t_category_id ORDER BY t_price)) t;
+----+---------------------+-----------------+---------+-----------+------------+
| id | t_category         | t_name         | t_price | pre_price | diff_price |
+----+---------------------+-----------------+---------+-----------+------------+
|  5 | 女装/女士精品       | 百褶裙          |   29.90 |    NULL   |     NULL   |
|  1 | 女装/女士精品       | T恤             |   39.90 |     29.90 |      10.00 |
|  2 | 女装/女士精品       | 连衣裙          |   79.90 |     39.90 |      40.00 |
|  3 | 女装/女士精品       | 卫衣            |   89.90 |     79.90 |      10.00 |
|  4 | 女装/女士精品       | 牛仔裤          |   89.90 |     89.90 |       0.00 |
|  6 | 女装/女士精品       | 呢绒外套        |  399.90 |     89.90 |     310.00 |
|  9 | 户外运动            | 登山杖          |   59.90 |    NULL   |     NULL   |
|  7 | 户外运动            | 自行车          |  399.90 |     59.90 |     340.00 |
| 10 | 户外运动            | 骑行装备        |  399.90 |    399.90 |       0.00 |
| 12 | 户外运动            | 滑板            |  499.90 |    399.90 |     100.00 |
| 11 | 户外运动            | 运动外套        |  799.90 |    499.90 |     300.00 |
|  8 | 户外运动            | 山地自行车      | 1399.90 |    799.90 |     600.00 |
+----+---------------------+-----------------+---------+-----------+------------+

-- 2，LEAD(expr,n)函数
/** lead ：[liːd] 引领，带路。形象的理解就是把数据从下向上推，下端出现空格
LEAD(expr,n)函数返回当前行的后n行的expr的值。*/
mysql> SELECT id, t_category, t_name, next_price, t_price,
    -> next_price - t_price AS diff_price
    -> FROM(
    -> SELECT  id, t_category, t_name, t_price,
    -> LEAD(t_price, 1) OVER w AS next_price
    -> FROM t_goods
    -> WINDOW w AS (PARTITION BY t_category_id ORDER BY t_price)) t;
+----+---------------------+-----------------+--------------+---------+------------+
| id | t_category          | t_name          | next_price | t_price | diff_price |
+----+---------------------+-----------------+--------------+---------+------------+
|  5 | 女装/女士精品       | 百褶裙          |        39.90 |   29.90 |      10.00 |
|  1 | 女装/女士精品       | T恤             |        79.90 |   39.90 |      40.00 |
|  2 | 女装/女士精品       | 连衣裙          |        89.90 |   79.90 |      10.00 |
|  3 | 女装/女士精品       | 卫衣            |        89.90 |   89.90 |       0.00 |
|  4 | 女装/女士精品       | 牛仔裤          |       399.90 |   89.90 |     310.00 |
|  6 | 女装/女士精品       | 呢绒外套        |        NULL  |  399.90 |       NULL |
|  9 | 户外运动            | 登山杖          |       399.90 |   59.90 |     340.00 |
|  7 | 户外运动            | 自行车          |       399.90 |  399.90 |       0.00 |
| 10 | 户外运动            | 骑行装备        |       499.90 |  399.90 |     100.00 |
| 12 | 户外运动            | 滑板            |       799.90 |  499.90 |     300.00 |
| 11 | 户外运动            | 运动外套        |      1399.90 |  799.90 |     600.00 |
|  8 | 户外运动            | 山地自行车      |        NULL  | 1399.90 |       NULL |
+----+---------------------+-----------------+--------------+---------+------------+
```

##### 9.4，首尾函数
```sql
-- 1，FIRST_VALUE(expr)函数
-- FIRST_VALUE(expr)函数返回第一个expr的值。
-- 按照价格排序，查询第1个商品的价格信息。
mysql> SELECT id, t_category, t_name, t_price, t_stock,
    -> FIRST_VALUE(t_price) OVER w AS first_price
    -> FROM t_goods
    -> WINDOW w AS (PARTITION BY t_category_id ORDER BY t_price);
+----+---------------------+-----------------+---------+---------+-------------+
| id | t_category          | t_name          | t_price | t_stock | first_price |
+----+---------------------+-----------------+---------+---------+-------------+
|  5 | 女装/女士精品       | 百褶裙          |   29.90 |     500 |       29.90 |
|  1 | 女装/女士精品       | T恤             |   39.90 |    1000 |       29.90 |
|  2 | 女装/女士精品       | 连衣裙          |   79.90 |    2500 |       29.90 |
|  3 | 女装/女士精品       | 卫衣            |   89.90 |    1500 |       29.90 |
|  4 | 女装/女士精品       | 牛仔裤          |   89.90 |    3500 |       29.90 |
|  6 | 女装/女士精品       | 呢绒外套        |  399.90 |    1200 |       29.90 |
|  9 | 户外运动            | 登山杖          |   59.90 |    1500 |       59.90 |
|  7 | 户外运动            | 自行车          |  399.90 |    1000 |       59.90 |
| 10 | 户外运动            | 骑行装备        |  399.90 |    3500 |       59.90 |
| 12 | 户外运动            | 滑板            |  499.90 |    1200 |       59.90 |
| 11 | 户外运动            | 运动外套        |  799.90 |     500 |       59.90 |
|  8 | 户外运动            | 山地自行车      | 1399.90 |    2500 |       59.90 |
+----+---------------------+-----------------+---------+---------+-------------+

-- 2，LAST_VALUE(expr)函数
-- LAST_VALUE(expr)函数返回最后一个expr的值。
mysql> SELECT id, t_category, t_name, t_price, t_stock,
    -> LAST_VALUE(t_price) OVER w AS last_price     
    -> FROM t_goods
    -> WINDOW w AS (PARTITION BY t_category_id ORDER BY t_price);
+----+---------------------+-----------------+---------+---------+------------+
| id | t_category          | t_name          | t_price | t_stock | last_price |
+----+---------------------+-----------------+---------+---------+------------+
|  5 | 女装/女士精品       | 百褶裙          |   29.90 |     500 |      29.90 |
|  1 | 女装/女士精品       | T恤             |   39.90 |    1000 |      39.90 |
|  2 | 女装/女士精品       | 连衣裙          |   79.90 |    2500 |      79.90 |
|  3 | 女装/女士精品       | 卫衣            |   89.90 |    1500 |      89.90 |
|  4 | 女装/女士精品       | 牛仔裤          |   89.90 |    3500 |      89.90 |
|  6 | 女装/女士精品       | 呢绒外套        |  399.90 |    1200 |     399.90 |
|  9 | 户外运动            | 登山杖          |   59.90 |    1500 |      59.90 |
|  7 | 户外运动            | 自行车          |  399.90 |    1000 |     399.90 |
| 10 | 户外运动            | 骑行装备        |  399.90 |    3500 |     399.90 |
| 12 | 户外运动            | 滑板            |  499.90 |    1200 |     499.90 |
| 11 | 户外运动            | 运动外套        |  799.90 |     500 |     799.90 |
|  8 | 户外运动            | 山地自行车      | 1399.90 |    2500 |    1399.90 |
+----+---------------------+-----------------+---------+---------+------------+
```

##### 9.5，其他函数
```sql
-- 1，NTH_VALUE(expr,n)函数
-- NTH_VALUE(expr,n)函数返回第n个expr的值。
mysql> SELECT id, t_category, t_name, t_price, 
    -> NTH_VALUE(t_price,2) OVER w AS second_price,
    -> NTH_VALUE(t_price,3) OVER w AS third_price
    -> FROM t_goods
    -> WINDOW w AS (PARTITION BY t_category_id ORDER BY t_price);
+----+---------------------+-----------------+---------+--------------+-------------+
| id | t_category          | t_name          | t_price | second_price | third_price |
+----+---------------------+-----------------+---------+--------------+-------------+
|  5 | 女装/女士精品       | 百褶裙          |   29.90 |         NULL |        NULL |
|  1 | 女装/女士精品       | T恤             |   39.90 |        39.90 |        NULL |
|  2 | 女装/女士精品       | 连衣裙          |   79.90 |        39.90 |       79.90 |
|  3 | 女装/女士精品       | 卫衣            |   89.90 |        39.90 |       79.90 |
|  4 | 女装/女士精品       | 牛仔裤          |   89.90 |        39.90 |       79.90 |
|  6 | 女装/女士精品       | 呢绒外套        |  399.90 |        39.90 |       79.90 |
|  9 | 户外运动            | 登山杖          |   59.90 |         NULL |        NULL |
|  7 | 户外运动            | 自行车          |  399.90 |       399.90 |      399.90 |
| 10 | 户外运动            | 骑行装备        |  399.90 |       399.90 |      399.90 |
| 12 | 户外运动            | 滑板            |  499.90 |       399.90 |      399.90 |
| 11 | 户外运动            | 运动外套        |  799.90 |       399.90 |      399.90 |
|  8 | 户外运动            | 山地自行车      | 1399.90 |       399.90 |      399.90 |
+----+---------------------+-----------------+---------+--------------+-------------+

-- 2，NTILE(n)函数
-- NTILE(n)函数将分区中的有序数据分为n个桶，记录桶编号。
mysql> SELECT 
    -> NTILE(3) OVER w AS nt,
    -> id, t_category, t_name, t_price
    -> FROM t_goods
    -> WINDOW w AS (PARTITION BY t_category_id ORDER BY t_price);
+------+----+---------------------+-----------------+---------+
| nt   | id | t_category          | t_name          | t_price |
+------+----+---------------------+-----------------+---------+
|    1 |  5 | 女装/女士精品       | 百褶裙          |   29.90 |
|    1 |  1 | 女装/女士精品       | T恤             |   39.90 |
|    2 |  2 | 女装/女士精品       | 连衣裙          |   79.90 |
|    2 |  3 | 女装/女士精品       | 卫衣            |   89.90 |
|    3 |  4 | 女装/女士精品       | 牛仔裤          |   89.90 |
|    3 |  6 | 女装/女士精品       | 呢绒外套        |  399.90 |
|    1 |  9 | 户外运动            | 登山杖          |   59.90 |
|    1 |  7 | 户外运动            | 自行车          |  399.90 |
|    2 | 10 | 户外运动            | 骑行装备        |  399.90 |
|    2 | 12 | 户外运动            | 滑板            |  499.90 |
|    3 | 11 | 户外运动            | 运动外套        |  799.90 |
|    3 |  8 | 户外运动            | 山地自行车      | 1399.90 |
+------+----+---------------------+-----------------+---------+
```

##### 9.6，更多窗口函数
[更多窗口函数](https://dev.mysql.com/doc/refman/8.0/en/window-function-descriptions.html#function_row-number。)

#### 10，JSON函数
JSON函数是对数据库中JSON数据类型的数据进行处理的函数，MySQL中内置了一系列的JSON函数。

```sql
-- JSON函数测试数据
mysql> CREATE TABLE test_json (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,content JSON);
mysql> INSERT INTO test_json (content) 
VALUES('{"name":"Johann", "age":12, "address":{"province":"HeBei", "city":"HanDan", "nativePlace":"HeBei"}}'),
('{"name":"Jessie", "age":11, "address":{"province":"ShanXi", "city":"DaTong", "nativePlace":"ShanXi"}}'),
('{"name":"Jack", "age":13, "address":{"province":"Hebei", "city":"BaoDing", "nativePlace":"ShanXi"}}');

mysql> SELECT id,content->'$.name' as userName,content->'$.address.province' as province FROM test_json;
+----+----------+----------+
| id | userName | province |
+----+----------+----------+
|  1 | "Johann" | "HeBei"  |
|  2 | "Jessie" | "ShanXi" |
|  3 | "Jack"   | "Hebei"  |
+----+----------+----------+
```
##### 10.1，JSON_CONTAINS()函数
```sql
-- JSON_CONTAINS(json_doc,value)函数 查询JSON类型的字段中是否包含value数据。如果包含则返回1，否则返回0。其中，json_doc为JSON类型的数据，value为要查找的数据，value必须是一个JSON字符串。
mysql> SELECT id,JSON_CONTAINS(content, '{"name":"Johann"}') FROM test_json;
+----+---------------------------------------------+
| id | JSON_CONTAINS(content, '{"name":"Johann"}') |
+----+---------------------------------------------+
|  1 |                                           1 |
|  2 |                                           0 |
|  3 |                                           0 |
+----+---------------------------------------------+
```

##### 10.2，JSON_SEARCH()函数
```sql
-- JSON_SEARCH(json_doc ->> '$[*].key',type,value)函数 在JSON类型的字段指定的key中，查找字符串value。如果找到value值，则返回索引数据。
mysql> SELECT id,JSON_SEARCH(content ->> '$.address', 'one', 'HeBei') FROM test_json;
+----+------------------------------------------------------+
| id | JSON_SEARCH(content ->> '$.address', 'one', 'HeBei') |
+----+------------------------------------------------------+
|  1 | "$.province"                                         |
|  2 | NULL                                                 |
|  3 | "$.province"                                         |
+----+------------------------------------------------------+

-- 函数的第二个参数type，取值可以是one或者all。当取值为one时，如果找到value值，则返回value值的第一个索引数据；当取值为all时，如果找到value值，则返回value值的所有索引数据。
mysql> SELECT id,JSON_SEARCH(content ->> '$.address', 'all', 'HeBei') FROM test_json;
+----+------------------------------------------------------+
| id | JSON_SEARCH(content ->> '$.address', 'all', 'HeBei') |
+----+------------------------------------------------------+
|  1 | ["$.province", "$.nativePlace"]                      |
|  2 | NULL                                                 |
|  3 | "$.province"                                         |
+----+------------------------------------------------------+
```

##### 10.3，JSON_PRETTY(json_doc)函数
```sql
-- JSON_PRETTY(json_doc)函数以优雅的格式显示JSON数据。
mysql> SELECT JSON_PRETTY(content) FROM test_json where id = 1;
+-----------------------------------------------------------------------------------------------------------------------------------+
| JSON_PRETTY(content)                                                                                                              |
+-----------------------------------------------------------------------------------------------------------------------------------+
| {
  "age": 12,
  "name": "Johann",
  "address": {
    "city": "HanDan",
    "province": "HeBei",
    "nativePlace": "HeBei"
  }
} |
+-----------------------------------------------------------------------------------------------------------------------------------+
1 row in set (0.00 sec)
```

##### 10.4，JSON_DEPTH(json_doc)函数
```sql
-- JSON_DEPTH(json_doc)函数返回JSON数据的最大深度。
mysql> SELECT JSON_DEPTH(content) FROM test_json;
+---------------------+
| JSON_DEPTH(content) |
+---------------------+
|                   3 |
|                   3 |
|                   3 |
+---------------------+
```

##### 10.5，JSON_LENGTH()函数
```sql
-- JSON_LENGTH(json_doc[,path])函数返回JSON数据的长度。
mysql> SELECT JSON_LENGTH(content) FROM test_json;
+----------------------+
| JSON_LENGTH(content) |
+----------------------+
|                    3 |
|                    3 |
|                    3 |
+----------------------+
```

##### 10.6，JSON_KEYS()函数
```sql
-- JSON_KEYS(json_doc[,path])函数返回JSON数据中顶层key组成的JSON数组。
mysql> SELECT JSON_KEYS(content) FROM test_json;
+----------------------------+
| JSON_KEYS(content)         |
+----------------------------+
| ["age", "name", "address"] |
| ["age", "name", "address"] |
| ["age", "name", "address"] |
+----------------------------+
```

##### 10.7，JSON_INSERT()函数
```sql
-- JSON_INSERT(json_doc,path,val[,path,val] ...)函数用于向JSON数据中插入数据。
mysql> SELECT JSON_INSERT(content, '$.sex','male','$.address.zip_code','000000') FROM test_json WHERE id = 1;
+------------------------------------------------------------------------------------------------------------------------------------------------+
| JSON_INSERT(content, '$.sex','male','$.address.zip_code','000000')                                                                             |
+------------------------------------------------------------------------------------------------------------------------------------------------+
| {"age": 12, "sex": "male", "name": "Johann", "address": {"city": "HanDan", "province": "HeBei", "zip_code": "000000", "nativePlace": "HeBei"}} |
+------------------------------------------------------------------------------------------------------------------------------------------------+

-- JSON_INSERT()函数并不会修改原表中的数据，只是修改的显示结果
mysql> select content from test_json where id = 1;
+-----------------------------------------------------------------------------------------------------------+
| content                                                                                                   |
+-----------------------------------------------------------------------------------------------------------+
| {"age": 12, "name": "Johann", "address": {"city": "HanDan", "province": "HeBei", "nativePlace": "HeBei"}} |
+-----------------------------------------------------------------------------------------------------------+
```

##### 10.8，JSON_REMOVE()函数
```sql
-- JSON_REMOVE(json_doc,path[,path] ...)函数用于移除JSON数据中指定key的数据。
mysql> SELECT JSON_REMOVE(content, '$.address.nativePlace') FROM test_json WHERE id = 1;
+-----------------------------------------------------------------------------------+
| JSON_REMOVE(content, '$.address.nativePlace')                                     |
+-----------------------------------------------------------------------------------+
| {"age": 12, "name": "Johann", "address": {"city": "HanDan", "province": "HeBei"}} |
+-----------------------------------------------------------------------------------+

-- JSON_REMOVE()函数并不会修改原表中的数据，只是修改的显示结果
mysql> select content from test_json where id = 1;
+-----------------------------------------------------------------------------------------------------------+
| content                                                                                                   |
+-----------------------------------------------------------------------------------------------------------+
| {"age": 12, "name": "Johann", "address": {"city": "HanDan", "province": "HeBei", "nativePlace": "HeBei"}} |
+-----------------------------------------------------------------------------------------------------------+
```

##### 10.9，JSON_REPLACE()函数
```sql
-- JSON_REPLACE(json_doc,path,val[,path,val] ...)函数用于更新JSON数据中指定Key的数据。
mysql> SELECT JSON_REPLACE(content,'$.address.city','XingTai') FROM test_json WHERE id = 1;
+------------------------------------------------------------------------------------------------------------+
| JSON_REPLACE(content,'$.address.city','XingTai')                                                           |
+------------------------------------------------------------------------------------------------------------+
| {"age": 12, "name": "Johann", "address": {"city": "XingTai", "province": "HeBei", "nativePlace": "HeBei"}} |
+------------------------------------------------------------------------------------------------------------+

-- JSON_REPLACE()函数并不会修改原表中的数据，只是修改的显示结果
mysql> select content from test_json where id = 1;
+-----------------------------------------------------------------------------------------------------------+
| content                                                                                                   |
+-----------------------------------------------------------------------------------------------------------+
| {"age": 12, "name": "Johann", "address": {"city": "HanDan", "province": "HeBei", "nativePlace": "HeBei"}} |
+-----------------------------------------------------------------------------------------------------------+
```

##### 10.10，JSON_SET()函数
```sql
-- JSON_SET(json_doc,path,val[,path,val] ...)函数用于向JSON数据中插入数据。
mysql> SELECT JSON_SET(content, '$.sex','male','$.address.zip_code','000000') FROM test_json WHERE id = 1;
+------------------------------------------------------------------------------------------------------------------------------------------------+
| JSON_SET(content, '$.sex','male','$.address.zip_code','000000')                                                                                |
+------------------------------------------------------------------------------------------------------------------------------------------------+
| {"age": 12, "sex": "male", "name": "Johann", "address": {"city": "HanDan", "province": "HeBei", "zip_code": "000000", "nativePlace": "HeBei"}} |
+------------------------------------------------------------------------------------------------------------------------------------------------+

-- JSON_SET()函数并不会修改原表中的数据，只是修改的显示结果
mysql> select content from test_json where id = 1;
+-----------------------------------------------------------------------------------------------------------+
| content                                                                                                   |
+-----------------------------------------------------------------------------------------------------------+
| {"age": 12, "name": "Johann", "address": {"city": "HanDan", "province": "HeBei", "nativePlace": "HeBei"}} |
+-----------------------------------------------------------------------------------------------------------+
```

##### 10.11，JSON_TYPE(json_val)函数
```sql
/** JSON_TYPE(json_val)函数用于返回JSON数据的JSON类型，MySQL中支持的JSON类型除了可以是MySQL中的数据类型外，还可以是OBJECT和ARRAY类型。
其中OBJECT表示JSON对象，ARRAY表示JSON数组。*/
mysql> SELECT JSON_TYPE(content) FROM test_json WHERE id = 1;
+--------------------+
| JSON_TYPE(content) |
+--------------------+
| OBJECT             |
+--------------------+
```

##### 10.12，JSON_VALID(value)函数
```sql
/** JSON_VALID(value)函数用于判断value的值是否是有效的JSON数据。
如果是，则返回1；否则返回0；如果value的值为NULL，则返回NULL。*/
mysql> SELECT JSON_VALID('{"city":"BeiJing"}'), JSON_VALID('city'), JSON_VALID(NULL);
+----------------------------------+--------------------+------------------+
| JSON_VALID('{"city":"BeiJing"}') | JSON_VALID('city') | JSON_VALID(NULL) |
+----------------------------------+--------------------+------------------+
|                                1 |                  0 |             NULL |
+----------------------------------+--------------------+------------------+
```

##### 10.13，更多JSON函数
[更多JSON函数](https://dev.mysql.com/doc/refman/8.0/en/json-function-reference.html)

#### 11，MySQL的其他函数

##### 11.1，FORMAT(value,n)函数
```sql
-- FORMAT(value,n)函数返回对数字value进行格式化后的结果数据，其中n表示四舍五入后保留到小数点后n位。如果n的值小于或者等于0，则只保留整数部分。
mysql> SELECT FORMAT(123.123, 2), FORMAT(123.523, 0), FORMAT(123.123, -2); 
+--------------------+--------------------+---------------------+
| FORMAT(123.123, 2) | FORMAT(123.523, 0) | FORMAT(123.123, -2) |
+--------------------+--------------------+---------------------+
| 123.12             | 124                | 123                 |
+--------------------+--------------------+---------------------+
```

##### 11.2，CONV(value,from,to)函数
```sql
-- CONV(value,from,to)函数将value的值进行不同进制之间的转换，value是一个整数，如果任意一个参数为NULL，则结果返回NULL。
mysql> SELECT CONV(16, 10, 2), CONV(8888,10,16), CONV(NULL, 10, 2);
+-----------------+------------------+-------------------+
| CONV(16, 10, 2) | CONV(8888,10,16) | CONV(NULL, 10, 2) |
+-----------------+------------------+-------------------+
| 10000           | 22B8             | NULL              |
+-----------------+------------------+-------------------+
```

##### 11.3，INET_ATON(value)函数
```sql
-- INET_ATON(value)函数将以点分隔的IP地址转化为一个数字表示，其中，value为以点表示的IP地址。
mysql> SELECT INET_ATON('192.168.1.100');
+----------------------------+
| INET_ATON('192.168.1.100') |
+----------------------------+
|                 3232235876 |
+----------------------------+
-- 以“192.168.1.100”为例，计算方式为192乘以256的3次方，加上168乘以256的2次方，加上1乘以256，再加上100。
```

##### 11.4，INET_NTOA(value)函数
```sql
-- INET_NTOA(value)函数将数字形式的IP地址转化为以点分隔的IP地址。
mysql> SELECT INET_NTOA(3232235876);
+-----------------------+
| INET_NTOA(3232235876) |
+-----------------------+
| 192.168.1.100         |
+-----------------------+
```

##### 11.5，BENCHMARK(n,expr)函数
```sql
-- BENCHMARK(n,expr)函数将表达式expr重复执行n次，主要用于测试MySQL处理expr表达式所耗费的时间。
mysql> SELECT BENCHMARK(1000000, MD5('mysql')); 
+----------------------------------+
| BENCHMARK(1000000, MD5('mysql')) |
+----------------------------------+
|                                0 |
+----------------------------------+
1 row in set (0.20 sec)
```

##### 11.6，CAST(value AS type)函数
```sql
-- CAST(value AS type)函数将value转换为type类型的值，其中type的取值如下示。
-- CONVERT(value,type)函数的作用与CAST(value AS type)函数相同
/** BINARY--二进制数据  CHAR(n)--字符  DATE--日期  TIME--时间  DATETIME--日期时间
    DECIMAL--定点数  SIGNED--有符号整数  UNSIGNED--无符号整数 */
mysql> SELECT CAST('123' AS SIGNED);
+-----------------------+
| CAST('123' AS SIGNED) |
+-----------------------+
|                   123 |
+-----------------------+
```

##### 11.7，CONVERT(value USING char_code)函数
```sql
-- 将value所使用的字符编码修改为char_code
mysql> SELECT CHARSET('mysql'), CHARSET(CONVERT('mysql' USING 'utf8'));
+------------------+----------------------------------------+
| CHARSET('mysql') | CHARSET(CONVERT('mysql' USING 'utf8')) |
+------------------+----------------------------------------+
| utf8mb4          | utf8                                   |
+------------------+----------------------------------------+
```