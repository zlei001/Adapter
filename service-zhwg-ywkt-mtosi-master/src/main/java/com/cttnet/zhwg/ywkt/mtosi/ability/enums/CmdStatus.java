package com.cttnet.zhwg.ywkt.mtosi.ability.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 *  Cmd命令返回状态
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/3/10
 * @since java 1.8
 */
@AllArgsConstructor
public enum CmdStatus {

    /** Cmd命令返回状态枚举 */
    RS_SUCCESS (0, "成功  "),

    RS_INVALID_FORMAT (1, "格式错误"),

    RS_CMD_NOACCEPT (2, "命令不支持"),

    RS_INVALID_PARA (3, "参数错误"),

    RS_INFC_ERROR (4, "接口错误"),

    RS_PERMISSION_LIMIT (5, "授权限制,拒绝执行"),

    RS_TIME_OUT (6, "指令超时"),

    RS_IF_RETURN_ERROR (7, "接口返回错误"),

    RS_LOGIN_TIMEOUT (8, "登录超时"),

    RS_LOGIN_FALSE (9, "登录失败"),

    RS_LOGIN_NULL (10, "用户没有登录"),

    RS_IF_RETURN_DATA_ERROR (11, "接口返回数据解析失败"),

    RS_IF_CONNREFUSED (12, "接口拒绝连接"),

    RS_ROLLBACK_SUCCESS (13, "回滚成功"),

    RS_ROLLBACK_FAIL (14, "回滚失败"),

    RS_OHTER (99, "其他"),

    ;

    /**
     * 异常编号
     */
    @Getter
    private int code;

    /**
     * 异常描述
     */
    @Getter
    private String desc;
}
