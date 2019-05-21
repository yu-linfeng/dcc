package com.coderbuff.dcc.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Description:
 * 节点VO，在zk中的结构，path=/test
 * [{
 *     created: 111111,
 *     properties: [{
 *         name: "a",
 *         config: [{
 *             name: "b",
 *             value: "c",
 *             desc:"d"
 *         }],
 *         desc: "b"
 *     }]
 * }]
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
    private List<Property> properties;


    public Node() {
    }

    public Node(String path) {
        this.path = path;
    }

    public String strValue() {
        JSONObject json = new JSONObject();
        json.put("properties", JSON.toJSONString(properties));
        return json.toString();
    }
}
