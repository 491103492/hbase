package com.suky.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.HFileOutputFormat2;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.util.Tool;


public class HbaseBulkLoadDriver extends Configuration implements Tool {
    @Override
    public int run(String[] strings) throws Exception {
        //1.创建job对象
//        Job job = Job.getInstance(super.getConf(), "BulkLoadDriver");
//        //2.设置集群运行必备参数
//        job.setJarByClass(HbaseBulkLoadDriver.class);
//        //3.设置天龙八部:
//        //3.1:设置输入类和输入的路径
//        job.setInputFormatClass(TextInputFormat.class);
//        TextInputFormat.addInputPath(job,new Path("hdfs://nodel:8020/hbase/bank_record/input/a"));
//        //3.2:设置map类以及输出的k2和v2的类型
//        job.setMapperClass(HbaseBulkLoadTask.class);
//        job.setMapOutputKeyClass (ImmutableBytesWritable.class);
//        job.setMapOutputValueClass(Put.class);
//        //3.3设置shuffle操作:分区排序规约分组(默认)
//        //3.7:设置reduce类,以及输出的k3和v3的类型
//        job.setNumReduceTasks(0);
//        job.setOutputKeyClass (ImmutableBytesWritable.class);
//        job.setOutputValueClass(Put.class);
//
//        //3.8:设置输出类,以及输出路径
//        job.setOutputFormatClass(HFileOutputFormat2.class);
//        Connection hconn = ConnectionFactory.createConnedtion(super.getConf());
//        Table table = hconn.getTable(TableName.valueOf("TRANSFER_RECORD"));
//        HFileOutputFormat2.configureIncrementalLoad(job,table, hconn.getRegionLocator(TableName.valueOf("TRANSFER_RECORD"));
//        HFileOutputFormat2.setOutputPath(job,new Path("hdfs://nodel:8020/hbase/bank_record/output"));

        return 0;
    }

    @Override
    public void setConf(Configuration configuration) {

    }

    @Override
    public Configuration getConf() {
        return null;
    }
}
