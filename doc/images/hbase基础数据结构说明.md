# HBase基础数据结构说明
rowkey: 唯一，顺序递增，长度不能超过 16KB，主键。自动排序
column family: 列族，列族下可以有多个列，列名可以重复，列值不能重复
column: 列，列名可以重复，列值不能重复
cell: 单元格，单元格由 rowkey、column family、column、value 组成
timestamp: 时间戳，默认为当前时间，可以指定时间戳，时间戳越小，越早
version: 版本，默认为 1。列值相同，时间戳不同的版本，即为不同版本

查询方式：（保障效率）
1. 根据rowkey查询
2. 根据rowkey 范围查询
3. 扫描全局数据