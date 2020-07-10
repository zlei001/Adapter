package com.kt.dc.otn.test;


import com.kt.dc.otn.bean.RequestResult;
import com.kt.dc.otn.config.BeanMapConfig;
import com.kt.dc.otn.adapter.impl.DcOtnAdapterServiceImpl;
import com.kt.dc.otn.service.impl.DcOtnServiceImpl;
import com.kt.dc.otn.util.PropertiesConstantUtil;
import com.kt.dc.otn.util.PropertiesUtil;
import com.kt.dc.otn.util.SendHttpRequestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author eknows
 * @version 1.0
 * @since 2019/2/13 9:28
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ConfigTest {

    protected final Logger logger = LoggerFactory.getLogger(ConfigTest.class);
    @Autowired
    private BeanMapConfig beanMapConfig;

    @Autowired
    private DcOtnAdapterServiceImpl dcOtnAdapterService;

    @Autowired
    DcOtnServiceImpl dcOtnService;

    @Test
    public void testMapConfig() {
//        Map<String, String> manufactorMap = beanMapConfig.getManufactorMap();
//        if (manufactorMap == null || manufactorMap.size() <= 0) {
//            System.out.println("limitSizeMap读取失败");
//        } else {
//            System.out.println("limitSizeMap读取成功，数据如下：");
//            for (String key : manufactorMap.keySet()) {
//                System.out.println("key: " + key + ", value: " + manufactorMap.get(key));
//            }
//        }
//sayHello
//        System.out.println("------");
//        dcOtnAdapterService.oTnAdapter("otn_h","getPathCompute", "nihao");
//        System.out.println("sssssssssssssss" + PropertiesUtil.getConfig("ceshi"));
//
//        RequestResult str = SendHttpRequestUtil.doGet("http://localhost:8761/baise/info", null);
//        System.out.println("请求 ==GET===》 " + str.getMessage()+"========>"+str.getResult());
//        String str1 = HttpClientUtil.httpPostRaw("http://localhost:8761/baise/xmlStr", "nihao", null);
//        System.out.println("请求 ===POST==》 " + str1);


        //dcOtnAdapterService.oTnAdapter("otn_h", "tm_get_point_topo", "ceshi");
        System.out.println( beanMapConfig.getManufactorMap().get("urlKey"));
        String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
                "<Transfer attribute=\"Connect\">\n" +
                "<ext id=\"20013\"/>\n" +
                "<outer to=\"15505510628\"/>\n" +
                "</Transfer>";
        String url = "https://localhost:8762/baise/xmlStrInput";

        RequestResult result = SendHttpRequestUtil.doPostXML(url, xmlStr);
        System.out.println(result.getResult());
//        System.out.println("aaaaaaaaaaa" + PropertiesUtil.getConfig("ceshi"));
//        System.out.println("sssssssssssssss" + PropertiesUtil.getConfig("tm_get_topo"));

//        String urlss =  PropertiesConstantUtil.HTTPS + "/ceshi/sss/ccss";
//        System.out.println(urlss);

//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        HttpPost post = new HttpPost(url);
//        CloseableHttpResponse httpResponse = null;
//        try {
//            post.setHeader("Content-type", "text/xml");
//            HttpEntity entity = new StringEntity(strxml);
//            post.setEntity(entity);
//            httpResponse = httpClient.execute(post);
//            System.out.println(httpResponse);
//        } catch (Exception e) {
//
//
//        }
    }
}

