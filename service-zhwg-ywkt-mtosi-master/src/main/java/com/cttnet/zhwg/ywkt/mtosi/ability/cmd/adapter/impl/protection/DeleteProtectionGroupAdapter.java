package com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.protection;

import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.AbstractCmdAdapter;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;
import org.apache.commons.collections.MapUtils;

import java.util.Map;

/**
 * <pre>
 *  删除保护组
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/4/3
 * @since java 1.8
 */
public class DeleteProtectionGroupAdapter extends AbstractCmdAdapter {
    @Override
    protected void createKvs() {
        Map<String, String> ccMap = this.command.getKvs();
        ccMap.put("NEList", MapUtils.getString(this.param,  "S_VENDOR_OBJ_NAME"));
    }

    @Override
    protected Cmd getCmd() {
        return Cmd.deleteProtectionGroup;
    }
}
