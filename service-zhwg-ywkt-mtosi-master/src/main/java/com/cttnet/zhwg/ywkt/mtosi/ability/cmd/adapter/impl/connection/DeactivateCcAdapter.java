package com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.connection;

import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;

/**
 * <pre>
 *  去激活交叉连接
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/4/3
 * @since java 1.8
 */
public class DeactivateCcAdapter extends DeactivateAndDeleteCcAdapter {

    @Override
    protected Cmd getCmd() {
        return Cmd.deactivateCrossConnection;
    }
}
