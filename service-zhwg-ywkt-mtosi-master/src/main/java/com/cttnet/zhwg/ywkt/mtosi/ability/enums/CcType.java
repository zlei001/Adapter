package com.cttnet.zhwg.ywkt.mtosi.ability.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 *  交叉连接类型
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/2/15
 * @since java 1.8
 */
@AllArgsConstructor
public enum CcType {
    /** 交叉连接类型枚举 */
    ST_SIMPLE(0, "ST_SIMPLE"),
    ST_ADD_DROP_A(1, "ST_ADD_DROP_A"),
    ST_ADD_DROP_Z(2, "ST_ADD_DROP_Z"),
    ST_INTERCONNECT(3, "ST_INTERCONNECT"),
    ST_DOUBLE_INTERCONNECT(4, "ST_DOUBLE_INTERCONNECT"),
    ST_DOUBLE_ADD_DROP(5, "ST_DOUBLE_ADD_DROP"),
    ST_OPEN_ADD_DROP(6, "ST_OPEN_ADD_DROP"),
    ST_EXPLICIT(7, "ST_EXPLICIT")
    ;
    @Getter
    private int code;
    @Getter
    private String name;

}
