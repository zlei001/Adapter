package com.kt.dc.otn.client.exception;

/**
 * <pre>HttpRequestException</pre>
 *
 * @author
 * @date
 * @since
 */
public class HttpRequestException extends RuntimeException{

    public HttpRequestException(String message) {
        super(message);
    }
    public HttpRequestException(String message, Exception exception) {
        super(message, exception);
    }
}
