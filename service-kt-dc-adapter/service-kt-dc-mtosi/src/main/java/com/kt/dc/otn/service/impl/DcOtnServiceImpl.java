package com.kt.dc.otn.service.impl;

import com.kt.dc.otn.bean.RequestResult;
import com.kt.dc.otn.config.BeanMapConfig;
import com.kt.dc.otn.service.DcOtnService;
import com.kt.dc.otn.util.PropertiesConstantUtil;
import com.kt.dc.otn.util.PropertiesUtil;
import com.kt.dc.otn.util.SendHttpRequestUtil;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/** 
* @Description: ptn适配类
* @Param:  
* @return:  
* @Author: Coder
* @Date: 2020-07-10 10:18 
*/
@Service("dcOtnService")
public class DcOtnServiceImpl implements DcOtnService {

    @Autowired
    private BeanMapConfig beanMapConfig;

    public String getUrl(){
        String url = PropertiesConstantUtil.HTTPS + PropertiesUtil.getConfig(beanMapConfig.getManufactorMap().get("urlKey"));
        return url;
    }

    @Override
    public RequestResult tm_get_topo() {
        return SendHttpRequestUtil.doGet(getUrl());
    }

    @Override
    public RequestResult tm_get_point_topo(String param) {
        String url = getUrl();
        url+=param;
        return SendHttpRequestUtil.doGet(url);
    }

    @Override
    public RequestResult tm_get_topo_intne(String xmlParam) {
        String url = getUrl();
        RequestResult result = SendHttpRequestUtil.doPostXML(url, xmlParam);
        return result;
    }

    @Override
    public RequestResult tm_get_uuid_ne(String xmlParam){
        String url = getUrl();
        RequestResult result = SendHttpRequestUtil.doPostXML(url, xmlParam);
        return result;
    }

    @Override
    public RequestResult tm_get_intne() {
        return null;
    }

    @Override
    public RequestResult tm_get_topo_extne() {
        return null;
    }

    @Override
    public RequestResult tm_get_extne() {
        return null;
    }

    @Override
    public RequestResult tm_get_topo_intlink() {
        return null;
    }

    @Override
    public RequestResult tm_get_uuid_link(String xmlParam) {
        return null;
    }
}
