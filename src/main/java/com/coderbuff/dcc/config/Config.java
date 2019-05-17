package com.coderbuff.dcc.config;

import com.coderbuff.dcc.util.ZooKeeperConn;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 * Bean加载
 * 2019-05-17
 * Created with OKevin.
 */
@Configuration
public class Config {

    @Bean
    public ZooKeeperConn getZooKeeper() {
        return new ZooKeeperConn();
    }
}
