package com.cttnet.zhwg.ywkt.actn.annunciate.config;

import com.cttnet.common.util.SpringContextUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * 订阅&通告
 * </pre>
 * <b>项目：</b> & <b>技术支持：凯通科技股份有限公司 (c) 2019 </b>
 *
 * @author 苏桂森
 * @version 1.0
 * @since jdk 1.8
 */
@Data
@Component
public class MessageConfig {

    @Value("${hw.actn.nce-t.url.message.subscribe:/restconf/operations/ietf-subscribed-notifications:establish-subscription}")
    private String subscribeUrl;
    @Value("${hw.actn.nce-t.url.message.streams:/restconf/streams/yang-push-json}")
    private String streamsUrl;
    @Value("${hw.actn.nce-t.url.message.heartbeat:: This is a heartbeat message.}")
    private String heartbeat ;
    @Value("${hw.actn.nce-t.url.message.dataPrefix:data:}")
    private String dataPrefix ;

    /**
     * spring容器中取单例
     * @return
     */
    public static MessageConfig getInstance() {

        return SpringContextUtils.getBean(MessageConfig.class);
    }
}
