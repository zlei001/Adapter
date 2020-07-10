package com.cttnet.zhwg.ywkt.log.basic;

import com.cttnet.common.enums.ResponseRecode;

/**
 * <pre>基础日志返回类</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-22
 * @since jdk 1.8
 */
public interface BasicLogResponse {

    /**
     * 返回状态
     * @return ResponseRecode
     */
    ResponseRecode recode();

    /**
     * 详情描述
     * @return desc
     */
    String desc();
}
