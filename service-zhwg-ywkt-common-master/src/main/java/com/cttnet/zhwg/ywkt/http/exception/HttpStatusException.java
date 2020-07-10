package com.cttnet.zhwg.ywkt.http.exception;

/**
 * <pre>HttpRequestException</pre>
 *
 * @author zhangyaomin
 * @date 2020-05-25
 * @since jdk 1.8
 */
public class HttpStatusException extends RuntimeException{

    /** 状态码 */
    private final int statusCode;

    public HttpStatusException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    @Override
    public String getMessage() {
        return String.format("statusCode:%s->%s", statusCode, super.getMessage());
    }

}
