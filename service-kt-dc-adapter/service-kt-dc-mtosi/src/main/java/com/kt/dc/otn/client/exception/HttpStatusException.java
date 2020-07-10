package com.kt.dc.otn.client.exception;

/**
 * <pre>HttpRequestException</pre>
 *
 * @author
 * @date
 * @since
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
