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

##### 1，数值类型










