package com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.connection;

import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.AbstractCmdAdapter;
import org.apache.commons.collections.MapUtils;

import java.util.Map;

/**
 * <pre>
 *  去激活并删除snc
 * </pre>
 *
 * @author wangzhaoshsi
 * @date 2020/2/19
 * @since java 1.8
 */
public class DeactivateAndDeleteSncAdapter extends AbstractCmdAdapter {


    @Override
    protected void createKvs() {
        //固定参数
        Map<String, String> ccMap = this.command.getKvs();
        ccMap.put("sncRef", MapUtils.getString(this.param, "sncName", ""));
    }

    /**
     * cmd
     *
     * @return cmd
     */
    @Override
    protected Cmd getCmd() {
        return Cmd.deactivateAndDeleteSubnetWorkConnection;
    }

}
