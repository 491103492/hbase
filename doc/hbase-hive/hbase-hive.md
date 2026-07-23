# HBase和Hive的集成操作
## 10.1hbase和hive的对比说明
HBase:是一个基于hadoop的nosql型数据库,延迟比较低,对接在线业务,或者对应实时业务
HIVE:是一个基于hadoop的数据仓库的工具,延迟比较高,对接离线分析的操作,主要是进行ETL操作
hive和hbase都是两款基于hadoop的不同软件,这两款软件也是可以支持集成操作的,可以使用hive读取hbase中数据
从而实现离线分析的操作,hbase和Phoenix进行集成,集成后主要可以进行即席的查询操作

## 10.2hbase如何hive进行集成操作
准备工作:
### 1)将hive提供的与hbase集成的通信包,拷贝到hbase的lib目录下
cd/export/server/hive-2.1.0/lib
cp hive-hbase-handler-2.1.0.jar /export/server/hbase-2.1.0/1ib/
### 2)将node3中hbase下的与hive整合通信包拷贝到node2和node1中
cd /export/server/hbase-2.1.0/lib/
scp -r hive-hbase-handler-2.1.0.jar node1:SPWD
scp -r hive-hbase-handler-2.1.0.jar node2:SPWD
### 3)修改hive的hive-site.xml:node3
cd/export/server/hive-2.1.0/conf
vim hive-site.xml
添加以下内容:
<property>
<hame>hive.zookeeper.quorum</name>
<value>node1.itcast.cn, node2.itcast.cn,node3.itoast.cn</value>
</property>
<property>
<name>hbase.zookeeper.quorum</name>
<value>node1.itcast.cn,node2.itcast.cn,node3.itcast.cn</value>
</property>
<property>
<name>hive.server2.enable.doAs</name>
<value>false</value>
</property>

### 4)修改hive的hive-env.sh配置文件:
cd/export/server/hive-2.1.0/conf
vim hive-env.sh
添加以下内容:
export HBASE_HOME=/export/server/hbase-2.1.0