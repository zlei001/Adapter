package com.cttnet.zhwg.ywkt.mtosi.ability.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 *  保护等级
 * </pre>
 *
 * @author wangzhaoshi
 * @date 2020/2/18
 * @since java 1.8
 */
@AllArgsConstructor
public enum StaticProtectionLevel {

    /** 保护等级枚举 **/
    MINOR_EXT("MINOR_EXT", "MINOR_EXT", 0),
    PREEMPTIBLE("PREEMPTIBLE", "PREEMPTIBLE", 1),
    UNPROTECTED("UNPROTECTED", "UNPROTECTED", 2),
    PARTIALLY_PROTECTED("PARTIALLY_PROTECTED", "PARTIALLY_PROTECTED", 3),
    FULLY_PROTECTED("FULLY_PROTECTED", "FULLY_PROTECTED", 4),
    HIGHLY_PROTECTED("HIGHLY_PROTECTED", "HIGHLY_PROTECTED", 5);

    ;
    @Getter
    private String desc;
    @Getter
    private String name;
    @Getter
    private int code;
}
