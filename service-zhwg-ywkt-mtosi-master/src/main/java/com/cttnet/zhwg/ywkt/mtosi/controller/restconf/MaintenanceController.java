package com.cttnet.zhwg.ywkt.mtosi.controller.restconf;

import com.cttnet.common.enums.ResponseData;
import com.cttnet.zhwg.ywkt.mtosi.data.dto.request.*;
import com.cttnet.zhwg.ywkt.mtosi.data.dto.response.ResponseOfDisablePRBSTest;
import com.cttnet.zhwg.ywkt.mtosi.data.dto.response.ResponseOfEnablePRBSTest;
import com.cttnet.zhwg.ywkt.mtosi.data.dto.response.ResponseOfGetPRBSTestResult;
import com.cttnet.zhwg.ywkt.mtosi.data.dto.response.ResponseOfGetRoundTripDelayResult;
import com.cttnet.zhwg.ywkt.mtosi.service.restconf.MaintenanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 *  网管维护操作Controller
 * </pre>
 *
 * @author zhangyaomin
 * @date 2019/12/28
 * @since java 1.8
 */
@Api(value = "maintenance", tags = "维护控制层")
@RestController
@RequestMapping(value = {"${server.prefix:${spring.application.name:}}/restconf/maintenance"})
public class MaintenanceController {


    private MaintenanceService maintenanceService;

    @Autowired
    public MaintenanceController(MaintenanceService maintenanceService){
        this.maintenanceService = maintenanceService;
    }

    @ApiOperation(value = "disablePRBSTest", notes = "关闭PRBS测试", httpMethod = "POST")
    @PostMapping("disablePRBSTest")
    public ResponseData<?> disablePRBSTest(@RequestBody @Validated RequestOfDisablePRBSTest request) {

        return maintenanceService.disablePRBSTest(request);
    }

    @ApiOperation(value = "enablePRBSTest", notes = "启动PRBS测试", httpMethod = "POST")
    @PostMapping("enablePRBSTest")
    public ResponseData<?> enablePRBSTest(@RequestBody @Validated RequestOfEnablePRBSTest request) {

        return  maintenanceService.enablePRBSTest(request);
    }

    @ApiOperation(value = "getPRBSTestResult", notes = "查询PRBS测试结果", httpMethod = "POST")
    @PostMapping("getPRBSTestResult")
    public ResponseData<?> getPRBSTestResult(@RequestBody @Validated RequestOfGetPRBSTestResult request) {

        return maintenanceService.getPRBSTestResult(request);

    }

    @ApiOperation(value = "getRoundTripDelayResult", notes = "查询OTN业务时延", httpMethod = "POST")
    @PostMapping("getRoundTripDelayResult")
    public ResponseData<?> getRoundTripDelayResult(@RequestBody @Validated RequestOfGetRoundTripDelayResult request) {

        return maintenanceService.getRoundTripDelayResult(request);
    }

    @ApiOperation(value = "measureRoundTripDelay", notes = "配置OTN业务时延", httpMethod = "POST")
    @PostMapping("measureRoundTripDelay")
    public ResponseData<?> measureRoundTripDelay(@RequestBody @Validated RequestOfMeasureRoundTripDelay request) {

        return maintenanceService.measureRoundTripDelay(request);
    }


    @ApiOperation(value = "operationLoopback", notes = "端口环回", httpMethod = "POST")
    @PostMapping("operationLoopback")
    public ResponseData<?> operationLoopback(@RequestBody @Validated RequestOfOperationLoopback request) {

        return maintenanceService.operationLoopback(request);
    }
    @ApiOperation(value = "operationLaserSwitchOff", notes = "打开、关闭端口激光器", httpMethod = "POST")
    @PostMapping("operationLaserSwitchOff")
    public ResponseData<?> operationLaserSwitchOff(@RequestBody @Validated RequestOfOperationLaserSwitchOff request) {

        return maintenanceService.operationLaserSwitchOff(request);
    }
    @ApiOperation(value = "operationCardReset", notes = "单板复位", httpMethod = "POST")
    @PostMapping("operationCardReset")
    public ResponseData<?> operationCardReset(@RequestBody @Validated RequestOfOperationCardReset request) {

        return maintenanceService.operationCardReset(request);
    }
    @ApiOperation(value = "getActiveMaintenanceOperations", notes = "查询网元上所有端口维护操作状态", httpMethod = "POST")
    @PostMapping("getActiveMaintenanceOperations")
    public ResponseData<?> getActiveMaintenanceOperations(@RequestBody @Validated RequestOfGetActiveMaintenanceOperations request) {

        return maintenanceService.getActiveMaintenanceOperations(request);
    }

}
