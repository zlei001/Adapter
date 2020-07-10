package com.cttnet.zhwg.ywkt.client.impl;

import com.cttnet.common.util.StringUtil;
import com.cttnet.common.util.SysConfig;
import com.cttnet.zhwg.ywkt.client.AbstractBasicClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <pre>通道Client</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-18
 * @since jdk 1.8
 */
public class ChannelClientImpl extends AbstractBasicClient {

    /**
     * 查询通道数据
     * @param channelIds channelIds
     * @return channels
     */
    public List<Map<String, Object>> loadListByIds(Set<String> channelIds) {

        String url = SysConfig.getProperty("ywkt.api.trans-conf.url.ports",
                getRequestPrefix() + "/wgjk/cswg/conf/1.0/channel-api/query/list");
        Map<String, Object> params = new HashMap<>(1);
        params.put("channelIds", StringUtil.appendBySplit(channelIds, ","));
        return this.list(url, params);
    }

    @Override
    protected String getApplicationName() {
        return SysConfig.getProperty("ywkt.api.trans-conf.application", "service-zhwg-jk-trans-conf");
    }
}
