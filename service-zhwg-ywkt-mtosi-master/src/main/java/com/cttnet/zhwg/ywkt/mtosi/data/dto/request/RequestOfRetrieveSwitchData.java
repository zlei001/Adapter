package com.cttnet.zhwg.ywkt.mtosi.data.dto.request;

import com.cttnet.zhwg.ywkt.mtosi.data.dto.base.RequestHeader;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <pre>
 *  查询保护组倒换状态
 * </pre>
 *
 * @author dengkaihong
 * @date 2020/7/8
 * @since java 1.8
 */
@Data
public class RequestOfRetrieveSwitchData {
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
    }
}
