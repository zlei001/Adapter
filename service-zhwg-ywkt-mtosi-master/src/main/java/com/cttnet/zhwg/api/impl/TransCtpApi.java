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
 *  CTP服务
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/3/31
 * @since java 1.8
 */
public class TransCtpApi extends AbstractService {

    /**
     * 查询CTP
     * @param  ctpId
     * @return list
     */
    public List<Map<String, Object>> loadCtpListById(List<String> ctpId) {

//        //测试数据 service-zhwg-jk-trans-conf
//        List<Map<String, Object>> list = new ArrayList<>();
//        for (int i = 0, len = ctpIds.size(); i < len; i++) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("s_port_id", ctpIds.get(i));
//            map.put("vendorObjName", "~MD=1~ME=6b41aa02-e1df-42e9-aa7d-4cf871f80533~PTP=/rack=1/shelf=1/slot=32/port=1~CTP=/odu2=" + (i+1));
//            list.add(map);
//        }
//        return list;
    	//查询对应网管配置侧接口
//        if(ctpIds != null && ctpIds.size() > 0) {
//        	List<Map<String, Object>> list = null;
//        	Map<String,Object> params = Collections.emptyMap();
//        	params.put("portIds",String.join(",", ctpIds));
//        	list = list("/wgjk/cswg/conf/1.0/trans-ctp-api/query/ports", params, true);
//        	list = Optional.ofNullable(list).orElse(Collections.emptyList());
//        	list = list.stream().map(m->{
//        		Map<String,Object> mp = Maps.newHashMap();
//        		mp.put("s_port_id",m.get("portId"));
//        		mp.put("vendorObjName",m.get("vendorObjName"));
//        		return mp;
//        	}).collect(Collectors.toList());
//        	return list;
//        }
//
//        return Lists.newArrayList();


        String url = SysConfig.getProperty("ywkt.api.trans-conf.url.ctpList", "/wgjk/cswg/conf/1.0/trans-ctp-api/query/ports");
        Map<String, Object> params = new HashMap<>(1);
//        StringBuffer ctpIds =  new StringBuffer();
//        for (int i = 0, len = ctpId.size(); i < len; i++){
//        	ctpIds.append(ctpId.get(i));
//        	if ( i > 0 ){
//        		ctpIds.append(",");
//			}
//		}
        params.put("portIds", StringUtil.appendBySplit(new HashSet<>(ctpId), ","));
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
