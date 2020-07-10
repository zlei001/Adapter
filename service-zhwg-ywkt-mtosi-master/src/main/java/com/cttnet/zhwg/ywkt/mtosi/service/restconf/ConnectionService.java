package com.cttnet.zhwg.ywkt.mtosi.service.restconf;

import com.cttnet.common.enums.ResponseData;
import com.cttnet.common.util.StringUtil;
import com.cttnet.zhwg.api.impl.ChannelApi;
import com.cttnet.zhwg.api.impl.TransCtpApi;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.CmdClient;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.bo.CmdResult;
import com.cttnet.zhwg.ywkt.mtosi.ability.constants.Splits;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.CmdStatus;
import com.cttnet.zhwg.ywkt.mtosi.data.dto.request.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  连接Service
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/2/13
 * @since java 1.8
 */
@Service
public class ConnectionService {


    /**结果标志 全部成功 1 ，部分成功 2 ， 全部失败 0*/
    private static final int SUCCESSFLAG = 1;

    private static final int PARTSUCCESSFLAG = 2;

    private static final int FAILUREFLAG = 0;

    /**
     * 创建并激活交叉连接 ，每次可能有多条数据
     * @param request 创建并激活交叉连接请求
     * @return 响应
     */
    public ResponseData<?> createAndActivateCrossConnection(RequestOfCreateAndActivateCrossConnection request) {

        List<CrossConnection> crossConnections = request.getCrossConnections();
        //  创建并激活交叉连接 失败条数
         int failureNum = 0;
        //取CTP id集合
        List<String> ctpIds = new ArrayList<>();
        for (CrossConnection connection : crossConnections) {
            ctpIds.addAll(connection.getSourceEndRefIdList());
            ctpIds.addAll(connection.getDestEndRefIdList());
        }
        //查询CTP
        TransCtpApi transCtpApi = new TransCtpApi();
        List<Map<String, Object>> ctpList = transCtpApi.loadCtpListById(ctpIds);
        Map<String, Map<String, Object>> portMap = StringUtil.transMapByKey(ctpList, "s_port_id");
        StringBuffer resultMsg = new StringBuffer();
        //TODO 需要改成并发
        for (int i = 0, len = crossConnections.size(); i < len; i++) {
            CrossConnection connection = crossConnections.get(i);
            Map<String, Object> param = crossConnectToMtosi(connection, portMap);
            CmdResult execute = CmdClient.execute(Cmd.createAndActivateCrossConnection, param);
            if (execute.getCmdStatus() != CmdStatus.RS_SUCCESS) {
                resultMsg.append("第[").append(i+1).append("]个创建并激活交叉连接错误：").append(execute.getRemark()).append("\r\n");
                failureNum++;
            } else {
                //TODO 调用CTP树更新服务, 调用创建交叉生成交叉连接对象
            }
        }
        ResponseData<Map<String, Object>> responseData = new ResponseData<>();
        Map<String,Object> response  = new HashMap<>();
        if ( failureNum == crossConnections.size()) {  // 全部失败

            response.put("code",FAILUREFLAG);
            response.put("desc",resultMsg.toString());

            responseData = ResponseData.fail(resultMsg.toString());
            responseData.setData(response);

        } else if( failureNum > 0 && failureNum < crossConnections.size()){ // 部分成功

            response.put("code",PARTSUCCESSFLAG);
            response.put("desc",resultMsg.toString());

            responseData = ResponseData.fail(resultMsg.toString());
            responseData.setData(response);

        }else if( failureNum == 0 ){ // 全部成功

            response.put("code",SUCCESSFLAG);
            responseData = ResponseData.ok(response);
        }
        return responseData;
    }

    /**
     * 交叉连接参数转化成mtosi
     * @param connection connection
     * @param portMap portMap
     * @return Map
     */
    private Map<String, Object> crossConnectToMtosi(CrossConnection connection, Map<String, Map<String, Object>> portMap) {
        Map<String, Object> param = new HashMap<>(10);
        param.put("emsId", connection.getEmsId());
        List<String> aEndRefIdList = connection.getSourceEndRefIdList();
        List<String> zEndRefIdList = connection.getDestEndRefIdList();
        StringBuffer aEndVendorName = new StringBuffer();
        for (String ctpId : aEndRefIdList) {
            //TODO ctpId查询异常处理
            String ctpName = MapUtils.getString(portMap.get(ctpId), "s_vendor_obj_name");
            if (aEndVendorName.length() != 0) {
                aEndVendorName.append(Splits.SHARP);
            }
            aEndVendorName.append(ctpName);
        }
        StringBuffer zEndVendorName = new StringBuffer();
        for (String ctpId : zEndRefIdList) {
            //TODO ctpId查询异常处理
            String ctpName = MapUtils.getString(portMap.get(ctpId), "s_vendor_obj_name");
            if (zEndVendorName.length() != 0) {
                zEndVendorName.append(Splits.SHARP);
            }
            zEndVendorName.append(ctpName);
        }
        param.put("S_A_END_VENDOR_NAME", aEndVendorName);
        param.put("S_Z_END_VENDOR_NAME", zEndVendorName);
        param.put("I_DIRECTION", connection.getDirection());
        param.put("I_CC_TYPE", connection.getCcType());
        param.put("S_A_END_TIME_SLOT", connection.getSourceEndRefTimeSlot());
        param.put("S_Z_END_TIME_SLOT", connection.getDestEndRefTimeSlot());
        param.put("S_CLIENT_SERVICE_TYPE", connection.getClientServiceType());
        param.put("S_CLIENT_SERVICE_CONTAINER", connection.getClientServiceContainer());
        param.put("S_TP_MAPPING_MODE", connection.getClientServiceMappingMode());
        //TODO 其他参数待补充
        return param;
    }

    /**
     * 创建并激活子网连接,创建激活子网每次只有一条数据
     * @param request 创建并激活子网连接请求
     * @return 响应
     */
    public ResponseData<?> createAndActivateSubnetWorkConnection(RequestOfCreateAndActiveSubnetWorkConnection request) {

        // 处理入参参数
        Map<String, Object> param = subnetWorkConnectToMtosi(request.getSubnetWorkConnection());

        CmdResult execute = CmdClient.execute(Cmd.createAndActivateSubnetWorkConnection, param);
        ResponseData<Object> responseData;
        if (execute.getCmdStatus() != CmdStatus.RS_SUCCESS) {
            responseData = ResponseData.fail(execute.getRemark());
        } else {
            responseData = ResponseData.ok(execute.getResult());
            responseData.setData(execute.getResult());
        }
        return responseData;
    }

    private Map<String, Object> subnetWorkConnectToMtosi(SubnetWorkConnection connection) {

        // 取源宿端端口CtpId 集合
        List<String> aEndRefIdList = connection.getSourceEndRefIdList();
        List<String> zEndRefIdList = connection.getDestEndRefIdList();
        //查询取源宿端支路口CTP
        String aEndVendorName = getSourceAndDestCtp(aEndRefIdList);
        String zEndVendorName = getSourceAndDestCtp(zEndRefIdList);

        // 取必经，避让通道id集合
        List<String> inclusionResource = connection.getInclusionResourceRefList();
        List<String> exclusionResource = connection.getExclusionResourceRefList();
        // 根据id查询通道信息集合
        String inclusionResourceRefList =  getInclusionAndExclusionResource(inclusionResource);
        String exclusionResourceRefList =  getInclusionAndExclusionResource(exclusionResource);

        // 取全路径主备数据所有交叉CtpID
        List<CcPath> majorCcPath =  connection.getMajorCcPath();
        List<CcPath> backuCcpath = connection.getBackupCcPath();
        // 根据CTPId查询 主备用数据CTP
        List<Map<String, Object>> majorCcList = getCcInclusionList(majorCcPath);
        List<Map<String, Object>> backupCcList = getCcInclusionList(backuCcpath);

        Map<String, Object> param = new HashMap<>(10);
        param.put("emsId", connection.getEmsId());
        param.put("S_A_END_VENDOR_NAME", aEndVendorName);
        param.put("S_Z_END_VENDOR_NAME", zEndVendorName);
        param.put("I_DIRECTION", connection.getDirection());
        param.put("I_CC_TYPE", connection.getCcType());
        param.put("aliasNameList", connection.getAliasNameList());
        param.put("S_USER_LABEL", connection.getUserLabel());
        param.put("S_A_END_TIME_SLOT", connection.getSourceEndRefTimeSlot());
        param.put("S_Z_END_TIME_SLOT", connection.getDestEndRefTimeSlot());
        param.put("ClientServiceType", connection.getClientServiceType());
        param.put("ClientServiceContainer", connection.getClientServiceContainer());
        param.put("ClientServiceMappingMode", connection.getClientServiceMappingMode());
        param.put("S_NE_TP_INCLUSIONS", inclusionResourceRefList);
        param.put("S_NE_TP_SNC_EXCLUS", exclusionResourceRefList);
        param.put("majorCcList",majorCcList);
        param.put("backupCcList",backupCcList);
        param.put("I_REROUTE_ALLOWED",connection.getRerouteAllowed());
        param.put("I_NETWORK_ROUTED",connection.getNetworkRouted());
        param.put("I_FULLROUTE",connection.getIsFullRoute());
        param.put("I_STATIC_PROT_LEVEL",connection.getStaticProtectionLevel());
        param.put("I_FORCE_UNIQUENESS",connection.getIsForceUniqueness());
        param.put("layerRate",connection.getLayerRate());
        //TODO 其他参数待补充
        return param;

    }

    /**
     * 获取交叉列表信息
     * @param Path
     * @return
     */
    private List<Map<String, Object>> getCcInclusionList(List<CcPath> Path){
        TransCtpApi transCtpApi = new TransCtpApi();
        List<Map<String, Object>> CcList = new ArrayList<>();

        if( Path != null && !Path.isEmpty() ){
            for(CcPath ccPath: Path){
                Map<String, Object> tempMap = new HashMap<>();
                List<String> sourceCtpIds = ccPath.getSourceCtpIds();
                List<String> destCtpIds = ccPath.getDestCtpIds();
                List<Map<String, Object>> Source = transCtpApi.loadCtpListById(sourceCtpIds);
                List<Map<String, Object>> Dest = transCtpApi.loadCtpListById(destCtpIds);
                tempMap.put("aRef" ,  Source);
                tempMap.put("zRef" , Dest );
                CcList.add(tempMap);
            }
        }
        return CcList;
    }

    /**
     * 获取必经，避让信息
     * @param resource
     * @return
     */
    private String getInclusionAndExclusionResource(List<String> resource){

        StringBuilder channelNames = new StringBuilder();

        if (CollectionUtils.isNotEmpty(resource)) {
            ChannelApi channelApi = new ChannelApi();
            List<Map<String, Object>>  CcList = channelApi.loadChannelListByIds(resource);
            for (Map<String, Object> ne : CcList) {
                String vendorObjName = MapUtils.getString(ne, "vendorObjName");
                if (channelNames.length() != 0) {
                    channelNames.append(Splits.SHARP);
                }
                channelNames.append(vendorObjName);
            }
        }
        return channelNames.toString();
    }

    /**
     * 获取源宿端 端口CTP信息
     * @param idlist
     * @return
     */
    private String getSourceAndDestCtp(List<String> idlist){

        StringBuilder neNames = new StringBuilder();

        if (CollectionUtils.isNotEmpty(idlist)) {
            TransCtpApi transCtpApi = new TransCtpApi();
            List<Map<String, Object>>  CcList =transCtpApi.loadCtpListById(idlist);

            for (Map<String, Object> ne : CcList) {
                String vendorObjName = MapUtils.getString(ne, "vendorObjName");
                if (neNames.length() != 0) {
                    neNames.append(Splits.SHARP);
                }
                neNames.append(vendorObjName);
            }
        }
        return neNames.toString();
    }

    /**
     * 去激活并删除交叉,有多条数据
     * @param request 去激活并删除交叉请求
     * @return 响应
     */
    public  ResponseData<?>  deactivateAndDeleteCrossConnection(RequestOfDeactivateAndDeleteCrossConnection request) {

        List<CrossConnection> crossConnections = request.getCrossConnections();
        //  去激活并删除交叉连接 失败条数
        int failureNum = 0 ;
        //取CTP id集合
        List<String> ctpIds = new ArrayList<>();
        for (CrossConnection connection : crossConnections) {
            ctpIds.addAll(connection.getSourceEndRefIdList());
            ctpIds.addAll(connection.getDestEndRefIdList());
        }
        //查询CTP
        TransCtpApi transCtpApi = new TransCtpApi();
        List<Map<String, Object>> ctpList = transCtpApi.loadCtpListById(ctpIds);
        Map<String, Map<String, Object>> portMap = StringUtil.transMapByKey(ctpList, "s_port_id");
        StringBuffer resultMsg = new StringBuffer();
        //TODO 需要改成并发
        for (int i = 0, len = crossConnections.size(); i < len; i++) {
            CrossConnection connection = crossConnections.get(i);
            Map<String, Object> param = crossConnectToMtosi(connection, portMap);
            CmdResult execute = CmdClient.execute(Cmd.deactivateAndDeleteCrossConnection, param);
            if (execute.getCmdStatus() != CmdStatus.RS_SUCCESS) {
                resultMsg.append("去激活并删除第[").append(i+1).append("]个交叉连接失败：").append(execute.getRemark()).append("\r\n");
                failureNum++;
            } else {
                //TODO 调用CTP树更新服务, 调用创建交叉生成交叉连接对象
            }
        }
        ResponseData<Map<String, Object>> responseData = new ResponseData<>();
        Map<String,Object> response  = new HashMap<>();
        if ( failureNum == crossConnections.size()) {  // 全部失败

            response.put("code",FAILUREFLAG);
            response.put("desc",resultMsg.toString());

            responseData = ResponseData.fail(resultMsg.toString());
            responseData.setData(response);

        } else if( failureNum > 0 && failureNum < crossConnections.size()){ // 部分成功

            response.put("code",PARTSUCCESSFLAG);
            response.put("desc",resultMsg.toString());

            responseData = ResponseData.fail(resultMsg.toString());
            responseData.setData(response);

        }else if( failureNum == 0 ){ // 全部成功

            response.put("code",SUCCESSFLAG);
            responseData = ResponseData.ok(response);
        }
        return responseData;
    }

    /**
     * 去激活并删除子网连接
     * @param request 去激活并删除子网连接请求
     * @return 响应
     */
    public ResponseData<?> deactivateAndDeleteSubnetWorkConnection(RequestOfDeactivateAndDeleteSubnetWorkConnection request) {
        // 失败次数
        int failureNum = 0 ;
        String emsId = request.getBody().getEmsId();
        String channelId = request.getBody().getChannelId();

        List<String> channelIds  = new ArrayList<>();
        channelIds.add(channelId);
        // 入参参数
        Map<String, Object> param = new HashMap<>();
        param.put("emsId",emsId);
        // 根据通道ID 调接口 获取 厂家对象名称  vendorObjName
        ChannelApi api = new ChannelApi();
        List<Map<String, Object>> ctpList = api.loadChannelListByIds(channelIds);

        for (Map<String, Object> map : ctpList) {
            String tpName  = MapUtils.getString(map,"vendorObjName");
            param.put("sncName", tpName);
        }

        CmdResult execute = CmdClient.execute(Cmd.deactivateAndDeleteSubnetWorkConnection, param);
        // 存放错误信息
        StringBuffer resultMsg = new StringBuffer();
        if (execute.getCmdStatus() != CmdStatus.RS_SUCCESS) {
            resultMsg.append("去激活并删除子网连接错误：").append(execute.getRemark()).append("\r\n");
            failureNum++;
        }

        ResponseData<Map<String, Object>> responseData;
        Map<String,Object> response  = new HashMap<>();
        if ( failureNum == 1) {  // 失败

            response.put("code",FAILUREFLAG);
            response.put("desc",resultMsg.toString());
            responseData = ResponseData.fail(resultMsg.toString());
            responseData.setData(response);

        } else{ // 成功
            response.put("code",SUCCESSFLAG);
            responseData = ResponseData.ok(response);
        }
        return responseData;
    }
}
