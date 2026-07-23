package com.suky.hbase;

import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class HbaseBulkLoadTask extends Mapper<LongWritable, Text, ImmutableBytesWritable, Text> {
}
