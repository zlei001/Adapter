package com.cttnet.zhwg.ywkt.mtosi.controller.restconf;

import com.cttnet.common.util.JackJson;
import org.apache.commons.collections.MapUtils;
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
 * ConnectionController Tester.
 *
 * @author dengkaihong
 * @since 2020/4/9
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TerminationPointControllerTest {


    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    private  static  final  String URL = "/service-zhwg-ywkt-mtosi/restconf/terminationPoint";

    @Before
    public void setUp() throws Exception {
        //在每个测试方法执行之前都初始化MockMvc对象    建议使用这种
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * 设置端口属性
     * Method: setTerminationPointData(@RequestBody @Validated RequestOfSetTerminationPointData request)
     *
     */
    @Test
    public void setTerminationPointData() throws Exception {
        String json  = "{\n" +
                "  \"requestHeader\" : {\n" +
                "    \"uuid\" : \"C7B60E5D5236B2E9B1516F08CFE71C7F\",\n" +
                "    \"nonce\" : \"1518144652667\",\n" +
                "    \"sign\" : \"051C576BF53B8B43B59DC028E16EE673\",\n" +
                "    \"domain\" : \"1010001\",\n" +
                "    \"headExtensions\" : null\n" +
                "  },\n" +
                "  \"body\" : {\n" +
                "\"emsId\" : \"5EB6969BABAAC1414C95AF931DCAE464\",\n" +
                "  \"tpId\" : \"000011388DF7C5014DA0E5723131887F\",\n " +
                "  \"commonType\" : 1\n " +
                "},\n" +
                "   \"transmissionParametersList\": [{\n" +
                "      \"layerRate\": \"LR_DIGITAL_SIGNAL_RATE\",\n" +
                "  \"parameterList\" : {\n" +
                "    \"ClientServiceType\": \"10GE_WAN\",\n" +
                "      \"ClientServiceContainer\": \"ODU2\",\n" +
                "      \"ClientServiceMappingMode\": \"BMP\"\n" +
                "  }\n" +
                "   }]\n" +
                "}";

        MvcResult mvcResult  = mockMvc.perform(
                //soapUI的请求controller地址
                MockMvcRequestBuilders.post(URL + "/setTerminationPointData")
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

    /**
     * 查询端口属性
     * Method: getTerminationPoint(@RequestBody @Validated RequestOfGetTerminationPoint request)
     *
     */
    @Test
    public void getTerminationPoint() throws Exception{
        String json  = "{\n" +
                "  \"requestHeader\" : {\n" +
                "    \"uuid\" : \"C7B60E5D5236B2E9B1516F08CFE71C7F\",\n" +
                "    \"nonce\" : \"1518144652667\",\n" +
                "    \"sign\" : \"051C576BF53B8B43B59DC028E16EE673\",\n" +
                "    \"domain\" : \"1010001\",\n" +
                "    \"headExtensions\" : null\n" +
                "  },\n" +
                "  \"body\" : {\n" +
                "\"emsId\" : \"5EB6969BABAAC1414C95AF931DCAE464\",\n" +
                "  \"tpId\" : \"D693063775806F630D098F417EAD16DB\"\n " +
                "}\n" +
                "}";

        MvcResult mvcResult  = mockMvc.perform(
                //soapUI的请求controller地址
                MockMvcRequestBuilders.post(URL + "/getTerminationPoint")
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