package com.cttnet.zhwg.ywkt.mtosi.ability.cmd.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * <pre>
 *  CMD命令对象
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/1/10
 * @since java 1.8
 */
@Data
public class Command implements Serializable {

    private static final long serialVersionUID = -9096938870452441268L;

    /** 线程Id */
    private String threadId;
    /** 构造请求报文入参 */
    private Map<String, String> kvs;
    /** 请求解析出参 */
    private CmdResult cmdResult;

    /** 请求开始时间 */
    private Date startTime;
    /** 请求结束时间 */
    private Date endTime;
    /** 请求报文 */
    private String requestXml;
    /** 响应报文 */
    private String responseXml;
}
