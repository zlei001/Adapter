package com.cttnet.zhwg.ywkt.mtosi.service.restconf;

import com.cttnet.common.enums.ResponseData;
import com.cttnet.common.util.StringUtil;
import com.cttnet.zhwg.api.impl.TransBoardApi;
import com.cttnet.zhwg.api.impl.TransCtpApi;
import com.cttnet.zhwg.api.impl.TransNeApi;
import com.cttnet.zhwg.api.impl.TransPortApi;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.CmdClient;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.bo.CmdResult;
import com.cttnet.zhwg.ywkt.mtosi.ability.constants.Splits;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.CmdStatus;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.SetCommonType;
import com.cttnet.zhwg.ywkt.mtosi.data.dto.request.*;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  网管维护操作Service
 * </pre>
 *
 * @author wangzhaoshi
 * @date 2020/2/27
 * @since java 1.8
 */
@Service
public class MaintenanceService {

    /**结果标志 成功 1 ，失败 0*/
    private static final int SUCCESSFLAG = 1;

    private static final int FAILUREFLAG = 0;
    /**
     * 关闭PRBS测试
     * @param request 关闭PRBS测试请求
     * @return 响应
     */
    public ResponseData<?>  disablePRBSTest(RequestOfDisablePRBSTest request) {

        String emsId = request.getBody().getEmsId();
        String tpId = request.getBody().getTpId();

        Map<String, Object> param = new HashMap<>();
        param.put("emsId", emsId);

        List<String> list  = new ArrayList<>();
        list.add(tpId);

        //根据toId 获取 tpName 放进param 键为 S_VENDOR_OBJ_NAME
        TransCtpApi api = new TransCtpApi();
        List<Map<String, Object>> ctpList  = api.loadCtpListById(list);

        for (Map<String, Object> map : ctpList) {
            String tpName  = MapUtils.getString(map,"vendorObjName");
            param.put("S_VENDOR_OBJ_NAME", tpName);
        }

        StringBuffer resultMsg = new StringBuffer();
        CmdResult execute = CmdClient.execute(Cmd.disablePRBSTest, param);

        if (execute.getCmdStatus() != CmdStatus.RS_SUCCESS) {
            resultMsg.append("关闭PRBS测试错误：").append(execute.getRemark()).append("\r\n");
        }
        ResponseData<Map<String, Object>> responseData = new ResponseData<>();
        Map<String,Object> response  = new HashMap<>();
        if (resultMsg.length() > 0 ) {  // 关闭PRBS测试失败

            response.put("code",FAILUREFLAG);
            response.put("desc",resultMsg.toString());

            responseData = ResponseData.fail(resultMsg.toString());
            responseData.setData(response);

        } else {    // 关闭PRBS测试成功

            response.put("code",SUCCESSFLAG);
            responseData = ResponseData.ok(response);
        }
        return responseData;
    }


    /**
     * 启动PRBS测试
     * @param request 启动PRBS测试请求
     * @return 响应
     */
    public ResponseData<?>  enablePRBSTest(RequestOfEnablePRBSTest request) {

        String emsId = request.getBody().getEmsId();
        String tpId = request.getBody().getTpId();
        String direction = request.getBody().getDirection();
        String accumulatingIndicator = request.getBody().getAccumulatingIndicator();
        String prbsType = request.getBody().getPrbsType();
        String sampleGranularity = request.getBody().getSampleGranularity();
        String testDuration = request.getBody().getTestDuration();

        Map<String, Object> param = new HashMap<>();
        param.put("emsId", emsId);
        param.put("direction", direction);
        param.put("accumulatingIndicator", accumulatingIndicator);
        param.put("prbsType", prbsType);
        param.put("sampleGranularity", sampleGranularity);
        param.put("testDuration", testDuration);

        List<String> list  = new ArrayList<>();
        list.add(tpId);
        //根据toId 获取 tpName 放进param 键为 S_VENDOR_OBJ_NAME
        TransCtpApi api = new TransCtpApi();
        List<Map<String, Object>> ctpList  = api.loadCtpListById(list);

        for (Map<String, Object> map : ctpList) {
            String tpName  = MapUtils.getString(map,"vendorObjName");
            param.put("S_VENDOR_OBJ_NAME", tpName);
        }
        StringBuffer resultMsg = new StringBuffer();

        CmdResult execute = CmdClient.execute(Cmd.enablePRBSTest, param);

        if (execute.getCmdStatus() != CmdStatus.RS_SUCCESS) {
            resultMsg.append("启动PRBS测试错误：").append(execute.getRemark()).append("\r\n");
        }

        ResponseData<Map<String, Object>> responseData = new ResponseData<>();
        Map<String,Object> response  = new HashMap<>();
        if (resultMsg.length() > 0 ) {  // 启动PRBS测试失败

            response.put("code",FAILUREFLAG);
            response.put("desc",resultMsg.toString());

            responseData = ResponseData.fail(resultMsg.toString());
            responseData.setData(response);

        } else {    // 启动PRBS测试成功

            response.put("code",SUCCESSFLAG);
            responseData = ResponseData.ok(response);
        }
        return responseData;
    }

    /**
     * 查询PRBS测试结果 返回测试结果
     * @param request 查询PRBS测试结果请求
     * @return 响应
     */
    public ResponseData<?>  getPRBSTestResult(RequestOfGetPRBSTestResult request) {

        String emsId = request.getBody().getEmsId();
        String tpId = request.getBody().getTpId();

        Map<String, Object> param = new HashMap<>();
        param.put("emsId", emsId);

        List<String> list  = new ArrayList<>();
        list.add(tpId);

        //根据toId 获取 tpName 放进param 键为 S_VENDOR_OBJ_NAME
        TransCtpApi api = new TransCtpApi();
        List<Map<String, Object>> ctpList  = api.loadCtpListById(list);

        for (Map<String, Object> map : ctpList) {
            String tpName  = MapUtils.getString(map,"vendorObjName");
            param.put("S_VENDOR_OBJ_NAME", tpName);
        }

        StringBuffer resultMsg = new StringBuffer();
        CmdResult execute = CmdClient.execute(Cmd.getPRBSTestResult, param);

        if (execute.getCmdStatus() != CmdStatus.RS_SUCCESS) {
            resultMsg.append("关闭PRBS测试错误：").append(execute.getRemark()).append("\r\n");
        }
        ResponseData<Object> responseData = new ResponseData<>();
        Map<String,Object> response  = new HashMap<>();
        if (resultMsg.length() > 0 ) {  // 查询PRBS测试结果失败

            response.put("code",FAILUREFLAG);
            response.put("desc",resultMsg.toString());

            responseData = ResponseData.fail(resultMsg.toString());
            responseData.setData(response);

        } else {
            // 查询PRBS测试结果成功
            responseData.setData(execute.getResult());

        }
        return responseData;
    }
    /**
     * 查询OTN业务时延 返回OTN时延
     * @param request 查询OTN业务时延请求
     * @return 响应
     */
    public ResponseData<?> getRoundTripDelayResult(RequestOfGetRoundTripDelayResult request) {

        //TODO  解析报文返回delay值为null，延后处理

        String emsId = request.getBody().getEmsId();
        String tpId = request.getBody().getTpId();
        String type = request.getBody().getType();

        Map<String, Object> param = new HashMap<>();
        param.put("emsId", emsId);
        param.put("type",type);

        List<String> list  = new ArrayList<>();
        list.add(tpId);
        //根据toId 获取 tpName 放进param 键为 S_VENDOR_OBJ_NAME
        TransCtpApi api = new TransCtpApi();
        List<Map<String, Object>> ctpList  = api.loadCtpListById(list);

        for (Map<String, Object> map : ctpList) {
            String tpName  = MapUtils.getString(map,"vendorObjName");
            param.put("S_VENDOR_OBJ_NAME", tpName);
        }

        CmdResult execute = CmdClient.execute(Cmd.getRoundTripDelayResult, param);

        ResponseData<Object> responseData;
        if (execute.getCmdStatus() != CmdStatus.RS_SUCCESS) {
            responseData = ResponseData.fail(execute.getRemark());
        } else {
            responseData = ResponseData.ok(execute.getResult());
            responseData.setData(execute.getResult());
        }
        return responseData;
    }

    /**
     * 配置OTN业务时延
     * @param request 配置OTN业务时延请求
     * @return 响应
     */
    public ResponseData<?> measureRoundTripDelay(RequestOfMeasureRoundTripDelay request) {

        String emsId = request.getBody().getEmsId();
        String tpId = request.getBody().getTpId();
        String type = request.getBody().getType();
        String role = request.getBody().getRole();

        Map<String, Object> param = new HashMap<>();
        param.put("emsId", emsId);
        param.put("type",type);
        param.put("role",role);

        List<String> list  = new ArrayList<>();
        list.add(tpId);

        //根据toId 获取 tpName 放进param 键为 S_VENDOR_OBJ_NAME
        TransCtpApi api = new TransCtpApi();
        List<Map<String, Object>> ctpList  = api.loadCtpListById(list);

        for (Map<String, Object> map : ctpList) {
            String tpName  = MapUtils.getString(map,"vendorObjName");
            param.put("S_VENDOR_OBJ_NAME", tpName);
        }

        CmdResult execute = CmdClient.execute(Cmd.measureRoundTripDelay, param);

        ResponseData<?> responseData;
        if (execute.getCmdStatus() != CmdStatus.RS_SUCCESS) {
            responseData = ResponseData.fail(execute.getRemark());
        } else {
            responseData = ResponseData.ok(param);
        }
        return responseData;
    }

    /**
     * 端口环回
     * @param request 端口环回请求
     * @return 响应
     */
    public ResponseData<?> operationLoopback(RequestOfOperationLoopback request) {

        String emsId = request.getBody().getEmsId();
        String tpId = request.getBody().getTpId();
        String mode = request.getBody().getMaintenanceOperationMode();
        String operation = request.getBody().getMaintenanceOperation();
        String layerRate = request.getBody().getLayerRate();
        String vendorObjName = "";
        int failureNum = 0 ;
        Map<String, Object> param = new HashMap<>();
        //根据端口id获取厂家对象
        if (StringUtil.isNotNull(tpId)){
            TransPortApi api = new TransPortApi();
            Map<String, Object>  map = api.loadPortById(tpId);
            vendorObjName = MapUtils.getString(map, "vendorObjName");
            if (vendorObjName.length() != 0) {
                param.put("S_VENDOR_OBJ_NAME", vendorObjName);
            }
        }
        param.put("emsId",emsId);
        param.put("maintenanceOperationMode",mode);
        param.put("maintenanceOperation",operation);
        param.put("layerRate",layerRate);

        CmdResult execute = CmdClient.execute(Cmd.operationLoopback, param);
        // 存放错误信息
        StringBuffer resultMsg = new StringBuffer();
        if (execute.getCmdStatus() != CmdStatus.RS_SUCCESS) {
            resultMsg.append("执行端口环回错误：").append(execute.getRemark()).append("\r\n");
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
    /**
     * 打开、关闭端口激光器
     * @param request 打开、关闭端口激光器请求
     * @return 响应
     */
    public ResponseData<?> operationLaserSwitchOff(RequestOfOperationLaserSwitchOff request) {

        String emsId = request.getBody().getEmsId();
        String tpId = request.getBody().getTpId();
        String mode = request.getBody().getMaintenanceOperationMode();
        String operation = request.getBody().getMaintenanceOperation();
        String layerRate = request.getBody().getLayerRate();
        String vendorObjName = "";
        int failureNum = 0 ;
        Map<String, Object> param = new HashMap<>();
        //根据端口id获取厂家对象
        if (StringUtil.isNotNull(tpId)){
            TransPortApi api = new TransPortApi();
            Map<String, Object>  map = api.loadPortById(tpId);
            vendorObjName = MapUtils.getString(map, "vendorObjName");
            if (vendorObjName.length() != 0) {
                param.put("S_VENDOR_OBJ_NAME", vendorObjName);
            }
        }
        param.put("emsId",emsId);
        param.put("maintenanceOperationMode",mode);
        param.put("maintenanceOperation",operation);
        param.put("layerRate",layerRate);

        CmdResult execute = CmdClient.execute(Cmd.operationLaserSwitchOff, param);
        // 存放错误信息
        StringBuffer resultMsg = new StringBuffer();
        if (execute.getCmdStatus() != CmdStatus.RS_SUCCESS) {
            resultMsg.append("执行开关端口激光器错误：").append(execute.getRemark()).append("\r\n");
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
    /**
     * 单板复位
     * @param request 单板复位请求
     * @return 响应
     */
    public ResponseData<?> operationCardReset(RequestOfOperationCardReset request) {

        String emsId = request.getBody().getEmsId();
        String tpId = request.getBody().getTpId();
        String mode = request.getBody().getMaintenanceOperationMode();
        String operation = request.getBody().getMaintenanceOperation();
        String layerRate = request.getBody().getLayerRate();
        String vendorObjName = "";
        int failureNum = 0 ;
        Map<String, Object> param = new HashMap<>();
        //根据板卡id获取厂家对象
        if (StringUtil.isNotNull(tpId)){
            TransBoardApi api = new TransBoardApi();
            Map<String, Object>  map = api.loadBoardById(tpId);
            vendorObjName = MapUtils.getString(map, "vendorObjName");
            if (vendorObjName.length() != 0) {
                param.put("S_VENDOR_OBJ_NAME", vendorObjName);
            }
        }
        param.put("emsId",emsId);
        param.put("maintenanceOperationMode",mode);
        param.put("maintenanceOperation",operation);
        param.put("layerRate",layerRate);

        CmdResult execute = CmdClient.execute(Cmd.operationCardReset, param);
        // 存放错误信息
        StringBuffer resultMsg = new StringBuffer();
        if (execute.getCmdStatus() != CmdStatus.RS_SUCCESS) {
            resultMsg.append("单板复位错误：").append(execute.getRemark()).append("\r\n");
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
    /**
     * 查询网元上所有端口维护操作状态,
     * @param request 查询网元上所有端口维护操作状态,请求
     * @return 响应
     */
    public ResponseData<?> getActiveMaintenanceOperations(RequestOfGetActiveMaintenanceOperations request) {

        String emsId = request.getBody().getEmsId();
        String tpId = request.getBody().getTpId();
        int commonType = request.getBody().getCommonType();

        String vendorObjName = "";
        int failureNum = 0 ;
        Map<String, Object> param = new HashMap<>();
        //根据端口id获取厂家对象
        if (StringUtil.isNotNull(tpId) && commonType == SetCommonType.PORT_ID.getValue()){
            TransPortApi api = new TransPortApi();
            Map<String, Object>  map = api.loadPortById(tpId);
            vendorObjName = MapUtils.getString(map, "vendorObjName");
            if (vendorObjName.length() != 0) {
                param.put("S_VENDOR_OBJ_NAME", vendorObjName);
            }
        }
        //根据网元id获取厂家对象
        if (StringUtil.isNotNull(tpId) && commonType == SetCommonType.NE_ID.getValue()){
            List<String> neList = new ArrayList<>();
            neList.add(tpId);
            TransNeApi api = new TransNeApi();
            List<Map<String, Object>> map = api.loadNeListByIds(neList);

            StringBuilder neNames = new StringBuilder();
            for (Map<String, Object> ne : map) {
                String vendor = MapUtils.getString(ne, "vendorObjName");
                if (neNames.length() != 0) {
                    neNames.append(Splits.SHARP);
                }
                neNames.append(vendor);
            }
            param.put("S_VENDOR_OBJ_NAME", neNames.toString());
        }
        param.put("emsId",emsId);

        CmdResult execute = CmdClient.execute(Cmd.getActiveMaintenanceOperations, param);
        // 存放错误信息
        StringBuffer resultMsg = new StringBuffer();
        if (execute.getCmdStatus() != CmdStatus.RS_SUCCESS) {
            resultMsg.append("查询维护操作状态错误：").append(execute.getRemark()).append("\r\n");
            failureNum++;
        }

        ResponseData<Object> responseData =new ResponseData<>();;
        Map<String,Object> response  = new HashMap<>();
        if ( failureNum == 1) {  // 失败

            response.put("code",FAILUREFLAG);
            response.put("desc",resultMsg.toString());
            responseData = ResponseData.fail(resultMsg.toString());
            responseData.setData(response);

        } else{ // 成功

            responseData.setData(execute.getResult());
        }
        return responseData;
    }

}
