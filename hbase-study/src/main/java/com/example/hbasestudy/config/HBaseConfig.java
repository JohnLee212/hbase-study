package com.example.hbasestudy.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

import java.io.IOException;

/**
 * @author liwenxing
 * @Version 1.0
 * @Description Hbase配置类
 * @date 2020/7/9 16:11
 */
@Configuration
@Slf4j
@EnableConfigurationProperties(HBaseProperties.class)
public class HBaseConfig {


    @Value("${hbase.zookeeper.quorum}")
    private String zookeeperQuorum;

    @Value("${hbase.zookeeper.property.clientPort}")
    private String clientPort;

    @Value("${zookeeper.znode.parent}")
    private String znodeParent;

    @Value("${hbase.master}")
    private String hbaseMaster;

    @Bean
    public HbaseTemplate hbaseTemplate() {
        org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
        conf.set("hbase.zookeeper.quorum", zookeeperQuorum);
        conf.set("hbase.zookeeper.property.clientPort", clientPort);
        conf.set("zookeeper.znode.parent", znodeParent);
        conf.set("hbase.master", hbaseMaster);
        return new HbaseTemplate(conf);
    }

    @Bean
    public Connection myHBaseConnection() {
        try {
            org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
            conf.set("hbase.zookeeper.quorum", zookeeperQuorum);
            conf.set("hbase.zookeeper.property.clientPort", clientPort);
            conf.set("zookeeper.znode.parent", znodeParent);
            conf.set("hbase.master", hbaseMaster);
            //-------暂时保留不清楚用处
            conf.set("zookeeper.sasl.client", "false");
            return ConnectionFactory.createConnection(conf);
        } catch (IOException e) {
           log.error("------Hbase初始化失败");
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @Bean
    public org.apache.hadoop.conf.Configuration customHBaseConfig() {
        org.apache.hadoop.conf.Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.master", "47.114.94.33:16020");
        conf.set("hbase.zookeeper.quorum", "47.114.94.33");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("hbase.rootdir", "hdfs://47.114.94.33:9000/hbase");
        //-------暂时保留不清楚用处
        conf.set("zookeeper.sasl.client", "false");
        return conf;
    }
}
