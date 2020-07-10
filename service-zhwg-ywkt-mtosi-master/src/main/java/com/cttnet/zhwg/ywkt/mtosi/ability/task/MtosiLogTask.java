package com.cttnet.zhwg.ywkt.mtosi.ability.task;

import com.cttnet.common.util.SpringContextUtils;
import com.cttnet.zhwg.ywkt.mtosi.data.po.MtosiLogPO;
import com.cttnet.zhwg.ywkt.mtosi.service.web.MtosiLogService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * <pre>
 *  南向日志入库任务
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/1/11
 * @since java 1.8
 */
@Slf4j
public class MtosiLogTask implements Runnable {

    /** 阻塞队列 */
    private static final LinkedBlockingQueue<List<MtosiLogPO>> QUEUE = new LinkedBlockingQueue<>();
    /** 运行状态 */
    @Setter
    private boolean state = true;

    /**
     * 构造方法
     */
    public MtosiLogTask() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> state = false));
    }

    public static void add(List<MtosiLogPO> mtWsExeLogs) {
        QUEUE.add(mtWsExeLogs);
    }

    @Override
    public void run() {
        MtosiLogService service = SpringContextUtils.getBean(MtosiLogService.class);
        while(state){
            try {
                List<MtosiLogPO> mtosiLogs = QUEUE.take();
                boolean b = service.saveBatch(mtosiLogs);
            } catch (Exception e) {
                log.error("南向日志入库失败", e);
            }
        }
    }
}
