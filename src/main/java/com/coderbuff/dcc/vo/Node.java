package com.coderbuff.dcc.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Description:
 * 节点VO
 * 2019-05-17
 * Created with OKevin.
 */
@Setter
@Getter
@ToString
public class Node {
    /**
     * 节点路径
     */
    private String path;

    /**
     * 节点数据
     */
    private String value;
}
