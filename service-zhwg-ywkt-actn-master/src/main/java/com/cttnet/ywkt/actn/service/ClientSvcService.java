package com.cttnet.ywkt.actn.service;

import com.cttnet.ywkt.actn.capacity.bo.Status;
import com.cttnet.ywkt.actn.data.dto.request.CreateBasicSvcRequestDTO;
import com.cttnet.ywkt.actn.data.dto.request.DeleteBasicSvcRequestDTO;
import com.cttnet.ywkt.actn.data.dto.request.ModifyBasicSvcRequestDTO;
import com.cttnet.ywkt.actn.data.dto.request.prec.ClientSvcPrecomputedRequestDTO;
import com.cttnet.ywkt.actn.data.dto.response.ClientSvcPrecomputedResponseDTO;
import com.cttnet.ywkt.actn.capacity.adapter.AbstractActnBaseAdapter;
import com.cttnet.ywkt.actn.capacity.adapter.impl.create.CreateClientSvc;
import com.cttnet.ywkt.actn.capacity.adapter.impl.delete.DeleteClientSvc;
import com.cttnet.ywkt.actn.capacity.adapter.impl.modify.ModifyBandwidthClientSvc;
import org.springframework.stereotype.Service;

/**
 * <pre>透传业务Service</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-20
 * @since jdk 1.8
 */
@Service
public class ClientSvcService {

    /**
     * <p>Description: 创建业务</p>
     * @author suguisen
     *
     */
    public String create(CreateBasicSvcRequestDTO request) {
        AbstractActnBaseAdapter adapter = new CreateClientSvc(request);
        adapter.execute();
        Status status = adapter.getResponseOfAtomic();
        String result = status.isOk() ? "调用成功" : status.getDesc();
        return result;
    }

    /**
     * <p>Description: 调整带宽</p>
     * @author suguisen
     *
     */
    public String updateBandwidth(ModifyBasicSvcRequestDTO modifyBasicSvcRequestDTO) {
        AbstractActnBaseAdapter adapter = new ModifyBandwidthClientSvc(modifyBasicSvcRequestDTO);
        adapter.execute();
        Status status = adapter.getResponseOfAtomic();
        String result = status.isOk() ? "调用成功" : status.getDesc();
        return result;
    }

    /**
     * <p>Description: 调整流量开关</p>
     * @author suguisen
     *
     */
    public String updateFlowSwitch(ModifyBasicSvcRequestDTO request) {
        return null;
    }

    public String delete(DeleteBasicSvcRequestDTO deleteBasicSvcRequestDTO) {
        AbstractActnBaseAdapter adapter = new DeleteClientSvc(deleteBasicSvcRequestDTO);
        adapter.execute();
        Status status = adapter.getResponseOfAtomic();
        String result = status.isOk() ? "调用成功" : status.getDesc();
        return result;
    }

    /**
     * <p>Description: 删除业务</p>
     * @author suguisen
     *
     */
    public ClientSvcPrecomputedResponseDTO precomputed(ClientSvcPrecomputedRequestDTO request) {

        return null;
    }

}
