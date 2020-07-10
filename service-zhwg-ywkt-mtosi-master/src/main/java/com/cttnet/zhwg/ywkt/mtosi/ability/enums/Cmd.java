package com.cttnet.zhwg.ywkt.mtosi.ability.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 *  南向接口命令
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/1/10
 * @since java 1.8
 */
@AllArgsConstructor
public enum Cmd {

    /**
     * 命令， copy from com.catt.mtosi.atomic.capacity.envm.Cmds
     */

    planUseCrossConnection("planUseCrossConnection", "计划占用交叉连接 "),
    unPlanUseCrossConnection("unPlanUseCrossConnection", "取消计划预占用交叉连接 "),

    createAndActivateCrossConnection("createAndActivateCrossConnection", "创建并激活交叉连接"),
    activateCrossConnection("activateCrossConnection", "激活交叉连接"),
    createCrossConnection("createCrossConnection", "创建交叉连接"),

    deactivateAndDeleteCrossConnection("deactivateAndDeleteCrossConnection","去激活并删除交叉连接"),
    deactivateCrossConnection("deactivateCrossConnection","去激活交叉连接"),
    deleteCrossConnection("deleteCrossConnection","删除交叉连接"),

    createAndActivateSubnetWorkConnection("createAndActivateSubnetworkConnection","创建并激活子网连接"),
    createSubnetWorkConnection("createSubnetWorkConnection","创建子网连接"),
    activateSubnetWorkConnection("activateSubnetWorkConnection","激活子网连接"),

    deactivateAndDeleteSubnetWorkConnection("deactivateAndDeleteSubnetworkConnection","去激活并删除子网连接"),
    deactivateSubnetWorkConnection("deactivateSubnetWorkConnection","去激活子网连接"),
    deleteSubnetWorkConnection("deleteSubnetWorkConnection","删除子网连接"),

    takeOverSnc("takeOverSNC","路径搜索"),
    setTerminationPointData("setTerminationPointData","设置端口属性"),
    setCommonAttributes("setCommonAttributes","修改路径名称"),
    getTerminationPoint("getTerminationPoint","查询端口层参数"),
    // 端口环回，开关激光器，单板复位用的是同一套模板 name相同
    operationLoopback("performMaintenanceOperation","端口环回"),
    operationLaserSwitchOff("performMaintenanceOperation","开关端口激光器"),
    operationCardReset("performMaintenanceOperation","单板复位"),

    enablePRBSTest("enablePRBSTest","启动PRBS测试"),
    disablePRBSTest("disablePRBSTest","关闭PRBS测试"),
    getPRBSTestResult("getPRBSTestResult","查询PRBS测试结果"),

    measureRoundTripDelay("measureRoundTripDelay","配置OTN业务时延"),
    getRoundTripDelayResult("getRoundTripDelayResult","查询OTN业务时延"),

    getActiveAlarms("getActiveAlarms","查询激活的告警"),
    deleteProtectionGroup("deleteProtectionGroup","删除保护组"),
    modifyProtectionGroup("modifyProtectionGroup","修改保护组"),
    getAllProtectionGroups("getAllProtectionGroups","查询所有保护组"),
    createProtectionGroup("createProtectionGroup","创建保护组"),
    retrieveSwitchData("retrieveSwitchData","查询保护组倒换状态"),

    getActiveMaintenanceOperations("getActiveMaintenanceOperations","查询网元上所有端口维护操作状态,"),


    ;
    /** 标识编码 */
    @Getter
    private String name;
    /** 描述 */
    @Getter
    private String desc;

}
