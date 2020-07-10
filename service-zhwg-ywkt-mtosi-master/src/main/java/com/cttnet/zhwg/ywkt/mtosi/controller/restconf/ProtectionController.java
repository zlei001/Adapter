package com.cttnet.zhwg.ywkt.mtosi.controller.restconf;

import com.cttnet.common.enums.ResponseData;
import com.cttnet.zhwg.ywkt.mtosi.data.dto.request.*;
import com.cttnet.zhwg.ywkt.mtosi.service.restconf.ProtectionService;
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
 *  保护组Controller
 * </pre>
 *
 * @author dengkaihong
 * @date 2020/4/14
 * @since java 1.8
 */
@Api(value="protection", tags="保护组控制层")
@RequestMapping(value = {"${server.prefix:${spring.application.name:}}/restconf/protection"})
@RestController
public class ProtectionController {

    private final ProtectionService protectionService;

    @Autowired
    public ProtectionController(ProtectionService protectionService) {
        this.protectionService = protectionService;
    }


    @ApiOperation(value = "getAllProtectionGroups", notes = "查询所有保护组", httpMethod = "POST")
    @PostMapping("getAllProtectionGroups")
    public ResponseData<?> getAllProtectionGroups(@RequestBody @Validated RequestOfGetAllProtectionGroups request) {

        return protectionService.getAllProtectionGroups(request);
    }

    @ApiOperation(value = "modifyProtectionGroup", notes = "修改保护组", httpMethod = "POST")
    @PostMapping("modifyProtectionGroup")
    public ResponseData<?> modifyProtectionGroup(@RequestBody  @Validated RequestOfModifyProtectionGroup request) {

        return protectionService.modifyProtectionGroup(request);
    }
    @ApiOperation(value = "createProtectionGroup", notes = "创建保护组", httpMethod = "POST")
    @PostMapping("createProtectionGroup")
    public ResponseData<?> createProtectionGroup(@RequestBody @Validated RequestOfCreateProtectionGroup request) {

        return protectionService.createProtectionGroup(request);
    }
    @ApiOperation(value = "deleteProtectionGroup", notes = "删除保护组", httpMethod = "POST")
    @PostMapping("deleteProtectionGroup")
    public ResponseData<?> deleteProtectionGroup(@RequestBody  @Validated RequestOfDeleteProtectionGroup request) {
        return protectionService.deleteProtectionGroup(request);
    }
    @ApiOperation(value = "retrieveSwitchData", notes = "查询保护组倒换状态", httpMethod = "POST")
    @PostMapping("retrieveSwitchData")
    public ResponseData<?> retrieveSwitchData(@RequestBody  @Validated RequestOfRetrieveSwitchData request) {
        return protectionService.retrieveSwitchData(request);
    }
}
