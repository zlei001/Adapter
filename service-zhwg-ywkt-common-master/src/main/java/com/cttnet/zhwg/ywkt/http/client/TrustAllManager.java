package com.cttnet.zhwg.ywkt.http.client;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * <pre>
 *	 信任所有证书
 * </pre>
 *
 * <b>项目：service-zhwg-micro-ywkt-common</b> & <b>技术支持：凯通科技股份有限公司 (c) 2019 </b>
 *
 * @author zhangyaomin
 * @since JDK 1.8
 * @version 1.0
 */
public class TrustAllManager implements X509TrustManager {

	@Override
	public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

	}

	@Override
	public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}

}
