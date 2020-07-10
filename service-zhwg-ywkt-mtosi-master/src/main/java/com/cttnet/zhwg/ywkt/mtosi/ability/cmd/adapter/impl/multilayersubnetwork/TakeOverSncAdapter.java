package com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.multilayersubnetwork;

import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.AbstractCmdAdapter;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;
import org.apache.commons.collections.MapUtils;

import java.util.Map;

/**
 * <pre>
 *  路径搜索
 * </pre>
 *
 * @author wangzhaoshsi
 * @date 2020/2/20
 * @since java 1.8
 */
public class TakeOverSncAdapter extends AbstractCmdAdapter {

    @Override
    protected void createKvs() {

        //固定参数
        Map<String, String> ccMap = this.command.getKvs();
        ccMap.put("NEList", MapUtils.getString(this.param,  "S_VENDOR_OBJ_NAME"));
    }

    /**
     * cmd
     *
     * @return cmd
     */
    @Override
    protected Cmd getCmd() {
        return Cmd.takeOverSnc;
    }

}
