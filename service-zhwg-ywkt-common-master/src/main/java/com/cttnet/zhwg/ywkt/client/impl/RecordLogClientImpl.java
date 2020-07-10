package com.cttnet.zhwg.ywkt.client.impl;

import com.cttnet.common.enums.ResponseData;
import com.cttnet.common.util.JackJson;
import com.cttnet.common.util.SysConfig;
import com.cttnet.zhwg.ywkt.client.AbstractBasicClient;
import com.cttnet.zhwg.ywkt.log.dto.request.OrderLogRelSaveRequestDTO;
import com.cttnet.zhwg.ywkt.log.dto.request.RecordLogSaveRequestDTO;
import org.springframework.http.MediaType;

import java.util.Map;

/**
 * <pre>订单日志调用Client</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-29
 * @since jdk 1.8
 */
public class RecordLogClientImpl extends AbstractBasicClient {


    /**
     * 保存日志
     * @param request request
     * @return ResponseData
     */
    public ResponseData<String> saveRecordLog(RecordLogSaveRequestDTO request) {

        String url = SysConfig.getProperty("ywkt.api.record-log.save-record-log",
                getRequestPrefix() + "/record/log/save");
        return this.post(url, request, ResponseData.class);


    }

    /**
     * 保存订单日志Rel
     * @param request request
     * @return ResponseData
     */
    public ResponseData<String> saveOrderLogRel(OrderLogRelSaveRequestDTO request) {

        String url = SysConfig.getProperty("ywkt.api.record-log.save-order-log-rel",
                getRequestPrefix() + "/order/log/rel/save");
        return this.post(url, request, ResponseData.class);


    }

    @Override
    protected String getApplicationName() {
        return SysConfig.getProperty("ywkt.api.record-log.application", "service-zhwg-ywkt-record-log");
    }
}
