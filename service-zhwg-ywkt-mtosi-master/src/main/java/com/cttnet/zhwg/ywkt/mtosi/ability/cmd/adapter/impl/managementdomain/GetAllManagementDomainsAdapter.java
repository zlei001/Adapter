package com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.managementdomain;

import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.AbstractCmdAdapter;

import java.util.Map;

/**
 * <pre>
 *  默认下发方法
 * </pre>
 *
 * @author wangzhaoshsi
 * @date 2020/2/20
 * @since java 1.8
 */
public class GetAllManagementDomainsAdapter extends AbstractCmdAdapter {


    @Override
    protected void createKvs() {

        //固定参数
        Map<String, String> ccMap = this.command.getKvs();

    }

    /**
     * cmd
     *
     * @return cmd
     */
    @Override
    protected Cmd getCmd() {
        return null;
    }

}
