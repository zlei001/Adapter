package com.cttnet.zhwg.ywkt.mtosi.ability.utils;

import com.cttnet.zhwg.ywkt.mtosi.ability.constants.PortProperties;
import com.cttnet.zhwg.ywkt.mtosi.ability.constants.Splits;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.DirectionType;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <pre>
 *  业务处理工具类
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/1/10
 * @since java 1.8
 */
public class BusinessUtils {


    /**
     * 构造名值对列表的值格式
     * @param nvs 键值对
     * @return 名值对格式的值
     */
    public static String toNvsList(Map<String, String> nvs) {
        String nvsList = "";
        if(nvs != null) {
            StringBuilder sb = new StringBuilder();
            for(Iterator<String> keys = nvs.keySet().iterator();
                keys.hasNext();) {
                String key = keys.next();
                String val = nvs.get(key);
                if(StringUtils.isEmpty(val)) {
                    continue;
                }
                sb.append(Splits.WAVE).append(key).append(Splits.EQUAL).append(val);
            }
            nvsList = sb.toString();
        }
        return nvsList;
    }

    /**
     * 获取分离时隙键值对表
     * @param aEndTimeSlot A端时隙
     * @param zEndTimeSlot Z端时隙
     * @return 隙键值对表
     */
    public static Map<String, String> toTimeSlotMap(
            String aEndTimeSlot, String zEndTimeSlot) {
        Map<String, String> timeSlots = new HashMap<>();
        if(StringUtils.isNotEmpty(aEndTimeSlot)) {
            String[] aTimeSlots =
                    aEndTimeSlot.trim().replaceFirst(Splits.REP_SHARP, StringUtils.EMPTY).split(Splits.SHARP);
            for(int i = 0; i < aTimeSlots.length; i++) {
                if(StringUtils.isEmpty(aTimeSlots[i])) {
                    continue;
                }

                String key = "aEnd" + (i + 1) + "_TimeSlot";
                String val = aTimeSlots[i];
                timeSlots.put(key, val);
            }
        }

        if(StringUtils.isNotEmpty(zEndTimeSlot)) {
            String[] zTimeSlots =
                    zEndTimeSlot.trim().replaceFirst(Splits.REP_SHARP, StringUtils.EMPTY).split(Splits.SHARP);
            for(int i = 0; i < zTimeSlots.length; i++) {
                if(StringUtils.isEmpty(zTimeSlots[i])) {
                    continue;
                }

                String key = "zEnd" + (i + 1) + "_TimeSlot";
                String val = zTimeSlots[i];
                timeSlots.put(key, val);
            }
        }
        return timeSlots;
    }

//    /**
//     * 构造层参数列表的值格式
//     * @param emsName EMS名称
//     * @param layerRate 层速率
//     * @param parameterList 层参数列表
//     * @param vendorExtensions 附加信息
//     * @return 层参数列表格式的值
//     */
//    public static String toTransmissionParametersList(final String emsName,
//                                                      final String layerRate, final Map<String, String> parameterList,
//                                                      final Map<String, String> vendorExtensions) {
//        StringBuilder tpList = new StringBuilder();
//
//        // 层速率
//        if(layerRate != null) {
//            tpList.append(layerRate);
//        }
//
//        // 层参数列表
//        if(parameterList != null) {
//            if(layerRate == null) {
//                tpList.append(Splits.SHARP);	//占位
//            }
//            tpList.append(Splits.SHARP);
//
//            List<String> keys = new LinkedList<>(parameterList.keySet());
//            for(String key : keys) {
//                if(StringUtils.isEmpty(key) || !parameterList.containsKey(key)) {
//                    continue;
//                }
//
//                String[] ttiNames = PortUtils.getTTINames(key);
//                if(ttiNames != null &&
//                        (FactoryName.isHW(emsName) || FactoryName.isZX(emsName))) {
//                    String sapiKey = ttiNames[0];
//                    String dapiKey = ttiNames[1];
//                    String sapiVal = parameterList.remove(sapiKey);
//                    String dapiVal = parameterList.remove(dapiKey);
//                    // 必须成对值非空才能下发
//                    if(StringUtils.isEmpty(sapiVal) || StringUtils.isEmpty(dapiVal)) {
//                        continue;
//                    }
//
//                    if(FactoryName.isHW(emsName)) {
//                        tpList.append(getHwTTIparameter(
//                                layerRate, sapiKey, sapiVal, dapiKey, dapiVal));
//
//                    } else if(FactoryName.isZX(emsName)) {
//                        tpList.append(getZxTTIparameter(
//                                layerRate, sapiKey, sapiVal, dapiKey, dapiVal));
//                    }
//
//                } else {
//                    String val = parameterList.remove(key);
//                    if(StringUtils.isEmpty(val)) {
//                        continue;
//                    }
//                    tpList.append(Splits.WAVE).append(key);
//                    tpList.append(Splits.EQUAL).append(val);
//                }
//            }
//        }
//
//        // 附加信息
//        if(vendorExtensions != null) {
//            if(parameterList == null) {
//                //占位
//                tpList.append(Splits.SHARP);
//            }
//            tpList.append(Splits.SHARP);
//            for (String key : vendorExtensions.keySet()) {
//                String val = vendorExtensions.get(key);
//                if (StringUtils.isEmpty(val) || StringUtils.isEmpty(key)) {
//                    continue;
//                }
//
//                tpList.append(Splits.WAVE).append(key).append(Splits.EQUAL).append(val);
//            }
//        }
//        return tpList.toString();
//    }
//
//    /**
//     * 构造华为的TTI参数
//     * @param layerRate 层速率
//     * @param sapiKey SAPI的键
//     * @param sapiVal SAPI的值
//     * @param dapiKey DAPI的键
//     * @param dapiVal DAPI的值
//     * @return 华为的TTI参数
//     */
//    private static String getHwTTIparameter(final String layerRate,
//                                            final String sapiKey, final String sapiVal,
//                                            final String dapiKey, final String dapiVal) {
//        StringBuilder ttiParameter = new StringBuilder();
//
//        // 合二为一
//        if(PortUtils.isOTU(layerRate) || PortUtils.isODU(layerRate)) {
//            String key = null;
//            if(PortUtils.isOTU(layerRate)) {
//                key = sapiKey.replaceFirst("^SM", "").replace("SAPI", "");
//            } else {
//                key = sapiKey.replaceFirst("^PM", "").replace("SAPI", "");
//            }
//            String val = ObjectUtils.concat(
//                    ObjectUtils.fill(ObjectUtils.removeSpace(sapiVal), 32, '0'),
//                    ObjectUtils.fill(ObjectUtils.removeSpace(dapiVal), 32, '0'));
//            val = ObjectUtils.fill(val, (32 + 32 + 60), '0');
//            val = ObjectUtils.addSpace(val, 2);
//            ttiParameter.append(Splits.WAVE).append(key).append(Splits.EQUAL).append(val);
//
//            // 不合并
//        } else {
//            ttiParameter.append(Splits.WAVE).append(sapiKey).append(Splits.EQUAL).append(sapiVal);
//            ttiParameter.append(Splits.WAVE).append(dapiKey).append(Splits.EQUAL).append(dapiVal);
//        }
//        return ttiParameter.toString();
//    }
//
//    /**
//     * 构造中兴的TTI参数
//     * @param layerRate 层速率
//     * @param sapiKey SAPI的键
//     * @param sapiVal SAPI的值
//     * @param dapiKey DAPI的键
//     * @param dapiVal DAPI的值
//     * @return 中兴的TTI参数
//     */
//    private static String getZxTTIparameter(final String layerRate,
//                                            final String sapiKey, final String sapiVal,
//                                            final String dapiKey, final String dapiVal) {
//        StringBuilder ttiParameter = new StringBuilder();
//
//        // 合二为一
//        if(PortUtils.isOTU(layerRate) || PortUtils.isODU(layerRate)) {
//            String key = sapiKey.replace("SAPI", "");
//            String val = ObjectUtils.concat(
//                    ObjectUtils.fill(ObjectUtils.removeSpace(sapiVal), 32, '0'),
//                    ObjectUtils.fill(ObjectUtils.removeSpace(dapiVal), 32, '0'));
//            val = ObjectUtils.fill(val, (32 + 32 + 64), '0');
//            ttiParameter.append(Splits.WAVE).append(key).append(Splits.EQUAL).append(val);
//
//            // 不合并
//        } else {
//            ttiParameter.append(Splits.WAVE).append(sapiKey).append(Splits.EQUAL).append(sapiVal);
//            ttiParameter.append(Splits.WAVE).append(dapiKey).append(Splits.EQUAL).append(dapiVal);
//        }
//        return ttiParameter.toString();
//    }
//
    /**
     * 判断方向类型
     * @param xEndRefList 源宿端
     * @return 方向类型，默认返回空串
     */
    public static DirectionType judgeDirection(String xEndRefList){
        DirectionType direction = null;
        if (xEndRefList.contains(DirectionType.SINK.getCode())) {
            direction = DirectionType.SINK;

        } else if (xEndRefList.contains(DirectionType.SOURCE.getCode())) {
            direction = DirectionType.SOURCE;

        }
        return direction;
    }
//
    /**
     * 反转原宿端
     * @param xEndRefList 某端口
     * @return 反转后的端口
     */
    public static String reverseEndRefList(String xEndRefList){
        if (StringUtils.isEmpty(xEndRefList)) {
            return xEndRefList;
        }
        String endRefList = xEndRefList;
        if (endRefList.contains(PortProperties.FREQUENCY_TUNABLE_NUMBER_1)) {
            endRefList = endRefList.replace(PortProperties.FREQUENCY_TUNABLE_NUMBER_1, PortProperties.FREQUENCY_000);

        } else if (endRefList.contains(PortProperties.FREQUENCY_000)) {
            endRefList = endRefList.replace(PortProperties.FREQUENCY_000, PortProperties.FREQUENCY_TUNABLE_NUMBER_1);

        }

        if (endRefList.contains(DirectionType.SINK.getCode())) {
            endRefList = endRefList.replace(DirectionType.SINK.getCode(), DirectionType.SOURCE.getCode());

        } else if (endRefList.contains(DirectionType.SOURCE.getCode())) {
            endRefList = endRefList.replace(DirectionType.SOURCE.getCode(), DirectionType.SINK.getCode());

        }

        if (endRefList.contains(PortProperties.SI_FLAG)) {
            endRefList = endRefList.replace(PortProperties.SI_FLAG, PortProperties.SO_FLAG);

        } else if (endRefList.contains(PortProperties.SO_FLAG)) {
            endRefList = endRefList.replace(PortProperties.SO_FLAG, PortProperties.SI_FLAG);

        }
        return endRefList;
    }
//
//    /**
//     * 分割字符串
//     * @param str 被分割的字符串
//     * @param separate 分割标志
//     * @return 分割后的字符串列表
//     */
//    public static List<String> split(String str, String separate) {
//        List<String> list = new LinkedList<String>();
//        if(str.contains(separate)){
//            String[] split = str.split(separate);
//
//            for (String string : split) {
//                list.add(string);
//            }
//        } else {
//            list.add(str);
//        }
//        return list;
//    }
//
//    /**
//     * 判断是否为符合规则的厂家原始值
//     * @param eSVendorObjName 期待厂家原始值对象
//     * @param rule 规则对像
//     * @return true为符合，false为不符合
//     */
//    public static boolean isPassVendorObjName(ExpectVendorObjName expect, Rule rule){
//
//        if(expect.getVendorObjName() == null){
//            return false;
//
//        }
//        if(expect.getVendorObjName().equals(rule.getVendorObjName())){
//            return true;
//
//        }
//        if(expect.getPort().equals(rule.getVendorObjName())){
//            return true;
//
//        }
//        if(expect.getNetElement().equals(rule.getVendorObjName())){
//            return true;
//        }
//
//        return false;
//    }
//
//    /**
//     * 获取厂家原始值的网元
//     * @param data 厂家原始值
//     * @return true为符合，false为不符合
//     */
//    public static String getVendorObjNameNetElement(String data){
//        int end = -1;
//        if (data.contains("~ME=")) {
//            end = ObjectUtils.findKeyIdx(data, "~ME=", "~", false);
//            if (end == -1) {
//                end = data.length();
//            }
//        } else {
//            end = data.length();
//        }
//        return data.substring(0, end);
//    }
//
//    /**
//     * 获取厂家原始值的端口
//     * @param data 厂家原始值
//     * @return true为符合，false为不符合
//     */
//    public static String getVendorObjNamePort(String data){
//        int end = -1;
//        if (data.contains("/port=")) {
//            end = ObjectUtils.findKeyIdx(data, "/port=", "~", false);
//            if (end == -1) {
//                end = data.length();
//            }
//        } else {
//            end = data.length();
//        }
//        return data.substring(0, end);
//    }
//
//    /**
//     * 判断是否为符合规则的网元实体名称
//     * @param eSMoiName 期待网元实体名称对象
//     * @param rule 规则对像
//     * @return true为符合，false为不符合
//     */
//    public static boolean isPassMoiName(ExpectMoiName expect, Rule rule){
//        boolean isPass = false;
//        if(expect.getVendorObjName() != null){
//            if(expect.getVendorObjName().equals(rule.getVendorObjName())){
//                isPass = true;
//                return isPass;
//            }
//        }
//        if(rule.getMoiName() != null && rule.getMoiName().toLowerCase().contains("/port=")){
//            isPass = expect.getPort().equals(rule.getMoiName());
//
//        } else {
//            isPass = expect.getNetElement().equals(rule.getMoiName());
//
//        }
//        return isPass;
//    }
//
//    /**
//     * 获取网元实体名称的端口
//     * @param data 网元实体名称
//     * @return true为符合，false为不符合
//     */
//    public static String getMoiNamePort(String data) {
//        int end = 0;
//        if (data.contains("/Port=")) {
//            int pStartIdx = data.indexOf("/Port=");
//            int pEndIdx = data.indexOf("/", pStartIdx + "/Port=".length());
//            if (pEndIdx == -1) {
//                pEndIdx = data.length();
//            }
//            String port = data.substring(pStartIdx, pEndIdx);
//            if (Pattern.compile("\\d").matcher(port).find()) {
//                Pattern pattern = Pattern
//                        .compile(".*/Port=[a-zA-Z-]{0,}[\\d]{0,}");
//                Matcher matcher = pattern.matcher(port);
//                String group = "";
//                if (matcher.find()) {
//                    group = matcher.group();
//                }
//                end = data.indexOf(group) + group.length();
//
//            } else if (port.contains("-")) {
//                port = port.split("-")[0];
//                end = data.indexOf(port) + port.length();
//
//            } else {
//                end = data.length();
//
//            }
//        } else {
//            end = data.length();
//        }
//        return data.substring(0, end);
//    }
//
//    /**
//     * 获取网元实体名称的网元
//     * @param data 网元实体名称
//     * @return true为符合，false为不符合
//     */
//    public static String getMoiNameNetElement(String data) {
//        int end = 0;
//        if (data.contains("/Ne=")) {
//            end = ObjectUtils.findKeyIdx(data, "/Ne=", "/", false);
//            if (end == -1) {
//                end = data.length();
//            }
//        } else {
//            end = data.length();
//        }
//        return data.substring(0, end);
//    }
//
//    /**
//     * 获取命理的规则对象
//     * @param cmd 命令对象
//     * @return 规则对象列表
//     */
//    public static List<Rule> getCommandRuleBeans(Command cmd){
//        List<Rule> beans = new LinkedList<Rule>();
//        Map<String, Object> inMap = cmd.getInMap();
//        if(Cmds.createAndActivateCrossConnection.equals(CommandConverter.mappingRealCommand(cmd))
//                || Cmds.deactivateAndDeleteCrossConnection.equals(CommandConverter.mappingRealCommand(cmd))){
//            List<Map<String, Object>> cmdList = ObjectUtils.getCmdList(inMap, "ccs");
//            for (Map<String, Object> map : cmdList) {
//                String aEndRefList = ObjectUtils.getStrVal(map, "S_A_END_VENDOR_NAME");
//                String zEndRefList = ObjectUtils.getStrVal(map, "S_Z_END_VENDOR_NAME");
//
//                String aEndPortId = ObjectUtils.getStrVal(map, "S_A_END_PORT_ID");
//                String zEndPortId = ObjectUtils.getStrVal(map, "S_Z_END_PORT_ID");
//
//                String[] aEnds = aEndRefList.split("##");
//                String[] aPortIds = aEndPortId.split("##");
//                int len = aEnds.length > aPortIds.length ? aEnds.length : aPortIds.length;
//
//                for (int i = 0; i < len; i++) {
//                    String emsName = ObjectUtils.getStrVal(map, "S_EMS_NAME");
//                    String moiName = ObjectUtils.getStrVal(map, "S_MOI_NAME");
//                    Rule rule = new Rule();
//                    rule.setPortId(i < aPortIds.length ? aPortIds[i] : "");
//                    rule.setVendorObjName(i < aEnds.length ? aEnds[i] : "");
//                    rule.setEmsName(emsName);
//                    rule.setMoiName(moiName);
//
//                    beans.add(rule);
//                }
//
//
//                String[] zEnds = zEndRefList.split("##");
//                String[] zPortIds = zEndPortId.split("##");
//                len = zEnds.length > zPortIds.length ? zEnds.length : zPortIds.length;
//                for (int i = 0; i < len; i++) {
//                    String emsName = ObjectUtils.getStrVal(map, "S_EMS_NAME");
//                    String moiName = ObjectUtils.getStrVal(map, "S_MOI_NAME");
//                    Rule rule = new Rule();
//                    rule.setPortId(i < zPortIds.length ? zPortIds[i] : "");
//                    rule.setVendorObjName(i < zEnds.length ? zEnds[i] : "");
//                    rule.setEmsName(emsName);
//                    rule.setMoiName(moiName);
//
//                    beans.add(rule);
//                }
//            }
//
//            // 保护倒换
//        } else if(Cmds.performProtectionCommand.equals(CommandConverter.mappingRealCommand(cmd))){
//            String portId = ObjectUtils.getStrVal(inMap, "S_PORT_ID");
//            String emsName = ObjectUtils.getStrVal(inMap, "S_EMS_NAME");
//            String moiName = ObjectUtils.getStrVal(inMap, "S_MOI_NAME");
//            String protectingPortName = ObjectUtils.getStrVal(inMap, "S_PROTING_PORT_NAME");
//            String protectedPortName = ObjectUtils.getStrVal(inMap, "S_PROTED_PORT_NAME");
//
//            Rule protectingRule = new Rule();
//            protectingRule.setPortId(portId);
//            protectingRule.setEmsName(emsName);
//            protectingRule.setMoiName(moiName);
//            protectingRule.setVendorObjName(protectingPortName);
//
//            beans.add(protectingRule);
//
//            Rule protectedRule = new Rule();
//            protectedRule.setPortId(portId);
//            protectedRule.setEmsName(emsName);
//            protectedRule.setMoiName(moiName);
//            protectedRule.setVendorObjName(protectedPortName);
//
//            beans.add(protectedRule);
//
//        } else {
//            String portId = ObjectUtils.getStrVal(inMap, "S_PORT_ID");
//            String emsName = ObjectUtils.getStrVal(inMap, "S_EMS_NAME");
//            String moiName = ObjectUtils.getStrVal(inMap, "S_MOI_NAME");
//            String vendorObjName = ObjectUtils.getStrVal(inMap, "S_VENDOR_OBJ_NAME");
//
//            Rule rule = new Rule();
//            rule.setPortId(portId);
//            rule.setEmsName(emsName);
//            rule.setMoiName(moiName);
//            rule.setVendorObjName(vendorObjName);
//
//            beans.add(rule);
//        }
//        return beans;
//    }
//
//    /**
//     * 获取命令的原宿端列表
//     * @param cmd 命令对象
//     * @return 原宿端列表
//     */
//    public static List<String> getXEndList(Command cmd) {
//        List<String> xEndList = new LinkedList<String>();
//        if(Cmds.createAndActivateCrossConnection.equals(cmd.getCmd())
//                || Cmds.deactivateAndDeleteCrossConnection.equals(cmd.getCmd())
//                || Cmds.activateCrossConnection.equals(cmd.getCmd())
//                || Cmds.deactivateCrossConnection.equals(cmd.getCmd())
//                || Cmds.preUseCrossConnection.equals(cmd.getCmd())
//                || Cmds.unPreUseCrossConnection.equals(cmd.getCmd())){
//            List<Map<String, Object>> cmdList = ObjectUtils.getCmdList(
//                    cmd.getInMap(), "ccs");
//            for (Map<String, Object> map : cmdList) {
//                String aEnd = ObjectUtils.getStrVal(map, "S_A_END_VENDOR_NAME");
//                String zEnd = ObjectUtils.getStrVal(map, "S_Z_END_VENDOR_NAME");
//                List<String> aEndList = Arrays.asList(aEnd.split("##"));
//                List<String> zEndList = Arrays.asList(zEnd.split("##"));
//
//                xEndList.addAll(aEndList);
//                xEndList.addAll(zEndList);
//            }
//
//        } else {
//            String xEnd = ObjectUtils.getStrVal(cmd.getInMap(), "S_VENDOR_OBJ_NAME");
//            xEndList = Arrays.asList(xEnd.split("##"));
//
//        }
//        return xEndList;
//    }
//
//    /**
//     * 获取命令里的EMS名称
//     * @param cmd 命令对象
//     * @return EMS名称
//     */
//    public static String getEmsName(Command cmd) {
//        String emsName = "";
//        if(Cmds.createAndActivateCrossConnection.equals(cmd.getCmd())
//                || Cmds.deactivateAndDeleteCrossConnection.equals(cmd.getCmd())
//                || Cmds.activateCrossConnection.equals(cmd.getCmd())
//                || Cmds.deactivateCrossConnection.equals(cmd.getCmd())
//                || Cmds.preUseCrossConnection.equals(cmd.getCmd())
//                || Cmds.unPreUseCrossConnection.equals(cmd.getCmd())
//                || Cmds.planUseCrossConnection.equals(cmd.getCmd())
//                || Cmds.unPlanUseCrossConnection.equals(cmd.getCmd())
//                || Cmds.unTempUseCrossConnection.equals(cmd.getCmd())){
//            List<Map<String, Object>> cmdList = ObjectUtils.getCmdList(
//                    cmd.getInMap(), "ccs");
//            emsName = ObjectUtils.getStrVal(cmdList.get(0), "S_EMS_NAME");
//
//        } else {
//            emsName = ObjectUtils.getStrVal(cmd.getInMap(), "S_EMS_NAME");
//
//        }
//        return emsName;
//    }
//
//    /**
//     * 消除含有DSR的CTP
//     * @param vendorObjectName 厂家原始值
//     * @return 消除后的值
//     */
//    @Deprecated
//    public static String deleteDsr(String vendorObjectName){
//        String result = vendorObjectName;
//        if(vendorObjectName.contains("~CTP=/dsr=")){
//            String dsr = ObjectUtils.split(vendorObjectName, "~CTP=/dsr=", "~", true, false);
//            result = vendorObjectName.replace(dsr, "");
//
//        }
//        return result;
//    }
//
//    /**
//     * och=1后追加odu4=1
//     * @param vendorObjectName 厂家原始值
//     * @return 追加后的值
//     */
//    @Deprecated
//    public static String och1AddOdu4(String vendorObjectName){
//        String result = vendorObjectName;
//        if(vendorObjectName.contains("~CTP=/och=1") && !vendorObjectName.contains("/odu")){
//            result = vendorObjectName.replace("~CTP=/och=1", "~CTP=/och=1/odu4=1");
//
//        }
//        return result;
//    }
//
    /**
     * 检查某端是否包含源端(src)方向
     * @param xEndList 某端
     * @return 是否包含源端(src)方向 ，true为包含，false为不包含
     */
    public static boolean isContainsSourceDirection(String xEndList){
        boolean isContains = false;
        String[] endLists = xEndList.split(Splits.SHARP);
        for (String endList : endLists) {
            if(DirectionType.SOURCE.equals(BusinessUtils.judgeDirection(endList))){
                isContains = true;
                break;
            }
        }
        return isContains;
    }
//
//    /**
//     * 检查某端是否包含宿端(sink)方向
//     * @param xEndList 某端
//     * @return 是否包含源端(sink)方向 ，true为包含，false为不包含
//     */
//    public static boolean isContainsSinkDirection(String xEndList){
//        boolean isContains = false;
//        String[] endLists = xEndList.split(Splits.SHARP);
//        for (String endList : endLists) {
//            if(DirectionType.SINK.equals(BusinessUtils.judgeDirection(endList))){
//                isContains = true;
//                break;
//            }
//        }
//        return isContains;
//    }
//
    /**
     * 检查A端Z端是否方向一致
     * @param aEndRefList A端(可支持多个)
     * @param zEndRefList Z端(只支持单个)
     * @return 方向是否一致，true为是，false为否
     */
    public static boolean isSameDirection(String aEndRefList, String zEndRefList){
        boolean isSame = false;
        DirectionType zDirection = BusinessUtils.judgeDirection(zEndRefList);
        if(DirectionType.SINK == zDirection){
            String[] endLists = aEndRefList.split(Splits.SHARP);
            for (String endList : endLists) {
                if(DirectionType.SINK == BusinessUtils.judgeDirection(endList)){
                    isSame = true;
                    break;
                }
            }

        } else if(DirectionType.SOURCE == zDirection){
            String[] endLists = aEndRefList.split(Splits.SHARP);
            for (String endList : endLists) {
                if(DirectionType.SOURCE.equals(BusinessUtils.judgeDirection(endList))){
                    isSame = true;
                    break;
                }
            }

        }
        return isSame;
    }
//
//    /**
//     * 日志跟踪
//     * @param cmd
//     */
//    public static String getPressId(Command cmd){
//        String sid = cmd.getSid();
//        int index = -1;
//        if(sid != null){
//            index = sid.indexOf("|I_PRESS_ID:");
//        }
//        String pressId = "";
//        if(index != -1){
//            pressId = sid.substring(index).replace("|I_PRESS_ID:", "");
//        }
//        return pressId;
//    }
//
//    public static String getTransmissionParametersList(Element element){
//        return getTransmissionParametersList(element, "transmissionParametersList");
//    }
//
//    public static String getTransmissionParametersList(Element element, String name){
//        StringBuffer sb = new StringBuffer();
//        Element transmissionParametersList = element.element(name);
//        if(transmissionParametersList != null){
//            List<Element> transmissionParameters = transmissionParametersList.elements("transmissionParameters");
//            for (Element transmissionParameter : transmissionParameters) {
//                Element layerRateElement = transmissionParameter.element("layerRate");
//                String layerRate = getLayerRate(layerRateElement);
//                String parameterList = XmlUtils.getSkList(transmissionParameter, "parameterList", "nvs", "name", "value");
//                sb.append(layerRate).append(Splits.SHARP).append(parameterList).append(Splits.AT);
//            }
//        }
//        if(sb.length() > 2){
//            sb.delete(sb.length() - 2 , sb.length());
//        }
//        return sb.toString();
//    }
//
//    public static String getLayerRate(Element element) {
//        String layerRate = "";
//        if (element != null) {
//            Attribute attr = element.attribute("extension");
//            if (attr != null) {
//                layerRate = attr.getValue();
//
//            } else {
//                layerRate = element.getTextTrim();
//            }
//        }
//        return layerRate;
//    }
}
