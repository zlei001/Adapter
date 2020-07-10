package com.cttnet.zhwg.ywkt.http.exception;

/**
 * <pre>HttpRequestException</pre>
 *
 * @author zhangyaomin
 * @date 2020-05-25
 * @since jdk 1.8
 */
public class HttpRequestException extends RuntimeException{

    public HttpRequestException(String message) {
        super(message);
    }
    public HttpRequestException(String message, Exception exception) {
        super(message, exception);
    }
}
