package com.cttnet.zhwg.ywkt.mtosi.ability.utils;

import com.cttnet.zhwg.ywkt.mtosi.ability.constants.Splits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * <PRE>
 * 保护组工具
 * </PRE>
 *
 * @author dengkaihong
 * @date 2020/3/27
 * @since java 1.8
 */
public class PgUtils {
    private static final Logger LOG = LoggerFactory.getLogger(PgUtils.class);

    // \\name=MD\\value=Huawei/U2000\\name=ME\\value=3145732\\name=PG\\value=/pg=6/type=8
    public static String getHwI2PgRef(String factoryValue) {
        String pgRef = factoryValue;
        String[] kvs = new String[]{};
        if(factoryValue.contains("\\\\")){
            kvs = factoryValue.split("\\\\");
            pgRef = getHwPgRefBySlash(kvs);

        } else if(factoryValue.contains("\\")){
            kvs = factoryValue.split("\\\\");
            pgRef = getHwPgRefBySlash(kvs);

        } else if(factoryValue.contains(";")){
            pgRef = getHwPgRefByDn(factoryValue);

        }
        return pgRef;
    }

    // MD=Huawei/U2000;ME=3145732;PG=/pg=2/type=8;
    private static String getHwPgRefByDn(String dn){
        StringBuffer pgRef = new StringBuffer();
        String[] parts = dn.split(";");
        for (String part : parts) {
            pgRef.append(Splits.WAVE).append(part);
        }
        return pgRef.toString();
    }

    private static String getHwPgRefBySlash(String[] kvs) {
        StringBuffer pgRef = new StringBuffer();
        try {
            for (int i = 2; i < kvs.length; i += 2) {
                String key = getHwI2Value(kvs[i - 1]);
                String value = getHwI2Value(kvs[i]);
                pgRef.append(Splits.WAVE).append(key).append(Splits.EQUAL).append(value);
            }
        } catch (Exception e) {
            LOG.error("", e);
        }
        return pgRef.toString();
    }

    private static String getHwI2Value(String object) {
        String value = "";
        int idx = object.indexOf(Splits.EQUAL);
        if (idx > 0) {
            value = object.substring(idx + 1, object.length());
        }
        return value;
    }

    public static String getZteI2PgRef(String endRef, String pgRef) {
        StringBuffer pg = new StringBuffer();
        String me = ObjectTools.split(endRef, "~MD=", "~PTP=", true, false);
        pg.append(me).append("~PG=").append(pgRef);
        return pg.toString();
    }

    public static void main(String[] args) {
        String endRef = "~MD=ZTE/U31(BN)~ME=BN:109675392~PTP=/direction=sink/rack=0/shelf=22/slot=62/type=OCH_TTP_Si/port=1~CTP=/frequency=0.000/odu4=1##~MD=ZTE/U31(BN)~ME=BN: 109675392~PTP=/direction=sink/rack=0/shelf=22/slot=58/type=OCH_TTP_Si/port=1~CTP=/frequency=0.000/odu4=1";
        String pgRef = "123";
        System.out.println(getZteI2PgRef(endRef, pgRef));

        String dn = "MD=Huawei/U2000;ME=3145732;PG=/pg=2/type=8;";
        System.out.println(getHwPgRefByDn(dn));

    }
}
