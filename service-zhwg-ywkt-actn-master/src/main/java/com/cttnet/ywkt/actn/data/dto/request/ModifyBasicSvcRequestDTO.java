package com.cttnet.ywkt.actn.data.dto.request;

import com.cttnet.ywkt.actn.data.valid.ModifyBandwidthBusiness;
import com.cttnet.ywkt.actn.data.valid.ModifyFlowSwitchBusiness;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>Description: 调整业务请求DTO </p>
 * @author suguisen
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "调整业务请求DTO", parent = BasicSvcRequestDTO.class)
public class ModifyBasicSvcRequestDTO extends BasicSvcRequestDTO implements Serializable {

    private static final long serialVersionUID = -7369670730935756546L;

    @NotNull(groups = {ModifyBandwidthBusiness.class})
    @ApiModelProperty(value = "带宽")
    private Long bandwidth;

    @NotNull(groups = {ModifyFlowSwitchBusiness.class})
    @ApiModelProperty(value = "流量开关")
    private Boolean flowSwitch;

}
