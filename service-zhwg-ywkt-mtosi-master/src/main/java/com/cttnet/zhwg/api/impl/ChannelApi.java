package com.cttnet.zhwg.api.impl;

import com.cttnet.common.util.StringUtil;
import com.cttnet.common.util.SysConfig;
import com.cttnet.zhwg.api.AbstractService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.util.*;

/**
 * <pre>
 *  通道服务
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/3/31
 * @since java 1.8
 */
public class ChannelApi extends AbstractService {


    /**
     * 根据通道ID查询通道
     * @param channelId channelId
     * @return channel
     */
    public Map<String, Object> loadChannelById(String channelId) {

        List<String> channelIds = new ArrayList<>();
        channelIds.add(channelId);
        List<Map<String, Object>> list = loadChannelListByIds(channelIds);
        return CollectionUtils.isNotEmpty(list) ? list.get(0) : MapUtils.EMPTY_MAP;

    }

    /**
     * 根据通道IDs查询通道集合
     * @param channelIds channelIds
     * @return channelList
     */
    public List<Map<String, Object>> loadChannelListByIds(List<String> channelIds) {

        String url = SysConfig.getProperty("ywkt.api.trans-conf.url.channel", "/wgjk/cswg/conf/1.0/channel-api/query/channel");
        Map<String, Object> params = new HashMap<>(1);
        params.put("channelIds", StringUtil.appendBySplit(new HashSet<>(channelIds), ","));
        return list(url, params);

    }

    /**
     * 获取应用名称
     *
     * @return
     */
    @Override
    protected String getApplication() {
        return SysConfig.getProperty("ywkt.api.trans-conf.application", "service-zhwg-jk-trans-conf");
    }
}
