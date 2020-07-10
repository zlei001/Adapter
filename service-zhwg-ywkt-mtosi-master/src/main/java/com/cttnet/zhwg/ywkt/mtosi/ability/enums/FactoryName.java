package com.cttnet.zhwg.ywkt.mtosi.ability.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 *  厂家名称
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/2/15
 * @since java 1.8
 */
@AllArgsConstructor
public enum  FactoryName {

    /** 厂家名称 */
    HW("HW", "华为"),
    ZTE("ZTE", "中兴"),
    FH("FH", "烽火"),
    AL("AL", "贝尔"),
    UNKNOW("UNKNOW", "未知")
    ;
    @Getter
    private String code;
    @Getter
    private String name;

}
