package com.cttnet.zhwg.ywkt.log.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * <pre>日志记录DTO</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-29
 * @since jdk 1.8
 */
@Data
@ApiModel(value="RecordLog保存请求对象", description="业务开通日志记录")
public class RecordLogSaveRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "请求连接")
    private String url;

    @ApiModelProperty(value = "请求方法名称")
    private String methodName;

    @ApiModelProperty(value = "模块")
    private String module;

    @ApiModelProperty(value = "请求方")
    private String requester;

    @ApiModelProperty(value = "响应方")
    private String responder;

    @ApiModelProperty(value = "请求报文")
    private String requestText;

    @ApiModelProperty(value = "响应报文")
    private String responseText;

    @ApiModelProperty(value = "请求时间")
    private Date requestTime;

    @ApiModelProperty(value = "响应时间")
    private Date responseTime;

    @ApiModelProperty(value = "时延")
    private Long delay;

    @ApiModelProperty(value = "状态")
    private Integer state;

    @ApiModelProperty(value = "详情描述")
    private String remark;

    @ApiModelProperty(value = "系统名称")
    private String systemName;

    @ApiModelProperty(value = "订单ID")
    private String orderBillId;
}
