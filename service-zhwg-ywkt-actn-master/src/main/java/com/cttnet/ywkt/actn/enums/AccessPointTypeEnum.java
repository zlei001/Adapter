package com.cttnet.ywkt.actn.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>Description: 接口类型</p>
 * @author suguisen
 *
 */
@AllArgsConstructor
public enum AccessPointTypeEnum {

    /** 端口接口类型 */
    //FIXME SDH业务暂时不支持
    E1(1, "ietf-trans-client-svc-types:electronic-e1"),

    OPTICAL_FE(3, "ietf-trans-client-svc-types:optical-fe"),
    ELECTRONIC_FE(3, "ietf-trans-client-svc-types:electronic-fe"),
    OPTICAL_GE(6, "ietf-trans-client-svc-types:optical-ge"),
    ELECTRONIC_GE(6, "ietf-trans-client-svc-types:electronic-ge"),

    OPTICAL_10GE(7,"ietf-trans-client-svc-types:optical-10ge"),
    OPTICAL_10GE_LAN(7,"ietf-trans-client-svc-types:optical-10ge"),
    OPTICAL_10GE_WAN(7,"ietf-trans-client-svc-types:optical-10ge"),
    OPTICAL_100GE(8, "ietf-trans-client-svc-types:optical-100ge"),

    //业务类型式接口类型
    //FIXME SDH业务暂时不支持
    STM1(2, "ietf-trans-client-svc-types:optical-ge"),

    STM4(4, "ietf-trans-client-svc-types:optical-ge"),
    STM16(5, "ietf-trans-client-svc-types:optical-10ge"),
    STM64(4, "ietf-trans-client-svc-types:optical-10ge"),
    OTU2(4, "ietf-trans-client-svc-types:optical-10ge"),
    OTU4(8, "ietf-trans-client-svc-types:optical-100ge"),

    ;

    //一对多时, 使用相对小的类型作为带宽判断
    @Getter
    private final int weight;
    @Getter
    private final String pointType;

    /**
     * 获取接口类型
     * @param pointTypeStr pointTypeStr
     * @return AccessPointTypeEnum
     */
    public static AccessPointTypeEnum getAccessPointType(String pointTypeStr ) {
        AccessPointTypeEnum accessPointType = null;
    	for(AccessPointTypeEnum pointType: AccessPointTypeEnum.values()  ) {
    		if( pointType.toString().equals(pointTypeStr) ) {
    			accessPointType = pointType;
    		}

    	}
    	return accessPointType;
    }

}
