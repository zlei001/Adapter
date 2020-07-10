package com.cttnet.zhwg.ywkt.client.impl;

import com.cttnet.common.util.StringUtil;
import com.cttnet.common.util.SysConfig;
import com.cttnet.zhwg.ywkt.client.AbstractBasicClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <pre>传输网元调用</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-17
 * @since jdk 1.8
 */
public class TransNeClientImpl extends AbstractBasicClient {


    /**
     * 查询网元数据
     * @param neIds neIds
     * @return TransNeDTOs
     */
    public List<Map<String, Object>> loadListByIds(Set<String> neIds) {

        Map<String, Object> params = new HashMap<>(1);
        params.put("neIds", StringUtil.appendBySplit(neIds, ","));
        String url = SysConfig.getProperty("ywkt.api.trans-conf.url.nes",
                getRequestPrefix() + "/wgjk/cswg/conf/1.0/trans-ne-api/query/nes");
        return this.list(url, params);
    }

    @Override
    protected String getApplicationName() {
        return SysConfig.getProperty("ywkt.api.trans-conf.application", "service-zhwg-jk-trans-conf");
    }
}
