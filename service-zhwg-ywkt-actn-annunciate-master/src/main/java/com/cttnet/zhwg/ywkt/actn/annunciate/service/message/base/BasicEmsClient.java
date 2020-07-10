package com.cttnet.zhwg.ywkt.actn.annunciate.service.message.base;

import com.cttnet.zhwg.ywkt.actn.annunciate.data.dto.ems.EmsDTO;
import com.cttnet.zhwg.ywkt.http.HttpUtils;
import com.cttnet.zhwg.ywkt.http.domian.HttpRequestBody;
import com.cttnet.zhwg.ywkt.http.enums.HttpMethodEnum;
import com.cttnet.zhwg.ywkt.http.enums.ProtocolEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *
 * </pre>
 * <b>项目：service-zhwg-actn-nce</b> & <b>技术支持：凯通科技股份有限公司 (c) 2019 </b>
 * @author zhangyaomin
 * @since JDK 1.8
 * @version 1.0
 */
@Slf4j
public class BasicEmsClient {

	private static final ProtocolEnum DEFAULT_PROTOCOL = ProtocolEnum.HTTPS;
	private static final String DEFAULT_PROTOCOL_LAYER = "TLS";
	private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
	private static final String DEFAULT_CONTENT_TYPE = "application/json";

	protected EmsDTO emsDto;

	public BasicEmsClient(EmsDTO emsDto) {
		if (emsDto == null) {
			throw new RuntimeException("emsDTO is null");
		}
		if (StringUtils.isEmpty(emsDto.getBaseUrl())) {
			throw new RuntimeException("baseUrl is null");
		}
		this.emsDto = emsDto;
	}

	/**
	 * @param url
	 * @param body 请求参数，没有不填
	 * @param method
	 * @param cls 对象
	 * @param methodName 日志入库标记使用
	 * @return
	 * @throws Exception
	 */
	public <T> T request(String url, String body, HttpMethodEnum method, Class<T> cls, String methodName) throws Exception{

		return request(getRequestBody(url, method), body, cls, methodName);
	}

	/**
	 *
	 * @param requestBody 开放设置
	 * @param body 请求参数，没有不填
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public <T> T request(HttpRequestBody requestBody, String body, Class<T> cls, String methodName) throws Exception{

		try {
			if (StringUtils.isNotBlank(body)) {
				return HttpUtils.request(requestBody, body, cls);
			} else {
				return HttpUtils.request(requestBody, cls);
			}
		} catch (Exception e) {
			log.error("请求失败", e);
			throw e;
		}
	}

	/**
	 * 请求参数
	 * @param url
	 * @param method
	 * @return
	 */
	public HttpRequestBody getRequestBody(String url, HttpMethodEnum method) {

		HttpRequestBody requestBody = new HttpRequestBody();
		url = emsDto.getBaseUrl() + url;
		requestBody.setUrl(url);
		requestBody.setMethod(method);
		requestBody.setTrustAll(true);
		requestBody.setProtocol(emsDto.getProtocolEnum() != null ? emsDto.getProtocolEnum() : DEFAULT_PROTOCOL);
		requestBody.setProtocolLayer(emsDto.getProtocolLayer() != null ? emsDto.getProtocolLayer() : DEFAULT_PROTOCOL_LAYER);
		requestBody.setRequestCharset(emsDto.getCharset() != null ? emsDto.getCharset() : DEFAULT_CHARSET);
		requestBody.setResponseCharset(emsDto.getCharset() != null ? emsDto.getCharset() : DEFAULT_CHARSET);
		Map<String, String> header = new HashMap<>(2);
		String authorization = "Basic " + new sun.misc.BASE64Encoder().encode((emsDto.getUsername() + ":" + emsDto.getPassword()).getBytes());
		header.put("Authorization", authorization);
		header.put("Content-Type", emsDto.getContentType() != null ? emsDto.getContentType() : DEFAULT_CONTENT_TYPE);
		requestBody.setHeaderExtensions(header);

		return requestBody;
	}
}
