package com.example.hbasestudy.controller;

import com.example.hbasestudy.service.IMyfHbaseDataService;
import com.example.hbasestudy.utils.HBaseUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author liwenxing
 * @Version 1.0
 * @Description cesh
 * @date 2020/7/9 16:22
 */
@Slf4j
@RestController
@RequestMapping("/hbase")
public class DemoController {
    @Resource
    private HbaseTemplate hbaseTemplate;

    @Resource
    private Connection myHBaseConnection;

    @Resource
    private IMyfHbaseDataService hBaseDataService;

    @Resource
    private SendMailText_Picture_Enclosure enclosure;

    @RequestMapping(value = "/test")
    public Map<String, Object> test() throws Exception {
        enclosure.send();
        Admin admin = myHBaseConnection.getAdmin();
        TableName tableName = TableName.valueOf("user2");
        boolean b = admin.tableExists(tableName);
        log.info("biao是否存在在{}",b);
        hBaseDataService.createTable("user3",new String[]{"info","zhangsan"});

        Table table = myHBaseConnection.getTable(TableName.valueOf("user3"));
        //创建 put，并制定 put 的Rowkey
        Put put = new Put(Bytes.toBytes("zhangsan"));
        //byte [] family, byte [] qualifier, byte [] value
        put.addColumn(Bytes.toBytes("student"), Bytes.toBytes("age"), Bytes.toBytes(18));
        put.addColumn(Bytes.toBytes("student"), Bytes.toBytes("sex"), Bytes.toBytes("男"));
        put.addColumn(Bytes.toBytes("student"), Bytes.toBytes("city"), Bytes.toBytes("beijing"));
        put.addColumn(Bytes.toBytes("student"), Bytes.toBytes("address"), Bytes.toBytes("henan"));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("hobby"), Bytes.toBytes("girl"));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("good"), Bytes.toBytes("basketball"));

        Put put1 = new Put(Bytes.toBytes("lisi"));
        // family ,qualifier, value  顺序不可乱，列族，列，内容
        put1.addColumn(Bytes.toBytes("student"), Bytes.toBytes("age"), Bytes.toBytes(22));
        put1.addColumn(Bytes.toBytes("student"), Bytes.toBytes("sex"), Bytes.toBytes("女"));
        put1.addColumn(Bytes.toBytes("student"), Bytes.toBytes("city"), Bytes.toBytes("nanjing"));
        put1.addColumn(Bytes.toBytes("student"), Bytes.toBytes("address"), Bytes.toBytes("hebei"));
        put1.addColumn(Bytes.toBytes("info"), Bytes.toBytes("hobby"), Bytes.toBytes("boy"));
        put1.addColumn(Bytes.toBytes("info"), Bytes.toBytes("good"), Bytes.toBytes("ball"));

        List<Put> list = Lists.newArrayList();
        list.add(put);
        list.add(put1);
        table.put(list);
        hbaseTemplate.get("user2", "user2:username", new RowMapper<Object>() {
            @Override
            public Object mapRow(Result result, int rowNum) throws Exception {
                return result.getValue(Bytes.toBytes("info2"),Bytes.toBytes("name"));
            }
        });
        Map<String, Object> map = Maps.newHashMap();
        try {
            //扫描表
            String str = HBaseUtils.scanAllRecord("user2");
            log.info("获取到hbase的内容：{}", str);
            map.put("hbaseContent", str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static void main(String[] args) {
        Configuration conf = HBaseConfiguration.create();
//        System.out.println(conf.get("hbase.master"));
        String ip ="47.114.94.33";
        String port ="2181";
        conf.set("hbase.master", "47.114.94.33:16010");
        conf.set("hbase.zookeeper.quorum",ip);
        conf.set("hbase.zookeeper.property.clientPort",port);
        conf.set("hbase.rootdir", "hdfs://47.114.94.33:9000/hbase");

        try {
            Connection connection = ConnectionFactory.createConnection(conf);
            Admin admin = connection.getAdmin();
            TableName tableName = TableName.valueOf("user3");
            boolean b = admin.tableExists(tableName);
            System.out.println("表是否存在？"+ b);
            TableName name = TableName.valueOf("user3");
            String[] columnFamily = new String[]{"zhangsan"};
            //如果存在则删除
            if (admin.tableExists(name)) {
                admin.disableTable(name);
                admin.deleteTable(name);
            } else {
                HTableDescriptor desc = new HTableDescriptor(name);
                for (String cf : columnFamily) {
                    desc.addFamily(new HColumnDescriptor(cf));
                }
                admin.createTable(desc);
            }

           /* Table table = connection.getTable(TableName.valueOf("user3"));
            //创建 put，并制定 put 的Rowkey
            Put put = new Put(Bytes.toBytes("zhangsan"));
            //byte [] family, byte [] qualifier, byte [] value
            put.addColumn(Bytes.toBytes("student"), Bytes.toBytes("age"), Bytes.toBytes(18));
            put.addColumn(Bytes.toBytes("student"), Bytes.toBytes("sex"), Bytes.toBytes("男"));
            put.addColumn(Bytes.toBytes("student"), Bytes.toBytes("city"), Bytes.toBytes("beijing"));
            put.addColumn(Bytes.toBytes("student"), Bytes.toBytes("address"), Bytes.toBytes("henan"));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("hobby"), Bytes.toBytes("girl"));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("good"), Bytes.toBytes("basketball"));

            Put put1 = new Put(Bytes.toBytes("lisi"));
            // family ,qualifier, value  顺序不可乱，列族，列，内容
            put1.addColumn(Bytes.toBytes("student"), Bytes.toBytes("age"), Bytes.toBytes(22));
            put1.addColumn(Bytes.toBytes("student"), Bytes.toBytes("sex"), Bytes.toBytes("女"));
            put1.addColumn(Bytes.toBytes("student"), Bytes.toBytes("city"), Bytes.toBytes("nanjing"));
            put1.addColumn(Bytes.toBytes("student"), Bytes.toBytes("address"), Bytes.toBytes("hebei"));
            put1.addColumn(Bytes.toBytes("info"), Bytes.toBytes("hobby"), Bytes.toBytes("boy"));
            put1.addColumn(Bytes.toBytes("info"), Bytes.toBytes("good"), Bytes.toBytes("ball"));

            List<Put> list = Lists.newArrayList();
            list.add(put);
            list.add(put1);
            table.put(list);*/

//            Get get = new Get();
//            System.out.println(table.);

        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
