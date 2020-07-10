package com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.terminationpoint;

import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.AbstractCmdAdapter;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.bo.CmdResult;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;
import org.apache.commons.collections.MapUtils;

import java.util.LinkedList;
import java.util.Map;
/**
 * <pre>
 *  查询端口属性
 * </pre>
 *
 * @author dengkaihong
 * @date 2020/4/3
 * @since java 1.8
 */
public class GetTerminationPointAdapter extends AbstractCmdAdapter {

    @Override
    protected void createKvs() {
        //固定参数
        Map<String, String> ccMap = this.command.getKvs();
        ccMap.put("tpRef", MapUtils.getString(this.param, "S_VENDOR_OBJ_NAME", ""));
    }

    /**
     * cmd
     *
     * @return cmd
     */
    @Override
    protected Cmd getCmd() {
        return Cmd.getTerminationPoint;
    }
    /**
     * 更新报文出参参数
     */
    @Override
    protected void updateResult() {
        LinkedList<Map<Object,Object>> result = (LinkedList<Map<Object,Object>>) this.cmdOut.get("DATA");
        StringBuffer  out  = new StringBuffer();
        for (Map map : result){
            out.append(MapUtils.getString(map,"transmissionParametersList"));
         }
        CmdResult cmdResult = this.command.getCmdResult();
        cmdResult.setResult(out.toString());
    }
}
