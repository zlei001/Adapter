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
 *   查询网元上所有端口维护操作状态,request
 * </pre>
 *
 * @author dengkaihong
 * @date 2020/7/6
 * @since java 1.8
 */
@Data
public class RequestOfGetActiveMaintenanceOperations implements Serializable {

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
        @NotNull
        @ApiModelProperty(value = "Id类型")
        private int commonType;
    }
}
