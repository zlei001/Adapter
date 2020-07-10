package com.cttnet.zhwg.ywkt.mtosi.ability.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 *  保护类型
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/2/15
 * @since java 1.8
 */
@AllArgsConstructor
public enum ProtectionType {

    /** 保护类型枚举 **/
    UNKNOWN("未知", "未知", -1),
    MINOR_EXT("MINOR_EXT", "MINOR_EXT", 1),
    MSP_1_PLUS_1("MSP_1_PLUS_1", "MSP_1_PLUS_1", 2),
    MSP_1_FOR_N("MSP_1_FOR_N", "MSP_1_FOR_N", 3),
    _2_FIBER_BLSR("2_FIBER_BLSR", "2_FIBER_BLSR", 4),
    _4_FIBER_BLSR("4_FIBER_BLSR", "4_FIBER_BLSR", 5),
    _1_PLUS_1("1_PLUS_1", "1_PLUS_1", 6);

    ;
    @Getter
    private String desc;
    @Getter
    private String name;
    @Getter
    private int code;
}
