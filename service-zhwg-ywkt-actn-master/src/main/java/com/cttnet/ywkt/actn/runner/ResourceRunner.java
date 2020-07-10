package com.cttnet.ywkt.actn.runner;

import com.cttnet.ywkt.actn.service.ems.ActnMacEmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * <p>Description: 资源数据初始化</p>
 * @author suguisen
 *
 */
@Component
@Order(1)
@Slf4j
public class ResourceRunner implements CommandLineRunner {

    @Autowired
    private ActnMacEmsService emsService;

    @Override
    public void run(String... args) throws Exception {

        // 初始化网管信息
        try {
            initEms();
        }catch (Exception e){
            log.warn("【初始化网管信息】失败：{}",e.getMessage());
        }
    }

    /**
     * 初始化网管信息
     */
    private void initEms() {
        log.info("开始初始化网管信息");
        int num = emsService.reload();
        log.info("初始化网管信息完成， num={}", num);
    }


}
