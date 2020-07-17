package com.example.hbasestudy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author liwenxing
 * @Version 1.0
 * @Description 测试
 * @date 2020/7/9 18:27
 */
@Component("hbaseProperties")
@ConfigurationProperties(prefix = HBaseProperties.CONF_PREFIX)
public class HBaseProperties {

    public static final String CONF_PREFIX = "hbase";

    private Map<String, String> config;

    public Map<String, String> getConfig() {
        return config;
    }

    public void setConfig(Map<String, String> config) {
        this.config = config;
    }
}
