package com.coderbuff.dcc.controller;

import com.coderbuff.dcc.util.ZooKeeperConn;
import com.coderbuff.dcc.util.ZooKeeperUtil;
import com.coderbuff.dcc.vo.Config;
import com.coderbuff.dcc.vo.Message;
import com.coderbuff.dcc.vo.Property;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Description:
 * 配置操作控制器
 * 2019-05-17
 * Created with OKevin.
 */
@RestController
@RequestMapping("/property")
@Slf4j
public class PropertyController {

    @Autowired
    private ZooKeeperConn zooKeeperConn;


    @PostMapping("setProperty")
    public Message setProperty(Property property) {
        try {
            /**
             * value
             */
            ZooKeeperUtil.setProperty(property.getPath(), property.getValue().toString(), zooKeeperConn.getZKConnection());
        } catch (KeeperException | InterruptedException e) {
            log.error("设置节点：{}值失败, 错误信息={}", property.getPath(), e.getMessage(), e);
            return Message.failure();
        }
        return Message.success(property);
    }

    @PostMapping("getProperty")
    public Message getProperty(Property property) {
        List<Property> propertyValue = Lists.newArrayList();
        try {
            String value = ZooKeeperUtil.getProperty(property.getPath(), zooKeeperConn.getZKConnection());
            propertyValue = convert(property.getPath(), value);
        } catch (KeeperException | InterruptedException | UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error("设置节点：{}值失败, 错误信息={}", property.getPath(), e.getMessage(), e);
            return Message.failure();
        }
        return Message.success(propertyValue);
    }

    private List<Property> convert(String path, String value) {
        Property property = new Property();
        property.setPath(path);
        property.setName("switch");
        Config config = new Config();
        config.setName("isEnableDb");
        config.setValue(false);
        config.setDesc("是否降级到数据库");
        Config config1 = new Config();
        config1.setName("xxIsEnableCache");
        config1.setValue(true);
        config1.setDesc("xx数据是否读缓存");
        List<Config> configs = Lists.newArrayList();
        configs.add(config);
        configs.add(config1);
        property.setValue(configs);
        property.setDesc("开关配置");
        List<Property> properties = Lists.newArrayList();
        properties.add(property);
        return properties;
    }
}
