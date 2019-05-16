package com.coderbuff.dcc.controller;

import com.coderbuff.dcc.util.ZooKeeperUtil;
import com.coderbuff.dcc.vo.Message;
import com.coderbuff.dcc.vo.Node;
import org.apache.zookeeper.KeeperException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Description:
 * 节点操作控制器
 * 2019-05-17
 * Created with OKevin.
 */
@RestController
@RequestMapping("/node")
public class NodeController {

    @PostMapping("/createNode")
    public Message createNode() {
        Node node = null;
        try {
            node = ZooKeeperUtil.INSTANCE.createNode("/test2", ZooKeeperUtil.INSTANCE.createZKConnection("localhost", 30000));
        } catch (KeeperException | InterruptedException | IOException e) {
            e.printStackTrace();
            return Message.failure();
        }
        return Message.success(node);
    }

    @PostMapping("/deleteNode")
    public Message deleteNode() {
        try {
            ZooKeeperUtil.INSTANCE.deleteNode("/test2", 0, ZooKeeperUtil.INSTANCE.createZKConnection("localhost", 30000));
        } catch (KeeperException | InterruptedException | IOException e) {
            e.printStackTrace();
            return Message.failure();
        }
        return Message.success();
    }


}
