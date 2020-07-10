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
 *  去激活并删除交叉连接request
 * </pre>
 *
 * @author wangzhaoshi
 * @date 2020/2/24
 * @since java 1.8
 */
@Data
public class RequestOfDeactivateAndDeleteCrossConnection  implements Serializable {

    private static final long serialVersionUID = 1533582869922676738L;

    @ApiModelProperty(value = "请求头部")
    private RequestHeader header;

    @Valid
    @Size(min = 1)
    @ApiModelProperty(value = "请求体", required = true)
    private List<CrossConnection> crossConnections;

}
