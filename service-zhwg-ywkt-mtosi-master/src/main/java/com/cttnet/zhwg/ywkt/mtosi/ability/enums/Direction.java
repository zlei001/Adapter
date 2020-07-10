package com.cttnet.zhwg.ywkt.mtosi.ability.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 *  方向
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/2/15
 * @since java 1.8
 */
@AllArgsConstructor
public enum Direction {
    /** 单向 */
    CD_UNI(2, "CD_UNI"),
    /** 双向 */
    CD_BI(1, "CD_BI"),
    ;
    @Getter
    private int code;
    @Getter
    private String name;

}
