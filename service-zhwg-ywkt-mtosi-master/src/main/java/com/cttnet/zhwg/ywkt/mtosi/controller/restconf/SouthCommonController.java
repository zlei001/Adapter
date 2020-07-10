package com.cttnet.zhwg.ywkt.mtosi.controller.restconf;

import com.cttnet.common.controller.BaseController;
import com.cttnet.common.enums.ResponseData;
import com.cttnet.zhwg.ywkt.mtosi.data.dto.request.RequestOfSetCommonAttributes;
import com.cttnet.zhwg.ywkt.mtosi.data.dto.request.RequestOfSetTerminationPointData;
import com.cttnet.zhwg.ywkt.mtosi.data.dto.response.ResponseOfSetCommonAttributes;
import com.cttnet.zhwg.ywkt.mtosi.data.dto.response.ResponseOfSetTerminationPointData;
import com.cttnet.zhwg.ywkt.mtosi.service.restconf.SouthCommonService;
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
 *  南向配置Controller
 * </pre>
 *
 * @author zhangyaomin
 * @date 2019/12/28
 * @since java 1.8
 */
@Slf4j
@Api(value = "southCommon", tags = "南向公共配置层")
@RestController
@RequestMapping(value = {"${server.prefix:${spring.application.name:}}/restconf/southCommon"})
public class SouthCommonController extends BaseController {

    private final SouthCommonService southCommonService;

    @Autowired
    public SouthCommonController(SouthCommonService southCommonService) {
        this.southCommonService = southCommonService;
    }


    @ApiOperation(value = "setCommonAttributes", notes = "设置公共属性", httpMethod = "POST", response = ResponseData.class)
    @PostMapping("setCommonAttributes")
    public ResponseData<?> setCommonAttributes(@RequestBody @Validated  RequestOfSetCommonAttributes request) {

        return southCommonService.setCommonAttributes(request);
    }


}
