package com.cttnet.zhwg.ywkt.actn.annunciate.data.dto.nce.messageQueue;

import com.cttnet.ywkt.actn.enums.Operation;
import com.cttnet.zhwg.ywkt.actn.annunciate.data.dto.ems.EmsDTO;
import lombok.Data;

/**
 * <p>Description: 通告消息队列基础对象</p>
 *
 * @author suguisen
 */
@Data
public class QueueDTO {

    /** 方法名 */
    protected String methodName;

    protected Operation operation;

    /** 业务id/隧道id */
    protected String uuid;

    /** 消息通告状态*/
    protected String ps;

    protected String changeTime;

    /** 网管*/
    protected EmsDTO emsDTO;

}
