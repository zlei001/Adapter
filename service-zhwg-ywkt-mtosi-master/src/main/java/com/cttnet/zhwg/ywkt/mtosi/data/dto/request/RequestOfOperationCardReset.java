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
 *  单板复位request
 * </pre>
 *
 * @author dengkaihong
 * @date 2020/7/3
 * @since java 1.8
 */
@Data
public class RequestOfOperationCardReset implements Serializable {

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
        private String tpId;
        @NotBlank
        @ApiModelProperty(value = "操作")
        private String maintenanceOperationMode;
        @NotBlank
        @ApiModelProperty(value = "类型")
        private String maintenanceOperation;
        @NotBlank
        @ApiModelProperty(value = "传输速率")
        private String layerRate;
    }
}
