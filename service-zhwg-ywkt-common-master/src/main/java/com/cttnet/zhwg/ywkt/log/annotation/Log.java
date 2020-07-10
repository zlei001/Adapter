package com.cttnet.zhwg.ywkt.log.annotation;

import java.lang.annotation.*;

/**
 * <pre>日志注解</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-22
 * @since jdk 1.8
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /** 模块 */
    String module() default "";
    /** 操作名称 */
    String methodName() default "";
    /** 请求方 */
    String requester() default "";
    /** 响应方 */
    String responder() default "";
    /** 请求链接，多个拼接*/
    String[] urls() default {};
}
