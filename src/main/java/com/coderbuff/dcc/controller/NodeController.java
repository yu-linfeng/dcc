package com.coderbuff.dcc.controller;

import com.coderbuff.dcc.util.ZooKeeperUtil;
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
    public String createNode() {
        try {
            ZooKeeperUtil.INSTANCE.createNode("/test2", ZooKeeperUtil.INSTANCE.createZKConnection("localhost", 30000));
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "OK";
    }
}
