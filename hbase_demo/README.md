# hbase
hbase
## 1.hbase的基本介绍
hbase产生背景
hdfs是一款吞吐量极高,适合于进行批量处理的工作,但是随机读写能力比较差(压根不支持),所以如果现在需要进行
随机读写的操作,此时HDFS就无能为力,所以迫切需要一款软件能够帮助实现高效的随机读写操作
hbase的基本介绍:
properties
### 1)hbase是一款noSQL型数据,不支持SQL.不支持join的操作,没有表关系,不支持事务(多行事务)
### 2)hbase是基于Google发布bigTable论文产生一款软件
### 3)hbase是采用java语言编写
### 4)hbase是基于HDFS的,数据最终是存储在HDFS上,如果想想思要启动HBase必须先启动HDFS
### 5)查询hbase中数据一般有三种方案:
#### 5.1:根据主键(rowkey)查询
#### 5.2:根据主键的范围检索
#### 5.3:查询全部数据
存储结构化和半结构化数据


导入的语法:
hbase org.apache.hadoop.hbase.mapreduce.Import 表名HDFS数据文件路径
注意:此命令需要在Linux的shell窗口下执行
执行操作:
1)先将资料中10w抄表数据上传到Linux中:
rz上传即可
在从Linux中上传hdfs的:/hbase/water_bill/input
hdfs dfs -mkdir -p /hbase/water_bill/input
hdfs dfs -put part-m-0000_10w /hbase/water_bill/input
3)执行导入操作:前提表必须存在
hbase org.apache.hadoop.hbase.mapreduce.Import WAITER_BILL /hbase/water_bill/input/part-m-00000_10w
选择语言
导出语法:
hbase org.apache.hadoop.hbase.mapreduce.Export 表名 HDFSS输出数据文件路径