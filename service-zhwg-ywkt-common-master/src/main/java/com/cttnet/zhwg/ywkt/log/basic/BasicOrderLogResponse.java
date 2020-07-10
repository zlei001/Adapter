package com.cttnet.zhwg.ywkt.log.basic;

/**
 * <pre>基础订单日志返回</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-22
 * @since jdk 1.8
 */
public interface BasicOrderLogResponse extends BasicLogResponse {

    /**
     * 订单ID
     * @return orderId
     */
    String orderId();
}
