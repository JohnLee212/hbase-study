package com.example.hbasestudy.service;

import java.io.IOException;
import java.util.List;

/**
 * @author liwenxing
 * @Version 1.0
 * @Description ces
 * @date 2020/7/9 20:23
 */
public interface IMyfHbaseDataService {
    /**
     * 输入 hbase 表名、点值如、日期串 如"2016-12-12"，列族c 得到 list
     */
    List<String> getListDataByPoints(String tableName,
                                            String pointCodes, String day, String cols) throws Exception;

    void createTable(String tableName, String... cols) throws IOException ;
}
