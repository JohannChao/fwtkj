### 单行转多行
表存储结构：

| id | codeNo      |
| --- |-------------|
| 1 | 001,002,003 |
| 2 | 002,004,005 |

查询的结果：

| id | codeNo |
| --- |--------|
| 1 | 001    |
| 1 | 002    |
| 1 | 003    |
| 2 | 002    |
| 2 | 004    |
| 2 | 005    |

```sql
-- Oracle
SELECT 
    id,
    --REGEXP_SUBSTR(codeNo, '[^,]+', 1, LEVEL):使用正则表达式提取 codeNo 字段中的逗号分隔的值,LEVEL 表示提取的第几个值
    REGEXP_SUBSTR(codeNo, '[^,]+', 1, LEVEL) AS codeNo,
    --LEVEL:是一个伪列,表示当前递归的层级
    LEVEL as RN 
FROM 
    table_name
--CONNECT BY:用于构建层次结构
CONNECT BY 
    --REGEXP_SUBSTR(codeNo, '[^,]+', 1, LEVEL) IS NOT NULL:确保只要还能找到逗号分隔的值,就继续递归
    REGEXP_SUBSTR(codeNo, '[^,]+', 1, LEVEL) IS NOT NULL
    --PRIOR id = id:递归条件,当前行的id值必须与上一行的id值相等,以确保递归过程中仅处理相同id的行
    AND PRIOR id = id 
    --PRIOR SYS_GUID() IS NOT NULL:递归终止条件,防止循环递归，因为SYS_GUID()函数在每次调用时都会生成一个新的全局唯一标识符。
    AND PRIOR SYS_GUID() IS NOT NULL
;

-- MySQL
SELECT
    id,
    SUBSTRING_INDEX(SUBSTRING_INDEX(codeNo, ',', numbers.n), ',', -1) AS codeNo,
    n AS RN
FROM
    table_name,
    --这个MySQL查询仅适用于最多包含5个逗号分隔的值的情况，如果需要处理更多的值，可以在numbers视图中添加更多的数字
    (SELECT 1 n UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5) numbers
WHERE
    LENGTH(codeNo) - LENGTH(REPLACE(codeNo, ',', '')) >= numbers.n - 1
ORDER BY
    id, n
;
```

### 多行转单行
表存储结构：

| id | codeNo |
| --- |--------|
| 1 | 001    |
| 1 | 002    |
| 1 | 003    |
| 2 | 002    |
| 2 | 004    |
| 2 | 005    |

查询的结果：

| id | codeNo      |
| --- |-------------|
| 1 | 001,002,003 |
| 2 | 002,004,005 |

```sql
-- Oracle
SELECT 
    id, 
    --使用LISTAGG函数将codeNo列的值按照指定的顺序（ORDER BY codeNo）合并为一个字符串，用逗号和空格（', '）作为分隔符。WITHIN GROUP表示在每个分组内进行排序和拼接。
    LISTAGG(codeNo , ', ') WITHIN GROUP (ORDER BY codeNo) AS codeNo
FROM table_name
GROUP BY id
;

-- MySQL
SELECT
    id,
    --GROUP_CONCAT：MySQL中用于将多行数据合并成一行的函数
    GROUP_CONCAT(codeNo ORDER BY codeNo SEPARATOR ', ') AS codeNo
FROM table_name
GROUP BY id
;
```

### 单列转多列
表存储结构：

| id | codeNo |
| -- | --- |
| 1 | 001,002 |
| 2 | 002,004,005 |
| 3 | 003,004,006,007 |

查询的结果：

| id | codeNo_1 | codeNo_2 | codeNo_3 | codeNo_4 |
| -- | --- | --- | --- | --- |
| 1 | 001 | 001 | - | - |
| 2 | 002 | 004 | 005 | - |
| 3 | 003 | 004 | 006 | 007 |

```sql
--Oracle
SELECT id,
       REGEXP_SUBSTR(codeNo, '[^,]+', 1, 1) AS codeNo_1,
       REGEXP_SUBSTR(codeNo, '[^,]+', 1, 2) AS codeNo_2,
       REGEXP_SUBSTR(codeNo, '[^,]+', 1, 3) AS codeNo_3,
       REGEXP_SUBSTR(codeNo, '[^,]+', 1, 4) AS codeNo_4
FROM table_name;

--MySQL
SELECT id,
       SUBSTRING_INDEX(SUBSTRING_INDEX(codeNo, ',', 1), ',', -1) AS codeNo_1,
       SUBSTRING_INDEX(SUBSTRING_INDEX(codeNo, ',', 2), ',', -1) AS codeNo_2,
       SUBSTRING_INDEX(SUBSTRING_INDEX(codeNo, ',', 3), ',', -1) AS codeNo_3,
       SUBSTRING_INDEX(SUBSTRING_INDEX(codeNo, ',', 4), ',', -1) AS codeNo_4
FROM table_name;
```
### 多行转多列
表存储结构：

| id | codeNo | codeDes |
| -- | --- | --- |
| 1 | 001 | zz1 |
| 1 | 002 | zz2 |
| 2 | 002 | zz2 |
| 2 | 004 | zz4 |
| 2 | 005 | zz5 |
| 3 | 003 | zz3 |
| 3 | 004 | zz4 |
| 3 | 006 | zz6 |
| 3 | 007 | zz7 |

查询的结果：

| id | codeNo_1 | codeDes_1 | codeNo_2 | codeDes_2 | codeNo_3 | codeDes_3 | codeNo_4 | codeDes_4 |
| -- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | 001 | zz1 | 002 | zz2 | - | - | - | - |
| 2 | 002 | zz2 | 004 | zz4 | 005 | zz5 | - | - |
| 3 | 003 | zz3 | 004 | zz4 | 006 | zz6 | 007 | zz7 |

```sql
--Oracle
WITH original_table AS (
    SELECT 1 AS id, '001' AS codeNo, 'zz1' AS codeDes FROM dual UNION ALL
    SELECT 1 AS id, '002' AS codeNo, 'zz2' AS codeDes FROM dual UNION ALL
    SELECT 2 AS id, '002' AS codeNo, 'zz2' AS codeDes FROM dual UNION ALL
    SELECT 2 AS id, '004' AS codeNo, 'zz4' AS codeDes FROM dual UNION ALL
    SELECT 2 AS id, '005' AS codeNo, 'zz5' AS codeDes FROM dual UNION ALL
    SELECT 3 AS id, '003' AS codeNo, 'zz3' AS codeDes FROM dual UNION ALL
    SELECT 3 AS id, '004' AS codeNo, 'zz4' AS codeDes FROM dual UNION ALL
    SELECT 3 AS id, '006' AS codeNo, 'zz6' AS codeDes FROM dual UNION ALL
    SELECT 3 AS id, '007' AS codeNo, 'zz7' AS codeDes FROM dual
),
     row_numbered AS (
         SELECT
             id,
             codeNo,
             codeDes,
             ROW_NUMBER() OVER(PARTITION BY id ORDER BY codeNo) RN
         FROM original_table
     )
SELECT id,
       MAX(CASE WHEN rn = 1 THEN codeNo END) AS codeNo_1,
       MAX(CASE WHEN rn = 1 THEN codeDes END) AS codeDes_1,
       MAX(CASE WHEN rn = 2 THEN codeNo END) AS codeNo_2,
       MAX(CASE WHEN rn = 2 THEN codeDes END) AS codeDes_2,
       MAX(CASE WHEN rn = 3 THEN codeNo END) AS codeNo_3,
       MAX(CASE WHEN rn = 3 THEN codeDes END) AS codeDes_3,
       MAX(CASE WHEN rn = 4 THEN codeNo END) AS codeNo_4,
       MAX(CASE WHEN rn = 4 THEN codeDes END) AS codeDes_4
FROM row_numbered
GROUP BY id;

--Oracle 
WITH original_table AS (
    SELECT 1 AS id, '001' AS codeNo, 'zz1' AS codeDes FROM dual UNION ALL
    SELECT 1 AS id, '002' AS codeNo, 'zz2' AS codeDes FROM dual UNION ALL
    SELECT 2 AS id, '002' AS codeNo, 'zz2' AS codeDes FROM dual UNION ALL
    SELECT 2 AS id, '004' AS codeNo, 'zz4' AS codeDes FROM dual UNION ALL
    SELECT 2 AS id, '005' AS codeNo, 'zz5' AS codeDes FROM dual UNION ALL
    SELECT 3 AS id, '003' AS codeNo, 'zz3' AS codeDes FROM dual UNION ALL
    SELECT 3 AS id, '004' AS codeNo, 'zz4' AS codeDes FROM dual UNION ALL
    SELECT 3 AS id, '006' AS codeNo, 'zz6' AS codeDes FROM dual UNION ALL
    SELECT 3 AS id, '007' AS codeNo, 'zz7' AS codeDes FROM dual
)
SELECT
    id,
    RN1_cn as codeNo_1,
    RN1_cd as codeDes_1,
    RN2_cn as codeNo_2,
    RN2_cd as codeDes_2,
    RN3_cn as codeNo_3,
    RN3_cd as codeDes_3,
    RN4_cn as codeNo_4,
    RN4_cd as codeDes_4
FROM (
 SELECT
     id,
     codeNo,
     codeDes,
     ROW_NUMBER() OVER(PARTITION BY id ORDER BY codeNo) RN
 FROM original_table
)
PIVOT (
    MAX(codeNo) as cn,MAX(codeDes) as cd
    FOR RN IN (1 RN1,2 RN2,3 RN3,4 RN4)
)

--MySQL
SELECT id,
       SUBSTRING_INDEX(SUBSTRING_INDEX(GROUP_CONCAT(codeNo ORDER BY codeNo), ',', 1), ',', -1) AS codeNo_1,
       SUBSTRING_INDEX(SUBSTRING_INDEX(GROUP_CONCAT(codeNo ORDER BY codeNo), ',', 2), ',', -1) AS codeNo_2,
       SUBSTRING_INDEX(SUBSTRING_INDEX(GROUP_CONCAT(codeNo ORDER BY codeNo), ',', 3), ',', -1) AS codeNo_3,
       SUBSTRING_INDEX(SUBSTRING_INDEX(GROUP_CONCAT(codeNo ORDER BY codeNo), ',', 4), ',', -1) AS codeNo_4
FROM original_table
GROUP BY id;
```