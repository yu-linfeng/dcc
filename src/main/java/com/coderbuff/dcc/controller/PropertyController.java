package com.coderbuff.dcc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.coderbuff.dcc.util.ZooKeeperConn;
import com.coderbuff.dcc.util.ZooKeeperUtil;
import com.coderbuff.dcc.vo.Config;
import com.coderbuff.dcc.vo.Message;
import com.coderbuff.dcc.vo.Node;
import com.coderbuff.dcc.vo.Property;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
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
    public Message setProperty(@RequestBody Node node) {
        try {
            ZooKeeperUtil.setProperty(node.getPath(), node.strValue(), zooKeeperConn.getZKConnection());
        } catch (KeeperException | InterruptedException e) {
            log.error("设置节点：{}值失败, 错误信息={}", node.getPath(), e.getMessage(), e);
            return Message.failure();
        }
        return Message.success(node);
    }

    @PostMapping("getProperty")
    public Message getProperty(@RequestBody Node node) {
        Node propertyValue = new Node();
        try {
            String value = ZooKeeperUtil.getProperty(node.getPath(), zooKeeperConn.getZKConnection());
            propertyValue = convert(node.getPath(), value);
        } catch (KeeperException | InterruptedException | UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error("设置节点：{}值失败, 错误信息={}", node.getPath(), e.getMessage(), e);
            return Message.failure();
        }
        return Message.success(propertyValue);
    }

    private Node convert(String path, String value) {
        Node node = new Node();
        node.setPath(path);
        List<Property> properties = Lists.newArrayList();
        JSONObject map = JSON.parseObject(value);
        JSONArray propertyArray = JSONArray.parseArray(map.getString("properties"));
        for (int i = 0; i < propertyArray.size(); i++) {
            JSONObject propertyObject = propertyArray.getJSONObject(i);
            Property property = new Property();
            property.setName(propertyObject.getString("name"));
            property.setDesc(propertyObject.getString("desc"));
            property.setCreated(propertyObject.getLong("created"));
            List<Config> configs = Lists.newArrayList(JSONArray.parseArray(propertyObject.getString("configs"), Config.class));
            property.setConfigs(configs);
            properties.add(property);

        }
        node.setProperties(properties);
        return node;
    }
}
