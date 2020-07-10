package com.cttnet.ywkt.actn.capacity.client.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cttnet.common.util.SysConfig;
import com.cttnet.ywkt.actn.capacity.bo.StatusResponse;
import com.cttnet.ywkt.actn.capacity.client.BasicEmsClient;
import com.cttnet.ywkt.actn.capacity.constants.ActnMethNames;
import com.cttnet.ywkt.actn.data.dto.ems.ActnMacEmsDTO;
import com.cttnet.ywkt.actn.domain.eth.request.RequestOfCreateEthtSvc;
import com.cttnet.ywkt.actn.domain.eth.response.ResponseOfQueryAllEthSvc;
import com.cttnet.ywkt.actn.domain.eth.response.ResponseOfQueryEthSvc;
import com.cttnet.ywkt.actn.domain.precompute.request.RequestOfInputPrecompute;
import com.cttnet.ywkt.actn.domain.precompute.response.ResponseOfOutputPrecompute;
import com.cttnet.zhwg.ywkt.http.domian.HttpRequestBody;
import com.cttnet.zhwg.ywkt.http.enums.HttpMethodEnum;

/**
 * <pre>以太Client</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-20
 * @since jdk 1.8
 */
public class EthSvcClientImpl extends BasicEmsClient {

    public EthSvcClientImpl(ActnMacEmsDTO actnMacEmsDTO) {
        super(actnMacEmsDTO);
    }

    /**
     * one
     *
     * @param uuid uuid
     * @return one
     */
    public ResponseOfQueryEthSvc one(String uuid) throws Exception {
        String url = SysConfig.getProperty("ywkt.actn.url.eth-svc.one", "/restconf/data/ietf-eth-tran-service:etht-svc/etht-svc-instances=");
        url += uuid;
        HttpRequestBody requestBody = getRequestBody(url, HttpMethodEnum.GET);
        return request(requestBody, ResponseOfQueryEthSvc.class, ActnMethNames.QUERY_ETH_SVC);
    }

    /**
     * all
     *
     * @return all
     */
    public ResponseOfQueryAllEthSvc all() throws Exception {
        String url = SysConfig.getProperty("ywkt.actn.url.eth-svc.all", "/restconf/data/ietf-eth-tran-service:etht-svc");
        HttpRequestBody requestBody = getRequestBody(url, HttpMethodEnum.GET);
        return request(requestBody, ResponseOfQueryAllEthSvc.class, ActnMethNames.QUERY_ETH_SVCS);
    }

    /**
     * 创建
     *
     * @return StatusResponse
     */
    public StatusResponse create(RequestOfCreateEthtSvc request) throws Exception {
        String url = SysConfig.getProperty("ywkt.actn.url.eth-svc.create", "/restconf/data/ietf-eth-tran-service:etht-svc");
        String body = JSON.toJSONString(request, SerializerFeature.NotWriteDefaultValue);
        return requestOfStatus(url, body, HttpMethodEnum.POST, ActnMethNames.CREATE_ETH_SVC);
    }

    /**
     * 更新
     *
     * @return StatusResponse
     */
    public StatusResponse update(RequestOfCreateEthtSvc request) throws Exception {
        String ethtsvcname = request.getEthtSvcInstances().get(0).getEthtsvcname();
        String url = SysConfig.getProperty("ywkt.actn.url.eth-svc.update", "/restconf/data/ietf-eth-tran-service:etht-svc/etht-svc-instances=");
        url += ethtsvcname;
        String body = JSON.toJSONString(request, SerializerFeature.NotWriteDefaultValue);
        return requestOfStatus(url, body, HttpMethodEnum.PATCH, ActnMethNames.UPDATE_ETH_SVC);
    }

    /**
     * 删除
     *
     * @return StatusResponse
     */
    public StatusResponse delete(String uuid) throws Exception {
        String url = SysConfig.getProperty("ywkt.actn.url.eth-svc.delete", "/restconf/data/ietf-eth-tran-service:etht-svc/etht-svc-instances=");
        url += uuid;
        return requestOfStatus(url, null, HttpMethodEnum.DELETE, ActnMethNames.DELETE_ETH_SVC);
    }

    /**
     * 透传业务预计算
     *
     * @param request
     * @return
     * @throws Exception
     */
    public ResponseOfOutputPrecompute precClientSvc(RequestOfInputPrecompute request) throws Exception {
        String url = SysConfig.getProperty("ywkt.actn.url.eth-svc.prec", "/restconf/operations/ietf-eth-tran-service:eth-svc-precompute");
        String json = JSON.toJSONString(request, SerializerFeature.NotWriteDefaultValue);
        HttpRequestBody requestBody = getRequestBody(url, HttpMethodEnum.POST);
        return request(requestBody, ResponseOfOutputPrecompute.class, ActnMethNames.PREC_ETH_SVC);
    }


}
