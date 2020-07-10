package com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.protection;


import com.catt.zhwg.ws.parser.services.WsParser;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.AbstractCmdAdapter;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.bo.CmdResult;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;
import org.apache.commons.collections.MapUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
/**
 * <pre>
 *  查询所有保护组
 * </pre>
 *
 * @author dengkaihong
 * @date 2020/4/14
 * @since java 1.8
 */
public class GetAllProtectionGroupsAdapter extends AbstractCmdAdapter {
    @Override
    protected void createKvs() {
        //固定参数
        Map<String, String> ccMap = this.command.getKvs();
        ccMap.put("meRef", MapUtils.getString(this.param,  "S_VENDOR_OBJ_NAME"));
    }
    @Override
    protected Cmd getCmd() {
        return Cmd.getAllProtectionGroups;
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
