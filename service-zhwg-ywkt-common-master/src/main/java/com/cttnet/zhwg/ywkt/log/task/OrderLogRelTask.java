package com.cttnet.zhwg.ywkt.log.task;

import com.cttnet.common.enums.ResponseData;
import com.cttnet.common.util.JackJson;
import com.cttnet.zhwg.ywkt.client.impl.RecordLogClientImpl;
import com.cttnet.zhwg.ywkt.log.dto.request.OrderLogRelSaveRequestDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * <pre>日志任务</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-22
 * @since jdk 1.8
 */
@Slf4j
public class OrderLogRelTask implements Runnable{

    /** 阻塞队列 */
    private static final LinkedBlockingQueue<OrderLogRelSaveRequestDTO> QUEUE = new LinkedBlockingQueue<>();

    private static boolean state = false;

    private final RecordLogClientImpl recordLogClient;

    public OrderLogRelTask() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> state = false));
        this.recordLogClient = new RecordLogClientImpl();
    }

    @Override
    public void run() {

        state = true;
        // 日志入库
        while(state) {
            try {
                OrderLogRelSaveRequestDTO take = QUEUE.take();
                ResponseData<String> response = recordLogClient.saveOrderLogRel(take);
                log.info("新增订单日志关联记录：{}", JackJson.getBasetJsonData(response));
            } catch (Exception e) {
                log.error("保存订单日志关联异常", e);
            }
        }

    }

    /**
     * 添加日志任务
     * @param orderLogRelSaveRequestDTO orderLogRelSaveRequestDTO
     */
    public static void add(OrderLogRelSaveRequestDTO orderLogRelSaveRequestDTO) {

        if (!state) {
            log.info("任务未启动");
            return;
        }
        try {
            QUEUE.put(orderLogRelSaveRequestDTO);
        } catch (InterruptedException e) {
            log.error("添加流程日志异常", e);
        }
    }
}
