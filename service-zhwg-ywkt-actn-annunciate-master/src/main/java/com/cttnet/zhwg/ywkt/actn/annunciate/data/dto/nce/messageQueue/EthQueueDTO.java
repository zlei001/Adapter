package com.cttnet.zhwg.ywkt.actn.annunciate.data.dto.nce.messageQueue;


import com.cttnet.ywkt.actn.bean.eth.EthtSvc;
import com.cttnet.ywkt.actn.enums.Operation;
import com.cttnet.zhwg.ywkt.actn.annunciate.data.dto.ems.EmsDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>Description: 通告以太消息队列对象</p>
 *
 * @author suguisen
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EthQueueDTO extends QueueDTO {

    private EthtSvc ethtSvc ;
    public EthQueueDTO(){}

    public EthQueueDTO(Operation operation, EthtSvc instance, String changeTime, EmsDTO emsDTO, String methodName) {
        this.operation = operation;
        this.ethtSvc = instance;
        this.changeTime = changeTime;
        this.emsDTO = emsDTO;
        this.methodName = methodName;
    }
    public EthQueueDTO(Operation operation, String uuid, String ps, String changeTime, EmsDTO emsDTO, String methodName) {
        this.operation = operation;
        this.uuid = uuid;
        this.ps = ps;
        this.changeTime = changeTime;
        this.emsDTO = emsDTO;
        this.methodName = methodName;
    }
}
