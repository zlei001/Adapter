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
 *  创建保护组
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/4/3
 * @since java 1.8
 */
public class CreateProtectionGroupAdapter extends AbstractCmdAdapter {


    @Override
    protected void createKvs() {

        Map<String, String> ccMap = this.command.getKvs();

        Map<String, Object> protectionMap =  MapUtils.getMap(this.param,"protectionMap");

        ccMap.put("parameterList", MapUtils.getString(this.param ,"parameter"));
        ccMap.put("name", MapUtils.getString(this.param,  "S_VENDOR_OBJ_NAME"));
        ccMap.put("userLabel", MapUtils.getString(this.param,  "userLabel"));
        ccMap.put("type", MapUtils.getString(this.param,  "type"));
        ccMap.put("reversionMode", MapUtils.getString(this.param,  "reversionMode"));
        ccMap.put("layerRate", MapUtils.getString(this.param,  "layerRate"));

        ccMap.put("protectionRelatedTpRefList", MapUtils.getString(protectionMap,  "protectionRelatedTpRefList"));
        ccMap.put("/E/B/c/c/vendorExtensions", MapUtils.getString(this.param,  "vendorExtensions"));
    }

    @Override
    protected Cmd getCmd() {
        return Cmd.createProtectionGroup;
    }
    /**
     * 更新返回结果
     */
    @Override
    protected void updateResult() {
        //如是适配器不只是返回状态，适配器内重写该方法，将cmdOut解析后设置到 cmdResult的result中
        LinkedList<Map<String, Object>> datas = (LinkedList<Map<String, Object>>) this.cmdOut.get(WsParser.KEY_DATA);
        Map<String, Object> data = datas.size() > 0 ? datas.get(0) : new HashMap<String, Object>();

        CmdResult cmdResult = this.command.getCmdResult();
        cmdResult.setResult(data);

    }

}
