package com.cttnet.zhwg.ywkt.mtosi.data.dto.request;

import com.cttnet.zhwg.ywkt.mtosi.data.dto.base.RequestHeader;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *  路径搜索request
 * </pre>
 *
 * @author wangzhaoshi
 * @date 2020/2/25
 * @since java 1.8
 */
@Data
public class RequestOfTakeOverSNC implements Serializable {

    private static final long serialVersionUID = 4472039274004787591L;

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
        private List<String> neIds;
    }
}
