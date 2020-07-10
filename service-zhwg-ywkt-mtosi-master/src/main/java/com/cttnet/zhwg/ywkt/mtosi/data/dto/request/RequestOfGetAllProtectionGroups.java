package com.cttnet.zhwg.ywkt.mtosi.data.dto.request;

import com.cttnet.zhwg.ywkt.mtosi.data.dto.base.RequestHeader;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <pre>
 *  查询所有保护组请求
 * </pre>
 *
 * @author dengkaihong
 * @date 2020/4/14
 * @since java 1.8
 */
@Data
public class RequestOfGetAllProtectionGroups {

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
        @ApiModelProperty(value = "版本号")
        private String tpId;

    }
}
