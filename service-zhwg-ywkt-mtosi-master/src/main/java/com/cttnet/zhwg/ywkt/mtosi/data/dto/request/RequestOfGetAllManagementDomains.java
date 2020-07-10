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
 *  查询所有管理域request
 * </pre>
 *
 * @author wangzhaoshi
 * @date 2020/2/25
 * @since java 1.8
 */
public class RequestOfGetAllManagementDomains implements Serializable {

    private static final long serialVersionUID = -6369732585841479207L;

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
    }
}
