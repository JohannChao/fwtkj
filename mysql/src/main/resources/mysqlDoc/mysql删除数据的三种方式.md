### 1，DELETE 删除数据
DELETE删除数据时的特性:
* 在 InnoDB 引擎中，delete 操作并不是真的把数据删除掉了，而是给数据打上删除标记，标记为删除状态，这一点我们可以通过将 MySQL 设置为非自动提交模式，来测试验证。
* 在 InnoDB 引擎中，使用了 delete 删除所有的数据之后，并不会重置自增列为初始值。

```sql
--建表语句
mysql> create table student_1(id int PRIMARY KEY AUTO_INCREMENT,name VARCHAR(255));

mysql> show create table student_1\G
*************************** 1. row ***************************
       Table: student_1
Create Table: CREATE TABLE `student_1` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8

--初始化插入数据
mysql> insert into student_1(name) values ("Johann1"),("Johann2"),("Johann3"),("Johann4"),("Johann5"),("Jessie1"),("Jessie2"),("Jessie3"),("Jessie4"),("Jessie5");

mysql> select * from student_1;
+----+---------+
| id | name    |
+----+---------+
|  1 | Johann1 |
|  2 | Johann2 |
|  3 | Johann3 |
|  4 | Johann4 |
|  5 | Johann5 |
|  6 | Jessie1 |
|  7 | Jessie2 |
|  8 | Jessie3 |
|  9 | Jessie4 |
| 10 | Jessie5 |
+----+---------+

--设置非自动提交
mysql> set autocommit=0;
mysql> show variables like 'autocommit';
+---------------+-------+
| Variable_name | Value |
+---------------+-------+
| autocommit    | OFF   |
+---------------+-------+

--使用DELETE删除数据
mysql> delete from student_1 where id = 5;
mysql> select * from student_1;
+----+---------+
| id | name    |
+----+---------+
|  1 | Johann1 |
|  2 | Johann2 |
|  3 | Johann3 |
|  4 | Johann4 |
|  6 | Jessie1 |
|  7 | Jessie2 |
|  8 | Jessie3 |
|  9 | Jessie4 |
| 10 | Jessie5 |
+----+---------+

--数据回滚 ROLLBACK
mysql> rollback;
mysql> select * from student_1;
+----+---------+
| id | name    |
+----+---------+
|  1 | Johann1 |
|  2 | Johann2 |
|  3 | Johann3 |
|  4 | Johann4 |
|  5 | Johann5 |
|  6 | Jessie1 |
|  7 | Jessie2 |
|  8 | Jessie3 |
|  9 | Jessie4 |
| 10 | Jessie5 |
+----+---------+

--再次删除数据，并手动提交
mysql> delete from student_1 where id > 5;
mysql> commit;

--插入新的数据
mysql> insert into student_1(name) values ("Peter1");
mysql> commit;

--查看新插入的数据，自增序列并未初始化
mysql> select * from student_1;
+----+---------+
| id | name    |
+----+---------+
|  1 | Johann1 |
|  2 | Johann2 |
|  3 | Johann3 |
|  4 | Johann4 |
|  5 | Johann5 |
| 11 | Peter1  |
+----+---------+
```

### 2，TRUNCATE 删除数据
TRUNCATE删除数据时的特性:
* delete 可以使用条件表达式删除部分数据，而 truncate 不能加条件表达式，所以它只能删除所有的行数据。
* truncate 是DDL语句（Data Definition Language 数据定义语言，它是用来维护存储数据的结构指令），而 delete 是一个DML语句。
* truncate 本质上是新建了一个表结构，再把原先的表删除掉，所以它属于 DDL语句，而非 DML语句。
* 在 InnoDB 引擎中，truncate 会重置自增列。

```sql
--建表语句
mysql> create table student_2(id int PRIMARY KEY AUTO_INCREMENT,name VARCHAR(255));

mysql> show create table student_2\G
*************************** 1. row ***************************
       Table: student_2
Create Table: CREATE TABLE `student_2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

--初始化插入数据
mysql> insert into student_2(name) values ("Johann1"),("Johann2"),("Johann3"),("Johann4"),("Johann5"),("Jessie1"),("Jessie2"),("Jessie3"),("Jessie4"),("Jessie5");

--删除数据【truncate只能删除全部数据，无法删除部分数据】
mysql> truncate student_2 where id > 5;
ERROR 1064 (42000): You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'where id > 5' at line 1

mysql> truncate student_2;

--插入新的数据
mysql> insert into student_2(name) values ("Peter1");

--查看新插入的数据，自增序列被初始化
mysql> select * from student_2;
+----+--------+
| id | name   |
+----+--------+
|  1 | Peter1 |
+----+--------+
```

### 3，DROP 删除数据
DROP删除数据时的特性:
* drop是一个DDL语句。
* delete和truncate只删除行数据，drop会把整张表的行数据和表结构一起删除掉。

```sql
--建表语句
mysql> create table student_3(id int PRIMARY KEY AUTO_INCREMENT,name VARCHAR(255));

mysql> show create table student_3\G
*************************** 1. row ***************************
       Table: student_3
Create Table: CREATE TABLE `student_3` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

--初始化插入数据
mysql> insert into student_3(name) values ("Johann1"),("Johann2"),("Johann3"),("Johann4"),("Johann5"),("Jessie1"),("Jessie2"),("Jessie3"),("Jessie4"),("Jessie5");

--删除数据
mysql> drop table student_3;

--插入新的数据
mysql> insert into student_3(name) values ("Peter1");
"ERROR 1146 (42S02): Table 'xxx.student_3' doesn't exist"
```

### DELETE, TRUNCATE, DROP 三者区别
* 数据恢复方面：delete 可以恢复删除的数据，而 truncate 和 drop 不能恢复删除的数据。
* 执行速度方面：drop > truncate > delete。
* 删除数据方面：drop 是删除整张表，包含行数据和字段、索引等数据，而 truncate 和 drop 只删除了行数据。
* 添加条件方面：delete 可以使用 where 表达式添加查询条件，而 truncate 和 drop 不能添加 where 查询条件。
* 重置自增列方面：在 InnoDB 引擎中，truncate 可以重置自增列，而 delete 不能重置自增列。