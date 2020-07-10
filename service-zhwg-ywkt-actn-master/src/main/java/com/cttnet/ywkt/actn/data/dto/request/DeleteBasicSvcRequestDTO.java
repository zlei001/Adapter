package com.cttnet.ywkt.actn.data.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * <p>Description: 删除业务请求DTO</p>
 * @author suguisen
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "删除业务请求DTO", parent = BasicSvcRequestDTO.class)
public class DeleteBasicSvcRequestDTO extends BasicSvcRequestDTO implements Serializable {

    private static final long serialVersionUID = -7369670730935756546L;

}
