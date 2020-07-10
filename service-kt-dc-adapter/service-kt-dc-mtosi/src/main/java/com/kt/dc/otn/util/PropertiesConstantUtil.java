package com.kt.dc.otn.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
* @Description: 获取配置文件参数工具类
* @Author: Coder
* @Date: 2020-07-10 09:31
*/
@Component
public class PropertiesConstantUtil {

    public static String PROFILE;

    public static String HTTPS;

    //直接用的@value  也可以通过 environment等去获取
    @Value("${spring.profiles.active}")
    public void setPROFILE(String profile) {
        PropertiesConstantUtil.PROFILE = profile;
    }

    @Value("${dc.otn.https.url}")
    public void setHTTPS(String url) {
        PropertiesConstantUtil.HTTPS = url;
    }

}

