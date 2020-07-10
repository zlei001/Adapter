package com.kt.dc.otn.bean;

import com.kt.dc.otn.enums.HttpMethodEnum;
import com.kt.dc.otn.enums.ProtocolEnum;
import lombok.Data;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * <pre>
 *	请求实体
 * </pre>
 * @author zhangyaomin
 * @since JDK 1.8
 * @version 1.0
 */
@Data
public class HttpRequestBody {

	/** 请求协议 http & https */
	private ProtocolEnum protocol;
	/** 请求连接 */
	private String url;
	/** 请求方式 */
	private HttpMethodEnum method;
	/** 头部信息 */
	private Map<String, String> headerExtensions;
	/** 请求参数字符编码 */
	private Charset requestCharset;
	/** 响应参数字符编码 */
	private Charset responseCharset;
	/** 连接超时，默认配置60s */
	private int connectTimeout;
	/** 请求响应超时，默认配置200s */
	private int socketTimeout;
	/** 证书地址  */
	private String keyStorePath;
	/** 证书密码 */
	private String keyStorePassword;
	/** 信任所有证书(绕过证书认证) ，默认true*/
	private boolean trustAll;
	/** 网络层加密： TLS传输层  SSL安全层 */
	private String protocolLayer;

}
