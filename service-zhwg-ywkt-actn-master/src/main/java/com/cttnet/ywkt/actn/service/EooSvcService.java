package com.cttnet.ywkt.actn.service;

import com.cttnet.ywkt.actn.capacity.bo.Status;
import com.cttnet.ywkt.actn.data.dto.request.CreateBasicSvcRequestDTO;
import com.cttnet.ywkt.actn.data.dto.request.DeleteBasicSvcRequestDTO;
import com.cttnet.ywkt.actn.data.dto.request.ModifyBasicSvcRequestDTO;
import com.cttnet.ywkt.actn.data.dto.request.prec.EooSvcPrecomputedRequestDTO;
import com.cttnet.ywkt.actn.data.dto.response.EooSvcPrecomputedResponseDTO;
import com.cttnet.ywkt.actn.capacity.adapter.AbstractActnBaseAdapter;
import com.cttnet.ywkt.actn.capacity.adapter.impl.create.CreateEthEoo;
import com.cttnet.ywkt.actn.capacity.adapter.impl.delete.DeleteEthSvc;
import com.cttnet.ywkt.actn.capacity.adapter.impl.modify.ModifyBandwidthEthEoo;
import org.springframework.stereotype.Service;

/**
 * <pre>EOO业务Service</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-20
 * @since jdk 1.8
 */
@Service
public class EooSvcService {


    /**
     * <p>Description: 创建业务</p>
     * @author suguisen
     *
     */
    public String create(CreateBasicSvcRequestDTO request) {
        AbstractActnBaseAdapter adapter = new CreateEthEoo(request);
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
        AbstractActnBaseAdapter adapter = new ModifyBandwidthEthEoo(modifyBasicSvcRequestDTO);
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

    /**
     * <p>Description: 删除业务</p>
     * @author suguisen
     *
     */
    public String delete(DeleteBasicSvcRequestDTO deleteBasicSvcRequestDTO) {
        AbstractActnBaseAdapter adapter = new DeleteEthSvc(deleteBasicSvcRequestDTO);
        adapter.execute();
        Status status = adapter.getResponseOfAtomic();
        String result = status.isOk() ? "调用成功" : status.getDesc();
        return result;
    }

    public EooSvcPrecomputedResponseDTO precomputed(EooSvcPrecomputedRequestDTO request) {
        return null;
    }

}
