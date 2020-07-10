package com.kt.dc.otn.util;




import com.kt.dc.otn.bean.HttpRequestBody;
import com.kt.dc.otn.bean.RequestResult;
import com.kt.dc.otn.client.HttpsClient;
import com.kt.dc.otn.client.exception.HttpStatusException;
import com.kt.dc.otn.enums.HttpMethodEnum;
import com.kt.dc.otn.enums.ProtocolEnum;
import com.kt.dc.otn.test.ConfigTest;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;

import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.*;

/**
 * 描述 用于发送http请求的工具类
 *
 * @author zi
 * @version 0.0.1
 * @date 20200709.
 * @see
 */

public class SendHttpRequestUtil {
    protected final static Logger logger = LoggerFactory.getLogger(SendHttpRequestUtil.class);
    private static final int MAX_TIMEOUT = 7000;
    private static final String CODE = "UTF-8";

    private static final ProtocolEnum DEFAULT_PROTOCOL = ProtocolEnum.HTTPS;
    private static final String DEFAULT_PROTOCOL_LAYER = "TLS";
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static final String DEFAULT_CONTENT_TYPE = "application/xml";
    private static final Integer FAILCODE = 200;
    private static final Integer SUCCESSCODE = 400;

    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;

    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        // 在提交请求之前 测试连接是否可用
        configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();
    }

    /**
     * 获取httpClient
     *
     * @param requestBody requestBody
     * @return
     * @throws Exception Exception
     */
    private static CloseableHttpClient getHttpClient(HttpRequestBody requestBody) throws Exception {

        Assert.notNull(requestBody.getProtocol(), "Protocol为空");
        CloseableHttpClient client = null;
        if (ProtocolEnum.HTTPS == requestBody.getProtocol()) {
            client = HttpsClient.builder().trustAll(requestBody.isTrustAll())
                    .protocolLayer(requestBody.getProtocolLayer())
                    .keyStorePath(requestBody.getKeyStorePath())
                    .keyStorePassword(requestBody.getKeyStorePassword())
                    .build()
                    .toCloseableHttpClient();
        } else {
            client = HttpClients.createDefault();
        }
        return client;
    }

    /**
     * 请求参数
     * @param url
     * @param method
     * @return
     */
    public static HttpRequestBody getRequestBody(String url, HttpMethodEnum method) {
        HttpRequestBody requestBody = new HttpRequestBody();
        requestBody.setUrl(url);
        requestBody.setMethod(method);
        requestBody.setTrustAll(true);
        requestBody.setProtocol(DEFAULT_PROTOCOL);
        requestBody.setProtocolLayer( DEFAULT_PROTOCOL_LAYER);
        requestBody.setRequestCharset(DEFAULT_CHARSET);
        requestBody.setResponseCharset(DEFAULT_CHARSET);
        Map<String, String> header = new HashMap<>(2);
        String authorization = "Basic " + new sun.misc.BASE64Encoder().encode(("nbiuser" + ":" + "Changeme_123").getBytes());
        header.put("Authorization", authorization);
        header.put("Content-Type",DEFAULT_CONTENT_TYPE);
        requestBody.setHeaderExtensions(header);

        return requestBody;
    }


    /**
     * 填充请求头参数至get请求中
     *
     * @param httpPost
     * @param headers
     * @return
     */
    private static HttpGet setHeadersToGet(HttpGet httpPost, Map<String, String> headers) {
        if (headers != null && headers.size() != 0) {
            for (String key : headers.keySet()) {
                httpPost.addHeader(key, headers.get(key));
            }
        }
        return httpPost;
    }


    /**
     * 填充请求头参数至Post请求中
     *
     * @param httpPost
     * @param headers
     * @return
     */
    private static HttpPost setHeadersToPost(HttpPost httpPost, Map<String, String> headers) {
        if (headers != null && headers.size() != 0) {
            for (String key : headers.keySet()) {
                httpPost.addHeader(key, headers.get(key));
            }
        }
        return httpPost;
    }


    /**
     * 填充请求头参数至Put请求中
     *
     * @param httpPut
     * @param headers
     * @return
     */
    private static HttpPut setHeadersToPut(HttpPut httpPut, Map<String, String> headers) {
        if (headers != null && headers.size() != 0) {
            for (String key : headers.keySet()) {
                httpPut.addHeader(key, headers.get(key));
            }
        }
        return httpPut;
    }


    /**
     * 填充请求体参数至Post请求中
     *
     * @param httpPost
     * @param params
     * @return
     */
    private static HttpPost setParamsToRequest(HttpPost httpPost, Map<String, Object> params) {

        List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            NameValuePair pair = null;
            if (entry.getValue() == null) {
                pair = new BasicNameValuePair(entry.getKey(), null);
            } else {
                pair = new BasicNameValuePair(entry.getKey(), entry
                        .getValue().toString());
            }
            pairList.add(pair);
        }
        httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
        return httpPost;
    }


    /**
     * 发送 GET 请求（HTTP），不带输入数据
     *
     * @param url
     * @return
     */
    public static RequestResult doGet(String url) {
        return doGet(url, new HashMap<String, Object>(), new HashMap<String, String>());
    }

    /**
     * 发送 GET 请求（HTTP）,K-V形式,无请求头参数
     *
     * @param url
     * @param params
     * @return
     */
    public static RequestResult doGet(String url, Map<String, Object> params) {
        return doGet(url, params, null);
    }

    /**
     * 发送 GET 请求（HTTP），K-V形式，有请求头参数
     *
     * @param url     API接口URL
     * @param params  参数map
     * @param headers 请求头参数
     * @return
     */
    public static RequestResult doGet(String url, Map<String, Object> params, Map<String, String> headers) {
        HttpRequestBody requestBody = getRequestBody(url, HttpMethodEnum.GET);
        RequestResult requestResult = new RequestResult();
        String result = null;
        try {

            result = HttpUtils.request(requestBody);

//            CloseableHttpClient httpClient = getHttpClient(requestBody);
//            HttpEntity entity = null;
//            httpGet = new HttpGet(apiUrl);
//            httpGet = setHeadersToGet(httpGet, headers);
//            response = httpClient.execute(httpGet);
//            if (response != null) {
//                entity = response.getEntity();
//            }
//
//            if (entity != null) {
//                InputStream instream = entity.getContent();
//                result = IOUtils.toString(instream, "UTF-8");
                requestResult.setResult(result);
                requestResult.setStatusCode(SUCCESSCODE);
//            }
        } catch (Exception e) {
            logger.error("doGet请求异常！" + e.getMessage());
            requestResult.setStatusCode(FAILCODE);
            e.printStackTrace();
        }
        return requestResult;
    }


    /**
     * 发送 POST 请求（HTTP），JSON形式，无请求头参数
     *
     * @param url
     * @param json json对象
     * @return
     */
    public static RequestResult doPost(String url, String json) {
        return doPost(url, json, null);
    }



    public static RequestResult doPostXML(String url,String xml){
        HttpRequestBody requestBody = getRequestBody(url, HttpMethodEnum.POST);
        CloseableHttpClient client = null;
        CloseableHttpResponse resp = null;
        RequestResult requestResult = new RequestResult();
        try{
//            HttpPost httpPost = new HttpPost(url);
//            httpPost.setHeader("Content-Type", "application/xml");
//            client = getHttpClient(requestBody);
//            StringEntity entityParams = new StringEntity(xml,"utf-8");
//
//            httpPost.setEntity(entityParams);
//            client = HttpClients.createDefault();
//            resp = client.execute(httpPost);
            resp = HttpUtils.requestResponse(requestBody, xml);
            String resultMsg = EntityUtils.toString(resp.getEntity(),"utf-8");
            requestResult.setResult(resultMsg);
        }catch (Exception e){
            logger.error("doPostXml请求异常！" + e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                if(client!=null){
                    client.close();
                }
                if(resp != null){
                    requestResult.setStatusCode(resp.getStatusLine().getStatusCode());
                    resp.close();
                }
            } catch (IOException e) {
                logger.error("response关闭异常！" + e.getMessage());
                e.printStackTrace();
            }
        }
        return requestResult;
    }



    /**
     * 发送 POST 请求（HTTP），JSON形式，有请求头参数
     *
     * @param url
     * @param json    json对象
     * @param headers 请求头参数
     * @return
     */
    public static RequestResult doPost(String url, String json, Map<String, String> headers) {
        HttpRequestBody requestBody = getRequestBody(url, HttpMethodEnum.POST);

        RequestResult requestResult = new RequestResult();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            httpPost.setConfig(requestConfig);
            httpPost = setHeadersToPost(httpPost, headers);
            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");//解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("text/xml");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity entity = response.getEntity();
                httpStr = EntityUtils.toString(entity, "UTF-8");
            }
            requestResult.setResult(httpStr);
        } catch (IOException e) {
            logger.error("doPost请求异常，Json形式！" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (response != null) {
                requestResult.setStatusCode(response.getStatusLine().getStatusCode());
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    logger.error("response关闭失败异常，Json形式！" + e.getMessage());
                    e.printStackTrace();
                }
            }

        }
        return requestResult;
    }


    /**
     * 发送 PUT 请求（HTTP），无参数，无请求头参数
     *
     * @param url API接口URL
     * @return
     */
    public static RequestResult put(String url) {
        return put(url, null, null);
    }

    /**
     * 发送 PUT 请求（HTTP），K-V形式，无请求头参数
     *
     * @param url    API接口URL
     * @param params 参数map
     * @return
     */
    public static RequestResult put(String url, Map<String, String> params) {
        return put(url, params, null);
    }

    /**
     * 发送 PUT 请求（HTTP），K-V形式，有请求头参数
     *
     * @param url     API接口URL
     * @param params  参数map
     * @param headers 请求头参数
     * @return
     */
    public static RequestResult put(String url, Map<String, String> params, Map<String, String> headers) {
        HttpRequestBody requestBody = getRequestBody(url, HttpMethodEnum.POST);

        String responseText = "";
        HttpEntity entity = null;
        RequestResult requestResult = null;
        CloseableHttpResponse response = null;
        String httpStr = null;
        try {
            CloseableHttpClient client = getHttpClient(requestBody);
            HttpPut httpPut = new HttpPut(url);
            if (headers != null) {
                Set<String> set = headers.keySet();
                for (String item : set) {
                    String value = headers.get(item);
                    httpPut.addHeader(item, value);
                }
            }
            if (params != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : params.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                httpPut.setEntity(new UrlEncodedFormEntity(paramList, CODE));
            }
            response = client.execute(httpPut);
            if (response != null) {
                int statusCode = response.getStatusLine().getStatusCode();
                entity = response.getEntity();
                requestResult.setStatusCode(statusCode);
                if (statusCode != HttpStatus.SC_OK || entity == null) {
                    return requestResult;
                }
            }

            httpStr = EntityUtils.toString(entity, "utf-8");
            requestResult.setResult(httpStr);
        } catch (Exception e) {
            logger.error("put请求异常，Map形式！" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    requestResult.setStatusCode(response.getStatusLine().getStatusCode());
                    EntityUtils.consume(response.getEntity());
                }
            } catch (Exception e) {
                logger.error("response关闭异常！" + e.getMessage());
                e.printStackTrace();
            }
        }
        return requestResult;
    }


    /**
     * 发送 SSL PUT 请求（HTTP），JSON形式，无请求头参数
     *
     * @param url  API接口URL
     * @param json JSON对象
     * @return
     */
    public static RequestResult doPut(String url, Object json) {
        return doPut(url, json, null);
    }

    /**
     * 发送 PUT 请求（HTTP），JSON形式，有请求头参数
     *
     * @param url     API接口URL
     * @param json    JSON对象
     * @param headers 请求头参数
     * @return
     */
    public static RequestResult doPut(String url, Object json, Map<String, String> headers) {
        HttpRequestBody requestBody = getRequestBody(url, HttpMethodEnum.POST);

        RequestResult requestResult = new RequestResult();
        HttpPut httpPut = new HttpPut(url);
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {
            CloseableHttpClient httpClient = getHttpClient(requestBody);
            HttpEntity entity = null;
            httpPut.setConfig(requestConfig);
            httpPut = setHeadersToPut(httpPut, headers);
            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");//解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPut.setEntity(stringEntity);
            response = httpClient.execute(httpPut);
            if (response != null) {
                int statusCode = response.getStatusLine().getStatusCode();
                entity = response.getEntity();
                requestResult.setStatusCode(statusCode);
                if (statusCode != HttpStatus.SC_OK || entity == null) {
                    return requestResult;
                }
            }

            httpStr = EntityUtils.toString(entity, "utf-8");
            requestResult.setResult(httpStr);
        } catch (Exception e) {
            logger.error("put请求异常，Json形式！" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    requestResult.setStatusCode(response.getStatusLine().getStatusCode());
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    logger.error("response关闭异常！" + e.getMessage());
                    e.printStackTrace();
                }
            }

        }
        return requestResult;
    }

    /**
     * 发送 DELETE 请求（HTTP）,无参数
     *
     * @param url API接口URL
     * @return
     */
    public static RequestResult doDelete(String url) {
        return doDelete(url, null, null);
    }

    /**
     * 发送 DELETE 请求（HTTP），K-V形式，无请求头参数
     *
     * @param url    API接口URL
     * @param params 参数map
     * @return
     */
    public static RequestResult doDelete(String url, Map<String, String> params) {
        return doDelete(url, params, null);
    }

    /**
     * 发送 DELETE 请求（HTTP），K-V形式，有请求头参数
     *
     * @param url     API接口URL
     * @param params  参数map
     * @param headers 请求头参数
     * @return
     */
    public static RequestResult doDelete(String url, Map<String, String> params, Map<String, String> headers) {
        CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
        String responseText = "";
        HttpEntity entity = null;
        RequestResult requestResult = null;
        CloseableHttpResponse response = null;
        String httpStr = null;
        try {
            HttpDelete httpDelete = new HttpDelete(url);
            if (headers != null) {
                Set<String> set = headers.keySet();
                for (String item : set) {
                    String value = headers.get(item);
                    httpDelete.addHeader(item, value);
                }
            }
            if (params != null) {
                URIBuilder uriBuilder = new URIBuilder(url);
                if (params != null) {
                    for (String key : params.keySet()) {
                        uriBuilder.setParameter(key, params.get(key));
                    }
                }
                URI uri = uriBuilder.build();
                httpDelete.setURI(uri);
            }
            response = client.execute(httpDelete);
            if (response != null) {
                int statusCode = response.getStatusLine().getStatusCode();
                entity = response.getEntity();
                requestResult.setStatusCode(statusCode);
                if (statusCode != HttpStatus.SC_OK || entity == null) {
                    return requestResult;
                }
            }

            httpStr = EntityUtils.toString(entity, "utf-8");
            requestResult.setResult(httpStr);
        } catch (Exception e) {
            logger.error("Delete请求异常！" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    requestResult.setStatusCode(response.getStatusLine().getStatusCode());
                    EntityUtils.consume(response.getEntity());
                }
            } catch (Exception e) {
                logger.error("response关闭异常！" + e.getMessage());
                e.printStackTrace();
            }
        }
        return requestResult;
    }



}
