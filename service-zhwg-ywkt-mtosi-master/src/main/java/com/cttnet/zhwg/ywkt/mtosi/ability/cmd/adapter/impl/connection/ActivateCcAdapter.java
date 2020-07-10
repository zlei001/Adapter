package com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.connection;

import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;

/**
 * <pre>
 *  激活交叉连接
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/3/23
 * @since java 1.8
 */
public class ActivateCcAdapter extends CreateAndActiveCcAdapter{

   @Override
    protected Cmd getCmd() {
        return Cmd.activateCrossConnection;
    }

}
