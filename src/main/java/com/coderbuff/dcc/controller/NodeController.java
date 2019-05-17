package com.coderbuff.dcc.controller;

import com.coderbuff.dcc.util.ZooKeeperConn;
import com.coderbuff.dcc.util.ZooKeeperUtil;
import com.coderbuff.dcc.vo.Message;
import com.coderbuff.dcc.vo.Node;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * 节点操作控制器
 * 2019-05-17
 * Created with OKevin.
 */
@RestController
@RequestMapping("/node")
public class NodeController {

    @Autowired
    private ZooKeeperConn zooKeeperConn;

    @PostMapping("/createNode")
    public Message createNode() {
        Node node = null;

        try {
            node = ZooKeeperUtil.createNode("/test2", zooKeeperConn.getZKConnection());
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            return Message.failure();
        }
        return Message.success(node);
    }

    @PostMapping("/deleteNode")
    public Message deleteNode() {
        try {
            ZooKeeperUtil.deleteNode("/test2", 0, zooKeeperConn.getZKConnection());
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            return Message.failure();
        }
        return Message.success();
    }


}
