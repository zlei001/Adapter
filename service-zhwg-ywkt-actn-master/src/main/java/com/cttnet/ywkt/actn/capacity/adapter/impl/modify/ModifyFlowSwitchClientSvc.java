package com.cttnet.ywkt.actn.capacity.adapter.impl.modify;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cttnet.ywkt.actn.bean.client.ClientSvcInstance;
import com.cttnet.ywkt.actn.capacity.bo.StatusResponse;
import com.cttnet.ywkt.actn.capacity.client.impl.ClientSvcClientImpl;
import com.cttnet.ywkt.actn.data.dto.request.ModifyBasicSvcRequestDTO;
import com.cttnet.ywkt.actn.domain.client.request.RequestOfUpdateClientSvc;
import com.cttnet.ywkt.actn.enums.FlowSwitch;
import com.cttnet.ywkt.actn.capacity.adapter.AbstractModifyAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 以太EOO调整流量开关（停复机）
 * </pre>
 * <b>项目：</b> & <b>技术支持：凯通科技股份有限公司 (c) 2019 </b>
 *
 * @author zhangyaomin
 * @version 1.0
 * @since jdk 1.8
 */
@Slf4j
public class ModifyFlowSwitchClientSvc extends AbstractModifyAdapter {


    public ModifyFlowSwitchClientSvc(ModifyBasicSvcRequestDTO modifyBasicSvcRequestDTO) {
        super(modifyBasicSvcRequestDTO);
    }

    @Override
    protected void modify() {

        String clientSvcName = modifyBasicSvcRequestDTO.getUuid();
        Boolean isFlowSwitch = modifyBasicSvcRequestDTO.getFlowSwitch();

        if (isFlowSwitch == null) {
            addErrInfo("流量开关为空", null);
            return;
        }

        String flowSwitch = isFlowSwitch ? FlowSwitch.SWITCHON.getValue() : FlowSwitch.SWITCHOFF.getValue();
        ClientSvcInstance clientSvcInstance = new ClientSvcInstance();
        clientSvcInstance.setClientSvcName(clientSvcName);
        clientSvcInstance.setFlowSwitch(flowSwitch);

        List<ClientSvcInstance> clientSvcInstances = new ArrayList<>();
        clientSvcInstances.add(clientSvcInstance);
        RequestOfUpdateClientSvc requestOfUpdateClientSvc = new RequestOfUpdateClientSvc();
        requestOfUpdateClientSvc.setClientSvcInstances(clientSvcInstances);

        log.info("透传业务修改流量开关入参：{}", JSON.toJSONString(requestOfUpdateClientSvc, SerializerFeature.NotWriteDefaultValue));
        StatusResponse response;
        try {
            response = new ClientSvcClientImpl(this.actnMacEmsDTO).update(requestOfUpdateClientSvc);
            this.ok = response.isOk();
            if (!response.isOk()) {
                addErrInfo("透传业务修改流量开关失败:" + response.getDesc(), null);
            } else {
                log.info("透传业务修改流量开关成功");
            }
        } catch (Exception e) {
            log.error("透传业务修改流量开关失败",e);
        }
    }
}
