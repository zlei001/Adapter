package com.cttnet.zhwg.ywkt.mtosi.service.restconf;

import com.cttnet.common.enums.ResponseData;
import com.cttnet.common.util.StringUtil;
import com.cttnet.zhwg.api.impl.ChannelApi;
import com.cttnet.zhwg.api.impl.TransPortApi;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.CmdClient;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.bo.CmdResult;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.CmdStatus;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.SetCommonType;
import com.cttnet.zhwg.ywkt.mtosi.data.dto.request.RequestOfSetCommonAttributes;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *  南向公共配置Service
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/1/7
 * @since java 1.8
 */
@Service
public class SouthCommonService {

    /**结果标志 成功 1 ，失败 0*/
    private static final int SUCCESSFLAG = 1;

    private static final int FAILUREFLAG = 0;

    /**
     * 设置通用属性
     * @param request 设置通用属性
     * @return 响应
     */
    public ResponseData<?> setCommonAttributes(RequestOfSetCommonAttributes request) {

            String emsId = request.getBody().getEmsId();
            String objectId = request.getBody().getObjectId();
            String aliasNameList = request.getBody().getAliasNameList();
            String userLabel = request.getBody().getUserLabel();
            int commonType = request.getBody().getCommonType();

            Map<String, Object> param = new HashMap<>();
            param.put("emsId", emsId);
            param.put("aliasNameList",aliasNameList);
            param.put("userLabel",userLabel);

            //获取端口
        if (commonType == SetCommonType.PORT_ID.getValue()){
            if (StringUtil.isNotNull(objectId)){
                TransPortApi api = new TransPortApi();
                Map<String, Object>  map = api.loadPortById(objectId);
                String vendorObjName = MapUtils.getString(map, "vendorObjName");
                if (vendorObjName.length() != 0) {
                    param.put("S_VENDOR_OBJ_NAME", vendorObjName);
                }
            }
        }
        // 获取通道
        if (commonType == SetCommonType.CHANNEL.getValue()){
            if (StringUtil.isNotNull(objectId)){
                ChannelApi api = new ChannelApi();
                Map<String, Object>  map = api.loadChannelById(objectId);
                String vendorObjName = MapUtils.getString(map, "vendorObjName");
                param.put("S_VENDOR_OBJ_NAME", vendorObjName);
            }
        }



        StringBuffer resultMsg = new StringBuffer();
        CmdResult execute = CmdClient.execute(Cmd.setCommonAttributes, param);
        if (execute.getCmdStatus() != CmdStatus.RS_SUCCESS) {
            resultMsg.append("设置端口属性错误：").append(execute.getRemark()).append("\r\n");
        }

        ResponseData<Map<String, Object>> responseData = new ResponseData<>();
        Map<String,Object> response  = new HashMap<>();
        if (resultMsg.length() > 0 ) {  // 设置失败

            response.put("code",FAILUREFLAG);
            response.put("desc",resultMsg.toString());

            responseData = ResponseData.fail(resultMsg.toString());
            responseData.setData(response);

        } else {    // 设置成功

            response.put("code",SUCCESSFLAG);
            responseData = ResponseData.ok(response);
        }
        return responseData;
    }

}
