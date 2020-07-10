package com.cttnet.zhwg.ywkt.log.aspectj;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cttnet.common.enums.ResponseRecode;
import com.cttnet.zhwg.ywkt.exception.BusinessException;
import com.cttnet.zhwg.ywkt.log.annotation.Log;
import com.cttnet.zhwg.ywkt.log.basic.BasicLogResponse;
import com.cttnet.zhwg.ywkt.log.basic.BasicOrderLogResponse;
import com.cttnet.zhwg.ywkt.log.dto.request.RecordLogSaveRequestDTO;
import com.cttnet.zhwg.ywkt.log.task.RecordLogTask;
import com.cttnet.zhwg.ywkt.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.context.properties.bind.PlaceholdersResolver;
import org.springframework.boot.context.properties.bind.PropertySourcesPlaceholdersResolver;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * <pre>日志拦截</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-22
 * @since jdk 1.8
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    private final Environment environment;

    private final PlaceholdersResolver placeholdersResolver;

    public LogAspect(Environment environment) {
        this.environment = environment;
        this.placeholdersResolver = new PropertySourcesPlaceholdersResolver(environment);
    }

    /**
     * 编织切点
     */
    @Pointcut("@annotation(com.cttnet.zhwg.ywkt.log.annotation.Log)")
    public void logPointCut() {
    }


    /**
     * 环绕通知
     *
     * @param joinPoint joinPoint
     * @return result
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws MethodArgumentNotValidException {
        // 获取日志配置
        RecordLogSaveRequestDTO recordLog = getRecordLog(joinPoint);
        Object result;
        try {
            // 执行请求
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("请求失败", throwable);
            saveRecordLog(recordLog, null, throwable);
            if (throwable instanceof BusinessException) {
                throw (BusinessException) throwable;
            }
            if (throwable instanceof IllegalArgumentException) {
                throw (IllegalArgumentException) throwable;
            }
            if (throwable instanceof MethodArgumentNotValidException) {
                throw (MethodArgumentNotValidException) throwable;
            }
            if (throwable instanceof HttpMessageNotReadableException) {
                throw (HttpMessageNotReadableException) throwable;
            }
            throw new RuntimeException(throwable);
        }
        // 保存日志
        saveRecordLog(recordLog, result, null);
        return result;
    }

    /**
     * 配置日志信息
     *
     * @param joinPoint joinPoint
     * @return RecordLogPO
     */
    private RecordLogSaveRequestDTO getRecordLog(ProceedingJoinPoint joinPoint) {

        Log annotation = getAnnotation(joinPoint);
        if (annotation == null) {
            return null;
        }
        RecordLogSaveRequestDTO recordLog = new RecordLogSaveRequestDTO();
        String url = parseUrl(joinPoint);
        String methodName = annotation.methodName();
        if (StringUtils.isBlank(methodName)) {
            methodName = joinPoint.getSignature().getName();
        }
        String module = annotation.module();
        String requester = annotation.requester();
        String responder = annotation.responder();
        recordLog.setId(IdUtil.fastSimpleUUID());
        recordLog.setUrl(url);
        recordLog.setModule(module);
        recordLog.setMethodName(methodName);
        recordLog.setRequester(requester);
        recordLog.setResponder(responder);
        recordLog.setRequestText(getParam(joinPoint));
        recordLog.setRequestTime(new Date());
        try {
            String sysName = environment.getProperty("spring.application.name")
                    + ":"
                    + environment.getProperty("server.port");
            recordLog.setSystemName(sysName);
        } catch (Exception e) {
            log.error("获取系统名称异常", e);
        }
        return recordLog;
    }

    /**
     * 获取Annotation
     *
     * @param joinPoint joinPoint
     * @return Log
     */
    private Log getAnnotation(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    /**
     * 获取请求参数
     *
     * @param joinPoint joinPoint
     * @return 多参数已 ## 分隔
     */
    private static String getParam(JoinPoint joinPoint) {

        Object[] args = joinPoint.getArgs();
        StringBuilder params = new StringBuilder();
        if (args != null) {
            for (Object obj : args) {
                if (params.length() != 0) {
                    params.append("##");
                }
                params.append(obj == null ? null : JSON.toJSONString(obj, SerializerFeature.NotWriteDefaultValue));
            }
        }
        return params.toString();
    }

    /**
     * 解析url
     *
     * @param joinPoint joinPoint
     * @return url
     */
    private String parseUrl(JoinPoint joinPoint) {
        Log annotation = getAnnotation(joinPoint);
        if (annotation == null) {
            return "";
        }
        String[] urls = annotation.urls();
        if (urls.length == 0) {
            try {
                return ServletUtils.getRequest().getRequestURL().toString();
            } catch (Exception e) {
                log.error("自动获取请求连接失败", e);
                return null;
            }

        }
        StringBuilder url = new StringBuilder();
        for (String str : urls) {
            Object o = this.placeholdersResolver.resolvePlaceholders(str);
            url.append(o == null ? null : o.toString());
        }
        return url.toString();
    }

    /**
     * 保存调用日志
     *
     * @param recordLog recordLog
     * @param result    result
     * @param e         e
     * @return orderId
     */
    private void saveRecordLog(RecordLogSaveRequestDTO recordLog, Object result, Throwable e) {
        if (recordLog == null) {
            return;
        }
        try {
            Date now = new Date();
            recordLog.setResponseTime(now);
            recordLog.setDelay(recordLog.getResponseTime().getTime() - recordLog.getRequestTime().getTime());
            recordLog.setResponseText(result == null ? null
                    : JSON.toJSONString(result, SerializerFeature.NotWriteDefaultValue));
            if (e != null) {
                recordLog.setState(ResponseRecode.SERVER_ERROR_CODE.getRecode());
                recordLog.setRemark(e.getMessage());
                RecordLogTask.add(recordLog);
                return;
            }
            if (result instanceof BasicOrderLogResponse) {
                BasicOrderLogResponse resp = (BasicOrderLogResponse) result;
                recordLog.setState(resp.recode().getRecode());
                recordLog.setRemark(resp.desc());
                RecordLogTask.add(recordLog);
                recordLog.setOrderBillId(resp.orderId());
                return;
            }
            if (result instanceof BasicLogResponse) {
                BasicLogResponse resp = (BasicLogResponse) result;
                recordLog.setState(resp.recode().getRecode());
                recordLog.setRemark(resp.desc());
                RecordLogTask.add(recordLog);
                return;
            }

            recordLog.setState(ResponseRecode.RS_OHTER.getRecode());
            recordLog.setRemark("响应参数未实现对应方法，无法获取状态");
            RecordLogTask.add(recordLog);
        } catch (Exception exception) {
            log.error("保存日志失败", e);
        }

    }
}
