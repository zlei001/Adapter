package com.cttnet.zhwg.ywkt.actn.annunciate.service.message.subscribe;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cttnet.ywkt.actn.bean.message.subscribe.Subscribe;
import com.cttnet.ywkt.actn.bean.message.subscribe.SubscribeOutput;
import com.cttnet.zhwg.ywkt.actn.annunciate.data.dto.ems.EmsDTO;
import com.cttnet.zhwg.ywkt.actn.annunciate.service.message.base.BasicEmsClient;
import com.cttnet.zhwg.ywkt.http.enums.HttpMethodEnum;
import com.cttnet.zhwg.ywkt.actn.annunciate.config.MessageConfig;

/**
 * <pre>
 *
 * </pre>
 * <b>项目：</b> & <b>技术支持：凯通科技股份有限公司 (c) 2019 </b>
 * @author zhangyaomin
 * @since jdk 1.8
 * @version 1.0
 */
public class SubscribeService extends BasicEmsClient {

    public SubscribeService(EmsDTO emsDTO) {
		super(emsDTO);
	}

	public SubscribeOutput subscibed(Subscribe subscribe) throws Exception {

        String url = MessageConfig.getInstance().getSubscribeUrl();
        String body = JSON.toJSONString(subscribe, SerializerFeature.NotWriteDefaultValue);
        return request(url, body, HttpMethodEnum.POST, SubscribeOutput.class, "订阅");
    }
}
