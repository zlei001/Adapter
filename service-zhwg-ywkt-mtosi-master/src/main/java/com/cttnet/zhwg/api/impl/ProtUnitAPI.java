package com.cttnet.zhwg.api.impl;

import com.cttnet.common.util.StringUtil;
import com.cttnet.common.util.SysConfig;
import com.cttnet.zhwg.api.AbstractService;
import com.cttnet.zhwg.ywkt.mtosi.ability.constants.Splits;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.util.*;

/**
 * <pre>
 * PortUnit服务
 * </pre>
 *
 * @author dengkaihong
 * @date 2020/4/14
 * @since java 1.8
 */
public class ProtUnitAPI extends AbstractService {


/*    public Map<String, Object> loadPortUnitById(String pgpId) {
        Map<String, Object> map = new HashMap<>();
        map.put("s_vendor_obj_name", "~MD=Huawei/U2000~ME=3146039~PG=/pg=4/type=2");
        return map;

    }*/

    public Map<String, Object> loadPortUnitByProId(String protectedPortId,String protectingPortId) {
        Map<String, Object> map = new HashMap<>();
        StringBuffer str = new StringBuffer();
        if (StringUtil.isNotNull(protectedPortId)){
            str.append("~MD=Huawei/U2000~ME=3146039~PTP=/rack=1/shelf=0/slot=4/domain=wdm/port=1");

        }
        if (StringUtil.isNotNull(protectingPortId)){
            str.append(Splits.SHARP);
            str.append("~MD=Huawei/U2000~ME=3146039~PTP=/rack=1/shelf=0/slot=4/domain=wdm/port=2");
        }

        map.put("protectionRelatedTpRefList",str);

        return map;

    }

    public Map<String, Object> loadProtUnitById(String neId ) {

        List<String> neIds = new ArrayList<>();
        neIds.add(neId);
        List<Map<String, Object>> list = loadProtUnitListByIds(neIds);
        return CollectionUtils.isNotEmpty(list) ? list.get(0) : MapUtils.EMPTY_MAP;

    }

    private List<Map<String, Object>> loadProtUnitListByIds(List<String> neIds ) {
        //第一个是读配置文件的配置，第二个是默认的请求链接，真实的地址
        String url = SysConfig.getProperty("ywkt.api.trans-conf.url.protection", "/wgjk/cswg/conf/1.0/prot-unit-api/query/board");
        Map<String, Object> params = new HashMap<>(1);
        params.put("neIds", StringUtil.appendBySplit(new HashSet<>(neIds), ","));
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
