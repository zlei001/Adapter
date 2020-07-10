package com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.maintenance;

import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.AbstractCmdAdapter;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.IsBoolean;
import com.cttnet.zhwg.ywkt.mtosi.ability.utils.BusinessUtils;
import com.cttnet.zhwg.ywkt.mtosi.utils.EnumUtils;
import org.apache.commons.collections.MapUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *  启动PRBS测试
 * </pre>
 *
 * @author dengkaihong
 * @date 2020/4/3
 * @since java 1.8
 */
public class EnablePRBSTestAdapter extends AbstractCmdAdapter {

    @Override
    protected void createKvs() {

        Map<String, String> ccMap = this.command.getKvs();
        ccMap.put("tpName", MapUtils.getString(this.param, "S_VENDOR_OBJ_NAME", ""));
        ccMap.put("direction", MapUtils.getString(this.param, "direction", ""));
        ccMap.put("testDuration", MapUtils.getString(this.param, "testDuration", ""));
        ccMap.put("sampleGranularity", MapUtils.getString(this.param, "sampleGranularity", ""));
        ccMap.put("prbsType", MapUtils.getString(this.param, "prbsType", ""));


        String accumulatingIndicator = MapUtils.getString(this.param, "accumulatingIndicator");
        //转换枚举
        IsBoolean isBoolean = EnumUtils.getEnum(IsBoolean.class, accumulatingIndicator, "getCode");
        if (isBoolean != null) {
            this.param.put("accumulatingIndicator", isBoolean.getName());
        }
        ccMap.put("accumulatingIndicator", MapUtils.getString(this.param, "accumulatingIndicator", ""));

        Map<String, String> vendorExtensions = new HashMap<String, String>();
        vendorExtensions.put("vendorExtensions",MapUtils.getString(this.param, "vendorExtensions", "vendorExtensions"));
        this.param.put("vendorExtensions",vendorExtensions);
        // /E/B/e/t/t 表示vendorExtensions在报文的层级 对应报文的开头字母 Envelope - Body -enablePRBSTestRequest -testParaList-testPara
        ccMap.put("/E/B/e/t/t/vendorExtensions", BusinessUtils.toNvsList(MapUtils.getMap(this.param, "vendorExtensions")));
    }

    /**
     * cmd
     *
     * @return cmd
     */
    @Override
    protected Cmd getCmd() {
        return Cmd.enablePRBSTest;
    }
}
