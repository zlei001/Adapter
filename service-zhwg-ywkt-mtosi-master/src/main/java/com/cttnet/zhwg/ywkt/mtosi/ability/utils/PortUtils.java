package com.cttnet.zhwg.ywkt.mtosi.ability.utils;

/**
 * <pre>
 *  端口工具类
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/1/10
 * @since java 1.8
 */
public class PortUtils {

    public static final String PTP = "~PTP=";
    public static final String FTP = "~FTP=";
    public static final String CTP = "~CTP=";
    public static final String ODU = "LR_OCH_Data_Unit";
    public static final String OTU = "LR_OCH_Transport_Unit";

    /**
     * 是否是PTP
     * @param tpName TP名称
     * @return true为是，false为否
     */
    public static boolean isPTP(final String tpName) {
        boolean isPTP = false;
        if(tpName != null) {
            isPTP = tpName.contains(PTP) & !tpName.contains(CTP);
        }
        return isPTP;
    }

    /**
     * 是否是CTP
     * @param tpName TP名称
     * @return true为是，false为否
     */
    public static boolean isCTP(final String tpName) {

        return tpName != null && tpName.contains(CTP);
    }

    /**
     * 是否是FTP
     * @param tpName TP名称
     * @return true为是，false为否
     */
    public static boolean isFTP(final String tpName) {
        boolean isFTP = false;
        if(tpName != null) {
            isFTP = tpName.contains(FTP);
        }
        return isFTP;
    }

    /**
     * 层速率是否为ODU
     * @param layerRate 层速率
     * @return true为是，false为否
     */
    public static boolean isODU(final String layerRate) {
        boolean isODU = false;
        if(layerRate != null) {
            isODU = layerRate.startsWith(ODU);
        }
        return isODU;
    }

    /**
     * 层速率是否为OTU
     * @param layerRate 层速率
     * @return true为是，false为否
     */
    public static boolean isOTU(final String layerRate) {
        boolean isOTU = false;
        if(layerRate != null) {
            isOTU = layerRate.startsWith(OTU);
        }
        return isOTU;
    }

    /**
     * 返回一对 ttiNames.
     *
     * @param ttiName TTI名称
     * @return 若入参不为ttiName，则返回null.
     * 			否则ttiNames[0]为sapi, ttiNames[1]为dapi
     */
    public static String[] getTTINames(final String ttiName) {
        String[] ttiNames = null;
        if(ttiName != null) {
            if (ttiName.contains("SAPI")) {
                ttiNames = new String[] {
                        ttiName,
                        ttiName.replace("SAPI", "DAPI")
                };

            } else if (ttiName.contains("DAPI")) {
                ttiNames = new String[] {
                        ttiName.replace("DAPI", "SAPI"),
                        ttiName
                };
            }
        }
        return ttiNames;
    }
}
