package com.cttnet.ywkt.actn.data.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * <pre>创建业务请求DTO</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-20
 * @since jdk 1.8
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "创建业务请求DTO", parent = BasicSvcRequestDTO.class)
public class CreateBasicSvcRequestDTO extends BasicSvcRequestDTO implements Serializable {

    private static final long serialVersionUID = -7369670730935756546L;

    @NotNull
    @ApiModelProperty(value = "带宽", required = true)
    private Long bandwidth;

    @NotNull
    @ApiModelProperty(value = "源端", required = true)
    private PointDTO sourcePoint;

    @NotNull
    @ApiModelProperty(value = "宿端", required = true)
    private PointDTO destPoint;

    @ApiModelProperty(value = "保护属性")
    private ProtectionPoint protection;

    @ApiModelProperty(value = "必经对象")
    private List<PointDTO> includedRestrictedResources;

    @ApiModelProperty(value = "避让对象")
    private List<PointDTO> excludedRestrictedResources;

    @ApiModelProperty(value = "电路可用率等级")
    private String availableRatioGrade;

    @ApiModelProperty(value = "算路策略")
    private RoutingStrategy routingStrategy;


}
