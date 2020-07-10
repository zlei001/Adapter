package com.cttnet.zhwg.ywkt.mtosi.ability.utils;

/**
 * <pre>
 *  对象工具类
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/1/11
 * @since java 1.8
 */
public class ObjectTools {

    /**
     * 连接字符串
     *
     * @param strlist
     *            多个字符串
     * @return 依次连接所有字符串的字符串
     */
    public static String concat(String... strlist) {
        StringBuilder sb = new StringBuilder();
        for (String str : strlist) {
            if (str == null) {
                continue;
            }
            sb.append(str);
        }
        return sb.toString();
    }
    /**
     * 根据两个特征关键字切割数据。 修正模式： 找不到开始关键字修正为0； 找不到结束关键字修正为文本长度；
     *
     * @param data
     *            数据
     * @param start
     *            开始关键字
     * @param end
     *            结束关键字
     * @param isContainStart
     *            是否包含开始关键字
     * @param isContainEnd
     *            是否包含结束关键字
     * @return 切割后的数据
     */
    public static String split(String data, String start, String end,
                               boolean isContainStart, boolean isContainEnd) {
        if ("".equals(data) || data == null) {
            return "";
        }
        int startIdx = findKeyIdx(data, start, isContainStart);
        int endIdx = findKeyIdx(data, start, end, isContainEnd);
        // 修正模式
        if (startIdx == -1) {
            startIdx = 0;

        }
        if (endIdx == -1) {
            endIdx = data.length();

        }
        String result = data.substring(startIdx, endIdx);
        return result;
    }


    /**
     * 寻找关键字在数据首次出现的位置
     *
     * @param data
     *            数据
     * @param key
     *            开始关键字
     * @param isContainKey
     *            位置是否包含关键字，true为包含，false为不包含
     * @return 关键字首次出现的位置，未找到则返回-1
     */
    public static int findKeyIdx(String data, String key, boolean isContainKey) {
        int idx = -1;
        if (data == null) {
            return idx;
        }
        idx = data.indexOf(key);
        if (idx != -1 && isContainKey == false) {
            idx += key.length();

        }
        return idx;
    }
    /**
     * 在数据中找出首after关键字之后的key关键字出现的位置
     *
     * @param data
     *            数据
     * @param after
     *            在此关键字之后
     * @param key
     *            关键字
     * @param isContainKey
     *            位置是否包含关键字，true为包含，false为不包含
     * @return 关键字首次出现的位置，未找到则返回-1
     */
    public static int findKeyIdx(String data, String after, String key,
                                 boolean isContainKey) {
        if (data == null) {
            return -1;
        }
        int startIdx = data.indexOf(after);
        int endIdx = -1;
        if (startIdx != -1) {
            startIdx += after.length();
            endIdx = data.indexOf(key, startIdx);

        }
        if (endIdx != -1 && isContainKey == true) {
            endIdx += key.length();

        }
        return endIdx;
    }


}
