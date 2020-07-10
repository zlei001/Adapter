package com.cttnet.ywkt.actn.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>Description: 算路策略枚举</p>
 *
 * @author suguisen
 */
@AllArgsConstructor
public enum MetricTypeEnum {


    /** 综合最优*/
    METRIC_TE("ietf-te-types:path-metric-te"),
    /** 跳数*/
    METRIC_HOP("ietf-te-types:path-metric-hop"),
    /** 时延*/
    DELAY_AVERAGE("ietf-te-types:path-metric-delay-average")
    ;

    @Getter
    private final String value;

}
