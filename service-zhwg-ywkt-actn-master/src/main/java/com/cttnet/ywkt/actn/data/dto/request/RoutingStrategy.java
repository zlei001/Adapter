package com.cttnet.ywkt.actn.data.dto.request;

import com.cttnet.ywkt.actn.enums.MetricTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>Description: 算路策略</p>
 *
 * @author suguisen
 */
@Data
public class RoutingStrategy implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "算路策略最小")
    private MetricTypeEnum minRoutingStrategy ;

    @ApiModelProperty(value = "算路策略门限")
    private MetricTypeEnum maxRoutingStrategy ;

    @ApiModelProperty(value = "门限值")
    private Long maxNum;

}
