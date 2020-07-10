package com.cttnet.zhwg.ywkt.mtosi.ability.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 *  网络路由
 * </pre>
 *
 * @author wangzhaoshi
 * @date 2020/2/18
 * @since java 1.8
 */
@AllArgsConstructor
public enum NetworkRouted {
    /** 网络路由枚举 */
    NR_NA(0, "NR_NA"),
    NR_NO(1, "NR_NO"),
    NR_YES(2, "NR_YES"),
    ;
    @Getter
    private int code;
    @Getter
    private String name;
}
