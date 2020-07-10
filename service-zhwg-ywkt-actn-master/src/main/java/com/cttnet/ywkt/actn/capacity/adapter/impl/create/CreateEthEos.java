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
import com.cttnet.ywkt.actn.bean.tunnel.sub.*;
import com.cttnet.ywkt.actn.capacity.bo.StatusResponse;
import com.cttnet.ywkt.actn.capacity.client.impl.EthSvcClientImpl;
import com.cttnet.ywkt.actn.capacity.client.impl.TeTunnelClientImpl;
import com.cttnet.ywkt.actn.capacity.constants.ActnParamConstants;
import com.cttnet.ywkt.actn.data.dto.request.CreateBasicSvcRequestDTO;
import com.cttnet.ywkt.actn.data.dto.request.RoutingStrategy;
import com.cttnet.ywkt.actn.domain.eth.request.RequestOfCreateEthtSvc;
import com.cttnet.ywkt.actn.domain.tunnel.request.RequestOfCreateTeTunnel;
import com.cttnet.ywkt.actn.domain.tunnel.request.RequestOfCreateTeTunnelEthEos;
import com.cttnet.ywkt.actn.enums.BusinessTypeEnum;
import com.cttnet.ywkt.actn.enums.MetricTypeEnum;
import com.cttnet.ywkt.actn.enums.PathScope;
import com.cttnet.ywkt.actn.enums.VcTypeEnum;
import com.cttnet.ywkt.actn.capacity.adapter.AbstractCreateAdapter;
import com.cttnet.ywkt.actn.capacity.adapter.common.NceMethodCommon;
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
public class CreateEthEos extends AbstractCreateAdapter {


    public CreateEthEos(CreateBasicSvcRequestDTO createBasicSvcRequestDTO) {
        super(createBasicSvcRequestDTO, BusinessTypeEnum.ETH_EOS);
    }

    @Override
    protected void setBandwidth(TeTunnel teTunnel) {
        TeBandwidth teBandwidth = new TeBandwidth();
        Long bandwidth = createBasicSvcRequestDTO.getBandwidth();
        String sdhtype =  ActnBussinessUtil.getGraininess(null, null, bandwidth);
        RoutingStrategy routingStrategy = createBasicSvcRequestDTO.getRoutingStrategy();
        // T1/使用时延策略 时 带宽小于20M 不能下到VC12 ,用VC3
        if (routingStrategy != null && MetricTypeEnum.DELAY_AVERAGE == routingStrategy.getMinRoutingStrategy() && bandwidth <= 20_000L) {
            sdhtype = VcTypeEnum.VC3.getValue();
        }
        teBandwidth.setIetfSdhTunnelSdhType(sdhtype);
        teTunnel.setTeBandwidth(teBandwidth);
    }


    /**
     * 预制隧道
     * @return
     */
    @Override
    protected List<TeTunnel> preBuild() {

        List<TeTunnel> teTunnels = new ArrayList<>();

        //sdh隧道
        TeTunnel teTunnelSDH = super.buildTeTunnel();
        teTunnelSDH.setProtection(null);
        teTunnelSDH.setEncoding("ietf-te-types:lsp-encoding-sdh");
        //teTunnelSDH.setShareTimeslotSdh(true);//共享时隙 //政企默认参数
        teTunnelSDH.setSncpRoutePolicy("ietf-sdh-types:link-reuse");//链路复用 //政企默认参数
        setBandwidth(teTunnelSDH);
        teTunnelSDH.setSwitchingType("ietf-te-types:switching-tdm");
        teTunnelSDH.getTeTopologyIdentifier().setTopologyId(ActnParamConstants.TOPOLOGY_ID_SDH);
        if (pointA.getVlan() == null) {
            pointA.setVlan(NceMethodCommon.DEFAULT_VLAN_A);
        }
        if (pointZ.getVlan() == null) {
            pointZ.setVlan(NceMethodCommon.DEFAULT_VLAN_Z);
        }
        if (pointA.getVlan() != null && pointZ.getVlan() != null) {
            teTunnelSDH.setTrafficPolicy("ietf-sdh-types:traffic-concentrated");//流量集中 EVPL(有VLAN) TODO 注意：EPL(没有VLAN值)不能填traffic—concentrated
        } else {
            teTunnelSDH.setTrafficPolicy("ietf-sdh-types:traffic-balanced");//流量分散 EPL
        }

        setTeTunnelPoint(teTunnelSDH);
        setPrimaryPathEthEos(teTunnelSDH);

        RequestOfCreateTeTunnel request = new RequestOfCreateTeTunnel();
        request.setTunnels(new ArrayList<>());
        request.getTunnels().add(teTunnelSDH);
        createTunnel(request);
        if (isOk()) {
            teTunnels.add(teTunnelSDH);
        }

        return teTunnels;
    }


    /**
     * <p>Description: 设置主路径 (EOS策略为空的情况，设置默认值综合最优)</p>
     * @author suguisen
     *
     */
    private void setPrimaryPathEthEos(TeTunnel teTunnel) {
        RoutingStrategy routingStrategy = createBasicSvcRequestDTO.getRoutingStrategy();
        if (routingStrategy == null || routingStrategy.getMinRoutingStrategy() == null) {
            if (teTunnel.getP2pPrimaryPaths() == null) {
                P2pPrimaryPaths p2pPrimaryPaths = new P2pPrimaryPaths();
                List<P2pPath> p2pPaths = new ArrayList<>();
                p2pPaths.add(new P2pPath());
                p2pPrimaryPaths.setP2pPrimaryPath(p2pPaths);
                teTunnel.setP2pPrimaryPaths(p2pPrimaryPaths);
            }

            P2pPath p2pPrimaryPath = teTunnel.getP2pPrimaryPaths().getP2pPrimaryPath().get(0);
            p2pPrimaryPath.setName("primary-path");//默认值
            if(!isOnceCpe){
                p2pPrimaryPath.setPathScope(PathScope.ENDTOEND.getValue());//CPE-CPE 都填endtoend
            }else{
                p2pPrimaryPath.setPathScope(PathScope.SEGMENT.getValue());//只要不是CPE-CPE 都填segment
            }

            Optimizations optimizations = new Optimizations();
            p2pPrimaryPath.setOptimizations(optimizations);
            optimizations.setOptimizationMetric(new ArrayList<>());
            OptimizationMetric optimizationMetric = new OptimizationMetric();
            optimizationMetric.setMetricType(MetricTypeEnum.METRIC_TE.getValue());//TODO 默认最优（最小代价）
            optimizations.getOptimizationMetric().add(optimizationMetric);

            //门限
//        PathMetricBounds pathMetricBounds = new PathMetricBounds();
//        p2pPrimaryPath.setPathMetricBounds(pathMetricBounds);
//        List<PathMetricBound> pathMetricBoundsList = new ArrayList<>();
//        PathMetricBound pathMetricBound = new PathMetricBound();
//        pathMetricBound.setMetricType("ietf-te-types:path-metric-delay-average");
//        pathMetricBound.setUpperBound(0L); //不需设值 即默认为0
//        pathMetricBoundsList.add(pathMetricBound);
//        pathMetricBounds.setPathMetricBoundList(pathMetricBoundsList);
        }
    }

    private void createTunnelEthEos(RequestOfCreateTeTunnelEthEos request) {

        TeTunnelClientImpl teTunnelService = new TeTunnelClientImpl(this.actnMacEmsDTO);
        StatusResponse status = teTunnelService.createTeTunnelEthEos(request);
        this.ok = status.isOk();
        if (!status.isOk()) {
            addErrInfo("【" + createBasicSvcRequestDTO.getSncName() + "】预制隧道失败：" + status.getDesc(), null);
        } else {
            log.info("【{}】预制隧道成功", createBasicSvcRequestDTO.getSncName());
        }

    }


    @Override
    protected void create(List<TeTunnel> teTunnels) {

        //TODO 待改成下发的EOS业务
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
        List<XxTunnel> sdhTunnels = new ArrayList<>();
        XxTunnel xxTunnel = new XxTunnel();
        TeTunnel teTunnel = teTunnels.get(0);
        xxTunnel.setName(teTunnel.getName());
        xxTunnel.setEncoding(teTunnel.getEncoding());
        xxTunnel.setSwitchingType(teTunnel.getSwitchingType());
        sdhTunnels.add(xxTunnel);
        underlay.setSdhTunnels(sdhTunnels);
        ethtSvc.setUnderlay(underlay);

        Resilience resilience = new Resilience();
        ethtSvc.setResilience(resilience);
        Protection protection = getProtection();
        resilience.setProtection(protection);
        // 以太业务sd-flag 使用 0 1标识
        //protection.setSdflag(sdFlag == SdFlag.ENABLE ? "1" : "0");

        List<EthtSvcEndPoint> ethtSvcEndPoints = new ArrayList<>();
        ethtSvc.setEthtSvcEndPoints(ethtSvcEndPoints);
        //源端
        EthtSvcEndPoint ethtSvcEndPointA = NceMethodCommon.setEthtSvcEndPoint(createBasicSvcRequestDTO,"0",pointA);
        long cirA = Long.valueOf(ethtSvcEndPointA.getIngressEgressBandwidthProfile().getCIR());
        ethtSvcEndPointA.getIngressEgressBandwidthProfile().setCIR(String.valueOf(cirA/1000*1024));
        ethtSvcEndPoints.add(ethtSvcEndPointA);
        //宿端
        EthtSvcEndPoint ethtSvcEndPointZ =  NceMethodCommon.setEthtSvcEndPoint(createBasicSvcRequestDTO,"1",pointZ);
        long cirZ = Long.valueOf(ethtSvcEndPointZ.getIngressEgressBandwidthProfile().getCIR());
        ethtSvcEndPointZ.getIngressEgressBandwidthProfile().setCIR(String.valueOf(cirZ/1000*1024));
        ethtSvcEndPoints.add(ethtSvcEndPointZ);
        //TODO  临时测试删除端口类型
        //ethtSvcEndPointA.getEthtSvcAccessPoints().get(0).setAccessPointType(null);
        //ethtSvcEndPointZ.getEthtSvcAccessPoints().get(0).setAccessPointType(null);


        //判断是否资源具备,不具备则 使用即用业务
        if (this.isOnceCpe) {
            Provisioning provisioning = new Provisioning();
            //默认值
            provisioning.setDeployType("ietf-eth-tran-types:available-once-cpe-connected");
            ethtSvc.setProvisioning(provisioning);
        }

        log.info("创建以太Eos业务入参：{}", JSON.toJSONString(createEthtSvc, SerializerFeature.NotWriteDefaultValue));
        StatusResponse response = null;
        try {
            response = new EthSvcClientImpl(this.actnMacEmsDTO).create(createEthtSvc);
            this.ok = response.isOk();
            if (!response.isOk()) {
                addErrInfo("创建Eos业务失败:" + response.getDesc(), null);
            } else {
                log.info("创建Eos成功");
            }
        } catch (Exception e) {
            log.error("创建以太业务失败", e);
        }
    }
}
