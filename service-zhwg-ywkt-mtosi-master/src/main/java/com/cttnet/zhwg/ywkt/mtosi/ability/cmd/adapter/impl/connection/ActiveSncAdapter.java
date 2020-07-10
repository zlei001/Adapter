package com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.connection;

import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;

/**
 * <pre>
 *  激活子网连接
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/4/3
 * @since java 1.8
 */
public class ActiveSncAdapter extends CreateAndActiveSncAdapter {

    @Override
    protected Cmd getCmd() {
        return Cmd.activateSubnetWorkConnection;
    }

}
