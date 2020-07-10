package com.cttnet.zhwg.ywkt.mtosi.ability.utils;


import com.cttnet.zhwg.ywkt.mtosi.ability.constants.Splits;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.DirectionType;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.EndRefType;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 *  交叉操作工具类
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/2/15
 * @since java 1.8
 */
public class CrossUtils {

//    /**
//     * 获取键值对里的全部时隙,有序
//     * @param kvs 键值对
//     * @return 时隙，格式为1-80 或 1-80##82-100
//     */
//    public static String getTimeolts(Map<String, String> kvs){
//        StringBuffer timesolts = new StringBuffer();
//        Set<String> keySet = kvs.keySet();
//        List<String> keyList = new LinkedList<String>();
//        for (String key : keySet) {
//            if(key.endsWith("_TimeSlot")){
//                keyList.add(key);
//            }
//        }
//
//        // 1到9个时隙没问题，10个或以上字典排序有可能出错
//        Collections.sort(keyList);
//        for (String key : keyList) {
//            String timesolt = kvs.get(key);
//            timesolts.append(timesolt).append(Splits.SHARP);
//        }
//        if(timesolts.length() > 2){
//            timesolts.delete(timesolts.length() - 2, timesolts.length());
//
//        }
//        return timesolts.toString();
//    }
//
//    /**
//     * 检查是那边的时隙
//     * @param kvs 键值对
//     * @return Type.A（'A'）为A端时隙，Type.Z（'Z'）为Z端时隙
//     */
//    public static String checkWhomsTimesolt(Map<String, String> kvs){
//        String whoms = "";
//        Set<String> keySet = kvs.keySet();
//        for (String key : keySet) {
//            if(key.endsWith("_TimeSlot")){
//                if(key.startsWith("aEnd")){
//                    whoms = S.A;
//                    break;
//
//                } else if (key.startsWith("zEnd")){
//                    whoms = TimeslotType.Z;
//                    break;
//
//                }
//            }
//        }
//        return whoms;
//    }
//
//    /**
//     * 判定删除交叉或子网连接命令状态
//     * @param pss 过程状态
//     * @return 判断结果
//     */
//    public static int judgeDeleteCcOrSNCStatus(ProcedureStatus... pss){
//
//        // 解析成功记1分
//        int successScore = 1 ;
//
//        // 解析失败记2分
//        int faultScore = 2 ;
//
//        int score = 0;
//        for (ProcedureStatus procedureStatus : pss) {
//            Map<String, Object> parseResult = procedureStatus.getParseResult();
//            if(parseResult == null){
//                continue;
//            }
//            String parseStatus = (String) parseResult.get(WsParser.KEY_STATUS);
//            if(WsParser.STATUS_ALL_S.equals(parseStatus)){
//                score += successScore;
//
//            } else if(WsParser.STATUS_ERROR.equals(parseStatus)){
//                String detailErrReason = procedureStatus.getDetailErrReason();
//                if(detailErrReason.toLowerCase().contains(FaultstringKeyword.CC_NO_EXIST.toLowerCase())
//                        || detailErrReason.toLowerCase().contains(FaultstringKeyword.SNC_NO_EXIST.toLowerCase())
//                        || detailErrReason.toLowerCase().contains(FaultstringKeyword.SNC_NO_EXIST2.toLowerCase())
//                        || procedureStatus.getResponseXml().contains(FaultstringKeyword.HW_CC_NO_EXIST.toLowerCase())){
//                    score += successScore;
//
//                } else {
//                    score += faultScore;
//
//                }
//
//            } else {
//                score += faultScore;
//
//            }
//        }
//
//        int result = 0;
//        if(score == successScore * pss.length){
//            result = ProcessingStatus.ALL_SUCCESS;
//
//        } else if(score == faultScore * pss.length){
//            result = ProcessingStatus.ALL_FAULT;
//
//        } else {
//            result = ProcessingStatus.PARTIAL_SUCCESS;
//
//        }
//
//        return result;
//    }
//
//    /**
//     * 判定中兴创建交叉的状态
//     * @param pss 过程状态
//     * @return 判断结果
//     */
//    public static int judgeZXCreateCCStatus(ProcedureStatus... pss){
//
//        // 解析成功记1分
//        int successScore = 1 ;
//
//        // 解析失败记2分
//        int faultScore = 2 ;
//
//        int score = 0;
//        for (ProcedureStatus procedureStatus : pss) {
//            Map<String, Object> parseResult = procedureStatus.getParseResult() == null ? new HashMap<String, Object>()
//                    : procedureStatus.getParseResult();
//            String parseStatus = (String) parseResult.get(WsParser.KEY_STATUS);
//            String detailErrReason = procedureStatus.getDetailErrReason();
//
//            if(WsParser.STATUS_ALL_S.equals(parseStatus)){
//                score += successScore;
//
//            } else if(WsParser.STATUS_ERROR.equals(parseStatus)){
//                if(detailErrReason.toLowerCase().contains(FaultstringKeyword.ZX_CC_EXIST.toLowerCase())){
//                    score += successScore;
//
//                } else {
//                    score += faultScore;
//
//                }
//
//            } else {
//                score += faultScore;
//
//            }
//        }
//
//        int result = 0;
//        if(score == successScore * pss.length){
//            result = ProcessingStatus.ALL_SUCCESS;
//
//        } else if(score == faultScore * pss.length){
//            result = ProcessingStatus.ALL_FAULT;
//
//        } else {
//            result = ProcessingStatus.PARTIAL_SUCCESS;
//
//        }
//
//        return result;
//    }
//
//
//
//    /**
//     * 修正Z端方向,由sink转成src
//     * @param zEndRefList Z端(只支持单个)
//     * @return 修正后的Z端
//     */
//    public static String correctZEndRefList(String zEndRefList){
//        DirectionType zDirection = BusinessUtils.judgeDirection(zEndRefList);
//
//        // Z端由sink转成src
//        if(DirectionType.SINK == zDirection){
//            zEndRefList = BusinessUtils.reverseEndRefList(zEndRefList);
//        }
//        return zEndRefList;
//    }
//
    /**
     * 修正A端方向,由src转成sink
     * @param aEndRefList A端
     * @return 修正后的A端
     */
    public static String correctAEndRefList(String aEndRefList) {
        StringBuffer sb = new StringBuffer();
        String[] endLists = aEndRefList.split(Splits.SHARP);

        // A端由src转成sink
        for (String endList : endLists) {
            if(DirectionType.SOURCE.equals(BusinessUtils.judgeDirection(endList))){
                endList = BusinessUtils.reverseEndRefList(endList);
            }
            sb.append(endList).append(Splits.SHARP);
        }
        sb.delete(sb.length() - 2 , sb.length());
        aEndRefList = sb.toString();

        return aEndRefList;
    }



    public static String getResponseEndRefListTimeslot(String vendorExtensions, String type){
        String result = "";
        if(vendorExtensions != null){
            String[] kvs = vendorExtensions.split(Splits.WAVE);
            Map<Integer, String> timeslotMap = new HashMap<Integer, String>();
            for (String kv : kvs) {
                int idx = kv.indexOf(Splits.EQUAL);
                if(idx > 0){
                    String key = kv.substring(0, idx);
                    String value = kv.substring(idx == kv.length() ? idx : idx + 1, kv.length());
                    if(EndRefType.A.getName().equals(type)){
                        if(key.startsWith("aEnd") && key.endsWith("_TimeSlot")){
                            int number = findNumber(key);
                            if(number != -1){
                                timeslotMap.put(number, value);
                            }
                        }
                    } else if(EndRefType.Z.getName().equals(type)){
                        if(key.startsWith("zEnd") && key.endsWith("_TimeSlot")){
                            int number = findNumber(key);
                            if(number != -1){
                                timeslotMap.put(number, value);
                            }
                        }

                    }
                }
            }
            List<Map.Entry<Integer, String>> entryList = new ArrayList<Map.Entry<Integer, String>>(timeslotMap.entrySet());
            Collections.sort(entryList,new Comparator<Map.Entry<Integer, String>>() {
                //升序排序
                @Override
                public int compare(Map.Entry<Integer, String> o1, Map.Entry<Integer, String> o2) {
                    return o1.getValue().compareTo(o2.getValue());
                }
            });

            StringBuffer sb = new StringBuffer();
            for (Map.Entry<Integer,String> entry : entryList) {
                sb.append(entry.getValue()).append(Splits.SHARP);
            }
            if(sb.length() > 0){
                sb.delete(sb.length() -2 , sb.length());
            }
            result = sb.toString();
        }
        return result;
    }

    public static int findNumber(String key) {
        String result = "";
        String regex = "(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(key);
        if(matcher.find()){
            result = matcher.group();
        }
        int number = -1;
        try {
            number = "".equals(result) ? -1 : Integer.valueOf(result);
        } catch (NumberFormatException e) {
            /*LOG.error("", e);*/
        }
        return number;
    }
}
