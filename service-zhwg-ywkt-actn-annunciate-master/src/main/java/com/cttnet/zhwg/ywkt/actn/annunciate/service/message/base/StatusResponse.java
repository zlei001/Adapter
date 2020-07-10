package com.cttnet.zhwg.ywkt.actn.annunciate.service.message.base;

import lombok.Data;
import org.apache.http.HttpStatus;

/**
 * <pre>
 * ACTN创建、修改、删除类型接口为异步接口只会返回状态
 * </pre>
 * <b>项目：</b> & <b>技术支持：凯通科技股份有限公司 (c) 2019 </b>
 *
 * @author zhangyaomin
 * @version 1.0
 * @since jdk 1.8
 */
@Data
public class StatusResponse {

    //Http状态 HttpStatus
    private int status;
    private String desc;
    /** 下发链接 */
    String url;
    /** 下发原始报文 */
    private String param;

    public boolean isOk() {

        return status == HttpStatus.SC_OK
                || status == HttpStatus.SC_CREATED
                || status == HttpStatus.SC_ACCEPTED
                || status == HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION
                || status == HttpStatus.SC_NO_CONTENT
                || status == HttpStatus.SC_RESET_CONTENT
                || status == HttpStatus.SC_PARTIAL_CONTENT
                || status == HttpStatus.SC_MULTI_STATUS
                ;
    }
}
