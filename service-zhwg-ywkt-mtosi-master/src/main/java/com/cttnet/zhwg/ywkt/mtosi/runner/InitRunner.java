package com.cttnet.zhwg.ywkt.mtosi.runner;

import com.cttnet.zhwg.ywkt.mtosi.ability.task.MtosiLogTask;
import com.cttnet.zhwg.ywkt.mtosi.async.AsyncManager;
import com.cttnet.zhwg.ywkt.mtosi.cache.MacEmsData;
import com.cttnet.zhwg.ywkt.mtosi.cache.MtosiMethodsData;
import com.cttnet.zhwg.ywkt.mtosi.cache.MtosiTemplateData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * <pre>
 *  初始化启动
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/1/11
 * @since java 1.8
 */
@Slf4j
@Order(value = 1000)
@Component
public class InitRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

        //1、初始化Mac网管信息
        log.info("初始化网管配置信息...");
        int num = MacEmsData.init();
        log.info("初始化网管配置信息成功[{}]", num);
        //2、初始化请求模板
        log.info("初始化南向请求模板...");
        num = MtosiTemplateData.init();
        log.info("初始化南向请求模板[{}]", num);
        //3、初始化南向日志任务
        log.info("启动南向日志入库任务...");
        AsyncManager.getInstance().execute(new MtosiLogTask());
        log.info("南向日志入库启动成功");
        log.info("初始化mtosi方法");
        MtosiMethodsData.init();
    }
}
