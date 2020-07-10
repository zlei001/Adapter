package com.cttnet.zhwg.ywkt.mtosi.service.restconf;

import com.cttnet.common.enums.ResponseData;
import com.cttnet.zhwg.api.impl.TransNeApi;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.CmdClient;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.bo.CmdResult;
import com.cttnet.zhwg.ywkt.mtosi.ability.constants.Splits;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.CmdStatus;
import com.cttnet.zhwg.ywkt.mtosi.data.dto.request.RequestOfTakeOverSNC;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/4/3
 * @since java 1.8
 */
@Service
public class MultiLayerSubnetWorkService {
    /**结果标志 成功 1 ，失败 0*/
    private static final int SUCCESSFLAG = 1;

    private static final int FAILUREFLAG = 0;
    /**
     * 路径搜索
     * @param request 路径搜索请求
     * @return 响应
     */
    public ResponseData<?> takeOverSnc(RequestOfTakeOverSNC request) {

        //
         String emsId = request.getBody().getEmsId();
        List<String> neIds = request.getBody().getNeIds();

        Map<String, Object> param = new HashMap<>();
        param.put("emsId", emsId);
        if (CollectionUtils.isNotEmpty(neIds)) {
            //
            TransNeApi api = new TransNeApi();
            List<Map<String, Object>> list = api.loadNeListByIds(neIds);
            StringBuilder neNames = new StringBuilder();
            for (Map<String, Object> ne : list) {
                String vendorObjName = MapUtils.getString(ne, "vendorObjName");
                if (neNames.length() != 0) {
                    neNames.append(Splits.SHARP);
                }
                neNames.append(vendorObjName);
            }
            param.put("S_VENDOR_OBJ_NAME", neNames.toString());
        }

        StringBuffer resultMsg = new StringBuffer();
        CmdResult execute = CmdClient.execute(Cmd.takeOverSnc, param);

        if (execute.getCmdStatus() != CmdStatus.RS_SUCCESS) {
            resultMsg.append("路径搜索失败：").append(execute.getRemark()).append("\r\n");
        }

        ResponseData<Map<String, Object>> responseData;
        Map<String,Object> response  = new HashMap<>();
        if (resultMsg.length() > 0 ) {  // 失败

            response.put("code",FAILUREFLAG);
            response.put("desc",resultMsg.toString());

            responseData = ResponseData.fail(resultMsg.toString());
            responseData.setData(response);

        } else {    // 成功

            response.put("code",SUCCESSFLAG);
            responseData = ResponseData.ok(response);
        }
        return responseData;
    }
}
