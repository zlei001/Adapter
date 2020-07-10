package com.cttnet.zhwg.ywkt.actn.annunciate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>Description: 心跳服务service</p>
 *
 * @author suguisen
 */
@Service
@Slf4j
public class HeartbeatSerivce {

    private String heartbeatTime ;

    public void refreshHeartbeatTime(String time) {
//        log.info("最新心跳时间: " + time);
        this.heartbeatTime = time;
    }

    public String getHeartbeatTime() {
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String currentTime = df.format(new Date());
//        log.info("执行定时任务时间: " + currentTime);
//        return currentTime;
        return this.heartbeatTime;
    }

}
