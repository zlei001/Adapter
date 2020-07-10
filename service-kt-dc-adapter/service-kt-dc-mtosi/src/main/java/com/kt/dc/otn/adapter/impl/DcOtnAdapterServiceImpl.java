package com.kt.dc.otn.adapter.impl;

import com.alibaba.fastjson.JSON;
import com.kt.dc.otn.bean.RequestResult;
import com.kt.dc.otn.config.BeanMapConfig;
import com.kt.dc.otn.adapter.DcOtnAdapterService;
import com.kt.dc.otn.handle.ApplicationContextGetBeanHelper;
import org.apache.commons.lang.StringUtils;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DcOtnAdapterServiceImpl implements DcOtnAdapterService {
    @Autowired
    private BeanMapConfig beanMapConfig;

    /**
     * param:flag 厂家标识， methodName 业务函数， params 业务参数
     *return: String
     */
    @Override
    public RequestResult oTnAdapter(String flag, String methodName, String params){
        try {
            beanMapConfig.getManufactorMap().put("urlKey",methodName);
            return invokeMethod(flag, methodName, params);
        }catch (Exception e){
            //统一的异常返回
            RequestResult requestResult = new RequestResult();
            requestResult.setMessage(e.getMessage());
            requestResult.setResult(e.getStackTrace().toString());
            return requestResult;
        }
    }

    /**
    * @Description:  根据反射原理调用适配类的函数
    * @Param:  flag 厂家标识， methodName 业务函数， params 业务参数
    * @return:  RequestResult
    * @Author: Coder
    * @Date: 2020-07-10 09:34
    */
    public RequestResult invokeMethod(String flag, String methodName, String params) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //根据参数获取具体适配类
        Map<String, String> manufactorMap = beanMapConfig.getManufactorMap();
        //System.out.println(ApplicationContextGetBeanHelper.getBean(manufactorMap.get(flag)).getClass());
        Method method = null;
        Object obj = null;
        //根据参数获取方法
        if(StringUtils.isNotBlank(params)){
             method = ApplicationContextGetBeanHelper.getBean(manufactorMap.get(flag)).getClass().getMethod(methodName,String.class);
             obj = method.invoke(ApplicationContextGetBeanHelper.getBean(manufactorMap.get(flag)), params);
        }else {
             method = ApplicationContextGetBeanHelper.getBean(manufactorMap.get(flag)).getClass().getMethod(methodName, null);
             obj = method.invoke(ApplicationContextGetBeanHelper.getBean(manufactorMap.get(flag)));
        }
        //执行方法获取返回值
        RequestResult requestResult = (RequestResult)obj;
        return requestResult;
    }
}
