package com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl;

import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.AbstractCmdAdapter;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;

/**
 * <pre>
 * 不支持类型操作
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/2/13
 * @since java 1.8
 */
public class NonSupportAdapter extends AbstractCmdAdapter {


    @Override
    protected void createKvs() {

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
