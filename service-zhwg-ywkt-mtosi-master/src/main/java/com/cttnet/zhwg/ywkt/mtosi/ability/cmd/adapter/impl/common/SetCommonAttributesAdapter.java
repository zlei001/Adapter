package com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.common;

import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.AbstractCmdAdapter;
import com.cttnet.zhwg.ywkt.mtosi.ability.constants.Splits;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.FactoryName;
import org.apache.commons.collections.MapUtils;

import java.util.Map;

/**
 * <pre>
 *  设置网元/板卡/PTP/SNC/TL/拓扑子网通用属性
 * </pre>
 *
 * @author wangzhaoshsi
 * @date 2020/2/19
 * @since java 1.8
 */
public class SetCommonAttributesAdapter extends AbstractCmdAdapter {

    @Override
    protected void createKvs() {

        // 调整参数方向
        adjustParam();
        // 固定参数
        Map<String, String> ccMap = this.command.getKvs();
        ccMap.put("objectRef", MapUtils.getString(this.param, "S_VENDOR_OBJ_NAME", ""));

        //非中兴网管，设置userLabel属性值
        if(!(FactoryName.ZTE == getFactoryName())){
            ccMap.put("userLabel", MapUtils.getString(this.param,  "userLabel"));
        }
        // 板卡/PTP 不支持设置aliasNameList属性  PTP 标识 PTP 板卡标识 EH、EQ
        String vendorObjName = MapUtils.getString(this.param,"S_VENDOR_OBJ_NAME");
        if (!vendorObjName.contains("PTP") && !vendorObjName.contains("EH") && vendorObjName.contains("EQ")){
            ccMap.put("aliasNameList", MapUtils.getString(this.param, "aliasNameList", ""));
        }

  }

    /**
     * cmd
     * @return cmd
     */
    @Override
    protected Cmd getCmd() {
        return Cmd.setCommonAttributes;
    }

    /**
     * 调整参数
     */
    private void adjustParam() {
        String aliasNameList = MapUtils.getString(this.param,  "aliasNameList");
        //xml对<、>进行转义兼容
        if(aliasNameList.contains(Splits.LT)){
            aliasNameList = aliasNameList.replace(Splits.LT,"&lt;");
        }
        if(aliasNameList.contains(Splits.GT)){
            aliasNameList = aliasNameList.replace(Splits.GT,"&gt;");
        }
        if(!"".equals(aliasNameList)){
            aliasNameList = Splits.WAVE + "nativeEMSName=" +  aliasNameList;
        }
        this.param.put("aliasNameList", aliasNameList);
    }

}
