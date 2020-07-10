package com.cttnet.zhwg.ywkt.mtosi.ability.cmd;

import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.AbstractCmdAdapter;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.bo.CmdResult;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;

import java.util.Map;

/**
 * <pre>
 *  南向下发命令
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/1/11
 * @since java 1.8
 */
public class CmdClient {

    /**
     * 执行
     * @param param
     * @return
     */
    public static CmdResult execute(Cmd cmd, Map<String, Object> param) {

        AbstractCmdAdapter cmdAdapter = CmdFactory.getInstance(cmd);
        CmdResult cmdResult = cmdAdapter.doCommand(param);
        return cmdResult;
    }

}
