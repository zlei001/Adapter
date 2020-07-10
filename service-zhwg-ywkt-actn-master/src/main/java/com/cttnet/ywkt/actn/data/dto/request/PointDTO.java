package com.cttnet.ywkt.actn.data.dto.request;

import com.cttnet.ywkt.actn.enums.AccessPointTypeEnum;
import com.cttnet.ywkt.actn.enums.NeTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <pre>网元节点对象</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-20
 * @since jdk 1.8
 */
@Data
@ApiModel(description = "Point对象")
public class PointDTO implements Serializable {

    private static final long serialVersionUID = -7939171013945057696L;

    @NotBlank
    @ApiModelProperty(value = "网元")
    private String neId;

    @ApiModelProperty(value = "端口")
    private String portId;

    @ApiModelProperty(value = "端口类型")
    private AccessPointTypeEnum accessPointType;

    /**
     * 1：不需要延展 2：需要延展
     */
    @NotNull(message="不能为空")
    @ApiModelProperty(value = "延展类型")
    private NeTypeEnum extensionType;

    @ApiModelProperty(value = "vlan")
    private Integer vlan;

}
