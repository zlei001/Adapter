package com.cttnet.ywkt.actn.service.ems;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cttnet.common.cache.CacheUtil;
import com.cttnet.ywkt.actn.data.dto.ems.ActnMacEmsDTO;
import com.cttnet.ywkt.actn.mapper.ems.EmsMapper;
import com.cttnet.ywkt.actn.data.po.ActnMacEmsPO;
import com.cttnet.ywkt.actn.support.converter.EmsConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: 网管 </p>
 * @author suguisen
 *
 */
@Slf4j
@Service
public class ActnMacEmsService {

    /**
     * redis存放
     */
    private static final String EMS_NAMESPACE = "ywkt-ems";
    @Value("${actn.default-ems-name:华为NCE网管}")
    private String emsName;

    @Autowired
    private EmsMapper emsMapper;
    @Autowired
    private EmsConverter emsConverter;


    /**
     * 查询网管信息
     * @param emsId
     * @return
     */
    public ActnMacEmsDTO getEmsById(String emsId) {

        CacheUtil cacheUtil = CacheUtil.getInstance(EMS_NAMESPACE);
        ActnMacEmsDTO emsPo = (ActnMacEmsDTO) cacheUtil.get(emsId);

        if (emsPo == null) {
            return getDefaultEms();
        }
        return emsPo;
    }

    /**
     * 查询网管信息
     * @param emsName
     * @return
     */
    public ActnMacEmsDTO getEmsByName(String emsName) {
        return getEmsById(emsName);
    }

    /**
     * 查询默认网管信息
     * @return
     */
    public ActnMacEmsDTO getDefaultEms() {
        CacheUtil cacheUtil = CacheUtil.getInstance(EMS_NAMESPACE);
        return (ActnMacEmsDTO) cacheUtil.get(emsName);
    }

    /**
     * 重新载入
     * @return
     */
    public int reload() {
        clear();
        return init();
    }

    public synchronized int init() {

        List<ActnMacEmsPO> emsList = emsMapper.selectList(new QueryWrapper<>());
        if (emsList == null || emsList.isEmpty()) {
            return 0;
        }

        CacheUtil cacheUtil = CacheUtil.getInstance(EMS_NAMESPACE);
        Map<String, ActnMacEmsDTO> emsIdMap = new HashMap<>();
        Map<String, ActnMacEmsDTO> emsNameMap = new HashMap<>();
        for (ActnMacEmsPO actnMacEmsPo : emsList) {

            ActnMacEmsDTO actnMacEmsDTO = emsConverter.poToDTO(actnMacEmsPo);

            actnMacEmsDTO.setBaseUrl(getBaseUrl(actnMacEmsPo.getProtocol(), actnMacEmsPo.getIp(), actnMacEmsPo.getPort()));
            //仅显示使用
            emsNameMap.put(actnMacEmsDTO.getEmsName(), actnMacEmsDTO);
            emsIdMap.put(actnMacEmsDTO.getEmsId(), actnMacEmsDTO);
            //redis
            cacheUtil.put(actnMacEmsDTO.getEmsName(), actnMacEmsDTO);
            cacheUtil.put(actnMacEmsDTO.getEmsId(), actnMacEmsDTO);

        }

        log.info("Ems数据成功加载到redis缓存,{}, {}", JSON.toJSONString(emsNameMap), JSON.toJSONString(emsIdMap));
        log.info("Ems数据成功加载到redis缓存,defaultEmsName, {}", emsName);
        return emsList.size();
    }

    /**
     * <p>Description: protocol、ip、port 转为url</p>
     * @author suguisen
     *
     */
    private String getBaseUrl(Integer protocol, String ip, Integer port) {
        StringBuilder url = new StringBuilder();
        if (protocol != null && protocol == 2) {
            url.append("https://");
        } else {
            url.append("http://");
        }
        url.append(ip).append(":").append(port);
        return url.toString();
    }


    public synchronized void clear() {
        CacheUtil cacheUtil = CacheUtil.getInstance(EMS_NAMESPACE);
        cacheUtil.removeAll();
        log.info("Ems数据成功从redis缓存移除");
    }

}
