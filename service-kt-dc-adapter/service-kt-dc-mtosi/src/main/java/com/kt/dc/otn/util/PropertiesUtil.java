package com.kt.dc.otn.util;

import com.kt.dc.otn.handle.PropertiesLoader;

import java.util.HashMap;
import java.util.Map;

/**
* @Description:   读取配置文件
* @Author: Coder
* @Date: 2020-07-10 09:32
*/
public class PropertiesUtil {

    private static Map<String, String> map = new HashMap();
    private static PropertiesLoader loader = new PropertiesLoader(new String[]{"application.yml", "application_nceSptn.yml", "application-" + PropertiesConstantUtil.PROFILE + ".yml"});

    /**
    * @Description:  根据配置的key获取属性值
    * @Param:  String
    * @return:  String
    * @Author: Coder
    * @Date: 2020-07-10 09:32
    */
    public static String getConfig(String key) {
        String value = map.get(key);
        if (value == null) {
            value = loader.getProperty(key);
            map.put(key, value != null ? value : "");
        }
        return value;
    }

    public static Boolean isDebug() {
        String debug = getConfig("web.debug");
        return "true".equals(debug);
    }
}

