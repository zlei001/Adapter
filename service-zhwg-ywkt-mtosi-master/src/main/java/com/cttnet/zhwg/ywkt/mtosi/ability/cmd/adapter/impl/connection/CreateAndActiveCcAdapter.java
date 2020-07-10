package com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.connection;

import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.AbstractCmdAdapter;
import com.cttnet.zhwg.ywkt.mtosi.ability.constants.Splits;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.*;
import com.cttnet.zhwg.ywkt.mtosi.ability.utils.BusinessUtils;
import com.cttnet.zhwg.ywkt.mtosi.ability.utils.CrossUtils;
import com.cttnet.zhwg.ywkt.mtosi.utils.EnumUtils;
import org.apache.commons.collections.MapUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *  创建激活交叉连接
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/1/7
 * @since java 1.8
 */
public class CreateAndActiveCcAdapter extends AbstractCmdAdapter {


    @Override
    protected void createKvs() {

        //调整参数方向
        adjustParam();
        //固定参数
        final boolean isActive = true;
        /* 原宿对象 */
        String aEndRefList = MapUtils.getString(this.param, "S_A_END_VENDOR_NAME");
        String zEndRefList = MapUtils.getString(this.param, "S_Z_END_VENDOR_NAME");
        String vendorExtensions;
        if (aEndRefList.contains(Splits.SHARP) || zEndRefList.contains(Splits.SHARP)) {
            vendorExtensions = this.getSncpVendorExtensions();
        } else {
            vendorExtensions = this.getVendorExtensions();
        }
        Map<String, String> ccMap = this.command.getKvs();
        ccMap.put("isActive", String.valueOf(isActive));
        ccMap.put("direction", MapUtils.getString(this.param, "I_DIRECTION", ""));
        ccMap.put("/E/B/c/c/ccType", MapUtils.getString(this.param, "I_CC_TYPE", ""));
        ccMap.put("aEndRefList", aEndRefList);
        ccMap.put("zEndRefList", zEndRefList);
        if(Cmd.activateCrossConnection == this.cmd){
            ccMap.put("/E/B/a/c/vendorExtensions", vendorExtensions);
        } else {
            ccMap.put("/E/B/c/c/vendorExtensions", vendorExtensions);

        }
    }

    /**
     * cmd
     *
     * @return cmd
     */
    @Override
    protected Cmd getCmd() {
        return Cmd.createAndActivateCrossConnection;
    }

    /**
     * 调整参数
     */
    private void adjustParam() {

        // 如果厂家是中兴且是 A -> Z1Z2， 则转换成 A1A2 -> Z （A端Z端交换）
        if(FactoryName.ZTE == getFactoryName()
                && !MapUtils.getString(this.param, "S_A_END_VENDOR_NAME", "").contains(Splits.SHARP)
                && MapUtils.getString(this.param, "S_Z_END_VENDOR_NAME", "").contains(Splits.SHARP)){
            // 交换A端Z端
            String temp  = MapUtils.getString(this.param, "S_A_END_VENDOR_NAME", "");
            this.param.put("S_A_END_VENDOR_NAME", this.param.get("S_Z_END_VENDOR_NAME"));
            this.param.put("S_Z_END_VENDOR_NAME", temp);
            // 交换时隙
            temp = MapUtils.getString(this.param, "S_A_END_TIME_SLOT");
            this.param.put("S_A_END_TIME_SLOT", this.param.get("S_Z_END_TIME_SLOT"));
            this.param.put("S_Z_END_TIME_SLOT", temp);
            this.param.put("I_CC_TYPE", 1);
            // 改变ccType
        }
        String aEndRefList = MapUtils.getString(this.param, "S_A_END_VENDOR_NAME", "");
        String zEndRefList = MapUtils.getString(this.param, "S_Z_END_VENDOR_NAME", "");
        // 源宿端修正
        boolean containsSourceDirection = BusinessUtils.isContainsSourceDirection(aEndRefList);
        boolean sameDirection = BusinessUtils.isSameDirection(aEndRefList, zEndRefList);
        if(FactoryName.ZTE == getFactoryName() && (containsSourceDirection || sameDirection)) {
            this.param.put("S_A_END_VENDOR_NAME", CrossUtils.correctAEndRefList(aEndRefList));
            this.param.put("S_Z_END_VENDOR_NAME", CrossUtils.correctAEndRefList(zEndRefList));
        }
        //方向:单向、双向
        String direction = MapUtils.getString(this.param, "I_DIRECTION", "");
        //交叉类型：简单交叉 保护交叉
        String ccType = MapUtils.getString(this.param, "I_CC_TYPE", "");
        //转换枚举
        Direction directionEnum = EnumUtils.getEnum(Direction.class, direction, "getCode");
        if (directionEnum != null) {
            this.param.put("I_DIRECTION", directionEnum.getName());
        }
        CcType ccTypeEnum = EnumUtils.getEnum(CcType.class, ccType, "getCode");
        if (ccTypeEnum != null) {
            this.param.put("I_CC_TYPE", ccTypeEnum.getName());
        }
    }



    /**
     * 获取附加信息
     * @return 附加信息
     */
    private String getSncpVendorExtensions() {

        Map<String, String> vendorExtensions = getVendorExtensionsMap();
        //SNCP下发设置默认参数
        vendorExtensions.put("holdOffTime", MapUtils.getString(this.param, "holdOffTime", "300"));
        vendorExtensions.put("wtrTime", MapUtils.getString(this.param, "wtrTime", "300"));
        vendorExtensions.put("monitorType", MapUtils.getString(this.param, "monitorType", "SNC/I"));
        vendorExtensions.put("reversionMode", MapUtils.getString(this.param, "reversionMode", "REVERTIVE"));
        vendorExtensions.put("protectionType", MapUtils.getString(this.param, "protectionType", ProtectionType._1_PLUS_1.getName()));
        return BusinessUtils.toNvsList(vendorExtensions);
    }

    /**
     * 获取附加信息
     * @return 附加信息
     */
    private String getVendorExtensions() {
        Map<String, String> vendorExtensions = getVendorExtensionsMap();
        return BusinessUtils.toNvsList(vendorExtensions);
    }

    /**
     * 获取 VendorExtensions
     * @return VendorExtensions
     */
    private Map<String, String> getVendorExtensionsMap() {

        String aEndTimeSlot = MapUtils.getString(this.param, "S_A_END_TIME_SLOT");
        String zEndTimeSlot = MapUtils.getString(this.param, "S_Z_END_TIME_SLOT");
        String clientServiceType = MapUtils.getString(this.param, "S_CLIENT_SERVICE_TYPE");
        String clientServiceContainer = MapUtils.getString(this.param, "S_CLIENT_SERVICE_CONTAINER");
        String clientServiceMappingMode = MapUtils.getString(this.param, "S_TP_MAPPING_MODE");
        Map<String, String> vendorExtensions = new HashMap<>(5); {
            vendorExtensions.put("ClientServiceType", clientServiceType);
            vendorExtensions.put("ClientServiceContainer", clientServiceContainer);
            vendorExtensions.put("ClientServiceMappingMode", clientServiceMappingMode);
            vendorExtensions.putAll(BusinessUtils.toTimeSlotMap(aEndTimeSlot, zEndTimeSlot));
            vendorExtensions.put("aliasName", MapUtils.getString(this.param, "aliasNameList"));
        }
        return vendorExtensions;
    }


}
