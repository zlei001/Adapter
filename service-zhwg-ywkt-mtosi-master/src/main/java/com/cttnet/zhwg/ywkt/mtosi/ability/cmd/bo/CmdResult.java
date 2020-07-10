package com.cttnet.zhwg.ywkt.mtosi.ability.cmd.bo;

import com.cttnet.zhwg.ywkt.mtosi.ability.enums.CmdStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * <pre>
 *  CMD命令result
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/2/13
 * @since java 1.8
 */
@NoArgsConstructor
@Data
public class CmdResult {

    /** 返回标识 */
    private CmdStatus cmdStatus;

    /** 命令线程标识 */
    private String threadId;

    /** 返回详情 */
    private String remark;

    /** 返回对象 */
    private Object result;

    public CmdResult(String threadId) {
        this.threadId = threadId;
    }
}
