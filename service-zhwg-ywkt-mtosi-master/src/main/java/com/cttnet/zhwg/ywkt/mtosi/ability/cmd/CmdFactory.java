package com.cttnet.zhwg.ywkt.mtosi.ability.cmd;


import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.AbstractCmdAdapter;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.NonSupportAdapter;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.common.SetCommonAttributesAdapter;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.connection.*;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.maintenance.*;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.multilayersubnetwork.TakeOverSncAdapter;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.protection.*;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.terminationpoint.GetTerminationPointAdapter;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter.impl.terminationpoint.SetTerminationPointDataAdapter;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;

/**
 * <pre>
 *  南向CMD统一处理
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/2/13
 * @since java 1.8
 */
public class CmdFactory {


    /**
     * 获取实例
     * @param cmd cmd
     * @return Adapter
     */
    public static AbstractCmdAdapter getInstance(Cmd cmd) {

        AbstractCmdAdapter adapter = null;
        switch (cmd) {
            case createAndActivateCrossConnection:
                adapter = new CreateAndActiveCcAdapter();
                break;
            case createAndActivateSubnetWorkConnection:
                adapter = new CreateAndActiveSncAdapter();
                break;
            case deactivateAndDeleteSubnetWorkConnection:
                adapter = new DeactivateAndDeleteSncAdapter();
                break;
            case deactivateAndDeleteCrossConnection:
                adapter = new DeactivateAndDeleteCcAdapter();
                break;
            case takeOverSnc:
                adapter = new TakeOverSncAdapter();
                break;
            case setCommonAttributes:
                adapter = new SetCommonAttributesAdapter();
                break;
            case setTerminationPointData:
                adapter = new SetTerminationPointDataAdapter();
                break;
            case getTerminationPoint:
                adapter = new GetTerminationPointAdapter();
                break;
            case operationLoopback:
                adapter = new OperationLoopbackAdapter();
                break;
            case operationCardReset:
                adapter = new OperationCardResetAdapter();
                break;
            case operationLaserSwitchOff:
                adapter = new OperationLaserSwitchOffAdapter();
                break;
            case enablePRBSTest:
                adapter = new EnablePRBSTestAdapter();
                break;
            case disablePRBSTest:
                adapter = new DisablePRBSTestAdapter();
                break;
            case getPRBSTestResult:
                adapter = new GetPRBSTestResultAdapter();
                break;
            case measureRoundTripDelay:
                adapter = new MeasureRoundTripDelayAdapter();
                break;
            case getRoundTripDelayResult:
                adapter = new GetRoundTripDelayResultAdapter();
                break;
            case getAllProtectionGroups:
                adapter = new GetAllProtectionGroupsAdapter();
                break;
            case createProtectionGroup:
                adapter = new CreateProtectionGroupAdapter();
                break;
            case deleteProtectionGroup:
                adapter = new DeleteProtectionGroupAdapter();
                break;
            case retrieveSwitchData:
                adapter = new RetrieveSwitchDataAdapter();
                break;
            case modifyProtectionGroup:
                adapter = new ModifyProtectionGroupAdapter();
                break;
            case createCrossConnection:
                adapter = new CreateCcAdapter();
                break;
            case createSubnetWorkConnection:
                adapter = new CreateSncAdapter();
                break;
            case activateCrossConnection:
                adapter = new ActivateCcAdapter();
                break;
            case activateSubnetWorkConnection:
                adapter = new ActiveSncAdapter();
                break;
            case deactivateCrossConnection:
                adapter = new DeactivateCcAdapter();
                break;
            case deactivateSubnetWorkConnection:
                adapter = new DeactivateSncAdapter();
                break;
            case deleteCrossConnection:
                adapter = new DeleteCcAdapter();
                break;
            case deleteSubnetWorkConnection:
                adapter = new DeleteSncAdapter();
                break;
            case getActiveMaintenanceOperations:
                adapter = new GetActiveMaintenanceOperationsAdapter();
                break;
            default:
                adapter = new NonSupportAdapter();
                break;
        }
        return adapter;
    }







}
