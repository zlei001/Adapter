package com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.connection;

import com.catt.zhwg.ws.parser.bean.vo.WXPResult;
import com.catt.zhwg.ws.parser.services.WsParser;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.AbstractCmdAdapter;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.bo.CmdResult;
import com.cttnet.zhwg.ywkt.mtosi.ability.constants.Splits;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.*;
import com.cttnet.zhwg.ywkt.mtosi.ability.utils.BusinessUtils;
import com.cttnet.zhwg.ywkt.mtosi.ability.utils.ObjectTools;
import com.cttnet.zhwg.ywkt.mtosi.ability.utils.PortUtils;
import com.cttnet.zhwg.ywkt.mtosi.utils.EnumUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * <pre>
 *  创建并激活snc
 * </pre>
 *
 * @author wangzhaoshsi
 * @date 2020/2/18
 * @since java 1.8
 */
public class CreateAndActiveSncAdapter extends AbstractCmdAdapter {

    /**
     * 网元型号
     */
    private static final String[] NE_TYPE = new String[]{"FONST 1000", "FONST 2000", "FONST 3000",
            "FONST 4000", "FONST 5000" };

    @Override
    protected void createKvs() {

        //调整参数方向
        adjustParam();
        //固定参数
        Map<String, String> ccMap = this.command.getKvs();
        ccMap.put("userLabel",  MapUtils.getString(this.param, "S_USER_LABEL"));
        ccMap.put("isForceUniqueness", MapUtils.getString(this.param, "I_FORCE_UNIQUENESS", "false"));
        ccMap.put("owner",  MapUtils.getString(this.param, "S_OWNER"));
        ccMap.put("aliasNameList",  MapUtils.getString(this.param, "aliasNameList"));
        ccMap.put("/E/B/c/c/vendorExtensions", BusinessUtils.toNvsList(MapUtils.getMap(this.param, "vendorExtensions")));
        //FIXME  name 具体含义？ I2标准中无该定义
        if (FactoryName.FH != getFactoryName()) {
            ccMap.put("name", MapUtils.getString(this.param, "sncName"));
        }
        ccMap.put("/E/B/c/c/direction",  MapUtils.getString(this.param, "I_DIRECTION", ""));
        ccMap.put("staticProtectionLevel", MapUtils.getString(this.param, "I_STATIC_PROT_LEVEL", "UNPROTECTED"));
        ccMap.put("rerouteAllowed", MapUtils.getString(this.param, "I_REROUTE_ALLOWED", "RR_NA"));
        ccMap.put("networkRouted", MapUtils.getString(this.param, "I_NETWORK_ROUTED", "NR_NA"));
        ccMap.put("/E/B/c/c/type",  MapUtils.getString(this.param, "I_CC_TYPE", ""));
        ccMap.put("/E/B/c/c/layerRate", MapUtils.getString(this.param, "layerRate", "LR_DIGITAL_SIGNAL_RATE"));
        // 必经
        ccMap.put("inclusionResourceRefList", MapUtils.getString(this.param,  "S_NE_TP_INCLUSIONS"));
        // 是否全路由
        ccMap.put("isFullRoute", MapUtils.getString(this.param, "I_FULLROUTE", "false"));
        // 避让
        ccMap.put("exclusionResourceRefList", MapUtils.getString(this.param, "S_NE_TP_SNC_EXCLUS"));
        ccMap.put("aEndTpRefList", MapUtils.getString(this.param, "S_A_END_VENDOR_NAME"));
        ccMap.put("zEndTpRefList", MapUtils.getString(this.param, "S_Z_END_VENDOR_NAME"));
        ccMap.put("tolerableImpact", MapUtils.getString(this.param, "I_TOLERABLE_IMPACT", ""));
        ccMap.put("osFreedomLevel", MapUtils.getString(this.param, "I_EMSFREEDOM_LEVEL", ""));

        // FIXME: 非华为、烽火厂家目前不支持直接配置ODU0或ODU4
        String layerRate = MapUtils.getString(this.param, "layerRate", "LR_DIGITAL_SIGNAL_RATE");
        if(!(FactoryName.HW == getFactoryName()) && !(FactoryName.FH == getFactoryName())) {
            if("LR_OCH_Data_Unit_0".equals(layerRate) ||
                    "LR_OCH_Data_Unit_4".equals(layerRate)) {
                ccMap.put("/E/B/c/c/layerRate", "VENDOR_EXT");
                ccMap.put("/E/B/c/c/layerRate.extension", layerRate);
            }
        }
        List<Map<String, Object>> majorCcList = (List<Map<String, Object>>) this.param.get("majorCcList");
        List<Map<String, Object>> backupCcList = (List<Map<String, Object>>) this.param.get("backupCcList");

        String ccInclusionList ="";

        // cc节点公共参数，isActive=true，direction=CD_UNI，ccType=ST_SIMPLE，这里是按照模板赋值顺序排的参数位置
        String commonCcHead = "true=CD_UNI~ST_SIMPLE=";
        // 主用路径
        if( majorCcList != null && !majorCcList.isEmpty() ){
            String obverseInfo = "~Direction=Obverse~ProtectionRole=Work";
            String reverseInfo = "~Direction=Reverse~ProtectionRole=Work";
            for( Map<String, Object> ccPath : majorCcList ){
                String a_Ref = MapUtils.getString(ccPath, "aRef");
                String z_Ref = MapUtils.getString(ccPath, "zRef");
                ccInclusionList += commonCcHead;
                ccInclusionList += ( a_Ref + z_Ref );
                ccInclusionList += obverseInfo;
                ccInclusionList += Splits.SHARP;
                ccInclusionList += commonCcHead;
                ccInclusionList += ( z_Ref + a_Ref );
                ccInclusionList += reverseInfo;
                ccInclusionList += Splits.SHARP;

            }
        }
        // 备用路径
        if( backupCcList != null && !backupCcList.isEmpty() ){
            String obverseInfo = "~Direction=Obverse~ProtectionRole=Protection";
            String reverseInfo = "~Direction=Reverse~ProtectionRole=Protection";
            for( Map<String, Object> ccPath : backupCcList ){
                String a_Ref = MapUtils.getString(ccPath, "aRef");
                String z_Ref = MapUtils.getString(ccPath, "zRef");
                ccInclusionList += commonCcHead;
                ccInclusionList += ( a_Ref + z_Ref );
                ccInclusionList += obverseInfo;
                ccInclusionList += Splits.SHARP;
                ccInclusionList += commonCcHead;
                ccInclusionList += ( z_Ref + a_Ref );
                ccInclusionList += reverseInfo;
                ccInclusionList += Splits.SHARP;
            }
        }
        ccMap.put("ccInclusionList",ccInclusionList);


    }

    /**
     * cmd
     *
     * @return cmd
     */
    @Override
    protected Cmd getCmd() {
        return Cmd.createAndActivateSubnetWorkConnection;
    }

    /**
     * 调整参数
     */
    private void adjustParam() {
        String layerRate = MapUtils.getString(this.param, "layerRate", "LR_DIGITAL_SIGNAL_RATE");
        String aEndTpRefList = MapUtils.getString(this.param, "S_A_END_VENDOR_NAME");
        String zEndTpRefList = MapUtils.getString(this.param, "S_Z_END_VENDOR_NAME");
        String aEndTimeSlot = MapUtils.getString(this.param, "S_A_END_TIME_SLOT");
        String zEndTimeSlot = MapUtils.getString(this.param, "S_Z_END_TIME_SLOT");
        String staticProtectionLevel = MapUtils.getString(this.param, "I_STATIC_PROT_LEVEL", "UNPROTECTED");

        Map<String, String> vendorExtensions = new HashMap<String, String>(); {
//               vendorExtensions.put("sncpNodeNameList", MapUtils.getString(this.param, "sncpNodeList", ""));
            if(!PortUtils.isODU(layerRate)) {
                vendorExtensions.put("ClientServiceType", MapUtils.getString(this.param, "ClientServiceType", "ClientServiceType"));
                vendorExtensions.put("ClientServiceContainer", MapUtils.getString(this.param, "ClientServiceContainer", "ClientServiceContainer"));
                vendorExtensions.put("ClientServiceMappingMode", MapUtils.getString(this.param, "ClientServiceMappingMode", "ClientServiceMappingMode"));
            }
            if(aEndTpRefList.contains(Splits.SHARP) || zEndTpRefList.contains(Splits.SHARP)){
                vendorExtensions.put("holdOffTime", MapUtils.getString(this.param, "holdOffTime", "holdOffTime"));
                vendorExtensions.put("wtrTime", MapUtils.getString(this.param, "wtrTime", "wtrTime"));
                vendorExtensions.put("monitorType", MapUtils.getString(this.param, "monitorType", "monitorType"));
                String reversionMode = MapUtils.getString(this.param, "reversionMode", "reversionMode");
                if(!reversionMode.startsWith("RM_")){
                    reversionMode = "RM_" + reversionMode;
                }
                vendorExtensions.put("reversionMode", reversionMode);
                vendorExtensions.put("protectionType", MapUtils.getString(this.param, "protectionType", "protectionType"));

            }
        }
        //TODO  待补充查服务，获取网元信息
//        String neModel = NetElementDao.getNeModel(emsname, nativeNeName);
        String neModel = "";

        boolean is1kTo5kNe = false;
        for (int i = 0; i < NE_TYPE.length; i++) {
            if(NE_TYPE[i].equals(neModel)){
                is1kTo5kNe = true;
                break;
            }
        }
        if(is1kTo5kNe == false){
            vendorExtensions.putAll(BusinessUtils.toTimeSlotMap(aEndTimeSlot, zEndTimeSlot));
        }

        /*
         *  仅烽火的逻辑.
         *  当 aEndTpRefList 或 zEndRefList 其中一个存在一份以上时（最多两份）触发.
         *  此时 staticProtectionLevel = "FULLY_PROTECTED"
         *
         *  1.aEndRefList = a1##a2,  zEndRefList = z1##z2  时，
         *  	aEndRefList = a1,  zEndRefList = z1
         *  	sncpNodeList = a2,z2 (同时[~]替换为[;],并去掉开头的[~])
         *  2.aEndRefList = a,  zEndRefList = z1##z2  时，
         *  	aEndRefList = a,  zEndRefList = z1
         *  	sncpNodeList = a,z2 (同时[~]替换为[;],并去掉开头的[~])
         *  3.aEndRefList = a1##a2,  zEndRefList = z 时，
         *  	aEndRefList = a1,  zEndRefList = z
         *  	sncpNodeList = a1,z (同时[~]替换为[;],并去掉开头的[~])
         */
        if(FactoryName.FH == getFactoryName()) {
            if(aEndTpRefList.contains(Splits.SHARP) || zEndTpRefList.contains(Splits.SHARP)) {
                String[] aEndTps = aEndTpRefList.trim().replaceFirst(Splits.REP_SHARP, "").split(Splits.SHARP);
                String[] zEndTps = zEndTpRefList.trim().replaceFirst(Splits.REP_SHARP, "").split(Splits.SHARP);

                if(aEndTps.length >= 1 && zEndTps.length >= 1) {
                    staticProtectionLevel = "FULLY_PROTECTED";
                    this.param.put("S_A_END_VENDOR_NAME", aEndTps[0]);
                    this.param.put("S_Z_END_VENDOR_NAME", zEndTps[0]);

                    String sncpA = aEndTps.length > 1 ? aEndTps[1] : aEndTps[0];
                    String sncpZ = zEndTps.length > 1 ? zEndTps[1] : zEndTps[0];
                    sncpA = sncpA.replaceFirst(Splits.REP_WAVE, "").replace(Splits.WAVE, Splits.SEMICOLON);
                    sncpZ = sncpZ.replaceFirst(Splits.REP_WAVE, "").replace(Splits.WAVE, Splits.SEMICOLON);
                    vendorExtensions.put("sncpNodeNameList", ObjectTools.concat(sncpA, Splits.COMMA, sncpZ));
                }
            }
            //烽火创建子网必经是需添加必经角色 TODO 保护必经如何处理
            if(StringUtils.isNotBlank(MapUtils.getString(this.param, "S_NE_TP_INCLUSIONS"))) {
                //(W:工作必经，P:保护必经，C:工作必经或保护必经都可以；无保护的路由为W)
                vendorExtensions.put("inclusionResourceRefListRole", "W");
            }
        }
        this.param.put("vendorExtensions", vendorExtensions);

        //是否唯一：true、false
        String forceUniqueness = MapUtils.getString(this.param, "I_FORCE_UNIQUENESS", "false");
        //方向:单向、双向
        String direction = MapUtils.getString(this.param, "I_DIRECTION", "");
        //重新路由允许：RR_NA、RR_NO、RR_YES
        String rerouteAllowed = MapUtils.getString(this.param, "I_REROUTE_ALLOWED", "RR_NA");
        //网络路由：NR_NA、NR_NO、NR_YES
        String networkRouted = MapUtils.getString(this.param, "I_NETWORK_ROUTED", "NR_NA");
        //交叉类型：简单交叉 保护交叉
        String ccType = MapUtils.getString(this.param, "I_CC_TYPE", "");
        //是否全路由：true、false
        String fullRoute = MapUtils.getString(this.param, "I_FULLROUTE", "false");
        //容忍程度：主要、次要
        String tolerableImpact = MapUtils.getString(this.param, "I_TOLERABLE_IMPACT", "");
        //网管自由度：
        String emsfreedomLevel = MapUtils.getString(this.param, "I_EMSFREEDOM_LEVEL", "");

        //转换枚举
        IsBoolean forceUniquenessEnum = EnumUtils.getEnum(IsBoolean.class, forceUniqueness, "getCode");
        if (forceUniquenessEnum != null) {
            this.param.put("I_FORCE_UNIQUENESS", forceUniquenessEnum.getName());
        }
        Direction directionEnum = EnumUtils.getEnum(Direction.class, direction, "getCode");
        if (directionEnum != null) {
            this.param.put("I_DIRECTION", directionEnum.getName());
        }
        StaticProtectionLevel staticProtectionLevelEnum = EnumUtils.getEnum(StaticProtectionLevel.class, staticProtectionLevel, "getCode");
        if (staticProtectionLevelEnum != null) {
            this.param.put("I_STATIC_PROT_LEVEL", staticProtectionLevelEnum.getName());
        }
        RerouteAllowed rerouteAllowedEnum = EnumUtils.getEnum(RerouteAllowed.class, rerouteAllowed, "getCode");
        if (rerouteAllowedEnum != null) {
            this.param.put("I_REROUTE_ALLOWED", rerouteAllowedEnum.getName());
        }
        NetworkRouted networkRoutedEnum = EnumUtils.getEnum(NetworkRouted.class, networkRouted, "getCode");
        if (networkRoutedEnum != null) {
            this.param.put("I_NETWORK_ROUTED", networkRoutedEnum.getName());
        }
        CcType ccTypeEnum = EnumUtils.getEnum(CcType.class, ccType, "getCode");
        if (ccTypeEnum != null) {
            this.param.put("I_CC_TYPE", ccTypeEnum.getName());
        }
        IsBoolean fullRouteEnum = EnumUtils.getEnum(IsBoolean.class, fullRoute, "getCode");
        if (fullRouteEnum != null) {
            this.param.put("I_FULLROUTE", fullRouteEnum.getName());
        }
        TolerableImpact tolerableImpactEnum = EnumUtils.getEnum(TolerableImpact.class, tolerableImpact, "getCode");
        if (tolerableImpactEnum != null) {
            this.param.put("I_FULLROUTE", tolerableImpactEnum.getName());
        }
        OsFreedomLevel osFreedomLevelEnum = EnumUtils.getEnum(OsFreedomLevel.class, emsfreedomLevel, "getCode");
        if (osFreedomLevelEnum != null) {
            this.param.put("I_FULLROUTE", osFreedomLevelEnum.getName());
        }
        String aliasNameList = MapUtils.getString(this.param,  "aliasNameList");
        //xml对<、>进行转义兼容
        if(aliasNameList.contains(Splits.LT)){
            aliasNameList = aliasNameList.replace(Splits.LT,"&lt;");
        }
        if(aliasNameList.contains(Splits.GT)){
            aliasNameList = aliasNameList.replace(Splits.GT,"&gt;");
        }
        if(!"".equals(aliasNameList)){
            aliasNameList = Splits.WAVE + "nativeEMSName=" +  aliasNameList;
        }
        this.param.put("aliasNameList", aliasNameList);
}
    @Override
    protected void updateResult() {

        //如是适配器不只是返回状态，适配器内重写该方法，将cmdOut解析后设置到 cmdResult的result中,前提是状态不会再被修改，否则的话都要重写此方法
        //updateCcCmdOut();
        LinkedList<Map<Object,Object>> result = (LinkedList<Map<Object,Object>>) this.cmdOut.get("DATA");
        CmdResult cmdResult = this.command.getCmdResult();
        cmdResult.setResult(result);
    }


//    private void updateCcCmdOut() {
//        //父类方法parseResponse ， 存了this.cmdOut 存了解析响应结果  老代码逻辑发现datas是从解析响应结果key为WsParser.KEY_DATA)的值
//        LinkedList<Map<String, Object>> datas = (LinkedList<Map<String, Object>>) this.cmdOut.get(WsParser.KEY_DATA);
//        Map<String, Object> data = datas.size() > 0 ? datas.get(0) : new HashMap<String, Object>();
//
//        String sncName = MapUtils.getString(data, "name","");
//        String userLabel = MapUtils.getString(data, "userLabel","");
//        String direction = MapUtils.getString(data, "direction","");
//        String ccType = MapUtils.getString(data, "ccType","");
//        //转换枚举
//        Direction directionEnum = EnumUtils.getEnum(Direction.class, direction, "getName");
//        if (directionEnum != null) {
//            this.param.put("I_DIRECTION", directionEnum.getName());
//        }
//        CcType ccTypeEnum = EnumUtils.getEnum(CcType.class, ccType, "getName");
//        if (ccTypeEnum != null) {
//            this.param.put("I_CC_TYPE", ccTypeEnum.getName());
//        }
//        //改变入参 map集合 this.param里面的值
//        this.param.put("sncName", sncName);
//        this.param.put("S_USER_LABEL", userLabel);
//
//        //不知道为什么这里要删除这些值？？？
//        data.remove("task_id");
//        data.remove("ems_id");
//        data.remove("m_seq");
//        data.remove("c_seq");
//        data.remove("vendorExtensions");
////        data.remove("name");
////        data.remove("userLabel");
//        data.remove("direction");
//        data.remove("sncType");
//
//        this.param.putAll(data);
//
//        Map<String, Object> extMap = getResponseVendorExtMap();
//
//        this.param.putAll(extMap);
//
//        if (this.command.getCmdResult().getCmdStatus()==CmdStatus.RS_SUCCESS){
//            this.param.put("recode", CmdStatus.RS_SUCCESS.getCode());
//            this.param.put("redesc", Cmd.createAndActivateSubnetWorkConnection.getDesc() + "成功");
//
//        }else {
//            this.param.put("recode",this.command.getCmdResult().getCmdStatus());
//            //获取响应报文
//            String responseXml = this.command.getResponseXml();
//            //调用父类方法获取错误原因，
//            String detailErrorReason = getDetailErrorReason(responseXml);
//            this.param.put("redesc",detailErrorReason);
//        }
//
//        boolean isSuccess = true ;
//        String sign = Cmd.createAndActivateSubnetWorkConnection.getDesc() + "成功";
//        for ( String key : this.param.keySet()) {
//            String redesc = (String) this.param.get("redesc");
//            if(!sign.equals(redesc)){
//                isSuccess = false;
//                break;
//            }
//        }
//        if(isSuccess){
//            this.param.put("recode",  CmdStatus.RS_SUCCESS.getCode());
//            this.param.put("redesc", sign + "成功");
//        }
//        //入参参数this.param调整完之后，放进出参this.cmdout里面
//        this.cmdOut.put("crossConnections",this.param);
//    }

    private Map<String, Object> getResponseVendorExtMap() {
        Map<String, Object> result = new HashMap<String, Object>();
        //获取过程状态记录
        String parseStatus = MapUtils.getString(this.cmdOut, WsParser.KEY_STATUS, "");
        if (WXPResult.STATUS_ALL_S.equals(parseStatus)) {
            //cmdOut 存了解析响应结果
            LinkedList<Map<String, Object>> datas = (LinkedList<Map<String, Object>>) this.cmdOut.get(WsParser.KEY_DATA);
            Map<String, Object> data = datas.size() > 0 ? datas.get(0) : new HashMap<String, Object>();

            String vendorExtensions = (String) data.get("vendorExtensions");
            if (vendorExtensions != null){
                String[] kvs = vendorExtensions.split(Splits.WAVE);
                for (String kv : kvs) {
                    int idx = kv.indexOf(Splits.EQUAL);
                    if(idx > 0){
                        String key = kv.substring(0, idx);
                        String value = kv.substring(idx == kv.length() ? idx : idx + 1, kv.length());
                        if("pgRef".equals(key)){
                            value = getI2PgRef(value);
                        }
                        result.put(key, value);
                    }
                }

            }
        }
        return result;
    }


    private String getI2PgRef(String factoryValue) {

        StringBuffer pgRef = new StringBuffer();
        String[] kvs = factoryValue.split("\\\\");
        try {
            for (int i = 2; i < kvs.length; i += 2) {
                String key = getI2Value(kvs[i - 1]);
                String value = getI2Value(kvs[i]);
                pgRef.append(Splits.WAVE).append(key).append(Splits.EQUAL).append(value);
            }
        } catch (Exception e) {
           /* LOG.error("", e);*/
        }
        return pgRef.toString();

    }

    private String getI2Value(String object) {
        String value = "";
        int idx = object.indexOf(Splits.EQUAL);
        if(idx > 0){
            value = object.substring(idx + 1 , object.length());
        }
        return value;

    }
}
