package com.cttnet.ywkt.actn.capacity.adapter.impl.modify;

import com.cttnet.common.util.SpringContextUtils;
import com.cttnet.ywkt.actn.bean.client.ClientSvcInstance;
import com.cttnet.ywkt.actn.bean.client.sub.SrvTunnel;
import com.cttnet.ywkt.actn.bean.tunnel.TeTunnel;
import com.cttnet.ywkt.actn.bean.tunnel.sub.TeBandwidth;
import com.cttnet.ywkt.actn.capacity.bo.StatusResponse;
import com.cttnet.ywkt.actn.capacity.client.impl.TeTunnelClientImpl;
import com.cttnet.ywkt.actn.data.dto.request.ModifyBasicSvcRequestDTO;
import com.cttnet.ywkt.actn.domain.client.response.ResponseOfQueryClientSvc;
import com.cttnet.ywkt.actn.domain.tunnel.request.RequestOfCreateTeTunnel;
import com.cttnet.ywkt.actn.enums.OduTypeEnum;
import com.cttnet.ywkt.actn.service.nce.NceSerivce;
import com.cttnet.ywkt.actn.capacity.adapter.AbstractModifyAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 以太CLIENT调整带宽
 * </pre>
 * <b>项目：</b> & <b>技术支持：凯通科技股份有限公司 (c) 2019 </b>
 *
 * @author zhangyaomin
 * @version 1.0
 * @since jdk 1.8
 */
@Slf4j
public class ModifyBandwidthClientSvc extends AbstractModifyAdapter {

    public ModifyBandwidthClientSvc(ModifyBasicSvcRequestDTO modifyBasicSvcRequestDTO) {
        super(modifyBasicSvcRequestDTO);
        // 带宽校验
        if (this.ok) {
            if (modifyBasicSvcRequestDTO.getBandwidth() == null ) {
                this.ok = false;
                this.errSet.add("带宽为空");
            }
        }
    }

    @Override
    protected void modify() {
        NceSerivce nceSerivce = SpringContextUtils.getBean(NceSerivce.class);
        String uuid = modifyBasicSvcRequestDTO.getUuid();
        ResponseOfQueryClientSvc businessClientSvc = nceSerivce.getBusinessClientSvc(actnMacEmsDTO.getEmsId(), uuid);
        if (businessClientSvc != null) {
            //获取隧道id
            List<ClientSvcInstance> clientSvcInstances = businessClientSvc.getClientSvcInstances();
            ClientSvcInstance clientSvcInstance = clientSvcInstances.get(0);
            List<SrvTunnel> svcTunnels = clientSvcInstance.getSvcTunnels();
            SrvTunnel srvTunnel = svcTunnels.get(0);
            String teTunnelName = srvTunnel.getTunnelName();

            TeTunnel teTunnel = new TeTunnel();
            teTunnel.setName(teTunnelName);
            TeBandwidth teBandwidth = new TeBandwidth();
            teBandwidth.setIetfOtnTunnelOduType(OduTypeEnum.ODU_FLEX.getValue());
            teBandwidth.setIetfotntunnelconnectionbandwidth(String.valueOf(modifyBasicSvcRequestDTO.getBandwidth()));
            teTunnel.setTeBandwidth(teBandwidth);

            RequestOfCreateTeTunnel request = new RequestOfCreateTeTunnel();
            request.setTunnels(new ArrayList<>());
            request.getTunnels().add(teTunnel);

            TeTunnelClientImpl teTunnelService = new TeTunnelClientImpl(this.actnMacEmsDTO);
            StatusResponse status = teTunnelService.updateTeTunnel(request);
            this.ok = status.isOk();
            if (!status.isOk()) {
                addErrInfo("透传带宽变更失败：" + status.getDesc(), null);
            } else {
                log.info("【{}】透传带宽变更成功",modifyBasicSvcRequestDTO.getSncName());
            }
        }else{
            addErrInfo("透传带宽变更失败" , null);
        }
    }
}
