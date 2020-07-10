package com.cttnet.zhwg.ywkt.mtosi.service.restconf;

import com.cttnet.common.enums.ResponseData;
import com.cttnet.common.util.StringUtil;
import com.cttnet.zhwg.api.impl.TransCtpApi;
import com.cttnet.zhwg.api.impl.TransPortApi;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.CmdClient;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.bo.CmdResult;
import com.cttnet.zhwg.ywkt.mtosi.ability.constants.Splits;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.CmdStatus;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.SetCommonType;
import com.cttnet.zhwg.ywkt.mtosi.data.dto.request.RequestOfGetTerminationPoint;
import com.cttnet.zhwg.ywkt.mtosi.data.dto.request.RequestOfSetTerminationPointData;
import com.cttnet.zhwg.ywkt.mtosi.data.dto.request.TransmissionParameters;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 端点Service
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/4/3
 * @since java 1.8
 */
@Service
public class TerminationPointService {


    /**结果标志 成功 1 ，失败 0*/
    private static final int SUCCESSFLAG = 1;

    private static final int FAILUREFLAG = 0;

    /**
     * 查询端口属性
     * @param request 查询端口属性请求
     * @return 响应
     */
    public ResponseData<?> getTerminationPoint(RequestOfGetTerminationPoint request) {

        String emsId = request.getBody().getEmsId();
        String tpId = request.getBody().getTpId();

        Map<String, Object> param = new HashMap<>();
        param.put("emsId",emsId);
        //获取厂家唯一标识
        if (StringUtil.isNotNull(tpId)){
            TransPortApi api = new TransPortApi();
            Map<String, Object>  map = api.loadPortById(tpId);
            String vendorObjName = MapUtils.getString(map, "vendorObjName");
            if (vendorObjName.length() != 0) {
                param.put("S_VENDOR_OBJ_NAME", vendorObjName);
            }
        }

        StringBuffer resultMsg = new StringBuffer();
        CmdResult execute = CmdClient.execute(Cmd.getTerminationPoint, param);
        if (execute.getCmdStatus() != CmdStatus.RS_SUCCESS) {
            resultMsg.append("查询端口属性错误：").append(execute.getRemark()).append("\r\n");
        }

        Object result = execute.getResult();
        List<TransmissionParameters> transmissionParametersList = TransmissionParameters.getTransmissionParametersList(result.toString());

        ResponseData<Object> responseData= new ResponseData<>();
        Map<String,Object> response  = new HashMap<>();
        if (resultMsg.length() > 0 ) {  // 查询端口属性失败

            response.put("code",FAILUREFLAG);
            response.put("desc",resultMsg.toString());

            responseData = ResponseData.fail(resultMsg.toString());
            responseData.setData(response);

        } else {
            // 查询端口属性成功
            responseData.setData(transmissionParametersList);

        }
        return responseData;
    }


    /**
     * 设置端口属性 Port
     * @param request 设置端口属性请求
     * @return 响应
     */
    public ResponseData<?> setTerminationPointData(RequestOfSetTerminationPointData request) {

        String emsId = request.getBody().getEmsId();
        String tpId = request.getBody().getTpId();
        int commonType = request.getBody().getCommonType();
        List<TransmissionParameters> transmissionParametersList = request.getTransmissionParametersList();

        StringBuilder sb = new StringBuilder();
        // transmissionParametersList 数据格式转化
        if (transmissionParametersList!=null){
            for (TransmissionParameters transmissionParameters : transmissionParametersList) {
                sb.append(transmissionParameters.requestDataFormat()).append(Splits.AT);
            }
        }
        if(sb.length() > Splits.AT.length()){
            sb.delete(sb.length() - Splits.AT.length(), sb.length());
        }

        Map<String, Object> param = new HashMap<>();
        param.put("emsId",emsId);
        param.put("transmissionParametersList",sb.toString());

        String vendorObjName = "";
        //根据端口id获取厂家对象
        if (StringUtil.isNotNull(tpId) && commonType == SetCommonType.PORT_ID.getValue()){
            TransPortApi api = new TransPortApi();
            Map<String, Object>  map = api.loadPortById(tpId);
             vendorObjName = MapUtils.getString(map, "vendorObjName");
            if (vendorObjName.length() != 0) {
                param.put("S_VENDOR_OBJ_NAME", vendorObjName);
            }
        }
        // 根据CtpId 获取厂家对象名称
        if (StringUtil.isNotNull(tpId) && commonType == SetCommonType.CTP_ID.getValue()){
            List<String> ctpList = new ArrayList<>();
            ctpList.add(tpId);
            TransCtpApi api = new TransCtpApi();
            List<Map<String, Object>>  map = api.loadCtpListById(ctpList);
            for (Map vendorObjNamemap :map){
                vendorObjName = MapUtils.getString(vendorObjNamemap, "vendorObjName");
            }
            if (vendorObjName.length() != 0) {
                param.put("S_VENDOR_OBJ_NAME", vendorObjName);
            }
        }

        StringBuffer resultMsg = new StringBuffer();
        CmdResult execute = CmdClient.execute(Cmd.setTerminationPointData, param);
        if (execute.getCmdStatus() != CmdStatus.RS_SUCCESS) {
            resultMsg.append("设置端口属性错误：").append(execute.getRemark()).append("\r\n");
        }
        ResponseData<Map<String, Object>> responseData = new ResponseData<>();
        Map<String,Object> response  = new HashMap<>();
        if (resultMsg.length() > 0 ) {  // 设置端口属性失败

            response.put("code",FAILUREFLAG);
            response.put("desc",resultMsg.toString());

            responseData = ResponseData.fail(resultMsg.toString());
            responseData.setData(response);

        } else {    // 设置端口属性成功

            response.put("code",SUCCESSFLAG);
            responseData = ResponseData.ok(response);
        }
        return responseData;
    }
}
