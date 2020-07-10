package com.cttnet.zhwg.ywkt.support.translate;

import com.cttnet.common.util.StringUtil;
import com.cttnet.zhwg.ywkt.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <pre>列表数据名称翻译</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-17
 * @since jdk 1.8
 */
@Slf4j
public class DataListTranslate {


    /**
     * 翻译
     * @param list 待翻译数据列
     * @param translate 翻译器
     * @param <T> T 对象数据类型
     */
    public static <T> void addNameById(List<T> list, Translate translate) {

        if (list == null || list.isEmpty()) {
            return;
        }
        try {
            Set<Map.Entry<String, String>> entries = translate.targetFields().entrySet();
            Set<String> values = new HashSet<>();
            for (T data: list) {
                for (Map.Entry<String, String> entry: entries) {
                    Field declaredField = data.getClass().getDeclaredField(entry.getKey());
                    declaredField.setAccessible(true);
                    Object o = declaredField.get(data);
                    values.add((String)o);
                }
            }
            List<Map<String, Object>> maps = translate.translate(values);
            if (maps == null || maps.isEmpty()) {
                log.debug("查询不到数据");
                return;
            }
            Map map = StringUtil.transMapByKey(maps, translate.getSourceKey());
            for (T data: list) {
                for (Map.Entry<String, String> entry: entries) {
                    Field declaredField = data.getClass().getDeclaredField(entry.getKey());
                    Field valueField = data.getClass().getDeclaredField(entry.getValue());
                    declaredField.setAccessible(true);
                    valueField.setAccessible(true);
                    Object o = declaredField.get(data);
                    Map one = MapUtils.getMap(map, o);
                    Object value = MapUtils.getObject(one, translate.getTargetKey());
                    valueField.set(data, value);
                }
            }
        } catch (Exception e) {
            log.error("翻译名称出错", e);
            throw  new BusinessException("翻译名称出错", e);
        }

    }

    public interface Translate<K> {

        /**
         * 远程查询数据
         * @param ids ids
         * @return datas
         */
        List<Map<String, Object>> translate(Set<K> ids);


        /**
         * 待翻译的目标对象字段
         * Map中key是翻译的对象主键，value是需要填充的值
         * @return map
         */
        Map<String, String> targetFields();

        /**
         * 标识key
         * @return key
         */
        String getSourceKey();

        /**
         *  目标字段key
         * @return key
         */
        String getTargetKey();
    }
}
