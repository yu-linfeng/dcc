package com.coderbuff.dcc.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

/**
 * Description:
 * ZK初始化连接
 * 2019-05-17
 * Created with OKevin.
 */
@Slf4j
public class ZooKeeperConn implements InitializingBean {

    /**
     *  zk服务
     */
    @Value("${zk.server.hosts}")
    private String hosts;

    /**
     * zk会话过期时间毫秒
     */
    @Value("${zk.server.session}")
    private Integer sessionTime;

    /**
     * zk
     */
    private ZooKeeper zk;

    /**
     * 最大重试次数
     */
    @Value("${zk.server.max.retry.count}")
    private Integer maxRetryCount;

    /**
     * 重试时间间隔毫秒
     */
    @Value("${zk.server.retry.interval}")
    private Integer retryInterval;

    @Override
    public void afterPropertiesSet() throws IOException, InterruptedException{
        Integer retryCount = 0;
        zk = new ZooKeeper(hosts, sessionTime, watchedEvent -> log.debug("正在连接ZooKeeper"));
        while (zk.getState() != ZooKeeper.States.CONNECTED) {
            Thread.sleep(retryInterval);
            retryCount++;
            if (retryCount.equals(maxRetryCount)) {
                log.error("连接ZooKeeper失败，超过最大重试次数：{}次", maxRetryCount);
                break;
            }
        }
    }

    /**
     * 获取zk服务
     * @return zk实例
     */
    public ZooKeeper getZKConnection() {
        return zk;
    }
}
