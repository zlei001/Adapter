package com.cttnet.zhwg.ywkt.mtosi.data.dto.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <pre>
 *  公共请求头部
 * </pre>
 *
 * @author zhangyaomin
 * @date 2019/12/28
 * @since java 1.8
 */
@Data
public class RequestHeader {

    @ApiModelProperty("UUID")
    public String uuid;
    @ApiModelProperty("认证信息")
    public String nonce;
    @ApiModelProperty("签名")
    public String sign;
    @ApiModelProperty("请求域")
    public String domain;
    @ApiModelProperty("头部拓展信息")
    public String headExtensions;

}
