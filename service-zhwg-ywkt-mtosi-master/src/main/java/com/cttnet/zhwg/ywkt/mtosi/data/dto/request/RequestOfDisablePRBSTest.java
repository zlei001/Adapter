package com.cttnet.zhwg.ywkt.mtosi.data.dto.request;

import com.cttnet.zhwg.ywkt.mtosi.data.dto.base.RequestHeader;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <pre>
 *  关闭PRBS测试request
 * </pre>
 *
 * @author wangzhaoshi
 * @date 2020/2/27
 * @since java 1.8
 */
@Data
public class RequestOfDisablePRBSTest implements Serializable {

    private static final long serialVersionUID = 5981361045164465920L;

    @ApiModelProperty(value = "请求头部")
    private RequestHeader header;

    @Valid
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
