package com.cttnet.zhwg.ywkt.mtosi.ability.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 *  容忍影响
 * </pre>
 *
 * @author wangzhaoshi
 * @date 2020/2/18
 * @since java 1.8
 */
@AllArgsConstructor
public enum TolerableImpact {
    /**  */
    GOI_HITLESS(0, "GOI_HITLESS"),
    /** 次要 */
    GOI_MINOR_IMPACT(1, "GOI_MINOR_IMPACT"),
    /** 主要 */
    GOI_MAJOR_IMPACT(2, "GOI_MAJOR_IMPACT"),
    ;
    @Getter
    private int code;
    @Getter
    private String name;
}
