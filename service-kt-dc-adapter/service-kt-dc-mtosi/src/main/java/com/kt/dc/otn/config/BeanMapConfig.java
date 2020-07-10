package com.kt.dc.otn.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix="dc")
@EnableConfigurationProperties(BeanMapConfig.class)
@ConditionalOnClass(BeanMapConfig.class)
public class BeanMapConfig {
    //manufactor
    /**
     * 从配置文件中读取的manufactor开头的数据
     * 注意：名称必须与配置文件中保持一致
     */
    private Map<String, String> manufactorMap = new HashMap<>();

    public Map<String, String> getManufactorMap() {
        return manufactorMap;
    }

    public void setManufactorMap(Map<String, String> manufactorMap) {
        this.manufactorMap = manufactorMap;
    }

}
