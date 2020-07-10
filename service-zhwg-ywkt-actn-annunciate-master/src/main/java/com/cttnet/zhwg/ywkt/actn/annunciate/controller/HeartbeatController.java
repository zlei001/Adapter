package com.cttnet.zhwg.ywkt.actn.annunciate.controller;

import com.cttnet.zhwg.ywkt.actn.annunciate.service.HeartbeatSerivce;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: 心跳能力</p>
 *
 * @author suguisen
 */
@Api(tags = "心跳能力")
@RestController
@RequestMapping("/heartbeat")
@Slf4j
public class HeartbeatController  {

    private static final String MODULE = "心跳能力";
    private static final String REQUESTER = "业务开通";
    private static final String RESPONDER = "心跳服务";

    @Autowired
    private HeartbeatSerivce heartbeatSerivce;


    @ApiOperation("刷新最新的心跳时间")
    @GetMapping("/refreshHeartbeatTime")
    public void refreshHeartbeatTime(@RequestParam("time") String time) {
        heartbeatSerivce.refreshHeartbeatTime(time);
    }


    @ApiOperation("获取最新的心跳时间")
    @GetMapping("/getHeartbeatTime")
    public String getHeartbeatTime() {

        String currentTime = heartbeatSerivce.getHeartbeatTime();
        return currentTime;
    }

}
