package com.cttnet.zhwg.ywkt.mtosi.controller.restconf;

import com.cttnet.common.util.JackJson;
import com.cttnet.zhwg.ywkt.mtosi.service.restconf.ConnectionService;
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
public class ConnectionControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Autowired
    private ConnectionService service;

    private  static  final  String URL  = "/service-zhwg-ywkt-mtosi/restconf/connection";

    @Before
    public void before() throws Exception {
        //在每个测试方法执行之前都初始化MockMvc对象    建议使用这种
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void after() throws Exception {

    }

    /**
    * 创建激活交叉连接单元测试
    * Method: createAndActivateCrossConnection(@RequestBody @Validated RequestOfCreateAndActivateCrossConnection request)
    *
    */
    @Test
    public void testCreateAndActivateCrossConnection() throws Exception {

        String json = "{\n" +
                "   \"requestHeader\":    {\n" +
                "      \"uuid\": \"C7B60E5D5236B2E9B1516F08CFE71C7F\",\n" +
                "      \"nonce\": \"1518144652667\",\n" +
                "      \"sign\": \"051C576BF53B8B43B59DC028E16EE673\",\n" +
                "      \"domain\": \"1010001\",\n" +
                "      \"headExtensions\": null\n" +
                "   },\n" +
                "   \"crossConnections\": [   {\n" +
                "      \"emsId\": \"9EF0B1BD2C3523F4C2DFBC0F69A9AABD\",\n" +
                "      \"isActive\": \"true\",\n" +
                "      \"direction\": 1,\n" +
                "      \"ccType\": 0,\n" +
                "      \"sourceEndRefIdList\": [\"82D59A7C3D4966564323F7801DF3C9B2\"],\n" +
                "      \"destEndRefIdList\": [\"597B2CFE98C3C4407B74AFEC7E9A6D4C\"],\n" +
                "      \"sourceEndRefTimeSlot\": [\"25-32\"],\n" +
                "      \"destEndRefTimeSlot\": [\"25-32\"],\n" +
                "      \"sncId\": \"\",\n" +
                "      \"layerRate\": \"\",\n" +
                "      \"clientServiceType\": \"STM-64\",\n" +
                "      \"clientServiceContainer\": \"ODU2\",\n" +
                "      \"clientServiceMappingMode\": \"BMP\",\n" +
                "      \"holdOffTime\": \"\",\n" +
                "      \"wtrTime\": \"\",\n" +
                "      \"monitorType\": \"\",\n" +
                "      \"reversionMode\": \"UNKNOWN\",\n" +
                "      \"protectionType\": \"\",\n" +
                "      \"aliasNameList\": \"广州肇庆W-64N0009IP（调整）\",\n" +
                "      \"sncpNodeList\": null,\n" +
                "      \"frequency\": null,\n" +
                "      \"pgId\": null,\n" +
                "      \"isFixed\": false\n" +
                "   }]\n" +
                "}";

          /*  String json = "{\"crossConnections\":" +
                    "[{\"ccType\":2,\"" +
                    "clientServiceContainer\":\"ODU0\"," +
                    "\"clientServiceMappingMode\":\"GMP-TTT\"," +
                    "\"clientServiceType\":\"客户层GE业务\"," +
                    "\"destEndRefIdList\":[\"6A1D2D10C8DF23B983C19997A06CB495\"]," +
                    "\"destEndRefTimeSlot\":[\"1\"]," +
                    "\"direction\":2,\"emsId\":\"9EF0B1BD2C3523F4C2DFBC0F69A9AABD\"," +
                    "\"holdOffTime\":\"\",\"monitorType\":\"\"," +
                    "\"protectionType\":\"\",\"reversionMode\":\"\"," +
                    "\"sourceEndRefIdList\":[\"48C785BFAE53D671D3FEA3FAA3F87A41\"]," +
                    "\"sourceEndRefTimeSlot\":[\"1\"],\"wtrTime\":\"\"}]," +
                    "\"header\":{\"domain\":\"1010001\"," +
                    "\"headExtensions\":\"\"," +
                    "\"nonce\":\"1518144652667\"," +
                    "\"sign\":\"051C576BF53B8B43B59DC028E16EE673\"," +
                    "\"uuid\":\"C7B60E5D5236B2E9B1516F08CFE71C7F\"}}";*/


        //perform,执行一个RequestBuilders请求，会自动执行SpringMVC的流程并映射到相应的控制器执行处理
        MvcResult  mvcResult  = mockMvc.perform(
                //soapUI的请求controller地址
                MockMvcRequestBuilders.post(URL+ "/createAndActivateCrossConnection")
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
    *创建激活子网连接单元测试
    * Method: createAndActivateSubnetWorkConnection(@RequestBody @Validated RequestOfCreateAndActiveSubnetWorkConnection request)
    *
    */
    @Test
    public void testCreateAndActivateSubnetWorkConnection() throws Exception {
         String json = "{\n" +
                 "   \"requestHeader\":    {\n" +
                 "      \"uuid\": \"C7B60E5D5236B2E9B1516F08CFE71C7F\",\n" +
                 "      \"nonce\": \"1570689747561\",\n" +
                 "      \"sign\": \"A90BA8F55E671E4806CE26F2D291B888\",\n" +
                 "      \"domain\": \"1010001\",\n" +
                 "      \"headExtensions\": null\n" +
                 "   },\n" +
                 "   \"subnetWorkConnection\": {\n" +
                 "      \"emsId\": \"5EB6969BABAAC1414C95AF931DCAE464\",\n" +
                 "      \"isActive\": null,\n" +
                 "      \"direction\": 1,\n" +
                 "      \"ccType\": 0,\n" +
                 "      \"sourceEndRefIdList\": [\"EBBBCBA6008AF775AAF73EAA714EE0C9\"],\n" +
                 "      \"destEndRefIdList\": [\"F40F8696236C4178E339F28BF77F1ABE\"],\n" +
                 "      \"clientServiceContainer\": \"ODU2\",\n" +
                 "      \"clientServiceMappingMode\": \"GMP\",\n" +
                 "      \"clientServiceType\": \"10GE_WAN\",\n" +
                 "      \"sncId\": null,\n" +
                 "      \"layerRate\": \"LR_DIGITAL_SIGNAL_RATE\",\n" +
                 "      \"aliasNameList\": \"test101009\",\n" +
                 "      \"userLabel\": \"test101009\"\n" +
                 "   }\n" +
                 "}";

        MvcResult  mvcResult  = mockMvc.perform(
                //soapUI的请求controller地址
                MockMvcRequestBuilders.post( URL+ "/createAndActivateSubnetWorkConnection")
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
    *去激活删除交叉连接单元测试
    * Method: deactivateAndDeleteCrossConnection(@RequestBody @Validated RequestOfDeactivateAndDeleteCrossConnection request)
    *
    */
    @Test
    public void testDeactivateAndDeleteCrossConnection() throws Exception {

        String json = "{\n" +
                "   \"requestHeader\":    {\n" +
                "      \"uuid\": \"C7B60E5D5236B2E9B1516F08CFE71C7F\",\n" +
                "      \"nonce\": \"1518144652667\",\n" +
                "      \"sign\": \"051C576BF53B8B43B59DC028E16EE673\",\n" +
                "      \"domain\": \"1010001\",\n" +
                "      \"headExtensions\": null\n" +
                "   },\n" +
                "   \"crossConnections\": [   {\n" +
                "      \"emsId\": \"9EF0B1BD2C3523F4C2DFBC0F69A9AABD\",\n" +
                "      \"isActive\": \"true\",\n" +
                "      \"direction\": 1,\n" +
                "      \"ccType\": 0,\n" +
                "      \"sourceEndRefIdList\": [\"82D59A7C3D4966564323F7801DF3C9B2\"],\n" +
                "      \"destEndRefIdList\": [\"597B2CFE98C3C4407B74AFEC7E9A6D4C\"],\n" +
                "      \"sourceEndRefTimeSlot\": [\"25-32\"],\n" +
                "      \"destEndRefTimeSlot\": [\"25-32\"],\n" +
                "      \"sncId\": \"\",\n" +
                "      \"layerRate\": \"\",\n" +
                "      \"clientServiceType\": \"STM-64\",\n" +
                "      \"clientServiceContainer\": \"ODU2\",\n" +
                "      \"clientServiceMappingMode\": \"BMP\",\n" +
                "      \"holdOffTime\": \"\",\n" +
                "      \"wtrTime\": \"\",\n" +
                "      \"monitorType\": \"\",\n" +
                "      \"reversionMode\": \"UNKNOWN\",\n" +
                "      \"protectionType\": \"\",\n" +
                "      \"aliasNameList\": \"广州肇庆W-64N0009IP（调整）\",\n" +
                "      \"sncpNodeList\": null,\n" +
                "      \"frequency\": null,\n" +
                "      \"pgId\": null,\n" +
                "      \"isFixed\": false\n" +
                "   }]\n" +
                "}";
        MvcResult  mvcResult  = mockMvc.perform(
                //soapUI的请求controller地址
                MockMvcRequestBuilders.post(URL+ "/deactivateAndDeleteCrossConnection")
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
    *去激活删除子网连接单元测试
    * Method: deactivateAndDeleteSubnetWorkConnection(@RequestBody @Validated RequestOfDeactivateAndDeleteSubnetworkConnection request)
    *
    */
    @Test
    public void testDeactivateAndDeleteSubnetWorkConnection() throws Exception {
        String json = "{\n" +
                "   \"requestHeader\":    {\n" +
                "      \"uuid\": \"C7B60E5D5236B2E9B1516F08CFE71C7F\",\n" +
                "      \"nonce\": \"1570689747561\",\n" +
                "      \"sign\": \"A90BA8F55E671E4806CE26F2D291B888\",\n" +
                "      \"domain\": \"1010001\",\n" +
                "      \"headExtensions\": null\n" +
                "   },\n" +
                "   \"body\":{\n" +
                "      \"emsId\": \"5EB6969BABAAC1414C95AF931DCAE464\",\n" +
                "      \"channelId\": 5012000117\n" +
                "   }\n" +
                "}";


        MvcResult  mvcResult  = mockMvc.perform(
                //soapUI的请求controller地址
                MockMvcRequestBuilders.post(URL+ "/deactivateAndDeleteSubnetWorkConnection")
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
