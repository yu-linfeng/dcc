package com.coderbuff.dcc.controller;

import com.coderbuff.dcc.util.ZooKeeperConn;
import com.coderbuff.dcc.util.ZooKeeperUtil;
import com.coderbuff.dcc.vo.Message;
import com.coderbuff.dcc.vo.Node;
import com.google.common.collect.Lists;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description:
 * 节点操作控制器
 * 2019-05-17
 * Created with OKevin.
 */
@RestController
@RequestMapping("/node")
@CrossOrigin
public class NodeController {

    @Autowired
    private ZooKeeperConn zooKeeperConn;

    @PostMapping("/createNode")
    public Message createNode(@RequestBody Node node) {

        try {
            node = ZooKeeperUtil.createNode(node.getPath(), zooKeeperConn.getZKConnection());
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            return Message.failure();
        }
        return Message.success(node);
    }

    @PostMapping("/deleteNode")
    public Message deleteNode(@RequestBody Node node) {
        try {
            ZooKeeperUtil.deleteNode(node.getPath(), -1, zooKeeperConn.getZKConnection());
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            return Message.failure();
        }
        return Message.success();
    }

    @PostMapping("/queryNode")
    public Message queryNode(Node node) {
        List<Node> nodeList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            Node aNode = new Node("/" + i);
            aNode.setCreated(System.currentTimeMillis());
            nodeList.add(aNode);
        }
        return Message.success(nodeList);
    }
}
