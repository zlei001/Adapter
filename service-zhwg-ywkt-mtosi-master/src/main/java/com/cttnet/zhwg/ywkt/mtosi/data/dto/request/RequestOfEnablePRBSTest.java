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
 *  启动PRBS测试request
 * </pre>
 *
 * @author wangzhaoshi
 * @date 2020/2/27
 * @since java 1.8
 */
@Data
public class RequestOfEnablePRBSTest implements Serializable {

    private static final long serialVersionUID = 4170483397296799304L;

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
        @ApiModelProperty(value = "方向")
        private String direction;
        @ApiModelProperty(value = "持续时间")
        private String testDuration;
        @ApiModelProperty(value = "采样周期")
        private String sampleGranularity;
        @ApiModelProperty(value = "是否累积错误")
        private String accumulatingIndicator;
        @ApiModelProperty(value = "类型")
        private String prbsType;
    }
}
