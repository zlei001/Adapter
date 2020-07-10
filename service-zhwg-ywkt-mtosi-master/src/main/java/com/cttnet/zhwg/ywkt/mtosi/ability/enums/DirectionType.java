package com.cttnet.zhwg.ywkt.mtosi.ability.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 *  方向类型
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/1/11
 * @since java 1.8
 */
@AllArgsConstructor
public enum DirectionType {

    /**源端*/
    SOURCE("direction=src"),
    /**宿端*/
    SINK("direction=sink")
    ;
    @Getter
    private String code;
}
