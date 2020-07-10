package com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.maintenance;

import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.AbstractCmdAdapter;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.bo.CmdResult;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;
import org.apache.commons.collections.MapUtils;

import java.util.LinkedList;
import java.util.Map;

/**
 * <pre>
 * 查询网元上所有端口维护操作状态,
 * </pre>
 *
 * @author dengkaihong
 * @date 2020/7/6
 * @since java 1.8
 */
public class GetActiveMaintenanceOperationsAdapter extends AbstractCmdAdapter {

    @Override
    protected void createKvs() {

        Map<String, String> ccMap = this.command.getKvs();
        ccMap.put("tpOrMeName", MapUtils.getString(this.param, "S_VENDOR_OBJ_NAME", ""));
    }

    /**
     * cmd
     *
     * @return cmd
     */
    @Override
    protected Cmd getCmd() {
        return Cmd.getActiveMaintenanceOperations;
    }

    /**
     * 更新报文出参参数
     */
    @Override
    protected void updateResult() {

        LinkedList<Map<Object,Object>> result = (LinkedList<Map<Object,Object>>) this.cmdOut.get("DATA");
       /* LinkedList<Map<String,String> > outMap = new LinkedList<>();
       for (Map map : result){
            Map<String,String> resultMap = new HashMap<>();
            resultMap.put("tpName",MapUtils.getString(map,"tpName"));
            resultMap.put("maintenanceOperation",MapUtils.getString(map,"maintenanceOperation"));
            outMap.add(resultMap);
        }*/
        // TODO  暂时不清楚需要返回什么参数 先把所有参数抛给上层。
        CmdResult cmdResult = this.command.getCmdResult();
        cmdResult.setResult(result);
    }
}
