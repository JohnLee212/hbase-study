package com.example.hbasestudy.controller;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;

/**
 * @author liwenxing
 * @Version 1.0
 * @Description ceshi
 * @date 2020/7/10 17:25
 */
public class ZKDemo {

    public static void main(String[] args) throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper("47.114.94.33:2181", 5000, new Watcher(){
            @Override
            public void process(WatchedEvent event) {
                // TODO Auto-generated method stub
            }
        });
        List<String> children = zooKeeper.getChildren("/", null);
        for (String string : children) {
            System.out.println(string);
        }
    }
}
