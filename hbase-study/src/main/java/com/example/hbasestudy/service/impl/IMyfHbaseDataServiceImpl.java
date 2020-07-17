package com.example.hbasestudy.service.impl;

import com.example.hbasestudy.service.IMyfHbaseDataService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liwenxing
 * @Version 1.0
 * @Description cesh
 * @date 2020/7/9 20:24
 */
@Service
@Slf4j
public class IMyfHbaseDataServiceImpl implements IMyfHbaseDataService {


    @Resource
    private HbaseTemplate hbaseTemplate;

    @Resource
    private Connection myHBaseConnection;

    /**
     * 输入 hbase 表名、点值、日期串 如"2016-12-12"，列族c 得到 list
     * "test1", pointCodes,"2016-12-18", "c"
     */
    @Override
    public List<String> getListDataByPoints(String tableName,
                                            String pointCodes, String day, String cols) throws Exception {
        List<String> dataList = Lists.newArrayList();
        // 拆分用来拼接rowkey
        String[] points = pointCodes.split(",");
        if (points.length <= 0) {
            return null;
        }
        List<Get> getList = new ArrayList<Get>();
        for (int i = 0; i < points.length; i++) {
            String rowkey = points[i] + (day.replaceAll("-", ""));
            Get get = new Get(Bytes.toBytes(rowkey));
            // 获取指定列族数据
            get.addFamily(Bytes.toBytes(cols));
            getList.add(get);
        }
        try {
            Table table = myHBaseConnection.getTable(TableName.valueOf(tableName));
            // 得到 一整行数据： 不指定列的情况下
            Result[] results = table.get(getList);
            for (int j = 0; j < results.length; j++) {
                Result result = results[j];
                // 查询后数据，拼结果串
                StringBuffer datas = new StringBuffer();
                datas.append("");
                Cell[] cells = result.rawCells();
                for (Cell cell : cells) {
                    if (datas.length() > 0) {
                        datas.append(",");
                    }
                    datas.append(new String(CellUtil.cloneQualifier(cell))
                            + ":" + new String(CellUtil.cloneValue(cell)));

                }
                if (datas.length() == 0) {
                    dataList.add("");
                } else {
                    // 处理数据后，添加到list
//                  dataList.add(dealDataOneDay(datas));
                }
            }
            table.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    @Override
    public void createTable(String tableName, String... cols) throws IOException {
        Admin admin = myHBaseConnection.getAdmin();
        TableName tableName2 = TableName.valueOf(tableName);
        if (admin.tableExists(tableName2)) {
            log.info("table is exists!");
        } else {
            HTableDescriptor descriptor = new HTableDescriptor(tableName2);
            for (String col : cols) {
                HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(col);
                descriptor.addFamily(hColumnDescriptor);
                admin.createTable(descriptor);
            }
        }
    }
}
