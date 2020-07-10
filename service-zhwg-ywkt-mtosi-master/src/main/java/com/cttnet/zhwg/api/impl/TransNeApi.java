package com.cttnet.zhwg.api.impl;

import com.cttnet.common.util.StringUtil;
import com.cttnet.common.util.SysConfig;
import com.cttnet.zhwg.api.AbstractService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  网元服务
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/3/31
 * @since java 1.8
 */
public class TransNeApi extends AbstractService {


    /**
     * 通过网元IDs查询网元对象集合
     * @param neIds neIds
     * @return neList
     */
    public List<Map<String, Object>> loadNeListByIds(List<String> neIds) {

        Map<String, Object> params = new HashMap<>(1);
        params.put("neIds", StringUtil.appendBySplit(new HashSet<>(neIds), ","));
        String url = SysConfig.getProperty("ywkt.api.trans-conf.url.nes", "/wgjk/cswg/conf/1.0/trans-ne-api/query/nes");
        return  this.list(url, params);
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
