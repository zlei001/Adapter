package com.cttnet.ywkt.actn.util;

import com.cttnet.common.util.SpringContextUtils;
import com.cttnet.common.util.SysConfig;
import com.cttnet.ywkt.actn.data.dto.actnNode.ActnNodeDTO;
import com.cttnet.ywkt.actn.data.dto.actnNode.ActnNodeListDTO;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * <p>Description: 访问其他服务接口</p>
 *
 * @author suguisen
 */
public class AccessServiceInterfaceUtil {


    /**
     * <p>Description: 查询ActnIds对象</p>
     * @author suguisen
     *
     */
    public static ActnNodeListDTO getActnIdsByMtosiIds(List<ActnNodeDTO> actnNodesDto) {
        String url = SysConfig.getProperty("ywkt.actn.url.getactnidsbymtosiids", "http://172.168.63.92:8899/wgjk-actn-collect/mtosi/getACTNIDsByMTOSIIDs");
        RestTemplate restTemplate = SpringContextUtils.getBean("restTemplateByUrl",RestTemplate.class);
        ActnNodeListDTO actnIdsByMtosiIds = restTemplate.postForObject(url, actnNodesDto, ActnNodeListDTO.class);
        return  actnIdsByMtosiIds;
    }
}
