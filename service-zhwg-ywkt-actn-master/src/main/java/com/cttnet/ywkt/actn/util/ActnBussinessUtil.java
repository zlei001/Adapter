package com.cttnet.ywkt.actn.util;

import com.cttnet.ywkt.actn.enums.AccessPointTypeEnum;
import com.cttnet.ywkt.actn.enums.ClientSignalEnum;
import com.cttnet.ywkt.actn.enums.OduTypeEnum;
import com.cttnet.ywkt.actn.enums.VcTypeEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 *  ACTN业务下发转换类
 * </pre>
 * <b>项目：</b> & <b>技术支持：凯通科技股份有限公司 (c) 2019 </b>
 *
 * @author zhangyaomin
 * @version 1.0
 * @since jdk 1.8
 */
@Slf4j
public class ActnBussinessUtil {

    /** 1G实际大小, 单位K */
    public static final Long _20M = 20_000L;
    public static final Long _60M = 60_000L;
    public static final Long _100M = 1_00_000L;
    public static final Long _500M = 5_00_000L;
    public static final Long _1G = 1_000_000L;
    public static final Long _1_25G = 1_250_000L;
    public static final Long _2_5G = 2_500_000L;
    public static final Long _9G = 9_000_000L;
    public static final Long _10G = 10_000_000L;
    public static final Long _11G = 11_000_000L;
    public static final Long _40G = 40_000_000L;
    public static final Long _80G = 80_000_000L;
    public static final Long _90G = 90_000_000L;
    public static final Long _100G = 100_000_000L;

//    /**
//     * 通过接口类型和带宽判断下发业务类型 (北京环境)
//     * @param pointTypeA
//     * @param pointTypeZ
//     * @param bandwidth
//     * @return
//     */
//    public static BusinessType getBusiness(AccessPointTypeEnum pointTypeA, AccessPointTypeEnum pointTypeZ, Long bandwidth,String from) {
//
//        BusinessType businessType;
//        if("shanghai".equals(from) ){
//            businessType = getBusinessByShanghai(pointTypeA,pointTypeZ,bandwidth);
//        }else {
//            businessType = getBusinessByBeijing(pointTypeA,pointTypeZ,bandwidth);
//        }
//        return businessType;
//    }

//    /**
//     * 通过接口类型和带宽判断下发业务类型 (北京环境)
//     * @param pointTypeA
//     * @param pointTypeZ
//     * @param bandwidth
//     * @return
//     */
//    private static BusinessType getBusinessByBeijing(AccessPointTypeEnum pointTypeA, AccessPointTypeEnum pointTypeZ, Long bandwidth) {
//
//        if (AccessPointTypeEnum.STM1 == pointTypeA || AccessPointTypeEnum.STM1 == pointTypeZ
//                || AccessPointTypeEnum.E1 == pointTypeA || AccessPointTypeEnum.E1 == pointTypeZ) {
//            //FIXME 下发SDH业务，暂不支持
//            return BusinessType.ETH_EOS;
//        }
//        if (AccessPointTypeEnum.STM4 == pointTypeA || AccessPointTypeEnum.STM4 == pointTypeZ
//                || AccessPointTypeEnum.STM16 == pointTypeA || AccessPointTypeEnum.STM16 == pointTypeZ
//                || AccessPointTypeEnum.STM64 == pointTypeA || AccessPointTypeEnum.STM64 == pointTypeZ
//                || AccessPointTypeEnum.OTU2 == pointTypeA || AccessPointTypeEnum.OTU2 == pointTypeZ
//                || AccessPointTypeEnum.OTU4 == pointTypeA || AccessPointTypeEnum.OTU4 == pointTypeZ) {
//            return BusinessType.CLIENT;
//        }
//        if (AccessPointTypeEnum.ELECTRONIC_FE == pointTypeA || AccessPointTypeEnum.OPTICAL_FE == pointTypeA
//                || AccessPointTypeEnum.ELECTRONIC_FE == pointTypeZ || AccessPointTypeEnum.OPTICAL_FE == pointTypeZ){
////            if(bandwidth >= _100M ) {
////                return BusinessType.ETH_EOO;
////            }else{
////                return BusinessType.ETH_EOS;
////            }
//            //TODO 待改-临时使用
//            return BusinessType.ETH_EOO;
//        } else {
//            //根据带宽判断下发业务
//            //FIXME 根据华为要求1G下发到EOO业务
////            if (bandwidth > _1G) {
////                return BusinessType.CLIENT;
////            } else if(bandwidth >= _100M && bandwidth <= _1G) {
////                return BusinessType.ETH_EOO;
////            }else{
////                return BusinessType.ETH_EOS;
////            }
//            //TODO 待改-临时使用
//            if (bandwidth > _1G) {
//                return BusinessType.CLIENT;
//            } else {
//                return BusinessType.ETH_EOO;
//            }
//        }
//    }

//    /**
//     * 通过接口类型和带宽判断下发业务类型 (上海环境)
//     * @param pointTypeA
//     * @param pointTypeZ
//     * @param bandwidth
//     * @return
//     */
//    private static BusinessType getBusinessByShanghai(AccessPointTypeEnum pointTypeA, AccessPointTypeEnum pointTypeZ, Long bandwidth) {
//
//        if (AccessPointTypeEnum.STM1 == pointTypeA || AccessPointTypeEnum.STM1 == pointTypeZ) {
//            //FIXME 下发SDH业务，暂不支持
//            return BusinessType.SDH;
//        }
//        if (AccessPointTypeEnum.STM4 == pointTypeA || AccessPointTypeEnum.STM4 == pointTypeZ
//                || AccessPointTypeEnum.STM16 == pointTypeA || AccessPointTypeEnum.STM16 == pointTypeZ
//                || AccessPointTypeEnum.STM64 == pointTypeA || AccessPointTypeEnum.STM64 == pointTypeZ
//                || AccessPointTypeEnum.OTU2 == pointTypeA || AccessPointTypeEnum.OTU2 == pointTypeZ
//                || AccessPointTypeEnum.OPTICAL_10GE_WAN == pointTypeA || AccessPointTypeEnum.OPTICAL_10GE_WAN == pointTypeZ) {
//            return BusinessType.CLIENT;
//        }
//
//        if (bandwidth <= _100M) {
//            return BusinessType.ETH_EOS;
//        } else {
//            return BusinessType.ETH_EOO;
//        }
//
//    }

    /**
     * 通过接口类型获取透传业务信号类型
     * @param pointTypeA
     * @param pointTypeZ
     * @return
     */
    public static ClientSignalEnum getClientSingal(AccessPointTypeEnum pointTypeA, AccessPointTypeEnum pointTypeZ) {

        AccessPointTypeEnum min = pointTypeA.getWeight() > pointTypeZ.getWeight() ? pointTypeZ : pointTypeA;
        ClientSignalEnum clientSignal = null;
        switch (min) {
            case STM1:
                clientSignal = ClientSignalEnum._STM1;
                break;
            case STM4:
                clientSignal = ClientSignalEnum._STM4;
                break;
            case STM16:
                clientSignal = ClientSignalEnum._STM16;
                break;
            case STM64:
                clientSignal = ClientSignalEnum._STM64;
                break;
            case OTU2:
                clientSignal = ClientSignalEnum._ODU2;
                break;
            case OTU4:
                clientSignal = ClientSignalEnum._ODU4;
                break;
            case OPTICAL_GE:
            case ELECTRONIC_GE:
                clientSignal = ClientSignalEnum._GE;
                break;
            case OPTICAL_10GE:
                clientSignal = ClientSignalEnum._10GE_LAN;
                break;
            case OPTICAL_10GE_LAN:
                clientSignal = ClientSignalEnum._10GE_LAN;
                break;
            case OPTICAL_10GE_WAN:
                clientSignal = ClientSignalEnum._10GE_WAN;
                break;
            case OPTICAL_100GE:
                clientSignal = ClientSignalEnum._100GE;
                break;
                default:
                    clientSignal = null;
        }

        return clientSignal;
    }

    /**
     * 通过带宽获取颗粒度
     * @param pointTypeA
     * @param pointTypeZ
     * @param bandwidth
     * @return
     */
    public static String getGraininess(AccessPointTypeEnum pointTypeA, AccessPointTypeEnum pointTypeZ, Long bandwidth) {

        String oduType;
        if (bandwidth <= _20M) {
            oduType = VcTypeEnum.VC12.getValue();
        } else if (bandwidth <= _60M) {
            oduType = VcTypeEnum.VC3.getValue();
        } else if (bandwidth <= _100M) {
            oduType = VcTypeEnum.VC4.getValue();
            //ODU0实际带宽是1.25G
        } else if (bandwidth <= _1_25G) {
            oduType = OduTypeEnum.ODU0.getValue();
        } else if (bandwidth <= _2_5G) {
            oduType = OduTypeEnum.ODU1.getValue();
        } else if (bandwidth <= _9G) {
            oduType = OduTypeEnum.ODU_FLEX.getValue();
        } else if (bandwidth <= _10G) {
            if (pointTypeA == AccessPointTypeEnum.OPTICAL_100GE && pointTypeZ == AccessPointTypeEnum.OPTICAL_100GE) {
                oduType = OduTypeEnum.ODU_FLEX.getValue();
            }else {
                oduType = OduTypeEnum.ODU2E.getValue();
            }
        } else if (bandwidth <= _80G) {
            oduType = OduTypeEnum.ODU_FLEX.getValue();
        } else {
            oduType = OduTypeEnum.ODU4.getValue();
        }
        return oduType;
    }

//    /**
//     * 解析actn端口id
//     * @param actnPortId
//     * @return
//     */
//    public static PhysicalPort getPortInfo(Long actnPortId) {
//
//        PhysicalPort physicalPort = new PhysicalPort();
//        physicalPort.setShelfId((int) (actnPortId >> 32));
//        physicalPort.setBoardId((int) ((actnPortId >> 24) & 0xff));
//        physicalPort.setSubCardId((int) ((actnPortId >> 26) & 0xff));
//        physicalPort.setPortId((int) (actnPortId & 0xff));
//        return  physicalPort;
//    }



    /**
     * 根据速率进行带宽转换
     * NCE里面，EOO业务只支持M为单位，1G = 1000M；
     * EOS业务默认单位是K，1M = 1000K，1G = 1000M；
     * @param circuitRate
     * @return
     */
    public static Long getBandwidth(String circuitRate) {
        Long bandWidth = 0L;
        try {
            double result = 0.0;
            double rateTemp = Double.parseDouble(circuitRate.substring(0 , circuitRate.length() - 1));
            if( circuitRate.endsWith("M") || circuitRate.endsWith("m") ) {
                result = rateTemp * 1000;
            }else if( circuitRate.endsWith("K") || circuitRate.endsWith("k") ) {
                result = rateTemp;
            }else if( circuitRate.endsWith("G") ||  circuitRate.endsWith("g") ) {
                result = rateTemp * 1000 * 1000;
            }
            bandWidth = (long) Math.ceil(result);
        }catch(Exception e) {
            log.info("速率转换带宽(K)异常!,速率单位不为K、M、G" );
        }
        return bandWidth;
    }

}
