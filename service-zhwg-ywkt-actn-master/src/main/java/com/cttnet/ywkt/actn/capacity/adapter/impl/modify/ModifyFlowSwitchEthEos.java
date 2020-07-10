package com.cttnet.ywkt.actn.capacity.adapter.impl.modify;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cttnet.ywkt.actn.bean.eth.EthtSvc;
import com.cttnet.ywkt.actn.capacity.bo.StatusResponse;
import com.cttnet.ywkt.actn.capacity.client.impl.EthSvcClientImpl;
import com.cttnet.ywkt.actn.data.dto.request.ModifyBasicSvcRequestDTO;
import com.cttnet.ywkt.actn.domain.eth.request.RequestOfCreateEthtSvc;
import com.cttnet.ywkt.actn.enums.FlowSwitch;
import com.cttnet.ywkt.actn.capacity.adapter.AbstractModifyAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 以太EOS调整流量开关（停复机）
 * </pre>
 * <b>项目：</b> & <b>技术支持：凯通科技股份有限公司 (c) 2019 </b>
 *
 * @author zhangyaomin
 * @version 1.0
 * @since jdk 1.8
 */
@Slf4j
public class ModifyFlowSwitchEthEos extends AbstractModifyAdapter {


    public ModifyFlowSwitchEthEos(ModifyBasicSvcRequestDTO modifyBasicSvcRequestDTO) {
        super(modifyBasicSvcRequestDTO);
    }
    @Override
    protected void modify() {

        String ethSvcName = modifyBasicSvcRequestDTO.getUuid();
        Boolean isFlowSwitch = modifyBasicSvcRequestDTO.getFlowSwitch();

        if (isFlowSwitch == null) {
            addErrInfo("流量开关为空", null);
            return;
        }

        String flowSwitch = isFlowSwitch ? FlowSwitch.SWITCHON.getValue() : FlowSwitch.SWITCHOFF.getValue();
        EthtSvc ethtSvc = new EthtSvc();
        ethtSvc.setEthtsvcname(ethSvcName);
        ethtSvc.setFlowSwitch(flowSwitch);
        List<EthtSvc> ethtSvcs = new ArrayList<>();
        ethtSvcs.add(ethtSvc);
        RequestOfCreateEthtSvc requestOfCreateEthtSvc = new RequestOfCreateEthtSvc();
        requestOfCreateEthtSvc.setEthtSvcInstances(ethtSvcs);

        log.info("以太EOS业务修改流量开关入参：{}", JSON.toJSONString(requestOfCreateEthtSvc, SerializerFeature.NotWriteDefaultValue));
        StatusResponse response = null;
        try {
            response = new EthSvcClientImpl(this.actnMacEmsDTO).update(requestOfCreateEthtSvc);
            this.ok = response.isOk();
            if (!response.isOk()) {
                addErrInfo("以太EOS业务修改流量开关失败:" + response.getDesc(), null);
            } else {
                log.info("以太EOS业务修改流量开关成功");
            }
        } catch (Exception e) {
            log.error("以太EOS业务修改流量开关失败",e);
        }
    }
}
