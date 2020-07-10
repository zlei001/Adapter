package com.cttnet.zhwg.ywkt.actn.annunciate.data.dto.nce.messageQueue;

import com.cttnet.ywkt.actn.bean.client.ClientSvcInstance;
import com.cttnet.ywkt.actn.enums.Operation;
import com.cttnet.zhwg.ywkt.actn.annunciate.data.dto.ems.EmsDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>Description: 通告透传消息队列对象</p>
 *
 * @author suguisen
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ClientQueueDTO extends QueueDTO {

    private ClientSvcInstance clientSvcInstance;

    public ClientQueueDTO() {}

    public ClientQueueDTO(Operation operation, ClientSvcInstance instance, String changeTime, EmsDTO emsDTO, String methodName) {
        this.operation = operation;
        this.clientSvcInstance = instance;
        this.changeTime = changeTime;
        this.emsDTO = emsDTO;
        this.methodName = methodName;
    }
    public ClientQueueDTO(Operation operation, String uuid, String ps, String changeTime, EmsDTO emsDTO, String methodName) {
        this.operation = operation;
        this.uuid = uuid;
        this.ps = ps;
        this.changeTime = changeTime;
        this.emsDTO = emsDTO;
        this.methodName = methodName;
    }


}
