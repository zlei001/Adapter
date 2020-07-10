package com.cttnet.zhwg.ywkt.mtosi.controller.restconf;

import com.cttnet.common.util.JackJson;
import com.cttnet.zhwg.ywkt.mtosi.data.dto.base.RequestHeader;
import com.cttnet.zhwg.ywkt.mtosi.data.dto.request.RequestOfDisablePRBSTest;
import com.cttnet.zhwg.ywkt.mtosi.service.restconf.MaintenanceService;
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
 * ConnectionController Tester.
 *
 * @author dengkaihong
 * @since 2020/4/8
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MaintenanceControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Autowired
    private MaintenanceService service ;

    private  static  final  String URL = "/service-zhwg-ywkt-mtosi/restconf/maintenance";
    @Before
    public void setUp() throws Exception {
        //在每个测试方法执行之前都初始化MockMvc对象    建议使用这种
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void tearDown() throws Exception {

    }
    /**
     * 取消PBRS测试 单元测试
     * Method: disablePRBSTest(@RequestBody @Validated RequestOfDisablePRBSTest request)
     *
     */
    @Test
    public void testDisablePRBSTest() throws Exception {

        RequestHeader header = new RequestHeader();
        RequestOfDisablePRBSTest.RequestBody body = new RequestOfDisablePRBSTest.RequestBody();

        header.setDomain("1010001");
        header.setHeadExtensions("null");
        header.setNonce("1518141626913");
        header.setSign("5EB6969BABAAC1414C95AF931DCAE464");
        header.setUuid("4f2dd5c6-1e41-4c13-a134-72db2681db9b");

        body.setEmsId("5EB6969BABAAC1414C95AF931DCAE464");
        body.setTpId("82D59A7C3D4966564323F7801DF3C9B2");

        RequestOfDisablePRBSTest request  = new RequestOfDisablePRBSTest();
        request.setHeader(header);
        request.setBody(body);

        MvcResult mvcResult  = mockMvc.perform(
                //soapUI的请求controller地址
                MockMvcRequestBuilders.post(URL + "/disablePRBSTest")
                        //请求类型 json 和soapUI里面的一样
                        .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        //传参,因为后端是@RequestBody所以这里直接传json字符串 soapUI里面的入参json
                        .content(JackJson.getBasetJsonData(request))
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
     * 启动PBRS测试 单元测试
     * Method: enablePRBSTest(@RequestBody @Validated RequestOfEnablePRBSTest request)
     *
     */
    @Test
    public void testEnablePRBSTest() throws Exception {
        String json  = "{\n" +
                "   \"requestHeader\":    {\n" +
                "      \"uuid\": \"2ecd438d-e73b-4eb8-acc4-89095fab41eb\",\n" +
                "      \"nonce\": \"1518141680866\",\n" +
                "      \"sign\": \"9A402CF0D643C95074ED47ADEF1F9000\",\n" +
                "      \"domain\": \"1010001\",\n" +
                "      \"headExtensions\": null\n" +
                "   },\n" +
                "   \"body\":    {\n" +
                "   \"emsId\": \"5EB6969BABAAC1414C95AF931DCAE464\",\n" +
                "   \"tpId\": \"82D59A7C3D4966564323F7801DF3C9B2\",\n" +
                "   \"direction\": \"Inward\",\n" +
                "   \"prbsType\": \"PRBS31\",\n" +
                "   \"testDuration\": 600,\n" +
                "   \"sampleGranularity\": 60,\n" +
                "   \"accumulatingIndicator\": false\n" +
                "}"+
                "}";

        MvcResult mvcResult  = mockMvc.perform(
                //soapUI的请求controller地址
                MockMvcRequestBuilders.post(URL + "/enablePRBSTest")
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
     * 获取PBRS测试结果 单元测试
     * Method: getPRBSTestResult(@RequestBody @Validated RequestOfGetPRBSTestResult request)
     *
     */
    @Test
    public void testGetPRBSTestResult() throws  Exception{

        RequestHeader header = new RequestHeader();
        RequestOfDisablePRBSTest.RequestBody body = new RequestOfDisablePRBSTest.RequestBody();

        header.setDomain("1010001");
        header.setHeadExtensions("null");
        header.setNonce("1518141626913");
        header.setSign("5EB6969BABAAC1414C95AF931DCAE464");
        header.setUuid("4f2dd5c6-1e41-4c13-a134-72db2681db9b");

        body.setEmsId("5EB6969BABAAC1414C95AF931DCAE464");
        body.setTpId("82D59A7C3D4966564323F7801DF3C9B2");

        RequestOfDisablePRBSTest request  = new RequestOfDisablePRBSTest();
        request.setHeader(header);
        request.setBody(body);

        MvcResult mvcResult  = mockMvc.perform(
                //soapUI的请求controller地址
                MockMvcRequestBuilders.post(URL + "/getPRBSTestResult")
                        //请求类型 json 和soapUI里面的一样
                        .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        //传参,因为后端是@RequestBody所以这里直接传json字符串 soapUI里面的入参json
                        .content(JackJson.getBasetJsonData(request))
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
     * 查询OTN业务时延 单元测试
     * Method: getRoundTripDelayResult(@RequestBody @Validated RequestOfGetRoundTripDelayResult request)
     *
     */

    @Test
    public void testGetRoundTripDelayResult() throws Exception {
        //TODO  解析报文返回delay值为null，延后处理
        String json = "{\n" +
                "   \"requestHeader\":    {\n" +
                "      \"uuid\": \"d32b5702-ee77-46e9-a7ea-d29483fa203b\",\n" +
                "      \"nonce\": \"1518143576952\",\n" +
                "      \"sign\": \"1D4E089F0C66F7C9899A8FD8C99B2EC0\",\n" +
                "      \"domain\": \"1010001\",\n" +
                "      \"headExtensions\": null\n" +
                "   },\n" +
                "   \"body\":    {\n" +
                "   \"emsId\": \"9EF0B1BD2C3523F4C2DFBC0F69A9AABD\",\n" +
                "   \"tpId\": \"82D59A7C3D4966564323F7801DF3C9B2\",\n" +
                "   \"type\": \"PM\"\n" +
                "}"+
                "}";
        MvcResult mvcResult  = mockMvc.perform(
                //soapUI的请求controller地址
                MockMvcRequestBuilders.post(URL + "/getRoundTripDelayResult")
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
     * 配置OTN业务时延 单元测试
     * Method: measureRoundTripDelay(@RequestBody @Validated RequestOfMeasureRoundTripDelay request)
     *
     */
    @Test
    public void testMeasureRoundTripDelay() throws Exception {
        String json = "{\n" +
                "   \"requestHeader\":    {\n" +
                "      \"uuid\": \"d32b5702-ee77-46e9-a7ea-d29483fa203b\",\n" +
                "      \"nonce\": \"1518143576952\",\n" +
                "      \"sign\": \"1D4E089F0C66F7C9899A8FD8C99B2EC0\",\n" +
                "      \"domain\": \"1010001\",\n" +
                "      \"headExtensions\": null\n" +
                "   },\n" +
                "   \"body\":    {\n" +
                "   \"emsId\": \"5EB6969BABAAC1414C95AF931DCAE464\",\n" +
                "   \"tpId\": \"FF58543710AB71D16FA6707C374A4022\",\n" +
                "   \"role\": \"Starting\",\n" +
                "   \"type\": \"PM\"\n" +
                "}"+
                "}";
        MvcResult mvcResult  = mockMvc.perform(
                //soapUI的请求controller地址
                MockMvcRequestBuilders.post(URL + "/measureRoundTripDelay")
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
     *端口环回
     */
    @Test
    public void testOperationLoopback() throws Exception {
        String json  = "{\n" +
                "  \"requestHeader\" : {\n" +
                "    \"uuid\" : \"C7B60E5D5236B2E9B1516F08CFE71C7F\",\n" +
                "    \"nonce\" : \"1585367122276\",\n" +
                "    \"sign\" : \"7FDC2A3AA674720F0A02C7C2A8DCD4AF\",\n" +
                "    \"domain\" : \"1010001\",\n" +
                "    \"headExtensions\" : null\n" +
                "  },\n" +
                "  \"body\" : {\n" +
                "\"emsId\" : \"5EB6969BABAAC1414C95AF931DCAE464\",\n" +
                "  \"tpId\" : \"D693063775806F630D098F417EAD16DB\",\n " +
                "  \"maintenanceOperationMode\" : \"MOM_RELEASE\",\n " +
                "  \"maintenanceOperation\" : \"FACILITY_LOOPBACK\",\n " +
                "  \"layerRate\" : \"LR_OCH_Data_Unit_0\"\n " +
                "}" +
                "}";
        MvcResult  mvcResult  = mockMvc.perform(
                //soapUI的请求controller地址
                MockMvcRequestBuilders.post(URL+ "/operationLoopback")
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
     *打开关闭端口激光器
     */
    @Test
    public void testOperationLaserSwitchOff() throws Exception {
        String json  = "{\n" +
                "  \"requestHeader\" : {\n" +
                "    \"uuid\" : \"C7B60E5D5236B2E9B1516F08CFE71C7F\",\n" +
                "    \"nonce\" : \"1585367122276\",\n" +
                "    \"sign\" : \"7FDC2A3AA674720F0A02C7C2A8DCD4AF\",\n" +
                "    \"domain\" : \"1010001\",\n" +
                "    \"headExtensions\" : null\n" +
                "  },\n" +
                "  \"body\" : {\n" +
                "\"emsId\" : \"5EB6969BABAAC1414C95AF931DCAE464\",\n" +
                "  \"tpId\" : \"D693063775806F630D098F417EAD16DB\",\n " +
                "  \"maintenanceOperationMode\" : \"MOM_RELEASE\",\n " +
                "  \"maintenanceOperation\" : \"Laser_Switch_On\",\n " +
                "  \"layerRate\" : \"LR_OCH_Data_Unit_0\"\n " +
                "}" +
                "}";
        MvcResult  mvcResult  = mockMvc.perform(
                //soapUI的请求controller地址
                MockMvcRequestBuilders.post(URL+ "/operationLaserSwitchOff")
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
     *查询网元上所有端口维护操作状态,
     */
    @Test
    public void testGetActiveMaintenanceOperations() throws Exception {
        String json  = "{\n" +
                "  \"requestHeader\" : {\n" +
                "    \"uuid\" : \"C7B60E5D5236B2E9B1516F08CFE71C7F\",\n" +
                "    \"nonce\" : \"1585367122276\",\n" +
                "    \"sign\" : \"7FDC2A3AA674720F0A02C7C2A8DCD4AF\",\n" +
                "    \"domain\" : \"1010001\",\n" +
                "    \"headExtensions\" : null\n" +
                "  },\n" +
                "  \"body\" : {\n" +
                "\"emsId\" : \"5EB6969BABAAC1414C95AF931DCAE464\",\n" +
                "  \"tpId\" : \"D693063775806F630D098F417EAD16DB\",\n " +
                "  \"commonType\" : 1\n " +
                "}" +
                "}";
        MvcResult  mvcResult  = mockMvc.perform(
                //soapUI的请求controller地址
                MockMvcRequestBuilders.post(URL+ "/getActiveMaintenanceOperations")
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
     *单板复位
     */
    @Test
    public void testOperationCardReset() throws Exception {
        String json  = "{\n" +
                "  \"requestHeader\" : {\n" +
                "    \"uuid\" : \"C7B60E5D5236B2E9B1516F08CFE71C7F\",\n" +
                "    \"nonce\" : \"1585367122276\",\n" +
                "    \"sign\" : \"7FDC2A3AA674720F0A02C7C2A8DCD4AF\",\n" +
                "    \"domain\" : \"1010001\",\n" +
                "    \"headExtensions\" : null\n" +
                "  },\n" +
                "  \"body\" : {\n" +
                "\"emsId\" : \"5EB6969BABAAC1414C95AF931DCAE464\",\n" +
                "  \"tpId\" : \"DD3642A8AF90C4B82A14735489442F99\",\n " +
                "  \"maintenanceOperationMode\" : \"MOM_OPERATE\",\n " +
                "  \"maintenanceOperation\" : \"WarmReset\",\n " +
                "  \"layerRate\" : \"LR_OCH_Data_Unit_0\"\n " +
                "}" +
                "}";
        MvcResult  mvcResult  = mockMvc.perform(
                //soapUI的请求controller地址
                MockMvcRequestBuilders.post(URL+ "/operationCardReset")
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