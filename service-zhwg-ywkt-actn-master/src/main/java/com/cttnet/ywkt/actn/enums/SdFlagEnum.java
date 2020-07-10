package com.cttnet.ywkt.actn.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>Sd使能、禁用； 使能时业务信号劣化时会触发倒换</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-20
 * @since jdk 1.8
 */
@AllArgsConstructor
public enum SdFlagEnum {

    /** 使能 */
    ENABLE("enable"),
    /** 禁止 */
    DISABLE("disable")
    ;

    @Getter
    private final String value;

}
