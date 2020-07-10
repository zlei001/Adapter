package com.cttnet.ywkt.actn.data.dto.request;

import com.cttnet.ywkt.actn.data.valid.CreateBusiness;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <pre>基础RequestDTO</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-20
 * @since jdk 1.8
 */
@Data
@ApiModel(description = "业务对象")
public class BasicSvcRequestDTO implements Serializable {
    private static final long serialVersionUID = -3158826571997682031L;

    @ApiModelProperty(value = "网管Id", required = true)
    @NotBlank
    private String emsId;

    @ApiModelProperty(value = "业务UUID", required = true)
    @NotBlank
    private String uuid;

    @ApiModelProperty(value = "业务名称", required = true)
    @NotBlank(groups = {CreateBusiness.class})
    private String sncName;

}
