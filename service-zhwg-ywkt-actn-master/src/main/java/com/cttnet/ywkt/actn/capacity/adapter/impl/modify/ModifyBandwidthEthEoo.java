package com.cttnet.ywkt.actn.capacity.adapter.impl.modify;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cttnet.ywkt.actn.bean.eth.EthtSvc;
import com.cttnet.ywkt.actn.bean.eth.sub.EthtSvcEndPoint;
import com.cttnet.ywkt.actn.bean.eth.sub.IngressEgressBandwidthProfile;
import com.cttnet.ywkt.actn.capacity.bo.StatusResponse;
import com.cttnet.ywkt.actn.capacity.client.impl.EthSvcClientImpl;
import com.cttnet.ywkt.actn.data.dto.request.ModifyBasicSvcRequestDTO;
import com.cttnet.ywkt.actn.domain.eth.request.RequestOfCreateEthtSvc;
import com.cttnet.ywkt.actn.capacity.adapter.AbstractModifyAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 以太EOO调整带宽
 * </pre>
 * <b>项目：</b> & <b>技术支持：凯通科技股份有限公司 (c) 2019 </b>
 *
 * @author zhangyaomin
 * @version 1.0
 * @since jdk 1.8
 */
@Slf4j
public class ModifyBandwidthEthEoo extends AbstractModifyAdapter {


    public ModifyBandwidthEthEoo(ModifyBasicSvcRequestDTO modifyBasicSvcRequestDTO) {
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

        String ethSvcName = modifyBasicSvcRequestDTO.getUuid();
        RequestOfCreateEthtSvc requestOfCreateEthtSvc = new RequestOfCreateEthtSvc();
        List<EthtSvc> ethtSvcInstances = new ArrayList<>();
        EthtSvc ethtSvc = new EthtSvc();
        ethtSvc.setEthtsvcname(ethSvcName);
        //业务调带宽
        List<EthtSvcEndPoint> ethtSvcEndPoints = new ArrayList<>();
        //源端
        EthtSvcEndPoint ethtSvcEndPointA = new EthtSvcEndPoint();
        ethtSvcEndPointA.setEthtSvcEndPointName("0");
        IngressEgressBandwidthProfile ingressEgressBandwidthProfileA = new IngressEgressBandwidthProfile();
        ingressEgressBandwidthProfileA.setBandwidthProfileType("ietf-eth-tran-types:mef-10-bwp");
        ingressEgressBandwidthProfileA.setCIR(String.valueOf(modifyBasicSvcRequestDTO.getBandwidth()));
        //ingressEgressBandwidthProfile.setEIR();
        ethtSvcEndPointA.setIngressEgressBandwidthProfile(ingressEgressBandwidthProfileA);
        ethtSvcEndPoints.add(ethtSvcEndPointA);
        //宿端
        EthtSvcEndPoint ethtSvcEndPointZ = new EthtSvcEndPoint();
        ethtSvcEndPointZ.setEthtSvcEndPointName("1");
        IngressEgressBandwidthProfile ingressEgressBandwidthProfileZ = new IngressEgressBandwidthProfile();
        ingressEgressBandwidthProfileZ.setBandwidthProfileType("ietf-eth-tran-types:mef-10-bwp");
        ingressEgressBandwidthProfileZ.setCIR(String.valueOf(modifyBasicSvcRequestDTO.getBandwidth()));
        ethtSvcEndPointZ.setIngressEgressBandwidthProfile(ingressEgressBandwidthProfileZ);
        ethtSvcEndPoints.add(ethtSvcEndPointZ);
        ethtSvc.setEthtSvcEndPoints(ethtSvcEndPoints);
        ethtSvcInstances.add(ethtSvc);
        requestOfCreateEthtSvc.setEthtSvcInstances(ethtSvcInstances);

        log.info("修改以太EOO业务入参：{}", JSON.toJSONString(requestOfCreateEthtSvc, SerializerFeature.NotWriteDefaultValue));
        StatusResponse response;
        try {
            response = new EthSvcClientImpl(this.actnMacEmsDTO).update(requestOfCreateEthtSvc);
            this.ok = response.isOk();
            if (!response.isOk()) {
                addErrInfo("修改以太EOO业务失败:" + response.getDesc(), null);
            } else {
                log.info("修改以太EOO业务成功");
            }
        } catch (Exception e) {
            log.error("修改以太EOO业务失败",e);
        }
    }
}
