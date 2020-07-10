package com.cttnet.zhwg.ywkt.mtosi.ability.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 *  布尔枚举
 * </pre>
 *
 * @author wangzhaoshi
 * @date 2020/2/18
 * @since java 1.8
 */
@AllArgsConstructor
public enum IsBoolean {
    /** 失败 */
    FALSE(0,"false"),
    /** 成功 */
    TRUE(1,"true");
    @Getter
    private int code;
    @Getter
    private String name;
}
