package com.coderbuff.dcc.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Description:
 * 节点数据
 * [{
 *     name: "switch",
 *     value: [{
 *         "name": "isEnableDb",
 *         "value": false,
 *         "desc":"是否降级到数据库"
 *     },{
 *         "name": "xxIsEnableCache",
 *         "value": true,
 *         "desc":"xx数据是否读缓存"
 *     }],
 *     desc:"开关"
 * }, {
 *     name: "config",
 *     value: [{
 *
 *     },{
 *
 *     }],
 *     desc:"配置信息"
 * }]
 * 2019-05-17
 * Created with OKevin.
 */
@Setter
@Getter
@ToString
public class Property {
    /**
     * 节点path
     */
    private String path;

    /**
     * 配置名称
     */
    private String name;

    /**
     * key对应的所有配置值
     */
    private List<Config> value;

    /**
     * 节点描述说明
     */
    private String desc;
}
