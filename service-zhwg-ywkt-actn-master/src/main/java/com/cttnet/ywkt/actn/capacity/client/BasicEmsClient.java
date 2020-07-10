package com.cttnet.ywkt.actn.capacity.client;

import com.cttnet.ywkt.actn.capacity.bo.StatusResponse;
import com.cttnet.ywkt.actn.data.dto.ems.ActnMacEmsDTO;
import com.cttnet.zhwg.ywkt.http.HttpUtils;
import com.cttnet.zhwg.ywkt.http.domian.HttpRequestBody;
import com.cttnet.zhwg.ywkt.http.enums.HttpMethodEnum;
import com.cttnet.zhwg.ywkt.http.enums.ProtocolEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>基础Ems调用Client</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-20
 * @since jdk 1.8
 */
@Slf4j
public class BasicEmsClient {

    private static final ProtocolEnum DEFAULT_PROTOCOL = ProtocolEnum.HTTPS;
    private static final String DEFAULT_PROTOCOL_LAYER = "TLS";
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static final String DEFAULT_CONTENT_TYPE = "application/json";


    final private ActnMacEmsDTO actnEmsBO;

    public BasicEmsClient(ActnMacEmsDTO actnEmsBO) {
        this.actnEmsBO = actnEmsBO;
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
     * @param cls cls
     * @return T
     * @throws Exception e
     */
    public <T> T request(HttpRequestBody requestBody, Class<T> cls, String methodName) throws Exception{
      return request(requestBody, null, cls, methodName);
    }

    /**
     *
     * @param requestBody 开放设置
     * @param body 请求参数
     * @param cls cls
     * @return T
     * @throws Exception e
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
     * 提供给异步接口使用
     * @param url url
     * @param body body
     * @param method method
     * @param methodName  methodName
     * @return 接口调用状态
     * @throws Exception Exception
     */
    public StatusResponse requestOfStatus(String url, String body, HttpMethodEnum method, String methodName) {

        HttpRequestBody requestBody = getRequestBody(url, method);
        CloseableHttpResponse response = null;
        String result = null;
        StatusResponse statusResponse = new StatusResponse();
        //记入原始下发报文
        statusResponse.setUrl(url);
        statusResponse.setParam(body);
        try {
            if (StringUtils.isNotBlank(body)) {
                response= HttpUtils.requestResponse(requestBody, body);
            } else {
                response= HttpUtils.requestResponse(requestBody);
            }
            //正常返回不会为null
            if (response != null) {
                int statusCode = response.getStatusLine().getStatusCode();
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity, requestBody.getResponseCharset());
                }
                statusResponse.setStatus(statusCode);
                statusResponse.setDesc(result);
            }
        } catch (Exception e) {
            log.error("调用失败", e);
            statusResponse.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            statusResponse.setDesc(e.getMessage());
            return statusResponse;
        }
        return statusResponse;
    }

    /**
     * 请求参数
     * @param url
     * @param method
     * @return
     */
    public HttpRequestBody getRequestBody(String url, HttpMethodEnum method) {

        HttpRequestBody requestBody = new HttpRequestBody();
        url = actnEmsBO.getBaseUrl() + url;
        requestBody.setUrl(url);
        requestBody.setMethod(method);
        requestBody.setTrustAll(true);
        requestBody.setProtocol(actnEmsBO.getProtocolEnum() != null ? actnEmsBO.getProtocolEnum() : DEFAULT_PROTOCOL);
        requestBody.setProtocolLayer(actnEmsBO.getProtocolLayer() != null ? actnEmsBO.getProtocolLayer() : DEFAULT_PROTOCOL_LAYER);
        requestBody.setRequestCharset(actnEmsBO.getCharset() != null ? actnEmsBO.getCharset() : DEFAULT_CHARSET);
        requestBody.setResponseCharset(actnEmsBO.getCharset() != null ? actnEmsBO.getCharset() : DEFAULT_CHARSET);
        Map<String, String> header = new HashMap<>(2);
        String authorization = "Basic " + new sun.misc.BASE64Encoder().encode((actnEmsBO.getUsername() + ":" + actnEmsBO.getPassword()).getBytes());
        header.put("Authorization", authorization);
        header.put("Content-Type", actnEmsBO.getContentType() != null ? actnEmsBO.getContentType() : DEFAULT_CONTENT_TYPE);
        requestBody.setHeaderExtensions(header);

        return requestBody;
    }
}
