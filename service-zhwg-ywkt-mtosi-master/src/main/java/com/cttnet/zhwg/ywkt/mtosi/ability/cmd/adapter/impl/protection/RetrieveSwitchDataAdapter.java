package com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.protection;

import com.catt.zhwg.ws.parser.services.WsParser;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.AbstractCmdAdapter;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.bo.CmdResult;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.FactoryName;
import freemarker.template.utility.StringUtil;
import org.apache.commons.collections.MapUtils;

import java.util.LinkedList;
import java.util.Map;

/**
 * <pre>
 * 查询保护组倒换状态
 * </pre>
 *
 * @author dengkaihong
 * @date 2020/7/8
 * @since java 1.8
 */
public class RetrieveSwitchDataAdapter extends AbstractCmdAdapter {


    @Override
    protected void createKvs() {

        Map<String, String> ccMap = this.command.getKvs();

        if (getFactoryName() == FactoryName.FH){
            // 烽火要求报文格式要求PG 接口查询返回EPG 要在此处做处理
            String OldvendorObjName = MapUtils.getString(this.param,  "vendorObjName");
           this.param.put("vendorObjName", StringUtil.replace(OldvendorObjName, "EPG", "PG"));
        }
        ccMap.put("reliableSinkCtpOrGroupRef", MapUtils.getString(this.param,"vendorObjName"));


    }

    @Override
    protected Cmd getCmd() {
        return Cmd.retrieveSwitchData;
    }
    /**
     * 更新返回结果
     */
    @Override
    protected void updateResult() {
        //如是适配器不只是返回状态，适配器内重写该方法，将cmdOut解析后设置到 cmdResult的result中
        LinkedList<Map<String, Object>> datas = (LinkedList<Map<String, Object>>) this.cmdOut.get(WsParser.KEY_DATA);
        CmdResult cmdResult = this.command.getCmdResult();
        cmdResult.setResult(datas);

    }

}
