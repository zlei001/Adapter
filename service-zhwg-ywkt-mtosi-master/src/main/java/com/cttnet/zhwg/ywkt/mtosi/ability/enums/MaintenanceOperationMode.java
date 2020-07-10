package com.cttnet.zhwg.ywkt.mtosi.ability.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author dengkaihong
 * @date 2020/4/3
 * @since java 1.8
 */
@AllArgsConstructor
public enum MaintenanceOperationMode {

    //设置环回
    MOM_OPERATE("MOM_OPERATE",0),
    //撤销环回
    MOM_RELEASE("MOM_RELEASE",1)
    ;
    @Getter
    private String name;
    @Getter
    private int code;
}
