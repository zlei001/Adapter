package com.cttnet.zhwg.ywkt.mtosi.advice;

import com.cttnet.common.enums.ResponseData;
import com.cttnet.common.enums.ResponseRecode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <pre>
 *  全局异常处理
 * </pre>
 *
 * @author zhangyaomin
 * @date 2019/12/28
 * @since java 1.8
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.cttnet.zhwg.ywkt.mtosi.controller.restconf")
public class MtosiExceptionHandler {
    /**
     * 参数校验失败
     * @param e  methodArgumentNotValidException
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseData<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        log.error("参数无效", e);
        ResponseData<?> responseData = new ResponseData<>();
        responseData.setCode(ResponseRecode.PARAM_ERROR_CODE.getRecode());
        StringBuilder sb = new StringBuilder();
        BindingResult bindingResult = e.getBindingResult();
        for(FieldError fieldError : bindingResult.getFieldErrors()){
            if (sb.length() != 0) {
                sb.append("##");
            }
            sb.append(fieldError.getField()).append(fieldError.getDefaultMessage());
        }
        responseData.setMessage(sb.toString());
        return responseData;
    }


    /**
     * 运行异常
     * @param e RuntimeException
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseData<?> handleException(RuntimeException e) {

        log.error("", e);
        ResponseData<?> responseData = new ResponseData<>();
        responseData.setCode(ResponseRecode.SERVER_ERROR_CODE.getRecode());
        responseData.setMessage(e.getMessage());
        return responseData;
    }

    /**
     * 系统异常
     * @param e Exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseData<?> handleException(Exception e) {

        log.error("内部异常", e);
        ResponseData<?> responseData = new ResponseData<>();
        responseData.setCode(ResponseRecode.SERVER_ERROR_CODE.getRecode());
        responseData.setMessage(ResponseRecode.SERVER_ERROR_CODE.getRedesc());
        return responseData;
    }
}