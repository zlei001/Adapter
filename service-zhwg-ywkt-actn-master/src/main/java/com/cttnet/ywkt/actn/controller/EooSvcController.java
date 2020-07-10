package com.cttnet.ywkt.actn.controller;

import com.cttnet.common.enums.ResponseData;
import com.cttnet.ywkt.actn.capacity.bo.Status;
import com.cttnet.ywkt.actn.data.dto.request.CreateBasicSvcRequestDTO;
import com.cttnet.ywkt.actn.data.dto.request.DeleteBasicSvcRequestDTO;
import com.cttnet.ywkt.actn.data.dto.request.ModifyBasicSvcRequestDTO;
import com.cttnet.ywkt.actn.data.dto.request.prec.EooSvcPrecomputedRequestDTO;
import com.cttnet.ywkt.actn.data.dto.response.EooSvcPrecomputedResponseDTO;
import com.cttnet.ywkt.actn.data.valid.CreateBusiness;
import com.cttnet.ywkt.actn.data.valid.DeleteBusiness;
import com.cttnet.ywkt.actn.data.valid.ModifyBandwidthBusiness;
import com.cttnet.ywkt.actn.data.valid.ModifyFlowSwitchBusiness;
import com.cttnet.ywkt.actn.service.EooSvcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>以太EOO业务Controller</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-20
 * @since jdk 1.8
 */
@Api(tags = "以太EOO业务")
@RestController
@RequestMapping("eoo/svc")
public class EooSvcController {
    final private EooSvcService eooSvcService;

    public EooSvcController(EooSvcService eooSvcService) {
        this.eooSvcService = eooSvcService;
    }

    @ApiOperation(value = "创建业务")
    @PostMapping("/create")
    public ResponseData<String> create(@Validated({CreateBusiness.class}) @RequestBody CreateBasicSvcRequestDTO request) {
        String result = eooSvcService.create(request);
        return ResponseData.ok(result);
    }

    @ApiOperation(value = "调整带宽")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuid", dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "bandwidth", value = "bandwidth", dataTypeClass = Long.class, paramType = "path")
    })
    @PatchMapping("/updateBandwidth")
    public ResponseData<String> updateBandwidth(@Validated({ModifyBandwidthBusiness.class}) @RequestBody ModifyBasicSvcRequestDTO request) {
        String result = eooSvcService.updateBandwidth(request);
        return ResponseData.ok(result);
    }

    @ApiOperation(value = "调整流量开关")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuid", dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "name", value = "name", dataTypeClass = String.class, paramType = "path")
    })
    @PatchMapping("/updateFlowSwitch")
    public ResponseData<String> updateFlowSwitch(@Validated({ModifyFlowSwitchBusiness.class}) @RequestBody ModifyBasicSvcRequestDTO request) {

        String result = eooSvcService.updateFlowSwitch(request);
        return ResponseData.ok(result);
    }

    @ApiOperation(value = "删除业务")
    @ApiImplicitParam(name = "uuid", value = "uuid", dataTypeClass = String.class, paramType = "path")
    @DeleteMapping("/delete")
    public ResponseData<String> delete(@Validated({DeleteBusiness.class}) @RequestBody DeleteBasicSvcRequestDTO request) {

        String result = eooSvcService.delete(request);
        return ResponseData.ok(result);
    }

    @ApiOperation(value = "预计算")
    @PostMapping("/precomputed")
    public ResponseData<EooSvcPrecomputedResponseDTO> precomputed(@Validated @RequestBody EooSvcPrecomputedRequestDTO request) {

        EooSvcPrecomputedResponseDTO precomputed = eooSvcService.precomputed(request);
        return ResponseData.ok(precomputed);
    }

}
