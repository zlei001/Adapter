package com.cttnet.zhwg.ywkt.client.impl;

import com.cttnet.common.util.StringUtil;
import com.cttnet.common.util.SysConfig;
import com.cttnet.zhwg.ywkt.client.AbstractBasicClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <pre>网管</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-18
 * @since jdk 1.8
 */
public class TransEmsClientImpl extends AbstractBasicClient {

    /**
     * 查询网管数据
     * @param emsIds emsIds
     * @return ports
     */
    public List<Map<String, Object>> loadListByIds(Set<String> emsIds) {

        String url = SysConfig.getProperty("ywkt.api.trans-conf.url.ems",
                getRequestPrefix() + "/wgjk/cswg/conf/1.0/trans-ems-api/data/query");
        Map<String, Object> params = new HashMap<>(1);
        params.put("emsIds", StringUtil.appendBySplit(emsIds, ","));
        return this.list(url, params);
    }


    @Override
    protected String getApplicationName() {
        return SysConfig.getProperty("ywkt.api.trans-conf.application", "service-zhwg-jk-trans-conf");
    }
}
