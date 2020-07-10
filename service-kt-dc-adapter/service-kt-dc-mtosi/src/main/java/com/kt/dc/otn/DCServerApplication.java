package com.kt.dc.otn;

import com.kt.dc.otn.adapter.impl.DcOtnAdapterServiceImpl;
import com.kt.dc.otn.bean.RequestResult;
import com.kt.dc.otn.test.ConfigTest;
import com.kt.dc.otn.util.SendHttpRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DCServerApplication {

    protected final static Logger logger = LoggerFactory.getLogger(ConfigTest.class);

    @Autowired
    DcOtnAdapterServiceImpl dcOtnAdapterService;

    public static void main(String[] args) {
        SpringApplication.run(DCServerApplication.class, args);

        logger.info("开始执行请求");
        String strPost = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<input xmlns=\"urn:chinamobile:uuid-fdn\">" +
                "<ids>" +
                "<id>b81fb48c-93e9-11ea-90cd-286ed4bd88fb</id>" +
                "</ids>" +
                "</input>";
        String urlGet = "https://129.20.62.33:26335/restconf/data/sptn-net-topology:net-topology/topologies?layer-rate=2";
        RequestResult resultGet = SendHttpRequestUtil.doGet(urlGet);
        //System.out.println("Get请求++++++++++++++" + resultGet.getResult());
        logger.info("Get请求++++++++++++++" +resultGet.getResult());

        logger.info("开始POSt执行请求");
        String urlPost = "https://129.20.62.33:26335/restconf/data/sptn-net-topology:net-topology/nodes";
        RequestResult resultPost = SendHttpRequestUtil.doPostXML(urlPost, strPost);
        //System.out.println("Post请求++++++++++++++" +resultPost.getResult());

        logger.info("Post请求++++++++++++++" +resultPost.getResult());

    }
}

