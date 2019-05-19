package com.coderbuff.dcc.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
     * 配置key（实际是path）
     */
    private String key;

    /**
     * 配置值
     */
    private String value;

    /**
     * 节点描述说明
     */
    private String desc;
}
