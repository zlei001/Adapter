package com.cttnet.zhwg.ywkt.mtosi.controller.restconf;

import com.cttnet.common.controller.BaseController;
import com.cttnet.common.enums.ResponseData;
import com.cttnet.zhwg.ywkt.mtosi.data.dto.request.RequestOfGetAllManagementDomains;
import com.cttnet.zhwg.ywkt.mtosi.data.dto.response.ResponseOfGetAllManagementDomains;
import com.cttnet.zhwg.ywkt.mtosi.service.restconf.ManagementDomainService;
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
 *  管理域Controller
 * </pre>
 *
 * @author zhangyaomin
 * @date 2019/12/28
 * @since java 1.8
 */
@Api(value="configCollect", tags="管理域数据层")
@RequestMapping(value = {"${server.prefix:${spring.application.name:}}/restconf/managementDomain"})
@RestController
public class ManagementDomainController extends BaseController {

        private final ManagementDomainService managementDomainService;

        @Autowired
        public ManagementDomainController(ManagementDomainService managementDomainService) {
                this.managementDomainService = managementDomainService;
        }

        @ApiOperation(value = "getAllManagementDomains", notes = "获取所有管理域", httpMethod = "POST")
        @PostMapping("getAllManagementDomains")
        public ResponseData<ResponseOfGetAllManagementDomains> getAllManagementDomains(@RequestBody @Validated RequestOfGetAllManagementDomains request) {

                ResponseOfGetAllManagementDomains responseOfGetAllManagementDomains = managementDomainService.getAllManagementDomains(request);
                return ResponseData.ok(responseOfGetAllManagementDomains);
        }
}
