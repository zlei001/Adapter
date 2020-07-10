package com.cttnet.zhwg.ywkt.mtosi.ability.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 *  是否激活成功
 * </pre>
 *
 * @author wangzhaoshi
 * @date 2020/2/18
 * @since java 1.8
 */
@AllArgsConstructor
public enum IsActive {
    /** 失败 */
    FALSE(0,"false"),
    /** 成功 */
    TRUE(1,"true");
    @Getter
    private int code;
    @Getter
    private String name;
}
