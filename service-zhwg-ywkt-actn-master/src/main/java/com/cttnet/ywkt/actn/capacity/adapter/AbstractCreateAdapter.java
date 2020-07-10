package com.cttnet.ywkt.actn.capacity.adapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cttnet.ywkt.actn.bean.tunnel.TeTunnel;
import com.cttnet.ywkt.actn.bean.tunnel.sub.*;
import com.cttnet.ywkt.actn.capacity.bo.StatusResponse;
import com.cttnet.ywkt.actn.capacity.client.impl.TeTunnelClientImpl;
import com.cttnet.ywkt.actn.capacity.constants.ActnParamConstants;
import com.cttnet.ywkt.actn.data.dto.request.CreateBasicSvcRequestDTO;
import com.cttnet.ywkt.actn.data.dto.request.PointDTO;
import com.cttnet.ywkt.actn.data.dto.request.ProtectionPoint;
import com.cttnet.ywkt.actn.domain.tunnel.request.RequestOfCreateTeTunnel;
import com.cttnet.ywkt.actn.enums.*;
import com.cttnet.ywkt.actn.capacity.adapter.common.NceMethodCommon;
import com.cttnet.ywkt.actn.util.ActnBussinessUtil;
import com.cttnet.ywkt.actn.util.PrimaryKeyUtil;
import lombok.extern.slf4j.Slf4j;

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
public abstract class AbstractCreateAdapter extends AbstractActnBaseAdapter {

    /** 备路径返回主路径前等待时间,单位：秒 */
    public static final int DEFAULTWAITTOREVERT = 600;
    /** 保护倒换延迟时间，单位：毫秒 */
    public static final int DEFAULTHOLDOFFTIME = 10;

    protected CreateBasicSvcRequestDTO createBasicSvcRequestDTO;

    protected PointDTO pointA;
    protected PointDTO pointZ;

    /** 是否是CPE即插即用*/
    protected boolean isOnceCpe = false;
    /** 是否保护 */
    protected boolean isProtected = false;
    /** 是否分段保护  */
    protected boolean segmentProtect = false;
    /** sd使能标识  */
    protected SdFlagEnum sdFlag = SdFlagEnum.ENABLE;

    /**
     * 业务类型
     */
    protected BusinessTypeEnum businessType;

    public AbstractCreateAdapter(CreateBasicSvcRequestDTO createBasicSvcRequestDTO,BusinessTypeEnum businessType) {
        super(createBasicSvcRequestDTO.getEmsId());
        this.createBasicSvcRequestDTO = createBasicSvcRequestDTO;
        this.businessType = businessType;
        //入参校验
        if (!this.ok) {
            return;
        }
        pointA = createBasicSvcRequestDTO.getSourcePoint();
        pointZ = createBasicSvcRequestDTO.getDestPoint();
        if (NeTypeEnum.CO == pointA.getExtensionType() || NeTypeEnum.CO == pointZ.getExtensionType()) {
            isOnceCpe = true;
        }
        ProtectionPoint protectionPoint = createBasicSvcRequestDTO.getProtection();
        if (protectionPoint != null && protectionPoint.getEnable()) {
            isProtected = true;
        }
        if (protectionPoint != null && protectionPoint.getSegmentProtect()!=null && protectionPoint.getSegmentProtect()) {
            segmentProtect = true;
        }
        if (protectionPoint != null) {
            sdFlag = protectionPoint.getSdFlag() != null ? protectionPoint.getSdFlag() : SdFlagEnum.ENABLE;
        }
    }

    /**
     * 创建业务
     * @param teTunnels Te隧道
     * @return
     */
    protected abstract void create(List<TeTunnel> teTunnels);

    /**
     * 预制隧道
     * @return
     */
    protected List<TeTunnel> preBuild() {

        List<TeTunnel> teTunnels = new ArrayList<>();
        if (!ok) {
            return teTunnels;
        }
        TeTunnel teTunnel = buildTeTunnel();
        if (!ok) {
            return teTunnels;
        }
        //资源具备下发时不需要指定
        //   setBandwidth(teTunnel);
        setTeTunnelPoint(teTunnel);

        RequestOfCreateTeTunnel request = new RequestOfCreateTeTunnel();
        request.setTunnels(new ArrayList<>());
        request.getTunnels().add(teTunnel);
        createTunnel(request);
        if (isOk()) {
            teTunnels.add(teTunnel);
        }
        return teTunnels;
    }

    /**
     * 构建TE隧道入参
     * @return
     */
    protected TeTunnel buildTeTunnel() {

        TeTunnel teTunnel = new TeTunnel();
        teTunnel.setName(PrimaryKeyUtil.getKey(createBasicSvcRequestDTO.getUuid() + "-teTunnel"));
        teTunnel.setEncoding("ietf-te-types:lsp-encoding-oduk");//默认值
        teTunnel.setTitle(createBasicSvcRequestDTO.getSncName() + "-TeTunnel");
        teTunnel.setSwitchingType("ietf-te-types:switching-otn");//默认值
//        teTunnel.setProvisioningState("ietf-te-types:tunnel-state-down");//默认值
        teTunnel.setProvisioningState("ietf-te-types:tunnel-admin-state-down");//默认值
        TeTopologyIdentifier teTopologyIdentifier = new TeTopologyIdentifier();
        teTopologyIdentifier.setClientId(ActnParamConstants.CLIENT_ID);
        teTopologyIdentifier.setProviderId(ActnParamConstants.PROVIDER_ID);
        teTopologyIdentifier.setTopologyId(ActnParamConstants.TOPOLOGY_ID_ELEC);//默认值
        teTunnel.setTeTopologyIdentifier(teTopologyIdentifier);
        //保护节点信息
        Protection teTunnelProtection = getProtection();
        //恢复
        Restoration restoration = getRestoration();

        teTunnel.setProtection(teTunnelProtection);
        teTunnel.setRestoration(restoration);
        return teTunnel;
    }

    /**
     * 设置隧道节点 包括两端和必经
     * @param teTunnel
     */
    protected void setTeTunnelPoint(TeTunnel teTunnel) {

        if (teTunnel == null) {
            addErrInfo("teTunnel为空", null);
            return;
        }
        setBandwidth(teTunnel);
        setPath(teTunnel, 1);
        if (createBasicSvcRequestDTO.getProtection() != null && createBasicSvcRequestDTO.getProtection().getEnable()) {
            setPath(teTunnel, 2);
        }
        //FIXME 待设置必经参数
        if(!isOnceCpe){
            teTunnel.setSource(this.pointA.getNeId());
            teTunnel.setDestination(this.pointZ.getNeId());
            P2pPath p2pPath = teTunnel.getP2pPrimaryPaths().getP2pPrimaryPath().get(0);
            //若没有必经避让点，则p2p-primary-paths设置null
            if(p2pPath.getExplicitRouteObjects()==null && p2pPath.getOptimizations()==null && p2pPath.getPathMetricBounds()==null){
                teTunnel.setP2pPrimaryPaths(null);
            }
            if (isProtected) {
                if(teTunnel.getP2pSecondaryPaths().getP2pSecondaryPath().get(0).getExplicitRouteObjects()==null){
                    teTunnel.setP2pSecondaryPaths(null);
                }
            }

        } else {
            //资源不具备 CO-*下发
            teTunnel.setSource(NeTypeEnum.CPE == pointA.getExtensionType() ? pointA.getNeId() : ActnParamConstants.ONCE_CPE_NAME);
            teTunnel.setDestination(NeTypeEnum.CPE == pointZ.getExtensionType() ? pointZ.getNeId() : ActnParamConstants.ONCE_CPE_NAME);
        }
    }

    /**
     * 指定带宽和承载容器
     * @param teTunnel
     */
    protected void setBandwidth(TeTunnel teTunnel) {

        TeBandwidth teBandwidth = new TeBandwidth();
        //通过AZ端接口类型和带宽获取对应的承载粒度
        String graininess = ActnBussinessUtil.getGraininess(pointA.getAccessPointType(), pointZ.getAccessPointType(), createBasicSvcRequestDTO.getBandwidth());
        if ( OduTypeEnum.ODU_FLEX.getValue().equals(graininess)) {
            teBandwidth.setIetfotntunnelconnectionbandwidth(String.valueOf(createBasicSvcRequestDTO.getBandwidth()));
        }
        teBandwidth.setIetfOtnTunnelOduType(graininess);
        teTunnel.setTeBandwidth(teBandwidth);
    }

    /**
     * 获取保护节点
     * @return
     */
    protected Protection getProtection() {

        Protection protection = new Protection();
        if (isProtected) {
            ProtectionPoint protectionPoint = createBasicSvcRequestDTO.getProtection();
            protection.setEnable(true);
            //FIXME 暂默认为1+1
            protection.setProtectionType(ProtectionTypeEnum.ONE_PLUS_ONE.getValue());
            //设置备用路由自动返回主用路由
            protection.setProtectionReversionDisable(protectionPoint.getReversionDisable());
            protection.setWaitToRevert(protectionPoint.getWaitToRevert() != null ? protectionPoint.getWaitToRevert() : DEFAULTWAITTOREVERT);
            protection.setHoldOffTime(protectionPoint.getHoldOffTime() != null ? protectionPoint.getHoldOffTime() : DEFAULTHOLDOFFTIME);
            protection.setSegmentprotect(segmentProtect);
            protection.setTcmlayer(segmentProtect ? "ietf-te-types:tcm1" : "ietf-te-types:pm");
            protection.setSncptype(segmentProtect ? "ietf-te-types:snc-s" : "ietf-te-types:snc-n");
            protection.setSdflag(sdFlag.getValue());
        } else {
            protection.setEnable(false);
            protection.setProtectionType(ProtectionTypeEnum.UNPROTECTED.getValue());
        }

        return protection;
    }

    /**
     * 恢复节点-重路由场景
     * @return
     */
    protected Restoration getRestoration() {
        Restoration restoration = new Restoration();
        restoration.setHoldOffTime(0);
        restoration.setRestorationType("ietf-te-types:lsp-restoration-restore-any");
        restoration.setWaitToRevert(0);
        restoration.setRestorationReversionDisable(true);
        restoration.setEnable(false);
        return restoration;
    }

    /**
     * 下发NCE 创建隧道
     * @param request
     */
    protected void createTunnel(RequestOfCreateTeTunnel request) {
        log.info("预置隧道入参：{}", JSON.toJSONString(request, SerializerFeature.NotWriteDefaultValue));
        TeTunnelClientImpl teTunnelService = new TeTunnelClientImpl(this.actnMacEmsDTO);
        StatusResponse status = teTunnelService.createTeTunnel(request);
        this.ok = status.isOk();
        if (!status.isOk()) {
            addErrInfo("【" + createBasicSvcRequestDTO.getSncName() + "】预制隧道失败：" + status.getDesc(), null);
        } else {
            log.info("【{}】预制隧道成功", createBasicSvcRequestDTO.getSncName());
        }
    }

    /**
     * 转换成ACTN的下发参数
     */
    protected void transACTN() {

        List<PointDTO> usePoints = new ArrayList<>();
        usePoints.add(this.pointA);
        usePoints.add(this.pointZ);
        if (createBasicSvcRequestDTO.getIncludedRestrictedResources() != null) {
            usePoints.addAll(createBasicSvcRequestDTO.getIncludedRestrictedResources());
        }
        if (createBasicSvcRequestDTO.getExcludedRestrictedResources() != null) {
            usePoints.addAll(createBasicSvcRequestDTO.getExcludedRestrictedResources());
        }
        //添加保护必经避让的转换对象
        if (createBasicSvcRequestDTO.getProtection() != null && createBasicSvcRequestDTO.getProtection().getEnable()) {
            if (createBasicSvcRequestDTO.getProtection()
                    .getIncludedRestrictedResources() != null) {
                usePoints.addAll(createBasicSvcRequestDTO.getProtection()
                        .getIncludedRestrictedResources());
            }
            if (createBasicSvcRequestDTO.getProtection()
                    .getExcludedRestrictedResources() != null) {
                usePoints.addAll(createBasicSvcRequestDTO.getProtection()
                        .getExcludedRestrictedResources());
            }
        }
        //批量转换Point
        transPoint(usePoints);
    }

    /**
     * 设置路径
     * @param major 1：主 2：备
     */
    protected void setPath(TeTunnel teTunnel, int major) {
        if(major == 1){
            //主路径
            NceMethodCommon.setPrimaryPaths(teTunnel,pointA,pointZ,createBasicSvcRequestDTO,businessType);
        }else{
            //备路径
            NceMethodCommon.setSecondaryPaths(teTunnel,pointA,pointZ,createBasicSvcRequestDTO,businessType);
        }

    }

    @Override
    protected void _exec() {

        transACTN();
        if (!isOk()) {
           return;
        }
        List<TeTunnel> teTunnels = preBuild();
        if (!isOk()) {
            return;
        }
        create(teTunnels);
    }

}
