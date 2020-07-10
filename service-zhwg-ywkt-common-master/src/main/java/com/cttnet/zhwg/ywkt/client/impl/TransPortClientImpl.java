package com.cttnet.zhwg.ywkt.client.impl;

import com.cttnet.common.util.StringUtil;
import com.cttnet.common.util.SysConfig;
import com.cttnet.zhwg.ywkt.client.AbstractBasicClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <pre>传输端口客户端</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-18
 * @since jdk 1.8
 */
public class TransPortClientImpl extends AbstractBasicClient {

    /**
     * 查询端口数据
     * @param portIds portIds
     * @return ports
     */
    public List<Map<String, Object>> loadListByIds(Set<String> portIds) {

        String url = SysConfig.getProperty("ywkt.api.trans-conf.url.ports",
                getRequestPrefix() + "/wgjk/cswg/conf/1.0/trans-port-api/query/ports");
        Map<String, Object> params = new HashMap<>(1);
        params.put("portIds", StringUtil.appendBySplit(portIds, ","));
        return this.list(url, params);
    }

    @Override
    protected String getApplicationName() {
        return SysConfig.getProperty("ywkt.api.trans-conf.application", "service-zhwg-jk-trans-conf");
    }
}
