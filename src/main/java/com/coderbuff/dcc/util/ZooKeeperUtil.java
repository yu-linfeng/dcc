package com.coderbuff.dcc.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * Description:
 * ZK工具类
 * 2019-05-17
 * Created with OKevin.
 */
public enum ZooKeeperUtil {

    /**
     * 单例
     */
    INSTANCE;

    /**
     * 最大重试次数
     */
    private final static Integer MAX_RETRY_COUNT = 5;

    /**
     * 重试时间间隔（毫秒）
     */
    private final static Integer RETRY_TIMES_MILLS = 3000;

    /**
     * 创建ZK连接
     * @param hosts zk服务
     * @param seesionTimeout 超时时间
     * @return zk连接
     * @throws IOException IOException
     * @throws InterruptedException InterruptedException
     */
    public ZooKeeper createZKConnection(String hosts, Integer seesionTimeout) throws IOException, InterruptedException {
        Integer retryCount = 0;
        ZooKeeper zk = new ZooKeeper(hosts, seesionTimeout, watchedEvent -> {
            System.out.println("connecting zk");
        });
        while (zk.getState() != ZooKeeper.States.CONNECTED) {
            Thread.sleep(RETRY_TIMES_MILLS);
            retryCount ++;
            if (retryCount.equals(MAX_RETRY_COUNT)) {
                break;
            }
        }

        return zk;
    }

    /**
     * 创建节点
     * @param path 路径
     * @param zk zk连接
     * @throws KeeperException KeeperException
     * @throws InterruptedException InterruptedException
     */
    public void createNode(String path, ZooKeeper zk) throws KeeperException, InterruptedException {
        zk.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    /**
     * 删除节点
     * @param path 路径
     * @param zk zk连接
     * @throws KeeperException KeeperException
     * @throws InterruptedException InterruptedException
     */
    public void deleteNode(String path, int version, ZooKeeper zk) throws KeeperException, InterruptedException {
        zk.delete(path, version);
    }


}
