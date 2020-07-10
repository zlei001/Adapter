package com.cttnet.zhwg.ywkt.mtosi.data.dto.request;

import com.cttnet.zhwg.ywkt.mtosi.data.dto.base.RequestHeader;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

/**
 * <pre>
 *  创建保护组请求
 * </pre>
 *
 * @author dengkaihong
 * @date 2020/4/14
 * @since java 1.8
 */
@Data
public class RequestOfCreateProtectionGroup implements Serializable {


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
            private String userLabel;
            private String protectedPortId;
            private String protectingPortId;
            private String layerRate;
            private String type;
            private String reversionMode;
            private String vendorExtensions;
            private Map<String, Object> parameter;
        }
}

