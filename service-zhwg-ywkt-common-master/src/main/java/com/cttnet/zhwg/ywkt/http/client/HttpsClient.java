package com.cttnet.zhwg.ywkt.http.client;

import lombok.Builder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.springframework.util.Assert;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * <pre>
 * HttpsClientBuilder
 * </pre>
 *
 * @author zhangyaomin
 * @version 1.0
 * @since JDK 1.8
 */
@Builder
public class HttpsClient {

    /**
     * 证书地址
     */
    private String keyStorePath;
    /**
     * 证书密码
     */
    private String keyStorePassword;
    /**
     * 信任所有证书(绕过证书认证)
     */
    private boolean trustAll;
    /**  */
    private String protocolLayer;



	/**
	 * 构建HttpClient
	 * @return CloseableHttpClient
	 * @throws NoSuchAlgorithmException NoSuchAlgorithmException
	 * @throws KeyManagementException KeyManagementException
	 * @throws KeyStoreException KeyStoreException
	 * @throws IOException IOException
	 * @throws CertificateException CertificateException
	 */
    public CloseableHttpClient toCloseableHttpClient() throws
            NoSuchAlgorithmException,
            KeyManagementException,
            KeyStoreException,
            IOException,
            CertificateException {

        ConnectionSocketFactory connectionSocketFactory = prepareCertificate();
        return register(connectionSocketFactory);
    }

    /**
     * 证书认证
     */
    private ConnectionSocketFactory prepareCertificate() throws
            NoSuchAlgorithmException,
            KeyManagementException,
            KeyStoreException,
            IOException,
            CertificateException {

        ConnectionSocketFactory connectionSocketFactory = null;
        if (this.trustAll) {
            Assert.notNull(protocolLayer, "protocolLayer为空");
            SSLContext ctx = SSLContext.getInstance(this.protocolLayer);
            X509TrustManager tm = new TrustAllManager();
            // 设置成已信任的证书
            ctx.init(null, new TrustManager[]{tm}, null);
            connectionSocketFactory = new SSLConnectionSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);
        } else {
            Assert.notNull(keyStorePath, "证书地址为空");
            Assert.notNull(keyStorePassword, "证书密码为空");
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            FileInputStream fileInputStream = new FileInputStream(this.keyStorePath);
            try {
                trustStore.load(fileInputStream, this.keyStorePassword.toCharArray());
            } finally {
                fileInputStream.close();
            }
            SSLContext sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(trustStore, TrustSelfSignedStrategy.INSTANCE)
                    .build();
            connectionSocketFactory = new SSLConnectionSocketFactory(sslcontext);
        }
        return connectionSocketFactory;
    }

    /**
     * 注册
     * @param connectionSocketFactory connectionSocketFactory
     * @return CloseableHttpClient
     */
    private CloseableHttpClient register(ConnectionSocketFactory connectionSocketFactory) {
        // 设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", connectionSocketFactory)
                .build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        HttpClients.custom().setConnectionManager(connManager);
        // 创建自定义的httpclient对象
        return  HttpClients.custom().setConnectionManager(connManager).build();
    }


}
