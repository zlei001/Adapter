package com.cttnet.ywkt.actn.controller;

import com.cttnet.common.enums.ResponseData;
import com.cttnet.ywkt.actn.capacity.bo.Status;
import com.cttnet.ywkt.actn.data.dto.request.CreateBasicSvcRequestDTO;
import com.cttnet.ywkt.actn.data.dto.request.DeleteBasicSvcRequestDTO;
import com.cttnet.ywkt.actn.data.dto.request.ModifyBasicSvcRequestDTO;
import com.cttnet.ywkt.actn.data.dto.request.prec.EosSvcPrecomputedRequestDTO;
import com.cttnet.ywkt.actn.data.dto.response.EosSvcPrecomputedResponseDTO;
import com.cttnet.ywkt.actn.data.valid.CreateBusiness;
import com.cttnet.ywkt.actn.data.valid.DeleteBusiness;
import com.cttnet.ywkt.actn.data.valid.ModifyBandwidthBusiness;
import com.cttnet.ywkt.actn.data.valid.ModifyFlowSwitchBusiness;
import com.cttnet.ywkt.actn.service.EosSvcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>以太Eos业务Controller</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-20
 * @since jdk 1.8
 */
@Api(tags = "以太EOS业务")
@RestController
@RequestMapping("eos/svc")
public class EosSvcController {
    final private EosSvcService eosSvcService;

    public EosSvcController(EosSvcService eosSvcService) {
        this.eosSvcService = eosSvcService;
    }

    @ApiOperation(value = "创建业务")
    @PostMapping("/create")
    public ResponseData<String> create(@Validated({CreateBusiness.class}) @RequestBody CreateBasicSvcRequestDTO request) {
        String result = eosSvcService.create(request);
        return ResponseData.ok(result);
    }

    @ApiOperation(value = "调整带宽")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuid", dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "bandwidth", value = "bandwidth", dataTypeClass = Long.class, paramType = "path")
    })
    @PatchMapping("/updateBandwidth")
    public ResponseData<String> updateBandwidth(@Validated({ModifyBandwidthBusiness.class}) @RequestBody ModifyBasicSvcRequestDTO request) {
        String result = eosSvcService.updateBandwidth(request);
        return ResponseData.ok(result);
    }

    @ApiOperation(value = "调整流量开关")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuid", dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "name", value = "name", dataTypeClass = String.class, paramType = "path")
    })
    @PatchMapping("/updateFlowSwitch")
    public ResponseData<String> updateFlowSwitch(@Validated({ModifyFlowSwitchBusiness.class}) @RequestBody ModifyBasicSvcRequestDTO request) {

        String result = eosSvcService.updateFlowSwitch(request);
        return ResponseData.ok(result);
    }

    @ApiOperation(value = "删除业务")
    @ApiImplicitParam(name = "uuid", value = "uuid", dataTypeClass = String.class, paramType = "path")
    @DeleteMapping("/delete")
    public ResponseData<String> delete(@Validated({DeleteBusiness.class}) @RequestBody DeleteBasicSvcRequestDTO request) {

        String result = eosSvcService.delete(request);
        return ResponseData.ok(result);
    }

    @ApiOperation(value = "预计算")
    @PostMapping("/precomputed")
    public ResponseData<EosSvcPrecomputedResponseDTO> precomputed(@Validated @RequestBody EosSvcPrecomputedRequestDTO request) {

        EosSvcPrecomputedResponseDTO precomputed = eosSvcService.precomputed(request);
        return ResponseData.ok(precomputed);
    }
}
