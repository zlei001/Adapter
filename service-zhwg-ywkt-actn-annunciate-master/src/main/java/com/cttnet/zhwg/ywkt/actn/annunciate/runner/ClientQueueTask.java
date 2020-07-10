package com.cttnet.zhwg.ywkt.actn.annunciate.runner;

import com.alibaba.fastjson.JSON;
import com.cttnet.common.util.SpringContextUtils;
import com.cttnet.ywkt.actn.bean.client.ClientSvcInstance;
import com.cttnet.zhwg.ywkt.actn.annunciate.data.dto.ems.EmsDTO;
import com.cttnet.zhwg.ywkt.actn.annunciate.enums.BusinessTypeEnum;
import com.cttnet.zhwg.ywkt.actn.annunciate.data.dto.nce.messageQueue.ClientQueueDTO;
import com.cttnet.zhwg.ywkt.actn.annunciate.service.NCEmessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * <p>Description: 通告队列-透传</p>
 *
 * @author suguisen
 */
@Slf4j
public class ClientQueueTask implements Runnable {
    private NCEmessageService nCEmessageService = SpringContextUtils.getBean(NCEmessageService.class);

    private final static BusinessTypeEnum businessType = BusinessTypeEnum.CLIENT;

    private static final LinkedBlockingQueue<ClientQueueDTO> QUEUE = new LinkedBlockingQueue<>();

    private boolean state = true;

    public ClientQueueTask() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> state = false));
    }

    @Override
    public void run() {
        while (state) {
            try {
                ClientQueueDTO clientQueueDTO = QUEUE.take();
                //透传业务状态
                ClientSvcInstance clientSvcInstance = clientQueueDTO.getClientSvcInstance();
                if ("clientSvcPs".equals(clientQueueDTO.getMethodName())) {
                    clientSvcPs(clientQueueDTO.getEmsDTO(), clientQueueDTO.getUuid(), clientQueueDTO.getPs(),
                            clientSvcInstance.getClientSvcTitle(), clientSvcInstance.getSvcTunnels().get(0).getTunnelName());
                }
                //透传业务变更
                else if ("clientSvc".equals(clientQueueDTO.getMethodName())) {
                    clientSvc(clientQueueDTO.getEmsDTO(), clientSvcInstance);
                }
                //透传业务CPE上线
                else if ("cpeOnline".equals(clientQueueDTO.getMethodName())) {
                    clientCpeOnline(clientQueueDTO.getEmsDTO(), clientQueueDTO.getUuid());
                }
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    /**
     * <p>Description: 透传业务状态</p>
     *
     * @author suguisen
     */
    private void clientSvcPs(EmsDTO emsDTO, String name, String ps, String title, String tunnelName) {
        log.info("透传业务状态，{}:{}", name, ps);
        nCEmessageService.dealClientOrEthtSvcPs(name, ps, emsDTO, title, tunnelName, businessType);
    }

    /**
     * <p>Description: 透传业务变更</p>
     *
     * @author suguisen
     */
    private void clientSvc(EmsDTO emsDTO, ClientSvcInstance instance) {
        log.info("透传业务变更，{}", JSON.toJSONString(instance));
        nCEmessageService.dealClientSvcDelete(instance.getClientSvcName(), emsDTO, businessType);
    }

    /**
     * <p>Description: 透传业务CPE上线</p>
     *
     * @author suguisen
     */
    private void clientCpeOnline(EmsDTO emsDTO, String name) {
        log.info("透传业务CPE上线，业务id：{}", name);
        if (StringUtils.isNotBlank(name)) {
            nCEmessageService.cpeOnline(name, emsDTO, businessType);
        }
    }

    /**
     * 添加任务
     *
     * @param clientQueueDTO 透传消息通告对象
     */
    public static void addTask(ClientQueueDTO clientQueueDTO) {

        try {
            QUEUE.put(clientQueueDTO);
        } catch (InterruptedException e) {
            log.error("添加透传任务失败", e);
        }
    }
}

