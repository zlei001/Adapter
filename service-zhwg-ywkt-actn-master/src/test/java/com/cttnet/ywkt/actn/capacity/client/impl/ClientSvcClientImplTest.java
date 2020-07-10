package com.cttnet.ywkt.actn.capacity.client.impl;

import com.alibaba.fastjson.JSON;
import com.cttnet.ywkt.actn.ActnApplication;
import com.cttnet.ywkt.actn.data.dto.ems.ActnMacEmsDTO;
import com.cttnet.ywkt.actn.domain.client.response.ResponseOfQueryAllClientSvc;
import com.cttnet.ywkt.actn.domain.client.response.ResponseOfQueryClientSvc;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <pre></pre>
 *
 * @author zhangyaomin
 * @date 2020-06-20
 * @since jdk 1.8
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActnApplication.class)
public class ClientSvcClientImplTest {

    private ActnMacEmsDTO actnEmsBO;

    @Before
    public void before() {
        actnEmsBO = new ActnMacEmsDTO();
        actnEmsBO.setBaseUrl("https://59.36.11.184:26336");
        actnEmsBO.setUsername("CNTEST");
        actnEmsBO.setPassword("Changeme_123");
    }

    @Test
    public void testAll() throws Exception {

        ClientSvcClientImpl client = new ClientSvcClientImpl(actnEmsBO);
        ResponseOfQueryAllClientSvc all = client.all();
        log.info("请求结果:{}", JSON.toJSONString(all));
    }

    @Test
    public void testOne() throws Exception {

        ClientSvcClientImpl client = new ClientSvcClientImpl(actnEmsBO);
        ResponseOfQueryClientSvc one = client.one("4a603508-de59-4272-9315-acb7abc6381f");
        log.info("请求结果:{}", JSON.toJSONString(one));
    }

}
