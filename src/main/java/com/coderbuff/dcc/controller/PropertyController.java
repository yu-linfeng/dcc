package com.coderbuff.dcc.controller;

import com.coderbuff.dcc.util.ZooKeeperConn;
import com.coderbuff.dcc.util.ZooKeeperUtil;
import com.coderbuff.dcc.vo.Message;
import com.coderbuff.dcc.vo.Property;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            ZooKeeperUtil.setProperty(property.getKey(), property.getValue(), zooKeeperConn.getZKConnection());
        } catch (KeeperException | InterruptedException e) {
            log.error("设置节点：{}值失败, 错误信息={}", property.getKey(), e.getMessage(), e);
            return Message.failure();
        }
        return Message.success(property);
    }
}
