package com.cttnet.zhwg.ywkt.mtosi.data.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *  交叉连接
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/1/2
 * @since java 1.8
 */
@Data
public class CrossConnection implements Serializable {

    private static final long serialVersionUID = 5907968921099593010L;

    @NotBlank
    @ApiModelProperty(value = "网管ID", required = true)
    private String emsId;
    @ApiModelProperty(value = "方向", required = true, allowableValues="1,2")
    private Integer direction;
    @ApiModelProperty(value = "交叉类型", required = true, allowableValues="1,2")
    private Integer ccType;
    @NotEmpty
    @ApiModelProperty(value = "A端对象", required = true)
    private List<String> sourceEndRefIdList;
    @NotEmpty
    @ApiModelProperty(value = "Z端对象", required = true)
    private List<String> destEndRefIdList;
    @NotEmpty
    @ApiModelProperty(value = "A端对象时隙", required = true)
    private List<String> sourceEndRefTimeSlot;
    @NotEmpty
    @ApiModelProperty(value = "Z端对象时隙", required = true)
    private List<String> destEndRefTimeSlot;
    @NotBlank
    @ApiModelProperty(value = "业务类型", required = true)
    private String clientServiceType;
    @NotBlank
    @ApiModelProperty(value = "容器类型", required = true)
    private String clientServiceContainer;

    @ApiModelProperty(value = "映射模式", required = true)
    private String clientServiceMappingMode;
    @ApiModelProperty(value = "holdOffTime")
    private String holdOffTime;
    @ApiModelProperty(value = "wtrTime")
    private String wtrTime;
    @ApiModelProperty(value = "监控类型")
    private String monitorType;
    @ApiModelProperty(value = "reversionMode")
    private String reversionMode;
    @ApiModelProperty(value = "保护类型")
    private String protectionType;
}
