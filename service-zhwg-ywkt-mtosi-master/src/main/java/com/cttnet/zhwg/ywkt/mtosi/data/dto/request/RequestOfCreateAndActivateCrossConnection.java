package com.cttnet.zhwg.ywkt.mtosi.data.dto.request;

import com.cttnet.zhwg.ywkt.mtosi.data.dto.base.RequestHeader;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *  创建并激活交叉连接请求
 * </pre>
 *
 * @author zhangyaomin
 * @date 2019/12/30
 * @since java 1.8
 */
@Data
public class RequestOfCreateAndActivateCrossConnection implements Serializable {

    private static final long serialVersionUID = -7666073201971594218L;

    @ApiModelProperty(value = "请求头部")
    private RequestHeader header;

    @Valid
    @Size(min = 1)
    @ApiModelProperty(value = "请求体", required = true)
    private List<CrossConnection> crossConnections;


}
