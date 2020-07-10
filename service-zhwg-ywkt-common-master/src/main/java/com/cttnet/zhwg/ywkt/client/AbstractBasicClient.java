package com.cttnet.zhwg.ywkt.client;

import com.alibaba.fastjson.JSON;
import com.cttnet.common.enums.ResponseData;
import com.cttnet.common.enums.ResponseRecode;
import com.cttnet.zhwg.ywkt.client.builder.RestTemplateBuilder;
import com.cttnet.zhwg.ywkt.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Optional.ofNullable;

/**
 * <pre>抽象调用服务Client</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-17
 * @since jdk 1.8
 */
@Slf4j
public abstract class AbstractBasicClient {

    /**
     * 查询list数据
     * @param url url
     * @param params params
     * @return list
     */
    protected List<Map<String, Object>> list(String url, Map<String, Object> params) {
        ResponseData<List<Map<String, Object>>> post = this.post(url, params, ResponseData.class);
        validResponse(post);
        List<Map<String, Object>> data = post.getData();
        return data;
    }

    /**
     * 查询list数据
     * @param url url
     * @param params params
     * @param clazz clazz
     * @param <T> t
     * @return list
     */
    protected <T>List<T> list(String url, Map<String, Object> params, Class<T> clazz) {
        ResponseData<List<Map>> post = this.post(url, params, ResponseData.class);
        validResponse(post);
        List<Map> data = post.getData();
       return parseData(data, clazz);
    }


    /**
     * post请求
     * @param url url
     * @param params params
     * @param <T> T
     * @return T
     */
    protected <T> T post(String url, Map<String, Object> params, Class<T> clazz) {

        return this.postByMedia(url, params, clazz, MediaType.APPLICATION_FORM_URLENCODED);
    }


    /**
     * post请求
     * @param url url
     * @param params params
     * @param <T> T
     * @return T
     */
    protected <T> T post(String url, Map<String, Object> params, Class<T> clazz, MediaType mediaType) {

        return this.postByMedia(url, params, clazz, mediaType);
    }

    /**
     * post请求
     * @param url url
     * @param params params
     * @param <T> T
     * @return T
     */
    private  <T> T postByMedia(String url, Map<String, Object> params, Class<T> clazz, MediaType mediaType) {

        String application = getApplicationName();
        RestTemplateBuilder builder = this.rest(application).url(url);

        // 添加参数
        Set<Map.Entry<String, Object>> entrySet = ofNullable(params).orElse(Collections.emptyMap()).entrySet();

        for (Map.Entry<String, Object> entry : entrySet) {
            builder.addParam(String.valueOf(entry.getKey()), entry.getValue());
        }
        return builder.post(clazz, mediaType);
    }

    /**
     * post请求
     * @param url
     * @param request
     * @param clazz
     * @param <T>
     * @return
     */
    protected <T> T post(String url, Object request, Class<T> clazz) {
        String application = getApplicationName();
        RestTemplateBuilder builder = this.rest(application).url(url);
        return builder.post(request, clazz);
    }



    /**
     * 跨服务调用方式，例如：
     * 1、rest(aplicationName).url(/xxx/xxx).setParams(Map).list();
     * 2、rest(aplicationName).url(/xxx/xxx).addParam(key,val).addParam(key,val)....addParam(key,val).list();
     * @param applicationName
     * @return
     */
    protected RestTemplateBuilder rest(String applicationName) {
        return new RestTemplateBuilder(applicationName);
    }

    protected void validResponse(ResponseData<?> responseData) {

        if (responseData.getCode() != ResponseRecode.SUCCESS_CODE.getRecode()
                && responseData.getCode() != ResponseRecode.RS_SUCCESS.getRecode()) {
            throw new BusinessException(ofNullable(responseData.getMessage()).orElse("接口调用返回异常状态代码！"));
        }
    }


    /**
     * 获取应用名
     * @return applicationName
     */
    protected abstract String getApplicationName();

    /**
     * 获取请求前缀
     * @return prefix
     */
    protected String getRequestPrefix() {
       return this.getApplicationName();
    }

    protected <T> List<T> parseData(List<Map> datas, Class<T> clazz) {
        return JSON.parseArray(JSON.toJSONString(datas), clazz);
    }





}
