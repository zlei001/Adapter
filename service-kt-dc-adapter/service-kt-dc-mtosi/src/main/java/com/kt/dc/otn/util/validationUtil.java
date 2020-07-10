package com.kt.dc.otn.util;

import com.alibaba.fastjson.JSON;
import com.kt.dc.otn.config.BeanMapConfig;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @Description Description
 * @Author Coder
 * @Date Created in 2020/7/9 0009
 */
public class validationUtil {
    /**
    * @Description: 验证参数
    * @Param:  param
    * @return: Boolean
    * @Author: Coder
    * @Date: 2020-07-09 19:34
    */
    public static Boolean checkParam(String param){
        if (StringUtils.isBlank(param)){
            return true;
        }
        Map maps = (Map) JSON.parse(param);
        if (maps.size() <= 0){
            return true;
        }

        if (StringUtils.isBlank(ObjectUtils.toString(maps.get("flag"))) ||
                StringUtils.isBlank(ObjectUtils.toString(maps.get("methodName")))){
            return true;
        }

        return false;
    }
}
