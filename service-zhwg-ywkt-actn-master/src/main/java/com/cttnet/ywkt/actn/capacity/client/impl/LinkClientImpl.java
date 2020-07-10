package com.cttnet.ywkt.actn.capacity.client.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cttnet.common.util.SysConfig;
import com.cttnet.ywkt.actn.capacity.client.BasicEmsClient;
import com.cttnet.ywkt.actn.data.dto.ems.ActnMacEmsDTO;
import com.cttnet.ywkt.actn.domain.network.response.ResponeOfQueryAllLinks;
import com.cttnet.ywkt.actn.domain.network.response.ResponeOfQueryLink;
import com.cttnet.zhwg.ywkt.http.domian.HttpRequestBody;
import com.cttnet.zhwg.ywkt.http.enums.HttpMethodEnum;

public class LinkClientImpl extends BasicEmsClient {

	public LinkClientImpl(ActnMacEmsDTO actnMacEmsDTO) {
		super(actnMacEmsDTO);
	}
	/**
	 * 查询指定拓扑下全量链路
	 * @return
	 * @throws Exception
	 */
	public ResponeOfQueryAllLinks all(String network) throws Exception {
		StringBuilder parm = new StringBuilder();
		if (StringUtils.isNotEmpty(network)) {
			parm.append(network).append("?fields=ietf-network-topology:link");
		} else {
			return null;
		}
		String url = SysConfig.getProperty("ywkt.actn.url.networks.all", "/restconf/data/ietf-network:networks");
		url += parm.toString();
		HttpRequestBody requestBody = getRequestBody(url, HttpMethodEnum.GET);
		return request(requestBody, ResponeOfQueryAllLinks.class, "查询指定拓扑下全量链路");
	}

	/**
	 * 查询指定拓扑指定链路
	 * @return
	 * @throws Exception
	 */
	public ResponeOfQueryLink one(String network, String linkid) throws Exception {
		StringBuilder parm = new StringBuilder();
		if(StringUtils.isNotEmpty(network) && StringUtils.isNotEmpty(linkid)){
			parm.append(network);
			parm.append("/ietf-network-topology:link=").append(linkid);
		} else {
			return null;
		}
		String url = SysConfig.getProperty("ywkt.actn.url.networks.one", "/restconf/data/ietf-network:networks/network=");
		url += parm.toString();
		HttpRequestBody requestBody = getRequestBody(url, HttpMethodEnum.GET);
		return request(requestBody, ResponeOfQueryLink.class, "查询指定拓扑指定链路");
	}

}
