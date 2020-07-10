package com.cttnet.zhwg.ywkt.mtosi.data.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *  子网连接
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/2/13
 * @since java 1.8
 */
@Data
public class SubnetWorkConnection implements Serializable {

    @NotBlank
    @ApiModelProperty(value = "网管ID", required = true)
    private String emsId;
    @ApiModelProperty(value = "方向", required = true, allowableValues="1,2")
    private Integer direction;
    @ApiModelProperty(value = "交叉类型", required = true, allowableValues="1,2")
    private Integer ccType;
    @ApiModelProperty(value = "", required = true, allowableValues="1,2")
    private String aliasNameList;
    @ApiModelProperty(value = "", required = true, allowableValues="1,2")
    private String userLabel;
    @ApiModelProperty(value = "", required = true, allowableValues="1,2")
    private String layerRate;
    @ApiModelProperty(value = "源端支路口", required = true)
    private List<String> sourceEndRefIdList;
    @ApiModelProperty(value = "宿端支路口", required = true)
    private List<String> destEndRefIdList;
    @ApiModelProperty(value = "A端对象时隙", required = true)
    private List<String> sourceEndRefTimeSlot;
    @ApiModelProperty(value = "Z端对象时隙", required = true)
    private List<String> destEndRefTimeSlot;
    @ApiModelProperty(value = "业务类型", required = true)
    private String clientServiceType;
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
    @ApiModelProperty(value = "")
    private String sncpNodeList;

    @ApiModelProperty(value = "主用路由交叉源宿端ctpId数据", required = true)
    private List<CcPath> majorCcPath;
    @ApiModelProperty(value = "备用路由交叉源宿端ctpId数据", required = true)
    private List<CcPath> backupCcPath;

    @ApiModelProperty(value = "子网连接保护级别")
    private String staticProtectionLevel;
    @ApiModelProperty(value = "网络路由标识")
    private String networkRouted;
    @ApiModelProperty(value = "重路由标识")
    private String rerouteAllowed;
    @ApiModelProperty(value = "全路由标识")
    private String isFullRoute;
    @ApiModelProperty(value = "全路由标识")
    private String isForceUniqueness;
    @ApiModelProperty(value = "非全路由方式下发时指定必经资源", required = true)
    private List<String> inclusionResourceRefList;
    @ApiModelProperty(value = "非全路由方式下发时指定避让资源", required = true)
    private List<String> exclusionResourceRefList;

}
