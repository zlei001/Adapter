package com.cttnet.zhwg.ywkt.mtosi.controller.restconf;

import com.cttnet.common.util.JackJson;
import org.apache.commons.collections.MapUtils;
import org.junit.*;
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
 * ProtectionController Tester.
 *
 * @author dengkaihong
 * @since 2020/4/15
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProtectionControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    private  static  final  String URL  = "/service-zhwg-ywkt-mtosi/restconf/protection";

    @Before
    public void before() throws Exception {
        //在每个测试方法执行之前都初始化MockMvc对象    建议使用这种
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAllProtectionGroups() throws Exception {

        String json  = "{\n" +
                "  \"header\" : {\n" +
                "    \"uuid\" : \"C7B60E5D5236B2E9B1516F08CFE71C7F\",\n" +
                "    \"nonce\" : \"1585367122276\",\n" +
                "    \"sign\" : \"7FDC2A3AA674720F0A02C7C2A8DCD4AF\",\n" +
                "    \"domain\" : \"1010001\",\n" +
                "    \"headExtensions\" : null\n" +
                "  },\n" +
                "  \"body\" : {\n" +
                "\"emsId\" : \"9EF0B1BD2C3523F4C2DFBC0F69A9AABD\",\n" +
                "  \"tpId\" : \"3A39A10C9FAE6DA01073F9E6805D5B7E\"\n " +
                "}" +
                "}";

        MvcResult mvcResult  = mockMvc.perform(
                //soapUI的请求controller地址
                MockMvcRequestBuilders.post(URL + "/getAllProtectionGroups")
                        //请求类型 json 和soapUI里面的一样
                        .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        //传参,因为后端是@RequestBody所以这里直接传json字符串 soapUI里面的入参json
                        .content(json)
                // 期望的结果状态 200  200 意味着能去到controller 404则是不能去到
        ).andExpect(MockMvcResultMatchers.status().isOk())
                //添加ResultHandler结果处理器，比如调试时 打印结果(print方法)到控制台
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Map map = JackJson.getMapByJsonString(content);
        //验证是否成功    2. 如何验证 createKvs()出来的参数是对的 延后处理
        Assert.assertEquals(MapUtils.getString(map,"message"),200, MapUtils.getIntValue(map,"code",-1));
        //验证是否能连通厂家接口，验证callWs()是否通
        Assert.assertNotEquals(MapUtils.getString(map,"message"),400,MapUtils.getIntValue(map,"code"));
        //验证出参是否有值
        Assert.assertNotNull("返回结果为null",map);
    }

    @Test
    public void modifyProtectionGroup() throws Exception {
        //TODO  解析报文返回DATA值为null，延后处理
        String json  = "{\n" +
                "  \"header\" : {\n" +
                "    \"uuid\" : \"C7B60E5D5236B2E9B1516F08CFE71C7F\",\n" +
                "    \"nonce\" : \"1585367122276\",\n" +
                "    \"sign\" : \"7FDC2A3AA674720F0A02C7C2A8DCD4AF\",\n" +
                "    \"domain\" : \"1010001\",\n" +
                "    \"headExtensions\" : null\n" +
                "  },\n" +
                "  \"body\" : {\n" +
                "\"emsId\" : \"9EF0B1BD2C3523F4C2DFBC0F69A9AABD\",\n" +
                "  \"tpId\" : \"23B73CE910D901F2BF912FF0D98D0A56\",\n " +
                " \"parameter\" : { \n" +
                " \"wtrTime\"  : \"ERERWE\",\n" +
                " \"holdOffTime\"  : \"ERERWE\",\n" +
                " \"springNodeId\"  : \"ERERWE\"\n" +
                "}\n" +
                "}\n" +
                "}";

        MvcResult mvcResult  = mockMvc.perform(
                //soapUI的请求controller地址
                MockMvcRequestBuilders.post(URL + "/modifyProtectionGroup")
                        //请求类型 json 和soapUI里面的一样
                        .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        //传参,因为后端是@RequestBody所以这里直接传json字符串 soapUI里面的入参json
                        .content(json)
                // 期望的结果状态 200  200 意味着能去到controller 404则是不能去到
        ).andExpect(MockMvcResultMatchers.status().isOk())
                //添加ResultHandler结果处理器，比如调试时 打印结果(print方法)到控制台
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Map map = JackJson.getMapByJsonString(content);
        //验证是否成功    2. 如何验证 createKvs()出来的参数是对的 延后处理
        Assert.assertEquals(MapUtils.getString(map,"message"),200, MapUtils.getIntValue(map,"code",-1));
        //验证是否能连通厂家接口，验证callWs()是否通
        Assert.assertNotEquals(MapUtils.getString(map,"message"),400,MapUtils.getIntValue(map,"code"));
        //验证出参是否有值
        Assert.assertNotNull("返回结果为null",map);
    }
    @Test
    public void retrieveSwitchData() throws Exception {
        String json  = "{\n" +
                "  \"header\" : {\n" +
                "    \"uuid\" : \"C7B60E5D5236B2E9B1516F08CFE71C7F\",\n" +
                "    \"nonce\" : \"1585367122276\",\n" +
                "    \"sign\" : \"7FDC2A3AA674720F0A02C7C2A8DCD4AF\",\n" +
                "    \"domain\" : \"1010001\",\n" +
                "    \"headExtensions\" : null\n" +
                "  },\n" +
                "  \"body\" : {\n" +
                "\"emsId\" : \"5EB6969BABAAC1414C95AF931DCAE464\",\n" +
                "  \"tpId\" : \"2BAF979FC9868D8DD16D0BA6E2E6D1CE\"\n " +
                "}\n" +
                "}";

        MvcResult mvcResult  = mockMvc.perform(
                //soapUI的请求controller地址
                MockMvcRequestBuilders.post(URL + "/retrieveSwitchData")
                        //请求类型 json 和soapUI里面的一样
                        .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        //传参,因为后端是@RequestBody所以这里直接传json字符串 soapUI里面的入参json
                        .content(json)
                // 期望的结果状态 200  200 意味着能去到controller 404则是不能去到
        ).andExpect(MockMvcResultMatchers.status().isOk())
                //添加ResultHandler结果处理器，比如调试时 打印结果(print方法)到控制台
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Map map = JackJson.getMapByJsonString(content);
        //验证是否成功    2. 如何验证 createKvs()出来的参数是对的 延后处理
        Assert.assertEquals(MapUtils.getString(map,"message"),200, MapUtils.getIntValue(map,"code",-1));
        //验证是否能连通厂家接口，验证callWs()是否通
        Assert.assertNotEquals(MapUtils.getString(map,"message"),400,MapUtils.getIntValue(map,"code"));
        //验证出参是否有值
        Assert.assertNotNull("返回结果为null",map);
    }

    //TODO  以下两个暂无响应报文，延后处理

    @Test
    @Ignore
    public void createProtectionGroup() {
    }

    @Test
    @Ignore
    public void deleteProtectionGroup() {


    }
}