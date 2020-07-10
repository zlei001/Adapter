package com.cttnet.zhwg.ywkt.mtosi.data.dto.request;

import com.cttnet.zhwg.ywkt.mtosi.data.dto.base.RequestHeader;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <pre>
 *  创建并激活子网连接request
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/2/13
 * @since java 1.8
 */
@Data
public class RequestOfCreateAndActiveSubnetWorkConnection implements Serializable {

    private static final long serialVersionUID = 706613708341078578L;

    @ApiModelProperty(value = "请求头部")
    private RequestHeader header;

    @Valid
    @NotNull
    @ApiModelProperty(value = "请求体", required = true)
    private SubnetWorkConnection subnetWorkConnection;

}
