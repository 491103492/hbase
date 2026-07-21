package com.suky.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * hbase 简单的操作实例
 */
public class CreateTable {

    // hbase 链接对象
    public static Connection connection;
    // 表
    public static String tableName = "test_students";

    static {
        Configuration conf = new Configuration();
        conf.set("hbase.zookeeper.quorum","localhost:2181"); // 多个zookeeper 使用逗号隔开 node:2181,node2:2181
        try {
            connection = ConnectionFactory.createConnection(conf);
            System.out.println("连接connection 成功" + connection);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 在hbase中创建表
     * @throws IOException
     */
    @Test
    public void createTable() throws IOException {
        // 获取链接对象
        Admin admin = connection.getAdmin();
        // 判断表是否存在
        boolean flag = admin.tableExists(TableName.valueOf(tableName));
        if (!flag) {
            // 创建表
            // 1. 指定表明
            TableDescriptorBuilder builder = TableDescriptorBuilder.newBuilder(TableName.valueOf(tableName));
            // 2. 添加列族
            TableDescriptorBuilder info = builder.setColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder("info".getBytes()).build());
            TableDescriptorBuilder score = builder.setColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder("score".getBytes()).build());

            // 3. 创建表
            admin.createTable(builder.build());
            System.out.println("创建表成功");
        }
        // 关闭链接
        admin.close();
    }

    /**
     * 插入数据
     * @throws IOException
     */
    @Test
    public void insertData() throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
//        table.put(
//                new Put("1002".getBytes())
//                        .addColumn("info".getBytes(),"name".getBytes(),"lisi".getBytes())
//                        .addColumn("info".getBytes(),"age".getBytes(),"18".getBytes())
//                        .addColumn("score".getBytes(),"chinese".getBytes(),"54".getBytes())
//                        .addColumn("score".getBytes(),"math".getBytes(),"45".getBytes())
//                        .addColumn("score".getBytes(),"english".getBytes(),"88".getBytes())
//        );
//        System.out.println("插入数据成功");

        // 批量插入数据
        List<Put> puts = new ArrayList<>();
        puts.add(new Put("1003".getBytes())
                .addColumn("info".getBytes(),"name".getBytes(),"wangwu".getBytes())
                .addColumn("info".getBytes(),"age".getBytes(),"19".getBytes())
                .addColumn("score".getBytes(),"chinese".getBytes(),"55".getBytes())
                .addColumn("score".getBytes(),"math".getBytes(),"56".getBytes())
                .addColumn("score".getBytes(),"english".getBytes(),"89".getBytes()));
        puts.add(new Put("1004".getBytes())
                .addColumn("info".getBytes(),"name".getBytes(),"zhaoliu".getBytes())
                .addColumn("info".getBytes(),"age".getBytes(),"20".getBytes())
                .addColumn("score".getBytes(),"chinese".getBytes(),"56".getBytes())
                .addColumn("score".getBytes(),"math".getBytes(),"57".getBytes())
                .addColumn("score".getBytes(),"english".getBytes(),"90".getBytes()));
        // 批量插入
        table.put(puts);
        System.out.println("批量插入数据成功");
        // 释放资源
        table.close();
    }

    /**
     * 查询数据
     * @throws IOException
     */
    @Test
    public void queryData() throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        // 1. 查询单个数据
        Get get = new Get("1002".getBytes());
        Result result = table.get(get);
        for (Cell cell : result.listCells()) {
            System.out.println(new String(CellUtil.cloneFamily(cell)) + ":" + new String(CellUtil.cloneQualifier(cell)) + ":" + new String(CellUtil.cloneValue(cell)));
        }


        table.close();
    }
    /**
     * 删除数据
     * @throws IOException
     */
    @Test
    public void deleteData() throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Delete delete = new Delete("1002".getBytes());
        table.delete(delete);
        System.out.println("删除数据成功");
        table.close();
        connection.close();
        System.out.println("关闭链接成功");
    }

    /**
     * 删除表
     * @throws IOException
     */
    @Test
    public void deleteTable() throws IOException {
        Admin admin = connection.getAdmin();
        admin.disableTable(TableName.valueOf(tableName)); // 删除表之前，一定需要先禁用表
        admin.deleteTable(TableName.valueOf(tableName));
        System.out.println("删除表成功");
        admin.close();
    }

    /**
     * 扫描数据
     * @throws IOException
     */
    @Test
    public void scanData() throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        // 1. 查询单个数据
        Scan scan = new Scan();
        // 增加过滤器
        SingleColumnValueFilter lisi = new SingleColumnValueFilter(
                "score".getBytes(),
                "mate".getBytes(),
                CompareFilter.CompareOp.GREATER_OR_EQUAL,
                new LongComparator(60));
        SingleColumnValueFilter wangwu = new SingleColumnValueFilter(
                "score".getBytes(),
                "mate".getBytes(),
                CompareFilter.CompareOp.LESS_OR_EQUAL,
                new LongComparator(100));
        // 默认只能设置一个
//        scan.setFilter(lisi);

        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        filterList.addFilter(lisi);
        filterList.addFilter(wangwu);
        // 使用多个
        scan.setFilter(filterList);


        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner) {
            for (Cell cell : result.listCells()) {
                System.out.println(new String(CellUtil.cloneFamily(cell)) + ":" + new String(CellUtil.cloneQualifier(cell)) + ":" + new String(CellUtil.cloneValue(cell)));
            }
        }


        table.close();
        connection.close();
    }

}
