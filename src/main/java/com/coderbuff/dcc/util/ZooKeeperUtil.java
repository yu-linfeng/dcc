package com.coderbuff.dcc.util;

import com.coderbuff.dcc.vo.Node;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * Description:
 * ZK工具类
 * 2019-05-17
 * Created with OKevin.
 */
public class ZooKeeperUtil {

    /**
     * 创建节点
     * @param path 路径
     * @param zk zk连接
     * @return 节点路径
     * @throws KeeperException KeeperException
     * @throws InterruptedException InterruptedException
     */
    public static Node createNode(String path, ZooKeeper zk) throws KeeperException, InterruptedException {
        zk.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        Node node = new Node(path);
        return node;
    }

    /**
     * 删除节点
     * @param path 路径
     * @param zk zk连接
     * @throws KeeperException KeeperException
     * @throws InterruptedException InterruptedException
     */
    public static void deleteNode(String path, int version, ZooKeeper zk) throws KeeperException, InterruptedException {
        zk.delete(path, version);
    }


}
