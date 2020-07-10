package com.cttnet.zhwg.ywkt.mtosi.cache;

import java.util.List;

import com.cttnet.common.cache.CacheUtil;
import com.cttnet.common.util.SpringContextUtils;
import com.cttnet.zhwg.ywkt.mtosi.ability.constants.Splits;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.FactoryName;
import com.cttnet.zhwg.ywkt.mtosi.data.po.MtosiTemplatePO;
import com.cttnet.zhwg.ywkt.mtosi.service.web.MtosiTemplateService;

/**
 * <pre>
 *  请求模板数据
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/1/11
 * @since java 1.8
 */
public class MtosiTemplateData {


    /** 缓存命名空间 */
    private static final String NAMESPACE = "service-zhwg-ywkt-mtosi-templateXml";

    public static int init() {
        MtosiTemplateService service = SpringContextUtils.getBean(MtosiTemplateService.class);
        List<MtosiTemplatePO> list = service.list(null);
        for (MtosiTemplatePO template: list) {
            add(template);
        }
        return list.size();
    }


    public synchronized static int reload() {
        clear();
        return init();
    }


    public static void add(MtosiTemplatePO template) {
        CacheUtil cache = CacheUtil.getInstance(NAMESPACE);
        String key = template.getFactoryName() + Splits.SHARP + template.getWsMethod() + Splits.SHARP + template.getVersion();
        cache.put(key, template);
    }


    private static void clear() {
        CacheUtil cache = CacheUtil.getInstance(NAMESPACE);
        cache.removeAll();
    }

    /**
     * 获取请求模板
     * @param factoryName factoryName
     * @param method cmd
     * @param version 版本号
     * @return requestXml
     */
    public static String getRequestTemplate(FactoryName factoryName, String method, String version) {
        MtosiTemplatePO templatePo = getTemplatePo(factoryName, method, version);
        if (templatePo == null) {
            return null;
        }
        return templatePo.getRequestStr();
    }

    /**
     * 获取响应模板
     * @param factoryName factoryName
     * @param method cmd
     * @param version 版本号
     * @return responseXml
     */
    public static String getResponseTemplate(FactoryName factoryName, String method, String version) {

        MtosiTemplatePO templatePo = getTemplatePo(factoryName, method, version);
        if (templatePo == null) {
            return null;
        }
        return templatePo.getResponseStr();
    }

    private static MtosiTemplatePO getTemplatePo(FactoryName factoryName, String method, String version) {
        CacheUtil cache = CacheUtil.getInstance(NAMESPACE);
        String key = factoryName + Splits.SHARP + method + Splits.SHARP + version;
        return (MtosiTemplatePO) cache.get(key);
    }
}
