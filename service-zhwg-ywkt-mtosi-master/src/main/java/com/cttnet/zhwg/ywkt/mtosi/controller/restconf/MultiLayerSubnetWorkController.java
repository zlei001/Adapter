package com.cttnet.zhwg.ywkt.mtosi.controller.restconf;

import com.cttnet.common.enums.ResponseData;
import com.cttnet.zhwg.ywkt.mtosi.data.dto.request.RequestOfTakeOverSNC;
import com.cttnet.zhwg.ywkt.mtosi.data.dto.response.ResponseOfTakeOverSNC;
import com.cttnet.zhwg.ywkt.mtosi.service.restconf.MultiLayerSubnetWorkService;
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
 *
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/4/3
 * @since java 1.8
 */
@Api(value = "connection", tags = "复用段子网连接层")
@RestController
@RequestMapping(value = {"${server.prefix:${spring.application.name:}}/restconf/multiLayerSubnetWork"})
public class MultiLayerSubnetWorkController {

    private final MultiLayerSubnetWorkService multiLayerSubnetWorkService;

    @Autowired
    public MultiLayerSubnetWorkController(MultiLayerSubnetWorkService multiLayerSubnetWorkService) {
        this.multiLayerSubnetWorkService = multiLayerSubnetWorkService;
    }

    @ApiOperation(value = "takeOverSnc", notes = "路径搜索", httpMethod = "POST")
    @PostMapping("takeOverSnc")
    public ResponseData<?> takeOverSnc(@RequestBody @Validated RequestOfTakeOverSNC request) {

        return multiLayerSubnetWorkService.takeOverSnc(request);
    }
}
