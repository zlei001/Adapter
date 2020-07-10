package com.cttnet.zhwg.ywkt.mtosi.cache;

import com.cttnet.common.cache.CacheUtil;
import com.cttnet.common.util.SpringContextUtils;
import com.cttnet.zhwg.ywkt.mtosi.ability.constants.Splits;
import com.cttnet.zhwg.ywkt.mtosi.data.po.MacEmsPO;
import com.cttnet.zhwg.ywkt.mtosi.service.web.MacEmsService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * <pre>
 *  MacEms数据
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/1/11
 * @since java 1.8
 */
@Slf4j
public class MacEmsData {

    /** 缓存命名空间 */
    private static final String NAMESPACE = "service-zhwg-ywkt-mtosi-macEms";

    /**
     * 通过网管ID判断EMS是否配置
     * @param emsId emsId
     * @param macType 接口类型
     * @return true/false
     */
    public static boolean isExistById(String emsId, int macType) {

        return getEms(emsId, macType) != null;
    }

    /**
     * 通过EMSID获取网管配置信息
     * @param emsId emsId
     * @param emsId macType
     * @return EmsInfo
     */
    public static MacEmsPO getEms(String emsId, int macType) {

        CacheUtil cache = CacheUtil.getInstance(NAMESPACE);
        String key = emsId + Splits.SHARP + macType;
        return (MacEmsPO) cache.get(key);
    }



    public static int init() {
        MacEmsService service = SpringContextUtils.getBean(MacEmsService.class);
        List<MacEmsPO> list = service.list(null);
        for (MacEmsPO macEms: list) {
            add(macEms);
        }
        return list.size();
    }


    public synchronized static int reload() {
        clear();
        return init();
    }


    public static void add(MacEmsPO maEms) {
        CacheUtil cache = CacheUtil.getInstance(NAMESPACE);
        String key = maEms.getEmsId() + Splits.SHARP + maEms.getMacType();
        cache.put(key, maEms);
    }

    public static void delete(String emsId, int macType) {
        CacheUtil cache = CacheUtil.getInstance(NAMESPACE);
        String key = emsId + Splits.SHARP + macType;
        cache.remove(key);
    }

    private static void clear() {
        CacheUtil cache = CacheUtil.getInstance(NAMESPACE);
        cache.removeAll();
    }

}
