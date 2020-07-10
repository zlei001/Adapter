package com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.connection;

import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;

/**
 * <pre>
 *  删除交叉连接
 * </pre>
 *
 * @author dengkaihong
 * @date 2020/4/15
 * @since java 1.8
 */
public class DeleteCcAdapter  extends  DeactivateAndDeleteCcAdapter{

    @Override
    protected Cmd getCmd() {
        return Cmd.deleteCrossConnection;
    }
}
