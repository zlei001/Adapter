package com.cttnet.zhwg.ywkt.mtosi.ability.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 *  自定义错误响应
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/3/10
 * @since java 1.8
 */
@AllArgsConstructor
public enum CustomErrorResponse {

    /**
     * 超时
     */
    TIMEOUT("timeout!", "设备厂家接口超时，请联系厂家处理!"),
    /**
     * 连接拒绝
     */
    CONNREFUSED("connect refused!", "设备厂家拒绝连接，请联系厂家处理!"),
    /**
     * 存在被禁止参数
     */
    FORBIDDEN("have forbidden params!", "参数错误"),
    /**
     * 其他
     */
    OTHER("occur unknow error!", "")
    ;
    @Getter
    private String code;

    @Getter
    private String desc;

}
