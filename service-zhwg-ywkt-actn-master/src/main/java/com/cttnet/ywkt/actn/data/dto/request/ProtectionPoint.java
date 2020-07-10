package com.cttnet.ywkt.actn.data.dto.request;

import com.cttnet.ywkt.actn.enums.ProtectionTypeEnum;
import com.cttnet.ywkt.actn.enums.SdFlagEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <pre>保护属性</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-20
 * @since jdk 1.8
 */
@ApiModel(description = "保护属性")
@Data
public class ProtectionPoint {

    /** 保护开关，控制整个protection数据结构的配置是否生效 */
    @ApiModelProperty(value = "保护开关")
    private Boolean enable;
    /** 是否分段保护 */
    @ApiModelProperty(value = "是否分段保护")
    private Boolean segmentProtect;
    /** sd使能标识 */
    @ApiModelProperty(value = "sd使能标识")
    private SdFlagEnum sdFlag;
    /** 保护类型 */
    @ApiModelProperty(value = "保护类型")
    private ProtectionTypeEnum protectionType;
    /** 主备是否可返回: 主用路径回复后自动倒换到主用路径 */
    @ApiModelProperty(value = "主备是否可返回")
    private Boolean reversionDisable;
    /** 备路径返回主路径前等待时间,单位：秒 300-720  默认600 */
    @ApiModelProperty(value = "备路径返回主路径前等待时间")
    private Integer waitToRevert;
    /** 保护倒换延迟时间，单位：毫秒 默认 10*/
    @ApiModelProperty(value = "保护倒换延迟时间")
    private Integer holdOffTime;
    /** 必经对象 */
    @ApiModelProperty(value = "必经对象")
    private List<PointDTO> includedRestrictedResources;
    /** 避让对象 */
    @ApiModelProperty(value = "避让对象")
    private List<PointDTO> excludedRestrictedResources;
}
