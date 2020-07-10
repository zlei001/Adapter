package com.cttnet.zhwg.ywkt.http;

import com.alibaba.fastjson.JSON;
import com.cttnet.common.util.SysConfig;
import com.cttnet.zhwg.ywkt.http.client.HttpsClient;
import com.cttnet.zhwg.ywkt.http.enums.HttpMethodEnum;
import com.cttnet.zhwg.ywkt.http.domian.HttpRequestBody;
import com.cttnet.zhwg.ywkt.http.enums.ProtocolEnum;
import com.cttnet.zhwg.ywkt.http.exception.HttpRequestException;
import com.cttnet.zhwg.ywkt.http.exception.HttpStatusException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * <pre></pre>
 *
 * @author zhangyaomin
 * @date 2020-05-22
 * @since jdk 1.8
 */
@Slf4j
public class HttpUtils {

    private final static Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private HttpUtils() {
    }

    /**
     * 请求
     *
     * @param requestBody   requestBody
     * @param responseClass responseClass
     * @return T
     * @throws Exception Exception
     */
    public static <T> T request(HttpRequestBody requestBody, Class<T> responseClass) throws Exception {

        CloseableHttpClient client = getHttpClient(requestBody);
        HttpRequestBase httpRequestBase = getHttpRequestBase(requestBody);
        String response = execute(client, httpRequestBase, requestBody.getResponseCharset());
        return toJavaObject(response, responseClass);
    }

    /**
     * 请求
     *
     * @param requestBody   requestBody
     * @param body          请求参数体 JSON 请求需支持 entity传值： PATCH、POST、PUT
     * @param responseClass responseClass
     * @return T
     * @throws Exception Exception
     */
    public static <T> T request(HttpRequestBody requestBody, String body, Class<T> responseClass) throws Exception {
        CloseableHttpClient client = getHttpClient(requestBody);
        HttpRequestBase httpRequestBase = getHttpRequestBase(requestBody, body);
        String response = execute(client, httpRequestBase, requestBody.getResponseCharset());
        return toJavaObject(response, responseClass);
    }

    /**
     * 请求
     *
     * @param requestBody   requestBody
     * @param bodyParam     请求参数体 bodyParam 请求需支持 entity传值： PATCH、POST、PUT
     * @param responseClass responseClass
     * @return T
     * @throws Exception Exception
     */
    public static <T> T request(HttpRequestBody requestBody, Map<String, String> bodyParam, Class<T> responseClass) throws Exception {

        CloseableHttpClient client = getHttpClient(requestBody);
        HttpRequestBase httpRequestBase = getHttpRequestBase(requestBody, bodyParam);
        String response = execute(client, httpRequestBase, requestBody.getResponseCharset());
        return toJavaObject(response, responseClass);
    }

    /**
     * 请求
     *
     * @param requestBody   requestBody
     * @param param         上传文件参数
     * @param responseClass responseClass
     * @return T
     * @throws Exception Exception
     */
    public <T> T uploadFile(HttpRequestBody requestBody, Map<String, Object> param, Class<T> responseClass) throws Exception {

        Charset charset = Optional.ofNullable(requestBody.getRequestCharset()).orElse(DEFAULT_CHARSET);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        ContentType contentType = ContentType.create(MapUtils.getString(requestBody.getHeaderExtensions(), "contentType", "multipart/form-data"), charset);
        ContentType contentTypeStr = ContentType.create("text/plain", charset);
        Set<Map.Entry<String, Object>> entries = param.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            String key = entry.getKey();
            Object val = entry.getValue();
            if (StringUtils.isEmpty(key) || val == null) {
                log.warn("上传文件参数节点", entry.toString());
                continue;
            }
            if (val instanceof File) {
                File file = (File) val;
                builder.addBinaryBody(key, (File) val, contentType, file.getName());
            } else if (val instanceof InputStream) {
                builder.addBinaryBody(key, (InputStream) val, contentType, null);
            } else if (val instanceof byte[]) {
                builder.addBinaryBody(key, (byte[]) val, contentType, null);
            } else {
                builder.addTextBody(key, val.toString(), contentTypeStr);
            }

        }
        CloseableHttpClient client = getHttpClient(requestBody);
        HttpPost post = new HttpPost(requestBody.getUrl());
        post.setEntity(builder.build());
        String response = execute(client, post, requestBody.getResponseCharset());
        return toJavaObject(response, responseClass);
    }


    /**
     * 请求
     * @param requestBody requestBody
     * @return CloseableHttpResponse
     * @throws Exception Exception
     */
    public static CloseableHttpResponse requestResponse(HttpRequestBody requestBody) throws Exception {

        CloseableHttpClient client = getHttpClient(requestBody);
        HttpRequestBase httpRequestBase = getHttpRequestBase(requestBody);
        return execute(client, httpRequestBase);
    }

    /**
     * 请求
     * @param requestBody requestBody
     * @param body 请求参数体 JSON 请求需支持 entity传值： PATCH、POST、PUT
     * @return CloseableHttpResponse
     * @throws Exception Exception
     */
    public static CloseableHttpResponse requestResponse(HttpRequestBody requestBody, String body) throws Exception {

        CloseableHttpClient client = getHttpClient(requestBody);
        HttpRequestBase httpRequestBase = getHttpRequestBase(requestBody, body);
        return execute(client, httpRequestBase);
    }

    /**
     * 请求
     * @param requestBody requestBody
     * @param bodyParam 请求参数体 bodyParam 请求需支持 entity传值： PATCH、POST、PUT
     * @return CloseableHttpResponse
     * @throws Exception Exception
     */
    public static CloseableHttpResponse requestResponse(HttpRequestBody requestBody, Map<String, String> bodyParam) throws Exception {

        CloseableHttpClient client = getHttpClient(requestBody);
        HttpRequestBase httpRequestBase = getHttpRequestBase(requestBody, bodyParam);
        return execute(client, httpRequestBase);
    }

    /**
     * 执行请求
     *
     * @param closeableHttpClient closeableHttpClient
     * @param httpRequestBase     httpRequestBase
     * @param responseCharset     响应编码格式
     * @return String
     */
    private static String execute(CloseableHttpClient closeableHttpClient, HttpRequestBase httpRequestBase, Charset responseCharset) {

        // 使用默认字符集
        responseCharset = Optional.ofNullable(responseCharset).orElse(DEFAULT_CHARSET);
        String result = null;
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            closeableHttpResponse = execute(closeableHttpClient, httpRequestBase);
            Assert.notNull(closeableHttpResponse, "");
            int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
            HttpEntity entity = closeableHttpResponse.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, responseCharset);
            }
            //20X成功
            if (statusCode == HttpStatus.SC_OK
                    || statusCode == HttpStatus.SC_CREATED
                    || statusCode == HttpStatus.SC_ACCEPTED
                    || statusCode == HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION
                    || statusCode == HttpStatus.SC_NO_CONTENT
                    || statusCode == HttpStatus.SC_RESET_CONTENT
                    || statusCode == HttpStatus.SC_PARTIAL_CONTENT
                    || statusCode == HttpStatus.SC_MULTI_STATUS) {
                return result;
            } else if (statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
                //重定向
                Header header = closeableHttpResponse.getFirstHeader("Location");
                result = header.getValue();
            } else {
                throw new HttpStatusException(statusCode, result);
            }
        } catch (Exception e) {
            log.error("请求失败", e);
            throw new HttpRequestException("请求失败", e);
        } finally {
            if (closeableHttpResponse != null) {
                try {
                    closeableHttpResponse.close();
                } catch (IOException e) {
                    log.error("关闭closeableHttpResponse失败", e);
                }
            }
            if (closeableHttpClient != null) {
                try {
                    closeableHttpClient.close();
                } catch (IOException e) {
                    log.error("关闭closeableHttpClient失败", e);
                }
            }
        }
        return result;
    }


    /**
     * 执行请求
     *
     * @param client          client
     * @param httpRequestBase httpRequestBase
     * @return CloseableHttpResponse
     * @throws IOException
     */
    private static CloseableHttpResponse execute(CloseableHttpClient client, HttpRequestBase httpRequestBase) throws IOException {

        return client.execute(httpRequestBase);
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
     * 获取http请求
     *
     * @param requestBody requestBody
     * @param body        请求参数体 JSON 请求需支持 entity传值： PATCH、POST、PUT
     * @return HttpRequestBase
     */
    private static HttpRequestBase getHttpRequestBase(HttpRequestBody requestBody, String body) {
        Charset requestCharset = requestBody.getRequestCharset();
        requestCharset = requestCharset == null ? DEFAULT_CHARSET : requestCharset;

        HttpRequestBase httpRequestBase = getHttpRequestBase(requestBody);
        //设置请求参数
        if (StringUtils.isNotEmpty(body)
                && httpRequestBase instanceof HttpEntityEnclosingRequestBase) {
            HttpEntityEnclosingRequestBase requestBase
                    = (HttpEntityEnclosingRequestBase) httpRequestBase;
            requestBase.setEntity(new StringEntity(body, requestCharset));

        }
        return httpRequestBase;
    }

    /**
     * 获取http请求
     *
     * @param requestBody requestBody
     * @param bodyParam   请求参数体 Map 请求需支持 entity传值： PATCH、POST、PUT
     * @return HttpRequestBase
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     */
    private static HttpRequestBase getHttpRequestBase(HttpRequestBody requestBody, Map<String, String> bodyParam) throws UnsupportedEncodingException {

        Charset requestCharset = Optional.ofNullable(requestBody.getRequestCharset()).orElse(DEFAULT_CHARSET);
        HttpRequestBase httpRequestBase = getHttpRequestBase(requestBody);
        //设置请求参数
        if (bodyParam != null
                && httpRequestBase instanceof HttpEntityEnclosingRequestBase) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Set<String> keySet = bodyParam.keySet();
            for (String key : keySet) {
                list.add(new BasicNameValuePair(key, bodyParam.get(key)));
            }
            HttpEntityEnclosingRequestBase requestBase
                    = (HttpEntityEnclosingRequestBase) httpRequestBase;
            if (list.size() > 0) {
                requestBase.setEntity(new UrlEncodedFormEntity(list, requestCharset));
            }
        }
        return httpRequestBase;
    }

    /**
     * 设置请求参数
     *
     * @param httpRequestBase httpRequestBase
     * @param requestBody     requestBody
     */
    private static void setConfig(HttpRequestBase httpRequestBase, HttpRequestBody requestBody) {

        int connectTimeout = requestBody.getConnectTimeout();
        int socketTimeout = requestBody.getSocketTimeout();
        int defaultConnectTimeout = Integer.parseInt(SysConfig.getProperty("ywkt.http.connect-timeout", "30"));
        int defaultSocketTimeout = Integer.parseInt(SysConfig.getProperty("ywkt.http.socket-timeout", "120"));
        connectTimeout = connectTimeout <= 0 ? defaultConnectTimeout : connectTimeout;
        socketTimeout = socketTimeout <= 0 ? defaultSocketTimeout : socketTimeout;
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(connectTimeout * 1000)
                .setSocketTimeout(socketTimeout * 1000)
                .build();
        httpRequestBase.setConfig(config);
    }

    /**
     * 获取http请求
     *
     * @param requestBody requestBody
     * @return
     */
    private static HttpRequestBase getHttpRequestBase(HttpRequestBody requestBody) {

        String url = requestBody.getUrl();
        HttpMethodEnum method = requestBody.getMethod();
        HttpRequestBase httpRequestBase = null;
        if (method == null) {
            throw new RuntimeException("请求方式不能为空,请指定请求方式");
        }
        switch (method) {
            case GET:
                httpRequestBase = new HttpGet(url);
                break;
            case HEAD:
                httpRequestBase = new HttpHead(url);
                break;
            case POST:
                httpRequestBase = new HttpPost(url);
                break;
            case PUT:
                httpRequestBase = new HttpPut(url);
                break;
            case PATCH:
                httpRequestBase = new HttpPatch(url);
                break;
            case DELETE:
                httpRequestBase = new HttpDelete(url);
                break;
            case OPTIONS:
                httpRequestBase = new HttpOptions(url);
                break;
            case TRACE:
                httpRequestBase = new HttpTrace(url);
                break;
            default:
                ;
        }
        Map<String, String> requestHeader = requestBody.getHeaderExtensions();
        //设置请求头部
        if (requestHeader != null) {
            Set<Map.Entry<String, String>> entries = requestHeader.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                httpRequestBase.addHeader(entry.getKey(), entry.getValue());
            }
        }
        setConfig(httpRequestBase, requestBody);
        return httpRequestBase;
    }

    /**
     * toJavaObject
     *
     * @param json  json
     * @param clazz class
     * @param <T>   T
     * @return T
     */
    private static <T> T toJavaObject(String json, Class<T> clazz) {
        if (clazz == String.class) {
            return (T) json;
        }
        return JSON.toJavaObject(JSON.parseObject(json), clazz);
    }
}
