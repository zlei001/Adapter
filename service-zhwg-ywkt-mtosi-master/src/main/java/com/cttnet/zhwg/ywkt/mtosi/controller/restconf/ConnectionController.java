package com.cttnet.zhwg.ywkt.mtosi.controller.restconf;

import com.cttnet.common.enums.ResponseData;
import com.cttnet.zhwg.ywkt.mtosi.data.dto.request.*;
import com.cttnet.zhwg.ywkt.mtosi.service.restconf.ConnectionService;
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
 *  南向下发Controller
 * </pre>
 *
 * @author zhangyaomin
 * @date 2019/12/28
 * @since java 1.8
 */
@Api(value = "connection", tags = "连接控制层")
@RestController
@RequestMapping(value = {"${server.prefix:${spring.application.name:}}/restconf/connection"})
public class ConnectionController {

    private ConnectionService connectionService;

    @Autowired
    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @ApiOperation(value = "createAndActivateCrossConnection", notes = "创建并激活交叉连接", httpMethod = "POST")
    @PostMapping("createAndActivateCrossConnection")
    public ResponseData<?> createAndActivateCrossConnection(@RequestBody @Validated RequestOfCreateAndActivateCrossConnection request) {

        return connectionService.createAndActivateCrossConnection(request);
    }

    @ApiOperation(value = "createAndActivateSubnetWorkConnection", notes = "创建并激活子网连接", httpMethod = "POST")
    @PostMapping("createAndActivateSubnetWorkConnection")
    public  ResponseData<?> createAndActivateSubnetWorkConnection(@RequestBody @Validated RequestOfCreateAndActiveSubnetWorkConnection request) {

        return connectionService.createAndActivateSubnetWorkConnection(request);
    }

    @ApiOperation(value = "deactivateAndDeleteCrossConnection", notes = "去激活并删除交叉", httpMethod = "POST")
    @PostMapping("deactivateAndDeleteCrossConnection")
    public ResponseData<?> deactivateAndDeleteCrossConnection(@RequestBody @Validated RequestOfDeactivateAndDeleteCrossConnection request) {

        return connectionService.deactivateAndDeleteCrossConnection(request);
    }

    @ApiOperation(value = "deactivateAndDeleteSubnetWorkConnection", notes = "去激活并删除子网", httpMethod = "POST")
    @PostMapping("deactivateAndDeleteSubnetWorkConnection")
    public ResponseData<?> deactivateAndDeleteSubnetWorkConnection(@RequestBody @Validated RequestOfDeactivateAndDeleteSubnetWorkConnection request) {

        return connectionService.deactivateAndDeleteSubnetWorkConnection(request);
    }

    /**
     * 单个适配器和父类的适配器逻辑一样，直接复用父类的service逻辑
     */
    @ApiOperation(value = "createCrossConnection", notes = "创建交叉连接", httpMethod = "POST")
    @PostMapping("createCrossConnection")
    public ResponseData<?> createCrossConnection(@RequestBody @Validated RequestOfCreateAndActivateCrossConnection request) {

        return connectionService.createAndActivateCrossConnection(request);
    }
    @ApiOperation(value = "activateCrossConnection", notes = "激活交叉连接", httpMethod = "POST")
    @PostMapping("activateCrossConnection")
    public ResponseData<?> activateCrossConnection(@RequestBody @Validated RequestOfCreateAndActivateCrossConnection request) {

        return connectionService.createAndActivateCrossConnection(request);
    }
    @ApiOperation(value = "deactivateCrossConnection", notes = "去激活交叉连接", httpMethod = "POST")
    @PostMapping("deactivateCrossConnection")
    public ResponseData<?> deactivateCrossConnection(@RequestBody @Validated RequestOfDeactivateAndDeleteCrossConnection request) {

        return connectionService.deactivateAndDeleteCrossConnection(request);
    }
    @ApiOperation(value = "deleteCrossConnection", notes = "删除交叉连接", httpMethod = "POST")
    @PostMapping("deleteCrossConnection")
    public ResponseData<?> deleteCrossConnection(@RequestBody @Validated RequestOfDeactivateAndDeleteCrossConnection request) {

        return connectionService.deactivateAndDeleteCrossConnection(request);
    }

    @ApiOperation(value = "createSubnetWorkConnection", notes = "创建子网连接", httpMethod = "POST")
    @PostMapping("createSubnetWorkConnection")
    public ResponseData<?> createSubnetWorkConnection (@RequestBody @Validated RequestOfCreateAndActiveSubnetWorkConnection request) {

        return connectionService.createAndActivateSubnetWorkConnection(request);
    }
    @ApiOperation(value = "activateSubnetWorkConnection", notes = "激活子网连接", httpMethod = "POST")
    @PostMapping("activateSubnetWorkConnection")
    public ResponseData<?> activateSubnetWorkConnection (@RequestBody @Validated RequestOfCreateAndActiveSubnetWorkConnection request) {

        return connectionService.createAndActivateSubnetWorkConnection(request);
    }
    @ApiOperation(value = "deactivateSubnetWorkConnection", notes = "去激活子网连接", httpMethod = "POST")
    @PostMapping("deactivateSubnetWorkConnection")
    public ResponseData<?> deactivateSubnetWorkConnection (@RequestBody @Validated RequestOfDeactivateAndDeleteSubnetWorkConnection request) {

        return connectionService.deactivateAndDeleteSubnetWorkConnection(request);
    }
    @ApiOperation(value = "deleteSubnetWorkConnection", notes = "删除子网连接", httpMethod = "POST")
    @PostMapping("deleteSubnetWorkConnection")
    public ResponseData<?> deleteSubnetWorkConnection (@RequestBody @Validated RequestOfDeactivateAndDeleteSubnetWorkConnection request) {

        return connectionService.deactivateAndDeleteSubnetWorkConnection(request);
    }

}
