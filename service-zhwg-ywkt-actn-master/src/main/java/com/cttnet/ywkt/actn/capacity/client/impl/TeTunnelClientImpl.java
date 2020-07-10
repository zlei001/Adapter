package com.cttnet.ywkt.actn.capacity.client.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cttnet.common.util.SysConfig;
import com.cttnet.ywkt.actn.capacity.bo.StatusResponse;
import com.cttnet.ywkt.actn.capacity.client.BasicEmsClient;
import com.cttnet.ywkt.actn.data.dto.ems.ActnMacEmsDTO;
import com.cttnet.ywkt.actn.domain.tunnel.request.RequestOfCreateTeTunnel;
import com.cttnet.ywkt.actn.domain.tunnel.request.RequestOfCreateTeTunnelEthEos;
import com.cttnet.ywkt.actn.domain.tunnel.response.ResponeOfQueryAllTunnels;
import com.cttnet.ywkt.actn.domain.tunnel.response.ResponeOfQueryTunnel;
import com.cttnet.zhwg.ywkt.http.enums.HttpMethodEnum;

/**
 * <pre>TE隧道Client</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-20
 * @since jdk 1.8
 */
public class TeTunnelClientImpl extends BasicEmsClient {

    public TeTunnelClientImpl(ActnMacEmsDTO actnMacEmsDTO) {
        super(actnMacEmsDTO);
    }

    /**
     * 创建TE隧道，包括隧道预制
     * @param request
     * @return
     * @throws Exception
     */
    public StatusResponse createTeTunnel(RequestOfCreateTeTunnel request) {
        String url = SysConfig.getProperty("ywkt.actn.url.te-tunnel.create", "/restconf/data/ietf-te:te/tunnels");
        String json = JSON.toJSONString(request, SerializerFeature.NotWriteDefaultValue);
        return requestOfStatus(url, json, HttpMethodEnum.POST, "创建隧道业务");
    }

    /**
     * 透传速率的变更，需要从隧道变更
     * @param request
     * @return
     * @throws Exception
     */
    public StatusResponse updateTeTunnel(RequestOfCreateTeTunnel request) {
        String url = SysConfig.getProperty("ywkt.actn.url.te-tunnel.update", "/restconf/data/ietf-te:te/tunnels/tunnel=");
        url += request.getTunnels().get(0).getName();
        String json = JSON.toJSONString(request, SerializerFeature.NotWriteDefaultValue);
        return requestOfStatus(url, json, HttpMethodEnum.PATCH, "变更隧道业务");
    }

    /**
     * 以太业务Eos创建TE隧道，包括隧道预制
     * @param request
     * @return
     * @throws Exception
     */
    public StatusResponse createTeTunnelEthEos(RequestOfCreateTeTunnelEthEos request) {
        String url = SysConfig.getProperty("ywkt.actn.url.te-tunnel.create", "/restconf/data/ietf-te:te");
        String json = JSON.toJSONString(request, SerializerFeature.NotWriteDefaultValue);
        return requestOfStatus(url, json, HttpMethodEnum.PATCH, "创建隧道业务");
    }

    /**
     * 查询全量TE隧道
     * @return
     * @throws Exception
     */
    public ResponeOfQueryAllTunnels queryAllTunnel() throws Exception {
        String url = SysConfig.getProperty("ywkt.actn.url.te-tunnel.query-all", "/restconf/data/ietf-te:te/tunnels/");
        return request(url, "", HttpMethodEnum.GET, ResponeOfQueryAllTunnels.class, "查询全量TE隧道");
    }

    /**
     * 查询指定TE隧道
     * @return
     * @throws Exception
     */
    public ResponeOfQueryTunnel queryTunnel(String name) throws Exception {
        String url = SysConfig.getProperty("ywkt.actn.url.te-tunnel.query", "/restconf/data/ietf-te:te/tunnels/tunnel=");
        url += name;
        return request(url, "", HttpMethodEnum.GET, ResponeOfQueryTunnel.class, "查询指定TE隧道");
    }



}
