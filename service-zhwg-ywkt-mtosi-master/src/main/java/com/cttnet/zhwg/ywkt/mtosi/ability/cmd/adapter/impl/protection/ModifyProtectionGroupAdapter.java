package com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.protection;

import com.catt.zhwg.ws.parser.services.WsParser;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.AbstractCmdAdapter;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.bo.CmdResult;
import com.cttnet.zhwg.ywkt.mtosi.ability.constants.Splits;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;
import org.apache.commons.collections.MapUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * <pre>
 *  修改保护组
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/4/3
 * @since java 1.8
 */
public class ModifyProtectionGroupAdapter extends AbstractCmdAdapter {

    @Override
    protected Cmd getCmd() {
        return Cmd.modifyProtectionGroup;
    }

    @Override
    protected void createKvs() {

        StringBuilder parameterList = new StringBuilder();

        Map<String, Object> parameter =  MapUtils.getMap(this.param,"parameter");

        String wtrTime = MapUtils.getString(parameter,"wtrTime");
        String holdOffTime =  MapUtils.getString(parameter,"holdOffTime");
        String springNodeId =  MapUtils.getString(parameter,"springNodeId");

        parameterList.append(Splits.WAVE).append("wtrTime=").append(wtrTime);
        parameterList.append(Splits.WAVE).append("holdOffTime=").append(holdOffTime);
        parameterList.append(Splits.WAVE).append("springNodeId=").append(springNodeId);

        Map<String, String> ccMap = this.command.getKvs();
        ccMap.put("/E/B/m/pgRef", MapUtils.getString(this.param, "S_VENDOR_OBJ_NAME", ""));
        ccMap.put("parameterList", String.valueOf(parameterList));

    }

    /**
     * 更新返回结果
     */
    @Override
    protected void updateResult() {
        //如是适配器不只是返回状态，适配器内重写该方法，将cmdOut解析后设置到 cmdResult的result中
        LinkedList<Map<String, Object>> datas = (LinkedList<Map<String, Object>>) this.cmdOut.get(WsParser.KEY_DATA);
        Map<String, Object> data = datas.size() > 0 ? datas.get(0) : new HashMap<String, Object>();

        data.remove("task_id");
        data.remove("save_table_name");

        CmdResult cmdResult = this.command.getCmdResult();
        cmdResult.setResult(data);

    }

}
