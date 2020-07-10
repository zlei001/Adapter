package com.cttnet.zhwg.ywkt.mtosi.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * <pre>
 *  主键工具类
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/1/14
 * @since java 1.8
 */
public class PrimaryKeyUtils {

    private PrimaryKeyUtils() {
    }

    /**
     * 生成UUID
     * @return uuid
     */
    public static String generate() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成UUID without '-'
     * @return uuid
     */
    public static String generateWithoutSplit() {
        return generate().replaceAll("-", StringUtils.EMPTY);
    }

    /**
     * 生成UUID
     * @return uuid
     */
    public static String generate(String name) {
        return StringUtils.isEmpty(name) ? generate() : UUID.nameUUIDFromBytes(name.getBytes()).toString();
    }

    /**
     * 生成UUID
     * param key
     * @return uuid
     */
    public static String generateWithoutSplit(String name) {
        return (StringUtils.isEmpty(name) ? generate() : UUID.nameUUIDFromBytes(name.getBytes()).toString()).replaceAll("-", StringUtils.EMPTY);
    }

}
