# hbase shell

## 状态 status

## 创建表
create 'test02','f1','f2' # 创建表 test02 列族 f1,f2

## 添加数据
put 'test01','row1','f1:name','zhangsan'
put 'test01','row1','f1:age','18'

## 查询数据
get 'test01','row1'
get 'test01','row1','f1:name'
scan 'test01'

## 系统与环境命令

### status‌：查看集群状态（支持 summary/simple/detailed 参数）
### version‌：查看 HBase 版本
### list‌：列出所有表
### exists '表名'‌：检查表是否存在
### whoami‌：显示当前操作用户 
### 表结构管理 (DDL)

### 创建表‌：create '表名', '列族 1', '列族 2'（可加属性如 {NAME=>'cf', VERSIONS=>3}）
### 描述表结构‌：describe '表名' 或 desc '表名'
### 修改表（加列族）‌：alter '表名', '新列族名'
### 修改表（删列族/改属性）‌：alter '表名', {NAME=>'列族', METHOD=>'delete'} 或 {NAME=>'列族', VERSIONS=>5}
### 禁用表‌：disable '表名'（删除前必须执行）
### 启用表‌：enable '表名'
### 删除表‌：drop '表名'（前提是先 disable）
### 清空表‌：truncate '表名'（保留结构，删除数据）‌
### 数据操作 (DML)

### 插入/更新数据‌：put '表名', '行键', '列族:列名', '值'（HBase 无单独 update，put 覆盖即为更新）
### 查询单行‌：get '表名', '行键'（可指定列：get '表名', '行键', '列族:列名'）
### 删除指定列‌：delete '表名', '行键', '列族:列名' 
### 删除整行‌：deleteall '表名', '行键' 
### 统计行数‌：count '表名' 
### 扫描与高级查询

### 全表扫描‌：scan '表名'（慎用大表）
### 限定范围扫描‌：scan '表名', {STARTROW=>'开始键', STOPROW=>'结束键', LIMIT=>行数}
### 指定列扫描‌：scan '表名', {COLUMN=>'列族'} 或 {COLUMNS=>'列族:列'}
### 过滤查询‌：scan '表名', FILTER=>"PrefixFilter('前缀')" 或组合条件（AND/OR）
### 查看支持过滤器‌：show_filters 
### 中文问题解决：scan '表名', {COLUMNS=>'列族:列', FORMATTER=>'toString'}  

格式:
scan
"表名",{FILTER=>"过滤器名称(比较运算符,比较器表达式)"}
常见的过滤器:
    rowkey过滤器:
        RowFilter:实现行键字符串的比较和过滤操作
        PrefixFilter :
    列族过滤器:
        rowkey前缀过滤器
        FamilyFilter:列族过滤器
    列名过滤器:
        QualifierFilter:列名过滤器,中显示对应列名的数据
    列值过滤器:
        ValueFilter:列值过滤器,找到符合值的数据
        SingleColumnValueFilter:
        (包含条件的内容字段)
    在执行的列族和列名中进行比较具体的值,将符合条数据整行数据全部都返回
        SingleColumnVlaueExcludeFilter:在执行的列族和列名中进行比较具体的值,将符合条数据整行数据全部
        都返回(去除条件内容的字段)
其他过滤器:
PageFilter:用来执行分页操作
比较运算符:=><>=<=!=

需求1:查询 以 rk开头的数据
scan 'test01' ,{FILTER=>"PrefixFilter('rk')"}
需求2:
查询name字段的值包含s这个字母数据
scan 'test01', {FILTER=>"SingleColumnValueFilter('C1','name',=,'substring:s')"}
scan 'test01', {FILTER=>"SingleColumnValueExcludeFilter('C1','name',=, 'substring:s')"}

如果不知道过滤器的构造参数,可以查看此地址:
http://hbase.apache.org/2.2/devapidocs/index.htm1