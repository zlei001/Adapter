package com.cttnet.zhwg.ywkt.mtosi.controller.restconf;

import com.cttnet.common.util.JackJson;
import org.apache.commons.collections.MapUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

/**
 * MultiLayerSubnetWorkController Tester.
 *
 * @author dengkaihong
 * @since 2020/4/13
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MultiLayerSubnetWorkControllerTest {


    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    private  static  final  String URL = "/service-zhwg-ywkt-mtosi/restconf/multiLayerSubnetWork";

    @Before
    public void setUp() throws Exception {
        //在每个测试方法执行之前都初始化MockMvc对象    建议使用这种
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void takeOverSnc() throws Exception {

        String json  = "{\n" +
                "   \"requestHeader\":    {\n" +
                "      \"uuid\": \"abd6f326-d21e-43fe-b7fd-c7ae55bd4200\",\n" +
                "      \"nonce\": \"1518144802590\",\n" +
                "      \"sign\": \"1802DDFB7E5750A095CDB1D84263E00C\",\n" +
                "      \"domain\": \"1010001\",\n" +
                "      \"headExtensions\": null\n" +
                "   },\n" +
                "   \"body\":    {\n" +
                "   \"emsId\": \"9EF0B1BD2C3523F4C2DFBC0F69A9AABD\",\n" +
                "   \"neIds\": [\"3C31AC9FC30D68DC46B3FDED9DC36627\"]\n" +
                "   }\n"+
                "}";

        MvcResult mvcResult  = mockMvc.perform(
                //soapUI的请求controller地址
                MockMvcRequestBuilders.post(URL + "/takeOverSnc")
                        //请求类型 json 和soapUI里面的一样
                        .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        //传参,因为后端是@RequestBody所以这里直接传json字符串 soapUI里面的入参json
                        .content(json)
                // 期望的结果状态 200
        ).andExpect(MockMvcResultMatchers.status().isOk())
                //添加ResultHandler结果处理器，比如调试时 打印结果(print方法)到控制台
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Map map = JackJson.getMapByJsonString(content);
        //验证是否成功
        Assert.assertEquals(MapUtils.getString(map,"message"),200, MapUtils.getIntValue(map,"code",-1));
        //验证是否能连通厂家接口，验证callWs()是否通
        Assert.assertNotEquals(MapUtils.getString(map,"message"),400, MapUtils.getIntValue(map,"code"));
        //验证出参是否有值
        Assert.assertNotNull("返回结果为null",map);
    }
}