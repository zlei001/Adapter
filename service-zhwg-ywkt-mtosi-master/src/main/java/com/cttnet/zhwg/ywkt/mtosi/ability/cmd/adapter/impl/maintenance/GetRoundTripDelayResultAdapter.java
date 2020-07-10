package com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.maintenance;

import com.catt.zhwg.ws.parser.services.WsParser;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.AbstractCmdAdapter;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.bo.CmdResult;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;
import org.apache.commons.collections.MapUtils;

import java.util.Map;

/**
 * <pre>
 *  查询OTN业务时延
 * </pre>
 *
 * @author dengkaihong
 * @date 2020/4/3
 * @since java 1.8
 */
public class GetRoundTripDelayResultAdapter extends AbstractCmdAdapter {

    @Override
    protected void createKvs() {
        Map<String, String> ccMap = this.command.getKvs();
        ccMap.put("tpName", MapUtils.getString(this.param, "S_VENDOR_OBJ_NAME", ""));
        ccMap.put("type", MapUtils.getString(this.param, "type", ""));
    }

    /**
     * cmd
     *
     * @return cmd
     */
    @Override
    protected Cmd getCmd() {
        return Cmd.getRoundTripDelayResult;
    }


    /**
     * 更新返回结果
     */
    @Override
    protected void updateResult() {
        //如是适配器不只是返回状态，适配器内重写该方法，将cmdOut解析后设置到 cmdResult的result中
        //只需要cmdout里面Key为DATA的值 返回时延结果
        String result =  MapUtils.getString(this.cmdOut, WsParser.KEY_STATUS);
        CmdResult cmdResult = this.command.getCmdResult();
        cmdResult.setResult(result);
    }



}
