package com.cttnet.ywkt.actn.capacity.adapter.impl.create;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cttnet.ywkt.actn.bean.client.sub.Provisioning;
import com.cttnet.ywkt.actn.bean.eth.EthtSvc;
import com.cttnet.ywkt.actn.bean.eth.sub.EthtSvcEndPoint;
import com.cttnet.ywkt.actn.bean.eth.sub.Resilience;
import com.cttnet.ywkt.actn.bean.eth.sub.Underlay;
import com.cttnet.ywkt.actn.bean.eth.sub.XxTunnel;
import com.cttnet.ywkt.actn.bean.tunnel.TeTunnel;
import com.cttnet.ywkt.actn.bean.tunnel.sub.Protection;
import com.cttnet.ywkt.actn.bean.tunnel.sub.Restoration;
import com.cttnet.ywkt.actn.bean.tunnel.sub.TeTopologyIdentifier;
import com.cttnet.ywkt.actn.capacity.bo.StatusResponse;
import com.cttnet.ywkt.actn.capacity.client.impl.EthSvcClientImpl;
import com.cttnet.ywkt.actn.capacity.constants.ActnParamConstants;
import com.cttnet.ywkt.actn.data.dto.request.CreateBasicSvcRequestDTO;
import com.cttnet.ywkt.actn.domain.eth.request.RequestOfCreateEthtSvc;
import com.cttnet.ywkt.actn.capacity.adapter.AbstractCreateAdapter;
import com.cttnet.ywkt.actn.capacity.adapter.common.NceMethodCommon;
import com.cttnet.ywkt.actn.enums.BusinessTypeEnum;
import com.cttnet.ywkt.actn.util.PrimaryKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *
 * </pre>
 * <b>项目：</b> & <b>技术支持：凯通科技股份有限公司 (c) 2019 </b>
 *
 * @author zhangyaomin
 * @version 1.0
 * @since jdk 1.8
 */
@Slf4j
public class CreateEthEoo extends AbstractCreateAdapter {

    public CreateEthEoo(CreateBasicSvcRequestDTO createBasicSvcRequestDTO) {
        super(createBasicSvcRequestDTO, BusinessTypeEnum.ETH_EOO);
    }

    @Override
    protected TeTunnel buildTeTunnel() {

        TeTunnel teTunnel = super.buildTeTunnel();
        //EOO业务下发保护节点在业务节点中设置
        teTunnel.setProtection(null);
        Restoration restoration = teTunnel.getRestoration();
        restoration.setRestorationType("ietf-te-types:lsp-restoration-restore-all");//以太默认值

        return teTunnel;
    }

    @Override
    protected void setBandwidth(TeTunnel teTunnel) {
        //EOO业务不需要指定承载粒度
    }

    @Override
    protected void create(List<TeTunnel> teTunnels) {

        RequestOfCreateEthtSvc createEthtSvc = new RequestOfCreateEthtSvc();
        List<EthtSvc> ethtSvcInstances = new ArrayList<>();
        createEthtSvc.setEthtSvcInstances(ethtSvcInstances);
        EthtSvc ethtSvc = new EthtSvc();
        ethtSvcInstances.add(ethtSvc);

        String ethtSvcName = createBasicSvcRequestDTO.getUuid();
        if (StringUtils.isBlank(ethtSvcName)) {
            ethtSvcName = PrimaryKeyUtil.getKey(createBasicSvcRequestDTO.getSncName());
        }
        ethtSvc.setEthtsvcname(ethtSvcName);
        ethtSvc.setEthtsvctitle(createBasicSvcRequestDTO.getSncName());
        ethtSvc.setEthtsvctype("ietf-eth-tran-types:p2p-svc");
        ethtSvc.setAdminstatus("ietf-te-types:tunnel-admin-state-up");

        TeTopologyIdentifier teTopologyIdentifier = new TeTopologyIdentifier();
        teTopologyIdentifier.setClientId(ActnParamConstants.CLIENT_ID);
        teTopologyIdentifier.setProviderId(ActnParamConstants.PROVIDER_ID);
        teTopologyIdentifier.setTopologyId(ActnParamConstants.TOPOLOGY_ID_ETHE);
        ethtSvc.setIdentifier(teTopologyIdentifier);

        Underlay underlay = new Underlay();
        List<XxTunnel> otnTunnels = new ArrayList<>();
        XxTunnel xxTunnel = new XxTunnel();
        xxTunnel.setName(teTunnels.get(0).getName());
        xxTunnel.setEncoding("ietf-te-types:lsp-encoding-oduk");
        xxTunnel.setSwitchingType("ietf-te-types:switching-otn");
        otnTunnels.add(xxTunnel);
        underlay.setOtnTunnels(otnTunnels);
        ethtSvc.setUnderlay(underlay);

        Resilience resilience = new Resilience();
        Protection protection = getProtection();
        resilience.setProtection(protection);
        // 以太业务sd-flag 使用 0 1标识
        //protection.setSdflag(sdFlag == SdFlag.ENABLE ? "1" : "0");
        ethtSvc.setResilience(resilience);
        List<EthtSvcEndPoint> ethtSvcEndPoints = new ArrayList<>();
        ethtSvc.setEthtSvcEndPoints(ethtSvcEndPoints);

        //源端
        EthtSvcEndPoint ethtSvcEndPointA = NceMethodCommon.setEthtSvcEndPoint(createBasicSvcRequestDTO, "0", pointA);
        ethtSvcEndPoints.add(ethtSvcEndPointA);
        //宿端
        EthtSvcEndPoint ethtSvcEndPointZ = NceMethodCommon.setEthtSvcEndPoint(createBasicSvcRequestDTO, "1", pointZ);
        ethtSvcEndPoints.add(ethtSvcEndPointZ);

        //判断是否资源具备,不具备则 使用即用业务
        if (this.isOnceCpe) {
            Provisioning provisioning = new Provisioning();
            //默认值
            provisioning.setDeployType("ietf-eth-tran-types:available-once-cpe-connected");
            ethtSvc.setProvisioning(provisioning);
        }

        log.info("创建以太业务入参：{}", JSON.toJSONString(createEthtSvc, SerializerFeature.NotWriteDefaultValue));
        StatusResponse response = null;
        try {
            response = new EthSvcClientImpl(this.actnMacEmsDTO).create(createEthtSvc);
            this.ok = response.isOk();
            if (!response.isOk()) {
                addErrInfo("创建EOO业务失败:" + response.getDesc(), null);
            } else {
                log.info("创建EOO成功");
            }
        } catch (Exception e) {
            log.error("创建以太业务失败", e);
        }
    }

}
