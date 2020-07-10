package com.kt.dc.otn.adapter;


import com.kt.dc.otn.bean.RequestResult;

public interface DcOtnAdapterService {
    /**
     * param:flag 厂家标识， methodName 业务函数， json 业务参数
     *return: String
     */
    public RequestResult oTnAdapter(String flag, String methodName, String params);
}
