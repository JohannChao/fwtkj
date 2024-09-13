### 1. mysqltuner.pl
这是mysql一个常用的数据库性能诊断工具，主要检查参数设置的合理性包括日志文件、存储引擎、安全建议及性能分析。针对潜在的问题，给出改进的建议，是mysql优化的好帮手。

在上一版本中，MySQLTuner支持MySQL / MariaDB / Percona Server的约300个指标。

项目地址：https://github.com/major/MySQLTuner-perl

### 2. tuning-primer.sh
这是mysql的另一个优化工具，针于mysql的整体进行一个体检，对潜在的问题，给出优化的建议。

项目地址：https://github.com/BMDan/tuning-primer.sh

### 3. pt-variable-advisor
pt-variable-advisor 可以分析MySQL变量并就可能出现的问题提出建议。

pt-variable-advisor是pt工具集的一个子工具，主要用来诊断你的参数设置是否合理。

项目地址：https://www.percona.com/downloads/percona-toolkit/LATEST/

### 4. pt-qurey-digest
pt-query-digest 主要功能是从日志、进程列表和tcpdump分析MySQL查询。

pt-query-digest主要用来分析mysql的慢日志，与mysqldumpshow工具相比，py-query_digest 工具的分析结果更具体，更完善。