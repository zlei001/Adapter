package com.cttnet.ywkt.actn.enums;

import lombok.Getter;

/**
 * <p>Description: actn端口业务类型 </p>
 * @author suguisen
 *
 */
public enum ClientSignalEnum {

    _GE("GE", "ietf-otn-types:client-signal-1GbE-GFP-T"),
    _10GE_LAN("10GE_LAN", "ietf-otn-types:client-signal-10GbE-LAN"),
    _10GE_WAN("10GE_WAN", "ietf-otn-types:client-signal-10GbE-WAN"),
    _40GE("40GE", "ietf-otn-types:client-signal-40GbE"),
    _100GE("100GE", "ietf-otn-types:client-signal-100GbE"),
    _STM1("STM-1", "ietf-otn-types:client-signal-OC3_STM1"),
    _STM4("STM-4", "ietf-otn-types:client-signal-OC12_STM4"),
    _STM16("STM-16", "ietf-otn-types:client-signal-OC48_STM16"),
    _STM64("STM-64", "ietf-otn-types:client-signal-OC192_STM64"),
    _STM256("STM-256", "ietf-otn-types:client-signal-OC768_STM256"),
    _ODU0("ODU0", "ietf-otn-types:client-signal-ODU0"),
    _ODU1("ODU1", "ietf-otn-types:client-signal-ODU1"),
    _ODU2("ODU2", "ietf-otn-types:client-signal-ODU2"),
    _ODU2e("ODU2e", "ietf-otn-types:client-signal-ODU2e"),
    _ODU3("ODU3", "ietf-otn-types:client-signal-ODU3"),
    _ODU3e2("ODU3e2", "ietf-otn-types:client-signal-ODU3e2"),
    _ODU4("ODU4", "ietf-otn-types:client-signal-ODU4"),
    _ODUFlex_cbr("ODUFlex_cbr", "ietf-otn-types:client-signal-ODUFlex-cbr"),
    _ODUFlex_gfp("ODUFlex_gfp", "ietf-otn-types:client-signal-ODUFlex-gfp"),
    _ODUCn("ODUCn", "ietf-otn-types:client-signal-ODUCn"),
    _FC400("FC400", "ietf-otn-types:client-signal-FC400"),
    _FC800("FC800", "ietf-otn-types:client-signal-FC800"),
    _FICON_4G("FICON_4G", "ietf-otn-types:client-signal-FICON-4G"),
    _FICON_8G("FICON_8G", "ietf-otn-types:client-signal-FICON-8G"),

    _TEST("FICON_1G", "ietf-otn-types:client-signal-1GbE-TTT-GMP")

    ;
    @Getter
    private String serviceType;
    @Getter
    private String signal;
    ClientSignalEnum(String serviceType, String signal) {

        this.serviceType = serviceType;
        this.signal = signal;
    }



}
