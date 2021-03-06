package com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.maintenance;

import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.AbstractCmdAdapter;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.FactoryName;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.MaintenanceOperationMode;
import com.cttnet.zhwg.ywkt.mtosi.utils.EnumUtils;
import org.apache.commons.collections.MapUtils;

import java.util.Map;

/**
 * <pre>
 *  单板复位
 * </pre>
 *
 * @author dengkaihong
 * @date 2020/7/3
 * @since java 1.8
 */
public class OperationCardResetAdapter extends AbstractCmdAdapter {

    @Override
    protected void createKvs() {

        adjustParam();

        Map<String, String> ccMap = this.command.getKvs();
        ccMap.put("tpName", MapUtils.getString(this.param, "S_VENDOR_OBJ_NAME", ""));
        ccMap.put("/E/B/p/m/maintenanceOperation", MapUtils.getString(this.param, "maintenanceOperation", ""));
        ccMap.put("maintenanceOperationMode",MapUtils.getString(this.param,"maintenanceOperationMode",""));
        //非烽火网管，设置layerRate属性值
        if(!(FactoryName.FH == getFactoryName())){
            ccMap.put("layerRate", MapUtils.getString(this.param,"layerRate",""));
        }
    }

    /**
     * cmd
     *
     * @return cmd
     */
    @Override
    protected Cmd getCmd() {
        return Cmd.operationCardReset;
    }

    private void adjustParam() {

        String maintenanceOperationMode = MapUtils.getString(this.param, "maintenanceOperationMode","");
        //转换枚举
        MaintenanceOperationMode maintenance = EnumUtils.getEnum(MaintenanceOperationMode.class, maintenanceOperationMode, "getCode");
        if (maintenance != null) {
            this.param.put("maintenanceOperationMode", maintenance.getName());
        }
    }


}
