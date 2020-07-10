package com.cttnet.zhwg.ywkt.mtosi.ability.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 *  网管自由度
 * </pre>
 *
 * @author wangzhaoshi
 * @date 2020/2/18
 * @since java 1.8
 */
@AllArgsConstructor
public enum OsFreedomLevel {
    /** 网管自由度枚举 */
    EMSFL_CC_AT_SNC_LAYER(0, "EMSFL_CC_AT_SNC_LAYER"),
    EMSFL_TERMINATE_AND_MAP(1, "EMSFL_TERMINATE_AND_MAP"),
    EMSFL_HIGHER_ORDER_SNCS(2, "EMSFL_HIGHER_ORDER_SNCS"),
    EMSFL_RECONFIGURATION(3, "EMSFL_RECONFIGURATION"),
    ;
    @Getter
    private int code;
    @Getter
    private String name;
}
