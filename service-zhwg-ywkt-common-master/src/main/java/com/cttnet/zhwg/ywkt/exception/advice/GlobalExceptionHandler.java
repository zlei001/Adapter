package com.cttnet.zhwg.ywkt.exception.advice;

import cn.hutool.core.collection.CollUtil;
import com.cttnet.common.enums.ResponseData;
import com.cttnet.zhwg.ywkt.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.List;

/**
 * 全局异常处理
 *
 * @author wangzefeng
 */
@Slf4j
public class GlobalExceptionHandler {


    /**
     * IllegalArgumentException 异常处理
     *
     * @param e 异常信息
     * @return 异常处理响应
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseData<Void> illegalArgumentExceptionHandle(IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        return ResponseData.fail(String.format("参数非法:%s", e.getMessage()));
    }

    /**
     * JSR 303 异常处理
     *
     * @param e 异常信息
     * @return 异常处理响应
     * @see RequestResponseBodyMethodProcessor#resolveArgument(MethodParameter, ModelAndViewContainer, NativeWebRequest, WebDataBinderFactory)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseData<Void> methodArgumentNotValidExceptionHandle(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);

        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        StringBuilder builder = new StringBuilder();
        int i = 0;
        if (CollUtil.isNotEmpty(fieldErrors)) {
            builder.append("参数校验失败:[");
            while (i < fieldErrors.size()) {
                if (i != 0) {
                    builder.append(",");
                }
                FieldError fieldError = fieldErrors.get(i);
                String field = fieldError.getField();
                builder.append(field).append(fieldError.getDefaultMessage());
                i++;
            }

            builder.append("]");
        }

        return ResponseData.fail(builder.toString());
    }

    /**
     * HttpMessage 解析失败异常处理
     *
     * @param e 异常信息
     * @return 异常处理响应
     * @see RequestResponseBodyMethodProcessor#resolveArgument(MethodParameter, ModelAndViewContainer, NativeWebRequest, WebDataBinderFactory)
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseData<Void> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        return ResponseData.fail("参数解析失败:" + e.getMessage());
    }

    /**
     * 业务异常处理
     *
     * @param e 异常信息
     * @return 异常处理响应
     */
    @ExceptionHandler(value = BusinessException.class)
    public ResponseData<Void> businessExceptionHandle(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseData.fail(e.getMessage());
    }

    /**
     * 非检查异常处理
     *
     * @param e 异常信息
     * @return 异常处理响应
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseData<Void> runtimeExceptionHandle(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseData.fail("系统内部异常");
    }
}
