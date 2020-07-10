package com.cttnet.zhwg.ywkt.mtosi.data.dto.request;

import com.cttnet.zhwg.ywkt.mtosi.data.dto.base.RequestHeader;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *  修改端口属性request
 * </pre>
 *
 * @author wangzhaoshi
 * @date 2020/2/24
 * @since java 1.8
 */
@Data
public class RequestOfSetTerminationPointData implements Serializable {

    private static final long serialVersionUID = 85064548044534278L;

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

    public List<TransmissionParameters> transmissionParametersList;
}
