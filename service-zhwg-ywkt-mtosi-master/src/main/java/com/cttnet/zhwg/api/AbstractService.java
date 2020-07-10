package com.cttnet.zhwg.api;

import com.cttnet.common.enums.ResponseData;
import com.cttnet.common.enums.ResponseRecode;
import com.cttnet.common.mybatis.builder.RestTemplateBuilder;
import com.cttnet.common.mybatis.service.BaseService;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Optional.ofNullable;

/**
 * <pre>
 *  抽象服务调用
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/3/31
 * @since java 1.8
 */
public abstract class AbstractService extends BaseService {

    /**
     * 获取应用名称
     * @return applicationName
     */
    protected abstract String getApplication();


    /**
     * 获取单个对象
     * @param url 请求连接
     * @param params 请求参数
     * @param isJson 是否是json
     * @return object
     */
    protected Map<String, Object> one(String url, Map<String, Object> params, boolean ...isJson) {
        ResponseData<?> responseData = this.post(url, params, ResponseData.class, isJson);
        return ofNullable((Map<String, Object>) responseData.getData()).orElse(Collections.emptyMap());
    }

    /**
     * 获取列表对象
     * @param url 请求连接
     * @param params 请求参数
     * @param isJson 是否是json
     * @return list
     */
    protected List<Map<String, Object>> list(String url, Map<String, Object> params, boolean ...isJson) {
        ResponseData<?> responseData = this.post(url, params, ResponseData.class, isJson);
        return ofNullable((List<Map<String, Object>>) responseData.getData()).orElse(Collections.emptyList());
    }

    /**
     * post请求
     * @param url 请求连接
     * @param params 请求参数
     * @param responseClass 相应参数class
     * @param isJson 是否是json
     * @param <T> T
     * @return ResponseData
     */
    private <T extends ResponseData<?>> T post(String url, Map<String, Object> params, Class<T> responseClass, boolean ...isJson) {

        String application = getApplication();
        RestTemplateBuilder builder = this.rest(application).url(url);

        // 添加参数
        Set<Map.Entry<String, Object>> entrySet = ofNullable(params).orElse(Collections.emptyMap()).entrySet();

        for (Map.Entry<String, Object> entry : entrySet) {
            builder.addParam(String.valueOf(entry.getKey()), entry.getValue());
        }
        // 请求数据
        boolean json = false;
        if (ArrayUtils.isNotEmpty(isJson) && isJson[0]) {
            json = true;
        }
        T t = json ? builder.postJSON(responseClass) : builder.post(responseClass);
        this.validResponse(t);
        return t;
    }

    protected void validResponse(ResponseData<?> responseData) {
        if (responseData == null) {
            throw new RuntimeException("接口调用返回null！");
        }
        if (responseData.getCode() != ResponseRecode.SUCCESS_CODE.getRecode()
                && responseData.getCode() != ResponseRecode.RS_SUCCESS.getRecode()) {
            throw new RuntimeException(ofNullable(responseData.getMessage()).orElse("接口调用返回异常状态代码！"));
        }
    }

}
