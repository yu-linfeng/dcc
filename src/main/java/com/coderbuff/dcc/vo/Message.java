package com.coderbuff.dcc.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 * <p>
 * 2019-05-17
 * Created with OKevin.
 */
@Setter
@Getter
public class Message<T>{
    /**
     * 错误码
     */
    Integer code;

    /**
     * 错误信息
     */
    String msg;

    /**
     * 数据
     */
    T data;

    /**
     * 默认失败信息
     * @return 失败
     */
    public static Message failure() {
        Message<String> message = new Message<>();
        message.code = -1;
        message.msg = "失败";
        message.data = "";
        return message;
    }

    /**
     * 默认成功信息
     * @return 成功
     */
    public static Message success() {
        Message<String> message = new Message<>();
        message.code = 0;
        message.msg = "成功";
        message.data = "";
        return message;
    }
}
