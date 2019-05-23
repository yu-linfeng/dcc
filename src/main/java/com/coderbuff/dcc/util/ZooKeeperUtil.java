package com.coderbuff.dcc.util;

import com.coderbuff.dcc.vo.Node;
import com.coderbuff.dcc.vo.Property;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Description:
 * ZK工具类
 * 2019-05-17
 * Created with OKevin.
 */
@Slf4j
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

    /**
     * 设置节点的值
     * @param key 节点路径
     * @param value 值
     * @param zk zk连接
     * @throws KeeperException KeeperException
     * @throws InterruptedException InterruptedException
     */
    public static void setProperty(String key, String value, ZooKeeper zk) throws KeeperException, InterruptedException {
        log.debug("设置节点：{}的值为：{}", key, value);
        zk.setData(key, value.getBytes(), -1);
    }

    /**
     * 获取节点配置
     * @param key 节点路径
     * @param zk zk连接
     * @return 节点配置
     * @throws KeeperException KeeperException
     * @throws InterruptedException InterruptedException
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     */
    public static String getProperty(String key, ZooKeeper zk) throws KeeperException, InterruptedException, UnsupportedEncodingException {
        log.debug("查询节点配置：{}的值", key);
        byte[] nodeProperty = zk.getData(key, false, new Stat());
        if (nodeProperty == null) {
            return "";
        }
        String value = new String(nodeProperty, "UTF-8");

        return value;
    }


    /**
     * 遍历叶子节点
     * @param path 遍历起始路径
     * @param zk zk连接
     * @return 节点
     * @throws KeeperException KeeperException
     * @throws InterruptedException InterruptedException
     */
    public static List<Node> listAllNode(String path, ZooKeeper zk) throws KeeperException, InterruptedException {
        List<Node> nodes = Lists.newArrayList();
        traversingProperty(path, zk, nodes);
        return nodes;
    }

    private static void traversingProperty(String path, ZooKeeper zk, List<Node> nodes) throws KeeperException, InterruptedException {
        List<String> list = zk.getChildren(path, false);
        if (path.equals("/")) {
            path = "";
        }
        for (String p : list) {
            String childPath = path + "/" + p;
            if (childPath.equals("/zookeeper") || childPath.equals("/dubbo")) {
                continue;
            }
            List<String> children = zk.getChildren(childPath, false);
            if (children.isEmpty()) {
                Node node = new Node();
                node.setPath(childPath);
                nodes.add(node);
            } else {
                traversingProperty(childPath, zk, nodes);
            }
        }
    }


}
