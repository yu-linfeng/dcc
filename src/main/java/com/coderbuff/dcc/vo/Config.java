package com.coderbuff.dcc.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Description:
 * 节点中的数据配置项
 * 2019-05-21
 * Created with OKevin.
 */
@Setter
@Getter
@ToString
public class Config {
    /**
     * 配置名称
     */
    private String name;

    /**
     * 值
     */
    private Object value;

    /**
     * 描述
     */
    private String desc;
}
