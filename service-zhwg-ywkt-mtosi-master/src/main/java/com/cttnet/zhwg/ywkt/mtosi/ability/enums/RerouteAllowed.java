package com.cttnet.zhwg.ywkt.mtosi.ability.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 *  重新路由允许
 * </pre>
 *
 * @author wangzhaoshi
 * @date 2020/2/18
 * @since java 1.8
 */
@AllArgsConstructor
public enum RerouteAllowed {
    /** 重新路由允许枚举 */
    RR_NA(0, "RR_NA"),
    RR_NO(1, "RR_NO"),
    RR_YES(2, "RR_YES"),
    ;
    @Getter
    private int code;
    @Getter
    private String name;

}
