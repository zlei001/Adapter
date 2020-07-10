package com.cttnet.zhwg.api.impl;

import com.cttnet.common.util.StringUtil;
import com.cttnet.common.util.SysConfig;
import com.cttnet.zhwg.api.AbstractService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.util.*;

/**
 * <pre>
 * 端口服务
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/3/31
 * @since java 1.8
 */
public class TransPortApi extends AbstractService {

    /**
     * 根据通道ID查询通道
     * @param portId portId
     * @return port
     */
    public Map<String, Object> loadPortById(String portId) {

        List<String> portIds = new ArrayList<>();
        portIds.add(portId);
        List<Map<String, Object>> list = loadPortListByIds(portIds);
        return CollectionUtils.isNotEmpty(list) ? list.get(0) : MapUtils.EMPTY_MAP;

    }

    /**
     * 根据端口IDs查询端口集合
     * @param portIds portIds
     * @return portList
     */
    public List<Map<String, Object>> loadPortListByIds(List<String> portIds) {

        String url = SysConfig.getProperty("ywkt.api.trans-conf.url.ports", "/wgjk/cswg/conf/1.0/trans-port-api/query/ports");
        Map<String, Object> params = new HashMap<>(1);
        params.put("portIds", StringUtil.appendBySplit(new HashSet<>(portIds), ","));
        return list(url, params);

    }

    /**
     * 获取应用名称
     *
     * @return applicationName
     */
    @Override
    protected String getApplication() {
        return SysConfig.getProperty("ywkt.api.trans-conf.application", "service-zhwg-jk-trans-conf");
    }
}
