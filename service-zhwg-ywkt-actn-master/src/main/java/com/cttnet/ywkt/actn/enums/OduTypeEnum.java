package com.cttnet.ywkt.actn.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 *
 * </pre>
 * <b>项目：</b> & <b>技术支持：凯通科技股份有限公司 (c) 2019 </b>
 *
 * @author zhangyaomin
 * @version 1.0
 * @since jdk 1.8
 */
@AllArgsConstructor
public enum OduTypeEnum {

    /** ODU承载类型 */
    ODU0("ietf-otn-types:prot-ODU0"),
    ODU1("ietf-otn-types:prot-ODU1"),
    ODU1E("ietf-otn-types:prot-ODU1e"),
    ODU2("ietf-otn-types:prot-ODU2"),
    ODU2E("ietf-otn-types:prot-ODU2e"),
    ODU3("ietf-otn-types:prot-ODU3"),
    ODU4("ietf-otn-types:prot-ODU4"),
    ODU_FLEX("ietf-otn-types:prot-ODUFlex-gfp"),
    ;

    @Getter
    private final String value;
}
