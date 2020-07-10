package com.cttnet.zhwg.ywkt.mtosi.ability.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 设置端口公共属性
 *  端口，网元 ，通道，CTP
 * @author dengkaihong
 * @since 2020/4/27
 */
@AllArgsConstructor
public enum SetCommonType {

    /** 传端口ID */
    PORT_ID(1),
    /** 传通道ID */
    CHANNEL(2),
    /** 传CtpID */
    CTP_ID(3),
    /** 传网元ID */
    NE_ID(4)
    ;
    @Getter
    private int value;

}
