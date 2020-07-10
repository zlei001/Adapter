package com.cttnet.zhwg.ywkt.http;

import com.cttnet.zhwg.ywkt.http.domian.HttpRequestBody;
import com.cttnet.zhwg.ywkt.http.enums.HttpMethodEnum;
import com.cttnet.zhwg.ywkt.http.enums.ProtocolEnum;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * <pre></pre>
 *
 * @author zhangyaomin
 * @date 2020-06-20
 * @since jdk 1.8
 */
public class HttpUtilsTest {



    @Test
    public void doRequest() throws Exception {
        //TODO
        HttpRequestBody requestBody = new HttpRequestBody();
        requestBody.setUrl("https://59.36.11.184:26336/restconf/data/ietf-trans-client-service:client-svc");
        requestBody.setMethod(HttpMethodEnum.GET);
        requestBody.setProtocol(ProtocolEnum.HTTPS);
        requestBody.setTrustAll(true);
        requestBody.setProtocolLayer("TLS");
        Map<String, String> head = new HashMap<>();
        head.put("Content-Type", "application/json");
        //账号密码
        String authorization = "Basic " + new sun.misc.BASE64Encoder().encode(("CNTEST" + ":" + "Changeme_123").getBytes());
        head.put("Authorization", authorization);
        requestBody.setHeaderExtensions(head);

        String request = HttpUtils.request(requestBody, String.class);

        System.out.println("请求结果：" + request);

    }

}
