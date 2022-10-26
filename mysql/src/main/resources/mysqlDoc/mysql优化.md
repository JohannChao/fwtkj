### Explain语句分析

Explain语句提供了有关 MySQL 如何执行语句的信息。Explain适用于“SELECT”、“DELETE”、“INSERT”、“REPLACE”和“UPDATE”语句。

Explain语句的输出结果如下：
[5.7官方参考手册](https://dev.mysql.com/doc/refman/5.7/en/explain-output.html#explain-output-interpretation)

| 列名          | JSON名称      | 备注                     |
| ------------- | ------------- | ------------------------ |
| id            | select_id     | SELECT标识符             |
| select_type   | None          | SELECT类型               |
| table         | table_name    | 输出结果的表名称         |
| partitions    | partitions    | 对应的分区               |
| type          | access_type   | 关联类型                 |
| possible_keys | possible_keys | 可能选择的索引           |
| key           | key           | 实际选择的索引           |
| key_len       | key_length    | 选择的索引长度           |
| ref           | ref           | 与索引比较的列           |
| rows          | rows          | 要检查的行的预估值       |
| filtered      | filtered      | 按表条件筛选的行的百分比 |
| extra         | None          | 附加信息                 |

#### 1，id
表示SELECT语句的序列号，在EXPLAIN分析的结果信息中，有多少个SELECT语句就有多少个序列号。如果当前行的结果数据中引用了其他行的结果数据，则该值为NULL。

#### 2，select_type
表示当前SQL语句的查询类型，可以表示当前SQL语句是简单查询语句还是复杂查询语句。

select_type常见的取值如下：

1.  SIMPLE：当前SQL语句是简单查询，不包含任何连接查询和子查询。
2.  PRIMARY：主查询或者包含子查询时最外层的查询语句。
3.  UNION：当前SQL语句是连接查询时，表示连接查询的第二个SELECT语句或者第二个后面的SELECT语句。
4.  DEPENDENT UNION：含义与UNION几乎相同，但是DEPENDENT UNION取决于外层的查询语句。
5.  UNION RESULT：表示连接查询的结果信息。
6.  SUBQUERY：表示子查询中的第一个查询语句。
7.  DEPENDENT SUBQUERY：含义与SUBQUERY几乎相同，但是DEPENDENT SUBQUERY取决于外层的查询语句。
8.  DERIVED：表示FROM子句中的子查询。
9.  MATERIALIZED：表示实例化子查询。
10.  UNCACHEABLE SUBQUERY：表示不缓存子查询的结果数据，重新计算外部查询的每一行数据。
11.  UNCACHEABLE UNION：表示不缓存连接查询的结果数据，每次执行连接查询时都会重新计算数据结果。

#### 3，table
当前查询（连接查询、子查询）所在的数据表。

#### 4，partitions
如果当前数据表是分区表，则表示查询结果匹配的分区。

#### 5，type
当前SQL语句所使用的关联类型或者访问类型，其取值从最优到最差依次为```system > const > eq_ref > ref > fulltext > ref_or_null > index_merge > unique_subquery > index_subquery > range > index > ALL```。

```sql
/**测试数据 */
CREATE TABLE `t_goods_category` (
  `id` int(11) NOT NULL,
  `t_category` varchar(30) DEFAULT NULL,
  `t_remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
INSERT INTO t_goods_category (id,t_category,t_remark) VALUES
	 (1,'女装/女士精品','女装'),
	 (2,'户外运动','户外');

CREATE TABLE `t_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `t_category_id` int(11) DEFAULT NULL,
  `t_category` varchar(30) DEFAULT NULL,
  `t_name` varchar(50) DEFAULT NULL,
  `t_price` decimal(10,2) DEFAULT NULL,
  `t_stock` int(11) DEFAULT NULL,
  `t_upper_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `foreign_category` (`t_category_id`),
  CONSTRAINT `t_goods_ibfk_1` FOREIGN KEY (`t_category_id`) REFERENCES `t_goods_category` (`id`)
);
INSERT INTO t_goods (t_category_id,t_category,t_name,t_price,t_stock,t_upper_time) VALUES
	 (1,'女装/女士精品','T恤',39.90,1000,'2020-11-10 00:00:00'),
	 (1,'女装/女士精品','连衣裙',79.90,2500,'2020-11-10 00:00:00'),
	 (1,'女装/女士精品','卫衣',79.90,1500,'2020-11-10 00:00:00'),
	 (1,'女装/女士精品','牛仔裤',89.90,3500,'2020-11-10 00:00:00'),
	 (1,'女装/女士精品','百褶裙',29.90,500,'2020-11-10 00:00:00'),
	 (1,'女装/女士精品','呢绒外套',399.90,1200,'2020-11-10 00:00:00'),
	 (2,'户外运动','自行车',399.90,1000,'2020-11-10 00:00:00'),
	 (2,'户外运动','山地自行车',1399.90,2500,'2020-11-10 00:00:00'),
	 (2,'户外运动','登山杖',59.90,1500,'2020-11-10 00:00:00'),
	 (2,'户外运动','骑行装备',399.90,3500,'2020-11-10 00:00:00'),
	 (2,'户外运动','户外运动外套',799.90,500,'2020-11-10 00:00:00'),
	 (2,'户外运动','滑板',499.90,1200,'2020-11-10 00:00:00'),
	 (2,'户外运动','轮滑',39.90,1000,'2022-09-27 15:49:30'),
	 (2,'户外运动','毽子',79.90,2500,'2022-09-27 15:49:30');

CREATE TABLE `t_explain_test1` (
  `id` int(11) NOT NULL,
  `t_name` varchar(30) NOT NULL DEFAULT '',
  `t_create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `name_index` (`t_name`)
);
INSERT INTO t_explain_test1
(id, t_name, t_create_time)
VALUES(1, '1001', CURRENT_TIMESTAMP()),(2, '1002', CURRENT_TIMESTAMP()),(3, '1003', CURRENT_TIMESTAMP()),
(4, '1004', CURRENT_TIMESTAMP()),(5, '1005', CURRENT_TIMESTAMP()),(6, '1006', CURRENT_TIMESTAMP()),
(7, '1007', CURRENT_TIMESTAMP()),(8, '1008', CURRENT_TIMESTAMP()),(9, '1009', CURRENT_TIMESTAMP()),
(10, '1010', CURRENT_TIMESTAMP()),(11, '1011', CURRENT_TIMESTAMP()),(12, '1012', CURRENT_TIMESTAMP()),
(13, '1013', CURRENT_TIMESTAMP()),(14, '1014', CURRENT_TIMESTAMP()),(15, '1015', CURRENT_TIMESTAMP()),
(16, '1016', CURRENT_TIMESTAMP()),(17, '1017', CURRENT_TIMESTAMP()),(18, '1018', CURRENT_TIMESTAMP()),
(19, '1019', CURRENT_TIMESTAMP()),(20, '1020', CURRENT_TIMESTAMP()),(21, '1021', CURRENT_TIMESTAMP());

CREATE TABLE `t_explain_test2` (
  `id` int(11) NOT NULL,
  `t_card_id` varchar(30) NOT NULL,
  `t_create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `card_id_index` (`t_card_id`)
);
INSERT INTO t_explain_test2
(id, t_card_id, t_create_time)
VALUES(1, '1001', CURRENT_TIMESTAMP()),(2, '1002', CURRENT_TIMESTAMP()),(3, '1003', CURRENT_TIMESTAMP()),
(4, '1004', CURRENT_TIMESTAMP()),(5, '1005', CURRENT_TIMESTAMP()),(6, '1006', CURRENT_TIMESTAMP()),
(7, '1007', CURRENT_TIMESTAMP()),(8, '1008', CURRENT_TIMESTAMP()),(9, '1009', CURRENT_TIMESTAMP()),
(10, '1010', CURRENT_TIMESTAMP()),(11, '1011', CURRENT_TIMESTAMP()),(12, '1012', CURRENT_TIMESTAMP()),
(13, '1013', CURRENT_TIMESTAMP()),(14, '1014', CURRENT_TIMESTAMP()),(15, '1015', CURRENT_TIMESTAMP()),
(16, '1016', CURRENT_TIMESTAMP()),(17, '1017', CURRENT_TIMESTAMP()),(18, '1018', CURRENT_TIMESTAMP()),
(19, '1019', CURRENT_TIMESTAMP()),(20, '1020', CURRENT_TIMESTAMP()),(21, '1021', CURRENT_TIMESTAMP()); 
```

##### 5.1，system
查询的数据表中只有一行数据，是const类型的特例。

##### 5.2，const
数据表中最多只有一行数据符合查询条件，当查询或连接的字段为主键或唯一索引时，则type的取值为const。
```sql
mysql> explain select * from t_explain_test1 where id = 1 \G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: t_explain_test1
   partitions: NULL
         type: const
possible_keys: PRIMARY
          key: PRIMARY
      key_len: 4
          ref: const
         rows: 1
     filtered: 100.00
        Extra: NULL
```

##### 5.3，eq_ref
如果查询语句中的连接条件或查询条件使用了主键或者非空唯一索引包含的全部字段，则type的取值为eq_ref，典型的场景为使用“=”操作符比较带索引的列。
```sql
--使用 select *  导致 t_goods 表索引失效
mysql> explain select g.* from t_goods g left join t_goods_category c on g.t_category_id = c.id \G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: g
   partitions: NULL
         type: ALL
possible_keys: NULL
          key: NULL
      key_len: NULL
          ref: NULL
         rows: 12
     filtered: 100.00
        Extra: NULL
*************************** 2. row ***************************
           id: 1
  select_type: SIMPLE
        table: c
   partitions: NULL
         type: eq_ref
possible_keys: PRIMARY
          key: PRIMARY
      key_len: 4
          ref: test_new.g.t_category_id
         rows: 1
     filtered: 100.00
        Extra: Using index
2 rows in set, 1 warning (0.00 sec)
--【g.id,g.t_category_id】两个字段都是索引字段，此时使用了覆盖索引 “Extra: Using index”
mysql> explain select g.id,g.t_category_id from t_goods g left join t_goods_category c on g.t_category_id = c.id \G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: g
   partitions: NULL
         type: index
possible_keys: NULL
          key: foreign_category
      key_len: 5
          ref: NULL
         rows: 12
     filtered: 100.00
        Extra: Using index
*************************** 2. row ***************************
           id: 1
  select_type: SIMPLE
        table: c
   partitions: NULL
         type: eq_ref
possible_keys: PRIMARY
          key: PRIMARY
      key_len: 4
          ref: test_new.g.t_category_id
         rows: 1
     filtered: 100.00
        Extra: Using index
2 rows in set, 1 warning (0.00 sec)
```
