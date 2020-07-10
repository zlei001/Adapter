package com.cttnet.zhwg.ywkt.client.builder;

import com.cttnet.common.util.JackJson;
import com.cttnet.common.util.SpringContextUtils;
import com.cttnet.zhwg.ywkt.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * <pre>微服务通信Service基类</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-17
 * @since jdk 1.8
 */
@Slf4j
public class RestTemplateBuilder {

    protected RestTemplate restTemplate;
    private final String applicationName;
    private String url;

    private final MultiValueMap<String, Object> mvm;

    public RestTemplateBuilder(String applicationName) {
        this.restTemplate = SpringContextUtils.getBean(RestTemplate.class);
        this.applicationName = applicationName;
        this.mvm = new LinkedMultiValueMap<String, Object>();
    }

    /**
     * 目标controller中的方法url
     *
     * @param url
     * @return
     */
    public RestTemplateBuilder url(String url) {
        this.url = url;
        return this;
    }

    /**
     * 直接设置参数集
     *
     * @param params
     * @return
     */
    public RestTemplateBuilder setParams(Map<String, Object> params) {
        params.entrySet().stream().forEach(p -> {
            if(null != p.getValue()) {
                this.mvm.add(p.getKey(), p.getValue().toString());
            }
        });
        return this;
    }
    /**
     * 直接设置参数集,Map字符串
     *
     * @param params
     * @return
     */
    public RestTemplateBuilder setParamsString(Map<String, String> params) {
        params.entrySet().stream().forEach(p -> {
            if(null != p.getValue()) {
                addParam(p.getKey(),p.getValue());
            }
        });
        return this;
    }

    /**
     * 添加参数对
     *
     * @param key
     * @param value
     * @return
     */
    public RestTemplateBuilder addParam(String key, Object value) {
        String val = null;
        if(value != null) {
            if(value instanceof String) {
                val = (String)value;
            }else {
                val = value.toString();
            }
            if(StringUtils.isNotBlank(val)) {
                this.mvm.add(key, val);
            }
        }
        return this;
    }

    /**
     * 返回请求路径
     *
     * @return
     */
    public String getRemoteUrl() {
        return String.format("http://%s/%s", applicationName, url);
    }

    /**
     * @param <T> 对象定义,比如 com.cttnet.common.enums.ResponseData
     *                     或者其他接口方自定义返回对象
     * @param responseType
     * @return
     */
    public  <T> T post(Class<T> responseType, MediaType mediaType) {
        long s = System.currentTimeMillis();
        HttpHeaders headers = new HttpHeaders();
        // 定义请求参数类型，这里用json所以是MediaType.APPLICATION_JSON APPLICATION_FORM_URLENCODED
        headers.setContentType(mediaType);
        Object requestEntity = null;
        if(mediaType == MediaType.APPLICATION_JSON) {
            requestEntity = new HttpEntity<Map<String, Object>>(
                    this.mvm.toSingleValueMap(), headers);
        }else {
            requestEntity = new HttpEntity<>(
                    this.mvm, headers);
        }

        T responseData = null;
        try{
            responseData = restTemplate.postForEntity(this.getRemoteUrl(), requestEntity, responseType).getBody();
        }catch (Exception e) {
            throw new BusinessException("API->{},P->{}", getRemoteUrl(), this.mvm, e);
        }
        if (responseData == null) {
            throw new BusinessException("API->{},P->{}请求数据为null", getRemoteUrl(), this.mvm);
        }
        log.info("API->{},耗时{}(ms)", getRemoteUrl(), (System.currentTimeMillis()-s));
        return responseData;
    }

    /**
     * @param <T>          对象定义,比如 com.cttnet.common.enums.ResponseData
     *                     或者其他接口方自定义返回对象
     * @param responseType
     * @return
     */
    public <T> T post(Class<T> responseType) {
        return post(responseType,MediaType.APPLICATION_FORM_URLENCODED);
    }
    /**
     * @param <T>          对象定义,比如 com.cttnet.common.enums.ResponseData
     *                     或者其他接口方自定义返回对象
     * @param responseType
     * @return
     */
    public <T> T postJSON(Class<T> responseType) {
        return post(responseType,MediaType.APPLICATION_JSON);
    }

    /**
     *
     * @return json 字符串
     */
    public String post() {
        return post(String.class);
    }

    /**
     * body对象请求
     * @param request
     * @param responseType
     * @param <T>
     * @return
     */
    public  <T> T post(Object request, Class<T> responseType) {
        long s = System.currentTimeMillis();
        HttpHeaders headers = new HttpHeaders();
        T responseData = null;
        try{
            responseData = restTemplate.postForEntity(this.getRemoteUrl(), request, responseType).getBody();
        }catch (Exception e) {
            throw new BusinessException("API->{},P->{}", getRemoteUrl(), JackJson.getBasetJsonData(request), e);
        }
        if (responseData == null) {
            throw new BusinessException("API->{},P->{}请求数据为null", getRemoteUrl(), JackJson.getBasetJsonData(request));
        }
        log.info("API->{},耗时{}(ms)", getRemoteUrl(), (System.currentTimeMillis()-s));
        return responseData;
    }

    /**
     * 获取请求连接
     * @return
     */
    public String getUrl() {
        return this.url;
    }

}
