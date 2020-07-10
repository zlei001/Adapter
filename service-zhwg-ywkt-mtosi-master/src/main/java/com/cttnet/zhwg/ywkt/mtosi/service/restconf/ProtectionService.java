package com.cttnet.zhwg.ywkt.mtosi.service.restconf;

import com.cttnet.common.enums.ResponseData;
import com.cttnet.common.util.StringUtil;
import com.cttnet.zhwg.api.impl.ProtUnitAPI;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.CmdClient;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.bo.CmdResult;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.CmdStatus;
import com.cttnet.zhwg.ywkt.mtosi.data.dto.request.*;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 保护组Service
 * </pre>
 *
 * @author degnkaihog
 * @date 2020/4/14
 * @since java 1.8
 */
@Service
public class ProtectionService {
    /**
     * 查询所有保护组
     * @param request 查询所有保护组请求
     * @return 响应
     */
    public ResponseData<?> getAllProtectionGroups(RequestOfGetAllProtectionGroups request){
        String emsId = request.getBody().getEmsId();
        String tpId = request.getBody().getTpId();

        Map<String, Object> param = new HashMap<>();
        param.put("emsId",emsId);
        if (StringUtil.isNotNull(tpId)){
            ProtUnitAPI api = new ProtUnitAPI();
            Map<String, Object> map = api.loadProtUnitById(tpId);

            String vendorObjName = MapUtils.getString(map, "s_vendor_obj_name");
            if (vendorObjName.length() != 0){
                param.put("S_VENDOR_OBJ_NAME",vendorObjName);
            }
        }

        CmdResult execute = CmdClient.execute(Cmd.getAllProtectionGroups, param);

        ResponseData<Object> responseData;
        if (execute.getCmdStatus() != CmdStatus.RS_SUCCESS) {
            responseData = ResponseData.fail(execute.getRemark());
        } else {
            responseData = ResponseData.ok(execute.getResult());
        }
        return responseData;
    }
    /**
     * 修改/设置保护组
     * @param request 修改/设置保护组请求
     * @return 响应
     */
    public ResponseData<?> modifyProtectionGroup(RequestOfModifyProtectionGroup request){
        String emsId = request.getBody().getEmsId();
        String tpId = request.getBody().getTpId();
        Map<String, Object>  parameter = request.getBody().getParameter();

        Map<String, Object> param = new HashMap<>();

        param.put("emsId",emsId);
        param.put("parameter",parameter);

        if (StringUtil.isNotNull(tpId)){
            ProtUnitAPI api = new ProtUnitAPI();
            Map<String, Object> map = api.loadProtUnitById(tpId);
            String vendorObjName = MapUtils.getString(map, "s_vendor_obj_name");
            if (vendorObjName.length() != 0){
                param.put("S_VENDOR_OBJ_NAME",vendorObjName);
            }
        }
        CmdResult execute = CmdClient.execute(Cmd.modifyProtectionGroup, param);
        //TODO  解析报文返回DATA值为null，延后处理
        ResponseData<Object> responseData;
        if (execute.getCmdStatus() != CmdStatus.RS_SUCCESS) {
            responseData = ResponseData.fail(execute.getRemark());
        } else {
            responseData = ResponseData.ok(execute.getResult());
        }
        return responseData;


    }
    /**
     * 创建保护组
     * @param request 创建保护组请求
     * @return 响应
     */
    public ResponseData<?> createProtectionGroup(RequestOfCreateProtectionGroup request){
        
        Map<String, Object> param = new HashMap<>();
        
        String emsId = request.getBody().getEmsId();
        String tpId = request.getBody().getTpId();
        String userLabel = request.getBody().getUserLabel();
        String protectedPortId = request.getBody().getProtectedPortId();
        String protectingPortId = request.getBody().getProtectingPortId();
        String layerRate = request.getBody().getLayerRate();
        String type = request.getBody().getType();
        String reversionMode = request.getBody().getReversionMode();
        String vendorExtensions = request.getBody().getVendorExtensions();

        Map<String, Object>  parameter = request.getBody().getParameter();
        
        ProtUnitAPI api = new ProtUnitAPI();
        if (StringUtil.isNotNull(tpId)){
            Map<String, Object> map = api.loadProtUnitById(tpId);
            String vendorObjName = MapUtils.getString(map, "s_vendor_obj_name");
            if (vendorObjName.length() != 0){
                param.put("S_VENDOR_OBJ_NAME",vendorObjName);
            }
        }
        Map<String, Object> protectionMap = api.loadPortUnitByProId(protectedPortId, protectingPortId);

        param.put("emsId",emsId);
        param.put("userLabel",userLabel);
        param.put("layerRate",layerRate);
        param.put("type",type);
        param.put("reversionMode",reversionMode);
        param.put("vendorExtensions",vendorExtensions);

        param.put("parameter",parameter);
        param.put("protectionMap",protectionMap);

        CmdResult execute = CmdClient.execute(Cmd.createProtectionGroup, param);

        ResponseData<Object> responseData;
        if (execute.getCmdStatus() != CmdStatus.RS_SUCCESS) {
            responseData = ResponseData.fail(execute.getRemark());
        } else {
            responseData = ResponseData.ok(execute.getResult());
        }
        return responseData;


    }
    /**
     * 删除保护组
     * @param request 删除保护组请求
     * @return 响应
     */
    public ResponseData<?> deleteProtectionGroup(RequestOfDeleteProtectionGroup request){
        String emsId = request.getBody().getEmsId();
        String tpId = request.getBody().getTpId();

        Map<String, Object> param = new HashMap<>();
        param.put("emsId",emsId);
        if (StringUtil.isNotNull(tpId)){
            ProtUnitAPI api = new ProtUnitAPI();
            Map<String, Object> map = api.loadProtUnitById(tpId);

            String vendorObjName = MapUtils.getString(map, "s_vendor_obj_name");
            if (vendorObjName.length() != 0){
                param.put("S_VENDOR_OBJ_NAME",vendorObjName);
            }
        }

        CmdResult execute = CmdClient.execute(Cmd.deleteProtectionGroup, param);

        ResponseData<Object> responseData;
        if (execute.getCmdStatus() != CmdStatus.RS_SUCCESS) {
            responseData = ResponseData.fail(execute.getRemark());
        } else {
            responseData = ResponseData.ok(param);
        }
        return responseData;
    }
    /**
     * 查询保护组倒换状态
     * @param request 查询保护组倒换状态请求
     * @return 响应
     */
    public ResponseData<?> retrieveSwitchData(RequestOfRetrieveSwitchData request){
        String emsId = request.getBody().getEmsId();
        String tpId = request.getBody().getTpId();

        Map<String, Object> param = new HashMap<>();
        param.put("emsId",emsId);
        if (StringUtil.isNotNull(tpId)){
            ProtUnitAPI api = new ProtUnitAPI();
            Map<String, Object> map = api.loadProtUnitById(tpId);

            String vendorObjName = MapUtils.getString(map, "vendorObjName");
            if (vendorObjName.length() != 0){
                param.put("vendorObjName",vendorObjName);
            }
        }

        CmdResult execute = CmdClient.execute(Cmd.retrieveSwitchData, param);

        ResponseData<Object> responseData;
        if (execute.getCmdStatus() != CmdStatus.RS_SUCCESS) {
            responseData = ResponseData.fail(execute.getRemark());
        } else {
            responseData = ResponseData.ok(param);
        }
        return responseData;
    }

}
