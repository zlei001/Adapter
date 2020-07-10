package com.cttnet.zhwg.ywkt.log.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>订单日志RelDTO</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-29
 * @since jdk 1.8
 */
@Data
@ApiModel(value="OrderLogRel保存请求对象", description="订单日志关联")
public class OrderLogRelSaveRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单ID")
    private String orderBillId;

    @ApiModelProperty(value = "日志ID")
    private String recordLogId;
}
