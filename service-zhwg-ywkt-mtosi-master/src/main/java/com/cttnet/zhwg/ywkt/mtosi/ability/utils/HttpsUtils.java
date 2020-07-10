package com.cttnet.zhwg.ywkt.mtosi.ability.utils;

import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;

/**
 * <pre>
 *  https请求
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/1/11
 * @since java 1.8
 */
@Slf4j
public class HttpsUtils {


    public static String get(String httpsUrl, String keyStorePath, String keyStorePassword, String requestXml, int connectTimeout, int readTimeout){
        log.info("请求URL:{}", httpsUrl);
        log.info("使用证书:{}", keyStorePath);
        log.info("证书秘钥:{}", keyStorePassword);
        String response = "";
        HttpsURLConnection httpsConn = null;
        try {
            SSLContext ssl = SSLContext.getInstance("TLS");
            ssl.init(getKeyManagers(keyStorePath, keyStorePassword), getTrustManagers(keyStorePath), new SecureRandom());
            httpsConn = (HttpsURLConnection) new URL(httpsUrl).openConnection();
            httpsConn.setSSLSocketFactory(ssl.getSocketFactory());
            httpsConn.setHostnameVerifier((hostname, session) -> true);
            httpsConn.setConnectTimeout(connectTimeout);
            httpsConn.setReadTimeout(readTimeout);
            httpsConn.setDoInput(true);
            httpsConn.setDoOutput(true);
            httpsConn.setRequestMethod("GET");
            httpsConn.setRequestProperty("Content-Length",
                    String.valueOf(requestXml.getBytes().length));
            httpsConn.setUseCaches(false);
            httpsConn.getOutputStream().write(requestXml.getBytes("UTF-8"));
            httpsConn.getOutputStream().flush();
            httpsConn.getOutputStream().close();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    httpsConn.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line = "";
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            response = result.toString();
        } catch (Exception e) {
            log.error("", e);
        } finally {
            if(httpsConn != null){
                httpsConn.disconnect();
            }
        }
        return response;
    }

    public static TrustManager[] getTrustManagers(String trustStore)
			throws
            java.io.IOException,
            java.security.GeneralSecurityException {
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream(trustStore), null);
        TrustManagerFactory tmf = TrustManagerFactory
                .getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);

        return tmf.getTrustManagers();
    }

    public static KeyManager[] getKeyManagers(String keyStore,
                                              String keyStorePassword)
			throws
            java.security.GeneralSecurityException,
            java.io.IOException {
        String keyStoreKey = keyStore.endsWith(".jks") ? "JKS" : (keyStore.endsWith(".p12") ? "PKCS12" : "");
        KeyStore ks = KeyStore.getInstance(keyStoreKey);
        ks.load(new FileInputStream(keyStore), keyStorePassword.toCharArray());
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory
                .getDefaultAlgorithm());
        kmf.init(ks, keyStorePassword.toCharArray());
        return kmf.getKeyManagers();
    }
}