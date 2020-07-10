package com.cttnet.zhwg.ywkt.actn.annunciate.util;


import com.cttnet.common.util.SpringContextUtils;
import com.cttnet.common.util.SysConfig;
import com.cttnet.zhwg.ywkt.actn.annunciate.data.dto.ems.EmsDTO;
import org.springframework.web.client.RestTemplate;

/**
 * <p>Description: 访问其他服务接口</p>
 *
 * @author suguisen
 */
public class AccessServiceInterfaceUtil {

    public static EmsDTO getDefaultEms() {
        String url = SysConfig.getProperty("actn.defaultEms", "http://172.168.63.92:9064/service-zhwg-ywkt-service/ems/getEms/defaultEms");
        RestTemplate restTemplate = SpringContextUtils.getBean("restTemplateByUrl",RestTemplate.class);
        EmsDTO emsDto = restTemplate.getForObject(url, EmsDTO.class);
        return emsDto;
    }

    public static EmsDTO getEmsDtoByName(String emsName) {
        String url = SysConfig.getProperty("actn.emsName", "http://localhost:9536/service-zhwg-ywkt-actn/actn/macEms/getEms/emsName=");
        url += emsName;
        RestTemplate restTemplate = SpringContextUtils.getBean("restTemplateByUrl",RestTemplate.class);
        EmsDTO emsDto = restTemplate.getForObject(url, EmsDTO.class);
        return emsDto;
    }
}
