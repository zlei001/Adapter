package com.cttnet.zhwg.ywkt.mtosi.controller.restconf;

import com.cttnet.common.enums.ResponseData;
import com.cttnet.zhwg.ywkt.mtosi.data.dto.request.RequestOfGetTerminationPoint;
import com.cttnet.zhwg.ywkt.mtosi.data.dto.request.RequestOfSetTerminationPointData;
import com.cttnet.zhwg.ywkt.mtosi.data.dto.response.ResponseOfSetTerminationPointData;
import com.cttnet.zhwg.ywkt.mtosi.service.restconf.TerminationPointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 *  端点控制层
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/4/3
 * @since java 1.8
 */
@Slf4j
@Api(value = "southCommon", tags = "端点控制层")
@RestController
@RequestMapping(value = {"${server.prefix:${spring.application.name:}}/restconf/terminationPoint"})
public class TerminationPointController {

    private final TerminationPointService terminationPointService;

    @Autowired
    public TerminationPointController(TerminationPointService terminationPointService) {
        this.terminationPointService = terminationPointService;
    }

    @ApiOperation(value = "setTerminationPointData", notes = "设置端口属性", httpMethod = "POST", response = ResponseData.class)
    @PostMapping("setTerminationPointData")
    public ResponseData<?> setTerminationPointData(@RequestBody @Validated  RequestOfSetTerminationPointData request) {

        return  terminationPointService.setTerminationPointData(request);
    }

    @ApiOperation(value = "getTerminationPoint", notes = "查询端口层参数", httpMethod = "POST")
    @PostMapping("getTerminationPoint")
    public ResponseData<?> getTerminationPoint(@RequestBody @Validated RequestOfGetTerminationPoint request) {

        ResponseData<?>response = terminationPointService.getTerminationPoint(request);
        return response;
    }
}
