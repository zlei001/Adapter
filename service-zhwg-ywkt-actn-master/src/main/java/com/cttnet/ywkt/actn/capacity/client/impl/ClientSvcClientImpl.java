package com.cttnet.ywkt.actn.capacity.client.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cttnet.common.util.SysConfig;
import com.cttnet.ywkt.actn.capacity.bo.StatusResponse;
import com.cttnet.ywkt.actn.capacity.client.BasicEmsClient;
import com.cttnet.ywkt.actn.capacity.constants.ActnMethNames;
import com.cttnet.ywkt.actn.data.dto.ems.ActnMacEmsDTO;
import com.cttnet.ywkt.actn.domain.client.request.RequestOfCreateClientSvc;
import com.cttnet.ywkt.actn.domain.client.request.RequestOfUpdateClientSvc;
import com.cttnet.ywkt.actn.domain.client.response.ResponseOfQueryAllClientSvc;
import com.cttnet.ywkt.actn.domain.client.response.ResponseOfQueryClientSvc;
import com.cttnet.ywkt.actn.domain.precompute.request.RequestOfInputPrecompute;
import com.cttnet.ywkt.actn.domain.precompute.response.ResponseOfOutputPrecompute;
import com.cttnet.zhwg.ywkt.http.domian.HttpRequestBody;
import com.cttnet.zhwg.ywkt.http.enums.HttpMethodEnum;

/**
 * <pre>OTN透传业务</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-20
 * @since jdk 1.8
 */
public class ClientSvcClientImpl extends BasicEmsClient {

    public ClientSvcClientImpl(ActnMacEmsDTO actnMacEmsDTO) {
        super(actnMacEmsDTO);
    }

    /**
     * one
     *
     * @param uuid uuid
     * @return one
     */
    public ResponseOfQueryClientSvc one(String uuid) throws Exception {
        String url = SysConfig.getProperty("ywkt.actn.url.client-svc.one", "/restconf/data/ietf-trans-client-service:client-svc/client-svc-instances=");
        url += uuid;
        HttpRequestBody requestBody = getRequestBody(url, HttpMethodEnum.GET);
        return request(requestBody, ResponseOfQueryClientSvc.class, ActnMethNames.QUERY_CLIENT_SVC);
    }

    /**
     * all
     *
     * @return all
     */
    public ResponseOfQueryAllClientSvc all() throws Exception {
        String url = SysConfig.getProperty("ywkt.actn.url.client-svc.all", "/restconf/data/ietf-trans-client-service:client-svc");
        HttpRequestBody requestBody = getRequestBody(url, HttpMethodEnum.GET);
        return request(requestBody, ResponseOfQueryAllClientSvc.class, ActnMethNames.QUERY_CLIENT_SVCS);
    }

    /**
     * 创建
     *
     * @return StatusResponse
     */
    public StatusResponse create(RequestOfCreateClientSvc request) throws Exception {
        String url = SysConfig.getProperty("ywkt.actn.url.client-svc.create", "/restconf/data/ietf-trans-client-service:client-svc");
        String body = JSON.toJSONString(request, SerializerFeature.NotWriteDefaultValue);
        return requestOfStatus(url, body, HttpMethodEnum.POST, ActnMethNames.CREATE_CLIENT_SVC);
    }

    /**
     * 更新
     *
     * @return StatusResponse
     */
    public StatusResponse update(RequestOfUpdateClientSvc request) throws Exception {
        String clientSvcName = request.getClientSvcInstances().get(0).getClientSvcName();
        String url = SysConfig.getProperty("ywkt.actn.url.client-svc.update", "/restconf/data/ietf-trans-client-service:client-svc/client-svc-instances=");
        url += clientSvcName;
        String body = JSON.toJSONString(request, SerializerFeature.NotWriteDefaultValue);
        return requestOfStatus(url, body, HttpMethodEnum.PATCH, ActnMethNames.UPDATE_CLIENT_SVC);
    }

    /**
     * 删除
     *
     * @return StatusResponse
     */
    public StatusResponse delete(String uuid) throws Exception {
        String url = SysConfig.getProperty("ywkt.actn.url.client-svc.delete", "/restconf/data/ietf-trans-client-service:client-svc/client-svc-instances=");
        url += uuid;
        return requestOfStatus(url, null, HttpMethodEnum.DELETE, ActnMethNames.DELETE_CLIENT_SVC);
    }

    /**
     * 透传业务预计算
     *
     * @param request
     * @return
     * @throws Exception
     */
    public ResponseOfOutputPrecompute precClientSvc(RequestOfInputPrecompute request) throws Exception {

        String url = SysConfig.getProperty("ywkt.actn.url.client-svc.prec", "/restconf/operations/ietf-trans-client-service:client-service-precompute");
        String json = JSON.toJSONString(request, SerializerFeature.NotWriteDefaultValue);
        HttpRequestBody requestBody = getRequestBody(url, HttpMethodEnum.POST);
        return request(requestBody, ResponseOfOutputPrecompute.class, ActnMethNames.PREC_CLIENT_SVC);
    }
}
