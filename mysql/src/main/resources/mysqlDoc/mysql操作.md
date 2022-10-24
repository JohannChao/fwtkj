### MySQL数据变更
MySQL数据变更主要体现在对数据的插入、更新和删除上，插入数据使用INSERT语句，更新数据使用UPDATE语句，删除数据则使用DELETE语句。

#### 1，数据插入
MySQL向数据表中插入数据使用INSERT语句。

可以向数据表中插入完整的行记录，为特定的字段插入数据，也可以使用一条INSERT语句向数据表中一次插入多行记录，还可以将一个数据表的查询结果插入另一个数据表中。
```sql
--语法格式1【不指定插入字段】
/**此时要求插入数据的值列表必须对应数据表中所有的列或字段，且顺序也要和数据表中定义字段的顺序保持一致。 */
INSERT INTO table_name 
VALUES 
(value1 [, value2, … , valuen])

--语法格式2【指定插入字段】
INSERT INTO table_name 
(column1 [, column2, …, columnn]) 
VALUES 
(value1 [,value2, …, valuen])

--语法格式3【一次插入多条数据】
INSERT INTO table_name 
VALUES 
(value1 [,value2, …, valuen]),
(value1 [,value2, …, valuen]),
……
(value1 [,value2, …, valuen])

--语法格式4【将查询结果插入另一个表中】
INSERT INTO target_table
(tar_column1 [, tar_column2, …, tar_columnn])
SELECT
src_column1 [, src_column2, …, src_columnn]
FROM source_table
[WHERE condition]

/**将B表的数据插入到A表中（该sql会把B表中所有的数据全部插入到A表中）*/
INSERT INTO A表 (字段1,字段2,字段3,字段4,字段5) SELECT 字段1,字段2,NULL,字段3,NULL FROM B表;

/**如果想要只插入A表中不存在的数据，可以使用下面的sql*/
INSERT INTO A表 (字段1,字段2,字段3,字段4,字段5) 
SELECT 字段1,字段2,NULL,字段3,NULL FROM B表 
WHERE NOT EXISTS (SELECT 1 FROM B表 WHERE B.字段2 = A.字段2);
```

#### 2，数据更新
MySQL支持对数据表中的数据进行更新操作，使用UPDATE语句来更新数据表中的数据记录。可以更新数据表中的所有记录，也可以指定更新条件来更新数据表中的特定记录。
```sql
--更新语句语法格式
UPDATE table_name
SET column1=value1, column2=value2, … , column=valuen
[WHERE condition]

/**使用B表更新A表（如果A表中存在B表中不存在的数据，那么此时这个数据会被更新为null）*/
UPDATE A表 AS A 
SET A.字段1 = (SELECT B.字段1 FROM B表 WHERE A.字段2 = B.字段2);
--使用B表更新A表（更新多个字段）
UPDATE A表 AS A 
LEFT JOIN B表 AS B ON A.字段2= B.字段2 
SET A.field_1 = B.field_1 , A.field_2 = B.field_2;

/**上述sql，如果A表中存在B表中不存在的数据，那么此时这个数据会被更新为null，为了避免上述情况，可以使用以下SQL */
UPDATE A表 AS A 
SET A.字段1 = (SELECT B.字段1 FROM B表 WHERE A.字段2 = B.字段2) 
WHERE EXISTS (SELECT 1 FROM B表 WHERE B.字段2 = A.字段2);
```

#### 3，数据删除
MySQL中使用DELETE语句删除数据。使用DELETE语句删除数据时，可以使用WHERE子句增加删除数据的条件限制。
```sql
--删除语句语法格式
DELETE FROM table_name [WHERE condition]

/**使用B表删除A表中的相同数据【B表中存在与A表相同字段值的数据，则删除A表中的数据】 */
DELETE A表 FROM A表,B表 WHERE A表.字段1=B表.字段1;
--或
DELETE FROM A表 USING A表,B表 WHERE A表.字段1=B表.字段1;

/**使用B表删除A表中不相同的数据【B表中不存在与A表相同字段值的数据，则删除A表中的数据】  */
DELETE A表 FROM A表 LEFT JOIN B表 ON A表.字段1=B表.字段1 WHERE B表.字段1 IS NULL; 
--或
DELETE FROM A表 USING A表 LEFT JOIN B表 ON A表.字段1=B表.字段1 WHERE B表.字段1 IS NULL;

/**删除A表与B表中的相同数据（只保留A表和B表中各自独有的数据） */
DELETE A表,B表 from A表 LEFT JOIN B表 ON A表.字段1=B表.字段1 WHERE A表.字段1='字段值';
```

### MySQL数据查询

```sql
/** 数据准备 */
mysql> INSERT INTO t_goods_category(id, t_category) VALUES (1, '女装/女士精品'),(2, '户外运动');

mysql> INSERT INTO t_goods 
    (t_category_id, t_category, t_name, t_price, t_stock, t_upper_time) 
    VALUES 
    (1, '女装/女士精品', 'T恤', 39.90, 1000, '2020-11-10 00:00:00'),
    (1, '女装/女士精品', '连衣裙', 79.90, 2500, '2020-11-10 00:00:00'),
    (1, '女装/女士精品', '卫衣', 79.90, 1500, '2020-11-10 00:00:00'),
    (1, '女装/女士精品', '牛仔裤', 89.90, 3500, '2020-11-10 00:00:00'),
    (1, '女装/女士精品', '百褶裙', 29.90, 500, '2020-11-10 00:00:00'),
    (1, '女装/女士精品', '呢绒外套', 399.90, 1200, '2020-11-10 00:00:00'),
    (2, '户外运动', '自行车', 399.90, 1000, '2020-11-10 00:00:00'),
    (2, '户外运动', '山地自行车', 1399.90, 2500, '2020-11-10 00:00:00'),
    (2, '户外运动', '登山杖', 59.90, 1500, '2020-11-10 00:00:00'),
    (2, '户外运动', '骑行装备', 399.90, 3500, '2020-11-10 00:00:00'),
    (2, '户外运动', '户外运动外套', 799.90, 500, '2020-11-10 00:00:00'),
    (2, '户外运动', '滑板', 499.90, 1200, '2020-11-10 00:00:00');
```

#### 1，SELECT语句 
```sql
--语句格式
SELECT * FROM table_name;
SELECT column1 [,column2, … ,columnn] FROM table_name;

/**使用完全限定表名查询数据
限定表名指的是在查询语句中的数据表名称前指定数据库名称，表明当前查询的数据表属于哪个数据库
此时，无需切换库，即可查看别的库中的指定表中的数据*/
SELECT column1 [, column2, … , columnn] FROM database_name.table_name
```

#### 2，WHERE语句 
```sql
--插入数据
mysql> INSERT INTO t_goods 
    (t_category_id, t_category, t_name, t_price, t_stock, t_upper_time) 
    VALUES
    (2, '户外运动', null, 39.90, 1000, current_timestamp()),
    (2, '户外运动', '', 79.90, 2500, now());

/**1，条件语句 */
--1.1)，查询字段值为NULL的数据【不包含 '' 数据】
mysql> select id,t_name,t_upper_time from t_goods where t_name is null;
+----+--------+---------------------+
| id | t_name | t_upper_time        |
+----+--------+---------------------+
| 13 | NULL   | 2022-09-27 15:49:30 |
+----+--------+---------------------+
--1.2)，查询字段值为 NOT NULL 的数据
mysql> select id,t_name,t_upper_time from t_goods where t_name is not null;
+----+--------------------+---------------------+
| id | t_name             | t_upper_time        |
+----+--------------------+---------------------+
|  1 | T恤                | 2020-11-10 00:00:00 |
|  2 | 连衣裙             | 2020-11-10 00:00:00 |
|  3 | 卫衣               | 2020-11-10 00:00:00 |
|  4 | 牛仔裤             | 2020-11-10 00:00:00 |
|  5 | 百褶裙             | 2020-11-10 00:00:00 |
|  6 | 呢绒外套           | 2020-11-10 00:00:00 |
|  7 | 自行车             | 2020-11-10 00:00:00 |
|  8 | 山地自行车         | 2020-11-10 00:00:00 |
|  9 | 登山杖             | 2020-11-10 00:00:00 |
| 10 | 骑行装备           | 2020-11-10 00:00:00 |
| 11 | 户外运动外套       | 2020-11-10 00:00:00 |
| 12 | 滑板               | 2020-11-10 00:00:00 |
| 14 |                    | 2022-09-27 15:49:30 |
+----+--------------------+---------------------+
--1.3)，查询空字符串 '' 数据【不包含 NULL 数据】
mysql> select id,t_name,t_upper_time from t_goods where t_name = '';
+----+--------+---------------------+
| id | t_name | t_upper_time        |
+----+--------+---------------------+
| 14 |        | 2022-09-27 15:49:30 |
+----+--------+---------------------+
--1.4)，查询非空字符串数据
mysql> select id,t_name,t_upper_time from t_goods where t_name != '';
+----+--------------------+---------------------+
| id | t_name             | t_upper_time        |
+----+--------------------+---------------------+
|  1 | T恤                | 2020-11-10 00:00:00 |
|  2 | 连衣裙             | 2020-11-10 00:00:00 |
|  3 | 卫衣               | 2020-11-10 00:00:00 |
|  4 | 牛仔裤             | 2020-11-10 00:00:00 |
|  5 | 百褶裙             | 2020-11-10 00:00:00 |
|  6 | 呢绒外套           | 2020-11-10 00:00:00 |
|  7 | 自行车             | 2020-11-10 00:00:00 |
|  8 | 山地自行车         | 2020-11-10 00:00:00 |
|  9 | 登山杖             | 2020-11-10 00:00:00 |
| 10 | 骑行装备           | 2020-11-10 00:00:00 |
| 11 | 户外运动外套       | 2020-11-10 00:00:00 |
| 12 | 滑板               | 2020-11-10 00:00:00 |
+----+--------------------+---------------------+

/**2，DISTINCT语句
DISTINCT关键字可以对查询出的结果数据进行去重处理*/
SELECT DISTINCT column FROM table_name
mysql> select t_category_id,t_category from t_goods;
+---------------+---------------------+
| t_category_id | t_category          |
+---------------+---------------------+
|             1 | 女装/女士精品       |
|             1 | 女装/女士精品       |
|             1 | 女装/女士精品       |
|             1 | 女装/女士精品       |
|             1 | 女装/女士精品       |
|             1 | 女装/女士精品       |
|             2 | 户外运动            |
|             2 | 户外运动            |
|             2 | 户外运动            |
|             2 | 户外运动            |
|             2 | 户外运动            |
|             2 | 户外运动            |
|             2 | 户外运动            |
|             2 | 户外运动            |
+---------------+---------------------+
mysql> select distinct t_category_id,t_category from t_goods;
+---------------+---------------------+
| t_category_id | t_category          |
+---------------+---------------------+
|             1 | 女装/女士精品       |
|             2 | 户外运动            |
+---------------+---------------------+

/**3，ORDER BY语句
默认情况下，排序字段中，NULL值是最小的，即升序排列处于最先的位置
*/
mysql> select id,t_name from t_goods order by t_name ASC;
+----+--------------------+
| id | t_name             |
+----+--------------------+
| 13 | NULL               |
| 14 |                    |
|  1 | T恤                |
|  3 | 卫衣               |
|  6 | 呢绒外套           |
|  8 | 山地自行车         |
| 11 | 户外运动外套       |
| 12 | 滑板               |
|  4 | 牛仔裤             |
|  9 | 登山杖             |
|  5 | 百褶裙             |
|  7 | 自行车             |
|  2 | 连衣裙             |
| 10 | 骑行装备           |
+----+--------------------+
--按照 t_name字段升序排列，但是 NULL处于最末尾
mysql> select id,t_name from t_goods order by ISNULL(t_name),t_name ASC;
+----+--------------------+
| id | t_name             |
+----+--------------------+
| 14 |                    |
|  1 | T恤                |
|  3 | 卫衣               |
|  6 | 呢绒外套           |
|  8 | 山地自行车         |
| 11 | 户外运动外套       |
| 12 | 滑板               |
|  4 | 牛仔裤             |
|  9 | 登山杖             |
|  5 | 百褶裙             |
|  7 | 自行车             |
|  2 | 连衣裙             |
| 10 | 骑行装备           |
| 13 | NULL               |
+----+--------------------+

/**4，HAVING语句 
HAVING语句主要对GROUP BY语句进行条件限制，在使用GROUP BY语句对查询数据进行分组时，只有满足HAVING条件的分组数据才会被显示。*/
mysql> SELECT t_category_id, GROUP_CONCAT(t_name) FROM t_goods GROUP BY t_category_id HAVING SUM(t_price) > 1000;
--对t_goods数据表中的数据进行分组，并且显示总价格大于1000的分组数据。
+---------------+-----------------------------------------------------------------------------+
| t_category_id | GROUP_CONCAT(t_name)                                                        |
+---------------+-----------------------------------------------------------------------------+
|             2 | 自行车,山地自行车,登山杖,骑行装备,户外运动外套,滑板,                        |
+---------------+-----------------------------------------------------------------------------+

/**5，WITH ROLLUP语句
WITH ROLLUP语句通常在GROUP BY语句中使用，在GROUP BY语句中添加WITH ROLLUP语句后会在查询出的分组记录的最后显示一条记录，显示本次查询出的所有记录的总和信息。 */
mysql> SELECT t_category_id, COUNT(*) FROM t_goods GROUP BY t_category_id WITH ROLLUP;
--按照t_category_id字段分组，同时显示查询出的结果记录的总和信息
+---------------+----------+
| t_category_id | COUNT(*) |
+---------------+----------+
|             1 |        6 |
|             2 |        8 |
|          NULL |       14 |
+---------------+----------+

/**6，LIMIT语句
LIMIT语句可以限制返回结果的记录行数，也可以实现分页查询。*/
LIMIT [m], n  --m：表示从哪一行开始显示，可以省略。n：表示返回结果数据的行数。
--查询t_goods数据表中从第6条数据开始，行数为4的记录。
mysql> SELECT id, t_category, t_name, t_price, t_stock FROM t_goods LIMIT 6, 4;
--或
mysql> SELECT id, t_category, t_name, t_price, t_stock FROM t_goods LIMIT 4 OFFSET 6;
+----+--------------+-----------------+---------+---------+
| id | t_category   | t_name          | t_price | t_stock |
+----+--------------+-----------------+---------+---------+
|  7 | 户外运动     | 自行车          |  399.90 |    1000 |
|  8 | 户外运动     | 山地自行车      | 1399.90 |    2500 |
|  9 | 户外运动     | 登山杖          |   59.90 |    1500 |
| 10 | 户外运动     | 骑行装备        |  399.90 |    3500 |
+----+--------------+-----------------+---------+---------+
```

#### 3，JOIN语句
```sql
--创建省份表
create table province_info(id int not null auto_increment primary key,province_abbr char(5),province_name varchar(30));
--创建省份城市表
create table province_city_info(
	id int not null auto_increment primary key,
	province_id int,
	city_code enum ('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'),
	city_abbr char(5),
	city_name varchar(30),
	constraint province_id_foreign_key foreign key(province_id) references province_info(id)
);
--修改省份城市表，添加车牌代码Set
ALTER TABLE province_city_info 
ADD COLUMN license_codes SET('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z');
--创建城市车牌代码表
create table Car_license_code_info(
	id int not null auto_increment primary key,
	province_id int,
	city_id int,
	license_code enum ('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'),
	constraint city_id_foreign_key foreign key(city_id) references province_city_info(id)
);
--插入省份数据
insert into province_info(province_abbr,province_name) values ('京','北京市'),('津','天津市'),('沪','上海市'),('渝','重庆市'),('冀','河北省'),('晋','山西省'),('鲁','山东省'),('豫','河南省');
--插入城市数据
insert into province_city_info (province_id,city_code,city_abbr,city_name,license_codes) values
	(5,'A','石','石家庄市','A'),
	(5,'B','唐','唐山市','B'),
	(5,'C','秦','秦皇岛市','C'),
	(5,'D','邯','邯郸市','D'),
	(5,'E','邢','邢台市','E'),
	(5,'F','保','保定市','F'),
	(5,'G','张','张家口市','G'),
	(5,'H','承','承德市','H'),
	(5,'J','沧','沧州市','J'),
	(5,'R','廊','廊坊市','R'),
	(5,'T','衡','衡水市','T');
insert into province_city_info (province_id,city_code,city_abbr,city_name,license_codes) values
	(7,'A','济','济南市','A,S'),
	(7,'B','青','青岛市','B,U'),
	(7,'C','淄','淄博市','C'),
	(7,'D','枣','枣庄市','D'),
	(7,'E','东','东营市','E'),
	(7,'F','烟','烟台市','F,Y'),
	(7,'G','潍','潍坊市','G,V'),
	(7,'H','任','济宁市','H'),
	(7,'J','泰','泰安市','J'),
	(7,'K','威','威海市','K'),
	(7,'L','日','日照市','L'),
	(7,'M','滨','滨州市','M'),
	(7,'N','德','德州市','N'),
	(7,'P','聊','聊城市','P'),
	(7,'Q','沂','临沂市','Q'),
	(7,'R','菏','菏泽市','R');
--插入城市车牌号数据
insert into Car_license_code_info (province_id,city_id,license_code) values 
	(5,13,'A'),(5,14,'B'),(5,15,'C'),(5,16,'D'),(5,17,'E'),(5,18,'F'),(5,19,'G'),(5,20,'H'),(5,21,'J'),(5,22,'R'),(5,23,'T'),
	(7,24,'A'),(7,24,'S'),(7,25,'B'),(7,25,'U'),(7,26,'C'),(7,27,'D'),(7,28,'E'),(7,29,'F'),(7,29,'Y'),(7,30,'G'),(7,30,'V'),
	(7,31,'H'),(7,32,'J'),(7,33,'K'),(7,34,'L'),(7,35,'M'),(7,36,'N'),(7,37,'P'),(7,38,'Q'),(7,39,'R');    
```

```sql
/**3.1，INNER JOIN语句（可简写为 JOIN语句）
内连接语句，返回左右两个表中均符合查询条件的记录 */
select 
province.province_abbr ,
province.province_name ,
city.city_code,
city.city_abbr ,
city.city_name ,
city.license_codes 
from province_info province 
inner join province_city_info city on province.id  = city.province_id
;

/**3.2，LEFT JOIN语句
左连接语句，返回左表中的所有记录和右表中符合查询条件的记录。
*/
select 
province.province_abbr ,
province.province_name ,
city.city_code,
city.city_abbr ,
city.city_name ,
city.license_codes 
from province_info province 
left join province_city_info city on province.id  = city.province_id
;

/**3.3，RIGHT JOIN语句
右连接语句，返回右表中所有的数据记录和左表中符合条件的数据记录。
*/
select 
province.province_abbr ,
province.province_name ,
city.city_code,
city.city_abbr ,
city.city_name ,
city.license_codes 
from province_info province 
right join province_city_info city on province.id  = city.province_id
;

/**3.4，CROSS JOIN语句
交叉连接，当没有使用连接条件时，使用CROSS JOIN语句连接的两张表，每张表中的每行数据都会与另一张表中的所有数据进行连接。（M*N笛卡尔积）
当使用连接条件时，会输出符合连接条件的结果数据。
*/
select 
province.province_abbr ,
province.province_name ,
city.city_code,
city.city_abbr ,
city.city_name ,
city.license_codes 
from province_info province 
cross join province_city_info city
[on province.id  = city.province_id] 
;
```

#### 4，子查询语句
MySQL支持将一个查询语句嵌套在另一个查询语句中，嵌套在另一个查询语句中的SQL语句就是子查询语句。子查询语句可以添加到SELECT、UPDATE和DELETE语句中，常用的操作符包括ANY、SOME、ALL、EXISTS、NOT EXISTS、IN和NOT IN等。

##### 4.1，ANY子查询（SOME子查询）
ANY关键字表示如果与子查询返回的任何值相匹配，则返回TRUE，否则返回FALSE。

SOME子查询的作用与ANY子查询的作用相同。
```sql
--查询t_goods数据表中t_category_id字段值大于t_goods_category数据表中任意一个id字段值的数据。
mysql> SELECT id, t_category_id, t_category, t_name, t_price FROM t_goods WHERE t_category_id > ANY (SELECT id FROM t_goods_category);
+----+---------------+--------------+--------------------+---------+
| id | t_category_id | t_category   | t_name             | t_price |
+----+---------------+--------------+--------------------+---------+
|  7 |             2 | 户外运动     | 自行车             |  399.90 |
|  8 |             2 | 户外运动     | 山地自行车         | 1399.90 |
|  9 |             2 | 户外运动     | 登山杖             |   59.90 |
| 10 |             2 | 户外运动     | 骑行装备           |  399.90 |
| 11 |             2 | 户外运动     | 户外运动外套       |  799.90 |
| 12 |             2 | 户外运动     | 滑板               |  499.90 |
| 13 |             2 | 户外运动     | NULL               |   39.90 |
| 14 |             2 | 户外运动     |                    |   79.90 |
+----+---------------+--------------+--------------------+---------+
```

##### 4.2，ALL子查询
ALL关键字表示如果同时满足所有子查询的条件，则返回TRUE，否则返回FALSE。
```sql
--查询t_goods数据表中t_category_id字段值大于t_goods_category数据表中所有id字段值的数据
mysql> SELECT id, t_category_id, t_category, t_name, t_price FROM t_goods WHERE t_category_id > ALL (SELECT id FROM t_goods_category);
Empty set (0.00 sec)
```

#### 5，UNION联合语句
UNION语句可以对使用多个SELECT语句查询出的结果数据进行合并，合并查询结果数据时，要求每个SELECT语句查询出的数据的列数和数据类型必须相同，并且相互对应。
```sql
--UNION语句语法
SELECT col1 [,col2, … , coln] FROM table1
UNION [ALL]
SELECT col1 [,col2, … , coln] FROM table2

/**UNION语句与UNION ALL语句
UNION语句，结果数据中会包含重复的数据记录。
UNION ALL语句，会删除重复的记录，返回的每行数据都是唯一的。
执行UNION ALL语句时所需要的资源比UNION语句少。如果明确知道合并数据后的结果数据不存在重复数据，或者不需要去除重复的数据，则尽量使用UNION ALL语句，以提高数据查询的效率。
 */
```

### MySQL索引

#### 1，索引简介
数据库中的索引是一个排好序的数据结构，实际上索引记录了添加索引的列值与数据表中每行记录之间的一一对应关系。

##### 1.1，MySQL遍历表的方式
MySQL通常以两种方式遍历数据表中的数据，分别是顺序遍历和索引遍历。

* 顺序遍历
从数据表中的第一行数据开始，顺序扫描数据表中所有的数据，直到在数据表中找到匹配查询条件的目标数据。使用这种方式查询数据，数据量较小时无明显的性能问题。随着数据量越来越大，查询性能越来越低。

* 索引遍历
通过遍历索引找到索引后，根据索引直接定位到数据表中的记录行。使用索引遍历的前提就是需要在数据表中建立相应的索引，查询数据时，根据列上的索引定位数据记录行，能极大地提高数据查询的性能。

##### 1.2，索引的优点与缺点

* 优点
1）所有的字段类型都可以添加索引；
2）可以为数据表中的一列或多列添加索引；
3）能够极大地提高数据的查询性能；
4）能够提高数据分组与排序的性能。

* 缺点
1）索引本身需要占用一定的存储空间，如果大量地使用索引，则索引文件会占用大量的磁盘空间。
2）索引的创建与维护需要耗费一定的时间，随着数据量的不断增长，耗费的时间会越来越长。
3）对数据表中的数据进行增加、删除和修改操作时，MySQL内部需要对索引进行动态维护，这也会消耗一定的维护时间。

##### 1.3，索引的创建原则

1. 尽量使用小的数据类型的列创建索引。数据类型越小，所占用的存储空间越小，不仅能够节省系统的存储空间，而且处理效率也会更高。
2. 尽量使用简单的数据类型的列创建索引。处理简单的数据类型比复杂的数据类型，系统开销小，因为数据类型越复杂，执行数据的比较操作时采取的比较操作也就越复杂。
3. 尽量不要在NULL值字段上创建索引。在NULL值字段上创建索引，会使索引、索引的统计信息和比较运算更加复杂。因此在创建数据表时，尽量不要使字段的默认值为NULL，将字段设置为NOT NULL，并赋予默认值。

#### 2，创建数据表时创建索引
```sql
/**创建数据表时，为字段创建索引 */
CREATE TABLE table_name 
column_name1 data_type1 [, column_name2 data_type2, …, column_namen data_typen]
[PRIMARY | UNIQUE | FULLTEXT | SPATIAL] [INDEX | KEY]
[index_name] (column_name [length])
[ASC | DESC]
字段说明：
·[PRIMARY | UNIQUE | FULLTEXT | SPATIAL]：索引的类型，分别表示唯一索引、全文索引和空间索引，创建数据表时，索引类型可以省略。
·[INDEX | KEY]：作用基本相同，指定在数据表中创建索引。
·[index_name]：创建的索引名称，名称可以省略。
·column_name：需要创建的索引列，可以是数据表中的单个列，也可以是数据表中的多个列。
·length：创建索引时，为索引指定的长度，参数可以省略。需要注意的是，只有字符串类型的字段才能为索引指定长度。
·[ASC | DESC]：指定以升序或者降序的方式来存储索引值，参数可省略。
```

##### 2.1，创建普通索引
普通索引是所有索引类型中最基本的索引类型，没有唯一性等限制，能够加快数据的检索效率。
```sql
mysql> create table t_index_1 (
	id int not null,
	t_name varchar(30) not null default '',
	t_department_id int not null default 0,
	t_create_time DATETIME,
	index department_id_index(t_department_id)
	);
--当创建索引未指定索引名称时，MySQL默认会以创建索引的字段名称来命名索引。
mysql> show create table t_index_1 \G
*************************** 1. row ***************************
       Table: t_index_1
Create Table: CREATE TABLE `t_index_1` (
  `id` int(11) NOT NULL,
  `t_name` varchar(30) NOT NULL DEFAULT '',
  `t_department_id` int(11) NOT NULL DEFAULT '0',
  `t_create_time` datetime DEFAULT NULL,
  KEY `department_id_index` (`t_department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
```

##### 2.2，创建唯一索引
* 创建唯一索引的列值必须唯一，但是允许值为空。如果创建的唯一索引中包含多个字段，也就是复合索引，则索引中包含的多个字段的值的组合必须唯一。
* 主键索引是特殊类型的唯一索引，与唯一索引不同的是，主键索引不仅具有唯一性，而且不能为空，而唯一索引中的列的数据可能为空。
```sql
mysql> create table t_index_2  (
	id int not null PRIMARY KEY,
	t_card_id varchar(30),
	t_name varchar(30) not null default '',
	t_department_id int not null default 0,
	t_create_time DATETIME,
	UNIQUE INDEX card_id_index(t_card_id)
	);
mysql> show create table t_index_2 \G
*************************** 1. row ***************************
       Table: t_index_2
Create Table: CREATE TABLE `t_index_2` (
  `id` int(11) NOT NULL,
  `t_card_id` varchar(30) DEFAULT NULL,
  `t_name` varchar(30) NOT NULL DEFAULT '',
  `t_department_id` int(11) NOT NULL DEFAULT '0',
  `t_create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `card_id_index` (`t_card_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
```

##### 2.3，创建组合索引
* 单列索引表示在创建的索引中，只包含数据表中的单个字段或列。MySQL中，支持在一张数据表中创建多个单列索引。上述示例中，创建的均是单列索引。
* 组合索引表示在创建的索引中，包含数据表中的多个字段或列。MySQL中，同样支持在一张数据表中创建多个组合索引。在使用组合索引查询数据时，MySQL支持最左匹配原则。
```sql
mysql> create table t_index_3  (
	id int not null PRIMARY KEY,
	t_no varchar(30) not null default '',
	t_name varchar(30) not null default '',
	t_department_id int not null default 0,
	t_create_time DATETIME,
	INDEX no_name_department_index(t_no,t_name,t_department_id)
	);
/**名称为no_name_department_index的复合索引在进行存储时，是按照t_no/t_name/t_department_id的顺序进行存放的。
根据索引的最左匹配原则，当在查询数据时，使用(t_no)、(t_no,t_name)和(t_no,t_name,t_department_id)中的一种进行查询时，MySQL会使用索引。
当使用(t_name)、(t_department_id)和(t_name,t_department_id)查询数据时，MySQL不会使用索引。 */	

mysql> show create table t_index_3 \G
*************************** 1. row ***************************
       Table: t_index_3
Create Table: CREATE TABLE `t_index_3` (
  `id` int(11) NOT NULL,
  `t_no` varchar(30) NOT NULL DEFAULT '',
  `t_name` varchar(30) NOT NULL DEFAULT '',
  `t_department_id` int(11) NOT NULL DEFAULT '0',
  `t_create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `no_name_department_index` (`t_no`,`t_name`,`t_department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
```

使用EXPALIN查看t_index_3数据表中索引的使用情况。【最左匹配原则】
```sql
/**1,使用t_no字段查询数据。【使用了索引】 */
mysql> EXPLAIN SELECT * FROM t_index_3 WHERE t_no = '001' \G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: t_index_3
   partitions: NULL
         type: ref
possible_keys: no_department_id_index
          key: no_department_id_index
      key_len: 92
          ref: const
         rows: 1
     filtered: 100.00
        Extra: NULL
1 row in set, 1 warning (0.00 sec)

/**2,使用t_no，t_name字段查询数据。【使用了索引】 */
mysql> EXPLAIN SELECT * FROM t_index_3 WHERE t_no = '001' AND t_name= 'johann' \G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: t_index_3
   partitions: NULL
         type: ref
possible_keys: no_department_id_index
          key: no_department_id_index
      key_len: 184
          ref: const,const
         rows: 1
     filtered: 100.00
        Extra: NULL
1 row in set, 1 warning (0.00 sec)

/**3,使用t_no，t_name，t_department_id字段查询数据。【使用了索引】 */
mysql> EXPLAIN SELECT * FROM t_index_3 WHERE t_no = '001' AND t_name= 'johann' AND t_department_id = 1 \G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: t_index_3
   partitions: NULL
         type: ref
possible_keys: no_department_id_index
          key: no_department_id_index
      key_len: 188
          ref: const,const,const
         rows: 1
     filtered: 100.00
        Extra: NULL
1 row in set, 1 warning (0.00 sec)

/**4,使用t_name字段查询数据。【没有使用索引】 */
mysql> EXPLAIN SELECT * FROM t_index_3 WHERE t_name= 'johann' \G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: t_index_3
   partitions: NULL
         type: ALL
possible_keys: NULL
          key: NULL
      key_len: NULL
          ref: NULL
         rows: 1
     filtered: 100.00
        Extra: Using where
1 row in set, 1 warning (0.00 sec)

/**5,使用t_department_id字段查询数据。【没有使用索引】 */
mysql> EXPLAIN SELECT * FROM t_index_3 WHERE t_department_id = 1 \G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: t_index_3
   partitions: NULL
         type: ALL
possible_keys: NULL
          key: NULL
      key_len: NULL
          ref: NULL
         rows: 1
     filtered: 100.00
        Extra: Using where
1 row in set, 1 warning (0.00 sec)

/**6,使用t_name，t_department_id字段查询数据。【没有使用索引】 */
mysql> EXPLAIN SELECT * FROM t_index_3 WHERE t_name= 'johann' AND t_department_id = 1 \G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: t_index_3
   partitions: NULL
         type: ALL
possible_keys: NULL
          key: NULL
      key_len: NULL
          ref: NULL
         rows: 1
     filtered: 100.00
        Extra: Using where
1 row in set, 1 warning (0.00 sec)

/**7,使用t_no，t_department_id字段查询数据。【使用了索引】 */
mysql> EXPLAIN SELECT * FROM t_index_3 WHERE t_no = '001' AND t_department_id = 1 \G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: t_index_3
   partitions: NULL
         type: ref
possible_keys: no_department_id_index
          key: no_department_id_index
      key_len: 92
          ref: const
         rows: 1
     filtered: 100.00
        Extra: Using index condition
1 row in set, 1 warning (0.00 sec)

/**8,使用t_no，t_name，t_department_id字段查询数据，但是查询字段顺序颠倒。【使用了索引】 */
mysql> EXPLAIN SELECT * FROM t_index_3 WHERE t_name= 'johann' AND t_department_id = 1 AND t_no = '001' \G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: t_index_3
   partitions: NULL
         type: ref
possible_keys: no_department_id_index
          key: no_department_id_index
      key_len: 188
          ref: const,const,const
         rows: 1
     filtered: 100.00
        Extra: NULL
1 row in set, 1 warning (0.00 sec)
```

##### 2.4，创建全文索引
创建全文索引时，对列的数据类型有一定的限制，只能为定义为CHAR、VARCHAR和TEXT数据类型的列创建全文索引，全文索引不支持对列的局部进行索引。
```sql
mysql> create table t_index_4  (
	id int not null PRIMARY KEY,
	t_name varchar(30) not null default '',
	t_info varchar(200),
	t_create_time DATETIME,
	FULLTEXT INDEX info_index(t_info)
	);
/**在MySQL 5.7之前的版本中，只有MyISAM存储类型的数据表支持全文索引。
在MySQL 5.7的部分版本和MySQL 8.x版本中，InnoDB存储引擎也支持创建全文索引。 */	
mysql> show create table t_index_4 \G
*************************** 1. row ***************************
       Table: t_index_4
Create Table: CREATE TABLE `t_index_4` (
  `id` int(11) NOT NULL,
  `t_name` varchar(30) NOT NULL DEFAULT '',
  `t_info` varchar(200) DEFAULT NULL,
  `t_create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  FULLTEXT KEY `info_index` (`t_info`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

--使用EXPLAIN查看索引的使用情况
mysql> EXPLAIN SELECT * FROM t_index_4 WHERE MATCH (t_info) AGAINST ('abc') \G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: t_index_4
   partitions: NULL
         type: fulltext
possible_keys: info_index
          key: info_index
      key_len: 0
          ref: const
         rows: 1
     filtered: 100.00
        Extra: Using where; Ft_hints: sorted
1 row in set, 1 warning (0.00 sec)
```

##### 2.5，创建空间索引
MySQL中支持在 GEOMETRY 数据类型的字段上创建空间索引。
```sql
mysql> CREATE TABLE t_index_5 (
    id INT NOT NULL,
    t_location GEOMETRY NOT NULL,
    SPATIAL INDEX geo_index(t_location)
    );
mysql> show create table t_index_5 \G
*************************** 1. row ***************************
       Table: t_index_5
Create Table: CREATE TABLE `t_index_5` (
  `id` int(11) NOT NULL,
  `t_location` geometry NOT NULL,
  SPATIAL KEY `geo_index` (`t_location`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
```

#### 3，为已有数据表添加索引
MySQL支持为已经存在的数据表中的字段创建索引，可以使用ALTER TABLE语句和CREATE INDEX语句为表中的字段创建索引。

```sql
/**1，使用 ALTER TABLE语句添加索引 */
ALTER TABLE table_name 
ADD [PRIMARY | UNIQUE | FULLTEXT | SPATIAL] [INDEX | KEY]
[index_name] (column_name [length])
[ASC | DESC]

/**2，使用 CREATE INDEX语句添加索引 */
CREATE [UNIQUE | FULLTEXT | SPATIAL] INDEX index_name
ON table_name(column_name [length])
[ASC | DESC]
```

创建测试表
```sql
--t_index_alter 数据表用于测试使用ALTER TABLE语句创建索引，t_index_create 数据表用于测试使用CREATE INDEX语句创建索引
mysql> CREATE TABLE t_index_alter (
    id int(11) NOT NULL,
    t_category_id int(11) DEFAULT '0',
    t_category varchar(30) DEFAULT '',
    t_name varchar(50) DEFAULT '',
    t_price decimal(10,2) DEFAULT '0.00',
    t_stock int(11) DEFAULT '0',
    t_upper_time datetime DEFAULT NULL,
    t_location geometry NOT NULL
    );
```

##### 3.1，创建普通索引
```sql
mysql> ALTER TABLE t_index_alter ADD INDEX category_id_index(t_category_id);
mysql> CREATE INDEX category_id_index ON t_index_create(t_category_id);
```
##### 3.2，创建唯一索引
```sql
mysql> ALTER TABLE t_index_alter ADD UNIQUE INDEX category_index(t_category);
mysql> CREATE UNIQUE INDEX category_index ON t_index_create(t_category);
```
##### 3.3，创建主键索引
```sql
mysql> ALTER TABLE t_index_alter ADD PRIMARY KEY (id);
/**MySQL不支持使用 CREATE INDEX语句创建主键索引。 */
```
##### 3.4，创建单列索引
```sql
mysql> ALTER TABLE t_index_alter ADD INDEX name_index(t_name);
mysql> CREATE INDEX name_index ON t_index_create(t_name);
```
##### 3.5，创建组合索引
```sql
mysql> ALTER TABLE t_index_alter ADD INDEX category_name_index(t_category,t_name);
mysql> CREATE INDEX category_name_index ON t_index_create(t_category,t_name);
```
##### 3.6，创建全文索引
```sql
mysql> ALTER TABLE t_index_alter ADD FULLTEXT INDEX name_fulltext_index(t_name);
mysql> CREATE FULLTEXT INDEX name_fulltext_index ON t_index_create(t_name);
```
##### 3.7，创建空间索引
```sql
mysql> ALTER TABLE t_index_alter ADD SPATIAL INDEX location_index(t_location);
mysql> CREATE SPATIAL INDEX location_index ON t_index_create(t_location);
```

#### 4，删除索引
MySQL中可以使用ALTER TABLE语句和DROP INDEX语句删除索引。
```sql
/**1，使用 ALTER TABLE语句删除索引 */
ALTER TABLE table_name DROP INDEX index_name

/**2，使用 CREATE INDEX语句删除索引 */
DROP INDEX index_name ON table_name
--删除索引示例
mysql> ALTER TABLE t_index_alter DROP INDEX name_fulltext_index;
mysql> DROP INDEX name_fulltext_index ON t_index_create;
```

#### 5，隐藏索引
MySQL 8.x开始支持隐藏索引，隐藏索引不会被优化器使用，但是仍然需要维护。隐藏索引通常会在软删除和灰度发布的场景中使用。

在MySQL 5.7版本之前，只能通过显式的方式删除索引，此时，如果发现删除索引后出现错误，又只能通过显式创建索引的方式将删除的索引创建回来。如果数据表中的数据量非常大，或者数据表本身比较大，这种操作就会消耗系统过多的资源，操作成本非常高。

* 软删除：从MySQL 8.x开始支持隐藏索引，只需要将待删除的索引设置为隐藏索引，使查询优化器不再使用这个索引，确认将索引设置为隐藏索引后系统不受任何响应，就可以彻底删除索引。这种通过先将索引设置为隐藏索引，再删除索引的方式就是软删除。

* 灰度发布：创建索引时，将索引设置为隐藏索引，通过修改查询优化器的开关，使隐藏索引对查询优化器可见，通过EXPLAIN对索引进行测试，确认新创建的隐藏索引有效，再将其设置为可见索引。这种方式就是灰度发布。

```sql
/**隐藏索引的创建方式 */
ALTER TABLE table_name ADD INDEX [index_name] (column_name [length]) INVISIBLE

CREATE INDEX index_name ON table_name (column_name [length]) INVISIBLE
```

创建测试表
```sql
mysql> CREATE TABLE invisible_index_test (
    visible_column INT, 
    invisible_column INT
    );
```

索引操作
```sql
/**1，在visible_column字段上创建普通索引 */
mysql> CREATE INDEX visible_column_index ON invisible_index_test (visible_column);

/**2，在invisible_column字段上创建隐藏索引 */
mysql> CREATE INDEX invisible_column_index ON invisible_index_test (invisible_column) INVISIBLE;

/**3，查看invisible_index_test数据表中的索引 */
mysql> SHOW CREATE TABLE invisible_index_test \G
*************************** 1. row ***************************
       Table: invisible_index_test
Create Table: CREATE TABLE `invisible_index_test` (
  `visible_column` int(11) DEFAULT NULL,
  `invisible_column` int(11) DEFAULT NULL,
  KEY `visible_column_index` (`visible_column`),
  KEY `invisible_column_index` (`invisible_column`) /*!80000 INVISIBLE */
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

/**4，使用SHOW INDEX FROM语句查看invisible_index_test数据表中的索引 */
mysql> SHOW INDEX FROM invisible_index_test \G
*************************** 1. row ***************************
        Table: invisible_index_test
   Non_unique: 1
     Key_name: visible_column_index
 Seq_in_index: 1
  Column_name: visible_column
    Collation: A
  Cardinality: 0
     Sub_part: NULL
       Packed: NULL
         Null: YES
   Index_type: BTREE
      Comment: 
Index_comment: 
      Visible: YES
   Expression: NULL
*************************** 2. row ***************************
        Table: invisible_index_test
   Non_unique: 1
     Key_name: invisible_column_index
 Seq_in_index: 1
  Column_name: invisible_column
    Collation: A
  Cardinality: 0
     Sub_part: NULL
       Packed: NULL
         Null: YES
   Index_type: BTREE
      Comment: 
Index_comment: 
      Visible: NO
   Expression: NULL

/**5，使用EXPAIN查看查询优化器对索引的使用情况 */
--查询优化器使用了可见索引
mysql> EXPLAIN SELECT * FROM invisible_index_test WHERE visible_column = 1 \G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: invisible_index_test
   partitions: NULL
         type: ref
possible_keys: visible_column_index
          key: visible_column_index
      key_len: 5
          ref: const
         rows: 1
     filtered: 100.00
        Extra: NULL
--查询优化器没用使用隐藏索引
mysql> EXPLAIN SELECT * FROM invisible_index_test WHERE invisible_column = 1 \G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: invisible_index_test
   partitions: NULL
         type: ALL
possible_keys: NULL
          key: NULL
      key_len: NULL
          ref: NULL
         rows: 1
     filtered: 100.00
        Extra: Using where

/**6，使隐藏索引对查询优化器可见。
在MySQL 8.x版本中，为索引提供了一种新的测试方式，可以通过查询优化器的一个开关来打开某个设置，使隐藏索引对查询优化器可见。 
*/
--查看查询优化器的开关设置，找到【use_invisible_indexes=off】，说明隐藏索引默认对查询优化器不可见。
mysql> select @@optimizer_switch \G 
*************************** 1. row ***************************
@@optimizer_switch: index_merge=on,index_merge_union=on,index_merge_sort_union=on,index_merge_
intersection=on,engine_condition_pushdown=on,index_condition_pushdown=on,mrr=on,mrr_cost_based=on,
block_nested_loop=on,batched_key_access=off,materialization=on,semijoin=on,loosescan=on,firstmatch=
on,duplicateweedout=on,subquery_materialization_cost_based=on,use_index_extensions=on,condition_
fanout_filter=on,derived_merge=on,use_invisible_indexes=off,skip_scan=on,hash_join=on

--使隐藏索引对查询优化器可见
mysql> set session optimizer_switch="use_invisible_indexes=on";
--使隐藏索引对查询优化器不可见
mysql> set session optimizer_switch="use_invisible_indexes=off";

--再次查看查询优化器的开关设置，use_invisible_indexes=on，此时隐藏索引对查询优化器可见
mysql>  select @@optimizer_switch \G
*************************** 1. row ***************************
@@optimizer_switch: index_merge=on,index_merge_union=on,index_merge_sort_union=on,index_merge_
intersection=on,engine_condition_pushdown=on,index_condition_pushdown=on,mrr=on,mrr_cost_based=on,
block_nested_loop=on,batched_key_access=off,materialization=on,semijoin=on,loosescan=on,firstmatch=
on,duplicateweedout=on,subquery_materialization_cost_based=on,use_index_extensions=on,condition_
fanout_filter=on,derived_merge=on,use_invisible_indexes=on,skip_scan=on,hash_join=on


/**7，再次使用EXPLAIN查看以字段invisible_column作为查询条件时的索引使用情况。此时，查询优化器会使用隐藏索引来查询数据。 */
mysql> EXPLAIN SELECT * FROM invisible_index_test WHERE invisible_column = 1 \G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: invisible_index_test
   partitions: NULL
         type: ref
possible_keys: invisible_column_index
          key: invisible_column_index
      key_len: 5
          ref: const
         rows: 1
     filtered: 100.00
        Extra: NULL
1 row in set, 1 warning (0.00 sec)

/**8，将一个普通索引设置为隐藏索引；将一个隐藏索引修改为普通索引 */
--将一个普通索引设置为隐藏索引
mysql> ALTER TABLE invisible_index_test ALTER INDEX visible_column_index INVISIBLE;
--将一个隐藏索引修改为普通索引
mysql> ALTER TABLE invisible_index_test ALTER INDEX invisible_column_index VISIBLE;  

/**注意：MySQL中不能将主键设置为隐藏索引。 */
```

#### 6，降序索引
从MySQL 4版本开始就已经支持降序索引的语法了，但是直到MySQL 8.x版本才开始真正支持降序索引，且只有MySQL的InnoDB存储引擎支持降序索引，同时，只有BTREE索引支持降序索引。另外，在MySQL 8.x版本中，不再对GROUP BY语句进行隐式排序。

#### 7，虚拟列和虚拟索引
Mysql 5.7 中推出了一个非常实用的功能 虚拟列 Generated (Virtual) Columns

在MySQL 5.7中，支持两种Generated Column，即Virtual Generated Column和Stored Generated Column，前者只将Generated Column保存在数据字典中（表的元数据），并不会将这一列数据持久化到磁盘上；后者会将Generated Column持久化到磁盘上，而不是每次读取的时候计算所得。

MySQL 5.7中，不指定Generated Column的类型，默认是Virtual Column。
```sql
/**1，创建表的同时，统建虚拟列 */
mysql> create table t_generated_column(
	c1 varchar(10),
	c2 varchar(10),
	c3 varchar(10) generated always as (UPPER(c1))
);
mysql> show create table t_generated_column \G
*************************** 1. row ***************************
       Table: t_generated_column
Create Table: CREATE TABLE `t_generated_column` (
  `c1` varchar(10) DEFAULT NULL,
  `c2` varchar(10) DEFAULT NULL,
  `c3` varchar(10) GENERATED ALWAYS AS (upper(`c1`)) VIRTUAL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/**2，建表之后，单独添加虚拟列 */
mysql> ALTER TABLE t_generated_column ADD COLUMN c3 VARCHAR(10) GENERATED ALWAYS AS (UPPER(c1));

/**3，mysql中在索引字段上使用函数，会导致索引失效 */
--创建索引
mysql> create index c2_index on t_generated_column(c2);
--验证，索引失效
mysql> explain select * from t_generated_column where upper(c2) = 'MYSQL' \G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: t_generated_column
   partitions: NULL
         type: ALL
possible_keys: NULL
          key: NULL
      key_len: NULL
          ref: NULL
         rows: 1
     filtered: 100.00
        Extra: Using where

/**4，对虚拟列创建虚拟索引，在真实字段上使用函数，此时会使用虚拟索引 */
--创建虚拟索引
mysql> create index c3_index on t_generated_column(c3);
--使用了虚拟索引
mysql> explain select * from t_generated_column where upper(c1) = 'MYSQL' \G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: t_generated_column
   partitions: NULL
         type: ref
possible_keys: c3_index
          key: c3_index
      key_len: 33
          ref: const
         rows: 1
     filtered: 100.00
        Extra: NULL
```

#### 8，函数索引
从MySQL 8.0.13 版本开始支持在索引中使用函数或者表达式的值，也就是在索引中可以包含函数或者表达式。函数索引中支持降序索引，同时，MySQL 8.x版本支持对JSON类型的数据添加函数索引。

函数索引可以基于虚拟列功能实现。	
```sql
/**1，创建数据表func_index */
mysql> CREATE TABLE func_index (
    -> c1 VARCHAR(10),
    -> c2 VARCHAR(10)
    -> );

/**2，为fun_index数据表的c1字段创建普通索引 */
mysql> CREATE INDEX c1_index ON func_index (c1);

/**3，为func_index数据表的c2字段创建一个将字段值转化为大写的函数索引 */
mysql> CREATE INDEX c2_index ON func_index ((UPPER(c2)));

/**4，查看func_index数据表中的索引 */
mysql> SHOW INDEX FROM func_index \G
*************************** 1. row ***************************
        Table: func_index
   Non_unique: 1
     Key_name: c1_index
 Seq_in_index: 1
  Column_name: c1
    Collation: A
  Cardinality: 0
     Sub_part: NULL
       Packed: NULL
         Null: YES
   Index_type: BTREE
      Comment: 
Index_comment: 
      Visible: YES
   Expression: NULL
*************************** 2. row ***************************
        Table: func_index
   Non_unique: 1
     Key_name: c2_index
 Seq_in_index: 1
  Column_name: NULL
    Collation: A
  Cardinality: 0
     Sub_part: NULL
       Packed: NULL
         Null: YES
   Index_type: BTREE
      Comment: 
Index_comment: 
      Visible: YES
   Expression: upper(`c2`)

/**5，验证查询优化器对索引的使用情况 */
--未使用索引
mysql> EXPLAIN SELECT * FROM func_index WHERE UPPER(c1) = 'ABC' \G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: func_index
   partitions: NULL
         type: ALL
possible_keys: NULL
          key: NULL
      key_len: NULL
          ref: NULL
         rows: 1
     filtered: 100.00
        Extra: Using where
--使用了索引
mysql> EXPLAIN SELECT * FROM func_index WHERE UPPER(c2) = 'ABC' \G 
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: func_index
   partitions: NULL
         type: ref
possible_keys: c2_index
          key: c2_index
      key_len: 43
          ref: const
         rows: 1
     filtered: 100.00
        Extra: NULL

/**6，MySQL 8.x版本支持对JSON类型的数据添加函数索引 */
mysql> CREATE TABLE func_json (
    -> data JSON,
    -> INDEX((CAST(data->>'$.name' AS CHAR(30))))
    -> );
·当JSON数据长度不固定时，如果直接对JSON数据进行索引，可能会超出索引长度，通常只截取JSON数据的一部分进行索引。
·CAST()类型转换函数把数据转化为CHAR(30)类型。使用方式为CAST(数据 AS 数据类型)。
·data ->> '$.name'表示JSON的运算符。
·可以将func_json数据表中的索引理解为获取JSON数据中name节点的值，并将其转化为CHAR(30)类型。
--查看索引
mysql> SHOW INDEX FROM func_json \G
*************************** 1. row ***************************
        Table: func_json
   Non_unique: 1
     Key_name: functional_index
 Seq_in_index: 1
  Column_name: NULL
    Collation: A
  Cardinality: 0
     Sub_part: NULL
       Packed: NULL
         Null: YES
   Index_type: BTREE
      Comment: 
Index_comment: 
      Visible: YES
   Expression: cast(json_unquote(json_extract(`data`,_utf8mb4\'$.name\')) as char(30) charset utf8mb4)
```

#### 9，全文本搜索
为了进行全文本搜索，必须索引被搜索的列，而且要随着数据的改变不断地重新索引。在对表列进行适当设计后，MySQL会自动进行所有的索引和重新索引。

在索引之后，SELECT可与Match()和Against()一起使用以实际执行搜索。

数据准备
```sql
/**1，创建测试表 */
create table full_text_search_test (
	note_id int not null primary key auto_increment,
	prod_id char(10) not null default '',
	note_date datetime not null default CURRENT_TIMESTAMP,
	note_text text,
	fulltext index (note_text)
);

/**2，插入测试数据 */
insert into full_text_search_test (note_text) values ('MySQL very good'),('MySQL not good'),('Oracle very good'),('Oracle not good');
insert into full_text_search_test (note_text) values ('i like MySQL'),('i like Oracle');
insert into full_text_search_test (note_text) values ('i do do do do do do like MySQL so so so so so'),('i do do do do do do like Oracle so so so so so');
insert into full_text_search_test (note_text) values 
('i 
just 
like 
MySQL'),
('i 
just 
like 
Oracle');
insert into full_text_search_test (note_text) values ('this is mongodb'),('this is redis');
```

全文本搜索测试
```sql
/**1，全文本搜索 match() against() 语法
查询 note_text 列中包含 'mysql' 的行 */
mysql> select note_id,note_date,note_text from full_text_search_test where match(note_text) against('mysql') \G
*************************** 1. row ***************************
  note_id: 1
note_date: 2022-10-10 11:40:13
note_text: MySQL very good
*************************** 2. row ***************************
  note_id: 2
note_date: 2022-10-10 11:40:13
note_text: MySQL not good
*************************** 3. row ***************************
  note_id: 5
note_date: 2022-10-10 11:42:52
note_text: i like MySQL
*************************** 4. row ***************************
  note_id: 7
note_date: 2022-10-10 11:45:08
note_text: i do do do do do do like MySQL so so so so so
*************************** 5. row ***************************
  note_id: 9
note_date: 2022-10-10 11:47:25
note_text: i
just
like
MySQL
/**注意：
1）传递给 Match()的值必须与 FULLTEXT()的定义列相同。如果指定多个列，则全文索引必须包含它们全部（而且次序正确）。
2）除非使用BINARY方式，否则全文本搜索不区分大小写。 
3）忽略词中的单引号。例如，don't索引为dont。
*/
/**
结果排序：
1）全文本搜索的一个重要部分就是对结果排序。具有较高等级的行先返回（因为这些行很可能是你真正想要的行）。
2）等级由MySQL根据行中词的数目、唯一词的数目、整个索引中词的总数以及包含该词的行的数目计算出来，文本中词靠前的行的等级值比词靠后的行的等级值高。
3）如果指定多个搜索项，则包含多数匹配词的那些行的等级值，将比包含较少词（或仅有一个匹配）的那些行的等级值更高。
 */



/**2，查询扩展【WITH QUERY EXPANSION】 
查询扩展用来设法放宽所返回的全文本搜索结果的范围。考虑下面的情况。你想找出所有提到anvils的注释。只有一个注释包含词anvils，但你还想找出可能与你的搜索有关的所有其他行，即使它们不包含词anvils。
*/
/**在使用查询扩展时，MySQL对数据和索引进行两遍扫描来完成搜索：
1）首先，进行一个基本的全文本搜索，找出与搜索条件匹配的所有行；
2）其次，MySQL检查这些匹配行并选择所有有用的词。
3）再其次，MySQL再次进行全文本搜索，这次不仅使用原来的条件，而且还使用所有有用的词。
 */
mysql> select note_id,note_date,note_text,match(note_text) against('mysql') as rank from full_text_search_test where match(note_text) against('mysql' with query expansion) \G
*************************** 1. row ***************************
  note_id: 1
note_date: 2022-10-10 11:40:13
note_text: MySQL very good
     rank: 0.14456059038639069
*************************** 2. row ***************************
  note_id: 2
note_date: 2022-10-10 11:40:13
note_text: MySQL not good
     rank: 0.14456059038639069
*************************** 3. row ***************************
  note_id: 9
note_date: 2022-10-10 11:47:25
note_text: i
just
like
MySQL
     rank: 0.14456059038639069
*************************** 4. row ***************************
  note_id: 3
note_date: 2022-10-10 11:40:13
note_text: Oracle very good
     rank: 0
*************************** 5. row ***************************
  note_id: 4
note_date: 2022-10-10 11:40:13
note_text: Oracle not good
     rank: 0
*************************** 6. row ***************************
  note_id: 10
note_date: 2022-10-10 11:47:25
note_text: i
just
like
Oracle
     rank: 0
*************************** 7. row ***************************
  note_id: 5
note_date: 2022-10-10 11:42:52
note_text: i like MySQL
     rank: 0.14456059038639069
*************************** 8. row ***************************
  note_id: 7
note_date: 2022-10-10 11:45:08
note_text: i do do do do do do like MySQL so so so so so
     rank: 0.14456059038639069
*************************** 9. row ***************************
  note_id: 6
note_date: 2022-10-10 11:42:52
note_text: i like Oracle
     rank: 0
*************************** 10. row ***************************
  note_id: 8
note_date: 2022-10-10 11:45:08
note_text: i do do do do do do like Oracle so so so so so
     rank: 0
/**使用查询扩展，查询结果，可能包括与搜索条件相匹配的行，同时，也会包含一些其他行，即与匹配行中的其他字段重复的行。
本示例中，查询结果中除了包含"mysql"的行，还包括其他行，这些行数据中要么存在 good，要么存在like。
查询扩展极大地增加了返回的行数，但这样做也增加了你实际上并不想要的行的数目。
 */



/**3，布尔文本搜索【IN BOOLEAN MODE】
以布尔方式搜索，支持查询：
1）要匹配的词；
2）要排斥的词（如果某行包含这个词，则不返回该行，即使它包含其他指定的词也是如此）；
3）排列提示（指定某些词比其他词更重要，更重要的词等级更高）；
4）表达式分组。
*/
/**全文本布尔操作符
布尔操作符        说明
  +           包含，词必须存在
  -           排除，词必须不存在
  >           包含，且增加等级值
  <           包含，且增加等级值
  ()          把词组成子表达式，允许这些子表达式作为一个组被包含，排除，排列等
  ~           取消一个词的排序值
  *           词尾通配符
  ""          定义一个短语，里面的短语是一个整体
 */
 
/**3.1，查询包含mysql，且不包含以good开头的数据*/
select note_id,note_date,note_text 
from full_text_search_test 
where match(note_text) against('mysql -good*' in boolean mode);

/**3.2，查询同时包含mysql和good的数据*/
select note_id,note_date,note_text 
from full_text_search_test 
where match(note_text) against('+mysql +good' in boolean mode);

/**3.3，查询包含mysql，或包含good的数据*/
select note_id,note_date,note_text 
from full_text_search_test 
where match(note_text) against('mysql good' in boolean mode);

/**3.4，查询包含mysql的数据，且如果也包含good，这些数据等级值更低（更靠后）*/
select note_id,note_date,note_text 
from full_text_search_test 
where match(note_text) against('mysql ~good' in boolean mode);

/**3.5，查询包含"mysql good"的数据*/
select note_id,note_date,note_text 
from full_text_search_test 
where match(note_text) against('"mysql good"' in boolean mode);

/**3.6，查询包含mysql，或包含good的数据。增加mysql词的等级，降低good词等级，即包含mysql的数据在前，包含good的数据在后*/
select note_id,note_date,note_text 
from full_text_search_test 
where match(note_text) against('>mysql <good' in boolean mode);

/**3.7，查询同时包含mysql和good，或者同时包含mysql和like。且同时包含mysql和good的数据的等级值更高*/
select note_id,note_date,note_text 
from full_text_search_test 
where match(note_text) against('+mysql +(>good <like)' in boolean mode);
```

### MySQL视图
视图本质上是一个虚拟表，它可以由数据库中的一张表或者多张表组合而成，视图从结构上也包含行和列。

#### 1，视图概述

##### 1.1，视图概念
视图可以由数据库中的一张表或者多张表生成，在结构上与数据表类似，但是视图本质上是一张虚拟表，视图中的数据也是由一张表或多张表中的数据组合而成。可以对视图中的数据进行增加、删除、修改、查看等操作，也可以对视图的结构进行修改。

在数据库中，视图不会保存数据，数据真正保存在数据表中。当对视图中的数据进行增加、删除和修改操作时，数据表中的数据会相应地发生变化；反之亦然。也就是说，不管是视图中的数据发生变化，还是数据表中的数据发生变化，另一方的数据也会相应地变化。

##### 1.2，视图的优点
1. 操作简单
将经常使用的查询操作定义为视图，可以使开发人员不需要关心视图对应的数据表的结构、表与表之间的关联关系，也不需要关心数据表之间的业务逻辑和查询条件，而只需要简单地操作视图即可，极大简化了开发人员对数据库的操作。

2. 数据安全
MySQL根据权限将用户对数据的访问限制在某些数据的结果集上，而这些数据的结果集可以使用视图来实现。因此，可以根据权限将用户对数据的访问限制在某些视图上，而不必直接查询或操作数据表，这在一定程度上保障了数据表中数据的安全性。

3. 数据独立
视图创建完成后，视图的结构就被确定了，当数据表的结构发生变化时不会影响视图的结构。当数据表的字段名称发生变化时，只需要简单地修改视图的查询语句即可，而不会影响用户对数据的查询操作。

4. 适应灵活多变的需求
当业务系统的需求发生变化后，如果需要改动数据表的结构，则工作量相对较大，可以使用视图来减少改动的工作量。这种方式在实际工作中使用得比较多。

5. 能够分解复杂的查询逻辑
数据库中如果存在复杂的查询逻辑，则可以将问题进行分解，创建多个视图获取数据，再将创建的多个视图结合起来，完成复杂的查询逻辑。

#### 2，创建视图
```sql
/**创建视图的语法 */
CREATE [OR REPLACE]
[ALGORITHM = {UNDEFINED | MERGE | TEMPTABLE}]
[DEFINER = user]
[SQL SECURITY { DEFINER | INVOKER }]
VIEW view_name [(column_list)]
AS select_statement
[WITH [CASCADED | LOCAL] CHECK OPTION]

--语法格式说明如下：
·CREATE：新建视图。
·REPLACE：替换已经存在的视图。
·ALGORITHM：标识视图使用的算法。
·{UNDEFINED | MERGE | TEMPTABLE}：视图使用的算法。其中，UNDEFINED表示MySQL会自动选择算法；MERGE表示将引用视图的语句与视图定义进行合并；TEMPTABLE表示将视图的结果放置到临时表中，接下来使用临时表执行相应的SQL语句。
·DEFINER：定义视图的用户。
·SQL SECURITY：安全级别。DEFINER表示只有创建视图的用户才能访问视图；INVOKER表示具有相应权限的用户能够访问视图。
·view_name：创建的视图名称。
·column_list：视图中包含的字段名称列表。
·select_statement：SELECT语句。
·[WITH [CASCADED | LOCAL] CHECK OPTION]：保证在视图的权限范围内更新视图。
/**创建视图示例 */
mysql> CREATE VIEW view_name_price 
  AS 
  SELECT t_name, t_price FROM t_goods;

mysql> CREATE VIEW view_category_goods(category, name, price)
  AS
  SELECT category.t_category, goods.t_name, goods.t_price
  FROM t_goods_category category, t_goods goods
  WHERE category.id = goods.t_category_id;
```

#### 3，查看视图
```sql
/**1，SHOW TABLES语句查看视图 */
mysql> show tables;
+-------------------------+
| Tables_in_test_new      |
+-------------------------+
| full_text_search_test   |
| table_test              |
| tbl_partition_innodb    |
| view_category_goods     |
| view_name_price         |
+-------------------------+

/**2，DESCRIBE/DESC语句查看视图 */
mysql> DESC view_category_goods;
+----------+---------------+------+-----+---------+-------+
| Field    | Type          | Null | Key | Default | Extra |
+----------+---------------+------+-----+---------+-------+
| category | varchar(30)   | YES  |     | NULL    |       |
| name     | varchar(50)   | YES  |     | NULL    |       |
| price    | decimal(10,2) | YES  |     | NULL    |       |
+----------+---------------+------+-----+---------+-------+

/**3，SHOW TABLE STATUS语句查看视图 */
mysql> SHOW TABLE STATUS like 'view_category_goods' \G
*************************** 1. row ***************************
           Name: view_category_goods
         Engine: NULL
        Version: NULL
     Row_format: NULL
           Rows: NULL
 Avg_row_length: NULL
    Data_length: NULL
Max_data_length: NULL
   Index_length: NULL
      Data_free: NULL
 Auto_increment: NULL
    Create_time: NULL
    Update_time: NULL
     Check_time: NULL
      Collation: NULL
       Checksum: NULL
 Create_options: NULL
        Comment: VIEW

/**4，SHOW CREATE VIEW语句查看视图 */
mysql> show create view view_name_price \G
*************************** 1. row ***************************
        View: view_name_price
  Create View: CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER 
  VIEW `view_name_price` AS select `t_goods`.`t_name` AS `t_name`,`t_goods`.`t_price` AS `t_price` from `t_goods`
character_set_client: utf8
collation_connection: utf8_general_ci

/**5，查看views数据表中的视图信息 
MySQL中会将视图的信息存储到 information_schema 数据库下的 views 数据表中，可以查看 views 数据表来查看视图的信息。
*/
mysql> SELECT * FROM information_schema.views where table_name like 'view%' \G
*************************** 1. row ***************************
       TABLE_CATALOG: def
        TABLE_SCHEMA: test_new
          TABLE_NAME: view_category_goods
     VIEW_DEFINITION: select `category`.`t_category` AS `category`,`goods`.`t_name` AS `name`,`goods`.`t_price` AS `price` from `test_new`.`t_goods_category` `category` join `test_new`.`t_goods` `goods` where (`category`.`id` = `goods`.`t_category_id`)
        CHECK_OPTION: NONE
        IS_UPDATABLE: YES
             DEFINER: root@localhost
       SECURITY_TYPE: DEFINER
CHARACTER_SET_CLIENT: utf8
COLLATION_CONNECTION: utf8_general_ci
*************************** 2. row ***************************
       TABLE_CATALOG: def
        TABLE_SCHEMA: test_new
          TABLE_NAME: view_name_price
     VIEW_DEFINITION: select `test_new`.`t_goods`.`t_name` AS `t_name`,`test_new`.`t_goods`.`t_price` AS `t_price` from `test_new`.`t_goods`
        CHECK_OPTION: NONE
        IS_UPDATABLE: YES
             DEFINER: root@localhost
       SECURITY_TYPE: DEFINER
CHARACTER_SET_CLIENT: utf8
COLLATION_CONNECTION: utf8_general_ci
```

#### 4，修改视图
MySQL中支持使用CREATE OR REPLACE VIEW语句和ALTER语句来修改视图的结构信息。
```sql
--使用CREATE OR REPLACE VIEW语句修改视图结构
CREATE [OR REPLACE]
    [ALGORITHM = {UNDEFINED | MERGE | TEMPTABLE}]
    [DEFINER = user]
    [SQL SECURITY { DEFINER | INVOKER }]
    VIEW view_name [(column_list)]
    AS select_statement
    [WITH [CASCADED | LOCAL] CHECK OPTION]

mysql> CREATE OR REPLACE VIEW view_category_goods(category, name, price)
  AS
  SELECT category.t_category, goods.t_name, goods.t_price
  FROM t_goods_category category, t_goods goods
  WHERE category.id = goods.t_category_id;

--使用ALTER语句修改视图结构
ALTER
    [ALGORITHM = {UNDEFINED | MERGE | TEMPTABLE}]
    [DEFINER = user]
    [SQL SECURITY { DEFINER | INVOKER }]
    VIEW view_name [(column_list)]
    AS select_statement
    [WITH [CASCADED | LOCAL] CHECK OPTION]

mysql> ALTER VIEW view_category_goods(category, name, price)
  AS
  SELECT category.t_category, goods.t_name, goods.t_price
  FROM t_goods_category category, t_goods goods
  WHERE category.id = goods.t_category_id;
```

#### 5，更新视图的数据 
MySQL支持使用INSERT、UPDATE和DELETE语句对视图中的数据进行插入、更新和删除操作。当视图中的数据发生变化时，数据表中的数据也会发生变化，反之亦然。
```sql
--创建测试视图
mysql> CREATE OR REPLACE VIEW view_category(id,t_category)
AS
SELECT id,t_category FROM t_goods_category;

mysql> select * from view_category;
+----+---------------------+
| id | t_category          |
+----+---------------------+
|  1 | 女装/女士精品       |
|  2 | 户外运动            |
+----+---------------------+
```

##### 5.1，直接更新视图数据
通过INSERT、UPDATE和DELETE语句对view_category视图中的数据进行了插入、更新和删除操作。

```sql
/**1，向视图插入数据 */
mysql> INSERT INTO view_category(id, t_category) VALUES (3, '水果');
--数据表中的数据，也随之发生变化
mysql> select id,t_category from t_goods_category;
+----+---------------------+
| id | t_category          |
+----+---------------------+
|  1 | 女装/女士精品       |
|  2 | 户外运动            |
|  3 | 水果                |
+----+---------------------+

/**2，更新视图中的数据 */
mysql> UPDATE view_category SET t_category = '图书' WHERE id = 3;
mysql> select id,t_category from t_goods_category;
+----+---------------------+
| id | t_category          |
+----+---------------------+
|  1 | 女装/女士精品       |
|  2 | 户外运动            |
|  3 | 图书                |
+----+---------------------+

/**3，删除视图中的数据 */
mysql> DELETE FROM view_category WHERE id = 3;
mysql> select id,t_category from t_goods_category;
+----+---------------------+
| id | t_category          |
+----+---------------------+
|  1 | 女装/女士精品       |
|  2 | 户外运动            |
+----+---------------------+
```

##### 5.2，间接更新视图数据
间接更新视图数据就是通过更新数据表的数据达到更新视图数据的目的。
```sql
/**1，向视图插入数据 */
mysql> INSERT INTO t_goods_category(id, t_category) VALUES (3, '电子设备');
--视图中的数据，也随之发生变化
mysql> select * from view_category;
+----+---------------------+
| id | t_category          |
+----+---------------------+
|  1 | 女装/女士精品       |
|  2 | 户外运动            |
|  3 | 电子设备            |
+----+---------------------+

/**2，更新视图中的数据 */
mysql> UPDATE t_goods_category SET t_category = '车辆配件' WHERE id = 3;
mysql> select * from view_category;
+----+---------------------+
| id | t_category          |
+----+---------------------+
|  1 | 女装/女士精品       |
|  2 | 户外运动            |
|  3 | 车辆配件            |
+----+---------------------+

/**3，删除视图中的数据 */
mysql> DELETE FROM t_goods_category WHERE id = 3;
mysql> select * from view_category;
+----+---------------------+
| id | t_category          |
+----+---------------------+
|  1 | 女装/女士精品       |
|  2 | 户外运动            |
+----+---------------------+
```

#### 6，删除视图
```sql
/**删除视图语句 */
DROP VIEW [IF EXISTS]
    view_name [, view_name] ...
    [RESTRICT | CASCADE]
--删除视图示例 
mysql> DROP VIEW view_category;
Query OK, 0 rows affected (0.00 sec)
mysql> DESC view_category;
ERROR 1146 (42S02): Table 'test_new.view_category' doesn't exist
```

### MySQL存储过程和函数
MySQL从5.0版本开始支持存储过程和函数。存储过程和函数能够将复杂的SQL逻辑封装在一起，应用程序无须关注存储过程和函数内部复杂的SQL逻辑，而只需要简单地调用存储过程和函数即可。

#### 1，存储过程和函数简介
在MySQL数据库中，存储程序可以分为存储过程和存储函数。存储过程和存储函数都是一系列SQL语句的集合，这些SQL语句被封装到一起组成一个存储过程或者存储函数保存到数据库中。

应用程序调用存储过程只需要通过CALL关键字并指定存储过程的名称和参数即可；同样，应用程序调用存储函数只需要通过SELECT关键字并指定存储函数的名称和参数即可。

存储过程和存储函数是有一定区别的，存储函数必须有返回值，而存储过程没有。

另外，存储过程的参数类型可以是IN、OUT和INOUT，而存储函数的参数类型只能是IN。

#### 2，创建存储过程和函数
```sql
/**1，创建存储过程的语法 */
CREATE PROCEDURE sp_name ([proc_parameter[,...]])
    [characteristic ...] routine_body
--语法格式说明：
·CREATE PROCEDURE：创建存储过程必须使用的关键字；
·sp_name：创建存储过程时指定的存储过程名称；
·proc_parameter：创建存储过程时指定的参数列表，参数列表可以省略；
·characteristic：创建存储过程时指定的对存储过程的约束；
·routine_body：存储过程的SQL执行体，使用 BEGIN…END 来封装存储过程需要执行的 SQL 语句。

--1.1，参数详细说明
--（1）proc_parameter：表示在创建存储过程时指定的参数列表。其列表形式如下：
[ IN | OUT | INOUT ] param_name type
--各项说明如下：
·IN：当前参数为输入参数，也就是表示入参；
·OUT：当前参数为输出参数，也就是表示出参；
·INOUT：当前参数即可以为输入参数，也可以为输出参数，也就是即可以表示入参，也可以表示出参；
·param_name：当前存储过程中参数的名称；
·type：当前存储过程中参数的类型，此类型可以是MySQL数据库中支持的任意数据类型。
--（2）characteristic：表示创建存储过程时指定的对存储过程的约束条件，其取值信息如下：
LANGUAGE SQL
  | [NOT] DETERMINISTIC
  | { CONTAINS SQL | NO SQL | READS SQL DATA | MODIFIES SQL DATA }
  | SQL SECURITY { DEFINER | INVOKER }
  | COMMENT 'string'
--各项说明如下：
·LANGUAGE SQL：存储过程的SQL执行体部分（存储过程语法格式中的routine_body部分）是由SQL语句组成的。
·[NOT] DETERMINISTIC：执行当前存储过程后，得出的结果数据是否确定。其中，DETERMINISTIC表示执行当前存储过程后得出的结果数据是确定的，即对于当前存储过程来说，每次输入相同的数据时，都会得到相同的输出结果。NOT DETERMINISTIC表示执行当前存储过程后，得出的结果数据是不确定的，即对于当前存储过程来说，每次输入相同的数据时，得出的输出结果可能不同。如果没有设置执行值，则MySQL默认为NOT DETERMINISTIC。
·{CONTAINS SQL | NO SQL | READS SQL DATA | MODIFIES SQL DATA}：存储过程中的子程序使用SQL语句的约束限制。其中，CONTAINS SQL表示当前存储过程的子程序包含SQL语句，但是并不包含读写数据的SQL语句；NO SQL表示当前存储过程的子程序中不包含任何SQL语句；READS SQL DATA表示当前存储过程的子程序中包含读数据的SQL语句；MODIFIES SQL DATA表示当前存储过程的子程序中包含写数据的SQL语句。如果没有设置相关的值，则MySQL默认指定值为CONTAINS SQL。
·SQL SECURITY {DEFINER | INVOKER}：执行当前存储过程的权限，即指明哪些用户能够执行当前存储过程。DEFINER表示只有当前存储过程的创建者或者定义者才能执行当前存储过程；INVOKER表示拥有当前存储过程的访问权限的用户能够执行当前存储过程。如果没有设置相关的值，则MySQL默认指定值为DEFINER。
·COMMENT 'string'：表示当前存储过程的注释信息，解释说明当前存储过程的含义。

/**注意：
在MySQL的存储过程中允许包含DDL的SQL语句，允许执行Commit（提交）操作，也允许执行Rollback（回滚）操作，但是不允许执行LOAD DATA INFILE语句。在当前存储过程中，可以调用其他存储过程或者函数。 */

--1.2，创建存储过程示例
mysql> delimiter //
mysql> CREATE PROCEDURE SelectAllData_goods()
    -> BEGIN
    -> SELECT * FROM t_goods;
    -> END
    -> //
Query OK, 0 rows affected (0.00 sec)
mysql> delimiter ;


/**2，创建存储函数的语法 */
CREATE FUNCTION func_name ([func_parameter[,...]])
    RETURNS type
    [characteristic ...] routine_body
--语法格式说明：
·CREATE FUNCTION：创建函数必须使用的关键字；
·func_name：创建函数时指定的函数名称；
·func_parameter：创建函数时指定的参数列表，参数列表可以省略；
·RETURNS type：创建函数时指定的返回数据类型；
·characteristic：创建函数时指定的对函数的约束；
·routine_body：函数的SQL执行体。
--2.1，参数详细说明
--（1）对于参数列表而言，存储过程的参数类型可以是IN、OUT和INOUT类型，而存储函数的参数类型只能是IN类型。
--（2）创建函数时对characteristic参数的说明与创建存储过程时对characteristic参数的说明相同，不再赘述。
--2.2，创建存储函数示例
mysql> delimiter $$
mysql> CREATE FUNCTION SelectNameById_goods()
    -> RETURNS varchar(50)
    -> RETURN (SELECT t_name FROM t_goods WHERE id = 11);
    -> $$
Query OK, 0 rows affected (0.00 sec)

mysql> delimiter ;
```

#### 3，查看存储过程和函数
MySQL数据库提供了3种方式来查看存储过程和函数，分别为：
* 使用SHOW CREATE语句查看存储过程和函数的创建信息；
* 使用SHOW STATUS语句查看存储过程和函数的状态信息；
* 从information_schema数据库中查看存储过程和函数的信息。
```sql
/**1，SHOW CREATE语句查看存储过程和函数的创建信息 */
SHOW CREATE {PROCEDURE | FUNCTION} sp_name
--示例 1
mysql> show create procedure SelectAllData_goods \G
*************************** 1. row ***************************
           Procedure: SelectAllData_goods
            sql_mode: ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION
    Create Procedure: CREATE DEFINER=`root`@`localhost` PROCEDURE `SelectAllData_goods`()
BEGIN
SELECT * FROM t_goods;
END
character_set_client: utf8
collation_connection: utf8_general_ci
  Database Collation: utf8_general_ci
--示例 2
mysql> show create function SelectNameById_goods \G
*************************** 1. row ***************************
            Function: SelectNameById_goods
            sql_mode: ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION
     Create Function: CREATE DEFINER=`root`@`localhost` FUNCTION `SelectNameById_goods`() RETURNS varchar(50) CHARSET utf8
RETURN (SELECT t_name FROM t_goods WHERE id = 11)
character_set_client: utf8
collation_connection: utf8_general_ci
  Database Collation: utf8_general_ci

/**2，SHOW STATUS语句查看存储过程和函数的状态信息 */
SHOW {PROCEDURE | FUNCTION} STATUS [LIKE 'pattern']
--示例 1
mysql> show procedure status like 'select%' \G
*************************** 1. row ***************************
                  Db: test_new
                Name: SelectAllData_goods
                Type: PROCEDURE
             Definer: root@localhost
            Modified: 2022-10-18 11:48:12
             Created: 2022-10-18 11:48:12
       Security_type: DEFINER
             Comment:
character_set_client: utf8
collation_connection: utf8_general_ci
  Database Collation: utf8_general_ci
--示例 2
mysql> show function status like 'select%' \G
*************************** 1. row ***************************
                  Db: test_new
                Name: SelectNameById_goods
                Type: FUNCTION
             Definer: root@localhost
            Modified: 2022-10-18 11:50:52
             Created: 2022-10-18 11:50:52
       Security_type: DEFINER
             Comment:
character_set_client: utf8
collation_connection: utf8_general_ci
  Database Collation: utf8_general_ci

/**3，从information_schema数据库中查看存储过程和函数的信息 */
SELECT * FROM information_schema.ROUTINES where ROUTINE_NAME = 'sp_name' [and ROUTINE_TYPE = 
{'PROCEDURE|FUNCTION'}];
SELECT * FROM information_schema.ROUTINES where ROUTINE_NAME LIKE 'sp_name' [and ROUTINE_TYPE = 
{'PROCEDURE|FUNCTION'}];
--示例 1
mysql> SELECT * FROM information_schema.ROUTINES where ROUTINE_NAME like 'Select%' and ROUTINE_TYPE = 'PROCEDURE' \G
*************************** 1. row ***************************
           SPECIFIC_NAME: SelectAllData_goods
         ROUTINE_CATALOG: def
          ROUTINE_SCHEMA: test_new
            ROUTINE_NAME: SelectAllData_goods
            ROUTINE_TYPE: PROCEDURE
               DATA_TYPE:
CHARACTER_MAXIMUM_LENGTH: NULL
  CHARACTER_OCTET_LENGTH: NULL
       NUMERIC_PRECISION: NULL
           NUMERIC_SCALE: NULL
      DATETIME_PRECISION: NULL
      CHARACTER_SET_NAME: NULL
          COLLATION_NAME: NULL
          DTD_IDENTIFIER: NULL
            ROUTINE_BODY: SQL
      ROUTINE_DEFINITION: BEGIN
SELECT * FROM t_goods;
END
           EXTERNAL_NAME: NULL
       EXTERNAL_LANGUAGE: NULL
         PARAMETER_STYLE: SQL
        IS_DETERMINISTIC: NO
         SQL_DATA_ACCESS: CONTAINS SQL
                SQL_PATH: NULL
           SECURITY_TYPE: DEFINER
                 CREATED: 2022-10-18 11:48:12
            LAST_ALTERED: 2022-10-18 11:48:12
                SQL_MODE: ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION
         ROUTINE_COMMENT:
                 DEFINER: root@localhost
    CHARACTER_SET_CLIENT: utf8
    COLLATION_CONNECTION: utf8_general_ci
      DATABASE_COLLATION: utf8_general_ci
--示例 2
mysql> SELECT * FROM information_schema.ROUTINES where ROUTINE_NAME like 'Select%' and ROUTINE_TYPE = 'FUNCTION' \G
*************************** 1. row ***************************
           SPECIFIC_NAME: SelectNameById_goods
         ROUTINE_CATALOG: def
          ROUTINE_SCHEMA: test_new
            ROUTINE_NAME: SelectNameById_goods
            ROUTINE_TYPE: FUNCTION
               DATA_TYPE: varchar
CHARACTER_MAXIMUM_LENGTH: 50
  CHARACTER_OCTET_LENGTH: 150
       NUMERIC_PRECISION: NULL
           NUMERIC_SCALE: NULL
      DATETIME_PRECISION: NULL
      CHARACTER_SET_NAME: utf8
          COLLATION_NAME: utf8_general_ci
          DTD_IDENTIFIER: varchar(50)
            ROUTINE_BODY: SQL
      ROUTINE_DEFINITION: RETURN (SELECT t_name FROM t_goods WHERE id = 11)
           EXTERNAL_NAME: NULL
       EXTERNAL_LANGUAGE: NULL
         PARAMETER_STYLE: SQL
        IS_DETERMINISTIC: NO
         SQL_DATA_ACCESS: CONTAINS SQL
                SQL_PATH: NULL
           SECURITY_TYPE: DEFINER
                 CREATED: 2022-10-18 11:50:52
            LAST_ALTERED: 2022-10-18 11:50:52
                SQL_MODE: ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION
         ROUTINE_COMMENT:
                 DEFINER: root@localhost
    CHARACTER_SET_CLIENT: utf8
    COLLATION_CONNECTION: utf8_general_ci
      DATABASE_COLLATION: utf8_general_ci
```

#### 4，修改存储过程和函数
创建存储过程和函数后，可以通过ALTER语句修改存储过程和函数的某些特性。
```sql
/**1，修改存储过程语法 */
ALTER PROCEDURE sp_name [characteristic ...]
--示例
mysql> ALTER PROCEDURE SelectAllData_goods
    LANGUAGE SQL
    NOT DETERMINISTIC
    READS SQL DATA
    SQL SECURITY INVOKER
    COMMENT 'Select All Data Create By Johann';
    

/**2，修改存储函数语法 */
ALTER FUNCTION func_name [characteristic ...]
--示例
mysql> ALTER FUNCTION SelectNameById_goods
    LANGUAGE SQL
    MODIFIES SQL DATA
    SQL SECURITY INVOKER
    COMMENT 'SelectNameById Create By Johann';

```

#### 5，调用存储过程和函数
调用存储过程和调用函数的方式稍有区别，调用存储过程使用的是CALL语句，而调用函数使用的是SELECT语句。
```sql
/**1，调用存储过程语句 */
CALL proc_name ([parameter[,…]])
--语法格式说明：
·CALL：调用存储过程的关键字；
·proc_name：调用存储过程的名称；
·parameter：存储过程定义的参数列表，当创建存储过程时没有定义参数列表，则参数列表为空。
--示例
mysql> call SelectAllData_goods();
+----+---------------+---------------------+--------------------+---------+---------+---------------------+
| id | t_category_id | t_category          | t_name             | t_price | t_stock | t_upper_time        |
+----+---------------+---------------------+--------------------+---------+---------+---------------------+
|  1 |             1 | 女装/女士精品       | T恤                |   39.90 |    1000 | 2020-11-10 00:00:00 |
|  2 |             1 | 女装/女士精品       | 连衣裙             |   79.90 |    2500 | 2020-11-10 00:00:00 |
|  3 |             1 | 女装/女士精品       | 卫衣               |   79.90 |    1500 | 2020-11-10 00:00:00 |
|  4 |             1 | 女装/女士精品       | 牛仔裤             |   89.90 |    3500 | 2020-11-10 00:00:00 |
|  5 |             1 | 女装/女士精品       | 百褶裙             |   29.90 |     500 | 2020-11-10 00:00:00 |
|  6 |             1 | 女装/女士精品       | 呢绒外套           |  399.90 |    1200 | 2020-11-10 00:00:00 |
|  7 |             2 | 户外运动            | 自行车             |  399.90 |    1000 | 2020-11-10 00:00:00 |
|  8 |             2 | 户外运动            | 山地自行车         | 1399.90 |    2500 | 2020-11-10 00:00:00 |
|  9 |             2 | 户外运动            | 登山杖             |   59.90 |    1500 | 2020-11-10 00:00:00 |
| 10 |             2 | 户外运动            | 骑行装备           |  399.90 |    3500 | 2020-11-10 00:00:00 |
| 11 |             2 | 户外运动            | 户外运动外套       |  799.90 |     500 | 2020-11-10 00:00:00 |
| 12 |             2 | 户外运动            | 滑板               |  499.90 |    1200 | 2020-11-10 00:00:00 |
| 13 |             2 | 户外运动            | NULL               |   39.90 |    1000 | 2022-09-27 15:49:30 |
| 14 |             2 | 户外运动            |                    |   79.90 |    2500 | 2022-09-27 15:49:30 |
+----+---------------+---------------------+--------------------+---------+---------+---------------------+

/**2，调用存储函数语句 */
SELECT func_name ([parameter[,…]])

--示例
mysql> select SelectNameById_goods();
+------------------------+
| SelectNameById_goods() |
+------------------------+
| 户外运动外套           |
+------------------------+
```

#### 6，删除存储过程和函数
删除存储过程和函数可以使用DROP语句。
```sql
/**删除存储过程、存储函数语句 */
DROP {PROCEDURE | FUNCTION} [IF EXISTS] proc_name|func_name
```

#### 7，MySQL中使用变量
在MySQL数据库的存储过程和函数中，可以使用变量来存储查询或计算的中间结果数据，或者输出最终的结果数据。

##### 7.1，如何定义变量及给变量赋值
在MySQL数据库中，可以使用 DECLARE 语句定义一个局部变量，变量的作用域为 BEGIN…END 语句块，变量也可以被用在嵌套的语句块中。变量的定义需要写在复合语句的开始位置，并且需要在任何其他语句的前面。定义变量时，可以一次声明多个相同类型的变量，也可以使用DEFAULT为变量赋予默认值。
```sql
/**1，定义变量语句 */
DECLARE var_name[,...] type [DEFAULT value]
--示例
DECLARE totalPrice,avgPrice DECIMAL(10,2) DEFAULT 0.00;
DECLARE totalCount DECIMAL(10,2) DEFAULT 0.00;

/**2，定义变量后，可以为变量进行赋值操作。变量可以直接赋值，也可以通过查询语句赋值。 */
/**2.1，直接赋值 */
SET var_name = expr [, var_name = expr] ...
--示例
SET totalPrice = 2999.99,avgPrice = (2999.99 / 12);
SET totalCount = 12;

/**2.2，查询语句赋值 */
SELECT col_name[,...] INTO var_name[,...] table_expr
--示例
SELECT SUM(t_price),COUNT(*) INTO totalprice,totalCount FROM t_goods;
SET avgPrice = (totalprice/totalCount);
```

##### 7.2，使用变量案例
```sql
/**1，存储过程中使用变量 */
mysql> delimiter //
mysql> create procedure SelectCountAndPrice()
    -> begin
    -> declare totalcount int default 0;
    -> declare totalprice,avgprice decimal(10,2) default 0.00;
    -> select sum(t_price) into totalprice from t_goods;
    -> select count(*) into totalcount from t_goods;
    -> set avgprice = (totalprice/totalcount);
    -> select totalprice,totalcount,avgprice;
    -> end //
Query OK, 0 rows affected (0.00 sec)
mysql> delimiter ;

/**存储函数中使用变量 */
mysql> delimiter //
mysql> create function SelectAvgStock()
    -> returns int
    -> begin
    -> declare totalcount,totalstock,avgstock int default 0;
    -> select count(*),sum(t_stock) into totalcount,totalstock from t_goods;
    -> set avgstock = (totalstock/totalcount);
    -> return avgstock;
    -> end //
Query OK, 0 rows affected (0.00 sec)
mysql> delimiter ;
```

#### 8，定义条件和处理程序
MySQL数据库支持定义条件和处理程序。

定义条件就是提前将程序执行过程中遇到的问题及对应的状态等信息定义出来，在程序执行过程中遇到问题时，可以返回提前定义好的条件信息。

处理程序能够定义在程序执行过程中遇到问题时应该采取何种处理方式来保证程序能够继续执行。

##### 8.1，如何定义条件和处理程序
```sql
/**1，定义条件语句 */
DECLARE condition_name CONDITION FOR condition_value
--语法格式说明：
·condition_name：定义的条件名称；
·condition_value：定义的条件类型。
--1.1，参数说明
--condition_value的取值如下：
SQLSTATE [VALUE] sqlstate_value | mysql_error_code
--参数说明：
·sqlstate_value：长度为5的字符串类型的错误信息；
·mysql_error_code：数值类型的错误代码。
--1.2，示例
DECLARE exec_ refused CONDITION FOR SQLSTATE '48000';  --使用sqlstate_value进行定义
DECLARE exec_refused CONDITION FOR 2199;  --使用mysql_error_code进行定义


/**2，定义处理程序语句 */
DECLARE handler_type HANDLER FOR condition_value[,...] sp_statement
--语法格式说明：
·handler_type：定义的错误处理方式；
·condition_value：定义的错误类型；
·sp_statement：当遇到定义的错误时，需要执行的存储过程或函数。
--2.1，参数详细说明
--1），handler_type参数的取值如下：
CONTINUE | EXIT | UNDO
--参数说明：
·CONTINUE：遇到错误时，不进行处理，继续向后执行；
·EXIT：遇到错误时，立刻退出程序；
·UNDO：遇到错误时，撤回之前的操作。
/** 注意：目前MySQL数据库还不支持UNDO操作。*/
--2），condition_value参数的取值如下：
SQLSTATE [VALUE] sqlstate_value
  | mysql_error_code
  | condition_name
  | SQLWARNING
  | NOT FOUND
  | SQLEXCEPTION
--参数说明：
·SQLSTATE [VALUE] sqlstate_value：长度为5的字符串类型的错误信息；
·mysql_error_code：数值类型的错误代码；
·condition_name：定义的条件名称；
·SQLWARNING：所有以01开头的SQLSTATE错误代码；
·NOT FOUND：所有以02开头的SQLSTATE错误代码；
·SQLEXCEPTION：所有没有被SQLWARNING或NOT FOUND捕获的SQLSTATE错误代码。
--2.2，示例
--1），定义处理程序捕获sqlstate_value值，当遇到sqlstate_value值为29011时，执行CONTINUE操作，并且输出DATABASE NOT FOUND信息。
DECLARE CONTINUE HANDLER FOR SQLSTATE '29011' SET @log=' DATABASE NOT FOUND';
--2），定义处理程序捕获mysql_error_code的值，当遇到mysql_error_code的值为1162时，执行CONTINUE操作
DECLARE CONTINUE HANDLER FOR 1162 SET @log=' SEARCH FAILED';
--3），先定义条件，捕获mysql_error_code的值，当遇到定义的条件名对应的异常时，执行CONTINUE操作
DECLARE search_failed CONDITION FOR 1162;
DECLARE CONTINUE HANDLER FOR search_failed SET @log=' SEARCH FAILED';
--4），使用 SQLWARNING 捕获所有以01开头的sqlstate_value错误代码，执行CONTINUE操作
DECLARE CONTINUE HANDLER FOR SQLWARNING SET @log=' SQLWARNING';
--5），使用 NOT FOUND 捕获所有以02开头的sqlstate_value错误代码，执行EXIT操作
DECLARE EXIT HANDLER FOR NOT FOUND SET @log=' SQL EXIT';
--6），使用SQLEXCEPTION捕获所有没有被 SQLWARNING 或 NOT FOUND 捕获的 sqlstate_value 错误代码，执行EXIT操作
DECLARE EXIT HANDLER FOR SQLEXCEPTION SET @log=' SQLEXCEPTION';
```
>注意：
>带有@符号的变量（比如@log）是用户变量，可以使用SET语句进行赋值，用户变量与MySQL的连接有关。在一个客户端的连接会话中定义的用户变量，只能在此连接会话中可见并使用，当此连接会话关闭时，该连接会话中创建的所有变量都会被自动释放。

##### 8.2，定义条件和处理程序案例

1. 在存储过程中定义条件和处理程序
```sql
mysql> DELIMITER $$
/**创建存储过程，并在存储过程中定义条件和处理程序*/
mysql> CREATE PROCEDURE InsertDataWithCondition()
    -> BEGIN
    -> DECLARE CONTINUE HANDLER FOR SQLSTATE '23000' SET @proc_value=1;
    -> SET @x = 1;
    -> INSERT INTO db_goods.t_goods (id, t_name, t_category, t_price, t_stock, t_upper_time) VALUES 
('1000011', '耐克运动鞋', '男鞋', '1399.90', '500', '2019-12-18 00:00:00');
    -> SET @x = 2;
    -> INSERT INTO db_goods.t_goods (id, t_name, t_category, t_price, t_stock, t_upper_time) VALUES 
('1000010', '登山杖', '登山设备', '159.90', '500', '2019-12-18 00:00:00');
    -> SET @x = 3;
    -> END $$
Query OK, 0 rows affected (0.13 sec)
mysql> DELIMITER ;

/**嗲用存储过程 */
mysql> CALL InsertDataWithCondition();
/**查询用户变量 */
mysql> SELECT @proc_value, @x;
+-------------+------+
| @proc_value | @x   |
+-------------+------+
|           1 |    3 |
+-------------+------+
```

2. 在函数中定义条件和处理程序
```sql
mysql> DELIMITER $$
/**创建函数，并在函数中定义条件和处理程序*/
mysql> CREATE FUNCTION InsertDataWithCondition()
    -> RETURNS INT
    -> BEGIN
    -> DECLARE CONTINUE HANDLER FOR SQLSTATE '23000' SET @func_value=1;
    -> SET @x = 1;
    -> INSERT INTO db_goods.t_goods (id, t_name, t_category, t_price, t_stock, t_upper_time) VALUES 
('1000011', '耐克运动鞋', '男鞋', '1399.90', '500', '2019-12-18 00:00:00');
    -> SET @x = 2;
    -> INSERT INTO db_goods.t_goods (id, t_name, t_category, t_price, t_stock, t_upper_time) VALUES 
('1000010', '登山杖', '登山设备', '159.90', '500', '2019-12-18 00:00:00');
    -> SET @x = 3;
    -> RETURN @x;
    -> END $$
Query OK, 0 rows affected (0.00 sec)
mysql> DELIMITER ;

/**调用函数，并查看用户变量 */
mysql> SELECT InsertDataWithCondition();
+---------------------------+
| InsertDataWithCondition() |
+---------------------------+
|                         3 |
+---------------------------+
```

#### 9，MySQL中游标的使用
如果在存储过程和函数中查询的数据量非常大，可以使用游标对结果集进行循环处理。

MySQL中游标的使用包括声明游标、打开游标、使用游标和关闭游标。

##### 9.1，游标的声明，打开，使用与关闭
```sql
/**1，声明游标 */
DECLARE cursor_name CURSOR FOR select_statement
--语法格式说明：
·cursor_name：声明的游标名称；
·select_statement：SELECT查询语句的内容，返回一个创建游标结果数据的集合。
--示例
DECLARE cursor_proc_func CURSOR FOR SELECT t_name, t_price, t_stock FROM t_goods

/**2，打开游标 */
OPEN cursor_name;
--示例
OPEN cursor_proc_func;

/**3，使用游标 */
FETCH cursor_name INTO var_name [, var_name] ...  --var_name必须在声明游标之前定义好
--示例
FETCH cursor_proc_func INTO name, price, stock;

/**4，关闭游标 */
CLOSE cursor_name
--示例
CLOSE cursor_proc_func;
```

>注意：
>游标必须在声明处理程序之前被声明，并且变量和条件必须在声明游标或处理程序之前被声明。即声明顺序依次是【变量，条件-->游标-->处理程序】
>游标只能用在存储过程和函数中。

##### 9.2，游标的使用案例

1. 在存储过程中使用游标
```sql
mysql> delimiter //
/**创建带有游标的存储过程 */
mysql> create procedure SelectTotalpriceUseCursor(OUT totalprice decimal(10,2))
    -> begin
    -> declare price decimal(10,2) default 0.00;
    -> declare cursor_price CURSOR for select t_price from t_goods;
    -> declare EXIT HANDLER for NOT FOUND close cursor_price;
    -> set totalprice = 0.00;
    -> open cursor_price;
    -> REPEAT
    -> fetch cursor_price into price;
    -> set totalprice = totalprice + price;
    -> until 0 end REPEAT;
    -> close cursor_price;
    -> end //

mysql> delimiter ;
/**调用存储过程 */
mysql> call SelectTotalpriceUseCursor(@x);
/**查询用户变量 */
mysql> select @x;
+---------+
| @x      |
+---------+
| 4398.60 |
+---------+
```
2. 在函数中使用游标
```sql
mysql> DELIMITER $$
/**创建带有游标的函数 */
mysql> CREATE FUNCTION StatisticsStock()
    -> RETURNS INT
    -> BEGIN
    -> DECLARE stock, totalstock INT DEFAULT 0;
    -> DECLARE cursor_stock CURSOR FOR SELECT t_stock FROM t_goods
    -> DECLARE CONTINUE HANDLER FOR NOT FOUND RETURN totalstock ;
    -> OPEN cursor_stock;
    -> REPEAT
    -> FETCH cursor_stock INTO stock;
    -> SET totalstock = totalstock + stock;
    -> UNTIL 0 END REPEAT;
    -> CLOSE cursor_stock;
    -> RETURN totalstock;
    -> END $$
Query OK, 0 rows affected (0.00 sec)
mysql> DELIMITER ;
/**调用函数 */
mysql> SELECT StatisticsStock();
+-------------------+
| StatisticsStock() |
+-------------------+
|              9130 |
+-------------------+
```

#### 10，MySQL中控制流程的使用













### MySQL触发器
MySQL从5.0.2版本开始支持触发器。MySQL中的触发器需要满足一定的条件才能执行，比如，在对某个数据表进行更新操作前首先需要验证数据的合法性，此时就可以使用触发器来执行。在MySQL中定义触发器能够在一定程度上保证数据的完整性。

#### 1，创建触发器
MySQL中创建触发器可以使用CREATE TRIGGER语句。MySQL中的触发器可以包含一条执行语句，也可以包含多条执行语句。
```sql
/**创建触发器语句 */
CREATE
    [DEFINER = user]
    TRIGGER trigger_name
    trigger_time trigger_event
    ON tbl_name FOR EACH ROW
    [trigger_order]
    trigger_body

--语法格式说明如下：
·trigger_name：创建的触发器的名称。
·trigger_time：标识什么时候执行触发器，支持两个选项，分别为BEFORE和AFTER。其中，BEFORE表示在某个事件之前触发，AFTER表示在某个事件之后触发。
·trigger_event：触发的事件，支持INSERT、UPDATE和DELETE操作。
·tbl_name：数据表名称，表示在哪张数据表上创建触发器。
·trigger_body：触发器中执行的SQL语句，可以有一条SQL语句，也可以是多条SQL语句。
```

##### 1.1，创建触发器示例
```sql
/**1，创建测试表 */
/**对test_trigger数据表进行增加、删除和修改操作，使用触发器将test_trigger数据表中的数据变化日志写入test_trigger_log数据表中。 */
mysql> CREATE TABLE test_trigger (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    t_note VARCHAR(30)
    );
mysql> CREATE TABLE test_trigger_log (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    t_log VARCHAR(30)
    );

/**2，在 test_trigger 表上创建一个 before insert 触发器*/
/**delimiter 是mysql分隔符，在mysql客户端中分隔符默认是分号（；）。如果一次输入的语句较多，并且语句中间有分号，这时需要新指定一个特殊的分隔符。 */
--重新定义分隔符
mysql> DELIMITER $$
--创建 before insert触发器
mysql> CREATE TRIGGER test_trigger_before_insert
    BEFORE INSERT 
    ON test_trigger
    FOR EACH ROW
    INSERT INTO test_trigger_log (t_log)
    VALUES ('before_insert');
    $$
--重新定义分隔符
mysql> DELIMITER ;
--插入数据
mysql> INSERT INTO test_trigger (t_note) VALUES ('测试 BEFORE INSERT 触发器');
--触发器生效
mysql> SELECT * FROM test_trigger_log;
+----+---------------+
| id | t_log         |
+----+---------------+
|  1 | before_insert |
+----+---------------+
--重新定义分隔符
mysql> DELIMITER $$
--创建 before insert触发器
mysql> CREATE TRIGGER test_trigger_after_insert
    AFTER INSERT 
    ON test_trigger
    FOR EACH ROW
    INSERT INTO test_trigger_log (t_log)
    VALUES ('after_insert');
    $$
--重新定义分隔符
mysql> DELIMITER ;
--插入数据
mysql> INSERT INTO test_trigger (t_note) VALUES ('测试 AFTER INSERT 触发器');
--触发器生效
mysql> SELECT * FROM test_trigger_log;
+----+---------------+
| id | t_log         |
+----+---------------+
|  1 | before_insert |
|  2 | before_insert |
|  3 | after_insert  |
+----+---------------+
```

#### 2，查看触发器
MySQL中支持使用SHOW TRIGGERS和SHOW CREATE TRIGGER语句查看触发器的信息。同时，在MySQL中会将触发器的信息存储在information_schema数据库中的triggers数据表中，所以也可以在trigger数据表中查看触发器的信息。
```sql
/**1，SHOW TRIGGERS语句查看触发器的信息 */
SHOW TRIGGERS [{FROM | IN} db_name] [LIKE 'pattern' | WHERE expr]
--示例
mysql> show triggers from test_new where `table` = 'test_trigger' AND `trigger` like '%before%' \G
*************************** 1. row ***************************
             Trigger: test_trigger_before_insert
               Event: INSERT
               Table: test_trigger
           Statement: INSERT INTO test_trigger_log(t_log)
VALUES ('before_insert')
              Timing: BEFORE
             Created: 2022-10-17 11:44:32.53
            sql_mode: ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION
             Definer: root@localhost
character_set_client: utf8
collation_connection: utf8_general_ci
  Database Collation: utf8_general_ci

/**2，SHOW CREATE TRIGGER语句查看触发器的信息 */
mysql> show create trigger test_trigger_after_insert \G
*************************** 1. row ***************************
               Trigger: test_trigger_after_insert
              sql_mode: ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION
SQL Original Statement: CREATE DEFINER=`root`@`localhost` TRIGGER test_trigger_after_insert
AFTER INSERT
ON test_trigger
FOR EACH ROW
INSERT INTO test_trigger_log(t_log)
VALUES ('after_insert')
  character_set_client: utf8
  collation_connection: utf8_general_ci
    Database Collation: utf8_general_ci
               Created: 2022-10-17 13:45:35.07

/**3，通过查看 information_schema.triggers 数据表中的数据查看触发器的信息 */
mysql> SELECT * FROM information_schema.triggers where trigger_name like 'test_trigger_before%' \G
*************************** 1. row ***************************
           TRIGGER_CATALOG: def
            TRIGGER_SCHEMA: test_new
              TRIGGER_NAME: test_trigger_before_insert
        EVENT_MANIPULATION: INSERT
      EVENT_OBJECT_CATALOG: def
       EVENT_OBJECT_SCHEMA: test_new
        EVENT_OBJECT_TABLE: test_trigger
              ACTION_ORDER: 1
          ACTION_CONDITION: NULL
          ACTION_STATEMENT: INSERT INTO test_trigger_log(t_log)
VALUES ('before_insert')
        ACTION_ORIENTATION: ROW
             ACTION_TIMING: BEFORE
ACTION_REFERENCE_OLD_TABLE: NULL
ACTION_REFERENCE_NEW_TABLE: NULL
  ACTION_REFERENCE_OLD_ROW: OLD
  ACTION_REFERENCE_NEW_ROW: NEW
                   CREATED: 2022-10-17 11:44:32.53
                  SQL_MODE: ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION
                   DEFINER: root@localhost
      CHARACTER_SET_CLIENT: utf8
      COLLATION_CONNECTION: utf8_general_ci
        DATABASE_COLLATION: utf8_general_ci
```

#### 3，删除触发器
```sql
/**删除触发器语句 */
DROP TRIGGER [IF EXISTS] [schema_name.]trigger_name
--示例
mysql> DROP TRIGGER IF EXISTS test_new.test_trigger_after_delete;
```

### MySQL分区
MySQL从5.1版本开始支持分区操作。对MySQL使用分区操作不仅能够存储更多的数据，而且在数据查询效率和数据吞吐量方面，也能够得到显著的提升。

#### 1，分区介绍
分区是指将一张表中的数据和索引分散存储到同一台计算机或不同计算机磁盘上的多个文件中。分区操作对于上层访问是透明的，用户访问MySQL中的分区表时，不必关心当前访问的数据存储到数据表的哪个分区中。对MySQL中的数据表进行分区也不会影响上层的业务逻辑。

```sql
/**MySQL 5.6以下版本，查看是否支持分区 */
mysql> show variables like '%partition%';
/**MySQL 5.6及5.6以上的版本中，查看是否支持分区 */
mysql> show plugins;
+----------------------------+----------+--------------------+---------+---------+
| Name                       | Status   | Type               | Library | License |
+----------------------------+----------+--------------------+---------+---------+
| binlog                     | ACTIVE   | STORAGE ENGINE     | NULL    | GPL     |
| mysql_native_password      | ACTIVE   | AUTHENTICATION     | NULL    | GPL     |
| sha256_password            | ACTIVE   | AUTHENTICATION     | NULL    | GPL     |
| CSV                        | ACTIVE   | STORAGE ENGINE     | NULL    | GPL     |
| MEMORY                     | ACTIVE   | STORAGE ENGINE     | NULL    | GPL     |
| InnoDB                     | ACTIVE   | STORAGE ENGINE     | NULL    | GPL     |
| INNODB_TRX                 | ACTIVE   | INFORMATION SCHEMA | NULL    | GPL     |
--省略......
| FEDERATED                  | DISABLED | STORAGE ENGINE     | NULL    | GPL     |
| partition                  | ACTIVE   | STORAGE ENGINE     | NULL    | GPL     |
| ngram                      | ACTIVE   | FTPARSER           | NULL    | GPL     |
+----------------------------+----------+--------------------+---------+---------+

/**版本分区说明：
1）在MySQL 5.7及以下的版本中，支持使用大部分存储引擎创建分区表，例如可以使用MyISAM、InnoDB和Memory等存储引擎创建分区表，其他诸如MERGE、CSV等存储引擎不支持创建分区表。
2）在MySQL 5.1版本中，同一张数据表的所有分区必须使用同一个存储引擎，即一张数据表中不能对一个分区使用一种存储引擎，而对另一个分区使用其他存储引擎。但是可以在不同的数据库中，对不同的数据表使用不同的存储引擎。
3）在MySQL 8.x版本中，MyISAM存储引擎已经不允许再创建分区表了，只能为实现了本地分区策略的存储引擎创建分区表，截至MySQL 8.0.18版本，只有InnoDB和NDB存储引擎支持创建分区表。
 */

/**创建分区语句 */
CREATE TABLE tbl_partition_innodb(
    id INT NOT NULL,
    name VARCHAR(30)
)ENGINE=InnoDB
PARTITION BY HASH(id)
PARTITIONS 5;
```

##### 1.1，分区优势
1. 存储更多的数据
MySQL中的数据表能够存储更多的数据。当没有使用分区时，同一个MySQL实例中的同一个数据表中的数据，只能存储到同一台计算机的同一磁盘的同一个数据文件中。使用分区后，同一个MySQL实例中的同一张数据表中的数据，能够存储到同一台计算机或不同计算机的不同磁盘上的不同的数据文件中，相比没有分区时，能够分散存储更多的数据。

2. 优化查询
分区后，在WHERE条件语句中包含分区条件时，能够只扫描符合条件的一个或多个分区来查询数据，而不必扫描整个数据表中的数据，从而提高了数据查询的效率。

3. 并行处理
当查询语句中涉及SUM()、COUNT()、AVG()、MAX()和MIN()等聚合函数时，可以在每个分区上进行并行处理，再统计汇总每个分区得出的结果，从而得出最终的汇总结果数据，整体上提高了数据查询与统计的效率。

4. 快速删除数据
如果数据表中的数据已经过期，或者不需要再存储到数据表中，可以通过删除分区的方式快速删除数据表中的数据。删除分区比删除数据表中的数据在效率上要高得多。

5. 更大的数据吞吐量
分区后，能够跨多个磁盘分散数据查询，每个查询之间可以并行进行，能够获得更大的查询吞吐量，提升数据查询的性能。

##### 1.2，分区类型
1. RANGE分区：根据一个连续的区间范围，将数据分散存储于不同的分区，支持对字段名或表达式进行分区。

2. LIST分区：根据给定的值列表，将数据分散存储到不同的分区，支持对字段名或表达式进行分区。

3. HASH分区：根据给定的分区个数，结合一定的HASH算法，将数据分散存储到不同的分区，可以使用用户自定义的函数。

4. KEY分区：与HASH分区类似，但是只能使用MySQL自带的HASH函数。

5. COLUMNS分区：为解决MySQL 5.5版本之前RANGE分区和LIST分区只支持整数分区而在MySQL 5.5版本新引入的分区类型。

6. 子分区：对数据表中的每个分区再次进行分区。

>补充：
>1）RANGE分区与LIST分区有一定的相似性，RANGE分区是基于一个连续的区间范围分区，而LIST分区是基于一个给定的值列表进行分区；HASH分区与KEY分区类似，HASH分区既可以使用MySQL本身提供的HASH函数进行分区，也可以使用用户自定义的表达式分区，而KEY分区只能使用MySQL本身提供的函数进行分区。
>2）在MySQL所有的分区类型中，进行分区的数据表可以不存在主键或者唯一键；如果存在主键或者唯一键，则不能使用主键或唯一键之外的其他字段进行分区操作。

#### 2，RANGE分区
RANGE分区是根据连续不间断的取值范围进行分区，并且每个分区中的取值范围不能重叠，可以使用VALUES LESS THAN语句定义分区区间。

##### 2.1，创建分区表
```sql
/**建表的同时进行RANGE分区操作 */
mysql> CREATE TABLE t_members (
      id INT NOT NULL,
      first_name VARCHAR(30),
      last_name VARCHAR(30),
      join_date DATE NOT NULL DEFAULT '2020-01-01',
      first_login_date DATE NOT NULL DEFAULT '2020-01-01',
      group_code INT NOT NULL,
      group_id INT NOT NULL
    )
    PARTITION BY RANGE (group_id)(
      PARTITION part0 VALUES LESS THAN (10),
      PARTITION part1 VALUES LESS THAN (20),
      PARTITION part2 VALUES LESS THAN (30),
      PARTITION part3 VALUES LESS THAN (40)
    );
/**此时将t_members数据表分为4个分区，group_id范围在1～9的成员信息保存在part0分区中，group_id范围在10~19的成员信息保存在part1分区中，以此类推。 */

/**查看分区表的数据分布 */
mysql> SELECT partition_name part,partition_method,partition_expression expr,partition_description part_desc,table_rows from information_schema.partitions where table_schema = schema() AND table_name = 't_members';
+-------+------------------+----------+-----------+------------+
| part  | partition_method | expr     | part_desc | table_rows |
+-------+------------------+----------+-----------+------------+
| part0 | RANGE            | group_id | 10        |          0 |
| part1 | RANGE            | group_id | 20        |          0 |
| part2 | RANGE            | group_id | 30        |          0 |
| part3 | RANGE            | group_id | 40        |          0 |
+-------+------------------+----------+-----------+------------+

/**向t_members数据表中插入一条group_id为15的数据 */
insert into t_members(id,first_name,last_name,join_date,first_login_date,group_code,group_id)
values (1,'johann','Zhao','2022-10-10','2022-10-10',10001,15);

/**再次查看分区表的数据分布，group_id为15的数据会被分配到part1分区中 */
mysql> SELECT partition_name part,partition_method,partition_expression expr,partition_description part_desc,table_rows from information_schema.partitions where table_schema = schema() AND table_name = 't_members';
+-------+------------------+----------+-----------+------------+
| part  | partition_method | expr     | part_desc | table_rows |
+-------+------------------+----------+-----------+------------+
| part0 | RANGE            | group_id | 10        |          0 |
| part1 | RANGE            | group_id | 20        |          1 |
| part2 | RANGE            | group_id | 30        |          0 |
| part3 | RANGE            | group_id | 40        |          0 |
+-------+------------------+----------+-----------+------------+
/**如果插入数据的值超过分区限制，此时插入失败。例如，向t_members表插入 group_id>39 的数据，此时插MySQL会报错【Table has no partition for value 40】，原因是MySQL无法确定将group_id的值大于39的数据存储到哪个分区。*/

/**MySQL中的RANGE分区只支持对整数类型的字段进行分区，如果分区的字段不是整数类型，则需要使用函数进行转换，将非整数类型的字段值转化为整数类型。 
MySQL中支持在VALUES LESS THAN语句中使用表达式，YEAR(join_date)*/
mysql> CREATE TABLE t_members_year (
    id INT NOT NULL,
    first_name VARCHAR(30),
    last_name VARCHAR(30),
    join_date DATE NOT NULL DEFAULT '2020-01-01', 
    first_login_date DATE NOT NULL DEFAULT '2020-01-01',
    group_code INT NOT NULL,
    group_id INT NOT NULL
    )
    PARTITION BY RANGE (YEAR(join_date))(
    PARTITION part0 VALUES LESS THAN(2010),
    PARTITION part1 VALUES LESS THAN(2020),
    PARTITION part2 VALUES LESS THAN(2030)
    );

/**分区字段包含在Where条件中，此时MySQL会快速确定需要扫描的分区，只需扫描确定的分区，而无需扫描整个表 */
mysql> explain select * from t_members where group_id >10 AND group_id < 25 \G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: t_members
   partitions: part1,part2
         type: ALL
possible_keys: NULL
          key: NULL
      key_len: NULL
          ref: NULL
         rows: 1
     filtered: 100.00
        Extra: Using where
```

##### 2.2，添加分区
MySQL中可以使用```ALTER TABLE ADD PARTITION```语句为数据表添加RANGE分区。
```sql
/**添加分区语句 */
--注意：为数据表添加RANGE分区时，只能从分区字段的最大端增加分区，否则会报错。
mysql> ALTER TABLE t_members ADD PARTITION (PARTITION part4 VALUES LESS THAN MAXVALUE);

mysql> show create table t_members \G
*************************** 1. row ***************************
       Table: t_members
Create Table: CREATE TABLE `t_members` (
  `id` int(11) NOT NULL,
  `first_name` varchar(30) DEFAULT NULL,
  `last_name` varchar(30) DEFAULT NULL,
  `join_date` date NOT NULL DEFAULT '2020-01-01',
  `first_login_date` date NOT NULL DEFAULT '2020-01-01',
  `group_code` int(11) NOT NULL,
  `group_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50100 PARTITION BY RANGE (group_id)
(PARTITION part0 VALUES LESS THAN (10) ENGINE = InnoDB,
 PARTITION part1 VALUES LESS THAN (20) ENGINE = InnoDB,
 PARTITION part2 VALUES LESS THAN (30) ENGINE = InnoDB,
 PARTITION part3 VALUES LESS THAN (40) ENGINE = InnoDB,
 PARTITION part4 VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */

 /**查看当前的分区数据 */
 --添加完新的分区，原有的分区 table_rows 被重新计数
mysql> SELECT partition_name part,partition_method,partition_expression expr,partition_description part_desc,table_rows from information_schema.partitions where table_schema = schema() AND table_name = 't_members';
+-------+------------------+----------+-----------+------------+
| part  | partition_method | expr     | part_desc | table_rows |
+-------+------------------+----------+-----------+------------+
| part0 | RANGE            | group_id | 10        |          0 |
| part1 | RANGE            | group_id | 20        |          0 |
| part2 | RANGE            | group_id | 30        |          0 |
| part3 | RANGE            | group_id | 40        |          0 |
| part4 | RANGE            | group_id | MAXVALUE  |          0 |
+-------+------------------+----------+-----------+------------+
```

##### 2.3，删除分区
MySQL中支持使用```ALTER TABLE DROP PARTITION```语句删除RANGE分区，删除分区时，也会将当前分区中的数据一同删除。
```sql
/**查询待删除的分区数据 */
mysql> select * from t_members where group_id = 15;
+----+------------+-----------+------------+------------------+------------+----------+
| id | first_name | last_name | join_date  | first_login_date | group_code | group_id |
+----+------------+-----------+------------+------------------+------------+----------+
|  1 | johann     | Zhao      | 2022-10-10 | 2022-10-10       |      10001 |       15 |
+----+------------+-----------+------------+------------------+------------+----------+

mysql> explain select * from t_members where group_id = 15 \G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: t_members
   partitions: part1
         type: ALL
possible_keys: NULL
          key: NULL
      key_len: NULL
          ref: NULL
         rows: 1
     filtered: 100.00
        Extra: Using where

/**删除分区语句 */     
mysql> ALTER TABLE t_members DROP PARTITION part1;
/**分区被删除 */
mysql> SELECT partition_name part,partition_method,partition_expression expr,partition_description part_desc,table_rows from information_schema.partitions where table_schema = schema() AND table_name = 't_members';
+-------+------------------+----------+-----------+------------+
| part  | partition_method | expr     | part_desc | table_rows |
+-------+------------------+----------+-----------+------------+
| part0 | RANGE            | group_id | 10        |          0 |
| part2 | RANGE            | group_id | 30        |          0 |
| part3 | RANGE            | group_id | 40        |          0 |
| part4 | RANGE            | group_id | MAXVALUE  |          0 |
+-------+------------------+----------+-----------+------------+
/**分区数据被删除 */
mysql> select * from t_members;
Empty set (0.00 sec) 

/**插入新的数据 */
mysql> insert into t_members(id,first_name,last_name,join_date,first_login_date,group_code,group_id) values (2,'johann','Zhao','2022-10-10','2022-10-10',10001,18);
/**原本应插入到part1分区的数据，被插入到part2分区 */
mysql> explain select * from t_members where group_id = 18 \G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: t_members
   partitions: part2
         type: ALL
possible_keys: NULL
          key: NULL
      key_len: NULL
          ref: NULL
         rows: 1
     filtered: 100.00
        Extra: Using where
```

##### 2.4，重定义分区
MySQL支持在不丢失数据的情况下，使用```ALTER TABLE REORGANIZE PARTITION INTO```语句重定义数据表的分区，可以将数据表中的一个分区拆分为多个分区，也可以将多个分区合并为一个分区。
```sql
/**查看当前表分区情况 */
mysql> show create table t_members \G
*************************** 1. row ***************************
       Table: t_members
Create Table: CREATE TABLE `t_members` (
  `id` int(11) NOT NULL,
  `first_name` varchar(30) DEFAULT NULL,
  `last_name` varchar(30) DEFAULT NULL,
  `join_date` date NOT NULL DEFAULT '2020-01-01',
  `first_login_date` date NOT NULL DEFAULT '2020-01-01',
  `group_code` int(11) NOT NULL,
  `group_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50100 PARTITION BY RANGE (group_id)
(PARTITION part0 VALUES LESS THAN (10) ENGINE = InnoDB,
 PARTITION part2 VALUES LESS THAN (30) ENGINE = InnoDB,
 PARTITION part3 VALUES LESS THAN (40) ENGINE = InnoDB,
 PARTITION part4 VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */

/**重定义分区之分区拆分：将 t_members 表的 part2(10-29) 分区拆分为 part1(10-19),part2(20-29) */
mysql> ALTER TABLE t_members REORGANIZE PARTITION part2 INTO (
    PARTITION part1 VALUES LESS THAN (20),
    PARTITION part2 VALUES LESS THAN (30)
);

/**查看重定义分区之分区拆分后，表分区情况 */
mysql> show create table t_members \G
*************************** 1. row ***************************
       Table: t_members
Create Table: CREATE TABLE `t_members` (
  `id` int(11) NOT NULL,
  `first_name` varchar(30) DEFAULT NULL,
  `last_name` varchar(30) DEFAULT NULL,
  `join_date` date NOT NULL DEFAULT '2020-01-01',
  `first_login_date` date NOT NULL DEFAULT '2020-01-01',
  `group_code` int(11) NOT NULL,
  `group_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50100 PARTITION BY RANGE (group_id)
(PARTITION part0 VALUES LESS THAN (10) ENGINE = InnoDB,
 PARTITION part1 VALUES LESS THAN (20) ENGINE = InnoDB,
 PARTITION part2 VALUES LESS THAN (30) ENGINE = InnoDB,
 PARTITION part3 VALUES LESS THAN (40) ENGINE = InnoDB,
 PARTITION part4 VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */

/**重定义分区之分区合并：将 part0(10),part1(20) 合并为 part0(20) */
mysql> ALTER TABLE t_members REORGANIZE PARTITION part0, part1 INTO (
    PARTITION part0 VALUES LESS THAN (20)
);

/**查看重定义分区之分区合并后，表分区情况 */
mysql> show create table t_members \G
*************************** 1. row ***************************
       Table: t_members
Create Table: CREATE TABLE `t_members` (
  `id` int(11) NOT NULL,
  `first_name` varchar(30) DEFAULT NULL,
  `last_name` varchar(30) DEFAULT NULL,
  `join_date` date NOT NULL DEFAULT '2020-01-01',
  `first_login_date` date NOT NULL DEFAULT '2020-01-01',
  `group_code` int(11) NOT NULL,
  `group_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50100 PARTITION BY RANGE (group_id)
(PARTITION part0 VALUES LESS THAN (20) ENGINE = InnoDB,
 PARTITION part2 VALUES LESS THAN (30) ENGINE = InnoDB,
 PARTITION part3 VALUES LESS THAN (40) ENGINE = InnoDB,
 PARTITION part4 VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */
```
>补充：
>1）使用ALTER TABLE REORGANIZE PARTITION INTO语句重定义分区，不会删除数据。
>2）重定义RANGE分区时，只能重定义范围相邻的分区，重定义后的分区需要与原分区的区间相同。
>3）同时，MySQL不支持使用重定义分区修改表分区的类型。

#### 3，LIST分区
LIST分区可以使用PARTITION BY LIST语句实现，然后通过VALUES IN (list)语句来定义分区，其中，在MySQL 5.5之前的版本中，list是一个逗号分隔的整数列表，不必按照某种顺序进行排列。在MySQL 5.5版本之后，支持对非整数类型进行LIST分区。

##### 3.1，创建分区表
```sql
/**创建表的同时进行 LIST分区操作 */
--LIST分区不支持使用MAXVALUE等方式定义其他值
mysql> CREATE TABLE t_members_list (
      id INT NOT NULL,
      t_name VARCHAR(30) NOT NULL,
      group_id INT NOT NULL
    ) 
    PARTITION BY LIST (group_id)(
      PARTITION part0 VALUES IN (1, 3, 5),
      PARTITION part1 VALUES IN (2, 6),
      PARTITION part2 VALUES IN (4, 7, 9),
      PARTITION part3 VALUES IN (8, 10)
    );

/**查看表分区情况 */
mysql> show create table t_members_list \G
*************************** 1. row ***************************
       Table: t_members_list
Create Table: CREATE TABLE `t_members_list` (
  `id` int(11) NOT NULL,
  `t_name` varchar(30) NOT NULL,
  `group_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50100 PARTITION BY LIST (group_id)
(PARTITION part0 VALUES IN (1,3,5) ENGINE = InnoDB,
 PARTITION part1 VALUES IN (2,6) ENGINE = InnoDB,
 PARTITION part2 VALUES IN (4,7,9) ENGINE = InnoDB,
 PARTITION part3 VALUES IN (8,10) ENGINE = InnoDB) */

/**查看分区表的数据分布 */
mysql> SELECT partition_name part,partition_method,partition_expression expr,partition_description part_desc,table_rows from information_schema.partitions where table_schema = schema() AND table_name = 't_members_list';
+-------+------------------+----------+-----------+------------+
| part  | partition_method | expr     | part_desc | table_rows |
+-------+------------------+----------+-----------+------------+
| part0 | LIST             | group_id | 1,3,5     |          0 |
| part1 | LIST             | group_id | 2,6       |          0 |
| part2 | LIST             | group_id | 4,7,9     |          0 |
| part3 | LIST             | group_id | 8,10      |          0 |
+-------+------------------+----------+-----------+------------+
```

##### 3.2，添加分区表
```sql
/**添加List分区 */
--添加List分区时，应注意，对于分区列表中的特定值，必须存在并且只能存在于一个分区中。
mysql> ALTER TABLE t_members_list ADD PARTITION(
  PARTITION part4 VALUES IN (11, 12)
);
/**查看表分区情况 */
mysql> show create table t_members_list \G
*************************** 1. row ***************************
       Table: t_members_list
Create Table: CREATE TABLE `t_members_list` (
  `id` int(11) NOT NULL,
  `t_name` varchar(30) NOT NULL,
  `group_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50100 PARTITION BY LIST (group_id)
(PARTITION part0 VALUES IN (1,3,5) ENGINE = InnoDB,
 PARTITION part1 VALUES IN (2,6) ENGINE = InnoDB,
 PARTITION part2 VALUES IN (4,7,9) ENGINE = InnoDB,
 PARTITION part3 VALUES IN (8,10) ENGINE = InnoDB,
 PARTITION part4 VALUES IN (11,12) ENGINE = InnoDB) */
```

##### 3.3，删除分区表
```sql
/**删除List 分区 */
mysql> ALTER TABLE t_members_list DROP PARTITION part4;
/**查看表分区情况 */
mysql> show create table t_members_list \G
*************************** 1. row ***************************
       Table: t_members_list
Create Table: CREATE TABLE `t_members_list` (
  `id` int(11) NOT NULL,
  `t_name` varchar(30) NOT NULL,
  `group_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50100 PARTITION BY LIST (group_id)
(PARTITION part0 VALUES IN (1,3,5) ENGINE = InnoDB,
 PARTITION part1 VALUES IN (2,6) ENGINE = InnoDB,
 PARTITION part2 VALUES IN (4,7,9) ENGINE = InnoDB,
 PARTITION part3 VALUES IN (8,10) ENGINE = InnoDB) */
```

##### 3.4，重定义分区表
MySQL中同样支持使用ALTER TABLE REORGANIZE PARTITION INTO语句重定义数据表的LIST分区。只不过重定义LIST分区的过程与重定义RANGE分区的过程不太相同。
```sql
/**重定义某个分区 */
mysql> ALTER TABLE t_members_list REORGANIZE PARTITION part1 INTO(
    PARTITION part1 VALUES IN (2, 6, 12)
);
/**查看表分区情况 */
mysql> show create table t_members_list \G
*************************** 1. row ***************************
       Table: t_members_list
Create Table: CREATE TABLE `t_members_list` (
  `id` int(11) NOT NULL,
  `t_name` varchar(30) NOT NULL,
  `group_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50100 PARTITION BY LIST (group_id)
(PARTITION part0 VALUES IN (1,3,5) ENGINE = InnoDB,
 PARTITION part1 VALUES IN (2,6,12) ENGINE = InnoDB,
 PARTITION part2 VALUES IN (4,7,9) ENGINE = InnoDB,
 PARTITION part3 VALUES IN (8,10) ENGINE = InnoDB) */

/**合并LIST分区时，只支持重定义相邻的分区，重定义后的分区区间必须与原分区区间范围相同 */
mysql> ALTER TABLE t_members_list REORGANIZE PARTITION part1,part3 INTO(
    PARTITION part1 VALUES IN (2, 6, 8, 10, 12)
);

/**重定义分区之合并相邻分区 */
mysql> ALTER TABLE t_members_list REORGANIZE PARTITION part0,part1 INTO(
    PARTITION part0 VALUES IN (1,3,5,2,6,12)
);
/**查看表分区情况 */
mysql> show create table t_members_list \G
*************************** 1. row ***************************
       Table: t_members_list
Create Table: CREATE TABLE `t_members_list` (
  `id` int(11) NOT NULL,
  `t_name` varchar(30) NOT NULL,
  `group_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50100 PARTITION BY LIST (group_id)
(PARTITION part0 VALUES IN (1,3,5,2,6,12) ENGINE = InnoDB,
 PARTITION part2 VALUES IN (4,7,9) ENGINE = InnoDB,
 PARTITION part3 VALUES IN (8,10) ENGINE = InnoDB) */

 /**重定义分区之合并拆分分区 */
 mysql> ALTER TABLE t_members_list REORGANIZE PARTITION part0 INTO(
    PARTITION part0 VALUES IN (1,3,5),
    PARTITION part1 VALUES IN (2,6,12)
);
/**查看表分区情况 */
mysql> show create table t_members_list \G
*************************** 1. row ***************************
       Table: t_members_list
Create Table: CREATE TABLE `t_members_list` (
  `id` int(11) NOT NULL,
  `t_name` varchar(30) NOT NULL,
  `group_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50100 PARTITION BY LIST (group_id)
(PARTITION part0 VALUES IN (1,3,5) ENGINE = InnoDB,
 PARTITION part1 VALUES IN (2,6,12) ENGINE = InnoDB,
 PARTITION part2 VALUES IN (4,7,9) ENGINE = InnoDB,
 PARTITION part3 VALUES IN (8,10) ENGINE = InnoDB) */
```

#### 4，COLUMNS分区
COLUMNS分区是MySQL 5.5版本引入的新的分区类型，能够解决MySQL之前的版本中RANGE分区和LIST分区只支持整数分区的问题。
COLUMNS分区可以分为RANGE COLUMNS分区和LIST COLUMNS分区。

* RANGE COLUMNS分区和LIST COLUMNS分区都支持整数类型、日期时间类型和字符串类型。

##### 4.1，RANGE COLUMNS分区
RANGE COLUMNS不仅增加了支持的数据类型，而且还能够对数据表中的多个字段进行分区。
```sql
/**创建表的同时进行 RANGE COLUMNS分区操作 */
mysql> CREATE TABLE t_members_range_columns(
    id INT NOT NULL,
    t_name VARCHAR(30) NOT NULL DEFAULT '',
    group_id INT NOT NULL,
    group_code INT NOT NULL
  )
  PARTITION BY RANGE COLUMNS (group_id, group_code) (
    PARTITION part0 VALUES LESS THAN (1, 10),
    PARTITION part1 VALUES LESS THAN (10, 20),
    PARTITION part2 VALUES LESS THAN (10, 30),
    PARTITION part3 VALUES LESS THAN (10, MAXVALUE),
    PARTITION part4 VALUES LESS THAN (MAXVALUE, MAXVALUE)
  );
/**向RANGE COLUMNS分区表中插入数据时，会按照字段组进行比较，如果插入的数据与字段组中的第一个字段值相同，则按照第二个字段值进行比较，直到确定数据插入哪个分区为止。 */

/**查看表分区情况 */
mysql> show create table t_members_range_columns \G
*************************** 1. row ***************************
       Table: t_members_range_columns
Create Table: CREATE TABLE `t_members_range_columns` (
  `id` int(11) NOT NULL,
  `t_name` varchar(30) NOT NULL DEFAULT '',
  `group_id` int(11) NOT NULL,
  `group_code` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50500 PARTITION BY RANGE  COLUMNS(group_id,group_code)
(PARTITION part0 VALUES LESS THAN (1,10) ENGINE = InnoDB,
 PARTITION part1 VALUES LESS THAN (10,20) ENGINE = InnoDB,
 PARTITION part2 VALUES LESS THAN (10,30) ENGINE = InnoDB,
 PARTITION part3 VALUES LESS THAN (10,MAXVALUE) ENGINE = InnoDB,
 PARTITION part4 VALUES LESS THAN (MAXVALUE,MAXVALUE) ENGINE = InnoDB) */

/**查看分区表的数据分布 */
mysql> SELECT partition_name part,partition_method,partition_expression expr,partition_description part_desc,table_rows from information_schema.partitions where table_schema = schema() AND table_name = 't_members_range_columns';
+-------+------------------+-------------------------+-------------------+------------+
| part  | partition_method | expr                    | part_desc         | table_rows |
+-------+------------------+-------------------------+-------------------+------------+
| part0 | RANGE COLUMNS    | `group_id`,`group_code` | 1,10              |          0 |
| part1 | RANGE COLUMNS    | `group_id`,`group_code` | 10,20             |          0 |
| part2 | RANGE COLUMNS    | `group_id`,`group_code` | 10,30             |          0 |
| part3 | RANGE COLUMNS    | `group_id`,`group_code` | 10,MAXVALUE       |          0 |
| part4 | RANGE COLUMNS    | `group_id`,`group_code` | MAXVALUE,MAXVALUE |          0 |
+-------+------------------+-------------------------+-------------------+------------+
```
RANGE COLUMNS分区的添加、删除和重定义与RANGE分区差别不大。

##### 4.2，LIST COLUMNS分区
LIST COLUMNS分区不仅具有LIST分区的特性，同样也可以支持对多个列进行分区。
```sql
/**创建表的同时进行 LIST COLUMNS分区操作 */
mysql> CREATE TABLE t_member_list_columns(
    id INT NOT NULL,
    t_name VARCHAR(30) NOT NULL DEFAULT '',
    group_id INT NOT NULL,
    group_code INT NOT NULL
  )
  PARTITION BY LIST COLUMNS (group_id, group_code)(
    PARTITION part0 VALUES IN ((1,1), (1, 2), (1,3)),
    PARTITION part1 VALUES IN ((1,4), (2,1)),
    PARTITION part2 VALUES IN ((3,3), (3,5))
  );

/**查看表分区情况 */
mysql> show create table t_member_list_columns \G
*************************** 1. row ***************************
       Table: t_member_list_columns
Create Table: CREATE TABLE `t_member_list_columns` (
  `id` int(11) NOT NULL,
  `t_name` varchar(30) NOT NULL DEFAULT '',
  `group_id` int(11) NOT NULL,
  `group_code` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50500 PARTITION BY LIST  COLUMNS(group_id,group_code)
(PARTITION part0 VALUES IN ((1,1),(1,2),(1,3)) ENGINE = InnoDB,
 PARTITION part1 VALUES IN ((1,4),(2,1)) ENGINE = InnoDB,
 PARTITION part2 VALUES IN ((3,3),(3,5)) ENGINE = InnoDB) */

/**查看分区表的数据分布 */
mysql> SELECT partition_name part,partition_method,partition_expression expr,partition_description part_desc,table_rows from information_schema.partitions where table_schema = schema() AND table_name = 't_member_list_columns';
+-------+------------------+-------------------------+-------------------+------------+
| part  | partition_method | expr                    | part_desc         | table_rows |
+-------+------------------+-------------------------+-------------------+------------+
| part0 | LIST COLUMNS     | `group_id`,`group_code` | (1,1),(1,2),(1,3) |          0 |
| part1 | LIST COLUMNS     | `group_id`,`group_code` | (1,4),(2,1)       |          0 |
| part2 | LIST COLUMNS     | `group_id`,`group_code` | (3,3),(3,5)       |          0 |
+-------+------------------+-------------------------+-------------------+------------+
```
List COLUMNS分区的添加、删除和重定义与List分区差别不大。

#### 5，HASH分区
HASH能够分散数据库中的热点数据，能够在一定程度上保证分区中的数据尽可能平均分布。HASH分区可以分为常规HASH分区和线性HASH分区。

##### 5.1，创建分区表
可以使用PARTITION BY HASH语句创建HASH分区表
```sql
/**常规HASH分区 */
mysql> CREATE TABLE t_members_hash(
    id INT NOT NULL,
    t_name VARCHAR(30) NOT NULL DEFAULT '',
    group_id INT NOT NULL
  )
  PARTITION BY HASH (group_id) 
  PARTITIONS 4;

/**查看表分区情况 */
mysql> show create table t_members_hash \G
*************************** 1. row ***************************
       Table: t_members_hash
Create Table: CREATE TABLE `t_members_hash` (
  `id` int(11) NOT NULL,
  `t_name` varchar(30) NOT NULL DEFAULT '',
  `group_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50100 PARTITION BY HASH (group_id)
PARTITIONS 4 */

/**查看分区表的数据分布 */
mysql> SELECT partition_name part,partition_method,partition_expression expr,partition_description part_desc,table_rows from information_schema.partitions where table_schema = schema() AND table_name = 't_members_hash';
+------+------------------+----------+-----------+------------+
| part | partition_method | expr     | part_desc | table_rows |
+------+------------------+----------+-----------+------------+
| p0   | HASH             | group_id | NULL      |          0 |
| p1   | HASH             | group_id | NULL      |          0 |
| p2   | HASH             | group_id | NULL      |          0 |
| p3   | HASH             | group_id | NULL      |          0 |
+------+------------------+----------+-----------+------------+


/**线性HASH分区。创建线性HASH分区表比创建常规HASH分区表多一个关键字 LINEAR【/ˈlɪniər/】*/
mysql> CREATE TABLE t_members_hash_linear(
    id INT NOT NULL,
    t_name VARCHAR(30) NOT NULL DEFAULT '',
    group_code INT NOT NULL
  )
  PARTITION BY LINEAR HASH (group_code)
  PARTITIONS 4;

/**查看表分区情况 */
mysql> show create table t_members_hash_linear \G
*************************** 1. row ***************************
       Table: t_members_hash_linear
Create Table: CREATE TABLE `t_members_hash_linear` (
  `id` int(11) NOT NULL,
  `t_name` varchar(30) NOT NULL DEFAULT '',
  `group_code` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50100 PARTITION BY LINEAR HASH (group_code)
PARTITIONS 4 */

/**查看分区表的数据分布 */
mysql> SELECT partition_name part,partition_method,partition_expression expr,partition_description part_desc,table_rows from information_schema.partitions where table_schema = schema() AND table_name = 't_members_hash_linear';
+------+------------------+------------+-----------+------------+
| part | partition_method | expr       | part_desc | table_rows |
+------+------------------+------------+-----------+------------+
| p0   | LINEAR HASH      | group_code | NULL      |          0 |
| p1   | LINEAR HASH      | group_code | NULL      |          0 |
| p2   | LINEAR HASH      | group_code | NULL      |          0 |
| p3   | LINEAR HASH      | group_code | NULL      |          0 |
+------+------------------+------------+-----------+------------+
```
>补充：
>1）常规HASH分区，在插入数据时会对分区列的值进行求模运算，从而得出数据被插入哪个分区中。基本算法如下：P = value % num
>2）在使用HASH分区时，当数据表中的数据发生变更时，每次都需要使用HASH算法计算一次，所以不推荐使用复杂的HASH算法，也不推荐对数据表中的多个字段进行HASH分区，否则会引起性能问题。

##### 5.2，添加分区
MySQL支持使用```ALTER TABLE ADD PARTITION```语句为数据表增加HASH分区。
```sql
/**添加 常规Hash分区 */
mysql> ALTER TABLE t_members_hash ADD PARTITION PARTITIONS 5;
/**查看分区表的数据分布 */
mysql> mysql> SELECT partition_name part,partition_method,partition_expression expr,partition_description part_desc,table_rows from information_schema.partitions where table_schema = schema() AND table_name = 't_members_hash';
+------+------------------+----------+-----------+------------+
| part | partition_method | expr     | part_desc | table_rows |
+------+------------------+----------+-----------+------------+
| p0   | HASH             | group_id | NULL      |          0 |
| p1   | HASH             | group_id | NULL      |          0 |
| p2   | HASH             | group_id | NULL      |          0 |
| p3   | HASH             | group_id | NULL      |          0 |
| p4   | HASH             | group_id | NULL      |          0 |
| p5   | HASH             | group_id | NULL      |          0 |
| p6   | HASH             | group_id | NULL      |          0 |
| p7   | HASH             | group_id | NULL      |          0 |
| p8   | HASH             | group_id | NULL      |          0 |
+------+------------------+----------+-----------+------------+

/**添加 线性Hash分区 */
mysql> ALTER TABLE t_members_hash_linear ADD PARTITION PARTITIONS 5;
/**查看分区表的数据分布 */
mysql> SELECT partition_name part,partition_method,partition_expression expr,partition_description part_desc,table_rows from information_schema.partitions where table_schema = schema() AND table_name = 't_members_hash_linear';
+------+------------------+------------+-----------+------------+
| part | partition_method | expr       | part_desc | table_rows |
+------+------------------+------------+-----------+------------+
| p0   | LINEAR HASH      | group_code | NULL      |          0 |
| p1   | LINEAR HASH      | group_code | NULL      |          0 |
| p2   | LINEAR HASH      | group_code | NULL      |          0 |
| p3   | LINEAR HASH      | group_code | NULL      |          0 |
| p4   | LINEAR HASH      | group_code | NULL      |          0 |
| p5   | LINEAR HASH      | group_code | NULL      |          0 |
| p6   | LINEAR HASH      | group_code | NULL      |          0 |
| p7   | LINEAR HASH      | group_code | NULL      |          0 |
| p8   | LINEAR HASH      | group_code | NULL      |          0 |
+------+------------------+------------+-----------+------------+
```

##### 5.3，合并分区
MySQL中不支持使用```ALTER TABLE DROP PARTITION```语句删除HASH分区，但是可以通过```ALTER TABLE COALESCE PARTITION```【/ˌkoʊəˈles/】语句对HASH分区进行合并。
```sql
/**合并分区，参数P 为需要减少的分区，即合并后的分区数目为 N-P */
mysql> ALTER TABLE t_members_hash COALESCE PARTITION 4;

/**查看分区表的数据分布 */
mysql> SELECT partition_name part,partition_method,partition_expression expr,partition_description part_desc,table_rows from information_schema.partitions where table_schema = schema() AND table_name = 't_members_hash';
+------+------------------+----------+-----------+------------+
| part | partition_method | expr     | part_desc | table_rows |
+------+------------------+----------+-----------+------------+
| p0   | HASH             | group_id | NULL      |          0 |
| p1   | HASH             | group_id | NULL      |          0 |
| p2   | HASH             | group_id | NULL      |          0 |
| p3   | HASH             | group_id | NULL      |          0 |
| p4   | HASH             | group_id | NULL      |          0 |
+------+------------------+----------+-----------+------------+
```
>补充：
>1）使用ALTER TABLE COALESCE PARTITION语句合并HASH分区时，PARTITION关键字后面的数字是要减少的分区数目，而不是减少到的分区数。
>2）不能使用ALTER TABLE COALESCE PARTITION语句来增加HASH分区的个数，否则MySQL会报错。

#### 6，KEY分区
MySQL中的KEY分区在某种程度上与HASH分区类似，只不过HASH分区可以使用用户自定义的函数和表达式，而KEY分区不能；

另外，HASH分区只支持对整数类型的列进行分区，而KEY分区能够支持对除BLOB和TEXT数据类型以外的其他数据类型的列进行分区。

##### 6.1，创建分区表
创建数据表时可以使用```PARTITION BY KEY```语句指定KEY分区。

与HASH分区不同的是：
1. 当数据表中存在主键时，可以不指定分区键，MySQL默认使用主键作为KEY分区的分区键。
2. 如果数据表中没有主键，则MySQL会自动选择非空并且唯一的列进行KEY分区。
3. 在既没有指定主键，又没有指定非空唯一键时，则必须为KEY分区指定分区键。
```sql
/**1，可以不指定分区键，MySQL默认使用主键作为KEY分区的分区键。 */
mysql> CREATE TABLE t_members_key_primary(
    id INT NOT NULL PRIMARY KEY,
    t_name VARCHAR(30),
    group_id INT NOT NULL
  ) 
  PARTITION BY KEY() 
  PARTITIONS 8;

/**2，如果数据表中没有主键，则MySQL会自动选择非空并且唯一的列进行KEY分区。 */
mysql> CREATE TABLE t_members_key_unique (
    id INT NOT NULL,
    t_name VARCHAR(30),
    group_id INT NOT NULL,
    UNIQUE KEY (group_id)
  ) 
  PARTITION BY KEY()
  PARTITIONS 6;


/**3，既没有指定主键，又没有指定非空唯一键时，则必须为KEY分区指定分区键。 */
mysql> CREATE TABLE t_members_key_unique_normal (
    id INT NOT NULL,
    t_name VARCHAR(30),
    group_id INT
  ) 
  PARTITION BY KEY(group_id) 
  PARTITIONS 4;
```
MySQL中对KEY分区进行添加和合并的操作与HASH分区相同。

#### 7，子分区
子分区表示可以对数据表中的RANGE分区和LIST分区再次进行子分区，形成复合分区，其中，子分区可以使用HASH分区，也可以使用KEY分区。

```sql
/**对RNAGE分区，再次使用HASH分区来进行子分区 */
mysql> CREATE TABLE t_member_partitions (
    id INT NOT NULL,
    t_name VARCHAR(30),
    group_id INT
  )
  PARTITION BY RANGE (group_id)
  SUBPARTITION BY HASH(group_id)
  SUBPARTITIONS 4
  (
    PARTITION part0 VALUES LESS THAN(10),
    PARTITION part1 VALUES LESS THAN(MAXVALUE)
  );
/**t_member_partitions数据表中存在2个RANGE分区，分别为part0分区和part1分区，每个RANGE分区又被进一步分成4个HASH子分区。
所以，t_member_partitions数据表中总共存在8个分区。

在向t_member_partitions数据表中插入数据时，group_id列小于10的数据将会被插入part0分区的HASH子分区中，group_id大于或者等于10的数据将会被插入part1分区的HASH子分区中。
*/
```

#### 8，分区中的NULL值处理
1. 在RANGE分区中，NULL值会被当作最小值进行处理。
2. 向LIST分区中写入NULL值时，LIST分区的值列表中必须包含NULL值才能被成功写入，否则MySQL会报错。
3. HASH分区与KEY分区中处理NULL值的方式相同，就是将NULL值当作0进行处理。

### MySQL公用表表达式和生成列

#### 1，公用表表达式
从MySQL 8.x版本开始支持公用表表达式（Common table expression，简称为CTE）。公用表表达式通过WITH语句实现，可以分为非递归公用表表达式和递归公用表表达式。

在常规的子查询中，派生表无法被引用两次，否则会引起MySQL的性能问题。如果使用CTE查询的话，子查询只会被引用一次，这也是使用CTE的一个重要原因。

##### 1.1，非递归CTE
```sql
/**cte语句，单字段*/
with cte_year as 
(select year(now()) as years)
select * from cte_year;
---
| years |
| ----- |
| 2022  |
---

/**cte语句，多个字段 */
with cte_year_month(years,months) as 
(select year(now()) as years,month(now()) as months)
select * from cte_year_month;
---
| years | months |
| ----- | ------ |
| 2022  | 10     |
---

/**cte语句，多个CTE之间相互引用 */
with cte_year_month(years,months) as 
(select year(now()) as years,month(now()) as months),
cte2(next_year,next_month) as 
(select years+1 as next_year,months+1 as next_month from cte_year_month)
select * from cte_year_month,cte2;
---
| years | months | next_year | next_month |
| ----- | ------ | --------- | ---------- |
| 2022  | 10     | 2023      | 11         |
---
```

##### 1.2，递归CTE
递归CTE的子查询可以引用自身，递归CTE的语法格式比非递归CTE的语法格式多一个关键字RECURSIVE。

在递归CTE中，子查询包含两种：一种是种子查询（种子查询会初始化查询数据，并在查询中不会引用自身），一种是递归查询（递归查询是在种子查询的基础上，根据一定的规则引用自身的查询）。这两个查询之间会通过UNION、UNION ALL或者UNION DISTINCT语句连接起来。

```sql
/**使用递归CTE在MySQL命令行中输出1～10的序列。 */
mysql> WITH RECURSIVE cte_num(num) AS
  (
  SELECT 1
  UNION ALL
  SELECT num + 1 FROM cte_num WHERE num < 10 
  )
  SELECT * FROM cte_num;
+------+
| num  |
+------+
|    1 |
|    2 |
|    3 |
|    4 |
|    5 |
|    6 |
|    7 |
|    8 |
|    9 |
|   10 |
+------+

/**CTE 进行树形查询 */
create table t1(id int, value char(10), parent_id int);
insert into t1 values(1, 'A', NULL);
insert into t1 values(2, 'B', 1);
insert into t1 values(3, 'C', 1);
insert into t1 values(4, 'D', 1);
insert into t1 values(5, 'E', 2);
insert into t1 values(6, 'F', 2);
insert into t1 values(7, 'G', 4);
insert into t1 values(8, 'H', 6);

--层序遍历
with recursive cte as (
  select id, value, 0 as level from t1 where parent_id is null
  union all
  select t1.id, t1.value, cte.level+1 from cte join t1 on t1.parent_id=cte.id
)
select * from cte;
+------+-------+-------+
| id   | value | level |
+------+-------+-------+
|    1 | A     |     0 |
|    2 | B     |     1 |
|    3 | C     |     1 |
|    4 | D     |     1 |
|    5 | E     |     2 |
|    6 | F     |     2 |
|    7 | G     |     2 |
|    8 | H     |     3 |
+------+-------+-------+

--深度优先遍历
with recursive cte as (
  select id, value, 0 as level, CAST(id AS CHAR(200)) AS path  from t1 where parent_id is null
  union all
  select t1.id, t1.value, cte.level+1, CONCAT(cte.path, ",", t1.id)  from cte join t1 on t1.parent_id=cte.id
)
select * from cte order by path;
+------+-------+-------+---------+
| id   | value | level | path    |
+------+-------+-------+---------+
|    1 | A     |     0 | 1       |
|    2 | B     |     1 | 1,2     |
|    5 | E     |     2 | 1,2,5   |
|    6 | F     |     2 | 1,2,6   |
|    8 | H     |     3 | 1,2,6,8 |
|    3 | C     |     1 | 1,3     |
|    4 | D     |     1 | 1,4     |
|    7 | G     |     2 | 1,4,7   |
+------+-------+-------+---------+
```

##### 1.3，递归CTE的限制
递归CTE的查询语句中需要包含一个终止递归查询的条件。当由于某种原因在递归CTE的查询语句中未设置终止条件时，MySQL会根据相应的配置信息，自动终止查询并抛出相应的错误信息。在MySQL中默认提供了如下两个配置项来终止递归CTE。

* cte_max_recursion_depth：如果在定义递归CTE时没有设置递归终止条件，当达到cte_max_recursion_depth参数设置的执行次数后【默认值为1000】，MySQL会报错。
* max_execution_time：表示SQL语句执行的最长毫秒时间，当SQL语句的执行时间超过此参数设置的值时，MySQL报错。

#### 2，生成列
MySQL中生成列的值是根据数据表中定义列时指定的表达式计算得出的，主要包含两种类型：Virtual生成列和Stored生成列，其中Virtual生成列是从数据表中查询记录时，计算该列的值；Stored生成列是向数据表中写入记录时，计算该列的值并将计算的结果数据作为常规列存储在数据表中。

通常，使用的比较多的是Virtual生成列，原因是Virtual生成列不占用存储空间。

```sql
/**1，创建表时指定生成列 */
mysql> CREATE TABLE t_genearted_column(
    a DOUBLE,
    b DOUBLE,
    c DOUBLE AS (a * a + b * b)
  );
mysql> CREATE TABLE t_column_virsual (
    a DOUBLE,
    b DOUBLE,
    c DOUBLE GENERATED ALWAYS AS (a + b) VIRTUAL
  );

/**2，为已有表添加生成列 */
mysql> CREATE TABLE t_add_column(
    a DOUBLE,
    b DOUBLE
  );
mysql> ALTER TABLE t_add_column ADD COLUMN c DOUBLE GENERATED ALWAYS AS(a * a + b * b) STORED;

/**3，修改已有的生成列 */
mysql> ALTER TABLE t_add_column MODIFY COLUMN c DOUBLE GENERATED ALWAYS AS (a * b) STORED;

/**4，删除生成列 */
mysql> ALTER TABLE t_add_column DROP COLUMN c;
```