package com.cttnet.ywkt.actn.capacity.adapter.common;

import com.cttnet.common.util.SysConfig;
import com.cttnet.ywkt.actn.bean.eth.sub.EthtSvcAccessPoint;
import com.cttnet.ywkt.actn.bean.eth.sub.EthtSvcEndPoint;
import com.cttnet.ywkt.actn.bean.eth.sub.IngressEgressBandwidthProfile;
import com.cttnet.ywkt.actn.bean.eth.sub.Tag;
import com.cttnet.ywkt.actn.bean.tunnel.TeTunnel;
import com.cttnet.ywkt.actn.bean.tunnel.sub.*;
import com.cttnet.ywkt.actn.capacity.constants.ActnParamConstants;
import com.cttnet.ywkt.actn.data.dto.request.CreateBasicSvcRequestDTO;
import com.cttnet.ywkt.actn.data.dto.request.PointDTO;
import com.cttnet.ywkt.actn.data.dto.request.RoutingStrategy;
import com.cttnet.ywkt.actn.enums.AccessPointTypeEnum;
import com.cttnet.ywkt.actn.enums.BusinessTypeEnum;
import com.cttnet.ywkt.actn.enums.NeTypeEnum;
import com.cttnet.ywkt.actn.enums.PathScope;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: </p>
 *
 * @author suguisen
 */
public class NceMethodCommon {

    /**
     * A端vlan
     */
    public static final Integer DEFAULT_VLAN_A = StringUtils.isBlank(SysConfig.getProperty("hw.actn.nce-t.default-vlan-a")) ? null : Integer.valueOf(SysConfig.getProperty("hw.actn.nce-t.default-vlan-a"));

    /**
     * Z端vlan
     */
    public static final Integer DEFAULT_VLAN_Z = StringUtils.isBlank(SysConfig.getProperty("hw.actn.nce-t.default-vlan-z")) ? null : Integer.valueOf(SysConfig.getProperty("hw.actn.nce-t.default-vlan-z"));

    /**
     * <p>Description: 公共方法setEthtSvcEndPoint</p>
     *
     * @param ethtSvcEndPointName
     * @param point
     * @return
     * @author suguisen
     */
    public static EthtSvcEndPoint setEthtSvcEndPoint(CreateBasicSvcRequestDTO createBasicSvcDto, String ethtSvcEndPointName, PointDTO point) {

        EthtSvcEndPoint ethtSvcEndPoint = new EthtSvcEndPoint();
        ethtSvcEndPoint.setEthtSvcEndPointName(ethtSvcEndPointName);

        List<EthtSvcAccessPoint> ethtSvcAccessPoints = new ArrayList<>();
        EthtSvcAccessPoint ethtSvcAccessPoint = new EthtSvcAccessPoint();
        ethtSvcAccessPoint.setAccessPointId("0");//FIXME  接入端序号信息 待改
        //端口ID access-ltp-id
        ethtSvcAccessPoint.setAccessLtpId(point.getPortId());
        if (NeTypeEnum.CPE == point.getExtensionType()) {
            ethtSvcAccessPoint.setAccessNodeId(point.getNeId());
        } else {
            ethtSvcAccessPoint.setAccessNodeId(ActnParamConstants.ONCE_CPE_NAME);
        }
        //接口类型设置
        AccessPointTypeEnum pointType = point.getAccessPointType();
        if (pointType != null) {
            //通过AZ端接口类型获取对应的以太业务信号
            String pointTypechange = pointType.getPointType().replace("ietf-trans-client-svc-types", "ietf-eth-tran-types");
            ethtSvcAccessPoint.setAccessPointType(pointTypechange);
        }

        ethtSvcAccessPoints.add(ethtSvcAccessPoint);
        ethtSvcEndPoint.setEthtSvcAccessPoints(ethtSvcAccessPoints);

        /** 当vlan没值则用EPL业务(不分层)下发 和 当vlan有值则用EVPL业务下发  */
        if (point.getVlan() == null) {
            point.setVlan(ethtSvcEndPointName.equals("0") ? DEFAULT_VLAN_A : DEFAULT_VLAN_Z);
        }
        //注意：这里上下if不能整合一起
        if (point.getVlan() != null) {
            ethtSvcEndPoint.setServiceClassificationType("ietf-eth-tran-types:vlan-classification");
            Tag outerTag = new Tag();
            outerTag.setTagType("ietf-eth-tran-types:classify-c-vlan");
            outerTag.setVlanValue(point.getVlan());
            ethtSvcEndPoint.setOuterTag(outerTag);
        } else {
            ethtSvcEndPoint.setServiceClassificationType("ietf-eth-tran-types:port-classification");
        }

        IngressEgressBandwidthProfile ingressEgressBandwidthProfile = new IngressEgressBandwidthProfile();
        ingressEgressBandwidthProfile.setBandwidthProfileType("ietf-eth-tran-types:mef-10-bwp");
        ingressEgressBandwidthProfile.setCIR(String.valueOf(createBasicSvcDto.getBandwidth()));
//        String eir = createBasicSvcDto.getEirBandwidth() == null ? "0" : String.valueOf(createBasicSvcDto.getEirBandwidth());
        //可扩展带宽
        ingressEgressBandwidthProfile.setEIR("0");
        ethtSvcEndPoint.setIngressEgressBandwidthProfile(ingressEgressBandwidthProfile);
        return ethtSvcEndPoint;
    }

    /**
     * <p>Description: 设置主路径</p>
     *
     * @author suguisen
     */
    public static void setPrimaryPaths(TeTunnel teTunnel, PointDTO sourcePoint, PointDTO destinationPoint, CreateBasicSvcRequestDTO createBasicSvcDto, BusinessTypeEnum businessType) {
        P2pPrimaryPaths p2pPrimaryPaths = new P2pPrimaryPaths();
        teTunnel.setP2pPrimaryPaths(p2pPrimaryPaths);
        List<P2pPath> p2pPrimaryPathList = new ArrayList<>();
        p2pPrimaryPaths.setP2pPrimaryPath(p2pPrimaryPathList);

        P2pPath p2pPrimaryPath = new P2pPath();
        p2pPrimaryPath.setName("primary-path");//默认值
        if (NeTypeEnum.CPE.getValue() == sourcePoint.getExtensionType().getValue() && NeTypeEnum.CPE.getValue() == destinationPoint.getExtensionType().getValue()) {
            p2pPrimaryPath.setPathScope(PathScope.ENDTOEND.getValue());//CPE-CPE 都填endtoend
        } else {
            p2pPrimaryPath.setPathScope(PathScope.SEGMENT.getValue());//只要不是CPE-CPE 都填segment
        }
        ExplicitRouteObject explicitRouteObjects = new ExplicitRouteObject();
        p2pPrimaryPath.setExplicitRouteObjects(explicitRouteObjects);
        p2pPrimaryPathList.add(p2pPrimaryPath);

        List<RouteObjectIncludeExclude> routeObjectIncludeExcludeList = new ArrayList<>();
        explicitRouteObjects.setRouteObjectIncludeExclude(routeObjectIncludeExcludeList);

        //TODO 必经测试
        /*List<Point> points = new ArrayList<>();
        Point point = new Point();
        point.setNeId("0.188.0.7");//eoo eos client
        point.setPortId("251592706");//eoo client
        //point.setPortId("100597811");//eos
        points.add(point);
        createBasicSvcDto.setIncludedRestrictedResources(points);*/

        //TODO 避让测试
        /*List<Point> points = new ArrayList<>();
        Point point = new Point();
        point.setNeId("0.188.0.7");//eoo eos client
        point.setPortId("251592706");//eoo client
//        point.setPortId("100597811");//eos
        points.add(point);
        createBasicSvcDto.setExcludedRestrictedResources(points);*/
        //设置主备路径公共部分
        setPrimaryOrSecondaryPathsCommon(sourcePoint, destinationPoint, teTunnel, routeObjectIncludeExcludeList, createBasicSvcDto,businessType);

        //算路策略(只加主路径)
        RoutingStrategy routingStrategy = createBasicSvcDto.getRoutingStrategy();
        if (routingStrategy != null) {
            //算路策略按最小xx算路
            if (routingStrategy.getMinRoutingStrategy() != null) {
                Optimizations optimizations = new Optimizations();
                p2pPrimaryPath.setOptimizations(optimizations);
                optimizations.setOptimizationMetric(new ArrayList<>());
                OptimizationMetric optimizationMetric = new OptimizationMetric();
                optimizationMetric.setMetricType(routingStrategy.getMinRoutingStrategy().getValue());
                optimizations.getOptimizationMetric().add(optimizationMetric);
            }
            //门限
            if (routingStrategy.getMaxRoutingStrategy() != null) {
                Long maxNum = routingStrategy.getMaxNum();
                PathMetricBounds pathMetricBounds = new PathMetricBounds();
                p2pPrimaryPath.setPathMetricBounds(pathMetricBounds);
                List<PathMetricBound> pathMetricBoundsList = new ArrayList<>();
                PathMetricBound pathMetricBound = new PathMetricBound();
                pathMetricBound.setMetricType(routingStrategy.getMaxRoutingStrategy().getValue());
                pathMetricBound.setUpperBound(maxNum == null ? 0L : maxNum); //不需设值 即默认为0
                pathMetricBoundsList.add(pathMetricBound);
                pathMetricBounds.setPathMetricBoundList(pathMetricBoundsList);
            }
        }

        if (routeObjectIncludeExcludeList.isEmpty()) {
            p2pPrimaryPath.setExplicitRouteObjects(null);
        }

    }


    /**
     * <p>Description: 设置备路径</p>
     *
     * @author suguisen
     */
    public static void setSecondaryPaths(TeTunnel teTunnel, PointDTO sourcePoint, PointDTO destinationPoint, CreateBasicSvcRequestDTO createBasicSvcDto, BusinessTypeEnum businessType) {
        P2pSecondaryPaths p2pSecondaryPaths = new P2pSecondaryPaths();
        List<P2pPath> p2pSecondaryPathList = new ArrayList<>();
        teTunnel.setP2pSecondaryPaths(p2pSecondaryPaths);
        p2pSecondaryPaths.setP2pSecondaryPath(p2pSecondaryPathList);

        P2pPath p2pSecondaryPath = new P2pPath();
        p2pSecondaryPath.setName("secondary-path");//默认值
        if (NeTypeEnum.CPE.getValue() == sourcePoint.getExtensionType().getValue() && NeTypeEnum.CPE.getValue() == destinationPoint.getExtensionType().getValue()) {
            p2pSecondaryPath.setPathScope(PathScope.ENDTOEND.getValue());//CPE-CPE 都填endtoend
        } else {
            p2pSecondaryPath.setPathScope(PathScope.SEGMENT.getValue());//只要不是CPE-CPE 都填segment
        }
        ExplicitRouteObject explicitRouteObjects = new ExplicitRouteObject();
        p2pSecondaryPath.setExplicitRouteObjects(explicitRouteObjects);
        p2pSecondaryPathList.add(p2pSecondaryPath);

        List<RouteObjectIncludeExclude> routeObjectIncludeExcludeList = new ArrayList<>();
        explicitRouteObjects.setRouteObjectIncludeExclude(routeObjectIncludeExcludeList);

        //设置主备路径公共部分
        setPrimaryOrSecondaryPathsCommon(sourcePoint, destinationPoint, teTunnel, routeObjectIncludeExcludeList, createBasicSvcDto, businessType);

        if (routeObjectIncludeExcludeList.isEmpty()) {
            p2pSecondaryPath.setExplicitRouteObjects(null);
        } else if (StringUtils.isNotBlank(teTunnel.getEncoding())) {
            //TODO 特殊处理 A级业务的即插即用（简单来说，就是预置ODUk隧道里面，备路径里面不填节点信息了。）

            //A级业务的即插即用，预置ODUk隧道里面，备路径里面不填节点信息了(A/AAA电路都为A级电路)
            if ("A".equals(createBasicSvcDto.getAvailableRatioGrade()) || "AAA".equals(createBasicSvcDto.getAvailableRatioGrade())) {
                if (teTunnel.getEncoding().contains("oduk")) {
                    /* otn 共享时隙 */
                    teTunnel.setShareTimeslotOtn(true);
                } else {
                    /* sdh 共享时隙 */
                    teTunnel.setShareTimeslotSdh(true);
                }
                if (NeTypeEnum.CO.getValue() == sourcePoint.getExtensionType().getValue()) {
                    routeObjectIncludeExcludeList.get(0).getUnnumberedHop().setNodeId(ActnParamConstants.ONCE_CPE_NAME);
                    routeObjectIncludeExcludeList.get(1).getLabelHop().getTeLabel().setIetfOtnTunnelTpn(0);
                }
                if (NeTypeEnum.CO.getValue() == destinationPoint.getExtensionType().getValue()) {
                    routeObjectIncludeExcludeList.get(routeObjectIncludeExcludeList.size() - 2).getUnnumberedHop().setNodeId(ActnParamConstants.ONCE_CPE_NAME);
                    routeObjectIncludeExcludeList.get(routeObjectIncludeExcludeList.size() - 1).getLabelHop().getTeLabel().setIetfOtnTunnelTpn(0);
                }
            }
        }
    }


    /**
     * <p>Description: 设置主备路径公共部分（源端+必经+避让+宿端）</p>
     *
     * @author suguisen
     */
    private static void setPrimaryOrSecondaryPathsCommon(PointDTO sourcePoint, PointDTO destinationPoint, TeTunnel teTunnel, List<RouteObjectIncludeExclude> routeObjectIncludeExcludeList, CreateBasicSvcRequestDTO crossConnection, BusinessTypeEnum businessType) {

        String routeIncludeEro = "ietf-te-types:route-include-ero";
        String routeExcludeEro = "ietf-te-types:route-exclude-ero";
        String hopType_LOOSE = "LOOSE"; //源宿端使用
        String hopType_STRICT = "STRICT";//必经避让节点使用

        //注意RouteObjectIncludeExclude:（unnumbered-hop作为源端使用时，需要为首跳，hop-type为LOOSE，label-hop为第二跳。如果unnumbered-hop作为宿端使用时，需要作为倒数第二跳，hop-type为LOOSE，label-hop为倒数第二跳）
        //源端
        if (NeTypeEnum.CO.getValue() == sourcePoint.getExtensionType().getValue()) {
            teTunnel.setSource(ActnParamConstants.ONCE_CPE_NAME);//默认值
            RouteObjectIncludeExclude routeIncludeA = setRouteObjectIncludeExclude(0, routeIncludeEro, hopType_LOOSE, sourcePoint,businessType);
            routeObjectIncludeExcludeList.add(routeIncludeA);
            RouteObjectIncludeExclude routeIncludeLabelHopA = setRouteObjectIncludeExclude(1, routeIncludeEro, null, sourcePoint, businessType);
            routeObjectIncludeExcludeList.add(routeIncludeLabelHopA);
        } else {
            teTunnel.setSource(sourcePoint.getNeId());
        }

        List<PointDTO> includedRestrictedResourceList = crossConnection.getIncludedRestrictedResources();//必经
        List<PointDTO> excludedRestrictedResourceList = crossConnection.getExcludedRestrictedResources();//避让
        //必经
        if (includedRestrictedResourceList != null && !includedRestrictedResourceList.isEmpty()) {
            for (PointDTO point : includedRestrictedResourceList) {
                RouteObjectIncludeExclude routeIncludeHopTypeSTRICT = setRouteObjectIncludeExclude(routeObjectIncludeExcludeList.size(), routeIncludeEro, hopType_STRICT, point, businessType);
                routeObjectIncludeExcludeList.add(routeIncludeHopTypeSTRICT);
            }
        }
        //避让
        if (excludedRestrictedResourceList != null && !excludedRestrictedResourceList.isEmpty()) {
            for (PointDTO point : excludedRestrictedResourceList) {
                RouteObjectIncludeExclude routeExcludeHopTypeSTRICT = setRouteObjectIncludeExclude(routeObjectIncludeExcludeList.size(), routeExcludeEro, hopType_STRICT, point, businessType);
                routeObjectIncludeExcludeList.add(routeExcludeHopTypeSTRICT);
            }
        }

        if (NeTypeEnum.CO.getValue() == destinationPoint.getExtensionType().getValue()) {
            teTunnel.setDestination(ActnParamConstants.ONCE_CPE_NAME);//默认值
            RouteObjectIncludeExclude routeIncludeZ = setRouteObjectIncludeExclude(routeObjectIncludeExcludeList.size(), routeIncludeEro, hopType_LOOSE, destinationPoint, businessType);
            routeObjectIncludeExcludeList.add(routeIncludeZ);
            RouteObjectIncludeExclude routeIncludeLabelHopZ = setRouteObjectIncludeExclude(routeObjectIncludeExcludeList.size(), routeIncludeEro, null, destinationPoint, businessType);
            routeObjectIncludeExcludeList.add(routeIncludeLabelHopZ);
        } else {
            teTunnel.setDestination(destinationPoint.getNeId());
        }

    }

    /**
     * <p>Description: 对routeObjectIncludeExclude 设置,带保护及不带保护都一样了,必经避让都一样</p>
     *
     * @author suguisen
     */
    private static RouteObjectIncludeExclude setRouteObjectIncludeExclude(int num, String explicitRouteUsage, String hopType, PointDTO point, BusinessTypeEnum businessType) {

        String hopType_LOOSE = "LOOSE"; //源宿端使用
        RouteObjectIncludeExclude routeObjectIncludeExclude = new RouteObjectIncludeExclude();
        routeObjectIncludeExclude.setIndex(num);
        routeObjectIncludeExclude.setExplicitRouteUsage(explicitRouteUsage);
        if (hopType == null) {//主备第二跳才会使用到
            LabelHop labelHop = new LabelHop();
            TeLabel teLabel = new TeLabel();
            //co没有指定端口 ietf-otn-tunnel:tpn 为 0 ，指定端口则为 1
            teLabel.setIetfOtnTunnelTpn(StringUtils.isNotBlank(point.getPortId()) && !point.getPortId().equals("0") ? 1 : 0);
            labelHop.setTeLabel(teLabel);
            routeObjectIncludeExclude.setLabelHop(labelHop);
        } else {
            UnnumberedHop unnumberedHop = new UnnumberedHop();
            unnumberedHop.setHopType(hopType);
            unnumberedHop.setNodeId(point.getNeId());
            String linkTpId;
            //optical-100ge用otu4  其他都用otu2吧
            if (hopType_LOOSE.equals(hopType)) {
                //仅源宿节点CO添加
                linkTpId = "0";
                AccessPointTypeEnum pointType = point.getAccessPointType();
                if (pointType != null) {
                    //通过AZ端接口类型获取对应的以太业务信号
                    String otuPortType = pointType.getPointType().contains("optical-100ge") ? "ietf-otn-types:port-otu4" : "ietf-otn-types:port-otu2";
                    if(BusinessTypeEnum.ETH_EOS == businessType || BusinessTypeEnum.SDH == businessType){
                        unnumberedHop.setSdhTunnelSignalType(otuPortType);
                    }else{
                        unnumberedHop.setOtuPortType(otuPortType);
                    }
                }
            } else {
                //必经避让 节点CO添加
                linkTpId = point.getPortId();
            }
            unnumberedHop.setLinkTpId(linkTpId);
            routeObjectIncludeExclude.setUnnumberedHop(unnumberedHop);
        }
        return routeObjectIncludeExclude;
    }


}
