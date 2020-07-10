package com.cttnet.ywkt.actn.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonCreator;

/**
 * <pre>网元类型</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-20
 * @since jdk 1.8
 */
@AllArgsConstructor
public enum NeTypeEnum {

    /** CPE */
    CPE(1,"CPE"),
    /** OTN */
    CO(2,"CO")
    ;
    @Getter
    private final int value;
    @Getter
    private final String name;

    @JsonCreator
    public static NeTypeEnum of(int value){
        for (NeTypeEnum neTypeEnum : NeTypeEnum.values()) {
            if(neTypeEnum.value == value){
                return neTypeEnum;
            }
        }
        return null;
    }

}
