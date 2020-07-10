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
 *  查询OTN业务时延request
 * </pre>
 *
 * @author dengkaihong
 * @date 2020/4/7
 * @since java 1.8
 */
@Data
public class RequestOfGetRoundTripDelayResult implements Serializable {

    private static final long serialVersionUID = 5981361045164465920L;

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
        @ApiModelProperty(value = "")
        private String tpId;
        @NotBlank
        @ApiModelProperty(value = "")
        private String type;
    }
}
