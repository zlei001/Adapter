package com.cttnet.ywkt.actn.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>Vc承载类型</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-20
 * @since jdk 1.8
 */
@AllArgsConstructor
public enum VcTypeEnum {
    /** VC*/
    VC12("ietf-sdh-types:sdh-vc12"),
    VC3("ietf-sdh-types:sdh-vc3"),
    VC4("ietf-sdh-types:sdh-vc4")
    ;
    @Getter
    private final String value;
}
