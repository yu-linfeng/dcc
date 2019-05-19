package com.coderbuff.dcc.vo;

import com.alibaba.fastjson.JSON;
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
     * 节点创建时间
     */
    private Long created;

    /**
     * 节点数据
     */
    private JSON value;


    public Node() {
    }

    public Node(String path) {
        this.path = path;
    }
}
