package com.cttnet.zhwg.ywkt.mtosi.data.dto.request;

import com.cttnet.zhwg.ywkt.mtosi.ability.constants.Splits;
import com.cttnet.zhwg.ywkt.mtosi.ability.utils.ObjectTools;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dengkaihong
 * @date 2020/4/13
 * @since java 1.8
 */
@Data
public class TransmissionParameters implements Serializable {

    public String layerRate;
    public Map<String, String> parameterList;
    // 键值对形式数据转换为符合报文格式要求的数据形式
    public String requestDataFormat(){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String,String> entry : parameterList.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(Splits.WAVE).append(key).append(Splits.EQUAL).append(value);
        }
        return ObjectTools.concat(layerRate, Splits.SHARP , sb.toString());
    }
   // 报文输出的数据格式转化为 键值对形式 List<Map<string,string>>
    public static List<TransmissionParameters> getTransmissionParametersList(String transmissionParametersList){
        List<TransmissionParameters> list = new ArrayList<TransmissionParameters>();
        if(transmissionParametersList != null && !"".equals(transmissionParametersList)){
            String[] tpListStrs = transmissionParametersList.split(Splits.AT);
            for(String str : tpListStrs){
                TransmissionParameters transmissionParameters = new TransmissionParameters();
                String[] tpMapStrs = str.split(Splits.SHARP);
                String layerRate = tpMapStrs[0];
                transmissionParameters.setLayerRate(layerRate);
                if(tpMapStrs != null && tpMapStrs.length ==2){
                    Map<String, String> parameterListMap = new HashMap<String, String>();
                    String[] parameterListStrs = tpMapStrs[1].split(Splits.WAVE);
                    for(String parameterList : parameterListStrs){
                        String[] parameterStrs = parameterList.split(Splits.EQUAL);
                        if(parameterStrs != null && parameterStrs.length ==2){
                            parameterListMap.put(parameterStrs[0], parameterStrs[1] + "");
                        }
                    }
                    transmissionParameters.setParameterList(parameterListMap);
                }
                list.add(transmissionParameters);
            }
        }
        return list;
    }

}
