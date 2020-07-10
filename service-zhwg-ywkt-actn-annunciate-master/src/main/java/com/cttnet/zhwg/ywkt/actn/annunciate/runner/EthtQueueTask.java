package com.cttnet.zhwg.ywkt.actn.annunciate.runner;


import com.alibaba.fastjson.JSON;
import com.cttnet.common.util.SpringContextUtils;
import com.cttnet.ywkt.actn.bean.eth.EthtSvc;
import com.cttnet.zhwg.ywkt.actn.annunciate.data.dto.ems.EmsDTO;
import com.cttnet.zhwg.ywkt.actn.annunciate.data.dto.nce.messageQueue.EthQueueDTO;
import com.cttnet.zhwg.ywkt.actn.annunciate.enums.BusinessTypeEnum;
import com.cttnet.zhwg.ywkt.actn.annunciate.service.NCEmessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * <p>Description: 通告队列-以太</p>
 *
 * @author suguisen
 */
@Slf4j
public class EthtQueueTask implements Runnable {
    private NCEmessageService nCEmessageService = SpringContextUtils.getBean(NCEmessageService.class);

    private final static BusinessTypeEnum businessType = BusinessTypeEnum.ETH;

    private final static LinkedBlockingQueue<EthQueueDTO> QUEUE = new LinkedBlockingQueue<>();

    private boolean state = true;

    public EthtQueueTask() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> state = false));
    }

    @Override
    public void run() {
        while (state) {
            try {
                EthQueueDTO ethQueueDTO = QUEUE.take();
                //以太业务状态
                EthtSvc ethtSvc = ethQueueDTO.getEthtSvc();
                if ("ethSvcPs".equals(ethQueueDTO.getMethodName())) {
                    String tunnelName = "";
                    if (ethtSvc.getUnderlay().getSdhTunnels() != null) {
                        //eos sdh
                        tunnelName = ethtSvc.getUnderlay().getSdhTunnels().get(0).getName();
                    } else if (ethtSvc.getUnderlay().getOtnTunnels() != null) {
                        //eoo
                        tunnelName = ethtSvc.getUnderlay().getOtnTunnels().get(0).getName();
                    }
                    ethSvcPs(ethQueueDTO.getEmsDTO(), ethQueueDTO.getUuid(), ethQueueDTO.getPs(), ethtSvc.getEthtsvctitle(), tunnelName);
                }
                //以太业务变更
                else if ("ethSvc".equals(ethQueueDTO.getMethodName())) {
                    ethSvc(ethQueueDTO.getEmsDTO(), ethtSvc);
                }
                //以太业务CPE上线
                else if ("cpeOnline".equals(ethQueueDTO.getMethodName())) {
                    ethCpeOnline(ethQueueDTO.getEmsDTO(), ethQueueDTO.getUuid());
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
     * <p>Description: 以太业务状态</p>
     *
     * @author suguisen
     */
    private void ethSvcPs(EmsDTO emsDTO, String name, String ps, String ethtsvctitle, String tunnelName) {
        log.info("以太业务状态，{}:{}", name, ps);
        nCEmessageService.dealClientOrEthtSvcPs(name, ps, emsDTO, ethtsvctitle, tunnelName, businessType);
    }

    /**
     * <p>Description: 以太业务变更</p>
     *
     * @author suguisen
     */
    private void ethSvc(EmsDTO emsDTO, EthtSvc instance) {
        log.info("以太业务变更，{}", JSON.toJSONString(instance));
        nCEmessageService.dealEthtSvcDelete(instance.getEthtsvcname(), emsDTO, businessType);
    }

    /**
     * <p>Description: 以太业务CPE上线</p>
     *
     * @author suguisen
     */
    private void ethCpeOnline(EmsDTO emsDTO, String name) {
        log.info("以太业务CPE上线，业务id：{}", name);
        if (StringUtils.isNotBlank(name)) {
            nCEmessageService.cpeOnline(name, emsDTO, businessType);
        }
    }

    /**
     * 添加任务
     *
     * @param ethQueueDTO 以太消息通告对象
     */
    public static void addTask(EthQueueDTO ethQueueDTO) {

        try {
            QUEUE.put(ethQueueDTO);
        } catch (InterruptedException e) {
            log.error("添加以太任务失败", e);
        }

    }


}
