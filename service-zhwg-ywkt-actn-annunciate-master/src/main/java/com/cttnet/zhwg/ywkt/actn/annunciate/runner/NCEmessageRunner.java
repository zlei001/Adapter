package com.cttnet.zhwg.ywkt.actn.annunciate.runner;


import com.alibaba.fastjson.JSON;
import com.cttnet.ywkt.actn.bean.client.ClientSvcInstance;
import com.cttnet.ywkt.actn.bean.client.sub.ClientSvcErrorInfo;
import com.cttnet.ywkt.actn.bean.client.sub.Provisioning;
import com.cttnet.ywkt.actn.bean.eth.EthtSvc;
import com.cttnet.ywkt.actn.bean.eth.sub.EthtSvcErrorInfo;
import com.cttnet.ywkt.actn.bean.message.annunciate.Notification;
import com.cttnet.ywkt.actn.bean.message.subscribe.Subscribe;
import com.cttnet.ywkt.actn.bean.message.subscribe.SubscribeOutput;
import com.cttnet.ywkt.actn.bean.tunnel.TeTunnel;
import com.cttnet.ywkt.actn.enums.Operation;
import com.cttnet.ywkt.actn.message.annunciate.Annunciate;
import com.cttnet.zhwg.ywkt.actn.annunciate.data.dto.ems.EmsDTO;
import com.cttnet.zhwg.ywkt.actn.annunciate.data.dto.nce.messageQueue.ClientQueueDTO;
import com.cttnet.zhwg.ywkt.actn.annunciate.data.dto.nce.messageQueue.EthQueueDTO;
import com.cttnet.zhwg.ywkt.actn.annunciate.enums.ProvisioningStateEnum;
import com.cttnet.zhwg.ywkt.actn.annunciate.service.HeartbeatSerivce;
import com.cttnet.zhwg.ywkt.actn.annunciate.service.NCEmessageService;
import com.cttnet.zhwg.ywkt.actn.annunciate.service.message.streams.connection.StreamsService;
import com.cttnet.zhwg.ywkt.actn.annunciate.service.message.subscribe.SubscribeService;
import com.cttnet.zhwg.ywkt.actn.annunciate.util.AccessServiceInterfaceUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>Description: NCE消息通告</p>
 *
 * @author suguisen
 */
@Component
@Order(1)
@Slf4j
public class NCEmessageRunner implements CommandLineRunner {

    @Value("${actn.hw.ems-name:华为NCE网管}")
    private String emsName;

    @Autowired
    NCEmessageService nceMessageService;

    @Autowired
    private HeartbeatSerivce heartbeatSerivce;

    @Override
    public void run(String... args) throws Exception {
        //消息通告
        getNceMessage();
    }
    private void getNceMessage() {

        EmsDTO emsDTO = AccessServiceInterfaceUtil.getEmsDtoByName(emsName);
//        EmsPo emsDTO = new EmsPo();
//        emsDTO.setEmsId(ems.getEmsId());
//        emsDTO.setBaseUrl(ems.getBaseUrl());
//        emsDTO.setUsername(ems.getUsername());
//        emsDTO.setPassword(ems.getPassword());
////        emsDTO.setProtocol(ProtocolEnum.HTTP);
//        emsDTO.setSaveLog(false);

        //1、订阅
        Subscribe subscribe = new Subscribe();
        Subscribe.SubScribedNotifications subScribedNotifications = new Subscribe.SubScribedNotifications();
        subScribedNotifications.setEncoding("encode-json");
        subscribe.setSubScribedNotifications(subScribedNotifications);
        try {
            SubscribeOutput json = new SubscribeService(emsDTO).subscibed(subscribe);
            log.info("建立订阅成功:{}", json);
            log.info("开始建立通告长连接");

            /**  透传业务消息通告队列*/
            ClientQueueTask clientQueueTask = new ClientQueueTask();
            new Thread(clientQueueTask).start();
            /**  以太业务消息通告队列*/
            EthtQueueTask ethtQueueTask = new EthtQueueTask();
            new Thread(ethtQueueTask).start();

            new StreamsService(json.getOutput().getId(), emsDTO, new Annunciate() {
                @Override
                public void clientSvc(Operation operation, ClientSvcInstance instance, String changeTime) {
                    log.info("消息通告：透传业务变更，{}", JSON.toJSONString(instance));
                    if (operation == Operation.DELETE) {
                        ClientQueueDTO clientQueueDTO = new ClientQueueDTO(operation, instance, changeTime, emsDTO, "clientSvc");
                        ClientQueueTask.addTask(clientQueueDTO);
                    } else if (operation == Operation.UPDATE) {
                        String ps = instance.getProvisioningState();
                        String name = instance.getClientSvcName();
                        if (ProvisioningStateEnum.STATESETUPFAILED.getValue().equals(ps)) {
                            Provisioning provisioning = instance.getProvisioning();
                            log.info("消息通告：透传业务错误信息，{}", "name : " + name + "," + JSON.toJSONString(provisioning));
                            nceMessageService.dealClientSvcError(name, provisioning, emsDTO);
                        } else if (ProvisioningStateEnum.STATEUP.getValue().equals(ps) || ProvisioningStateEnum.MODIFIED.getValue().equals(ps)
                                || ProvisioningStateEnum.MODIFYFAILED.getValue().equals(ps)) {
                            ClientQueueDTO clientQueueDTO = new ClientQueueDTO(operation, name, ps, changeTime, emsDTO, "clientSvcPs");
                            clientQueueDTO.setClientSvcInstance(instance);
                            ClientQueueTask.addTask(clientQueueDTO);
                        }
                    }
                }

                @Override
                public void ethSvc(Operation operationEnum, EthtSvc instance, String changeTime) {
                    log.info("消息通告：以太业务变更，{}", JSON.toJSONString(instance));
                    if (operationEnum == Operation.DELETE) {
                        EthQueueDTO ethQueueDTO = new EthQueueDTO(operationEnum, instance, changeTime, emsDTO, "ethSvc");
                        EthtQueueTask.addTask(ethQueueDTO);
                    } else if (operationEnum == Operation.UPDATE) {
                        String ps = instance.getState().getProvState();
                        String name = instance.getEthtsvcname();
                        if (ProvisioningStateEnum.STATESETUPFAILED.getValue().equals(ps)) {
                            Provisioning provisioning = instance.getProvisioning();
                            log.info("消息通告：以太业务错误信息，{}", "name : " + name + "," + JSON.toJSONString(provisioning));
                            nceMessageService.dealEthtSvcError(name, provisioning, emsDTO);
                        } else if (ProvisioningStateEnum.STATEUP.getValue().equals(ps) || ProvisioningStateEnum.MODIFIED.getValue().equals(ps)
                                || ProvisioningStateEnum.MODIFYFAILED.getValue().equals(ps)) {
                            EthQueueDTO ethQueueDTO = new EthQueueDTO(operationEnum, name, ps, changeTime, emsDTO, "ethSvcPs");
                            ethQueueDTO.setEthtSvc(instance);
                            EthtQueueTask.addTask(ethQueueDTO);
                        }
                    }
                }

                @Override
                public void tunnel(Operation operationEnum, TeTunnel instance, String changeTime) {
                    log.info("消息通告：隧道，{}", JSON.toJSONString(instance));
                }

                @Override
                public void clientSvcPs(Operation operationEnum, String name, String ps, String changeTime) {
                    log.info("--消息通告：透传业务状态，{}:{}", name, ps);
                }

                @Override
                public void ethSvcPs(Operation operationEnum, String name, String ps, String changeTime) {
                    log.info("--消息通告：以太业务状态，{}:{}", name, ps);
                }

                @Override
                public void tunnelPs(Operation operationEnum, String name, String ps, String changeTime) {
                    log.info("--消息通告：TE状态，{}:{}", name, ps);
                }

                @Override
                public void clientCpeOnline(Operation operationEnum, String name, String changeTime) {
                    log.info("消息通告：透传业务CPE上线，业务id：{}", name);
                    if(StringUtils.isNotBlank(name)){
                        ClientQueueDTO clientQueueDTO = new ClientQueueDTO(operationEnum,name,null,changeTime, emsDTO,"cpeOnline");
                        ClientQueueTask.addTask(clientQueueDTO);
                    }
                }

                @Override
                public void ethCpeOnline(Operation operationEnum, String name, String changeTime) {
                    log.info("消息通告：以太CPE上线，业务id：{}", name);
                    if(StringUtils.isNotBlank(name)){
                        EthQueueDTO ethQueueDTO = new EthQueueDTO(operationEnum,name,null,changeTime, emsDTO,"cpeOnline");
                        EthtQueueTask.addTask(ethQueueDTO);
                    }
                }

                @Override
                public void heartbeat(String msg) {
//                    log.info("心跳，{}", msg);
                    //刷新最新心跳时间
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    heartbeatSerivce.refreshHeartbeatTime(df.format(new Date()));
                }

                @Override
                public void other(Operation operationEnum, Notification notification, String changeTime) {
                    log.info("其他未解析，{}", JSON.toJSONString(notification));
                }

                @Override
                public void error(String msg, String error) {
                    log.error("错误，{}，{}", msg, error);
                }

                @Override
                public void clientSvcError(Operation operationEnum, String name, ClientSvcErrorInfo clientSvcErrorInfo, String changeTime) {
                    log.info("--消息通告：透传业务错误信息，{}", "name : "+ name + "," + JSON.toJSONString(clientSvcErrorInfo));
                    //nceMessageService.dealClientSvcError(operationEnum,name,clientSvcErrorInfo,emsDTO);
                }

                @Override
                public void ethtSvcError(Operation operationEnum, String name, EthtSvcErrorInfo ethtSvcErrorInfo, String changeTime) {
                    log.info("--消息通告：以太业务错误信息，{}", "name : "+ name + "," + JSON.toJSONString(ethtSvcErrorInfo));
                    //nceMessageService.dealEthtSvcError(operationEnum,name,ethtSvcErrorInfo,emsDTO);
                }
            }).execute();
        } catch (Exception e) {
            log.error("",e);
        }
    }
}
