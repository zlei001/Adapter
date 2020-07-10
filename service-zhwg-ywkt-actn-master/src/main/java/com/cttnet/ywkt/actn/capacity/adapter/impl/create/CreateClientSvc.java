package com.cttnet.ywkt.actn.capacity.adapter.impl.create;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cttnet.ywkt.actn.bean.client.ClientSvcInstance;
import com.cttnet.ywkt.actn.bean.client.sub.AccessPort;
import com.cttnet.ywkt.actn.bean.client.sub.Provisioning;
import com.cttnet.ywkt.actn.bean.client.sub.SrvTunnel;
import com.cttnet.ywkt.actn.bean.tunnel.TeTunnel;
import com.cttnet.ywkt.actn.capacity.bo.StatusResponse;
import com.cttnet.ywkt.actn.capacity.client.impl.ClientSvcClientImpl;
import com.cttnet.ywkt.actn.capacity.constants.ActnParamConstants;
import com.cttnet.ywkt.actn.data.dto.request.CreateBasicSvcRequestDTO;
import com.cttnet.ywkt.actn.domain.client.request.RequestOfCreateClientSvc;
import com.cttnet.ywkt.actn.enums.*;
import com.cttnet.ywkt.actn.capacity.adapter.AbstractCreateAdapter;
import com.cttnet.ywkt.actn.util.ActnBussinessUtil;
import com.cttnet.ywkt.actn.util.PrimaryKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

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
public class CreateClientSvc extends AbstractCreateAdapter {

    public CreateClientSvc(CreateBasicSvcRequestDTO createBasicSvcRequestDTO) {
        super(createBasicSvcRequestDTO, BusinessTypeEnum.CLIENT);
    }

    @Override
    protected void create(List<TeTunnel> teTunnels) {

        RequestOfCreateClientSvc createClientSvc = new RequestOfCreateClientSvc();
        List<ClientSvcInstance> clientSvcInstances = new ArrayList<>();
        createClientSvc.setClientSvcInstances(clientSvcInstances);
        ClientSvcInstance clientSvc = new ClientSvcInstance();
        clientSvcInstances.add(clientSvc);
        String clientSvcName = createBasicSvcRequestDTO.getUuid();
        if (StringUtils.isBlank(clientSvcName)) {
            clientSvcName = PrimaryKeyUtil.getKey(createBasicSvcRequestDTO.getSncName());
        }
        clientSvc.setClientSvcName(clientSvcName);
        clientSvc.setClientSvcTitle(createBasicSvcRequestDTO.getSncName());
        clientSvc.setAccessProviderId(ActnParamConstants.PROVIDER_ID);
        clientSvc.setAccessClientId(ActnParamConstants.CLIENT_ID);
        clientSvc.setAccessTopologyId(ActnParamConstants.TOPOLOGY_ID_ELEC);

        //默认值
        clientSvc.setAdminStatus("ietf-te-types:tunnel-admin-state-up");
//        clientSvc.setAdminStatus("ietf-te-types:tunnel-state-up");
        AccessPort srcPort = new AccessPort();
        AccessPort dstPort = new AccessPort();
        //设置源宿的节点端口
        srcPort.setAccessNodeId(pointA.getNeId());
        dstPort.setAccessNodeId(pointZ.getNeId());
        srcPort.setAccessLtpId(pointA.getPortId());
        dstPort.setAccessLtpId(pointZ.getPortId());
        AccessPointTypeEnum pointTypeA = pointA.getAccessPointType();
        AccessPointTypeEnum pointTypeZ = pointZ.getAccessPointType();

        if (pointTypeA != null && pointTypeZ != null) {
            //通过AZ端接口类型获取对应的透传业务信号
            ClientSignalEnum clientSignal = ActnBussinessUtil.getClientSingal(pointTypeA, pointTypeZ);
            srcPort.setClientSignal(clientSignal != null ? clientSignal.getSignal() : null);
            dstPort.setClientSignal(clientSignal != null ? clientSignal.getSignal() : null);
            srcPort.setAccessPointType(pointTypeA.getPointType());
            dstPort.setAccessPointType(pointTypeZ.getPointType());
        }
        clientSvc.setSrcAccessPorts(srcPort);
        clientSvc.setDstAccessPorts(dstPort);

        Provisioning provisioning = new Provisioning();
        clientSvc.setProvisioning(provisioning);
        String graininess = ActnBussinessUtil.getGraininess(pointTypeA, pointTypeZ, createBasicSvcRequestDTO.getBandwidth());
        // TODO 若是ODUFLEX 才可调整带宽
        if (OduTypeEnum.ODU_FLEX.getValue().equals(graininess)) {
            // TODO 可调整带宽
            provisioning.setBandwidthAdjustment("ietf-trans-client-svc-types:lossy-adjustment");
        }
        //判断是否资源具备,不具备则 使用即用业务
        if (isOnceCpe) {
            //即插即用重置两端CPE节点为0.0.0.0
            if (NeTypeEnum.CO == pointA.getExtensionType()) {
                srcPort.setAccessNodeId(ActnParamConstants.ONCE_CPE_NAME);
            }
            if (NeTypeEnum.CO == pointZ.getExtensionType()) {
                dstPort.setAccessNodeId(ActnParamConstants.ONCE_CPE_NAME);
            }
            //即插即用
            provisioning.setDeployType("ietf-trans-client-svc-types:available-once-cpe-connected");
        }
        if (StringUtils.isBlank(provisioning.getBandwidthAdjustment()) && StringUtils.isBlank(provisioning.getDeployType())) {
            clientSvc.setProvisioning(null);
        }

        if (teTunnels != null && !teTunnels.isEmpty()) {
            List<SrvTunnel> srvTunnels = new ArrayList<>();
            for (TeTunnel teTunnel : teTunnels) {
                SrvTunnel srvTunnel = new SrvTunnel();
                srvTunnel.setTunnelName(teTunnel.getName());
                srvTunnels.add(srvTunnel);
            }
            clientSvc.setSvcTunnels(srvTunnels);
        }

        log.info("创建透传业务入参：{}", JSON.toJSONString(createClientSvc, SerializerFeature.NotWriteDefaultValue));
        StatusResponse response = null;
        try {
            response = new ClientSvcClientImpl(this.actnMacEmsDTO).create(createClientSvc);
            this.ok = response.isOk();
            if (!response.isOk()) {
                addErrInfo("创建透传业务失败:" + response.getDesc(), null);
            } else {
                log.info("创建透传业务成功");
            }
        } catch (Exception e) {
            log.error("创建透传业务失败", e);
        }

    }
}
