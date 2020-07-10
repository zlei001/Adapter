package com.cttnet.ywkt.actn.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 * 保护类型枚举
 * </pre>
 *
 * @author zhangyaomin
 * @version 1.0
 * @since jdk 1.8
 */
@AllArgsConstructor
public enum ProtectionTypeEnum {

    /** 无保护 */
    UNPROTECTED("ietf-te-types:lsp-protection-unprotected"),
    /** 1+1 */
    ONE_PLUS_ONE("ietf-te-types:lsp-protection-bidir-1-to-1")
    ;
    @Getter
    private final String value;
}
