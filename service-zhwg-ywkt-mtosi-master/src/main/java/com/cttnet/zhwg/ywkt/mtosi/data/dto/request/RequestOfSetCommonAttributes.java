package com.cttnet.zhwg.ywkt.mtosi.data.dto.request;

import com.cttnet.zhwg.ywkt.mtosi.data.dto.base.RequestHeader;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <pre>
 *  设置公共属性request
 * </pre>
 *
 * @author zhangyaomin
 * @date 2019/12/28
 * @since java 1.8
 */
@Data
public class RequestOfSetCommonAttributes implements Serializable {

    private static final long serialVersionUID = 525664531639094591L;

    @ApiModelProperty(value = "请求头部")
    private RequestHeader header;

    @Valid
    @NotNull
    @ApiModelProperty(value = "请求体", required = true)
    private RequestBody body;

    @Data
    public static class RequestBody {
        @NotBlank
        @ApiModelProperty(value = "网管ID", required = true)
        private String emsId;
        @NotBlank
        @ApiModelProperty(value = "版本号")
        private String objectId;
        @ApiModelProperty(value = "")
        private String aliasNameList;
        @ApiModelProperty(value = "通道名称")
        private String userLabel;
        @NotNull
        @ApiModelProperty(value = "Id类型")
        private int commonType;
    }

}
