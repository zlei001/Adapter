package com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.alarm;

import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.AbstractCmdAdapter;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;
import org.apache.commons.collections.MapUtils;

import java.util.Map;

/**
 * <pre>
 *  激活告警信息
 * </pre>
 *
 * @author wangzhaoshsi
 * @date 2020/2/21
 * @since java 1.8
 */
public class GetActiveAlarmsAdapter extends AbstractCmdAdapter {


    @Override
    protected void createKvs() {

        String probableCauseList = MapUtils.getString(this.param, "probableCauseList");
        probableCauseList = probableCauseList.replace("~ru=", "~v14:ru=");
        probableCauseList = probableCauseList.replace("~contra=", "~v14:contra=");
        probableCauseList = probableCauseList.replace("~probableCause=", "~v14:probableCause=");

        //固定参数
        Map<String, String> ccMap = this.command.getKvs();
        ccMap.put("scope", MapUtils.getString(this.param, "S_VENDOR_OBJ_NAME"));
        ccMap.put("source", MapUtils.getString(this.param, "source"));
        ccMap.put("perceivedSeverityList", MapUtils.getString(this.param, "perceivedSeverityList"));
        ccMap.put("probableCauseList", probableCauseList);
        ccMap.put("acknowledgeIndication", MapUtils.getString(this.param, "acknowledgeIndication"));
        ccMap.put("queryExpression", MapUtils.getString(this.param, "queryExpression"));

    }

    /**
     * cmd
     *
     * @return cmd
     */
    @Override
    protected Cmd getCmd() {
        return Cmd.getActiveAlarms;
    }


}
