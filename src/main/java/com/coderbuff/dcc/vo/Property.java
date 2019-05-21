package com.coderbuff.dcc.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Description:
 * 节点数据
 * 2019-05-17
 * Created with OKevin.
 */
@Setter
@Getter
@ToString
public class Property {
    /**
     * 配置名称
     */
    private String name;

    /**
     * key对应的所有配置值
     */
    private List<Config> config;

    /**
     * 节点描述说明
     */
    private String desc;

    /**
     * 创建时间
     */
    private Long created;
}
