package com.suky.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.junit.Test;

import java.io.IOException;

public class CreateTable {

    /**
     * 在hbase中创建表
     * @throws IOException
     */
    @Test
    public void createTable() throws IOException {

        Configuration conf = new Configuration();
        Connection connection = ConnectionFactory.createConnection(conf);


    }
}
