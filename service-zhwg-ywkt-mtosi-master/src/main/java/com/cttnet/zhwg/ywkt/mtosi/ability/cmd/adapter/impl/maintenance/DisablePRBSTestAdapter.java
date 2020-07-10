package com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.maintenance;

import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.AbstractCmdAdapter;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;
import org.apache.commons.collections.MapUtils;

import java.util.Map;

/**
 * <pre>
 *  关闭PRBS测试
 * </pre>
 *
 * @author dengkaihong
 * @date 2020/4/3
 * @since java 1.8
 */
public class DisablePRBSTestAdapter extends AbstractCmdAdapter {

    @Override
    protected void createKvs() {

        Map<String, String> ccMap = this.command.getKvs();
        ccMap.put("tpName", MapUtils.getString(this.param, "S_VENDOR_OBJ_NAME", ""));

    }

    /**
     * cmd
     *
     * @return cmd
     */
    @Override
    protected Cmd getCmd() {
        return Cmd.disablePRBSTest;
    }
}
