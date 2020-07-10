package com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.terminationpoint;

import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.AbstractCmdAdapter;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.FactoryName;
import org.apache.commons.collections.MapUtils;

import java.util.Map;

/**
 * <pre>
 *  设置端口层参数（通用）
 * </pre>
 *
 * @author wangzhaoshsi
 * @date 2020/2/19
 * @since java 1.8
 */
public class SetTerminationPointDataAdapter extends AbstractCmdAdapter {



    @Override
    protected void createKvs() {

        //调整参数方向
        adjustParam();
        //固定参数
        Map<String, String> ccMap = this.command.getKvs();
        ccMap.put("tpRef", MapUtils.getString(this.param, "S_VENDOR_OBJ_NAME", ""));
        ccMap.put("transmissionParametersList", MapUtils.getString(this.param, "transmissionParametersList"));


        //TODO 公共请求信息
//        ccMap.putAll(MtosiAtomicCapacityConfig.getInstn().getRequestthis.param(emsname));
    }

    /**
     * cmd
     *
     * @return cmd
     */
    @Override
    protected Cmd getCmd() {
        return Cmd.setTerminationPointData;
    }

    /**
     * 调整参数
     */
    private void adjustParam() {

        String tpRef = MapUtils.getString(this.param, "S_VENDOR_OBJ_NAME");
        String transmissionParametersList = MapUtils.getString(this.param, "transmissionParametersList");
//			String transmissionParametersList = getTransmissionParametersList(inMap);
        /*
         * 烽火修改业务类型
         * 1、OTU4修改为100GE使用CTP=och/odu4=1下发
         * 2、100GE修改为OTU4使用CTP=odu4=1下发
         */
        if (FactoryName.FH == getFactoryName()) {
            if (transmissionParametersList.contains("ClientServiceType=100GE")) {

                int pos = tpRef.indexOf("~CTP=");
                if(pos >= 0) {
                    tpRef = tpRef.substring(0, pos) + "~CTP=/och=1/odu4=1";
                }
            } else if (transmissionParametersList.contains("ClientServiceType=OTU4")) {
                int pos = tpRef.indexOf("~CTP=");
                if(pos >= 0) {
                    tpRef = tpRef.substring(0, pos) + "~CTP=/odu4=1";
                }
            }
        }

        /*
         * 华为修改业务类型
         * 1、OTU4修改为100GE使用CTP=och=1下发
         * 2、100GE修改为OTU4使用CTP=dsr=1下发
         */
        if (FactoryName.HW == getFactoryName()) {
            if (transmissionParametersList.contains("ClientServiceType=100GE")) {

                int pos = tpRef.indexOf("~CTP=");
                if(pos >= 0) {
                    tpRef = tpRef.substring(0, pos) + "~CTP=/och=1";
                }
            } else if (transmissionParametersList.contains("ClientServiceType=OTU4")) {
                int pos = tpRef.indexOf("~CTP=");
                if(pos >= 0) {
                    tpRef = tpRef.substring(0, pos) + "~CTP=/dsr=1";
                }
            }
        }
        this.param.put("S_VENDOR_OBJ_NAME", tpRef);
    }

}
